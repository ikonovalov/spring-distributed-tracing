package ru.codeunited.service.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

@SpringBootApplication
@EnableCircuitBreaker
public class ServiceOrder {

    @Bean
    @ConditionalOnProperty(prefix = "services", name="proxy")
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${service.proxy}") String proxyUrl) {

        return restTemplateBuilder
                .requestFactory(() -> {
                    SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
                    URL prx;
                    try {
                        prx = new URL(proxyUrl);
                    } catch (MalformedURLException e) {
                        throw new IllegalArgumentException(e);
                    }
                    Proxy proxy = new Proxy(
                            Proxy.Type.HTTP, new InetSocketAddress(prx.getHost(), prx.getPort())
                    );
                    clientHttpReq.setProxy(proxy);
                    return clientHttpReq;
                })
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrder.class, args).registerShutdownHook();
    }
}
