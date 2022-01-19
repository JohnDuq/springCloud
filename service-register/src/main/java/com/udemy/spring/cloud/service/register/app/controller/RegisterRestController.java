package com.udemy.spring.cloud.service.register.app.controller;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.service.register.app.common.Path;
import com.udemy.spring.cloud.service.register.app.service.IRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Path.REGISTER_USER)
public class RegisterRestController {

    @Autowired
    private IRegisterService iRegisterService;

    @PostMapping
    public Mono<User> registerUserEmail(@RequestBody Mono<User> mnUser) {
        return mnUser.flatMap(iRegisterService::registerUser);
    }

}
