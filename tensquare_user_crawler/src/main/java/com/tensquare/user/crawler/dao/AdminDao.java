package com.tensquare.user.crawler.dao;

import com.tensquare.user.crawler.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    /**
     * 根据用户名查询
     *
     * @param loginName 用户名
     * @return 返回
     */
    Admin findByLoginname(String loginName);
}
