package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description: 数据访问接口
 * @Author: maishuren
 * @Date: 2019/10/17 21:19
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
