package com.springboot101.confirm.receiver;

import com.springboot101.confirm.config.MqConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 报警队列消费者代码
 * @description 报警消费者
 */
@Slf4j
@Component
public class BackupConsumer {
    @RabbitListener(queues = MqConfirmConfig.BACKUP_QUEUE_NAME)
    public void receiveWarningMsg(Message message){
        String msg = new String(message.getBody());
        log.info("备份消息：{}",msg);
    }
}