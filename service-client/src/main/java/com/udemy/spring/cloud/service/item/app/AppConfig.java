package com.udemy.spring.cloud.service.item.app;

import java.time.Duration;

import com.udemy.spring.cloud.service.item.app.common.CircuitName;

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

    private int slidingWindowSize = 10; // Rango de peticiones a medir
    private int failureRateThreshold = 50; // Porcentaje de fallo permitido para no abrir el circuito
    private int permittedNumberOfCallsInHalfOpenState = 5; // Numero permitido de peticiones en estado medio-abierto
    private int slowCallRateThreshold = 50; // Porcentaje permitido de llamadas con timeout
    private Duration waitDurationOpenState = Duration.ofSeconds(10L); // Tiempo de espera para cerrar de nuevo el circuito
    private Duration timeLimiterConfig = Duration.ofSeconds(10L); // (TIME OUT) Tiempo de espera del circuito
    private Duration slowCallDurationThreshold = Duration.ofSeconds(7L); // Tiempo de llamado considerado lento para el circuito

    @Bean
    @LoadBalanced
    public RestTemplate registryRestTemplate() {
        return new RestTemplate();
    }

    // Metodo para configurar la resiliencia para un circuito
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> buildCustomizerResilience4JCircuitBreakerFactory() {
        return factory -> factory.configureDefault(circuitBreakerName -> {
            if (CircuitName.MY_FIRST_CIRCUIT.equals(circuitBreakerName)) {
                // Configuración de CircuitBreaker especifica 
                // para el circuito con nombre 'myFirstCircuitBreakerResilience4j'
                return new Resilience4JConfigBuilder(circuitBreakerName)
                    .circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                            .slidingWindowSize(slidingWindowSize)
                            .failureRateThreshold(failureRateThreshold)
                            .waitDurationInOpenState(waitDurationOpenState)
                            .permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState)
                            .slowCallRateThreshold(slowCallRateThreshold)
                            .slowCallDurationThreshold(slowCallDurationThreshold)
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(timeLimiterConfig).build())
                    .build();
            } else {
                // Configuración de CircuitBreaker por defecto
                // slidingWindowSize=100, slidingWindowSize=50, waitDurationInOpenState=60 segundos, timeout=1 segundo
                return new Resilience4JConfigBuilder(circuitBreakerName)
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .build();
            }
        });
    }

}
