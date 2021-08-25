package com.udemy.spring.cloud.gateway.zuul.zuulgateway.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/gateway/api/security/**")
                .permitAll() // Permite a todos llegar a la ruta
            .antMatchers(HttpMethod.GET,
                "/gateway/api/user/user-dao",
                "/gateway/api/user/role-dao",
                "/gateway/api/user/user-role-dao")
                .permitAll() // Permite a todos llegar a la ruta
            .antMatchers(HttpMethod.GET, 
                "/gateway/api/user/user-dao/**",
                "/gateway/api/user/role-dao/**",
                "/gateway/api/user/user-role-dao/**",
                "/gateway/api/client/**", 
                "/gateway/api/producer/**")
                .hasAnyRole("ADMIN", "USER")// Permite solo a los roles realizar la consulta, no se requiere el ROLE_
            .antMatchers(HttpMethod.POST, 
                "/gateway/api/user/**",
                "/gateway/api/producer/**",
                "/gateway/api/client/**")
                .hasRole("ADMIN")// Permite solo al ADMIN realizar la creacion, no se requiere el ROLE_
            .antMatchers(HttpMethod.PUT, 
                "/gateway/api/user/**",
                "/gateway/api/producer/**",
                "/gateway/api/clien/t**")
                .hasRole("ADMIN")// Permite solo al ADMIN realizar la actualizacion, no se requiere el ROLE_
            .antMatchers(HttpMethod.DELETE, 
                "/gateway/api/user/**",
                "/gateway/api/producer/**",
                "/gateway/api/client/**")
                .hasRole("ADMIN")// Permite solo al ADMIN realizar la eliminacion, no se requiere el ROLE_
            .anyRequest().authenticated(); // Obliga a todas las demas peticiones estar autenticadas
    }

    // Define el lugar de almacenamiento del token
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Define el Token y el secreto de codificacion
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("algun_codigo_secreto_aeiou"); // Define secreto de codificacion del JWT
        return jwtAccessTokenConverter;
    }

}
