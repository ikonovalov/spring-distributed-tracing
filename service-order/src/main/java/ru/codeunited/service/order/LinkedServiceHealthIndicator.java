package ru.codeunited.service.order;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class LinkedServiceHealthIndicator extends AbstractHealthIndicator {

    private final TimeService timeService;

    public LinkedServiceHealthIndicator(TimeService timeServiceClient) {
        this.timeService = timeServiceClient;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            builder.withDetail("time-service", timeService.now());

            builder.up();
        } catch (Exception e) {
            builder.outOfService();
        }
    }
}
