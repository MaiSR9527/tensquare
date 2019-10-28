package com.msr.tensquare.user.interceptor;

import com.msr.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 08:29
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authHeader)) {
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (StringUtils.isNotEmpty(roles) && StringUtils.equalsIgnoreCase(roles, "admin")) {
                        request.setAttribute("admin_claims", claims);
                    }
                    if (StringUtils.isNotEmpty(roles) && StringUtils.equalsIgnoreCase(roles, "user")) {
                        request.setAttribute("user_claims", claims);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }


}
