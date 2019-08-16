package com.xiaoniucr.init;

import com.xiaoniucr.process.MQMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MQMessageListen implements MessageListenerConcurrently {

    @Autowired
    private MQMessageProcessor messageProcessor;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        MessageExt message = list.get(0);
        String body = new String(message.getBody());
        String topicName = message.getTopic();
        String tags = message.getTags();
        String keys = message.getKeys();
        log.info("[MQMessageListen] Topic：{}，tags：{},keys：{}",topicName,tags,keys);
        boolean result = messageProcessor.handleMessage(message);
        if(!result){
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


    public void setMessageProcessor(MQMessageProcessor messageProcessor){
        this.messageProcessor = messageProcessor;
    }



}
