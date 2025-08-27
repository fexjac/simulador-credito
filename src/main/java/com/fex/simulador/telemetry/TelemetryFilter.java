package com.fex.simulador.telemetry;

import com.fex.simulador.model.local.RequestMetric;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.util.concurrent.TimeUnit;

@Provider
@Priority(Priorities.USER)
@ApplicationScoped
public class TelemetryFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String START = "_t0_nanos";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty(START, System.nanoTime());
    }

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
        Object started = req.getProperty(START);
        if (started == null) return;

        long nanos = System.nanoTime() - (long) started;
        int ms = (int) Math.min(Integer.MAX_VALUE, TimeUnit.NANOSECONDS.toMillis(nanos));

        String path = req.getUriInfo().getPath();
        if (path.startsWith("q/") || path.startsWith("openapi")) return;

        persistAsync(req.getMethod(), path, res.getStatus(), ms);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void persistAsync(String method, String path, int status, int durationMs) {
        RequestMetric m = new RequestMetric();
        m.method = method;
        m.path = path;
        m.status = status;
        m.durationMs = durationMs;
        m.persist();
    }
}
