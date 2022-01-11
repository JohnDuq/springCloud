package com.udemy.spring.cloud.service.register.app.client.impl;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;

import reactor.core.publisher.Mono;

public class EmailServiceClientImpl implements IEmailServiceClient{

    @Override
    public Mono<User> save(User user) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
