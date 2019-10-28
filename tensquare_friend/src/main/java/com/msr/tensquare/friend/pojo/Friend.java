package com.msr.tensquare.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/26 14:41
 */
@Data
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike;
}
