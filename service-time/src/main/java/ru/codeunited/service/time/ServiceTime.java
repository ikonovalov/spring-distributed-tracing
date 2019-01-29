package ru.codeunited.service.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceTime {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTime.class, args).registerShutdownHook();
    }
}
