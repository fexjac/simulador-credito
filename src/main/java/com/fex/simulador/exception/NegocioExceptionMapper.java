package com.fex.simulador.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<NegocioException> {
    @Override
    public Response toResponse(NegocioException ex) {
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Map.of("erro", ex.getMessage()))
                .build();
    }
}
