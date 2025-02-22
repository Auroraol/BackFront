package net.javadog.pulsar.logistics.service;

/**
 * 物流-接口
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
public interface LogisticsService {

    /**
     * 更新订单的物流信息。
     *
     * 该方法用于根据订单号更新订单的物流状态。具体实现可能包括查询物流公司的API，
     * 获取最新的物流信息，并将这些信息更新到订单系统中。
     *
     * @param orderId 订单号，用于标识需要更新物流信息的订单。
     *                假设订单号是唯一且不为空的字符串。
     */
    void updateLogistics(String orderId);
}
