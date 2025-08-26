package com.fex.simulador.resource;

import com.fex.simulador.dto.ProdutoDiaResponseDTO;
import com.fex.simulador.dto.ProdutoDiaSerieResponseDTO;
import com.fex.simulador.service.AnalyticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;

@Path("/analytics")
@Produces(MediaType.APPLICATION_JSON)
public class AnalyticsResource {

    @Inject AnalyticsService analytics;


    @GET
    @Path("/produtos-dia")
    public ProdutoDiaResponseDTO porDia(@QueryParam("data") String dataStr) {
        LocalDate data = (dataStr == null || dataStr.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(dataStr);
        return analytics.porDia(data);
    }


    @GET
    @Path("/produtos-dia/serie")
    public ProdutoDiaSerieResponseDTO serie(@QueryParam("inicio") String inicioStr,
                                            @QueryParam("fim") String fimStr) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = (inicioStr == null || inicioStr.isBlank()) ? hoje.minusDays(6) : LocalDate.parse(inicioStr);
        LocalDate fim    = (fimStr == null || fimStr.isBlank())       ? hoje              : LocalDate.parse(fimStr);
        return analytics.serie(inicio, fim);
    }
}
