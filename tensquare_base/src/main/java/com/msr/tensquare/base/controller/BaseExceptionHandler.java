package com.msr.tensquare.base.controller;

import com.msr.tensquare.entity.Result;
import com.msr.tensquare.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 20:11
 */
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        log.error("发生异常：{}", e.getMessage());
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
