package ru.codeunited.service.time;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeEndpoint {

    private Logger log = LoggerFactory.getLogger(TimeEndpoint.class);

    @GetMapping("/now")
    public ResponseEntity<String> getTime() {
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return ResponseEntity.ok(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

}
