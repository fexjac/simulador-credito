package com.fex.simulador.model.local;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto_local")
public class ProdutoLocal extends PanacheEntity {

    @Column(name = "co_produto", nullable = false, unique = true)
    public int coProduto;

    @Column(name = "no_produto", nullable = false, length = 200)
    public String noProduto;

    @Column(name = "pc_taxa_juros", nullable = false, precision = 10, scale = 9)
    public java.math.BigDecimal taxaJuros;

    @Column(name = "nu_minimo_meses", nullable = false)
    public int minimoMeses;

    @Column(name = "nu_maximo_meses")
    public Integer maximoMeses;

    @Column(name = "vr_minimo", nullable = false, precision = 18, scale = 2)
    public java.math.BigDecimal vrMinimo;

    @Column(name = "vr_maximo", precision = 18, scale = 2)
    public java.math.BigDecimal vrMaximo;
}
