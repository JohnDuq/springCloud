package com.udemy.spring.cloud.service.item.app;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {

    private String circuitBreakerNameExample = "myFirstCircuitBreakerResilience4j";
    private int slidingWindowSize = 10; // Rango de peticiones a medir
    private int failureRateThreshold = 5; // porcentaje de fallo permitido para no abrir el circuito
    private Duration waitDurationInOpenState = Duration.ofSeconds(10l); // Tiempo de espera para cerrar de nuevo el circuito

    @Bean
    @LoadBalanced
    public RestTemplate registryRestTemplate() {
        return new RestTemplate();
    }

    // Metodo para configurar la resiliencia para un circuito
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> buildCustomizerResilience4JCircuitBreakerFactory() {
        return factory -> factory.configureDefault(circuitBreakerName -> {
            if (circuitBreakerNameExample.equals(circuitBreakerName)) {
                // Configuración de CircuitBreaker especifica 
                // para el circuito con nombre 'myFirstCircuitBreakerResilience4j'
                return new Resilience4JConfigBuilder(circuitBreakerName)
                    .circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                            .slidingWindowSize(slidingWindowSize)
                            .failureRateThreshold(failureRateThreshold)
                            .waitDurationInOpenState(waitDurationInOpenState)
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.ofDefaults())
                    .build();
            } else {
                // Configuración de CircuitBreaker por defecto
                // slidingWindowSize=100, slidingWindowSize=50, waitDurationInOpenState=60 segundos
                return new Resilience4JConfigBuilder(circuitBreakerName)
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .build();
            }
        });
    }

}
