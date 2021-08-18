package com.udemy.spring.cloud.email.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.udemy.spring.cloud.email.controller.common.EmailAccountStatus;
import com.udemy.spring.cloud.email.controller.common.ServiceMapping;
import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.controller.model.response.ConfirmEmailAccountRes;
import com.udemy.spring.cloud.email.controller.model.response.RegisterEmailAccountRes;
import com.udemy.spring.cloud.email.model.data.ConfirmationToken;
import com.udemy.spring.cloud.email.model.data.UserEntity;
import com.udemy.spring.cloud.email.model.database.ConfirmationTokenRepository;
import com.udemy.spring.cloud.email.model.database.UserRepository;
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
        RegisterEmailAccountRes registerEmailAccountReqRes = new RegisterEmailAccountRes();
        UserEntity existingUser = userRepository.findByEmailIdIgnoreCase(registerEmailAccountReq.getEmail());
        if (existingUser != null) {
            registerEmailAccountReqRes.setStatus("ERROR");
            registerEmailAccountReqRes.setMessage("Email already exist");
        } else {

            UserEntity userEntity = new UserEntity();
            userEntity.setEmailId(registerEmailAccountReq.getEmail());
            userEntity.setEnabled(false);
            userEntity.setFirstName(registerEmailAccountReq.getFirstName());
            userEntity.setLastName(registerEmailAccountReq.getLastName());

            userRepository.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);

            confirmationTokenRepository.save(confirmationToken);

            MimeMessage mimeMessage = buildMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(userEntity.getEmailId());
            mimeMessageHelper.setSubject("Complete Registration!");
            // String linkButtonHtml = "<a href=\"http://localhost:8989" +
            // ServiceMapping.CONFIRM_EMAIL_ACCOUNT_VIEW + "/"
            // + confirmationToken.getConfirmationToken() + "\">HERE</a><br>";
            // String tokenValue = "Token value:" +
            // confirmationToken.getConfirmationToken();
            // mimeMessageHelper.setText("To confirm your account, please click here : " +
            // linkButtonHtml + tokenValue,
            // true);

            mimeMessageHelper.setText(iTemplateService.generateHtmlVerify(registerEmailAccountReq.getFirstName(),
                    registerEmailAccountReq.getLastName(), confirmationToken.getConfirmationToken()), true);

            sendEmail(mimeMessage);

            registerEmailAccountReqRes.setStatus("ok");

        }
        registerEmailAccountReqRes.setRegisterEmailAccountReq(registerEmailAccountReq);
        return registerEmailAccountReqRes;
    }

    public ConfirmEmailAccountRes confirmEmailAccount(String unconfirmedToken) {
        ConfirmEmailAccountRes confirmEmailAccountRes = new ConfirmEmailAccountRes();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(unconfirmedToken);
        if (token != null) {
            UserEntity userEntity = userRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
            userEntity.setEnabled(true);
            userRepository.save(userEntity);
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
