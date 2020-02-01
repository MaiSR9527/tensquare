package com.msr.tensquare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 文件处理工具类
 * @author: MaiShuRen
 * @date: 2020/1/30 18:26
 * @version: v1.0
 */

public class FileUtil {

    /**
     * 将多个文本文件合并为一个文本文件
     * @param outFileName
     * @param inFileNames
     * @throws IOException
     */
    public static void merge(String outFileName, List<String> inFileNames) throws IOException {
        FileWriter writer = new FileWriter(outFileName, false);
        for (String inFileName : inFileNames) {
            try {
                String txt = readToString(inFileName);
                writer.write(txt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
    }

    /**
     * 查找某目录下的所有文件名称
     *
     * @param path 文件路径
     * @return 返回结果
     */
    public static List<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] temp = file.listFiles();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].isFile()) {
                files.add(temp[i].toString());
            }
            if (temp[i].isDirectory()) {
                files.addAll(getFiles(temp[i].toString()));
            }
        }

        return files;
    }

    /**
     * 读取文件内容
     *
     * @param fileName 文件名称
     * @return 返回文件内容
     * @throws IOException io异常
     */
    public static String readToString(String fileName) throws IOException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(fileContent);
        fileInputStream.close();
        return new String(fileContent, encoding);
    }
}
