package com.fex.simulador.repository;

import com.fex.simulador.model.az.ProdutoAz;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

@ApplicationScoped
public class ProdutoAzRepository {

    @Inject
    @DataSource("mssql")
    AgroalDataSource dsMssql;

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public Optional<ProdutoAz> findMatching(BigDecimal valor, int prazoMeses) {
        final String sql = """
            SELECT TOP 1 CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS, NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO
            FROM dbo.PRODUTO
            WHERE (? >= VR_MINIMO) AND (VR_MAXIMO IS NULL OR ? <= VR_MAXIMO)
              AND (? >= NU_MINIMO_MESES) AND (NU_MAXIMO_MESES IS NULL OR ? <= NU_MAXIMO_MESES)
            ORDER BY PC_TAXA_JUROS ASC, CO_PRODUTO ASC
            """;
        try (Connection con = dsMssql.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, valor);
            ps.setBigDecimal(2, valor);
            ps.setInt(3, prazoMeses);
            ps.setInt(4, prazoMeses);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProdutoAz p = new ProdutoAz();
                    p.setCoProduto(rs.getInt("CO_PRODUTO"));
                    p.setNoProduto(rs.getString("NO_PRODUTO"));
                    p.setTaxaJuros(rs.getBigDecimal("PC_TAXA_JUROS"));
                    p.setMinimoMeses(rs.getInt("NU_MINIMO_MESES"));
                    int max = rs.getInt("NU_MAXIMO_MESES");
                    p.setMaximoMeses(rs.wasNull() ? null : max);
                    p.setVrMinimo(rs.getBigDecimal("VR_MINIMO"));
                    BigDecimal vrMax = rs.getBigDecimal("VR_MAXIMO");
                    p.setVrMaximo(rs.wasNull() ? null : vrMax);
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na consulta dos produtos no Servidor Azure: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}
