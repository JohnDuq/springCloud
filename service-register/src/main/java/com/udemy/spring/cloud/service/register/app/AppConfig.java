package com.udemy.spring.cloud.service.register.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient buildWebClient(){
        return WebClient.create("http://localhost:8090/gateway/api/user/user-dao");
    }

}
