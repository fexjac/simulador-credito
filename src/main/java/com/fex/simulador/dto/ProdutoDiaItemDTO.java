package com.fex.simulador.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProdutoDiaItemDTO {
    private LocalDate data;
    private int codigoProduto;
    private String descricaoProduto;
    private long qtdSimulacoes;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorMedioDesejado;
    private BigDecimal totalPrestacoesPrice; // soma das prestações PRICE do dia

    public ProdutoDiaItemDTO(LocalDate data, int codigoProduto, String descricaoProduto, long qtdSimulacoes,
                             BigDecimal valorTotalDesejado, BigDecimal valorMedioDesejado, BigDecimal totalPrestacoesPrice) {
        this.data = data;
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.qtdSimulacoes = qtdSimulacoes;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorMedioDesejado = valorMedioDesejado;
        this.totalPrestacoesPrice = totalPrestacoesPrice;
    }
    public LocalDate getData() { return data; }
    public int getCodigoProduto() { return codigoProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public long getQtdSimulacoes() { return qtdSimulacoes; }
    public BigDecimal getValorTotalDesejado() { return valorTotalDesejado; }
    public BigDecimal getValorMedioDesejado() { return valorMedioDesejado; }
    public BigDecimal getTotalPrestacoesPrice() { return totalPrestacoesPrice; }
}
