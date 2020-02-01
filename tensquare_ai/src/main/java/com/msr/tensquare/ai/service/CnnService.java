package com.msr.tensquare.ai.service;

import com.msr.tensquare.ai.util.CnnUtil;
import com.msr.tensquare.util.IKUtil;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/2/1 11:06
 * @version: v1.0
 */
@Service
@Slf4j
public class CnnService {

    /**
     * 词向量模型
     */
    @Value("${ai.vecModel}")
    private String vecModel;
    /**
     * 训练模型结果保存路径
     */
    @Value("${ai.cnnModel}")
    private String cnnModel;
    /**
     * 爬虫分词后的数据路径
     */
    @Value("${ai.dataPath}")
    private String dataPath;

    /**
     * 构建卷积模型
     *
     * @return      
     */
    public boolean build() {
        try {
            //创建计算图对象
            ComputationGraph net = CnnUtil.createComputationGraph(100);
            //加载词向量 训练数据集
            WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(vecModel));
            String[] childPaths = {"ai", "web"};
            DataSetIterator trainIter = CnnUtil.getDataSetIterator(dataPath, childPaths, wordVectors, 32, 256, new Random(12345));
            //模型训练
            net.fit(trainIter);
            //保存模型之前先删除
            new File(cnnModel).delete();
            //保存模型
            ModelSerializer.writeModel(net, cnnModel, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 返回map集合 分类与百分比
     *
     * @param content
     * @return
     */
    public Map textClassify(String content) {
        log.info("content:" + content);
        //分词
        try {
            content = IKUtil.split(content, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] childPaths = {"ai","web"};
        Map map = null;
        try {
            map = CnnUtil.predictions(vecModel, cnnModel, dataPath, childPaths, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
