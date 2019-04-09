package ru.codeunited.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FlumeEventRepositoryProperties.class)
@ConditionalOnProperty(
        prefix = "audit.flume",
        name = "enabled",
        havingValue = "true"
)
public class FlumeEventRepositoryAutoConfiguration {

    @WithFlume @Bean(initMethod = "doStart", destroyMethod = "doStop")
    public FlumeEventRepository flumeEventRepository(FlumeEventRepositoryProperties properties) {
        return new FlumeEventRepository(properties);
    }

}
