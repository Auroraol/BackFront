package net.javadog.pulsar.stock.service;

/**
 * 库存-接口
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
public interface StockService {
    /**
     * 更新库存信息的函数。
     * <p>
     * 本函数旨在根据订单号更新相应的库存信息。通过订单号，检索到相应的订单详情，
     * 进而对订单中涉及到的商品库存进行更新。具体的更新逻辑未在函数中直接体现，
     * 而是通过调用其他函数或操作数据库等方式实现。这里注重描述函数的目的和作用，
     * 而非实现细节。
     *
     * @param orderId 订单号，作为更新库存的关键依据。
     *                订单号应唯一标识一个订单，通过订单号可以找到对应的订单详情。
     */
    void updateStock(String orderId);
}
