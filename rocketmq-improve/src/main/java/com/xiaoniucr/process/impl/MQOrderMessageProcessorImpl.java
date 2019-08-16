package com.xiaoniucr.process.impl;

import com.xiaoniucr.dto.Order;
import com.xiaoniucr.process.MQMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQOrderMessageProcessorImpl implements MQMessageProcessor<Order> {

    @Override
    public boolean handleMessage(Order message) {

        String prefix;
        if(message.getStatus() == 0){
            prefix = "下单";
        }else{
            prefix = "交易";
        }
        log.info("用户{}成功：order==>{}",prefix,message);
        return true;
    }

    @Override
    public Class<Order> getClazz() {
        return Order.class;
    }
}
