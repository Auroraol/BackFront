package com.springboot101.confirm.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: 消费者消费消息失败的回调
 */
@Slf4j
@Component
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        //注入
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnsCallback(this::returnedMessage);
    }

    /*交换机确认回调
    1.交换机收到了消息 触发回调
    1.1 correlationData(我们在发消息的时候自己创建的) 消息的ID以及消息内容
    1.2 ack 交换机收到消息 true
    1.3 cause 交换机收到消息的原因 null
    ---------------------------------
    2.交换机未收到消息 触发回调
    2.1 correlationData 消息的ID以及消息内容
    2.2 ack 交换机未收到消息 false
    2.3 cause 失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            log.info("发送消息到交换机成功！correlationData={}", correlationData.getId());
        }else {
            log.error("发送消息到交换机失败！原因为：{}",cause.toString());
        }
    }

    //只有不可达目的地时才进行回退 只有失败时才触发 若消息发送至延迟队列则一定会触发回退 记得根据交换机名称排除延迟队列
    //在消息传递过程中不可达目的地时将消息返回给生产者
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息：'{}',被交换机：{}回退，routingKey为：{},回退原因为：{}"
                ,new String(returned.getMessage().getBody())
                ,returned.getExchange()
                ,returned.getRoutingKey()
                ,returned.getReplyText());
        /*
        //10s后消息重发
        try {
            Thread.sleep(10000);
            log.info("时间:{}，生产者重新发消息",new Date());
            // 正确的路由key, 可以写死
            rabbitTemplate.convertAndSend(returned.getExchange(),returned.getRoutingKey(),new String(returned.getMessage().getBody()));
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */
    }
}
