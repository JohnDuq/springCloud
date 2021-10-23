package com.formacionbdi.springboot.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Captura el token del request
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)) 
            // Verifica que venga con el estandard Bearer
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            // Emite un vacío en las credenciales si no cumple
            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
            // Reemplaza el Bearer
            .map(token -> token.replace("Bearer ", ""))
            // Emite el token con el ReactiveAuthenticationManager
            .flatMap(token -> 
                    reactiveAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
            // Escribe el authentication en el contexto reactivo
            .flatMap(authentication -> 
                chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }
    
}
