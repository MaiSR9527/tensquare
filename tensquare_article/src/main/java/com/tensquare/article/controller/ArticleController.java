package com.tensquare.article.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 09:31
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private static Lock reenLock = new ReentrantLock();
    private final ArticleService articleService;
    private final RedisTemplate<String, Object> redisTemplate;

    public ArticleController(ArticleService articleService, RedisTemplate<String, Object> redisTemplate) {
        this.articleService = articleService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 审核文章
     *
     * @param articleId 文章id
     * @return 返回
     */
    @PutMapping(value = "/examine/{articleId}")
    public Result examine(@PathVariable("articleId") String articleId) {

        articleService.updateState(articleId);
        return new Result(true, StatusCode.OK, "审核成功");
    }

    /**
     * 点赞
     * @param articleId 文章id
     * @return 返回
     */
    @PutMapping(value = "/thumbup/{articleId}")
    public Result thumbup(@PathVariable("articleId") String articleId) {
        articleService.addThumbup(articleId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        //先查缓存
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        if (ObjectUtils.isEmpty(article)) {
            //防止缓存击穿
            if (reenLock.tryLock()) {
                try {
                    article = articleService.findById(id);
                    redisTemplate.opsForValue().set("article_" + id, article, 60 * 10, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reenLock.unlock();
                }
            } else {
                article = (Article) redisTemplate.opsForValue().get("article_" + id);
                if (ObjectUtils.isEmpty(article)) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return findById(id);
                }
            }
        }
        return new Result(true, StatusCode.OK, "查询成功", article);
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article 文章
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        //TODO token验证
        articleService.add(article);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        redisTemplate.delete("article_" + article.getId());
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        redisTemplate.delete("article_" + id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
