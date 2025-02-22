package net.javadog.pulsar.stock.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.javadog.pulsar.stock.service.StockService;
import org.springframework.stereotype.Service;

/**
 * 库存-接口实现
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {
    @SneakyThrows
    @Override
    public void updateStock(String orderId) {
        // 模拟根据oderId更新库存状态，耗时1秒
        Thread.sleep(2000);
        log.info("【stock】库存逻辑执行成功！");
    }
}
