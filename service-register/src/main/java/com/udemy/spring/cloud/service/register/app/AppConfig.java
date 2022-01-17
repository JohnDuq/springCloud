package com.udemy.spring.cloud.service.register.app;

import com.udemy.spring.cloud.service.register.app.common.BeanName;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    private static final String HTTP = "http://";

    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder();
    }
    
    @Bean(BeanName.SERVICE_EMAIL)
    public WebClient webClientServiceEmail(WebClient.Builder builder) {
        return builder.baseUrl(HTTP.concat(BeanName.SERVICE_EMAIL)).build();
    }

    @Bean(BeanName.SERVICE_USER_ROLE)
    public WebClient webClientUserRole(WebClient.Builder builder) {
        return builder.baseUrl(HTTP.concat(BeanName.SERVICE_USER_ROLE)).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
