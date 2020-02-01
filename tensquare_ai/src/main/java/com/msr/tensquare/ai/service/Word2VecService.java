package com.msr.tensquare.ai.service;

import com.msr.tensquare.util.FileUtil;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 22:32
 * @version: v1.0
 */
@Service
public class Word2VecService {

    /**
     * 模型分词路径
     */
    @Value("${ai.wordLib}")
    private String wordLib;

    /**
     * 模型训练保存的路径
     */
    @Value("${ai.vecModel}")
    private String vecModel;

    /**
     * 爬虫分词后的数据路径
     */
    @Value("${ai.dataPath}")
    private String dataPath;

    /**
     * 合并文件内容，用于训练
     */
    public void mergeWord() {
        List<String> fileNames = FileUtil.getFiles(dataPath);
        try {
            FileUtil.merge(wordLib, fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void build() {
        try {
            SentenceIterator iterator = new LineSentenceIterator(new File(wordLib));
            Word2Vec vec = new Word2Vec.Builder()
                    //分词语料库中词语出现的最小次数
                    .minWordFrequency(5)
                    //设置迭代次数
                    .iterations(1)
                    //词向量维度
                    .layerSize(100)
                    //随机种子
                    .seed(42)
                    //当前词于与预测次在一个句子中的最大距离
                    .windowSize(5)
                    .iterate(iterator)
                    .build();
            vec.buildVocab();
            vec.fit();
            //保存模型
            //1.先删除
            new File(vecModel).delete();
            //2.保存
            WordVectorSerializer.writeWordVectors(vec,vecModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
