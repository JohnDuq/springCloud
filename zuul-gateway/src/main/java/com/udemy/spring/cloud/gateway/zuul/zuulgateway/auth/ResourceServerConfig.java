package com.udemy.spring.cloud.gateway.zuul.zuulgateway.auth;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${config.security.oauth.jwt.key}")
    private String secretKeyJWT;

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
            .anyRequest().authenticated()// Obliga a todas las demas peticiones estar autenticadas
            .and().cors().configurationSource(corsConfigurationSource()); // registra origenes cruzados
    }

    // Bean de registro de origenes cruzados
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        //corsConfig.addAllowedOrigin("*"); 
        //corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200")); para Angular
        corsConfig.setAllowedOrigins(Arrays.asList("*"));//permite transacciones de origen cruzado desde cualquier lugar
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));//permite los verbos del origen cruzado
        corsConfig.setAllowCredentials(true);//permite el uso de las credenciales
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));//permite solo éstas cabeceras de origen cruzado
        // Clase para registrar la configuracion de origenes cruzados
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    // Bean de registro de filtrado para los origenes cruzados
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(corsConfigurationSource()));// Se instancia el bean de registro de filtros con el CorsConfiguration creado
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Se le entrega la prioridad alta
        return bean;
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
        jwtAccessTokenConverter.setSigningKey(secretKeyJWT); 
        jwtAccessTokenConverter.setSigningKey(Base64.getEncoder().encodeToString(secretKeyJWT.getBytes()));
        // Define secreto de codificacion del JWT
        return jwtAccessTokenConverter;
    }

}
