package com.udemy.spring.cloud.email.service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.email.client.IUserCloudClientFeign;
import com.udemy.spring.cloud.email.model.data.ConfirmationToken;
import com.udemy.spring.cloud.email.service.IEmailService;
import com.udemy.spring.cloud.email.service.ITemplateService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    
    private final IUserCloudClientFeign iUserCloudClientFeign;
    private final ITemplateService iTemplateService;
    private final JavaMailSender javaMailSender;

    public MimeMessage buildMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public User registerEmailAccount(User user) throws MessagingException {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setCreatedDate(new Date());
        confirmationToken.setToken(user.getEmailToken());
        confirmationToken.setEmail(user.getEmail());

        MimeMessage mimeMessage = buildMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Verify Registration!");
        mimeMessageHelper.setText(iTemplateService.generateHtmlVerify(user), true);

        sendEmail(mimeMessage);
        return user;
    }

    public String confirmEmailAccount(String unconfirmedToken) {
        User user = iUserCloudClientFeign.findByEmailToken(unconfirmedToken);
        if (user != null) {
            user.setLoginTry(0);
            user.setEmailStatus("VERIFIED");
            iUserCloudClientFeign.save(user);
            return user.getEmail();
        } else {
            return null;
        }
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
