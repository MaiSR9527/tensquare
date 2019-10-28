package com.tensquare.qa.controller;

import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import com.tensquare.qa.client.BaseClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    private final BaseClient baseClient;

    public ProblemController(ProblemService problemService, BaseClient baseClient) {
        this.problemService = problemService;
        this.baseClient = baseClient;
    }

    /**
     * 根据id查找
     * @param labelId 标签id
     * @return 返回
     */
    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable("labelId")String labelId){
        return baseClient.findById(labelId);
    }

    /**
     * 查询最新回复的问题
     *
     * @param labelId 标签id
     * @param page    当前页
     * @param size    每页条数
     * @return 返回结果
     */
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result newReplyList(@PathVariable("labelid") String labelId,
                               @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problems = problemService.newReplyList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(problems.getTotalElements(), problems.getContent()));

    }

    /**
     * 查询热门回复的问题
     *
     * @param labelId 标签id
     * @param page    当前页
     * @param size    每页条数
     * @return 返回结果
     */
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result hotReplyList(@PathVariable("labelid") String labelId,
                               @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problems = problemService.hotReplyList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(problems.getTotalElements(), problems.getContent()));

    }

    /**
     * 查询等待回复的问题
     *
     * @param labelId 标签id
     * @param page    当前页
     * @param size    每页条数
     * @return 返回结果
     */
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result waitReplyList(@PathVariable("labelid") String labelId,
                                @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Problem> problems = problemService.waitReplyList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(problems.getTotalElements(), problems.getContent()));

    }

    /**
     * 查询全部数据
     *
     * @return 返回结果
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 返回结果
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 查询条件
     * @return 返回结果
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem 对象
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (ObjectUtils.isEmpty(claims)) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem 对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
