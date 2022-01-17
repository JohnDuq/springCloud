package com.udemy.spring.cloud.service.register.app.client;

import com.udemy.spring.cloud.commons.model.auth.User;

import reactor.core.publisher.Mono;

public interface IUserRoleCloudClient {

    public Mono<User> findByUsername(String username);

    public Mono<User> save(User user);

    public Mono<User> update(User user, Long idUser);

}
