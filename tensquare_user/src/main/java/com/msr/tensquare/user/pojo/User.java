package com.msr.tensquare.user.pojo;

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
@Table(name = "tb_user")
public class User implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生年月日
     */
    private java.util.Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * E-Mail
     */
    private String email;
    /**
     * 注册日期
     */
    private java.util.Date regdate;
    /**
     * 修改日期
     */
    private java.util.Date updatedate;
    /**
     * 最后登陆日期
     */
    private java.util.Date lastdate;
    /**
     * 在线时长（分钟）
     */
    private Long online;
    /**
     * 兴趣
     */
    private String interest;
    /**
     * 个性
     */
    private String personality;
    /**
     * 粉丝数
     */
    private Integer fanscount;
    /**
     * 关注数
     */
    private Integer followcount;

}
