package com.msr.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/20 13:32
 */
@Data
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

    @Id
    private String id;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    private String state;
}
