package com.msr.tensquare.consumer.listener;

import com.aliyuncs.exceptions.ClientException;
import com.msr.tensquare.consumer.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author: maishuren
 * @Date: 2019/10/25 08:18
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;
    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @RabbitHandler
    public void onMessage(Map<String, String> map) {
        String mobile = map.get("mobile");
        String checkCode = map.get("checkCode");
        try {
            smsUtil.sendSms(mobile, templateCode, signName, "{\"checkcode\":\"" + checkCode + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println(mobile + "     " + checkCode);
    }
}
