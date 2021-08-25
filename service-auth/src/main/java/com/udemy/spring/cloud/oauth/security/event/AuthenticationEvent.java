package com.udemy.spring.cloud.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvent implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationEvent.class);

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String message = "Succes login: " + userDetails.getUsername();
        System.out.println(message);
        log.info(message);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String message = "Error login: " + exception.getMessage();
        System.err.println(message);
        log.error(message);
    }

}
