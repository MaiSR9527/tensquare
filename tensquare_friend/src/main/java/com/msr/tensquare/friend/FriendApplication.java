package com.msr.tensquare.friend;

import com.msr.tensquare.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 13:44
 */
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
