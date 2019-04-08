package ru.codeunited.audit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "audit.flume", value = "audit.flume")
@Component
public class FlumeEventRepositoryProperties {

    private String name;

    private boolean enabled;

    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getName() {
        return name == null ? "default" : name;
    }

    public FlumeEventRepositoryProperties setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public FlumeEventRepositoryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public FlumeEventRepositoryProperties setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public FlumeEventRepositoryProperties put(String key, String value) {
        properties.put(key, value);
        return this;
    }

    public FlumeEventRepositoryProperties putAll(Map<String, String> keysValues) {
        properties.putAll(keysValues);
        return this;
    }
}
