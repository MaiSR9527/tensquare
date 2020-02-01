package com.msr.tensquare.ai.task;

import com.msr.tensquare.ai.service.CnnService;
import com.msr.tensquare.ai.service.Word2VecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 22:53
 * @version: v1.0
 */
@Component
@Slf4j
public class TrainTask {

    @Autowired
    private Word2VecService word2VecService;

    @Autowired
    private CnnService cnnService;

    @Scheduled(cron = "0 23 12 * * ?")
    public void trainModel() {
        log.info("开始合并语料库");
        word2VecService.mergeWord();
        log.info("合并语料库完毕");

        log.info("开始构建词向量模型");
        word2VecService.build();
        log.info("构建词向量模型结束");

        /*log.info("开始构建神经网络卷积模型");
        cnnService.build();
        log.info("构建神经网络卷积模型结束");*/
    }
}
