package com.udemy.spring.cloud.email.service;

import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;

public interface ITemplateService {

    public String generateHtmlVerify(RegisterEmailAccountReq registerEmailAccountReq, String token);
    
}
