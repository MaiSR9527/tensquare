package com.msr.tensquare.friend.controller;

import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.msr.tensquare.friend.client.UserClient;
import com.msr.tensquare.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 13:52
 */
@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    private final HttpServletRequest request;
    private final FriendService friendService;
    private final UserClient userClient;

    public FriendController(HttpServletRequest request, FriendService friendService, UserClient userClient) {
        this.request = request;
        this.friendService = friendService;
        this.userClient = userClient;
    }

    /**
     * @param friendId 好友id
     * @param type     0不喜欢 1喜欢
     * @return 返回
     */
    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendId,
                            @PathVariable("type") String type) {
        // TODO 测试添加好友接口
        //判断登录
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (ObjectUtils.isEmpty(claims)) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //已登录，拿到用户id
        String userId = claims.getId();
        //判断是添加好友还是非好友
        if (StringUtils.isNotEmpty(type)) {
            if (StringUtils.equals("1", type)) {
                //添加好友
                int flag = friendService.addFriend(userId, friendId);
                if (flag == 1) {
                    userClient.updateFansAndFollow(userId, friendId, 1);
                    return new Result(true, StatusCode.OK, "添加好友成功");
                } else if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
            } else if (StringUtils.equals("0", type)) {
                //添加非好友
                int flag = friendService.addNoFriend(userId, friendId);
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                } else if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    /**
     * 删除好友
     *
     * @param friendId 好友id
     * @return 返回
     */
    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable("friendid") String friendId) {
        //TODO 测试删除好友接口
        //判断登录
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (ObjectUtils.isEmpty(claims)) {
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //已登录，拿到用户id
        String userId = claims.getId();
        friendService.deleteFriend(userId, friendId);
        userClient.updateFansAndFollow(userId, friendId, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
