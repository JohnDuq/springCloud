package com.udemy.spring.cloud.service.register.app.service.impl;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;
// import com.udemy.spring.cloud.service.register.app.client.IUserCloudClientFeign;
import com.udemy.spring.cloud.service.register.app.service.IRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class RegisterServiceImpl implements IRegisterService{

    // @Autowired
    // private IUserCloudClientFeign iUserCloudClientFeign;
    @Autowired
    private IEmailServiceClient iEmailServiceClient;

    public Mono<User> registerUser(User user) {
        return Mono.just(user)
                //.map(iUserCloudClientFeign::save)
                .flatMap(iEmailServiceClient::save);
    }

}
