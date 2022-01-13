package com.udemy.spring.cloud.service.register.app.service;

import com.udemy.spring.cloud.commons.model.auth.User;

import reactor.core.publisher.Mono;

public interface IRegisterService {

    public Mono<User> registerUser(User user);
    
}
