package com.fex.simulador.dto;

import java.util.List;

public class ResultadoDTO {
    private String tipo;
    private List<ParcelaDTO> parcelas;

    public ResultadoDTO() {}
    public ResultadoDTO(String tipo, List<ParcelaDTO> parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public String getTipo() { return tipo; }
    public List<ParcelaDTO> getParcelas() { return parcelas; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setParcelas(List<ParcelaDTO> parcelas) { this.parcelas = parcelas; }
}
