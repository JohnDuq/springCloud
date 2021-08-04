package com.udemy.spring.cloud.oauth.client;

import com.udemy.spring.cloud.commons.model.auth.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-cloud")
public interface IUserClientFeign {

    @GetMapping("/user-dao/search/findByUsername")
    public User findByUsername(@RequestParam String username);

}
