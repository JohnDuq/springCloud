package com.udemy.spring.cloud.oauth.client;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.oauth.model.Roles;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-cloud")
public interface IUserCloudClientFeign {

    @GetMapping("/user-dao/search/findByUsername")
    public User findByUsername(@RequestParam("username") String username);

    @GetMapping("/role-dao/search/getRolesByUser")
    public Roles getRolesByUser(@RequestParam("username") String username);

}
