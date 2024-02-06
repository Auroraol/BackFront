package com.springboot101.confirm.receiver;

import com.rabbitmq.client.Channel;
import com.springboot101.confirm.config.MqConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 普通队列消费者代码
 * @description 接受消息,消息消费
 * @date 2020/6/29 16:31
 */
@Slf4j
@Component
public class ReceiverMessage {

    @RabbitListener(queues = MqConfirmConfig.CONFIRM_QUEUE_NAME)
    public void processHandler1(String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("接受到的队列confirm.queue消息：{}",msg);

            //TODO 具体业务

            // 手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //这是用来确认消息已经被成功处理的代码。basicAck 方法通知 RabbitMQ 该消息已经被成功处理，并且可以从队列中删除。
        }  catch (Exception e) {

            if (message.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}