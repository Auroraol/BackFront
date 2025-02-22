package net.javadog.pulsar.pay.service.pulsar.starter;

import cn.hutool.extra.spring.SpringUtil;
import net.javadog.pulsar.pay.service.pulsar.impl.PayPulsarConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(value = 1)
public class PayPulsarConsumerStart implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args){
        // 支付启动器
        new Thread(() -> {
            PayPulsarConsumer consumer = SpringUtil.getBean(PayPulsarConsumer.class);
            try {
                consumer.start();
            } catch (Exception e) {
                log.error("消费Pulsar数据异常，停止Pulsar连接。异常信息：{}", e.getMessage(), e);
                consumer.close();
            }
        }).start();
    }
}
