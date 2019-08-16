package com.xiaoniucr;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MQApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MQApplication.class,args);
        Environment environment = context.getEnvironment();
        DefaultMQProducer producer = context.getBean(DefaultMQProducer.class);

        for(int i = 0;i < 10; i ++){
            String body = "hello rocketMQ" + i;
            Message message = new Message(environment.getProperty("rocketmq.producer.topic"),environment.getProperty("rocketmq.producer.tag"),body.getBytes());
            try {
                SendResult result = producer.send(message);
                Thread.sleep(1000);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //关闭资源
        producer.shutdown();
        System.out.println("producer shutdown!");

    }
}
