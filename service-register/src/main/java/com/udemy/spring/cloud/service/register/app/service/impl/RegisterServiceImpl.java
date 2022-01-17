package com.udemy.spring.cloud.service.register.app.service.impl;

import java.util.Date;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;
import com.udemy.spring.cloud.service.register.app.common.DefaultValue;
import com.udemy.spring.cloud.service.register.app.common.Status;
import com.udemy.spring.cloud.service.register.app.client.IUserRoleCloudClient;
import com.udemy.spring.cloud.service.register.app.service.IRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private IUserRoleCloudClient iUserRoleCloudClient;
    @Autowired
    private IEmailServiceClient iEmailServiceClient;

    public Mono<User> registerUser(User user) {
        return Mono.just(user)
                .doOnNext(this::setDefaultValues)
                .flatMap(iUserRoleCloudClient::save)
                .flatMap(iEmailServiceClient::sendEmailVerification);
    }

    private User setDefaultValues(User user){
        user.setStatus(Status.DISABLE);
        user.setEmailStatus(Status.NO_VERIFIED);
        user.setLoginTry(DefaultValue.LOGIN_TRY);
        user.setCreateAt(new Date());
        user.setCreateFor(DefaultValue.SYSTEM);
        return user;
    }

}
