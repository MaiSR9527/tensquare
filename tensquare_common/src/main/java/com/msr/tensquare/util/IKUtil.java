package com.msr.tensquare.util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * @description: 分词工具类
 * @author: MaiShuRen
 * @date: 2020/1/30 22:56
 * @version: v1.0
 */

public class IKUtil {
    public static String split(String content,String splitChar) throws IOException {
        StringReader stringReader = new StringReader(content);
        IKSegmenter ikSegmenter = new IKSegmenter(stringReader, true);
        StringBuilder builder = new StringBuilder("");
        Lexeme lex = null;
        while ((lex=ikSegmenter.next())!=null){
            builder.append(lex.getLexemeText()).append(splitChar);
        }
        return builder.toString();
    }
}
