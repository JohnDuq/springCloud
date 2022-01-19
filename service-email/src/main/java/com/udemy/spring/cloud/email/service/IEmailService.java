package com.udemy.spring.cloud.email.service;

import javax.mail.MessagingException;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.email.model.data.ConfirmationToken;

public interface IEmailService {

    public User registerEmailAccount(User user) throws MessagingException;

    public ConfirmationToken confirmEmailAccount(String unconfirmedToken);

}
