# 一 简介

初次接触 WebSocket 的人，都会问同样的问题：我们已经有了 HTTP 协议，为什么还需要另一个协议？它能带来什么好处？

答案很简单，因为 HTTP 协议有一个缺陷：通信只能由客户端发起。

举例来说，我们想了解今天的天气，只能是客户端向服务器发出请求，服务器返回查询结果。HTTP 协议做不到服务器主动向客户端推送信息。



WebSocket 协议在2008年诞生，2011年成为国际标准。所有浏览器都已经支持了。

它的最大特点就是，服务器可以主动向客户端推送信息，客户端也可以主动向服务器发送信息，是真正的双向平等对话，属于[服务器推送技术](https://en.wikipedia.org/wiki/Push_technology)的一种。



## 1.1 传统的消息通讯方式

### ajax轮询

ajax轮询的原理非常简单，让浏览器隔个几秒就发送一次请求，询问服务器是否有新信息。

场景再现：

客户端：啦啦啦，有没有新信息(Request)

服务端：没有（Response）



### long poll

long poll 其实原理跟 ajax轮询 差不多，都是采用轮询的方式，不过采取的是阻塞模型（一直打电话，没收到就不挂电话），也就是说，客户端发起连接后，如果没消息，就一直不返回Response给客户端。直到有消息才返回，返回完之后，客户端再次建立连接，周而复始。

场景再现：

客户端：啦啦啦，有没有新信息，没有的话就等有了才返回给我吧（Request）

服务端：额。。 等待到有消息的时候。。来 给你（Response）



小姐：

ajax轮询 需要服务器有很快的处理速度和资源。（速度）
long poll 需要有很高的并发，也就是说同时接待客户的能力。（场地大小）

### **长连接**

在页面里嵌入一个隐蔵iframe，将这个隐蔵iframe的src属性设为对一个长连接的请求或是采用xhr请求，服务器端就能源源不断地往客户端输入数据。
优点：消息即时到达，不发无用请求；管理起来也相对方便。
缺点：服务器维护一个长连接会增加开销，当客户端越来越多的时候，server压力大！
实例：Gmail聊天



#### (1).基于http协议的长连接

在HTTP1.0和HTTP1.1协议中都有对长连接的支持。其中HTTP1.0需要在request中增加”Connection： keep-alive“ header才能够支持，而HTTP1.1默认支持.

http1.0请求与服务端的交互过程:

a)客户端发出带有包含一个header：”Connection： keep-alive“的请求

b)服务端接收到这个请求后,根据http1.0和”Connection： keep-alive“判断出这是一个长连接,就会在response的header中也增加”Connection： keep-alive“,同是不会关闭已建立的tcp连接.

c)客户端收到服务端的response后,发现其中包含”Connection： keep-alive“，就认为是一个长连接，不关闭这个连接。并用该连接再发送request.转到a)

#### (2).http1.1请求与服务端的交互过程:



a)客户端发出http1.1的请求

b)服务端收到http1.1后就认为这是一个长连接,会在返回的response设置Connection： keep-alive,同是不会关闭已建立的连接.

c)客户端收到服务端的response后,发现其中包含”Connection： keep-alive“，就认为是一个长连接，不关闭这个连接。并用该连接再发送request.转到a)

基于http协议的长连接减少了请求,减少了建立连接的时间,但是每次交互都是由客户端发起的,客户端发送消息,服务端才能返回客户端消息.因为客户端也不知道服务端什么时候会把结果准备好,所以客户端的很多请求是多余的,仅是维持一个心跳,浪费了带宽.

## **1.2 websocket的方式实现服务端消息推送**



1.什么是socket？什么是websocket？两者有什么区别？websocket是仅仅将socket的概念移植到浏览器中的实现吗？



我们知道，在网络中的两个应用程序（进程）需要全双工相互通信（全双工即双方可同时向对方发送消息），需要用到的就是socket，它能够提供端对端通信，对于程序员来讲，他只需要在某个应用程序的一端（暂且称之为客户端）创建一个socket实例并且提供它所要连接一端（暂且称之为服务端）的IP地址和端口，而另外一端（服务端）创建另一个socket并绑定本地端口进行监听，然后客户端进行连接服务端，服务端接受连接之后双方建立了一个端对端的TCP连接，在该连接上就可以双向通讯了，而且一旦建立这个连接之后，通信双方就没有客户端服务端之分了，提供的就是端对端通信了。我们可以采取这种方式构建一个桌面版的im程序，让不同主机上的用户发送消息。从本质上来说，socket并不是一个新的协议，它只是为了便于程序员进行网络编程而对tcp/ip协议族通信机制的一种封装。



