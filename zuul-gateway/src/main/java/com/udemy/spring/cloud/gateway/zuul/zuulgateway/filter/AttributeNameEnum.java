package com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeNameEnum {

    INIT_TIME("initTime", "time init transaction");

    private final String name;
    private final String description;

}
