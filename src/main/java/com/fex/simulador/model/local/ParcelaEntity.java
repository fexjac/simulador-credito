package com.fex.simulador.model.local;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parcela")
public class ParcelaEntity extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "simulacao_id")
    public SimulacaoEntity simulacao;

    @Column(name = "tipo", nullable = false, length = 10)
    public String tipo;

    @Column(name = "numero", nullable = false)
    public int numero;

    @Column(name = "valor_amortizacao", nullable = false, precision = 18, scale = 2)
    public BigDecimal valorAmortizacao;

    @Column(name = "valor_juros", nullable = false, precision = 18, scale = 2)
    public BigDecimal valorJuros;

    @Column(name = "valor_prestacao", nullable = false, precision = 18, scale = 2)
    public BigDecimal valorPrestacao;
}
