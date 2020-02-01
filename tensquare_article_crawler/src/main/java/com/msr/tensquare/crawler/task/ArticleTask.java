package com.msr.tensquare.crawler.task;

import com.msr.tensquare.crawler.pipeline.ArticleDbPipeline;
import com.msr.tensquare.crawler.pipeline.ArticleTxtPipeline;
import com.msr.tensquare.crawler.processor.ArticleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @description: 爬虫任务类
 * @author: MaiShuRen
 * @date: 2020/1/30 11:22
 * @version: v1.0
 */
@Component
@Slf4j
public class ArticleTask {

    @Autowired
    private ArticleProcessor articleProcessor;

    @Autowired
    private ArticleDbPipeline dbPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    @Autowired
    private ArticleTxtPipeline articleTxtPipeline;

    /**
     * AI频道文章爬取,并保存到数据库
     */
//    @Scheduled(cron = "0 36 13 * * ?")
    public void aiDbTask() {
        log.info("开始爬取CSDN AI频道的文章");
        Spider spider = new Spider(articleProcessor);
        spider.addUrl("https://ai.csdn.net");
        dbPipeline.setChannelId("ai");
        spider.addPipeline(dbPipeline);
        spider.setScheduler(redisScheduler);
        spider.thread(1).start();
    }

    @Scheduled(cron = "0 50 15 * * ?")
    public void aiTxtTask() {
        log.info("开始爬取CSDN AI频道的文章");
        Spider spider = new Spider(articleProcessor);
        spider.addUrl("https://ai.csdn.net");
        articleTxtPipeline.setChannelId("ai");
        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.thread(1).start();
    }

    /**
     * 爬取web专题的文章
     */
//    @Scheduled(cron = "0 26 15 * * ?")
    public void webTask() {
        System.out.println("爬取WEB文章");
        Spider spider = Spider.create(new ArticleProcessor());
        spider.addUrl("https://blog.csdn.net/nav/web");
        articleTxtPipeline.setChannelId("web");
        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
        spider.stop();
    }

    /**
     * csdn的java频道文章爬取
     */
    /*@Scheduled(cron = "0 48 11 * * ?")
    public void javaTask() {
        log.info("开始爬取CSDN java频道的文章");
        Spider spider = new Spider(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/java");
        dbPipeline.setChannelId("java");
        spider.addPipeline(dbPipeline);
        spider.setScheduler(redisScheduler);
    }*/
}
