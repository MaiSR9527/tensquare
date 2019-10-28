package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 23:33
 */
public interface CommentDao extends MongoRepository<Comment, String> {
}
