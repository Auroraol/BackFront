package net.javadog.pulsar.pay.service.pulsar.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import net.javadog.pulsar.logistics.service.pulsar.LogisticsPulsarProducer;
import net.javadog.pulsar.order.service.pulsar.OrderPulsarProducer;
import net.javadog.pulsar.stock.service.pulsar.StockPulsarProducer;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * 支付异步通知-消费者
 *
 * @author hdx
 * @version 1.0
 * @since 2024.07
 */
@Slf4j
@Component
public class PayPulsarConsumer {

    @Value("${pulsar.url}")
    private String url;
    @Value("${pulsar.pay-topic}")
    private String topic;
    @Value("${pulsar.pay-subscription}")
    private String subscription;

    private PulsarClient client = null;
    private Consumer<byte[]> consumer = null;

    private OrderPulsarProducer orderPulsarProducer;

    private StockPulsarProducer stockPulsarProducer;

    private LogisticsPulsarProducer logisticsPulsarProducer;


    @Resource
    public void setStockPulsarProducer(StockPulsarProducer stockPulsarProducer) {
        this.stockPulsarProducer = stockPulsarProducer;
    }

    @Resource
    public void setOrderPulsarProducer(OrderPulsarProducer orderPulsarProducer) {
        this.orderPulsarProducer = orderPulsarProducer;
    }

    @Resource
    public void setLogisticsPulsarProducer(LogisticsPulsarProducer logisticsPulsarProducer) {
        this.logisticsPulsarProducer = logisticsPulsarProducer;
    }

    /**
     * 使用@PostConstruct注解用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
     */
    @PostConstruct
    public void initPulsar() throws Exception {
        try {
            //构造Pulsar client
            client = PulsarClient.builder()
                    .serviceUrl(url)
                    .build();
            final String ip = InetAddress.getLocalHost().getHostAddress().replaceAll("\\.", "");
            //创建consumer
            consumer = client.newConsumer()
                    .topic(topic.split(","))
                    .consumerName("payPulsarConsumer" + ip)
                    .subscriptionName(subscription)
                    //指定消费模式，包含：Exclusive，Failover，Shared，Key_Shared。默认Exclusive模式
                    .subscriptionType(SubscriptionType.Shared)
                    //指定从哪里开始消费还有Latest，valueof可选，默认Latest
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                    //指定消费失败后延迟多久broker重新发送消息给consumer，默认60s
                    .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                    .batchReceivePolicy(BatchReceivePolicy.builder()
                    .maxNumBytes(10*1024*1024)
                    .maxNumMessages(-1)
                    .timeout(100, TimeUnit.MILLISECONDS)
                    .build())
                    .subscribe();
        } catch (Exception e) {
            log.error("Pulsar初始化异常：", e);
            throw e;
        }
    }

    public void start() throws Exception {
        //消费消息
        log.debug("支付消费者启动");
        while (true) {
            Message<byte[]> message = consumer.receive();
            log.debug("消费消息中，message：{}", message);
            final String id = new String(message.getValue());

            if (ObjectUtil.isNotNull(id)) {
                try {
                    messageHandle(id);
                    consumer.acknowledge(message);
                } catch (Exception e) {
                    log.error("消费Pulsar支付数据异常，key【{}】，orderId【{}】：", message.getKey(), id, e);
                }
            }

        }
    }

    private void messageHandle(String orderId) {
        log.info("【支付消费】，orderId：{}", orderId);

        // 订单生产消息
        orderPulsarProducer.handler(orderId, 0);

        // 库存生产消息
        stockPulsarProducer.handler(orderId, 0);

        // 物流生产消息
        logisticsPulsarProducer.handler(orderId, 0);
    }

    public void close() {
        try {
            consumer.close();
        } catch (PulsarClientException e) {
            log.error("关闭Pulsar消费者失败：", e);
        }
        try {
            client.close();
        } catch (PulsarClientException e) {
            log.error("关闭Pulsar连接失败：", e);
        }
    }

}
