package com.udemy.spring.cloud.service.register.app.client.impl;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class EmailServiceClientImpl implements IEmailServiceClient{

    private static final String REGISTER_EMAIL_ACCOUNT = "/registerEmailAccount";

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<User> save(User user) {
        return webClient.post()
            .uri(REGISTER_EMAIL_ACCOUNT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .retrieve()
            .bodyToMono(User.class);
    }

}
