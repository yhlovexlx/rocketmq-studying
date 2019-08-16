
# SpringBoot整合RocketMQ升级版
针对实际应用中，往往不会只有一个消息处理类，MQMessageProcessor。
消费者会收到不同topic，不同tag的的消息，这里我们来对我们的consumer类和消息处理类进行扩展一下。

