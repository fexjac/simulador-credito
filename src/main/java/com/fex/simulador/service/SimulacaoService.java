package com.fex.simulador.service;

import com.fex.simulador.dto.*;
import com.fex.simulador.exception.NegocioException;
import com.fex.simulador.model.ProdutoAz;
import com.fex.simulador.repository.ProdutoAzRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject ProdutoAzRepository produtoRepo;
    @Inject AmortizationService amort;

    public SimulacaoResponse simular(SimulacaoRequest req) {
        var valor = req.getValorDesejado();
        int prazo = req.getPrazo();

        if (valor == null || valor.signum() <= 0) {
            throw new NegocioException("valorDesejado deve ser positivo");
        }
        if (prazo <= 0) {
            throw new NegocioException("prazo deve ser positivo");
        }

        ProdutoAz produto = produtoRepo.findMatching(valor, prazo)
                .orElseThrow(() -> new NegocioException("Nenhum produto compatível com valor e prazo informados"));


        if (valor.compareTo(produto.getVrMinimo()) < 0 ||
                (produto.getVrMaximo() != null && valor.compareTo(produto.getVrMaximo()) > 0)) {
            throw new NegocioException("Valor não possui produto disponível");
        }
        if (prazo < produto.getMinimoMeses() ||
                (produto.getMaximoMeses() != null && prazo > produto.getMaximoMeses())) {
            throw new NegocioException("Prazo não possui produto disponível");
        }

        BigDecimal i = produto.getTaxaJuros();

        var sacParcelas   = amort.sac(valor, i, prazo);
        var priceParcelas = amort.price(valor, i, prazo);

        var sac   = new ResultadoDTO("SAC",   sacParcelas);
        var price = new ResultadoDTO("PRICE", priceParcelas);

        SimulacaoResponse resp = new SimulacaoResponse();
        resp.setIdSimulacao(System.currentTimeMillis());
        resp.setCodigoProduto(produto.getCoProduto());
        resp.setDescricaoProduto(produto.getNoProduto());
        resp.setTaxaJuros(produto.getTaxaJuros());
        resp.setResultadoSimulacao(List.of(sac, price));
        return resp;
    }
}
