package com.msr.tensquare.user.service;

import com.msr.tensquare.user.dao.AdminDao;
import com.msr.tensquare.user.pojo.Admin;
import com.msr.tensquare.util.IdWorker;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
@Service
public class AdminService {

    private final AdminDao adminDao;

    private final IdWorker idWorker;

    private final BCryptPasswordEncoder encoder;

    public AdminService(AdminDao adminDao, IdWorker idWorker, BCryptPasswordEncoder encoder) {
        this.adminDao = adminDao;
        this.idWorker = idWorker;
        this.encoder = encoder;
    }

    /**
     * 查询全部列表
     *
     * @return 返回
     */
    public List<Admin> findAll() {
        return adminDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap 查询条件
     * @param page     当前页
     * @param size     每页数量
     * @return 返回
     */
    public Page<Admin> findSearch(Map whereMap, int page, int size) {
        Specification<Admin> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Admin> findSearch(Map whereMap) {
        Specification<Admin> specification = createSpecification(whereMap);
        return adminDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Admin findById(String id) {
        return adminDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        //密码加密
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void update(Admin admin) {
        adminDao.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        adminDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Admin> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 登陆名称
            if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + searchMap.get("loginname") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + searchMap.get("password") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));

        };

    }

    /**
     * 登录
     *
     * @param admin 管理员信息
     * @return 返回
     */
    public Admin login(Admin admin) {
        //现根据用户名查询
        Admin dbAdmin = adminDao.findByLoginname(admin.getLoginname());

        if (ObjectUtils.allNotNull(dbAdmin) && encoder.matches(admin.getPassword(), dbAdmin.getPassword())) {
            //登录成功
            return dbAdmin;
        }
        //登录失败
        return null;
    }
}
