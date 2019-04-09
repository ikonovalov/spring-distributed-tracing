package ru.codeunited.service.time;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codeunited.audit.FlumeEventRepository;
import ru.codeunited.audit.WithFlume;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeEndpoint {

    private final Logger log = LoggerFactory.getLogger(TimeEndpoint.class);

    private final Counter counter;

    private final AuditEventRepository eventRepository;

    public TimeEndpoint(MeterRegistry registry, @WithFlume  AuditEventRepository eventRepository) {
        this.counter = registry.counter("time.service.now");
        this.eventRepository = eventRepository;
    }


    @GetMapping("/now")
    public ResponseEntity<String> getTime() {
        eventRepository.add(
                new AuditEvent(
                        "Non-principal", "TIME_ACCESS",
                        "low=yes"
                )
        );
        String now = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info(now);
        counter.increment();
        return ResponseEntity.ok(now);
    }

}
