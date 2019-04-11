package ru.codeunited.service.order;

public interface ReservationService extends HealthCheckable {

    String reservation(String orderId);

}
