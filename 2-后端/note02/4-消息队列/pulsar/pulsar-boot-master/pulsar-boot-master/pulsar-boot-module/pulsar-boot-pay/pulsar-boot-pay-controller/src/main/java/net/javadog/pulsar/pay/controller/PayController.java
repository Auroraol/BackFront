package net.javadog.pulsar.pay.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.javadog.pulsar.common.pojo.ResponseData;
import net.javadog.pulsar.logistics.service.LogisticsService;
import net.javadog.pulsar.order.service.OrderService;
import net.javadog.pulsar.pay.service.pulsar.PayPulsarProducer;
import net.javadog.pulsar.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付-控制器
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
@Tag(name = "支付-控制器")
@RestController
@RequestMapping("/pay")
public class PayController {

    private static final Logger log = LoggerFactory.getLogger(PayController.class);
    private PayPulsarProducer payPulsarProducer;

    private OrderService orderService;

    private StockService stockService;

    private LogisticsService logisticsService;

    @Resource
    public void setPayPulsarProducer(PayPulsarProducer payPulsarProducer) {
        this.payPulsarProducer = payPulsarProducer;
    }

    @Resource
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Resource
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Resource
    public void setLogisticsService(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping("/payNormal")
    @Operation(summary = "支付-普通串行支付-方式A")
    public ResponseData payPulsar(@RequestParam String orderId){
        long startTime = System.currentTimeMillis();
        log.info("==========支付-普通串行支付-方式A开始==========");

        // 处理支付逻辑...省略

        // 处理订单逻辑...
        orderService.updateOrder(orderId);

        // 处理库存逻辑...
        stockService.updateStock(orderId);

        // 处理物流逻辑
        logisticsService.updateLogistics(orderId);

        long endTime = System.currentTimeMillis();
        log.info("方式A【总耗时】(毫秒),{}", endTime - startTime);
        log.info("==========支付-普通串行支付-结束A开始==========");
        return ResponseData.success("支付-普通串行支付-方式A总耗时(毫秒):" + (endTime - startTime));
    }

    @GetMapping("/payPulsar")
    @Operation(summary = "支付-采用消息队列解耦-方式B")
    public ResponseData payNormal(@RequestParam String orderId){
        long startTime = System.currentTimeMillis();
        log.info("==========支付-采用消息队列解耦-方式B开始==========");
        payPulsarProducer.handler(orderId, 0);
        long endTime = System.currentTimeMillis();
        log.info("方式B【总耗时】(毫秒),{}", endTime - startTime);
        log.info("==========支付-采用消息队列解耦-方式B结束==========");
        return ResponseData.success("支付-采用消息队列解耦-方式B总耗时(毫秒):" + (endTime - startTime));
    }

}
