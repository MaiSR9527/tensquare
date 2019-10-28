package com.tensquare.article.pojo;

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
@Table(name = "tb_channel")
public class Channel implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 频道名称
     */
    private String name;
    /**
     * 状态
     */
    private String state;


}
