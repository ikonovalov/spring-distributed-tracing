package ru.codeunited.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

@Configuration
@EnableConfigurationProperties(FlumeEventRepositoryProperties.class)
@ConditionalOnProperty(
        prefix = "audit.flume",
        name = "enabled",
        havingValue = "true"
)
public class FlumeEventRepositoryAutoConfiguration {

    private final FlumeEventRepositoryProperties properties;

    @Autowired
    public FlumeEventRepositoryAutoConfiguration(FlumeEventRepositoryProperties properties) {
        this.properties = properties;
    }

    @WithFlume @Bean(initMethod = "doStart", destroyMethod = "doStop")
    public FlumeEventRepository flumeEventRepository(FlumeEventRepositoryProperties properties) {
        return new FlumeEventRepository(properties);
    }

}
