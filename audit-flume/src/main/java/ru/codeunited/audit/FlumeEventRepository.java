package ru.codeunited.audit;

import org.apache.flume.EventDeliveryException;
import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.flume.event.JSONEvent;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FlumeEventRepository implements AuditEventRepository {

    private final EmbeddedAgent agent;

    private final Logger log = LoggerFactory.getLogger(FlumeEventRepository.class);

    public FlumeEventRepository(FlumeEventRepositoryProperties setup) {
        agent = new EmbeddedAgent(setup.getName());
        agent.configure(setup.getProperties());
    }

    public void doStart() {
        agent.start();
    }

    public void doStop() {
        agent.stop();
    }

    @Override
    public void add(AuditEvent event) {
        JSONEvent jsonEvent = new JSONEvent();
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonEvent.setHeaders(new HashMap<>());
            String body = mapper.writeValueAsString(event);
            jsonEvent.setBody(body.getBytes(UTF_8));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            agent.put(jsonEvent);
        } catch (EventDeliveryException e) {
            log.error("Fatal error", e);
        }
    }

    @Override
    public List<AuditEvent> find(String principal, Instant after, String type) {
        return Collections.emptyList();
    }
}
