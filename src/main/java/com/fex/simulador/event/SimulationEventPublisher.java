// src/main/java/com/fex/simulador/event/SimulationEventPublisher.java
package com.fex.simulador.event;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fex.simulador.dto.SimulacaoResponse;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ApplicationScoped
public class SimulationEventPublisher {

    @Inject EventHubClientProvider provider;
    @Inject ObjectMapper mapper;

    public void publish(SimulacaoResponse payload) {
        provider.get().ifPresentOrElse(
                client -> doSend(client, payload),
                () -> Log.debug("Event Hubs não configurado/ativo; envio ignorado.")
        );
    }

    private void doSend(EventHubProducerClient client, SimulacaoResponse payload) {
        try {
            String json = mapper.writeValueAsString(payload);
            EventData event = new EventData(json.getBytes(StandardCharsets.UTF_8));
            client.send(List.of(event));
            Log.debugf("Evento enviado ao Event Hubs. idSimulacao=%s", payload.getIdSimulacao());
        } catch (Exception e) {

            Log.error("Falha ao enviar evento ao Event Hubs (simulação seguirá OK).", e);
        }
    }
}
