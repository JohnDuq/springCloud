package com.udemy.spring.cloud.service.register.app.client.impl;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.client.IUserRoleCloudClient;
import com.udemy.spring.cloud.service.register.app.common.BeanName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UserRoleCloudClientImpl implements IUserRoleCloudClient {

    @Autowired
    @Qualifier(BeanName.SERVICE_USER_ROLE)
    private WebClient webClient;

    public Mono<User> findByUsername(String username) {
        return null;
    }

    public Mono<User> save(User user) {
        return webClient.post()
            .uri("/".concat(BeanName.USER_DAO))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .retrieve()
            .bodyToMono(User.class);
    }

    public Mono<User> update(User user, Long idUser) {
        return null;
    }

}
