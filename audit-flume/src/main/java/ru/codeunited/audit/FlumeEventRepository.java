package ru.codeunited.audit;

import org.apache.flume.EventDeliveryException;
import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.flume.event.JSONEvent;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class FlumeEventRepository implements AuditEventRepository {

    private final FlumeEventRepositoryProperties setup;

    private final EmbeddedAgent agent;

    private final Logger log = LoggerFactory.getLogger(FlumeEventRepository.class);

    public FlumeEventRepository(FlumeEventRepositoryProperties setup) {
        this.setup = setup;

        setup.put("sinks", "sink1 sink2");
        setup.put("sink1.type", "avro");
        setup.put("sink2.type", "avro");
        setup.put("sink1.hostname", "collector1.apache.org");
        setup.put("sink1.port", "5564");
        setup.put("sink2.hostname", "collector2.apache.org");
        setup.put("sink2.port",  "5565");
        setup.put("processor.type", "load_balance");

        agent = new EmbeddedAgent(setup.getName());
        agent.configure(setup.getProperties());
    }

    @PostConstruct
    public void doStart() {
        agent.start();
    }

    @PreDestroy
    public void doStop() {
        agent.stop();
    }

    @Override
    public void add(AuditEvent event) {
        JSONEvent jsonEvent = new JSONEvent();
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonEvent.setBody(mapper.writeValueAsBytes(event));
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
