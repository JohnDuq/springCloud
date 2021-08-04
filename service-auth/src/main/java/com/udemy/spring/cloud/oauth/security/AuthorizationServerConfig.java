package com.udemy.spring.cloud.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Configuracion para acceso al microservicio service-auth
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") // Permite a todos los consumidores acceder a la ruta POST /oauth/token
                .checkTokenAccess("isAuthenticated()"); // Permite validar que el consumidor este autenticado
    }

    // Configuracion para autenticar un consumidor y los usuarios del micro
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("frontendapp") // Definen los consumidores
                .secret(bCryptPasswordEncoder.encode("secret")) // Define el secreto de autenticacion para el consumidor
                .scopes("read", "write") // Define el alcance del consumidor
                .authorizedGrantTypes("password", "refresh_token") // Define el metodo de autenticacion de los usuarios
                                                                   // en el consumidor, refresh token renueva el token
                                                                   // despues de un tiempo definido
                .accessTokenValiditySeconds(3600)// Tiempo de validez del token (1 hora)
                .refreshTokenValiditySeconds(3600);// Tiempo de refresh del token (1 hora)

        // Para agregar otro consumidor se usa metodo and() y se envía de nuevo la info
        // .and()
        // .withClient("frontendapp")
        // .secret(bCryptPasswordEncoder.encode("secret"))
        // .scopes("read", "write")
        // .authorizedGrantTypes("password", "refresh_token")
        // .accessTokenValiditySeconds(3600)
        // .refreshTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)// Define la interfaz de autenticacion de usuario
                .accessTokenConverter(accessTokenConverter())// Define el token converter
                .tokenStore(tokenStore());// Define el lugar de almacenamiento del token
    }

    // Define el Token y el secreto de codificacion
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("algun_codigo_secreto_aeiou"); // Define secreto de codificacion del JWT
        return jwtAccessTokenConverter;
    }

    // Define el lugar de almacenamiento del token
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

}
