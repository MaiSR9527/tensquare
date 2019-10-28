package com.msr.tensquare.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 11:01
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka6868Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka6868Application.class, args);
    }
}
