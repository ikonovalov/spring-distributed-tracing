package ru.codeunited.service.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

@SpringBootApplication
@EnableCircuitBreaker
public class ServiceOrder {

    @Configuration
    @ConditionalOnProperty(prefix = "services.proxy", name="enabled", havingValue = "false", matchIfMissing = true)
    public static class OpenConfiguration {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
            return restTemplateBuilder.build();
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "services.proxy", name="enabled", havingValue = "true")
    public static class ProxiedConfiguration {

        @Bean
        public RestTemplate restTemplate(
                RestTemplateBuilder restTemplateBuilder,
                @Value("${services.proxy.url}") String proxyUrl) {

            return restTemplateBuilder
                    .requestFactory(() -> {
                        SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
                        try {
                            URL prx = new URL(proxyUrl);
                            String host = prx.getHost();
                            int port = prx.getPort();
                            Proxy proxy = new Proxy(HTTP, new InetSocketAddress(host, port));
                            clientHttpReq.setProxy(proxy);
                            return clientHttpReq;
                        } catch (MalformedURLException e) {
                            throw new IllegalArgumentException(e);
                        }
                    })
                    .build();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrder.class, args).registerShutdownHook();
    }
}
