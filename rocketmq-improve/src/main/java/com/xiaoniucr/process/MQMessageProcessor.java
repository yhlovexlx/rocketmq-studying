package com.xiaoniucr.process;

import com.alibaba.fastjson.JSON;

public interface MQMessageProcessor<T> {

    //消息处理
    boolean handleMessage(T message);

    //获取消息实体类类型
    Class<T> getClazz();

    //消息反序列化为对象
    default T transferMessage(String message){
        return JSON.parseObject(message,getClazz());
    }
}
