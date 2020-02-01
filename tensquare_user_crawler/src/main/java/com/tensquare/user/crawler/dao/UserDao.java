package com.tensquare.user.crawler.dao;

import com.tensquare.user.crawler.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 根据电话码号查找用户
     *
     * @param mobile 电话号码
     * @return 返回
     */
    User findByMobile(String mobile);

    /**
     * 更新粉丝数
     *
     * @param x
     * @param friendId
     */
    @Query(value = "update tb_user set fanscount=fanscount+? where id = ?", nativeQuery = true)
    void updateFanCount(int x, String friendId);

    /**
     * 更新关注数
     *
     * @param x
     * @param userId
     */
    @Query(value = "update tb_user set followcount=fanscount+? where id = ?", nativeQuery = true)
    void updateFollowCount(int x, String userId);
}
