package ru.codeunited.service.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.codeunited.audit.FlumeEventRepository;
import ru.codeunited.audit.FlumeEventRepositoryProperties;

@SpringBootApplication
public class ServiceTime {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTime.class, args).registerShutdownHook();
    }
}
