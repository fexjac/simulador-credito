package com.fex.simulador.dto;

import java.time.LocalDate;
import java.util.List;

public class ProdutoDiaResponseDTO {
    private LocalDate dataReferencia;
    private List<ProdutoDiaItemDTO> itens;

    public ProdutoDiaResponseDTO(LocalDate dataReferencia, List<ProdutoDiaItemDTO> itens) {
        this.dataReferencia = dataReferencia;
        this.itens = itens;
    }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public List<ProdutoDiaItemDTO> getItens() { return itens; }
}
