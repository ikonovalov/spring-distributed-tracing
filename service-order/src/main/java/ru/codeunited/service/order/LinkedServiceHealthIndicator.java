package ru.codeunited.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ClassUtils.getUserClass;

@Component
public class LinkedServiceHealthIndicator extends AbstractHealthIndicator {

    private List<HealthCheckable> healthCheckables;

    private Logger log = LoggerFactory.getLogger(LinkedServiceHealthIndicator.class);

    public LinkedServiceHealthIndicator(List<HealthCheckable> healthCheckable) {
        this.healthCheckables = healthCheckable;
    }

    private String renameBoolState(boolean b) {
        return b ? "CONNECTED" : "DISCONNECTED";
    }

    @Override
    protected void doHealthCheck(Health.Builder health) {
        try {
            Optional<Boolean> total = healthCheckables.stream().map(h -> {
                boolean isAlive = h.isOk();
                String serviceName = getUserClass(h).getSimpleName();
                String connectionState = renameBoolState(isAlive);
                health.withDetail(serviceName, connectionState);
                log.debug("Linked service {} is {}", serviceName, connectionState);
                return isAlive;
            }).reduce((l, r) -> l && r);

            total.map(t -> t ? health.up() : health.outOfService()).orElseGet(health::unknown);
        } catch (Exception e) {
            health.outOfService();
        }
    }
}
