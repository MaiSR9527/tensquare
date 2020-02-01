package com.tensquare.user.crawler.task;

import com.tensquare.user.crawler.pipeline.UserPipeline;
import com.tensquare.user.crawler.processor.UserProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 18:13
 * @version: v1.0
 */
@Component
@Slf4j
public class UserTask {

    @Autowired
    private RedisScheduler redisScheduler;

    @Autowired
    private UserPipeline userPipeline;

    @Autowired
    private UserProcessor userProcessor;

    @Scheduled(cron = "0 56 12 * * ?")
    public void userTask() {
        log.info("开始爬取用户信息");
        Spider spider = Spider.create(userProcessor);
        spider.addUrl("https://blog.csdn.net");
        spider.addPipeline(userPipeline);
        spider.setScheduler(redisScheduler);
        spider.thread(2).start();
    }
}
