package com.msr.tensquare.crawler.pipeline;

import com.msr.tensquare.crawler.dao.ArticleDao;
import com.msr.tensquare.crawler.pojo.Article;
import com.msr.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @description: 爬取的文章入库类
 * @author: MaiShuRen
 * @date: 2020/1/30 11:10
 * @version: v1.0
 */
@Component
public class ArticleDbPipeline implements Pipeline {


    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;
    private String channelId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //取出标题
        String title = resultItems.get("title");
        //取出内容
        String content = resultItems.get("content");
        //保存到数据库
        Article article = new Article();
        article.setId(idWorker.nextId() + "");
        article.setTitle(title);
        article.setChannelid(channelId);
        article.setContent(content);
        articleDao.save(article);

    }
}
