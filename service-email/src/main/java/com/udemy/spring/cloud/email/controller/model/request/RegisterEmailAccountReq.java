package com.udemy.spring.cloud.email.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmailAccountReq {

    private String email;
    private String firstName;
    private String lastName;
    
}
