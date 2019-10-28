package com.msr.tensquare.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 09:29
 */
@Getter
@Setter
@AllArgsConstructor
public class PageResult <T> {

    private long total;

    private List<T> rows;


}
