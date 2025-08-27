package com.fex.simulador.resource;

import com.fex.simulador.dto.TelemetryDiaResponseDTO;
import com.fex.simulador.service.TelemetryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
public class TelemetryResource {

    @Inject TelemetryService telemetry;

    @GET
    public TelemetryDiaResponseDTO porDia(@QueryParam("data") String dataStr) {
        LocalDate dia = (dataStr == null || dataStr.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(dataStr);
        return telemetry.porDia(dia);
    }
}
