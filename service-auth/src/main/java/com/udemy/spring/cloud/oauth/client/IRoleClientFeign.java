package com.udemy.spring.cloud.oauth.client;

import java.util.List;

import com.udemy.spring.cloud.commons.model.auth.Role;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-cloud")
public interface IRoleClientFeign {

    @GetMapping("/role-dao/search/getRolesByUser")
    public List<Role> getRolesByUser(@RequestParam String username);

}
