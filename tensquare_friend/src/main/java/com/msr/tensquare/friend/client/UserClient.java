package com.msr.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 15:49
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新好友的粉丝数和用户的关注数
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @param x        1 或 -1
     */
    @PutMapping("/user/{userid}/{friendid}/{x}")
    void updateFansAndFollow(@PathVariable("userid") String userId,
                             @PathVariable("friendid") String friendId,
                             @PathVariable int x);
}
