package com.tensquare.qa.config;

import com.tensquare.qa.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Description: 注册拦截器
 * @Author: maishuren
 * @Date: 2019/10/26 08:35
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    private final JwtInterceptor jwtInterceptor;

    public InterceptorConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");


    }
}
