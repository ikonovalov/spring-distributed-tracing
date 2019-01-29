package ru.codeunited.service.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReservationServiceClient implements ReservationService {

    private final RestTemplate restTemplate;

    @Value("${services.reservation.url}")
    private String reservationServiceUrl;

    public ReservationServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String reservation(String orderId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                reservationServiceUrl + "/reservation/{orderId}", String.class, orderId
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        throw new RuntimeException("Failed to connect with time service");
    }
}
