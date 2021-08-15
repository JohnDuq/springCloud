package com.udemy.spring.cloud.email.controller.model.response;

import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmailAccountRes {
    private RegisterEmailAccountReq registerEmailAccountReq;
    private String status;
    private String message;
}
