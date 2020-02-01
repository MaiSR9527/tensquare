package com.msr.tensquare.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 18:01
 * @version: v1.0
 */

public class DownloadUtil {

    public static void download(String urlStr, String filename, String savePath) throws IOException {
        URL url = new URL(urlStr);
        //打开url连接
        URLConnection connection = url.openConnection();
        //请求超时时间
        connection.setConnectTimeout(5000);
        //输入流
        InputStream inputStream = connection.getInputStream();
        //缓冲数据
        byte[] bytes = new byte[1024];
        //数据长度
        int len;
        //文件
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file.getPath() + "\\" + filename);
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            inputStream.close();
        }

    }
}
