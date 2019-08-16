package com.xiaoniucr;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.xiaoniucr.dto.Order;
import com.xiaoniucr.dto.User;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MQApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MQApplication.class,args);
        Environment environment = context.getEnvironment();
        DefaultMQProducer producer = context.getBean(DefaultMQProducer.class);
        try {
            User user = new User(1,"张三",18,0,0);
            Message userRegisterMessage = new Message(environment.getProperty("rocketmq.producer.user.topic"),environment.getProperty("rocketmq.producer.user.register.tag"), JSON.toJSONBytes(user));
            producer.send(userRegisterMessage);
            user.setActive(1);
            Message userActiveMessage = new Message(environment.getProperty("rocketmq.producer.user.topic"),environment.getProperty("rocketmq.producer.user.active.tag"), JSON.toJSONBytes(user));
            producer.send(userActiveMessage);
            Order order = new Order(1,"手表",1000.00,"张三",0);
            Message orderCreateMessage = new Message(environment.getProperty("rocketmq.producer.order.topic"),environment.getProperty("rocketmq.producer.order.create.tag"), JSON.toJSONBytes(order));
            producer.send(orderCreateMessage);
            order.setStatus(1);
            Message orderSuccessMessage = new Message(environment.getProperty("rocketmq.producer.order.topic"),environment.getProperty("rocketmq.producer.order.success.tag"), JSON.toJSONBytes(order));
            producer.send(orderSuccessMessage);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //关闭资源
        producer.shutdown();
        System.out.println("producer shutdown!");

    }
}
