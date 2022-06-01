package com.udemy.spring.cloud.oauth.client;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.oauth.model.Roles;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-user-role")
public interface IUserCloudClientFeign {

    @GetMapping("/user-dao/search/findByUsername")
    public User findByUsername(@RequestParam("username") String username);

    @PostMapping("/user-dao")
    public User save(@RequestBody User user);

    @GetMapping("/role-dao/search/getRolesByUser")
    public Roles getRolesByUser(@RequestParam("username") String username);

}
