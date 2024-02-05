package com.springboot101.confirm.sender;

import com.springboot101.confirm.callback.ConfirmCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 
 * @Description:
 */
@Slf4j
@Component
public class SendMessage {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * @param exchangeName   交换机
     * @param routingKey     队列
     * @param message        消息体
    
     * @description 发送消息
     * @date 2020/6/29 16:22
     */
    public void sendMessage(String exchangeName, String routingKey, Object message) {
        /**
         * 发送消息
         */
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return messagePostProcessor;
                },
                new CorrelationData(UUID.randomUUID().toString()));
        /*
        * 下面是每个参数的含义：
        exchangeName：要发送消息的交换机的名称。
        routingKey：要使用的路由键。
        message：要发送的消息体。它可以是任何 Java 对象，Spring AMQP 将根据消息转换器的配置将其转换为消息。
        messagePostProcessor：持久化模式意味着一旦消息被接收，它将被持久化存储，以确保即使在消息代理重启后，消息也不会丢失。
        correlationData：用于关联消息的数据。通常，可以使用 CorrelationData 对象提供一个关联标识，以便在发送确认或失败时了解消息的状态。
        * */
    }
}
