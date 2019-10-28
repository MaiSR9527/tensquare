package com.tensquare.article.controller;

import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 23:36
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 新增评论
     *
     * @param comment 评论对象
     * @return 返回操作页面结果
     */
    @PostMapping
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "添加成功");
    }

}
