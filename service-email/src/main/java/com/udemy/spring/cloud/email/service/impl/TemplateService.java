package com.udemy.spring.cloud.email.service.impl;

import java.io.StringWriter;

import com.udemy.spring.cloud.email.controller.common.ServiceMapping;
import com.udemy.spring.cloud.email.controller.model.request.RegisterEmailAccountReq;
import com.udemy.spring.cloud.email.service.ITemplateService;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class TemplateService implements ITemplateService {

    public String generateHtmlVerify(RegisterEmailAccountReq registerEmailAccountReq, String token) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        Template template = velocityEngine.getTemplate("templates/verifyEmail.html");

        VelocityContext context = new VelocityContext();
        context.put("view", ServiceMapping.CONFIRM_EMAIL_ACCOUNT_VIEW);
        context.put("firstName", registerEmailAccountReq.getFirstName());
        context.put("lastName", registerEmailAccountReq.getLastName());
        context.put("token", token);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
