package com.fex.simulador.event;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@ApplicationScoped
public class EventHubClientProvider {

    @ConfigProperty(name = "eventhubs.enabled", defaultValue = "true")
    boolean enabled;

    @ConfigProperty(name = "eventhubs.connection-string")
    Optional<String> connectionString;

    @ConfigProperty(name = "eventhubs.event-hub-name", defaultValue = "")
    String eventHubName;

    private EventHubProducerClient client;

    @PostConstruct
    void init() {
        if (!enabled) {
            Log.infov("Event Hubs desabilitado (eventhubs.enabled=false).");
            return;
        }
        if (connectionString.isEmpty() || connectionString.get().isBlank()) {
            Log.warn("Event Hubs habilitado, mas 'eventhubs.connection-string' não foi definido.");
            return;
        }

        String conn = connectionString.get();
        boolean hasEntityPath = conn.toLowerCase().contains("entitypath=");

        EventHubClientBuilder builder = new EventHubClientBuilder();
        if (hasEntityPath) {
            // quando a connection string JÁ tem EntityPath
            client = builder.connectionString(conn).buildProducerClient();
        } else {
            // quando a connection string NÃO tem EntityPath – precisa do nome do hub
            if (eventHubName == null || eventHubName.isBlank()) {
                Log.warn("Connection string sem EntityPath e 'eventhubs.event-hub-name' vazio; não será possível enviar.");
                return;
            }
            client = builder.connectionString(conn, eventHubName).buildProducerClient();
        }

        Log.infov("EventHubProducerClient criado. Hub: {0}", hasEntityPath ? "(via EntityPath na connection string)" : eventHubName);
    }

    public Optional<EventHubProducerClient> get() {
        return Optional.ofNullable(client);
    }

    @PreDestroy
    void close() {
        if (client != null) {
            client.close();
            Log.debug("EventHubProducerClient fechado.");
        }
    }
}
