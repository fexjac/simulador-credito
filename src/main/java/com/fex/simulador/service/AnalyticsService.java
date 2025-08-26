package com.fex.simulador.service;

import com.fex.simulador.dto.ProdutoDiaItemDTO;
import com.fex.simulador.dto.ProdutoDiaResponseDTO;
import com.fex.simulador.dto.ProdutoDiaSerieResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AnalyticsService {

    @Inject EntityManager em;

    private static final String TZ = "America/Sao_Paulo";

    public ProdutoDiaResponseDTO porDia(LocalDate data) {
        String sql = """
            SELECT
              (s.created_at AT TIME ZONE '%s')::date AS dia,
              s.codigo_produto,
              s.descricao_produto,
              COUNT(*) AS qtd,
              COALESCE(SUM(s.valor_desejado), 0) AS soma_valor,
              COALESCE(AVG(s.valor_desejado), 0) AS media_valor,
              COALESCE(SUM(p.valor_prestacao), 0) AS soma_prestos_price
            FROM simulacao s
            LEFT JOIN parcela p ON p.simulacao_id = s.id AND p.tipo = 'PRICE'
            WHERE (s.created_at AT TIME ZONE '%s')::date = :dia
            GROUP BY dia, s.codigo_produto, s.descricao_produto
            ORDER BY s.codigo_produto
            """.formatted(TZ, TZ);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("dia", data)
                .getResultList();

        List<ProdutoDiaItemDTO> itens = mapRows(rows);
        return new ProdutoDiaResponseDTO(data, itens);
    }

    public ProdutoDiaSerieResponseDTO serie(LocalDate inicio, LocalDate fim) {
        if (fim.isBefore(inicio)) {
            var tmp = inicio;
            inicio = fim;
            fim = tmp;
        }

        String sql = """
            SELECT
              (s.created_at AT TIME ZONE '%s')::date AS dia,
              s.codigo_produto,
              s.descricao_produto,
              COUNT(*) AS qtd,
              COALESCE(SUM(s.valor_desejado), 0) AS soma_valor,
              COALESCE(AVG(s.valor_desejado), 0) AS media_valor,
              COALESCE(SUM(p.valor_prestacao), 0) AS soma_prestos_price
            FROM simulacao s
            LEFT JOIN parcela p ON p.simulacao_id = s.id AND p.tipo = 'PRICE'
            WHERE (s.created_at AT TIME ZONE '%s')::date BETWEEN :inicio AND :fim
            GROUP BY dia, s.codigo_produto, s.descricao_produto
            ORDER BY dia, s.codigo_produto
            """.formatted(TZ, TZ);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();

        List<ProdutoDiaItemDTO> itens = mapRows(rows);
        return new ProdutoDiaSerieResponseDTO(inicio, fim, itens);
    }

    private List<ProdutoDiaItemDTO> mapRows(List<Object[]> rows) {
        List<ProdutoDiaItemDTO> itens = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            LocalDate dia;
            Object oDia = r[0];
            if (oDia instanceof java.sql.Date d) {
                dia = d.toLocalDate();
            } else if (oDia instanceof LocalDate ld) {
                dia = ld;
            } else {
                dia = LocalDate.parse(oDia.toString());
            }

            int cod = ((Number) r[1]).intValue();
            String desc = (String) r[2];
            long qtd = ((Number) r[3]).longValue();
            BigDecimal soma = (BigDecimal) r[4];
            BigDecimal media = (BigDecimal) r[5];
            BigDecimal somaPrestos = (BigDecimal) r[6];

            itens.add(new ProdutoDiaItemDTO(dia, cod, desc, qtd, soma, media, somaPrestos));
        }
        return itens;
    }
}
