package com.fex.simulador.service;

import com.fex.simulador.dto.PaginadoDTO;
import com.fex.simulador.dto.SimulacaoListaItemDTO;
import com.fex.simulador.model.local.SimulacaoEntity;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class SimulacaoQueryService {

    public PaginadoDTO<SimulacaoListaItemDTO> listar(int pagina, int tamPagina) {
        if (pagina < 1) pagina = 1;
        if (tamPagina < 1 || tamPagina > 500) tamPagina = 50;

        PanacheQuery<SimulacaoEntity> q = SimulacaoEntity.find("order by createdAt desc");
        long total = q.count();

        int first = (pagina - 1) * tamPagina;
        List<SimulacaoEntity> sims = q.page(first / tamPagina, tamPagina).list();


        List<SimulacaoListaItemDTO> itens = sims.stream().map(sim -> {
            BigDecimal totalPrice = Panache.getEntityManager()
                    .createQuery("""
                        select coalesce(sum(p.valorPrestacao), 0)
                        from ParcelaEntity p
                        where p.simulacao.id = :id and p.tipo = 'PRICE'
                    """, BigDecimal.class)
                    .setParameter("id", sim.id)
                    .getSingleResult();
            return new SimulacaoListaItemDTO(sim.id, sim.valorDesejado, sim.prazoMeses, totalPrice);
        }).toList();

        return new PaginadoDTO<>(pagina, total, tamPagina, itens);
    }
}
