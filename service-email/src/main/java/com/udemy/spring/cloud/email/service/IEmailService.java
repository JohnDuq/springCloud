package com.udemy.spring.cloud.email.service;

import javax.mail.MessagingException;

import com.udemy.spring.cloud.commons.model.auth.User;

public interface IEmailService {

    public User registerEmailAccount(User user) throws MessagingException;

    public String confirmEmailAccount(String unconfirmedToken);

}
