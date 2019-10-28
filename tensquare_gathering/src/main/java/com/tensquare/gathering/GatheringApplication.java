package com.tensquare.gathering;

import com.msr.tensquare.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 15:17
 */
@SpringBootApplication
@EnableCaching
public class GatheringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatheringApplication.class, args);
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

}
