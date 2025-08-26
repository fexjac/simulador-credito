package com.fex.simulador.resource;

import com.fex.simulador.dto.PaginadoDTO;
import com.fex.simulador.dto.SimulacaoListaItemDTO;
import com.fex.simulador.service.SimulacaoQueryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoListResource {

    @Inject SimulacaoQueryService query;

    @GET
    public PaginadoDTO<SimulacaoListaItemDTO> listar(
            @QueryParam("pagina") @DefaultValue("1") int pagina,
            @QueryParam("qtdRegistrosPagina") @DefaultValue("50") int size
    ) {
        return query.listar(pagina, size);
    }
}
