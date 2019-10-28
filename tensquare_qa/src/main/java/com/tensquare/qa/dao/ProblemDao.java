package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 23:02
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * @param labelId  标签id
     * @param pageable 分页对象
     * @return 返回结果
     */
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? order by replytime desc ", nativeQuery = true)
    Page<Problem> newReplyList(String labelId, Pageable pageable);

    /**
     * @param labelId  标签id
     * @param pageable 分页对象
     * @return 返回结果
     */
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? order by reply desc ", nativeQuery = true)
    Page<Problem> hotReplyList(String labelId, Pageable pageable);

    /**
     * @param labelId  标签id
     * @param pageable 分页对象
     * @return 返回结果
     */
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? and reply = 0 order by createtime desc ", nativeQuery = true)
    Page<Problem> waitReplyList(String labelId, Pageable pageable);
}
