package com.msr.tensquare.spit.dao;

import com.msr.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 15:35
 */

public interface SpitDao extends MongoRepository<Spit, String> {

    /**
     * 根据上级ID查询吐槽列表（分页）  
     * @param parentid 父级id
     * @param pageable 分页对象
     * @return
     */
    Page<Spit> findByParentid(String parentid, Pageable pageable);



}
