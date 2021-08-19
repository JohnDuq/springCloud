package com.udemy.spring.cloud.email.service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.udemy.spring.cloud.email.controller.common.EmailAccountStatus;
import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.controller.model.response.ConfirmEmailAccountRes;
import com.udemy.spring.cloud.email.controller.model.response.RegisterEmailAccountRes;
import com.udemy.spring.cloud.email.model.data.ConfirmationToken;
import com.udemy.spring.cloud.email.model.data.UserEntity;
import com.udemy.spring.cloud.email.model.database.ConfirmationTokenRepository;
import com.udemy.spring.cloud.email.model.database.UserRepository;
import com.udemy.spring.cloud.email.service.IEmailService;
import com.udemy.spring.cloud.email.service.ITemplateService;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private UserRepository userRepository;
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

    public RegisterEmailAccountRes registerEmailAccount(RegisterEmailAccountReq registerEmailAccountReq)
            throws MessagingException {
        RegisterEmailAccountRes registerEmailAccountRes = new RegisterEmailAccountRes();
        UserEntity existingUser = userRepository.findByEmailIdIgnoreCase(registerEmailAccountReq.getEmail());
        if (existingUser != null) {
            registerEmailAccountRes.setStatus("ERROR");
            registerEmailAccountRes.setMessage("Email already exist");
        } else {

            UserEntity userEntity = new UserEntity();
            userEntity.setEmailId(registerEmailAccountReq.getEmail());
            userEntity.setEnabled(false);
            userEntity.setFirstName(registerEmailAccountReq.getFirstName());
            userEntity.setLastName(registerEmailAccountReq.getLastName());

            userRepository.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setCreatedDate(new Date());
            confirmationToken.setUserEntity(userEntity);
            confirmationToken.setConfirmationToken(RandomStringUtils.randomAlphabetic(15));

            confirmationTokenRepository.save(confirmationToken);

            MimeMessage mimeMessage = buildMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(userEntity.getEmailId());
            mimeMessageHelper.setSubject("Complete Registration!");
            mimeMessageHelper.setText(iTemplateService.generateHtmlVerify(registerEmailAccountReq,
                    confirmationToken.getConfirmationToken()), true);

            sendEmail(mimeMessage);

            registerEmailAccountRes.setStatus("ok");

        }
        registerEmailAccountRes.setRegisterEmailAccountReq(registerEmailAccountReq);
        return registerEmailAccountRes;
    }

    public ConfirmEmailAccountRes confirmEmailAccount(String unconfirmedToken) {
        ConfirmEmailAccountRes confirmEmailAccountRes = new ConfirmEmailAccountRes();
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(unconfirmedToken);
        if (confirmationToken != null) {
            UserEntity userEntity = userRepository
                    .findByEmailIdIgnoreCase(confirmationToken.getUserEntity().getEmailId());
            userEntity.setEnabled(true);
            userRepository.save(userEntity);

            confirmationToken.setConfirmationDate(new Date());
            confirmationTokenRepository.save(confirmationToken);

            confirmEmailAccountRes.setStatus(EmailAccountStatus.CONFIRMED);
            confirmEmailAccountRes.setUserEntity(userEntity);

        } else {
            confirmEmailAccountRes.setStatus(EmailAccountStatus.NOT_CONFIRMED);
            confirmEmailAccountRes.setMessage("The token is invalid");
        }
        return confirmEmailAccountRes;
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
