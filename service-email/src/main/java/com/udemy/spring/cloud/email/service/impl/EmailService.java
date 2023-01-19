package com.udemy.spring.cloud.email.service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.udemy.spring.cloud.commons.model.auth.Role;
import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.commons.model.auth.UserRole;
import com.udemy.spring.cloud.email.client.IUserRoleCloudClientFeign;
import com.udemy.spring.cloud.email.service.IEmailService;
import com.udemy.spring.cloud.email.service.ITemplateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final IUserRoleCloudClientFeign iUserRoleCloudClientFeign;
    private final ITemplateService iTemplateService;
    private final JavaMailSender javaMailSender;

    public MimeMessage buildMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public User registerEmailAccount(User user) throws MessagingException {
        MimeMessage mimeMessage = buildMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Verify Registration!");
        mimeMessageHelper.setText(iTemplateService.generateHtmlVerify(user), true);
        sendEmail(mimeMessage);
        return user;
    }

    public String confirmEmailAccount(String unconfirmedToken) {
        User user = iUserRoleCloudClientFeign.findUserByEmailToken(unconfirmedToken);
        if (user != null) {
            user.setLoginTry(0);
            user.setEmailStatus("VERIFIED");
            user.setStatus("ENABLE");
            user.setEmailToken(null);
            user = iUserRoleCloudClientFeign.saveUser(user);

            Role role = iUserRoleCloudClientFeign.findRoleByName("ROLE_USER");

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRole.setCreateAt(new Date());
            userRole.setCreateFor("SYSTEM");

            iUserRoleCloudClientFeign.saveUserRole(userRole);

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
