# 实现

![image-20240205222832346](REDEME.assets/image-20240205222832346.png)

## 交换机的发布确认

![image-20240205232113176](REDEME.assets/image-20240205232113176.png)





## 回退消息并重发

![image-20240205224135612](REDEME.assets/image-20240205224135612.png)



## 备份交换机

备份交换机可以理解为 RabbitMQ 中交换机的“备胎”，当给某一个交换机声明一个对应的备份交换机时，就是为它创建一个备胎，当交换机接收到一条不可路由消息时，将会把这条消息转发到备份交换机中，由备份交换机来进行转发和处理，通常备份交换机的类型为 Fanout （扇出），这样就能把所有消息都投递到与其绑定的队列中，然后我们在备份交换机下绑定一个队列，这样所有那些原交换机无法被路由的消息，就会都进 入这个队列了。当然，我们还可以建立一个报警队列，用独立的消费者来进行监测和报警并可以重发消息。

![](REDEME.assets/image-20240205231709919.png)

交换机都收到消息

+ key1路径的消息正常被消费者消费 
+ key12路径的消息找不到对应的队列，被转发到报警和备份队列中，此时消息回报功能不生效

# 代码

## application.yml

```yaml
spring:
    rabbitmq:
        host: 192.168.200.134
        port: 5672
        username: admin
        password: admin
        #    高级发布确认 发布消息成功后将会触发回调方法
        publisher-confirm-type: correlated
        #    消息回退 当消息未路由至队列时触发,确保消息发送失败后可以重新返回到队列中,当消息无法被路由到队列时，RabbitMQ 会将消息返回给生产者
        publisher-returns: true
```

## Callback

ConfirmCallbackService

```java
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
           // log.info("时间:{} 发送消息到交换机成功！correlationData={}",new Date(), correlationData.getId());
        }else {
            log.error("发送消息到交换机失败！原因为：{}",cause.toString());
        }
    }

    //当消息未路由到队列时触发 只有失败时才触发 若消息发送至延迟队列则一定会触发回退 记得根据交换机名称排除延迟队列
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息：'{}',被交换机：{}回退，回退原因为：{}，routingKey为：{}"
                ,new String(returned.getMessage().getBody())
                ,returned.getExchange()
                ,returned.getReplyText()
                ,returned.getRoutingKey());
        //10s后消息重发
        try {
            Thread.sleep(10000);
            log.info("时间:{}，生产者重新发消息",new Date());
            // 正确的路由key, 可以写死
            rabbitTemplate.convertAndSend(returned.getExchange(),returned.getRoutingKey(),new String(returned.getMessage().getBody()));
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

