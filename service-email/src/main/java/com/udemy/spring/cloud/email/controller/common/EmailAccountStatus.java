package com.udemy.spring.cloud.email.controller.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailAccountStatus {
    
    public static final String CONFIRMED = "CONFIRMED";
    public static final String NOT_CONFIRMED = "NOT_CONFIRMED";

}
