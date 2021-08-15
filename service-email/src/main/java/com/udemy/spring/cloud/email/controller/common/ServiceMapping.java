package com.udemy.spring.cloud.email.controller.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceMapping {

    public static final String REGISTER_EMAIL_ACCOUNT = "/registerEmailAccount";
    public static final String CONFIRM_EMAIL_ACCOUNT = "/confirmEmailAccount";
    public static final String CONFIRM_EMAIL_ACCOUNT_VIEW = "/confirmEmailAccountView";
    public static final String CONFIRM_EMAIL_ACCOUNT_TOKEN = CONFIRM_EMAIL_ACCOUNT + "/{token}";
    public static final String CONFIRM_EMAIL_ACCOUNT_VIEW_TOKEN = CONFIRM_EMAIL_ACCOUNT_VIEW + "/{token}";

}
