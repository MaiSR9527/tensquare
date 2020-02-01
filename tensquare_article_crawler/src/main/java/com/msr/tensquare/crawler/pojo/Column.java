package com.msr.tensquare.crawler.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/18 09:31
 */
@Data
@Entity
@Table(name = "tb_column")
public class Column implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;


    /**
     * 专栏名称
     */
    private String name;
    /**
     * 专栏简介
     */
    private String summary;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 申请日期
     */
    private java.util.Date createtime;
    /**
     * 审核日期
     */
    private java.util.Date checktime;
    /**
     * 状态
     */
    private String state;


}
