package com.fex.simulador.resource;

import com.fex.simulador.dto.SimulacaoRequest;
import com.fex.simulador.dto.SimulacaoResponse;
import com.fex.simulador.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject SimulacaoService service;

    @POST
    public SimulacaoResponse criar(@Valid SimulacaoRequest req) {
        return service.simular(req);
    }
}
