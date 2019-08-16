package com.xiaoniucr.process;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消息处理接口
 */
public interface MQMessageProcessor {

    boolean handleMessage(MessageExt messageExt);
}
