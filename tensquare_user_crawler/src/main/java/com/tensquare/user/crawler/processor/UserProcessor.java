package com.tensquare.user.crawler.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 15:07
 * @version: v1.0
 */
@Component
@Slf4j
public class UserProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a‐z0‐9A-Z]+/article/details/[0‐9]+").all());
        String nickname = page.getHtml().xpath("//*[@id=\"uid\"]/text()").get();
        String image = page.getHtml().xpath("//*[@id=\"asideProfile\"]/div[1]/div[1]/a").css("img", "src").toString();
        if (StringUtils.isNotBlank(nickname) && StringUtils.isNotBlank(image)) {
            //如果有昵称和头像
            page.putField("nickname", nickname);
            page.putField("image", image);
        } else {
            //跳过
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetrySleepTime(3000).setSleepTime(1000);
    }
}
