package com.tensquare.qa.client;

import com.msr.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 12:58
 */
@FeignClient("tensquare-base")
public interface BaseClient {
    /**
     * 根据id查找
     * @param labelId 标签id
     * @return 返回
     */
    @GetMapping("/label/{labelId}")
    Result findById(@PathVariable("labelId")String labelId);

}
