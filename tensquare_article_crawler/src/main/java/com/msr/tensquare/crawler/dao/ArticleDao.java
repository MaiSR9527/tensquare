package com.msr.tensquare.crawler.dao;

import com.msr.tensquare.crawler.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 09:31
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {


    /**
     * 文章审核
     *
     * @param id id
     */
    @Modifying
    @Query(value = "update tb_article set state = 1 where id = ?", nativeQuery = true)
    void update(String id);

    /**
     * 根据
     *
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set thumbup=thumbup+1 where id = ?", nativeQuery = true)
    void addThumbup(String id);
}
