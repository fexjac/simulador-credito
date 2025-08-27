package com.fex.simulador.service;

import com.fex.simulador.dto.TelemetryDiaResponseDTO;
import com.fex.simulador.dto.TelemetryItemDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TelemetryService {

    @Inject EntityManager em;

    private static final String TZ = "America/Sao_Paulo";

    public TelemetryDiaResponseDTO porDia(LocalDate dia) {
        String sql = """
            SELECT
              split_part(path, '/', 1) AS endpoint,
              COUNT(*)                                   AS qtd,
              CAST(AVG(duration_ms) AS BIGINT)           AS tempo_medio,
              MIN(duration_ms)                           AS tempo_min,
              MAX(duration_ms)                           AS tempo_max,
              CASE WHEN COUNT(*) = 0 THEN 0
                   ELSE SUM(CASE WHEN status BETWEEN 200 AND 299 THEN 1 ELSE 0 END)::decimal / COUNT(*)
              END                                        AS perc_sucesso
            FROM request_metric
            WHERE (created_at AT TIME ZONE '%s')::date = :dia
            GROUP BY endpoint
            ORDER BY endpoint
            """.formatted(TZ);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("dia", dia)
                .getResultList();

        List<TelemetryItemDTO> itens = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            String endpoint = (String) r[0];
            long qtd        = ((Number) r[1]).longValue();
            long media      = ((Number) r[2]).longValue();
            long min        = ((Number) r[3]).longValue();
            long max        = ((Number) r[4]).longValue();
            double sucesso  = ((BigDecimal) r[5]).doubleValue();

            itens.add(new TelemetryItemDTO(pretty(endpoint), qtd, media, min, max, sucesso));
        }
        return new TelemetryDiaResponseDTO(dia, itens);
    }

    private String pretty(String firstSegment) {
        return switch (firstSegment) {
            case "simulacoes" -> "Simulacao";
            case "analytics"  -> "Analytics";
            case "telemetria" -> "Telemetria";
            default -> firstSegment.isBlank() ? "root" : firstSegment;
        };
    }
}
