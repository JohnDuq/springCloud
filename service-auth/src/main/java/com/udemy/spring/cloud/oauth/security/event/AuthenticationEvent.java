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

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationEvent implements AuthenticationEventPublisher {

    private static final String SEPARATOR = "-";

    private Logger log = LoggerFactory.getLogger(AuthenticationEvent.class);

    @Autowired
    private Tracer tracer;
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
                StringBuilder errors = new StringBuilder();
                errors.append(message);
                User user = iUserService.findByUsername(authentication.getName());
                if (user.getLoginTry() == null) {
                    user.setLoginTry(0);
                }
                user.setLoginTry(user.getLoginTry() + 1);
                String errorloginTry = String.format("User %s tried login time %s", user.getUsername(), 
                    user.getLoginTry());
                log.error(errorloginTry);
                errors.append(SEPARATOR).append(errorloginTry);

                if (user.getLoginTry() >= loginTry) {
                    String errorUserDisabled = String.format("User %s DISABLE for maximun trys login %s", 
                        user.getUsername(), loginTry);
                    log.error(errorUserDisabled);
                    user.setStatus("DISABLE");
                    errors.append(SEPARATOR).append(errorUserDisabled);
                }

                iUserService.update(user, user.getIdUser());
                tracer.currentSpan().tag("ERROR TAG NAME", errors.toString());
            } catch (FeignException fe) {
                log.error("User does not exist!");
            }
        }
    }

}
