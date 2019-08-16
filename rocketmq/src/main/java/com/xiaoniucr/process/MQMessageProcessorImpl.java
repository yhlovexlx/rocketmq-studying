package com.xiaoniucr.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * 消息处理接口实现类
 */
@Slf4j
@Service
public class MQMessageProcessorImpl  implements MQMessageProcessor {


    @Override
    public boolean handleMessage(MessageExt messageExt) {

        //收到的消息为字节码，转为String
        String message = new String(messageExt.getBody());
        System.out.println(String.format("收到了消息：%s",message));
        log.info("收到了消息：{}",message);
        return true;
    }
}
