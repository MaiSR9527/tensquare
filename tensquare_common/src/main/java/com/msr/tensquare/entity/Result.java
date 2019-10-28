package com.msr.tensquare.entity;

import lombok.*;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/17 09:22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean flag;

    private Integer code;

    private String massage;

    private Object data;

    public Result(boolean flag, Integer code, String massage) {
        this.flag = flag;
        this.code = code;
        this.massage = massage;
    }


}
