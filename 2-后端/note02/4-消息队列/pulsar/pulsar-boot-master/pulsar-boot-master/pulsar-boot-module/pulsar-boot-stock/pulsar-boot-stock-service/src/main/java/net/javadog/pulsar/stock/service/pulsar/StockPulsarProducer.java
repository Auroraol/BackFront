package net.javadog.pulsar.stock.service.pulsar;

/**
 * 库存异步通知生产者.
 *
 * @author hdx
 * @version 1.0.0
 * @since 2024/06/18 13:26:00
 */
public interface StockPulsarProducer {

    /**
     * 库存-处理消息
     */
    void handler(String orderId, long delay);
}
