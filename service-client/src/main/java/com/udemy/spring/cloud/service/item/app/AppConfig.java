package com.udemy.spring.cloud.service.item.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate registryRestTemplate() {
        return new RestTemplate();
    }

}
