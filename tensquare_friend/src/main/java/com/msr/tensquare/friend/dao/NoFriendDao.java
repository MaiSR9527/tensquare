package com.msr.tensquare.friend.dao;

import com.msr.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 15:17
 */

public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    /**
     * 根据用户id和好友id查找记录
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return 返回
     */
    NoFriend findByUseridAndFriendid(String userId, String friendId);
}
