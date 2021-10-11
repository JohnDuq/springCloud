package com.udemy.spring.cloud.gateway.filter.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class SpecificGatewayFilterFactory
        extends AbstractGatewayFilterFactory<SpecificGatewayFilterFactory.Cookie> {

    private Logger logger = LoggerFactory.getLogger(SpecificGatewayFilterFactory.class);

    public SpecificGatewayFilterFactory() {
        super(Cookie.class);
    }

    @Override
    public GatewayFilter apply(Cookie cookie) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info(String.format("SPECIFIC PRE Filter %s", cookie.message));
            return chain.filter(exchange) // .filter continua con los demas filtros
                .then(Mono.fromRunnable(() -> {
                    Optional
                        .ofNullable(cookie.value)
                        .ifPresent(value -> // Si el valor es 
                            exchange.getResponse().addCookie(ResponseCookie.from(cookie.name, value).build()));
                    logger.info("SPECIFIC POST Filter");
                }));
        }, 2);// 2 es el orden de ejecución
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "name", "value");
    }

    @Override
    public String name() {
        return "ExampleCookieName"; // Nombre de referencia de la subclase para el YML
    }

    @Getter
    @Setter
    public static class Cookie {
        private String message;
        private String name;
        private String value;
    }

}
