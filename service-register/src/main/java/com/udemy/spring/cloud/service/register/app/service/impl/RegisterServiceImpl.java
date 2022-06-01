package com.udemy.spring.cloud.service.register.app.service.impl;

import java.util.Date;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IEmailServiceClient;
import com.udemy.spring.cloud.service.register.app.client.IUserRoleCloudClient;
import com.udemy.spring.cloud.service.register.app.common.DefaultValue;
import com.udemy.spring.cloud.service.register.app.common.Status;
import com.udemy.spring.cloud.service.register.app.service.IRegisterService;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements IRegisterService {

    private final IUserRoleCloudClient iUserRoleCloudClient;
    private final IEmailServiceClient iEmailServiceClient;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Mono<User> registerUser(User user) {
        return Mono.just(user)
                .doOnNext(this::setDefaultValues)
                .flatMap(iUserRoleCloudClient::save)
                .flatMap(iEmailServiceClient::sendEmailVerification);
    }

    private User setDefaultValues(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(Status.DISABLE);
        user.setEmailStatus(Status.NO_VERIFIED);
        user.setEmailToken(RandomStringUtils.randomAlphabetic(15));
        user.setLoginTry(DefaultValue.LOGIN_TRY);
        user.setCreateAt(new Date());
        user.setCreateFor(DefaultValue.SYSTEM); //TODO Put the user that make the transaction
        return user;
    }

}
