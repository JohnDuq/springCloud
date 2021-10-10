package com.udemy.spring.cloud.gateway.filter;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SpringGlobalFilter implements GlobalFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(SpringGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("SpringGlobalFilter PRE Filter");
        exchange.getRequest().mutate().headers(headers -> headers.add("token", "123456")); // Agrega una cabecera a la petición
        return chain.filter(exchange) // .filter continua con los demas filtros
                .then(Mono.fromRunnable(() -> {
                    logger.info("SpringGlobalFilter POST Filter");

                    Optional
                        .ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
                        .ifPresent(value -> 
                            exchange.getResponse().getHeaders().add("token", value));

                    exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build()); // Agrega un valor en cookie
                    exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN); // Agrega un valor en la cabecera
                    // exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                }));
    }

    @Override
    public int getOrder() {
        return -1; // da el orden de ejecución del filtro
    }

}
