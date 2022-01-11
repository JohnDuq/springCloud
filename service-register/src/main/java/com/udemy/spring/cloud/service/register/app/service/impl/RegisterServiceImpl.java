package com.udemy.spring.cloud.service.register.app.service.impl;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;
import com.udemy.spring.cloud.service.register.app.client.IUserCloudClientFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class RegisterServiceImpl {


    @Autowired
    private WebClient webClient;
    @Autowired
    private IUserCloudClientFeign iUserCloudClientFeign;
    @Autowired
    private IEmailServiceClient iEmailServiceClient;

    public Mono<User> registerUser(User user) {
        return Mono.just(user)
                .map(iUserCloudClientFeign::save)
                .flatMap(userSaved -> {
                    return webClient.post()
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userSaved)
                            .retrieve()
                            .bodyToMono(User.class);
                });
    }

}
