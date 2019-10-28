package com.tensquare.recruit.config;

import com.msr.tensquare.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: common包下的配置类
 * @Author: maishuren
 * @Date: 2019/10/17 10:17
 */
@Configuration
public class CommonConfig {

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
