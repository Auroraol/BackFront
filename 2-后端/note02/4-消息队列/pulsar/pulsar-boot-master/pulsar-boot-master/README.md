## 前言

### 🍊缘由

####  消息队列一出手，pulsar就知有没有

![](https://img.javadog.net/blog/pulsar/929ad614405b456d96de4aaae16563ff.png)

##### 🐣闪亮主角

大家好，我是[JavaDog程序狗](https://mp.weixin.qq.com/s/FjOdxCAPFrJpruhS1YB-MA)

今天跟大家分享**pulsar**，一个分布式的消息发布/订阅传递平台。

本狗以身入局，将**pulsar的使用场景，结合实际使用案例，进行大白话分析**。

通过简单代码demo进行讲解，**pulsar在java中如何使用？如何通过pulsar进行异步解耦？......等**

![](https://img.javadog.net/blog/pulsar/e855198ab1e44ca4a3402713ce634928.png)

#####  😈你想听的故事 

狗哥最近在整理学习笔记时，偶然在百度发现pulsar相关的教程竟然大部分付费会员才能看，淦！

![](https://img.javadog.net/blog/pulsar/3e8721f50a4f43cd959f323ffb082140.png)

首先，我不反对知识付费，但是花钱才能看总感觉差那么一点意思。

于是乎，狗哥将**我司日常使用的消息队列pulsar进行总结整理**，让大家一次性免费学个够，不付费也能学的酣畅淋漓

### 🎁如何获取源码

公众号：【JavaDog程序狗】

关注公众号，**发送  “pulsar”**，无任何套路即可获得！

![](https://img.javadog.net/blog/pulsar/0ee7aee02e0f4de99ee78ab98f80517d.png)

## 正文

### 🎯主要目标

#### 1.pulsar是什么

#### 2.pulsar有什么核心特性

#### 3.pulsar与其他消息队列区别

#### 4.docker如何安装pulsar

#### 5.docker如何安装pulsar-manager

#### 6.实际电商业务分析

#### 7.java中如何使用pulsar解决业务问题

### 🍪目标讲解

#### 一.pulsar是什么？

##### 1.官网地址

> [https://pulsar.apache.org/](https://pulsar.apache.org/)

![](https://img.javadog.net/blog/pulsar/2bbb4b2f73e647eaa20665aa46f76c8b.png)

##### 2.pulsar简介

> Apache Pulsar 是一个高性能、可扩展且灵活的分布式消息传递和流处理平台

 👽人话解释

> Pulsar 就是一个消息中间件，和Kafka、RocketMQ功能差不多，多用于削峰解耦

##### 3.pulsar发展历程

- Pulsar 最初由 Yahoo 开发，并在内部大规模使用。

- 2016 年，Yahoo 将 Pulsar 开源，并将其捐赠给 Apache 软件基金会。

- 2018 年，Pulsar 成为了 **Apache 软件基金会的顶级项目**。

![](https://img.javadog.net/blog/pulsar/34a8189a51c443c8996198237fe552fa.png)

******

#### 二.pulsar核心特性？

##### 1.消息传递

- 支持多种消息订阅模式
	如独占（Exclusive）、灾备（Failover）、共享（Shared）和 Key_Shared 订阅。
	
- 支持消息的延迟发送，
	即消息可以在发布后的一段时间内被消费者消费。

##### 2.存储与计算分离架构

- Pulsar 采用了存储与计算分离的设计，这意味着消息的处理逻辑（Broker）与数据存储可以独立扩展。
- 这种架构使得 Pulsar 在扩展性和可靠性方面表现优异。

##### 3.多租户支持

- Pulsar 支持多租户环境，允许不同的应用程序或团队在同一个集群中运行而互不影响。

##### 4.持久化存储

- Pulsar 提供了持久化的消息存储能力，确保即使在故障发生时消息也不会丢失。

##### 5.轻量级函数式计算

- Pulsar 支持在消息传递之上构建简单的函数式计算，这使得它不仅仅是一个消息队列，还可以用于实现流处理应用。

##### 6.跨地域复制

- Pulsar 允许跨多个地理位置的数据中心进行数据复制，以实现高可用性和灾难恢复。

👽人话解释

> Pulsar 就像是一个快递中转站，但它不仅能高效地处理大量的包裹（消息），还能确保每个包裹都能准确无误地送达目的地（消费者）

******

#### 三.pulsar与其他消息队列区别？

|  | Pulsar |ActiveMQ  |RabbitMQ  | RocketMQ | Kafka |
|--|--|--|--|--|--|
|单机吞吐量  | 十万级 | 万级 | 万级 |十万级  | 十万级 |
|开发语言  | Java |Java  | Erlang |Java  | Java/Scala |
|维护者  | Apache |Apache  | Spring |Apache | Apache  |
|社区活跃度  | 高 | 低 | 高 | 高 | 高 |
|消费模式  |独占、共享、灾备、key共享  |P2P、Pub-Sub  |direct、topic、Headers、fanout  | 基于Topic和MessageTag |基于Topic的Pub-Sub  |
|顺序消息  |支持  | 不支持 |不支持 | 支持 | 支持  |
|稳定性  |一般  | 好 | 好 | 一般 | 较差 |
|集群支持  | 集群模式 |主备模式  | 复制模式 |主备模式  | 集群可扩展性强 |

关于消息队列的选取，在实际案例中取决于你的具体需求和技术背景

1. 大规模数据流处理
推荐使用：Apache Pulsar 或 Apache Kafka
理由：
Pulsar：支持存储与计算分离，可以独立扩展存储和处理能力，非常适合大规模数据流处理。
Kafka：以其高性能和低延迟著称，适合处理大量实时数据流。

2. 多租户环境
推荐使用：Apache Pulsar
理由：
Pulsar：提供了强大的多租户支持，可以为不同的应用程序和服务分配独立的命名空间和资源。

3. 高可用性和容错性
推荐使用：Apache Pulsar 或 Apache Kafka
理由：
Pulsar：具有内置的高可用性支持，消息持久化到磁盘，支持消息复制。
Kafka：同样具有高可用性，支持消息持久化和副本机制。

4. 灵活的消息路由
推荐使用：Apache RabbitMQ
理由：
RabbitMQ：基于 AMQP 协议，支持复杂的路由规则和消息模式，非常适合需要灵活消息路由的场景。

5. 简单易用性
推荐使用：Apache RabbitMQ 或 RocketMQ
理由：
RabbitMQ：提供了丰富的客户端库，易于集成。
RocketMQ：API 设计简洁，易于理解和使用。

******

#### 四.docker如何安装pulsar

小伙伴如果不会在windows上安装docker，请查看狗哥之前文章

[【从零开始】Docker Desktop：听说你小子要玩我](https://mp.weixin.qq.com/s/kZXSpU8Cc2yswEglozAvTQ)

> https://mp.weixin.qq.com/s/kZXSpU8Cc2yswEglozAvTQ

##### 1.安装pulsar 

> 拉取pulsar2.9.2版本镜像

在Windows PowerShell，输入拉取pulsar镜像命令

```
docker pull apachepulsar/pulsar:2.9.2
```

![](https://img.javadog.net/blog/pulsar/1c8d4909fb7f457eb0b35279af5e2da3.png)

##### 2.启动pulsar 

> 启动pulsar2.9.2单机版

```
docker run -it -p 6650:6650 -p 8080:8080 apachepulsar/pulsar:2.9.2 bin/pulsar standalone
```

![](https://img.javadog.net/blog/pulsar/4ff4ae2075c442a28e8be884bbf1d5f3.png)

##### 3.关闭对话框

使用Ctrl+P+Q组合键。当你在容器内部时，按下这个组合键可以将当前的终端会话断开，同时保持容器继续运行。

```
Ctrl+P+Q
```

![](https://img.javadog.net/blog/pulsar/07613fb1abf04f9b9d2a051055f3008a.png)

******

#### 五.docker如何安装pulsar-manager

什么是pulsar-manager？

> **Pulsar Manager 是 Apache Pulsar 的一个管理工具，它提供了一个用户界面和 RESTful API 用于管理和监控 Pulsar 集群**。
> Pulsar Manager 可以帮助管理员执行一系列集群管理任务，例如创建和删除命名空间、管理 topic、查看集群状态等。

👽人话解释

> pulsar-manager就是一个pulsar的可视化工具，功能就像Navicat差不多

##### 1.安装pulsar-manager 

> 拉取pulsar-manager0.2.0版本镜像

在Windows PowerShell，输入拉取pulsar镜像命令

```
docker pull apachepulsar/pulsar-manager:v0.2.0
```

![](https://img.javadog.net/blog/pulsar/b7698d0fe07148e9847efee15628d5f3.png)

##### 2.启动pulsar-manager  

> 启动pulsar-manager v0.2.0

```
docker run -it -p 9527:9527 -p 7750:7750 -e SPRING_CONFIGURATION_FILE=/pulsar-manager/pulsar-manager/application.properties apachepulsar/pulsar-manager:v0.2.0
```

![](https://img.javadog.net/blog/pulsar/3faeaf08edfa41019082c253b02d2794.png)

##### 3.关闭对话框

使用Ctrl+P+Q组合键。当你在容器内部时，按下这个组合键可以将当前的终端会话断开，同时保持容器继续运行。

```
Ctrl+P+Q
```

![](https://img.javadog.net/blog/pulsar/d17b36d9ce734f668e88899611a77aa3.png)

##### 4.设置账号和密码

> 设置账号为admin，密码为apachepulsar

```
CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)
curl \
   -H 'X-XSRF-TOKEN: $CSRF_TOKEN' \
   -H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' \
   -H "Content-Type: application/json" \
   -X PUT http://localhost:7750/pulsar-manager/users/superuser \
   -d '{"name": "admin", "password": "apachepulsar", "description": "test", "email": "xxx@test.org"}'
```

![](https://img.javadog.net/blog/pulsar/8dac6a53f6a5419fa08c98e8baaeafd2.png)

##### 5.登录控制台验证

> 访问 [http://localhost:9527/](http://localhost:9527/)

输入账号：admin  密码：apachepulsar

![](https://img.javadog.net/blog/pulsar/9e8112bc96c847678cf0f5d160fb7095.png)

##### 6.新增环境

> 新增环境，填写环境名，及启动的pulsar单体应用地址

![](https://img.javadog.net/blog/pulsar/2c78bedbde074387b78a6ce7471491b3.png)

##### 7.查看控制台

![](https://img.javadog.net/blog/pulsar/67877c25e22f4b438b3443b4e7cd9dfb.png)

- Clusters (集群)
定义: Pulsar 集群是一组运行 Pulsar Broker 的服务器集合。
作用: 多个集群可以被配置来**实现地理分布式的部署，以支持数据复制和灾难恢复**。
管理: 使用 pulsar-admin clusters 命令来管理集群。

- Tenants (租户)
定义: 租户代表了组织或应用程序的逻辑分隔。
作用: **租户用于隔离不同组织的数据，并且可以为每个租户设置访问控制策略**。
管理: 使用 pulsar-admin tenants 命令来管理租户。

- Namespaces (命名空间)
定义: 命名空间是在租户内部进一步划分资源的方式。
作用: **命名空间用于隔离不同应用程序的数据，并且可以为每个命名空间设置不同的策略和配置**。
管理: 使用 pulsar-admin namespaces 命令来管理命名空间。

- Topics (主题)
定义: 主题是消息的实际载体，是生产者发送消息和消费者接收消息的地方。
作用: **每个主题都属于一个特定的命名空间，主题可以有多个消费者订阅**。
管理: 使用 pulsar-admin topics 命令来管理主题。

- Tokens (令牌)
定义: 令牌是一种认证机制，允许客户端通过提供一个安全令牌来访问 Pulsar 资源。
作用: **令牌通常用于简化客户端认证过程，特别是对于不需要长期凭证的情况**。
管理: 使用 pulsar-admin tokens 命令来管理令牌。

******

#### 六.实际电商业务分析

 🌰场景实例

张三在电商平台买了一个产品，支付成功后，张三等着收货就好...

但是在程序业务视角来看，支付成功后，其实还有很多下游服务在默默执行。

如库存、物流、订单服务都会有相应逻辑执行。

我们就模拟真实电商案例，来讲解下**如何使用pulsar以及pulsar能解决什么痛点问题**。

![](https://img.javadog.net/blog/pulsar/9c8753995e2a454f9c7100b4f21fa061.png)

##### 情况一：没有使用pulsar消息队列，业务正常串行执行

张三支付成功后，更新订单，更新库存，更新物流...串行操作，每个服务都耗时2秒

![](https://img.javadog.net/blog/pulsar/5f899377ea0e4a98bb8b7fdbd9945cf9.png)

这种串行的执行方式有很大的问题，如果**整个链路串行执行，那么响应的时间就是每个业务执行时间想加，更新订单(2秒)，更新库存(2秒)，更新物流(2秒)，总共耗时6秒**。

如果还有其他下游业务，链路时间会一直叠加，造成张三用户访问等待时长，并且如果链路中有失败，则会导致整个链路异常

##### 情况二：使用pulsar消息队列，实现异步解耦

张三支付成功后，更新订单，更新库存，更新物流...并行操作

![](https://img.javadog.net/blog/pulsar/61322376e6184ce7b7d97dc258e87c44.png)

这种使用异步解耦方式，每个服务都异步执行，响应立刻返回，用户体验绝佳

******

#### 七.java中如何使用pulsar解决业务问题

我们将上面六中的两种情况进行代码实操，**串行执行和使用pulsar异步解耦**

##### 1.代码结构

![](https://img.javadog.net/blog/pulsar/6f23b6ef0fbc4ce98ef15e869390dcb5.png)
##### 2.关键代码

- 引入pulsar依赖

![](https://img.javadog.net/blog/pulsar/c7b7450618d642b899d6f7074a43efe2.png)


```xml
  <dependency>
      <groupId>org.apache.pulsar</groupId>
      <artifactId>pulsar-client</artifactId>
      <version>${pulsar.version}</version>
  </dependency>
```

- pulsar配置

![](https://img.javadog.net/blog/pulsar/259bc1f7f595439f8549574d73f7508d.png)

```yml
pulsar:
  #支付topic
  pay-topic: persistent://public/default/pay-topic
  #支付subscription
  pay-subscription: pay-subscription
  #订单topic
  order-topic: persistent://public/default/order-topic
  #订单subscription
  order-subscription: order-subscription
  #库存topic
  stock-topic: persistent://public/default/stock-topic
  #库存subscription
  stock-subscription: stock-subscription
  #物流topic
  logistics-topic: persistent://public/default/logistics-topic
  #物流subscription
  logistics-subscription: logistics-subscription
```

![](https://img.javadog.net/blog/pulsar/da592faff38b466697469dfdc4588c1d.png)

```yml
# Pulsar配置
pulsar:
  url: pulsar://192.168.31.27:6650
```

- pulsar生产者

![](https://img.javadog.net/blog/pulsar/63c7795fd8a94dfcaf88c96ba5aa3af2.png)

![](https://img.javadog.net/blog/pulsar/b33919433fbd4ddbb3e2e9bc24093de5.png)

![](https://img.javadog.net/blog/pulsar/5c32c65a9b5c4fdf91eaf44cf0703ad0.png)

```java
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

```

##### 3.启动调试

-  程序启动

> 在pulsar-boot-main下找到Application并运行。注意：pulsar一定先起来！！

![](https://img.javadog.net/blog/pulsar/fbb4bab03d624ebf98d3fb124bb569ec.png)

-  打开swagger接口文档

> 在控制台打开打印的swagger地址，进行接口调试

![](https://img.javadog.net/blog/pulsar/8889863dba7441cf9109ee7627dbbc08.png)

-  访问串行调用方法A

> 随意录入orderId，点击**支付-普通串行支付-方式A**接口【Execute】按钮，查看结果

![](https://img.javadog.net/blog/pulsar/a685529268bb4b23b44f140888806cb5.png)

![](https://img.javadog.net/blog/pulsar/5a802f606c454b068a6f9a02cc459d5f.png)

![](https://img.javadog.net/blog/pulsar/493ce85434b048c19af1aaf68bac114c.png)

-  访问消息队列解耦方法B

> 随意录入orderId，点击**采用消息队列解耦-方式B**接口【Execute】按钮，查看结果

![](https://img.javadog.net/blog/pulsar/6b8b0754d1d44543811d7b2607c083f4.png)

![](https://img.javadog.net/blog/pulsar/5b924f6481ed4fbe851086980d6c7c23.png)

![](https://img.javadog.net/blog/pulsar/ec076759c15d4d019e8e0b718ce20924.png)

##### 4.演示总结

demo代码逻辑简单，业务逻辑清晰，**以最直观的响应时间来展示使用pulsar异步解耦优势，并使用优雅的分层使代码结构干净整洁**。

希望大家能够**下载demo实操一下，好记性不如烂笔头**；彻底掌握使用pulsar的小技巧，将其运用到实战中，真正体现它的优点。

### 🍈猜你想问

####  如何与狗哥联系进行探讨

##### 关注公众号【JavaDog程序狗】

公众号回复【入群】或者【加入】，便可成为【程序员学习交流摸鱼群】的一员，问题随便问，牛逼随便吹，目前**群内已有超过300+个小伙伴啦**！！！

![](https://img.javadog.net/blog/pulsar/e98d36c93ee2d2e1d59adb7005ca5486.png)

##### 2.踩踩狗哥博客

[javadog.net](https://www.javadog.net/)

**里面有狗哥的私密联系方式呦 😘**

>大家可以在里面留言，随意发挥，有问必答

![](https://img.javadog.net/blog/pulsar/e0650478576648aeb5ca12d5f32199ee.png)

###  🍯猜你喜欢

####  文章推荐

[【实操】Spring Cloud Alibaba AI，阿里AI这不得玩一下(含前后端源码)](https://mp.weixin.qq.com/s/ObTHS9Jplkp2ZjcUiBNFfg)

[【规范】看看人家Git提交描述，那叫一个规矩](https://mp.weixin.qq.com/s/EbNWRpSYMdWFv5aUQ2ockw)

[【项目实战】SpringBoot+uniapp+uview2打造H5+小程序+APP入门学习的聊天小项目](https://mp.weixin.qq.com/s/g7AZOWLgW5vcCahyJDEPKA)

[【项目实战】SpringBoot+uniapp+uview2打造一个企业黑红名单吐槽小程序](https://mp.weixin.qq.com/s/t_qwF_HvkdW-6TI3sYUHrA)

[【模块分层】还不会SpringBoot项目模块分层？来这手把手教你！](https://mp.weixin.qq.com/s/fpkiNR2tj832a6VxZozwDg)

![](https://img.javadog.net/blog/pulsar/a303cb88939e299ef9ff35fa1de5e25a.jpg)