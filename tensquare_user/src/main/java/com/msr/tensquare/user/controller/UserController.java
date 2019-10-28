package com.msr.tensquare.user.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.msr.tensquare.user.pojo.User;
import com.msr.tensquare.user.service.UserService;
import com.msr.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, RedisTemplate<String, Object> redisTemplate, JwtUtil jwtUtil) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 更新好友的粉丝数和用户的关注数
     */
    @PutMapping("/{userid}/{friendid}/{x}")
    public void updateFansAndFollow(@PathVariable("userid") String userId,
                                    @PathVariable("friendid") String friendId,
                                    @PathVariable int x) {
        userService.updateFansAndFollow(x, userId, friendId);
    }

    //TODO 测试用户登录

    /**
     * 用户登录
     *
     * @param user 用户信息
     * @return 返回
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        user = userService.login(user.getMobile(), user.getPassword());
        if (ObjectUtils.isEmpty(user)) {
            return new Result(false, StatusCode.ERROR, "登录失败");
        }
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("roles", user);
        return new Result(true, StatusCode.OK, "登录成功", map);
    }

    /**
     * 用户注册
     *
     * @param code 验证码
     * @param user 用户信息
     * @return 返回
     */
    @PostMapping("register/{code}")
    public Result register(@PathVariable("code") String code, @RequestBody User user) {
        //得到缓存中的验证码
        String checkCodeRedis = (String) redisTemplate.opsForValue().get("checkCode_" + user.getMobile());
        if (StringUtils.isNotBlank(checkCodeRedis)) {
            if (StringUtils.equals(checkCodeRedis, code)) {
                userService.add(user);
                return new Result(true, StatusCode.OK, "注册成功");
            } else {
                return new Result(false, StatusCode.ERROR, "请输入正确验证码");
            }
        }
        return new Result(false, StatusCode.ERROR, "请先获取验证码");
    }

    /**
     * 发送短信
     *
     * @param mobile 手机号码
     * @return 返回
     */
    @PostMapping("sendsms/{mobile}")
    public Result sendSma(@PathVariable("mobile") String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 查询全部数据
     *
     * @return 返回
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 返回
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 搜索条件田家庵
     * @return 返回
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user 用户信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user 用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id 用户id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("admin_claims");
        String role = (String) claims.get("roles");
        if (!StringUtils.equalsIgnoreCase(role, "admin")) {
            throw new RuntimeException("权限不足");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
