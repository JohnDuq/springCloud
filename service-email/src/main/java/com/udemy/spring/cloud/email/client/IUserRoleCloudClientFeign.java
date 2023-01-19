package com.udemy.spring.cloud.email.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.spring.cloud.commons.model.auth.Role;
import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.commons.model.auth.UserRole;

@FeignClient(name = "service-user-role")
public interface IUserRoleCloudClientFeign {

    @GetMapping("/role-dao/search/findByName")
    public Role findRoleByName(@RequestParam("name") String name);

    @GetMapping("/user-dao/search/findByEmailToken")
    public User findUserByEmailToken(@RequestParam("emailToken") String emailToken);

    @PostMapping("/user-dao")
    public User saveUser(@RequestBody User user);

    @PostMapping("/user-role-dao")
    public UserRole saveUserRole(@RequestBody UserRole userRole);
    
}
