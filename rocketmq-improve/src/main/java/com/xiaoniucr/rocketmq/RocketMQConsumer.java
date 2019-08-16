package com.xiaoniucr.rocketmq;

import com.xiaoniucr.process.impl.MQOrderMessageProcessorImpl;
import com.xiaoniucr.process.impl.MQUserMessageProcessorImpl;
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
    private MQUserMessageProcessorImpl userMessageProcessor;
    @Autowired
    private MQOrderMessageProcessorImpl orderMessageProcessor;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    //用户模块topic
    @Value("${rocketmq.consumer.user.topic}")
    private String userTopic;
    @Value("${rocketmq.consumer.user.register.tag}")
    //用户模块下的子tag
    private String userRegisterTag;
    @Value("${rocketmq.consumer.user.active.tag}")
    private String userActiveTag;

    //订单模块topic
    @Value("${rocketmq.consumer.order.topic}")
    private String orderTopic;
    //订单模块下的子tag
    @Value("${rocketmq.consumer.order.create.tag}")
    private String orderCreateTag;
    @Value("${rocketmq.consumer.order.success.tag}")
    private String orderSuccessTag;

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
        //用户相关消息处理
        listen.registerHandler(userRegisterTag,userMessageProcessor);
        listen.registerHandler(userActiveTag,userMessageProcessor);
        //订单相关消息处理
        listen.registerHandler(orderCreateTag,orderMessageProcessor);
        listen.registerHandler(orderSuccessTag,orderMessageProcessor);
        consumer.registerMessageListener(listen);
        try {
            //这里需要多加一个设置。第二个参数是tag表示只会消费topic下面标签为tag的消息，如果是* 就表示会消费tapic下面所有的消息。
            consumer.subscribe(userTopic,"*");
            consumer.subscribe(orderTopic,"*");
            consumer.start();
            log.info("consumer is start ,groupName:{},topic:{}、{}",groupName,userTopic,orderTopic);
        } catch (MQClientException e) {
            log.error("consumer start error");
            e.printStackTrace();
        }
        return consumer;
    }
}
