package com.msr.tensquare.base.service;

import com.msr.tensquare.base.dao.LabelDao;
import com.msr.tensquare.base.pojo.Label;
import com.msr.tensquare.util.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 10:52
 */
@Service
@Transactional(rollbackOn = RuntimeException.class)
public class LabelService {

    private final LabelDao labelDao;
    private final IdWorker idWorker;

    public LabelService(LabelDao labelDao, IdWorker idWorker) {
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    /**
     * 查询所有
     *
     * @return 返回结果
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据标签id查询
     *
     * @param labelId 标签id
     * @return 返回结果
     */
    public Label findById(String labelId) {
        Optional<Label> label = labelDao.findById(labelId);
        return label.orElse(null);
    }

    /**
     * 保存
     *
     * @param label 标签
     */
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 更新
     *
     * @param label 标签
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 根据标签id删除
     *
     * @param labelId 标签
     */
    public void deleteById(String labelId) {
        labelDao.deleteById(labelId);
    }

    /**
     * 搜索查询
     *
     * @param label 标签
     * @return 返回结果
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 跟对象，也就是要把条件封装到哪一个对象，
             * @param criteriaQuery 封装的都是关键自查询，
             * @param criteriaBuilder 封装条件对象
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //存放所有查询条件
                List<Predicate> predicateList = new ArrayList<>();

                if (StringUtils.isNotBlank(label.getLabelname())) {
                    Predicate labelName = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    predicateList.add(labelName);
                }

                if (StringUtils.isNotBlank(label.getState())) {
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    predicateList.add(state);

                }

                // 最终返回值
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicates = predicateList.toArray(predicates);

                return criteriaBuilder.and(predicates);
            }
        });
    }

    /**
     * 分页查询
     *
     * @param label label对象
     * @param page  当前页
     * @param size  每页数量
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll((Specification<Label>) (root, criteriaQuery, criteriaBuilder) -> {
            //存放所有查询条件
            List<Predicate> predicateList = new ArrayList<>();

            if (StringUtils.isNotBlank(label.getLabelname())) {
                Predicate labelName = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                predicateList.add(labelName);
            }

            if (StringUtils.isNotBlank(label.getState())) {
                Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                predicateList.add(state);
            }

            // 最终返回值
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicates = predicateList.toArray(predicates);

            return criteriaBuilder.and(predicates);
        }, pageable);
    }

}
