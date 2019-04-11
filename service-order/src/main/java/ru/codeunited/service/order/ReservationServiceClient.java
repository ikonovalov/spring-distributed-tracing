package ru.codeunited.service.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "fallbackReservation")
    @Override
    public String reservation(String orderId) {
        String reservationUrl = reservationServiceUrl + "/reservation/{orderId}";
        ResponseEntity<String> response = restTemplate.getForEntity(reservationUrl, String.class, orderId);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to connect with time service");
        }
    }

    @SuppressWarnings({"unused", "SameReturnValue"})
    protected String fallbackReservation(String orderId) {
        return "Reservation service temporary not available";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public String getBaseUrl() {
        return reservationServiceUrl;
    }
}
