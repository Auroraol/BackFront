package net.javadog.pulsar.logistics.service.pulsar;

/**
 * 订单异步通知生产者.
 *
 * @author hdx
 * @version 1.0.0
 * @since 2024/06/18 13:26:00
 */
public interface LogisticsPulsarProducer {

    /**
     * 订单-处理消息
     */
    void handler(String orderId, long delay);
}
