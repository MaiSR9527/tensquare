package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description: 数据访问接口
 * @Author: maishuren
 * @Date: 2019/10/17 21:19
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    /**
     * 查询热门企业
     * @param isHot 热门标志
     * @return 返回查询结果
     */
    List<Enterprise> findByIshot(String isHot);
}
