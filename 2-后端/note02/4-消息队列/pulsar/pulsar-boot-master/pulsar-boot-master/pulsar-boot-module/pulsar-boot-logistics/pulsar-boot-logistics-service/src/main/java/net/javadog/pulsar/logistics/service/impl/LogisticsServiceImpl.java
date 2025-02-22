package net.javadog.pulsar.logistics.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.javadog.pulsar.logistics.service.LogisticsService;
import org.springframework.stereotype.Service;

/**
 * 物流-接口实现
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
@Service
@Slf4j
public class LogisticsServiceImpl implements LogisticsService {
    @SneakyThrows
    @Override
    public void updateLogistics(String orderId) {
        // 模拟根据oderId更新物流状态，耗时1秒
        Thread.sleep(2000);
        log.info("【logistics】物流逻辑执行成功！");
    }
}
