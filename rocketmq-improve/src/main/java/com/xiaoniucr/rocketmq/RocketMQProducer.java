package com.xiaoniucr.rocketmq;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 生产者初始化
 */
@Slf4j
@Component
public class RocketMQProducer {

    @Value("${rocketmq.producer.groupName}")
    private String groupName;
    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;
    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize;
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private int sendMsgTimeout;

    private DefaultMQProducer producer;


    @Bean
    public DefaultMQProducer getRocketMQProducer(){

        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setVipChannelEnabled(false);

        try {
            producer.start();
            log.info("rocketMQ is start! groupName:{},nameserAddr:{}",groupName,namesrvAddr);
        } catch (MQClientException e) {
            log.error(String.format("rocketMQ start failed,%s",e.getMessage()));
            e.printStackTrace();
        }
        return producer;
    }




}
