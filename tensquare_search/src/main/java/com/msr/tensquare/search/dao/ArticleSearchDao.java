package com.msr.tensquare.search.dao;

import com.msr.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/20 13:50
 */

public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {

    /**
     * 根据关键字查询
     *
     * @param title    文章标题
     * @param content  文章内容
     * @param pageable 分页对象
     * @return 返回结果
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
