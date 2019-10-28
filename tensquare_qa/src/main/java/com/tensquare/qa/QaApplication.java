package com.tensquare.qa;

import com.msr.tensquare.util.IdWorker;
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
 * @Date: 2019/10/17 23:02
 */
@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class QaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

}
