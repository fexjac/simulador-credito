package com.fex.simulador.dto;

import java.math.BigDecimal;

public class SimulacaoListaItemDTO {
    private long idSimulacao;
    private BigDecimal valorDesejado;
    private int prazo;
    private BigDecimal valorTotalParcelas;

    public SimulacaoListaItemDTO(long id, BigDecimal valor, int prazo, BigDecimal total) {
        this.idSimulacao = id;
        this.valorDesejado = valor;
        this.prazo = prazo;
        this.valorTotalParcelas = total;
    }

    public long getIdSimulacao() { return idSimulacao; }
    public BigDecimal getValorDesejado() { return valorDesejado; }
    public int getPrazo() { return prazo; }
    public BigDecimal getValorTotalParcelas() { return valorTotalParcelas; }
}
