package com.udemy.spring.cloud.service.register.app.client;

import com.udemy.spring.cloud.commons.model.auth.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import reactor.core.publisher.Mono;

public interface IEmailServiceClient {

    public Mono<User> save(User user);
    
}
