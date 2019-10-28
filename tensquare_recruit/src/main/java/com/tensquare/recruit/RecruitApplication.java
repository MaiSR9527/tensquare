package com.tensquare.recruit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 21:19
 */
@EnableEurekaClient
@SpringBootApplication
public class RecruitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitApplication.class, args);
	}


	
}
