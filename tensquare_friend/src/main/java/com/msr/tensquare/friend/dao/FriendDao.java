package com.msr.tensquare.friend.dao;

import com.msr.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 14:39
 */

public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 根据用户id和好友id查找记录
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return 返回
     */
    Friend findByUseridAndFriendid(String userId, String friendId);

    /**
     * @param like     like字段
     * @param userId   用户id
     * @param friendId 好友id
     */
    @Query(value = "update tb_friend set islike = ? where userid = ? and friendid = ?", nativeQuery = true)
    void updateLike(String like, String userId, String friendId);

    @Query(value = "delete from tb_friend where userid=?and friendid=?", nativeQuery = true)
    void deleteFriend(String userId, String friendId);
}
