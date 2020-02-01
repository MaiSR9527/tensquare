package com.msr.tensquare.crawler.pipeline;

import com.msr.tensquare.util.IKUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 23:16
 * @version: v1.0
 */
@Component
public class ArticleTxtPipeline implements Pipeline {
    @Value("${ai.dataPath}")
    private String dataPath;
    private String channelId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //取出标题
        String title = resultItems.get("title");
        //取出内容
        String content = resultItems.get("content");
        try {
            PrintWriter printWriter = new PrintWriter(new File(dataPath + "/" + channelId + "/" + UUID.randomUUID() + ".txt"));
            printWriter.print(IKUtil.split(title + " " + content, " "));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
