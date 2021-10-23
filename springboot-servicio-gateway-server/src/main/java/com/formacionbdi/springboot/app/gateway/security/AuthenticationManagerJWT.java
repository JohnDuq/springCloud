package com.formacionbdi.springboot.app.gateway.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJWT implements ReactiveAuthenticationManager {

    @Value("${config.security.oauth.jwt.key}")
    private String keyJWT;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication.getCredentials().toString()) // Consigue el token de la petición
                .map(token -> {
                    // Consigue la llave del token del servidor de configuración en git
                    SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(keyJWT.getBytes()));
                    // Valida con la clave secreta el token y retorna la información usuario-roles
                    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
                })
                .map(claims -> {
                    // Se captura el nombre de usuario
                    String username = claims.get("user_name", String.class);
                    // Se captura los roles
                    List<String> roles = claims.get("authorities", List.class);

                    //Convierte el listado de roler a C. GrantedAuthority como lo requiere spring framewaork
                    Collection<GrantedAuthority> listAuthorities = 
                        roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                    // Retorna el usuario autenticado
                    return new UsernamePasswordAuthenticationToken(username, null, listAuthorities);
                });
    }
    
}
