package com.tensquare.article.service;

import com.msr.tensquare.util.IdWorker;
import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 23:34
 */
@Service
public class CommentService {

    private final CommentDao commentDao;
    private final IdWorker idWorker;

    public CommentService(CommentDao commentDao, IdWorker idWorker) {
        this.commentDao = commentDao;
        this.idWorker = idWorker;
    }

    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }
}
