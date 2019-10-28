package com.tensquare.qa.service;

import com.msr.tensquare.util.IdWorker;
import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 23:02
 */
@Service
public class ProblemService {

    private final ProblemDao problemDao;

    private final IdWorker idWorker;

    public ProblemService(ProblemDao problemDao, IdWorker idWorker) {
        this.problemDao = problemDao;
        this.idWorker = idWorker;
    }

    public Page<Problem> newReplyList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.newReplyList(labelId, pageable);
    }

    public Page<Problem> hotReplyList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.hotReplyList(labelId, pageable);
    }

    public Page<Problem> waitReplyList(String labelId, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.waitReplyList(labelId, pageable);
    }

    /**
     * 查询全部列表
     *
     * @return 返回结果
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap map对象
     * @param page     当前页
     * @param size     每页条数
     * @return 返回结果
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap map对象
     * @return 返回结果
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id id
     * @return 返回结果
     */
    public Problem findById(String id) {
        return problemDao.findById(id).orElse(null);
    }

    /**
     * 增加
     *
     * @param problem 对象
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem 对象
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 搜索条件
     * @return 返回结果
     */
    private Specification<Problem> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 是否解决
            if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                predicateList.add(cb.like(root.get("solve").as(String.class), "%" + searchMap.get("solve") + "%"));
            }
            // 回复人昵称
            if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + searchMap.get("replyname") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
