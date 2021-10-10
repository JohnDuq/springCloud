package com.udemy.spring.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SpringGlobalFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(SpringGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("SpringGlobalFilter PRE Filter");
        return chain.filter(exchange) // .filter continua con los demas filtros
                .then(Mono.fromRunnable(() -> {
                    logger.info("SpringGlobalFilter POST Filter");
                    exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build()); // Agrega un valor en cookie
                    exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN); // Agrega un valor en la cabecera
                    // exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                }));
    }

}
