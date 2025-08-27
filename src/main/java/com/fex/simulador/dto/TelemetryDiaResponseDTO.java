package com.fex.simulador.dto;

import java.time.LocalDate;
import java.util.List;

public class TelemetryDiaResponseDTO {
    private LocalDate dataReferencia;
    private List<TelemetryItemDTO> listaEndpoints;

    public TelemetryDiaResponseDTO(LocalDate dataReferencia, List<TelemetryItemDTO> listaEndpoints) {
        this.dataReferencia = dataReferencia;
        this.listaEndpoints = listaEndpoints;
    }

    public LocalDate getDataReferencia() { return dataReferencia; }
    public List<TelemetryItemDTO> getListaEndpoints() { return listaEndpoints; }
}
