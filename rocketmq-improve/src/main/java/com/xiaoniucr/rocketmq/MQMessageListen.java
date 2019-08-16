package com.xiaoniucr.rocketmq;

import com.google.common.collect.Maps;
import com.xiaoniucr.process.MQMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Map;

@Slf4j
public class MQMessageListen implements MessageListenerConcurrently {

    //MQMessageProcessor接口的实现类放进map集合 key：tag value：MQMessageProcessor实现类
    private Map<String, MQMessageProcessor> handleMap = Maps.newHashMap();

    public void registerHandler(String tags, MQMessageProcessor mqMessageProcessor) {
        handleMap.put(tags, mqMessageProcessor);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        MessageExt message = list.get(0);
        String body = new String(message.getBody());
        String topicName = message.getTopic();
        //获取到tag
        String tags = message.getTags();
        String keys = message.getKeys();
        log.info("[MQMessageListen] Topic：{}，tags：{},keys：{}",topicName,tags,keys);
        //根据tag从handleMap里获取到我们的处理类
        MQMessageProcessor mqMessageProcessor = handleMap.get(tags);
        Object obj = mqMessageProcessor.transferMessage(body);
        boolean result = mqMessageProcessor.handleMessage(obj);
        if(!result){
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }




}
