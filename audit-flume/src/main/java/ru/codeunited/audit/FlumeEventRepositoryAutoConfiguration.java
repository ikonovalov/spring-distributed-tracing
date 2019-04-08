package ru.codeunited.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FlumeEventRepositoryProperties.class)
@ConditionalOnClass(name = {"org.springframework.boot.actuate.audit.AuditEventRepository"})
@ConditionalOnProperty(prefix = "audit.flume", name = "enabled", havingValue = "true", matchIfMissing = false)
public class FlumeEventRepositoryAutoConfiguration {

    @Autowired
    private FlumeEventRepositoryProperties properties;

    @Bean
    public FlumeEventRepository flumeEventRepository() {
        return new FlumeEventRepository(properties);
    }

}
