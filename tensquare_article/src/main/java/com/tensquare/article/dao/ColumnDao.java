package com.tensquare.article.dao;

import com.tensquare.article.pojo.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 09:31
 */
public interface ColumnDao extends JpaRepository<Column, String>, JpaSpecificationExecutor<Column> {

}
