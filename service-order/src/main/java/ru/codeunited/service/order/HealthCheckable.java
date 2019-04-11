package ru.codeunited.service.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface HealthCheckable {

    default boolean isOk() {
        try {
            ResponseEntity<String> response = getRestTemplate().getForEntity(getBaseUrl() + "/actuator/health", String.class);
            return response.getStatusCodeValue() < 500;
        } catch (Exception e) {
            return false;
        }
    }

    RestTemplate getRestTemplate();

    String getBaseUrl();
}
