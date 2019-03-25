package ru.codeunited.service.time;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeEndpoint {

    private final Logger log = LoggerFactory.getLogger(TimeEndpoint.class);

    private final Counter counter;

    public TimeEndpoint(MeterRegistry registry) {
        this.counter = registry.counter("time.service.now");
    }


    @GetMapping("/now")
    public ResponseEntity<String> getTime() {
        String now = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info(now);
        counter.increment();
        return ResponseEntity.ok(now);
    }

}
