package com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter.pre;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter.AttributeNameEnum;
import com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter.FilterTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PreTimeTrackFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PreTimeTrackFilter.class);
    private static final boolean SHOULD_FILTER = true;
    private static final int ORDER = 1;

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("Entering to pre filter gateway");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        log.info(String.format("%s request routes to %s", httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL().toString()));

        Long initTime = System.currentTimeMillis();
        httpServletRequest.setAttribute(AttributeNameEnum.INIT_TIME.getName(), initTime);

        return null;
    }

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getType();
    }

    @Override
    public int filterOrder() {
        return ORDER;
    }

}
