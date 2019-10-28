package com.tensquare.gathering.dao;

import com.tensquare.gathering.pojo.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 15:17
 */
public interface GatheringDao extends JpaRepository<Gathering, String>, JpaSpecificationExecutor<Gathering> {

}
