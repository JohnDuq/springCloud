package com.udemy.spring.cloud.oauth.security.event;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.oauth.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import feign.FeignException;

@Component
public class AuthenticationEvent implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationEvent.class);

    @Autowired
    private IUserService iUserService;
    @Value("${config.security.oauth.client.id}")
    private String consumerApp;
    @Value("${config.security.oauth.user.login.try}")
    private Integer loginTry;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String message = "Succes login: " + userDetails.getUsername();
        System.out.println(message);
        log.info(message);
        if (!consumerApp.equals(authentication.getName())) {
            User user = iUserService.findByUsername(authentication.getName());
            user.setLoginTry(0);
            iUserService.update(user, user.getIdUser());
        }

    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String message = "Error login: " + exception.getMessage();
        System.err.println(message);
        log.error(message);
        if (!consumerApp.equals(authentication.getName())) {
            try {
                User user = iUserService.findByUsername(authentication.getName());
                if (user.getLoginTry() == null) {
                    user.setLoginTry(0);
                }
                user.setLoginTry(user.getLoginTry() + 1);
                log.error(String.format("User %s tried login time %s", user.getUsername(), user.getLoginTry()));

                if (user.getLoginTry() >= loginTry) {
                    log.error(String.format("User %s DISABLE for maximun trys login %s", user.getUsername(), loginTry));
                    user.setStatus("DISABLE");
                }

                iUserService.update(user, user.getIdUser());
            } catch (FeignException fe) {
                log.error("User does not exist!");
            }
        }
    }

}
