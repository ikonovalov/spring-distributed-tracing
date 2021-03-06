package ru.codeunited.service.order;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RefreshScope
public class OrderRestService {

    private final TimeService timeService;

    private final ReservationService reservationService;

    private final AuditEventRepository eventRepository;

    private final Counter counter;

    private final Logger log = LoggerFactory.getLogger(OrderRestService.class);

    public OrderRestService(
            TimeService timeService,
            ReservationService reservationService,
            AuditEventRepository eventRepository,
            MeterRegistry registry) {

        this.timeService = timeService;
        this.reservationService = reservationService;
        this.eventRepository = eventRepository;
        this.counter = registry.counter("order.service.placed-orders");
    }

    @PostConstruct
    void afterConstruction() {
        log.info(OrderRestService.class.getName() + " is UP");
    }

    @PostMapping
    public ResponseEntity<String> placeOrder() {
        String orderId = UUID.randomUUID().toString();
        String nowTime = timeService.now();
        String reservation = reservationService.reservation(orderId);
        ResponseEntity<String> ok = ResponseEntity.ok("Order " + orderId + " placed in " + nowTime + "\n" + reservation + "\n");
        counter.increment();
        eventRepository.add(
                new AuditEvent("Non-principal", "PLACE_ORDER",
                        "orderId=" + orderId,
                        "reservation=" + reservation
                )
        );
        return ok;
    }

}


