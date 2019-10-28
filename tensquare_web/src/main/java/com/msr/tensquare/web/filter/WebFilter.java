package com.msr.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 22:34
 */
@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String header = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(header)) {
            currentContext.addZuulRequestHeader("Authorization", header);
        }
        return null;
    }
}
