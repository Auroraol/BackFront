package net.javadog.pulsar.stock.service.pulsar.starter;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.javadog.pulsar.stock.service.pulsar.impl.StockPulsarConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 1)
public class StockPulsarConsumerStart implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args){
        // 支付启动器
        new Thread(() -> {
            StockPulsarConsumer consumer = SpringUtil.getBean(StockPulsarConsumer.class);
            try {
                consumer.start();
            } catch (Exception e) {
                log.error("消费Pulsar数据异常，停止Pulsar连接。异常信息：{}", e.getMessage(), e);
                consumer.close();
            }
        }).start();
    }
}
