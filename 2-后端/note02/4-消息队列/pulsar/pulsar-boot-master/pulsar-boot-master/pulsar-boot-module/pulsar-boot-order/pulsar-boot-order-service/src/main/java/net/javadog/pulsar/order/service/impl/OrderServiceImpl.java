package net.javadog.pulsar.order.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.javadog.pulsar.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单-接口实现
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @SneakyThrows
    @Override
    public void updateOrder(String orderId) {
        // 模拟根据oderId更新订单状态，耗时1秒
        Thread.sleep(2000);
        log.info("【order】订单逻辑执行成功！");
    }
}
