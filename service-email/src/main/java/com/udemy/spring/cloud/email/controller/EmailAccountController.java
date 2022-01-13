package com.udemy.spring.cloud.email.controller;

import javax.mail.MessagingException;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.email.controller.common.EmailAccountStatus;
import com.udemy.spring.cloud.email.controller.common.ServiceMapping;
import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.controller.model.response.ConfirmEmailAccountRes;
import com.udemy.spring.cloud.email.service.IEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EmailAccountController {

    private static final String TOKEN = "token";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String ACCOUNT_VERIFIED = "accountVerified";

    @Autowired
    private IEmailService iEmailService;

    @PostMapping(value = ServiceMapping.REGISTER_EMAIL_ACCOUNT)
    public User registerEmailAccount(@RequestBody User user) throws MessagingException {
        iEmailService.registerEmailAccount(new RegisterEmailAccountReq(user.getEmail(), user.getName(),
                user.getLastName()));
        return user;
    }

    //TODO The confirmation return error 500
    @GetMapping(value = ServiceMapping.CONFIRM_EMAIL_ACCOUNT)
    public ConfirmEmailAccountRes confirmUserAccount(@RequestParam(value = TOKEN) String unconfirmedToken) {
        return iEmailService.confirmEmailAccount(unconfirmedToken);
    }

    @GetMapping(value = ServiceMapping.CONFIRM_EMAIL_ACCOUNT_VIEW_TOKEN)
    public ModelAndView confirmUserAccountView(ModelAndView modelAndView,
            @PathVariable(TOKEN) String unconfirmedToken) {
        ConfirmEmailAccountRes confirmEmailAccountRes = iEmailService.confirmEmailAccount(unconfirmedToken);
        if (EmailAccountStatus.CONFIRMED.equals(confirmEmailAccountRes.getStatus())) {
            modelAndView.addObject(MESSAGE, confirmEmailAccountRes.getUserEntity().getEmailId());
            modelAndView.setViewName(ACCOUNT_VERIFIED);
        } else {
            modelAndView.addObject(MESSAGE, "The token is invalid or broken!");
            modelAndView.setViewName(ERROR);
        }
        return modelAndView;
    }
}
