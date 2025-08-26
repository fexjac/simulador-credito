package com.fex.simulador.dto;

import java.time.LocalDate;
import java.util.List;

public class ProdutoDiaSerieResponseDTO {
    private LocalDate inicio;
    private LocalDate fim;
    private List<ProdutoDiaItemDTO> itens;

    public ProdutoDiaSerieResponseDTO(LocalDate inicio, LocalDate fim, List<ProdutoDiaItemDTO> itens) {
        this.inicio = inicio;
        this.fim = fim;
        this.itens = itens;
    }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFim() { return fim; }
    public List<ProdutoDiaItemDTO> getItens() { return itens; }
}
