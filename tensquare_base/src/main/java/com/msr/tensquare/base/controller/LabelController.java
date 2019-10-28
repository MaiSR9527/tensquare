package com.msr.tensquare.base.controller;

import com.msr.tensquare.base.pojo.Label;
import com.msr.tensquare.base.service.LabelService;
import com.msr.tensquare.entity.PageResult;
import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 控制类
 * @Author: maishuren
 * @Date: 2019/10/17 10:30
 */

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping()
    public Result findAll() {

        return new Result(true, StatusCode.OK, "查询成功！", labelService.findAll());
    }

    @GetMapping(value = "{labelId}")
    public Result findById(@PathVariable("labelId") String labelId) {

        return new Result(true, StatusCode.OK, "查询成功！", labelService.findById(labelId));
    }

    @PostMapping
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功！");
    }

    @PutMapping(value = "{labelId}")
    public Result update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功！");
    }

    @DeleteMapping(value = "{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId) {

        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功！");
    }

    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功！", list);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result find(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody Label label) {
        Page<Label> labelPage = labelService.pageQuery(label,page,size);
        return new Result(true, StatusCode.OK, "查询成功！", new PageResult<>(labelPage.getTotalElements(), labelPage.getContent()));
    }


}
