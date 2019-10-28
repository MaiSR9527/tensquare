package com.tensquare.gathering.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 15:17
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @Cacheable(value = "gathering", key = "#id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findById(id));
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
        Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param gathering
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Gathering gathering) {
        gatheringService.add(gathering);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering", key = "#gathering.id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "gathering", key = "#id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        gatheringService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
