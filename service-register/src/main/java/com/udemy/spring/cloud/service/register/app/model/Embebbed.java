package com.udemy.spring.cloud.service.register.app.model;

import java.util.List;

import com.udemy.spring.cloud.commons.model.auth.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Embebbed {
    List<Role> roles;
}
