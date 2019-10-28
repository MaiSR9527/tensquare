package com.msr.tensquare.user.service;

import com.msr.tensquare.user.dao.UserDao;
import com.msr.tensquare.user.pojo.User;
import com.msr.tensquare.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    private final UserDao userDao;

    private final IdWorker idWorker;

    private final RedisTemplate<String, Object> redisTemplate;

    private final BCryptPasswordEncoder encoder;

    private final RabbitTemplate rabbitTemplate;

    public UserService(UserDao userDao, IdWorker idWorker,
                       RedisTemplate<String, Object> redisTemplate,
                       RabbitTemplate rabbitTemplate,
                       BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.encoder = encoder;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFollowcount(0);
        user.setFanscount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id 用户id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + searchMap.get("personality") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

    /**
     * 发送短信
     *
     * @param mobile 手机号码
     */
    public void sendSms(String mobile) {
        //生成6位随机数
        String checkCode = RandomStringUtils.randomNumeric(6);
        //向缓存存储，设置过期时间
        redisTemplate.opsForValue().set("checkCode_" + mobile, checkCode, 5, TimeUnit.MINUTES);
        //向用户发一份
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkCode", checkCode);
        //rabbitTemplate.convertAndSend("sms", map);
        log.info("发送验证码：{}----手机号码为：{}", checkCode, mobile);
        //向控制台发一份
    }

    /**
     * 用户登录
     *
     * @param mobile   手机号码
     * @param password 密码
     * @return 返回
     */
    public User login(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if (ObjectUtils.allNotNull(user) && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void updateFansAndFollow(int x, String userId, String friendId) {
        userDao.updateFanCount(x, friendId);
        userDao.updateFollowCount(x, userId);
    }
}
