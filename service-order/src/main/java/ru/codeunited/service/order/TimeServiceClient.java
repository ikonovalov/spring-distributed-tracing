package ru.codeunited.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TimeServiceClient implements TimeService {

    private final RestTemplate restTemplate;

    private Logger log = LoggerFactory.getLogger(TimeServiceClient.class);

    @Value("${services.time.url}")
    private String timeServiceUrl;

    public TimeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String now() {

        ResponseEntity<String> response = restTemplate.getForEntity(timeServiceUrl + "/now", String.class);

        HttpStatus statusCode = response.getStatusCode();
        log.debug("Call Time service and response Code : " + statusCode);

        if (statusCode == HttpStatus.OK) {
            return response.getBody();
        } else {
            log.error("Time service returns statusCode = " + statusCode);
            throw new RuntimeException("Failed to connect with time service");
        }
    }
}
