package com.tensquare.user.crawler.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/24 22:23
 */
@Data
@Entity
@Table(name = "tb_admin")
public class Admin implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 登录名称
     */
    private String loginname;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private String state;

}
