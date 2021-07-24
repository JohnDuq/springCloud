package com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterTypeEnum {

    PRE("pre", "before route"), POST("post", "after route"), ROUTE("route", "between route");

    private final String type;
    private final String description;

}
