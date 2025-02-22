package net.javadog.pulsar.order.service;

/**
 * 订单-接口
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
public interface OrderService {

    /**
     * 根据支付ID更新订单状态。
     *
     * 本方法旨在接收一个支付ID，然后根据该ID在系统中查找对应的订单，并更新其支付状态。
     * 假设系统中存在一种机制，能够通过支付ID唯一地识别订单。
     *
     * @param orderId 支付ID，用于识别订单。
     */
    void updateOrder(String orderId);
}
