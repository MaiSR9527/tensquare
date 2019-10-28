package com.msr.tensquare.search.service;

import com.msr.tensquare.search.dao.ArticleSearchDao;
import com.msr.tensquare.search.pojo.Article;
import com.msr.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/20 13:51
 */
@Service
public class ArticleSearchService {

    private final IdWorker idWorker;

    private final ArticleSearchDao articleSearchDao;


    public ArticleSearchService(IdWorker idWorker, ArticleSearchDao articleSearchDao) {
        this.idWorker = idWorker;
        this.articleSearchDao = articleSearchDao;
    }

    /**
     * 保存
     *
     * @param article 对象
     */
    public void save(Article article) {
        article.setId(idWorker.nextId() + "");
        articleSearchDao.save(article);
    }

    /**
     * 关键字查询文章 分页
     *
     * @param key  关键字
     * @param page 当前页
     * @param size 每页条数
     * @return 返回结果
     */
    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return articleSearchDao.findByTitleOrContentLike(key, key, pageable);
    }
}
