package com.udemy.spring.cloud.email.service;

import javax.mail.MessagingException;

import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.controller.model.response.ConfirmEmailAccountRes;
import com.udemy.spring.cloud.email.controller.model.response.RegisterEmailAccountRes;

public interface IEmailService {

    public RegisterEmailAccountRes registerEmailAccount(RegisterEmailAccountReq registerEmailAccountReq)
            throws MessagingException;

    public ConfirmEmailAccountRes confirmEmailAccount(String unconfirmedToken);

}
