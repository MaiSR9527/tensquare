package com.msr.tensquare.spit.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.msr.tensquare.spit.pojo.Spit;
import com.msr.tensquare.spit.service.SpitService;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/19 15:37
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    private final SpitService spitService;

    private final RedisTemplate<String, Object> redisTemplate;

    public SpitController(SpitService spitService, RedisTemplate<String, Object> redisTemplate) {
        this.spitService = spitService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询全部数据
     *
     * @return 返回页面
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 返回页面
     */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable("id") String id) {
        spitService.readUp(id);
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /**
     * 增加
     *
     * @param spit 对象
     * @return 返回页面
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        //TODO 添加token验证
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 更新
     *
     * @param spit 对象
     * @param id   id
     * @return 返回页面
     */
    @PutMapping("{id}")
    public Result update(@RequestBody Spit spit, @PathVariable("id") String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据id删除
     *
     * @return 返回页面
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable("id") String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     * 根据上级ID查询吐槽列表
     *
     * @param parentId 父级id
     * @param page     当前页
     * @param size     每页条数
     * @return 返回结果
     */
    @GetMapping(value = "/comment/{parentId}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }


    /**
     * 点赞
     *
     * @param id id
     * @return 返回结果
     */
    @PutMapping("/thumbup/{id}")
    public Result updateThumbup(@PathVariable("id") String id) {
        //判断用户是否点过赞
        //从token中获取
        String userId = "2013";
        if (!StringUtils.isEmpty(redisTemplate.opsForValue().get("thumbup_" + userId + "_" + id))) {
            return new Result(false, StatusCode.ERROR, "你已经点过赞了");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + id, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
