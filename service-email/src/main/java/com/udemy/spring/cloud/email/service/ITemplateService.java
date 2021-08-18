package com.udemy.spring.cloud.email.service;

public interface ITemplateService {

    public String generateHtmlVerify(String firstName, String lastName, String token);
    
}
