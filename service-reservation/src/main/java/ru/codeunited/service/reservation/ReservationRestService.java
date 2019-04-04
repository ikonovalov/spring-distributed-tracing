package ru.codeunited.service.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ReservationRestService {

    private final TimeService timeService;

    private final AuditEventRepository eventRepository;

    private final Logger log = LoggerFactory.getLogger(ReservationRestService.class);

    public ReservationRestService(TimeService timeService, AuditEventRepository eventRepository) {
        this.timeService = timeService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/reservation/{orderId}")
    public ResponseEntity<String> getReservation(@PathVariable String orderId) {
        String msg = "Order " + orderId + " reserved at " + timeService.now();
        log.info(msg);
        String reservationId = UUID.randomUUID().toString();
        eventRepository.add(new AuditEvent("Non-principal", "RESERVATION",
                    "orderId=" + orderId,
                    "reservationId=" + reservationId
                ));
        return ResponseEntity.ok(reservationId);
    }
}
