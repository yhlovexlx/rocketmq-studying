package com.xiaoniucr.process.impl;

import com.xiaoniucr.dto.User;
import com.xiaoniucr.process.MQMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQUserMessageProcessorImpl implements MQMessageProcessor<User> {


    @Override
    public boolean handleMessage(User message) {

        String prefix;
        if(message.getActive() == 0){
            prefix = "注册";
        }else{
            prefix = "激活";
        }
        log.info("用户{}成功：user==>{}",prefix,message);
        return true;
    }

    @Override
    public Class<User> getClazz() {
        return User.class;
    }
}
