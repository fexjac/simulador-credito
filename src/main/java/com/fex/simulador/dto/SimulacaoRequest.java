package com.fex.simulador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SimulacaoRequest {
    @NotNull(message = "valorDesejado é obrigatório")
    @Min(value = 1, message = "valorDesejado deve ser >= 1")
    private BigDecimal valorDesejado;

    @Min(value = 1, message = "prazo deve ser >= 1")
    private int prazo;

    public BigDecimal getValorDesejado() { return valorDesejado; }
    public void setValorDesejado(BigDecimal valorDesejado) { this.valorDesejado = valorDesejado; }

    public int getPrazo() { return prazo; }
    public void setPrazo(int prazo) { this.prazo = prazo; }
}