socket传送门：http://blog.csdn.net/luokehua789789/article/details/54378264

### **websocket是html5规范中的一个部分，它借鉴了socket这种思想，为web应用程序客户端和服务端之间（注意是客户端服务端）提供了一种全双工通信机制。同时，它又是一种新的应用层协议，**[websocket协议](http://datatracker.ietf.org/doc/rfc6455/?include_text=1)**是为了提供web应用程序和服务端全双工通信而专门制定的一种应用层协议，通常它表示为：ws://echo.websocket.org/?encoding=text HTTP/1.1，可以看到除了前面的协议名和http不同之外，它的表示地址就是传统的url地址。ws wss  http  https  ws+ssl wss**



Websocket其实是一个新协议，跟HTTP协议基本没有关系，只是为了兼容现有浏览器的握手规范而已，也就是说它是HTTP协议上的一种补充可以通过这样一张图理解

#### websocket具有以下几个方面的优势：

#### （1）建立在 TCP 协议之上，服务器端的实现比较容易。



#### （2）与 HTTP 协议有着良好的兼容性。默认端口也是80和443，并且握手阶段采用 HTTP 协议，因此握手时不容易屏蔽，能通过各种 HTTP 代理服务器。http 80  https 443

#### （3）数据格式比较轻量，性能开销小，通信高效。

#### （4）可以发送文本，也可以发送二进制数据。

#### （5）没有同源限制，客户端可以与任意服务器通信。

#### （6）协议标识符是ws（如果加密，则为wss），服务器网址就是 URL。



### **1.2.1.websocket的通信原理和机制**

既然是基于浏览器端的web技术，那么它的通信肯定少不了http,websocket本身虽然也是一种新的应用层协议，但是它也不能够脱离http而单独存在。具体来讲，我们在客户端构建一个websocket实例，并且为它绑定一个需要连接到的服务器地址，当客户端连接服务端的时候，会向服务端发送一个类似下面的http报文



```yaml
GET /chat HTTP/1.1
Host: server.example.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: x3JJHMbDL1EzLkh9GBhXDw==
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13
Origin: http://example.com
Upgrade: websocket
Connection: Upgrade
```

这个就是Websocket的核心了，告诉Apache、Nginx等服务器：发起的是websocket协议。



```yaml
Sec-WebSocket-Key: x3JJHMbDL1EzLkh9GBhXDw==
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13
```

首先，*Sec-WebSocket-Key*  是一个Base64 encode的值，这个是浏览器随机生成的，告诉服务器：泥煤，不要忽悠窝，我要验证尼是不是真的是Websocket助理。
然后，*Sec_WebSocket-Protocol*  是一个用户定义的字符串，用来区分同URL下，不同的服务所需要的协议。简单理解：今晚我要服务A，别搞错啦~
最后，*Sec-WebSocket-Version* 是告诉服务器所使用的Websocket Draft（协议版本），在最初的时候，Websocket协议还在 Draft 阶段，各种奇奇怪怪的协议都有，而且还有很多期奇奇怪怪不同的东西，什么Firefox和Chrome用的不是一个版本之类的，当初Websocket协议太多可是一个大难题。。不过现在还好，已经定下来啦~大家都使用的一个东西~ 脱水：服务员，我要的是13岁的噢→_→



然后服务器会返回下列东西，表示已经接受到请求， 成功建立Websocket啦！

```yaml
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: HSmrc0sMlYUkAGmm5OPpG2HaGWk=
Sec-WebSocket-Protocol: chat
```



依然是固定的，告诉客户端即将升级的是Websocket协议，而不是mozillasocket，lurnarsocket或者shitsocket。
然后，Sec-WebSocket-Accept 这个则是经过服务器确认，并且加密过后的 Sec-WebSocket-Key。服务器：好啦好啦，知道啦，给你看我的ID CARD来证明行了吧。。
后面的，Sec-WebSocket-Protocol 则是表示最终使用的协议。

