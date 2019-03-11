package ru.codeunited.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RefreshScope
public class OrderRestService {

    private final TimeService timeService;

    private final ReservationService reservationService;

    private final Logger log = LoggerFactory.getLogger(OrderRestService.class);

    public OrderRestService(TimeService timeService, ReservationService reservationService) {
        this.timeService = timeService;
        this.reservationService = reservationService;
    }

    @PostConstruct
    void afterConstruction() {
        log.info(OrderRestService.class.getName() + " is UP");
    }

    @PostMapping
    public ResponseEntity<String> placeOrder() throws IOException {
        String orderId = UUID.randomUUID().toString();
        String nowTime = timeService.now();
        String reservation = reservationService.reservation(orderId);
        return ResponseEntity.ok("Order " + orderId + " placed in " + nowTime + "\n" + reservation + "\n");
    }


}


