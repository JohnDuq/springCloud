package com.udemy.spring.cloud.email.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.udemy.spring.cloud.email.model.data.ConfirmationToken;
import com.udemy.spring.cloud.email.model.data.UserEntity;
import com.udemy.spring.cloud.email.model.database.ConfirmationTokenRepository;
import com.udemy.spring.cloud.email.model.database.UserRepository;
import com.udemy.spring.cloud.email.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody UserEntity userEntity) throws MessagingException {
        String response = "";
        UserEntity existingUser = userRepository.findByEmailIdIgnoreCase(userEntity.getEmailId());
        if (existingUser != null) {
            response = "error";
        } else {
            userRepository.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);

            confirmationTokenRepository.save(confirmationToken);

            MimeMessage mimeMessage = emailService.buildMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(userEntity.getEmailId());
            mimeMessageHelper.setSubject("Complete Registration!");
            String linkButtonHtml = "<a href=\"http://localhost:8989/confirm-account/"
                    + confirmationToken.getConfirmationToken() + "\">HERE</a>";
            mimeMessageHelper.setText("To confirm your account, please click here : " + linkButtonHtml, true);

            emailService.sendEmail(mimeMessage);

            response = "ok";

        }
        return response;
    }

    @GetMapping(value = "/confirm-account/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken) {
        String response = "";
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            UserEntity user = userRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
            user.setEnabled(true);
            userRepository.save(user);
            response = "ok";
        } else {
            response = "error";
        }
        return response;
    }
}
