package com.fex.simulador.service;

import com.fex.simulador.dto.*;
import com.fex.simulador.exception.NegocioException;
import com.fex.simulador.model.ProdutoAz;
import com.fex.simulador.model.local.ParcelaEntity;
import com.fex.simulador.model.local.ProdutoLocal;
import com.fex.simulador.model.local.SimulacaoEntity;
import com.fex.simulador.repository.ProdutoAzRepository;
import com.fex.simulador.repository.local.ParcelaRepository;
import com.fex.simulador.repository.local.ProdutoLocalRepository;
import com.fex.simulador.event.SimulationEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject ProdutoAzRepository produtoRepoAz;
    @Inject ProdutoLocalRepository produtoLocalRepo;
    @Inject ParcelaRepository parcelaRepo;
    @Inject AmortizationService amort;
    @Inject SimulationEventPublisher publisher;

    @Transactional
    public SimulacaoResponse simular(SimulacaoRequest req) {
        var valor = req.getValorDesejado();
        int prazo = req.getPrazo();

        if (valor == null || valor.signum() <= 0) {
            throw new NegocioException("valorDesejado deve ser positivo");
        }
        if (prazo <= 0) {
            throw new NegocioException("prazo deve ser positivo");
        }


        ProdutoAz produto = produtoRepoAz.findMatching(valor, prazo)
                .orElseThrow(() -> new NegocioException("Nenhum produto compatível com valor e prazo informados"));

        validarFaixas(valor, prazo, produto);


        ProdutoLocal local = produtoLocalRepo.findByCodigo(produto.getCoProduto())
                .orElseGet(ProdutoLocal::new);
        local.coProduto   = produto.getCoProduto();
        local.noProduto   = produto.getNoProduto();
        local.taxaJuros   = produto.getTaxaJuros();
        local.minimoMeses = produto.getMinimoMeses();
        local.maximoMeses = produto.getMaximoMeses();
        local.vrMinimo    = produto.getVrMinimo();
        local.vrMaximo    = produto.getVrMaximo();
        produtoLocalRepo.persist(local);


        BigDecimal i = produto.getTaxaJuros();
        var sacParcelas   = amort.sac(valor, i, prazo);
        var priceParcelas = amort.price(valor, i, prazo);


        SimulacaoEntity sim = new SimulacaoEntity();
        sim.valorDesejado   = valor;
        sim.prazoMeses      = prazo;
        sim.codigoProduto   = produto.getCoProduto();
        sim.descricaoProduto= produto.getNoProduto();
        sim.taxaJuros       = produto.getTaxaJuros();
        sim.persist();

        var toPersist = new ArrayList<ParcelaEntity>(sacParcelas.size() + priceParcelas.size());
        toPersist.addAll(mapParcelas(sim, "SAC", sacParcelas));
        toPersist.addAll(mapParcelas(sim, "PRICE", priceParcelas));
        parcelaRepo.persist(toPersist);


        var sac   = new ResultadoDTO("SAC",   sacParcelas);
        var price = new ResultadoDTO("PRICE", priceParcelas);

        SimulacaoResponse resp = new SimulacaoResponse();
        resp.setIdSimulacao(sim.id);
        resp.setCodigoProduto(produto.getCoProduto());
        resp.setDescricaoProduto(produto.getNoProduto());
        resp.setTaxaJuros(produto.getTaxaJuros());
        resp.setResultadoSimulacao(List.of(sac, price));
        publisher.publish(resp);
        return resp;
    }

    private void validarFaixas(BigDecimal valor, int prazo, ProdutoAz produto) {
        if (valor.compareTo(produto.getVrMinimo()) < 0 ||
                (produto.getVrMaximo() != null && valor.compareTo(produto.getVrMaximo()) > 0)) {
            throw new NegocioException("Valor fora da faixa do produto");
        }
        if (prazo < produto.getMinimoMeses() ||
                (produto.getMaximoMeses() != null && prazo > produto.getMaximoMeses())) {
            throw new NegocioException("Prazo fora da faixa do produto");
        }
    }

    private List<ParcelaEntity> mapParcelas(SimulacaoEntity sim, String tipo, List<ParcelaDTO> src) {
        List<ParcelaEntity> out = new ArrayList<>(src.size());
        for (var p : src) {
            ParcelaEntity e = new ParcelaEntity();
            e.simulacao = sim;
            e.tipo = tipo;
            e.numero = p.getNumero();
            e.valorAmortizacao = p.getValorAmortizacao();
            e.valorJuros = p.getValorJuros();
            e.valorPrestacao = p.getValorPrestacao();
            out.add(e);
        }
        return out;
    }
}