返回的状态码为101，表示同意客户端协议转换请求，并将它转换为websocket协议。以上过程都是利用http通信完成的，称之为websocket协议握手(websocket Protocol handshake)，进过这握手之后，客户端和服务端就建立了websocket连接，以后的通信走的都是websocket协议了。所以总结为websocket握手需要借助于http协议，建立连接后通信过程使用websocket协议。同时需要了解的是，该websocket连接还是基于我们刚才发起http连接的那个TCP连接。一旦建立连接之后，我们就可以进行数据传输了，websocket提供两种数据传输：文本数据和二进制数据。



至此，HTTP已经完成它所有工作了，接下来就是完全按照Websocket协议进行了。

基于以上分析，我们可以看到，websocket能够提供低延迟，高性能的客户端与服务端的双向数据通信。它颠覆了之前web开发的请求处理响应模式，并且提供了一种真正意义上的客户端请求，服务器推送数据的模式，特别适合实时数据交互应用开发。



对比前面的http的客户端服务器的交互图可以发现WebSocket方式减少了很多TCP打开和关闭连接的操作，WebSocket的资源利用率高。



二、websocket创建

### websocket的创建和常用的属性方法

以下 API 用于创建 WebSocket 对象。

var Socket = new WebSocket(url, [protocol] );

以上代码中的第一个参数 url, 指定连接的 URL。第二个参数 protocol 是可选的，指定了可接受的子协议。



## WebSocket 常用属性

以下是 WebSocket 对象的属性。假定我们使用了以上代码创建了 Socket 对象：



| 属性                  | 描述                                                         |
| --------------------- | ------------------------------------------------------------ |
| Socket.readyState     | 只读属性 readyState 表示连接状态，可以是以下值：0 - 表示连接尚未建立。1 - 表示连接已建立，可以进行通信。2 - 表示连接正在进行关闭。3 - 表示连接已经关闭或者连接不能打开。 |
| Socket.bufferedAmount | 只读属性 bufferedAmount 已被 send() 放入正在队列中等待传输，但是还没有发出的 UTF-8 文本字节数。 |



CONNECTING：值为0，表示正在连接。

OPEN：值为1，表示连接成功，可以通信了。

CLOSING：值为2，表示连接正在关闭。

CLOSED：值为3，表示连接已经关闭，或者打开连接失败。



可以看到，当onopen触发时，对应的额就是readyState的OPEN状态，不包含OPENING；onclose触发时，对应的就是CLOSED状态，不包含CLOSING状态。



## WebSocket 事件



以下是 WebSocket 对象的相关事件。假定我们使用了以上代码创建了 Socket 对象：

| 事件    | 事件处理程序     | 描述                       |
| ------- | ---------------- | -------------------------- |
| open    | Socket.onopen    | 连接建立时触发             |
| message | Socket.onmessage | 客户端接收服务端数据时触发 |
| error   | Socket.onerror   | 通信发生错误时触发         |
| close   | Socket.onclose   | 连接关闭时触发             |

## WebSocket 方法

以下是 WebSocket 对象的相关方法。假定我们使用了以上代码创建了 Socket 对象：

| 方法           | 描述             |
| -------------- | ---------------- |
| Socket.send()  | 使用连接发送数据 |
| Socket.close() | 关闭连接         |



三  springboot整合 rabbitmq和websocket

3.1 导入依赖

```yaml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--rabbit MQ-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

        <!--WEB SOCKET-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>

        <!--小辣椒lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- json的依赖-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.75</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
```



3.2 配置yml文件

```yaml
spring:
  rabbitmq:
    host: 162.14.64.72
    port: 5672
    username: test
    password: test
    virtual-host: /test
    publisher-returns: true
    template:
      mandatory: true
    publisher-confirms: true
```



定义常量

```yaml
package com.qf.config.mq2;

/**
 * @author ：_my
 * @date ：Created in 2021/12/23 10:05
 * @description：定义mq
 * @modified By：`
 * @version: 1.0
 */

