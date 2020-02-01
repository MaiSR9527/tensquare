package com.msr.tensquare.ai.util;

import org.deeplearning4j.iterator.CnnSentenceDataSetIterator;
import org.deeplearning4j.iterator.LabeledSentenceProvider;
import org.deeplearning4j.iterator.provider.FileLabeledSentenceProvider;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.ConvolutionMode;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.GlobalPoolingLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/31 18:04
 * @version: v1.0
 */

public class CnnUtil {

    /**
     * 得到卷积神经网络
     *
     * @param cnnLayerFeatureMaps
     * @return 返回
     */
    public static ComputationGraph createComputationGraph(int cnnLayerFeatureMaps) {

        //向量大小
        int vectorSize = 300;
        // cnnLayerFeatureMaps 每种大小卷积层的卷积核的数量=词向量维度
        ComputationGraphConfiguration netConfiguration = new NeuralNetConfiguration.Builder()
                //设置卷积模式
                .convolutionMode(ConvolutionMode.Same)
                .graphBuilder()
                .addInputs("input")
                //卷积层
                .addLayer("cnn1", new ConvolutionLayer.Builder()
                        //卷积区域尺寸
                        .kernelSize(3, vectorSize)
                        //卷积平移步幅
                        .stride(1, vectorSize)
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                .addLayer("cnn2", new ConvolutionLayer.Builder()
                        .kernelSize(4, vectorSize)
                        .stride(1, vectorSize)
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                .addLayer("cnn3", new ConvolutionLayer.Builder()
                        .kernelSize(5, vectorSize)
                        .stride(1, vectorSize)
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                //连接
                .addVertex("merge", new MergeVertex(), "cnn1", "cnn2", "cnn3")
                //池化层
                .addLayer("globalPool", new GlobalPoolingLayer.Builder().build(), "merge")
                //输出层
                .addLayer("out", new OutputLayer.Builder()
                        .nIn(3 * cnnLayerFeatureMaps)
                        .nOut(3).build(), "globalPool")
                .setOutputs("out")
                .build();

        ComputationGraph net = new ComputationGraph(netConfiguration);
        net.init();
        return net;
    }

    /**
     * 返回训练数据集
     *
     * @param path         数据集所在目录
     * @param childPaths   子目录
     * @param wordVectors  词向量模型
     * @param minBatchSize 最小批处理数量
     * @param maxBatchSize 最大批处理对象
     * @param random       穗子种子
     * @return 返回
     */
    public static DataSetIterator getDataSetIterator(String path, String[] childPaths, WordVectors wordVectors, int minBatchSize, int maxBatchSize, Random random) {

        HashMap<String, List<File>> reviewFilesMap = new HashMap<>(16);

        for (String childPath : childPaths) {
            reviewFilesMap.put(childPath, Arrays.asList(new File(path + "/" + childPath).listFiles()));
        }

        //标记跟踪
        LabeledSentenceProvider sentenceProvider = new FileLabeledSentenceProvider(reviewFilesMap, random);
        return new CnnSentenceDataSetIterator.Builder()
                .sentenceProvider(sentenceProvider)
                .wordVectors(wordVectors)
                .minibatchSize(minBatchSize)
                .maxSentenceLength(maxBatchSize)
                .useNormalizedWordVectors(false)
                .build();
    }

    public static Map<String, Double> predictions(String vecModel, String
            cnnModel, String dataPath, String[] childPaths, String content) throws IOException {
        Map<String, Double> map = new HashMap<>(16);
        //模型应用
        //通过cnn模型获取计算图对象
        ComputationGraph model = ModelSerializer.restoreComputationGraph(cnnModel);
        //加载词向量模型对象
        WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(vecModel));
        //加载数据集
        DataSetIterator dataSet = CnnUtil.getDataSetIterator(dataPath, childPaths, wordVectors, 32, 256, new Random(12345));
        //通过句子获取概率矩阵对象
        INDArray featuresFirstNegative = ((CnnSentenceDataSetIterator) dataSet).loadSingleSentence(content);
        INDArray predictionsFirstNegative = model.outputSingle(featuresFirstNegative);
        List<String> labels = dataSet.getLabels();
        for (int i = 0; i < labels.size(); i++) {
            map.put(labels.get(i) + "",
                    predictionsFirstNegative.getDouble(i));
        }
        return map;
    }
}
