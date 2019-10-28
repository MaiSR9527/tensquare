package com.msr.tensquare.gateway;

import com.msr.tensquare.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 21:51
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ManageGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageGatewayApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
