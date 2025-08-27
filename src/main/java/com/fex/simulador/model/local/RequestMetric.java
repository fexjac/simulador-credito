package com.fex.simulador.model.local;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "request_metric")
public class RequestMetric extends PanacheEntity {

    @Column(nullable = false, length = 10)
    public String method;

    @Column(nullable = false, length = 255)
    public String path;

    @Column(nullable = false)
    public int status;

    @Column(name = "duration_ms", nullable = false)
    public int durationMs;

    @Column(name = "created_at", nullable = false)
    public java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
