package ru.codeunited.service.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ReservationRestService {

    private final TimeService timeService;

    public ReservationRestService(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/reservation/{orderId}")
    public ResponseEntity<String> getReservation(@PathVariable String orderId) throws IOException {
        return ResponseEntity.ok("Order " + orderId + " reserved at " + timeService.now());
    }
}
