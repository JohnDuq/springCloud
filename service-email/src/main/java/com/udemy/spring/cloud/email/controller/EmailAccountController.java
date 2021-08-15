package com.udemy.spring.cloud.email.controller;

import javax.mail.MessagingException;

import com.udemy.spring.cloud.email.controller.common.EmailAccountStatus;
import com.udemy.spring.cloud.email.controller.common.ServiceMapping;
import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.controller.model.response.ConfirmEmailAccountRes;
import com.udemy.spring.cloud.email.controller.model.response.RegisterEmailAccountRes;
import com.udemy.spring.cloud.email.service.IEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EmailAccountController {

    @Autowired
    private IEmailService iEmailService;

    @PostMapping(value = ServiceMapping.REGISTER_EMAIL_ACCOUNT)
    public RegisterEmailAccountRes registerEmailAccount(@RequestBody RegisterEmailAccountReq registerEmailAccountReq)
            throws MessagingException {
        return iEmailService.registerEmailAccount(registerEmailAccountReq);
    }

    @PostMapping(value = ServiceMapping.CONFIRM_EMAIL_ACCOUNT_TOKEN)
    public ConfirmEmailAccountRes confirmUserAccount(@PathVariable("token") String unconfirmedToken) {
        return iEmailService.confirmEmailAccount(unconfirmedToken);
    }

    @GetMapping(value = ServiceMapping.CONFIRM_EMAIL_ACCOUNT_VIEW)
    public ModelAndView confirmUserAccountView(ModelAndView modelAndView, @PathVariable("token") String unconfirmedToken) {
        ConfirmEmailAccountRes confirmEmailAccountRes = iEmailService.confirmEmailAccount(unconfirmedToken);
        if (EmailAccountStatus.CONFIRMED.equals(confirmEmailAccountRes.getStatus())) {
            modelAndView.addObject("message", confirmEmailAccountRes.getUserEntity().getEmailId());
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The token is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
