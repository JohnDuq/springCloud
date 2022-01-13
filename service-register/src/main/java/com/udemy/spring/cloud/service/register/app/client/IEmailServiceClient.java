package com.udemy.spring.cloud.service.register.app.client;

import com.udemy.spring.cloud.commons.model.auth.User;

import reactor.core.publisher.Mono;
public interface IEmailServiceClient {

    public Mono<User> save(User user);
    
}
