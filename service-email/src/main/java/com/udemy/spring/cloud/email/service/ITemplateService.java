package com.udemy.spring.cloud.email.service;

import com.udemy.spring.cloud.commons.model.auth.User;

public interface ITemplateService {

    public String generateHtmlVerify(User user);
    
}
