package com.fex.simulador.dto;

public class TelemetryItemDTO {
    private String nomeApi;
    private long qtdRequisicoes;
    private long tempoMedio;   // ms
    private long tempoMinimo;  // ms
    private long tempoMaximo;  // ms
    private double percentualSucesso; // 0..1

    public TelemetryItemDTO(String nomeApi, long qtd, long media, long min, long max, double sucesso) {
        this.nomeApi = nomeApi;
        this.qtdRequisicoes = qtd;
        this.tempoMedio = media;
        this.tempoMinimo = min;
        this.tempoMaximo = max;
        this.percentualSucesso = sucesso;
    }

    public String getNomeApi() { return nomeApi; }
    public long getQtdRequisicoes() { return qtdRequisicoes; }
    public long getTempoMedio() { return tempoMedio; }
    public long getTempoMinimo() { return tempoMinimo; }
    public long getTempoMaximo() { return tempoMaximo; }
    public double getPercentualSucesso() { return percentualSucesso; }
}
