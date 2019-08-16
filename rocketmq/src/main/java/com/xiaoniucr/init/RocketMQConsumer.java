package com.xiaoniucr.init;

import com.xiaoniucr.process.MQMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketMQConsumer {


    @Autowired
    private MQMessageProcessor messageProcessor;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.topic}")
    private String topic;
    @Value("${rocketmq.consumer.tag}")
    private String tag;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;


    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer()
    {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);
        //我们自己实现的监听类
        MQMessageListen listen = new MQMessageListen();
        listen.setMessageProcessor(messageProcessor);
        consumer.registerMessageListener(listen);
        try {
            consumer.subscribe(topic,tag);
            consumer.start();
            log.info("consumer is start ,groupName:{},topic:{}",groupName,topic);
        } catch (MQClientException e) {
            log.error("consumer start error");
            e.printStackTrace();
        }
        return consumer;
    }
}
