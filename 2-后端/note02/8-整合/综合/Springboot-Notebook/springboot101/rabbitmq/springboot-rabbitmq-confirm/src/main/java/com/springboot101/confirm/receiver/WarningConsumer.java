package com.springboot101.confirm.receiver;

import com.rabbitmq.client.Channel;
import com.springboot101.confirm.config.MqConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 报警队列消费者代码
 * @description 报警消费者
 */
@Slf4j
@Component
public class WarningConsumer {
    @RabbitListener(queues = MqConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message){
        String msg=new String(message.getBody());
        log.error("Warning!:发现不可路由消息：{}",msg);
    }
}