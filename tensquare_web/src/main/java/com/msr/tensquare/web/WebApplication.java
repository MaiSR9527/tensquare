package com.msr.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 22:13
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