public class RabbitConstant {
    //交换机名称
    public final static String EXCHANGE = "exchange_test";
    //队列
    public final static String QUEUE_TRANSACTION = "queue_transaction";
    public final static String QUEUE_CONTRACT = "queue_contract";
    public final static String QUEUE_QUALIFICATION = "queue_qualification";
    //路由key
    public final static String RK_TRANSACTION = "transaction";
    public final static String RK_CONTRACT = "contract";
    public final static String RK_QUALIFICATION = "qualification";
}
```



3.3 注入rabbitMQ

```java
package com.qf.config.mq2;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author ：_my
* @date ：Created in 2021/12/23 10:06
* @description：初始化
* @modified By：`
* @version: 1.0
*/
@Configuration
public class RabbitMqConfig {
    
    /**
    * 声明队列
    *
    * @return
    */
    @Bean
    public Queue queueTransaction() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_TRANSACTION, true);
    }
    
    @Bean
    public Queue queueContract() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_CONTRACT, true);
    }
    
    @Bean
    public Queue queueQualification() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_QUALIFICATION, true);
    }
    
    /**
    * 声明交互器
    *
    * @return
    */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RabbitConstant.EXCHANGE);
    }
    
    /**
    * 绑定
    *
    * @return
    */
    @Bean
    public Binding bindingTransaction() {
        return BindingBuilder.bind(queueTransaction()).to(directExchange()).with(RabbitConstant.RK_TRANSACTION);
    }
    
    /**
    * 绑定
    *
    * @return
    */
    @Bean
    public Binding bindingContract() {
        return BindingBuilder.bind(queueContract()).to(directExchange()).with(RabbitConstant.RK_CONTRACT);
    }
    
    /**
    * 绑定
    *
    * @return
    */
    @Bean
    public Binding bindingQualification() {
        return BindingBuilder.bind(queueQualification()).to(directExchange()).with(RabbitConstant.RK_QUALIFICATION);
    }
}
```

创建发送消息的格式

```java
package com.qf.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageVo implements Serializable {

    private String receiveUserId;

    private String routingKey;
}
```







3.4 编写pulisher

```java
package com.qf.config.mq2;

import com.alibaba.fastjson.JSONObject;
import com.qf.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
* @author ：_my
* @date ：Created in 2021/12/23 10:08
* @description：生产者
* @modified By：`
* @version: 1.0
*/
@Component
@Slf4j
public class Sender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    //消息发送确认回调方法
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息发送成功:" + correlationData);
    }
    
    //消息发送失败回调方法
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息发送失败:" + new String(message.getBody()));
    }
    
    /**
    * 发送消息，不需要实现任何接口，供外部调用
    *
    * @param messageVo
    */
    public void send(MessageVo messageVo) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE, messageVo.getRoutingKey(), JSONObject.toJSON(messageVo).toString(), correlationId);
    }
}
```



3.5 编写消费者

```java
package com.qf.config.mq2;

/**
* @author ：_my
* @date ：Created in 2021/12/23 10:11
* @description：消费者
* @modified By：`
* @version: 1.0
*/
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.io.IOException;


@Configuration
@EnableRabbit
@Slf4j
public class ConsumerConfig implements RabbitListenerConfigurer {
    
    @Autowired
    private ConnectionFactory connectionFactory;
    
    @Autowired
    private WebSocketServerEndpoint webSocketServerEndpoint;
    
    @Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }
    
    @Bean
    public SimpleMessageListenerContainer messageContainer(@Qualifier("queueTransaction") Queue transaction, @Qualifier("queueContract") Queue contract, @Qualifier("queueQualification") Queue qualification) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(transaction, contract, qualification);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                log.info("receive msg : " + new String(body));
                try {
                    webSocketServerEndpoint.sendMessageToAll(new String(body));
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息成功消费
                } catch (IOException e) {
                    log.error("消息推送前台出错：" + e.getMessage() + "/r/n重新发送");
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); //重新发送
                }
            }
        });
        return container;
    }
    
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(handlerMethodFactory());
    }
}
```

3.6 注入websocket

```java
package com.qf.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
    
}
```



3.7 编写websocket 服务器端

```java
package com.qf.config.mq2;

/**
* @author ：_my
* @date ：Created in 2021/12/23 10:13
* @description：websocket
* @modified By：`
* @version: 1.0
*/
import com.alibaba.fastjson.JSONObject;
import com.qf.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* ServerEndpoint
* <p>
* 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
  但在springboot中连容器都是spring管理的。
