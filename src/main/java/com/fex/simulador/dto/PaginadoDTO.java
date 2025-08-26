package com.fex.simulador.dto;

import java.util.List;

public class PaginadoDTO<T> {
    private int pagina;
    private long qtdRegistros;
    private int qtdRegistrosPagina;
    private List<T> registros;

    public PaginadoDTO(int pagina, long total, int porPagina, List<T> registros) {
        this.pagina = pagina;
        this.qtdRegistros = total;
        this.qtdRegistrosPagina = porPagina;
        this.registros = registros;
    }

    public int getPagina() { return pagina; }
    public long getQtdRegistros() { return qtdRegistros; }
    public int getQtdRegistrosPagina() { return qtdRegistrosPagina; }
    public List<T> getRegistros() { return registros; }
}
