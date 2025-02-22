package net.javadog.pulsar.pay.service.pulsar;

/**
 * 支付异步通知生产者.
 *
 * @author hdx
 * @version 1.0.0
 * @since 2024/06/18 13:26:00
 */

public interface PayPulsarProducer {

    /**
     * 支付-支付成功消息
     */
    void handler(String orderId, long delay);
}
