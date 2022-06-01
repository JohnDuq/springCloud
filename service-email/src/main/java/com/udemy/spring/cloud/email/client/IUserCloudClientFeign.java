package com.udemy.spring.cloud.email.client;

import com.udemy.spring.cloud.commons.model.auth.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-user-role")
public interface IUserCloudClientFeign {

    @GetMapping("/user-dao/search/findByEmailToken")
    public User findByEmailToken(@RequestParam("emailToken") String emailToken);

    @PostMapping("/user-dao")
    public User save(@RequestBody User user);

}
