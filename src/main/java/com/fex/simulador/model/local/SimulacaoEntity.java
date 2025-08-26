package com.fex.simulador.model.local;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacao")
public class SimulacaoEntity extends PanacheEntity {

    @Column(name = "valor_desejado", nullable = false, precision = 18, scale = 2)
    public BigDecimal valorDesejado;

    @Column(name = "prazo_meses", nullable = false)
    public int prazoMeses;

    @Column(name = "codigo_produto", nullable = false)
    public int codigoProduto;

    @Column(name = "descricao_produto", nullable = false, length = 200)
    public String descricaoProduto;

    @Column(name = "taxa_juros", nullable = false, precision = 10, scale = 9)
    public BigDecimal taxaJuros;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();
}
