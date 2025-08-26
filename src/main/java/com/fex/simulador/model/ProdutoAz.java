package com.fex.simulador.model;

import java.math.BigDecimal;

public class ProdutoAz {
    private int coProduto;
    private String noProduto;
    private BigDecimal taxaJuros;
    private int minimoMeses;
    private Integer maximoMeses;
    private BigDecimal vrMinimo;
    private BigDecimal vrMaximo;

    public int getCoProduto() { return coProduto; }
    public void setCoProduto(int coProduto) { this.coProduto = coProduto; }
    public String getNoProduto() { return noProduto; }
    public void setNoProduto(String noProduto) { this.noProduto = noProduto; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public int getMinimoMeses() { return minimoMeses; }
    public void setMinimoMeses(int minimoMeses) { this.minimoMeses = minimoMeses; }
    public Integer getMaximoMeses() { return maximoMeses; }
    public void setMaximoMeses(Integer maximoMeses) { this.maximoMeses = maximoMeses; }
    public BigDecimal getVrMinimo() { return vrMinimo; }
    public void setVrMinimo(BigDecimal vrMinimo) { this.vrMinimo = vrMinimo; }
    public BigDecimal getVrMaximo() { return vrMaximo; }
    public void setVrMaximo(BigDecimal vrMaximo) { this.vrMaximo = vrMaximo; }
}