* <p>
* 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
  所以可以用一个静态set保存起来。

	websocket  是使用的wss协议需要配置路径映射
 *  通过类似GET请求方式传递参数的方法（服务端采用第二种方法"WebSocketHandler"实现）
 *  websocket = new WebSocket("ws://127.0.0.1:18080/testWebsocket?id=23&name=Lebron");
*/
@ServerEndpoint("/ws/yxd/{userId}") //WebSocket客户端建立连接的地址
@Component
@Slf4j
public class WebSocketServerEndpoint {
    
    /**
    * 存活的session集合（使用线程安全的map保存）
    */
    private static Map<String, Session> livingSessions = new ConcurrentHashMap<>();
    
    /**
    * 建立连接的回调方法
    *
    * @param session 与客户端的WebSocket连接会话
    * @param userId  用户名，WebSocket支持路径参数
    */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        livingSessions.put(session.getId(), session);
        log.info(userId + "进入连接");
    }
    
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        log.info(userId + " : " + message);
    	sendMessageToAll(userId + " : " + message);
    }
    
    
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        log.error(error.getStackTrace() + "");
    }
    
    
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        livingSessions.remove(session.getId());
        log.info(userId + " 关闭连接");
    }
    
    /**
    * 单独发送消息
    *
    * @param session
    * @param message
    */
        public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * 群发消息
    *
    * @param message
    */
    public void sendMessageToAll(String message) {
        MessageVo messageVo = JSONObject.parseObject(message, MessageVo.class);
        livingSessions.forEach((sessionId, session) -> {
            //发给指定的接收用户
            //            if (userId.equals(messageVo.getReceiveUserId())) {
            sendMessage(session, message);
            //            }
        });
    }
    
}
```

3.8 编写客服端

```yaml
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div id="msgs">这是消息</div>

</body>

<script>
        
        var ws = null;
		var xt = 0;
		var url = "ws://localhost:8080/ws/yxd/1";
		
		function createWebSocket(){
			try {
				   ws = new WebSocket(url);
			   } catch (e) {
				   reconnect();
			   }
		
		}
		
		function reconnect(){
			createWebSocket();
		
		}
		
		if(ws == null ){
			reconnect();
			
		}
		
		
		//申请一个WebSocket对象，参数是服务端地址，同http协议使用http://开头一样，WebSocket协议的url使用ws://开头，另外安全的WebSocket协议使用wss://开头
        ws.onopen = function () {
            //当WebSocket创建成功时，触发onopen事件
            console.log("open");
        }
        ws.onmessage = function (e) {
            //当客户端收到服务端发来的消息时，触发onmessage事件，参数e.data包含server传递过来的数据
            console.log(e.data);
        }
        ws.onclose = function (e) {
            //当客户端收到服务端发送的关闭连接请求时，触发onclose事件
            console.log("close");
			reconnect();
			
        }
        ws.onerror = function (e) {
            //如果出现连接、处理、接收、发送数据失败的时候触发onerror事件
            console.log(error);
        }  

		
		//心跳 防止长时间没有传输信息 断链接
	 /** setInterval(function(){
		  if(xt >= 2){
			  console.log("重新链接");
			  reconnect();
			  xt = 0;
		  }else{
			  xt++;
		  }
		  
	  },5000)*/

		
        
    </script>
</html>
```

3.9 编写测试类

```yaml
package com.qf.controller;

import com.qf.config.mq2.Sender;
import com.qf.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：_my
 * @date ：Created in 2021/12/22 10:29
 * @description：测试websocket
 * @modified By：`
 * @version: 1.0
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    Sender sender;
    /**
     * 接口：
     * 调用向队列中发送消息的方法
     */
    @RequestMapping("senderMsg")
    public void senderMsg() {
        MessageVo messageVo = new MessageVo();
        messageVo.setReceiveUserId("1");
        messageVo.setRoutingKey("contract");
        sender.send(messageVo);
    }

}
```



测试结果

![img](10-WebSocket+mq(消息实时整合).assets/1640239365651-e4b38b1c-9f17-493f-bc60-713e283d80c0.png)