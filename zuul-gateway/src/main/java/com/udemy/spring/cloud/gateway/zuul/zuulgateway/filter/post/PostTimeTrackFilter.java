package com.udemy.spring.cloud.gateway.zuul.zuulgateway.filter.post;

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
public class PostTimeTrackFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTimeTrackFilter.class);
    private static final boolean SHOULD_FILTER = true;
    private static final int ORDER = 1;

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("Entering to post filter gateway");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        Long initTime = (Long) httpServletRequest.getAttribute(AttributeNameEnum.INIT_TIME.getName());
        Long finalTime = System.currentTimeMillis();
        Long timeElapsed = finalTime - initTime;

        log.info(String.format("Time elapsed in seconds %s", timeElapsed.doubleValue() / 1000.00));
        log.info(String.format("Time elapsed in mills %s", timeElapsed));

        return null;
    }

    @Override
    public String filterType() {
        return FilterTypeEnum.POST.getType();
    }

    @Override
    public int filterOrder() {
        return ORDER;
    }

}
