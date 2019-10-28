package com.msr.tensquare.gateway.filter;

import com.msr.tensquare.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 22:20
 */
@Component
public class ManageFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求前或请求后执行
     *
     * @return 返回类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 当前过滤器的操作
     *
     * @return return 任何Object的值表示继续执行 setSendZuulResponse(false)表示不继续执行
     */
    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        //登录放行
        if (request.getRequestURI().indexOf("login") > 0) {
            return null;
        }

        String header = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(header)) {
            if (header.startsWith("Beaer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (StringUtils.equalsIgnoreCase(roles, "amdin")) {
                        currentContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);
                }

            }
        }
        //终止运行
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}
