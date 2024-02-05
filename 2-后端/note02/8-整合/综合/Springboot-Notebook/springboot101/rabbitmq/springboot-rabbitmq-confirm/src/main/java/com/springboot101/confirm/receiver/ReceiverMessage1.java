package com.springboot101.confirm.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "fanout.queue1")
public class ReceiverMessage1 {

    private int retryNum = 5;

    private int currentNum = 0;


    @RabbitHandler
    public void processHandler(CorrelationData correlationData , String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("消费者 2 号收到：{}", msg);

            String correlationId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");

            System.out.println(correlationId);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {

        }
    }
}