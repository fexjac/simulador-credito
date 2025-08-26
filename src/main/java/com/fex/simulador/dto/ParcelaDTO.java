package com.fex.simulador.dto;

import java.math.BigDecimal;

public class ParcelaDTO {
    private int numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    public ParcelaDTO() {}

    public ParcelaDTO(int numero, BigDecimal amort, BigDecimal juros, BigDecimal prest) {
        this.numero = numero;
        this.valorAmortizacao = amort;
        this.valorJuros = juros;
        this.valorPrestacao = prest;
    }

    public int getNumero() { return numero; }
    public BigDecimal getValorAmortizacao() { return valorAmortizacao; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public BigDecimal getValorPrestacao() { return valorPrestacao; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setValorAmortizacao(BigDecimal v) { this.valorAmortizacao = v; }
    public void setValorJuros(BigDecimal v) { this.valorJuros = v; }
    public void setValorPrestacao(BigDecimal v) { this.valorPrestacao = v; }
}
