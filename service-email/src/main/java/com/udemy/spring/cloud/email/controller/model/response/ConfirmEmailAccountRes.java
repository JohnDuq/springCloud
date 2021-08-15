package com.udemy.spring.cloud.email.controller.model.response;
import com.udemy.spring.cloud.email.model.data.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmEmailAccountRes {

    private String status;
    private String message;
    private UserEntity userEntity;
    
}
