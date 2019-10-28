package com.msr.tensquare.search.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.msr.tensquare.search.pojo.Article;
import com.msr.tensquare.search.service.ArticleSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/20 13:55
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    private final ArticleSearchService articleSearchService;

    public ArticleSearchController(ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }


    @PostMapping
    public Result save(@RequestBody Article article) {
        articleSearchService.save(article);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @GetMapping("/{key}/{page}/{size}")
    public Result findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageData = articleSearchService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK, "搜索成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }


}
