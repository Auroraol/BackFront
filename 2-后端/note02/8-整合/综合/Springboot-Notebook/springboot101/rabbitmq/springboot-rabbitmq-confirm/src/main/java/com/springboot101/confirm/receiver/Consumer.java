package com.springboot101.confirm.receiver;

import com.springboot101.confirm.config.MqConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
/**
 * @Author: LFJ
 * 普通队列消费者代码
 * @Date: 2024-02-05 22:23
 */
@Slf4j
@Component
public class Consumer {
	@RabbitListener(queues = MqConfirmConfig.CONFIRM_QUEUE_NAME)
	public void confirmConsumer(Message message){
		String msg = new String(message.getBody());
		log.info("接受到的队列confirm.queue消息：{}",msg);
	}
}