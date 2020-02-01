package com.tensquare.user.crawler.pipeline;

import com.msr.tensquare.util.DownloadUtil;
import com.msr.tensquare.util.IdWorker;
import com.tensquare.user.crawler.dao.UserDao;
import com.tensquare.user.crawler.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;

/**
 * @description:
 * @author: MaiShuRen
 * @date: 2020/1/30 18:09
 * @version: v1.0
 */
@Component
@Slf4j
public class UserPipeline implements Pipeline {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        User user = new User();
        user.setId(idWorker.nextId()+"");
        user.setNickname(resultItems.get("nickname"));
        String image = resultItems.get("image");
        String filename = image.substring(image.lastIndexOf("/")+1);
        user.setAvatar(filename);
        try {
            DownloadUtil.download(image,filename,"e:/userImg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
