package com.fex.simulador.dto;

import java.math.BigDecimal;
import java.util.List;

public class SimulacaoResponse {
    private long idSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaJuros;
    private List<ResultadoDTO> resultadoSimulacao;

    public long getIdSimulacao() { return idSimulacao; }
    public void setIdSimulacao(long idSimulacao) { this.idSimulacao = idSimulacao; }
    public int getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(int codigoProduto) { this.codigoProduto = codigoProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public List<ResultadoDTO> getResultadoSimulacao() { return resultadoSimulacao; }
    public void setResultadoSimulacao(List<ResultadoDTO> resultadoSimulacao) { this.resultadoSimulacao = resultadoSimulacao; }
}
