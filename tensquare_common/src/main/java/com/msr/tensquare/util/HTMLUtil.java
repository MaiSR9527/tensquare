package com.msr.tensquare.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: html标签处理工具类
 * @author: MaiShuRen
 * @date: 2020/1/30 23:03
 * @version: v1.0
 */

public class HTMLUtil {

    public static String delHtmlTag(String htmlStr) {
        //定义script标签的正则表达式
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style标签的正则表达式
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式
        String regExHtml = "<[^>]+>";
        Pattern patternScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher matcherScript = patternScript.matcher(htmlStr);
        //过滤script标签
        htmlStr = matcherScript.replaceAll("");

        Pattern patternStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher matcherStyle = patternStyle.matcher(htmlStr);
        //过滤style标签
        htmlStr = matcherStyle.replaceAll("");

        Pattern patternHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher matcherHtml = patternHtml.matcher(htmlStr);
        //过滤html标签
        htmlStr = matcherHtml.replaceAll("");
        //返回文本字符串
        return htmlStr.trim();
    }
}
