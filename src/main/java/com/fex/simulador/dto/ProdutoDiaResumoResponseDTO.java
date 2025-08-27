package com.fex.simulador.dto;

import java.time.LocalDate;
import java.util.List;

public class ProdutoDiaResumoResponseDTO {
    private LocalDate dataReferencia;
    private List<ProdutoDiaResumoItemDTO> simulacoes;

    public ProdutoDiaResumoResponseDTO(LocalDate dataReferencia, List<ProdutoDiaResumoItemDTO> simulacoes) {
        this.dataReferencia = dataReferencia;
        this.simulacoes = simulacoes;
    }

    public LocalDate getDataReferencia() { return dataReferencia; }
    public List<ProdutoDiaResumoItemDTO> getSimulacoes() { return simulacoes; }
}
