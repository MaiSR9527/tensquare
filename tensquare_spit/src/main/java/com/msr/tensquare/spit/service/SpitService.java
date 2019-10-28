package com.msr.tensquare.spit.service;

import com.msr.tensquare.spit.dao.SpitDao;
import com.msr.tensquare.spit.pojo.Spit;
import com.msr.tensquare.util.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 15:37
 */
@Service
public class SpitService {

    private final SpitDao spitDao;

    private final IdWorker idWorker;

    private final MongoTemplate mongoTemplate;

    public SpitService(IdWorker idWorker, SpitDao spitDao, MongoTemplate mongoTemplate) {
        this.idWorker = idWorker;
        this.spitDao = spitDao;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 查询所有记录
     *
     * @return 返回结果
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 返回结果
     */
    public Spit findById(String id) {
        return spitDao.findById(id).orElse(null);
    }

    /**
     * 添加
     *
     * @param spit 对象
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        if (StringUtils.isNotBlank(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 更新
     *
     * @param spit 对象
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 根据id删除
     *
     * @param id id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }


    /**
     * 根据上级ID查询吐槽列表
     *
     * @param parentid 父级id
     * @param page     当前页
     * @param size     每页条数
     * @return 返回结果
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageRequest);
    }

    /**
     * 点赞
     *
     * @param id id
     */
    public void updateThumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbuo", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    //TODO:实现增加浏览量和分享数

}
