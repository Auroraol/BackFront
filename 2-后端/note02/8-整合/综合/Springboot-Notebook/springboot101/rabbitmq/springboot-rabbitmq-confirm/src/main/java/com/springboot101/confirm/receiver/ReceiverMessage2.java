package com.springboot101.confirm.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *基于注解声明队列和交换机
 * @description 接受消息,消息消费
 * @date 2020/6/29 16:31
 */
@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "direct.queue1"),
        exchange = @Exchange(name = "jindao.direct", type = ExchangeTypes.DIRECT),
        key = {"confirm.direct.queue1", "confirm.direct.queue2"}
))
public class ReceiverMessage2 {

    @RabbitHandler
    public void processHandler1(String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("接受到的队列direct.queue1消息：{}",msg);

            //TODO 具体业务

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

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