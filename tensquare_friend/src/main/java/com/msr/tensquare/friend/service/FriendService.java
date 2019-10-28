package com.msr.tensquare.friend.service;

import com.msr.tensquare.friend.dao.FriendDao;
import com.msr.tensquare.friend.dao.NoFriendDao;
import com.msr.tensquare.friend.pojo.Friend;
import com.msr.tensquare.friend.pojo.NoFriend;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 14:11
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加好友
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return 返回
     */
    public int addFriend(String userId, String friendId) {
        //判断userId-friendId是否存在,防止重复添加,重复返回0
        Friend abFriend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (ObjectUtils.allNotNull(abFriend)) {
            return 0;
        }
        //添加，默认islike为0
        Friend addFriend = new Friend();
        addFriend.setUserid(userId);
        addFriend.setFriendid(friendId);
        addFriend.setIslike("0");
        friendDao.save(addFriend);
        //判断friendId-userId方向是否有数据,如果有，则双方的islike都置为1
        Friend likeEach = friendDao.findByUseridAndFriendid(friendId, userId);
        if (ObjectUtils.allNotNull(likeEach)) {
            friendDao.updateLike("1", userId, friendId);
            friendDao.updateLike("1", friendId, userId);
        }
        return 1;
    }

    public int addNoFriend(String userId, String friendId) {
        NoFriend dbNoFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (dbNoFriend != null) {
            return 0;
        }
        NoFriend addNoFriend = new NoFriend();
        addNoFriend.setUserid(userId);
        addNoFriend.setFriendid(friendId);
        noFriendDao.save(addNoFriend);
        return 1;
    }


    public void deleteFriend(String userId, String friendId) {
        //删除好友列表userId-friendId记录
        friendDao.deleteFriend(userId, friendId);
        //更新friendId到userId的islike为0
        friendDao.updateLike("0", friendId, userId);
        //非好友表添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
    }
}
