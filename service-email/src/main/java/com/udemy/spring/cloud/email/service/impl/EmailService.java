package com.udemy.spring.cloud.email.service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.email.model.data.ConfirmationToken;
import com.udemy.spring.cloud.email.model.database.ConfirmationTokenRepository;
import com.udemy.spring.cloud.email.service.IEmailService;
import com.udemy.spring.cloud.email.service.ITemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private ITemplateService iTemplateService;

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public MimeMessage buildMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public User registerEmailAccount(User user) throws MessagingException {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setCreatedDate(new Date());
        confirmationToken.setToken(user.getEmailToken());
        confirmationToken.setEmail(user.getEmail());

        confirmationTokenRepository.save(confirmationToken);

        MimeMessage mimeMessage = buildMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Complete Registration!");
        mimeMessageHelper.setText(iTemplateService.generateHtmlVerify(user), true);

        sendEmail(mimeMessage);
        return user;
    }

    public ConfirmationToken confirmEmailAccount(String unconfirmedToken) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(unconfirmedToken);
        if (confirmationToken != null) {
            confirmationTokenRepository.deleteById(confirmationToken.getTokenId());
        }
        return confirmationToken;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Async
    public void sendEmail(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }

}
