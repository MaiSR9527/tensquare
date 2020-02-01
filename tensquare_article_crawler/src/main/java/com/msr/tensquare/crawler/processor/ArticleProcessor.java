package com.msr.tensquare.crawler.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @description: 文章爬取类
 * @author: MaiShuRen
 * @date: 2020/1/30 10:51
 * @version: v1.0
 */
@Component
@Slf4j
public class ArticleProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-z0-9A-Z]+/article/details/[0-9]+").all());
        //*[@id="mainBox"]/ //*[@id="content_views"]
        String title = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").get();
        String content = page.getHtml().xpath("//*[@id=\"content_views\"]").get();
        //获取页面需要的内容
        log.info("标题：{}", title);
        log.info("内容是：{}", content);

        //如果有标题和内容
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content)) {
            page.putField("title", title);
            page.putField("content", content);
        } else {
            //没有跳过
            page.setSkip(true);
        }

    }

    @Override
    public Site getSite() {
        return Site.me().setRetrySleepTime(3000).setSleepTime(10000);
    }
}
