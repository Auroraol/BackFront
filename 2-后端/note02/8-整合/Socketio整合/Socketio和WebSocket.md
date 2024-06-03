# WebSocket和Socket.IO

选择使用 [Socket.IO](http://socket.io/) 还是 WebSocket 取决于你的应用程序需求和偏好。

1. **WebSocket**:
   - WebSocket 是 HTML5 提供的一种在单个 TCP 连接上进行全双工通信的协议。
   - WebSocket 提供了原始的套接字接口，可以直接与服务器通信，而不需要任何中间服务器。
   - 使用 WebSocket 可以实现实时性很高的双向通信，适合需要高度互动性和实时性的应用程序，比如在线游戏、实时聊天应用等。
2. **[Socket.IO](http://socket.io/)**:
   - [Socket.IO](http://socket.io/) 是建立在 WebSocket 之上的一个库，它封装了 WebSocket，并提供了更多的功能和兼容性。
   - [Socket.IO](http://socket.io/) 提供了可靠性更强的双向通信，并且在底层自动处理了一些兼容性问题，如自动降级到轮询等。
   - [Socket.IO](http://socket.io/) 不仅支持 WebSocket，还支持其他传输方式，如轮询、长轮询等，这使得它在不同环境下具有更好的兼容性和稳定性。
   - 使用 [Socket.IO](http://socket.io/) 可以更容易地构建跨平台的实时应用程序，而不需要太多的关注底层通信协议的细节。

如果你的应用程序需要高度的实时性和互动性，并且你希望更轻松地处理跨浏览器和跨平台的兼容性问题，那么使用 [Socket.IO](http://socket.io/) 是一个不错的选择。但如果你只需要简单的双向通信，并且更关注于原始的 WebSocket 功能，那么直接使用 WebSocket 也是可以的。

![image-20240601114703600](Socketio%E5%92%8CWebSocket.assets/image-20240601114703600.png)

# SocketIO基础概念图

SocketIO、namespace（命名空间）、room（房间）的关系如下：

![请添加图片描述](https://raw.githubusercontent.com/Auroraol/Drawing-bed/main/img/202406011641499.png)

SocketIO广播是以namespace或者room为维度的，具体如下：

> 如果不定义namespace，默认是/
> 如果定义了namespace，没有定义room，房间默认的名字和namespace一样。

# SpringBoot整合netty-socketio

## 后端

<img src="Socketio%E5%92%8CWebSocket.assets/image-20240601164743542.png" alt="image-20240601164743542" style="zoom:80%;" />

### 1、maven依赖

```xml
<!-- netty-socketio-->
<dependency>
    <groupId>com.corundumstudio.socketio</groupId>
    <artifactId>netty-socketio</artifactId>
    <version>2.0.9</version>
</dependency>
```

### 2、socketIO的yml配置

```yaml
# netty-socketio 配置
socketio:
  host: 0.0.0.0
  # SocketIO端口
  port: 9974
  #  namespaces: /Chat, /Test
  namespaces: /Chat, /Test
  # 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
  maxFramePayloadLength: 1048576
  # 设置http交互最大内容长度
  maxHttpContentLength: 1048576
  # socket连接数大小（如只监听一个端口boss线程组为1即可）
  bossCount: 1
  workCount: 100
  allowCustomRequests: true
  # 协议升级超时时间（毫秒），默认10秒。HTTP握手升级为ws协议超时时间
  upgradeTimeout: 1000000
  # Ping消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
  pingTimeout: 6000000
  # Ping消息间隔（毫秒），默认25秒。客户端向服务器发送一条心跳消息间隔
  pingInterval: 25000
```

### 3、socketIO的config代码

#### SocketIoConfig

```java
package com.lfj.blog.common.chatSocketio.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:43
 */
//
@Configuration
public class SocketIoConfig {

	@Autowired
	SocketIOProperties socketIOProperties;

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config =
				new com.corundumstudio.socketio.Configuration();
		config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
		// 配置端口
		config.setPort(socketIOProperties.getPort());
		// 开启Socket端口复用
		com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();
		socketConfig.setReuseAddress(true);
		config.setSocketConfig(socketConfig);
		// 连接数大小
		config.setWorkerThreads(socketIOProperties.getWorkCount());
		// 允许客户请求
		config.setAllowCustomRequests(socketIOProperties.isAllowCustomRequests());
		// 协议升级超时时间(毫秒)，默认10秒，HTTP握手升级为ws协议超时时间
		config.setUpgradeTimeout(socketIOProperties.getUpgradeTimeout());
		// Ping消息超时时间(毫秒)，默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
		config.setPingTimeout(socketIOProperties.getPingTimeout());
		// Ping消息间隔(毫秒)，默认25秒。客户端向服务器发送一条心跳消息间隔
		config.setPingInterval(socketIOProperties.getPingInterval());
		//允许最大帧长度,防止他人利用大数据来攻击服务器
		config.setMaxFramePayloadLength(socketIOProperties.getMaxFramePayloadLength());
		//允许下最大内容
		config.setMaxHttpContentLength(socketIOProperties.getMaxHttpContentLength());
		final SocketIOServer server = new SocketIOServer(config);
		//添加命名空间
		Optional.ofNullable(socketIOProperties.getNamespaces()).ifPresent(nss ->
				Arrays.stream(nss).forEach(server::addNamespace));
		return server;

	}

	/**
	 * 注入OnConnect，OnDisconnect，OnEvent注解。 不写的话Spring无法扫描OnConnect，OnDisconnect等注解
	 */
	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
		return new SpringAnnotationScanner(socketIOServer);
	}
}
```

#### SocketIoProperties

```java
package com.lfj.blog.common.chatSocketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:28
 */
@Component
@ConfigurationProperties(prefix = "socketio")
@Data
public class SocketIoProperties {
	private String host;
	private int port;
	private String[] namespaces;
	private int maxFramePayloadLength;
	private int maxHttpContentLength;
	private int bossCount;
	private int workCount;
	private boolean allowCustomRequests;
	private int upgradeTimeout;
	private int pingTimeout;
	private int pingInterval;
}
```

### 4、SocketIoServerRunner启动类

SocketIoServerRunner

```java
package com.lfj.blog.common.chatSocketio;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.lfj.blog.common.chatSocketio.config.SocketIoProperties;
import com.lfj.blog.utils.SpringContextUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:48
 * 定义启动类
 * 这里为每个命名空间注册一个事件处理Handler, 规则为bean的name为命名空间名称+MessageHandler。
 */
@Component
//@Order(1)
@Log4j2
public class SocketIoServerRunner implements CommandLineRunner {
	@Autowired
	SocketIoProperties socketIOProperties;

	@Autowired(required = false)
	private SocketIOServer socketIOServer;

	@Override
	public void run(String... args) throws Exception {
		if (socketIOServer != null) {
			Optional.ofNullable(socketIOProperties.getNamespaces()).ifPresent(nss ->

					Arrays.stream(nss).forEach(ns -> {
						//获取命名空间
						SocketIONamespace socketIONamespace = socketIOServer.getNamespace(ns);
						//获取期待的类名
						String className = ns.substring(1) + "MessageEventHandler";
						try {
							// 包名.类名
							Object bean = SpringContextUtil.		getBean(Class.forName("com.lfj.blog.common.chatSocketio.socketHandler.impl." + className));

							Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
						} catch (Exception e) {
							log.error("Error loading event handler for namespace: " + ns, e);
						}
					}));
			socketIOServer.start();
		}
	}
}
```

### 5、用户缓存信息ClientCache

```java
package com.lfj.blog.common.chatSocketio.cache;

/**
 * @Author: LFJ
 * @Date: 2024-06-01 16:15
 * 存储用户的缓存信息
 */

import com.corundumstudio.socketio.SocketIOClient;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCache {

	//用于存储用户的socket缓存信息
	private static ConcurrentHashMap<String, HashMap<UUID, SocketIOClient>> concurrentHashMap
			= new ConcurrentHashMap<>();

	//根据用户id和session删除用户某个session信息
	public static void deleteSessionClientByUserId(String userId, UUID sessionId) {
		concurrentHashMap.get(userId).remove(sessionId);
	}

	//保存用户信息
	public static void saveClient(String userId, UUID sessionId, SocketIOClient socketIOClient) {
		HashMap<UUID, SocketIOClient> sessionIdClientCache = concurrentHashMap.get(userId);
		if (sessionIdClientCache == null) {
			sessionIdClientCache = new HashMap<>();
		}
		sessionIdClientCache.put(sessionId, socketIOClient);
		concurrentHashMap.put(userId, sessionIdClientCache);
	}

	//获取用户信息
	public HashMap<UUID, SocketIOClient> getUserClient(String userId) {
		return concurrentHashMap.get(userId);
	}

	//删除用户缓存信息
	public void deleteUserCacheByUserId(String userId) {
		concurrentHashMap.remove(userId);
	}
}
```

### 6、自定义请求类(这里没有到)

#### MsgTypeEnum

```java
package com.lfj.blog.common.chatSocketio.vo;

/**
 * 消息类型
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/4/6 19:53
 */
public enum MsgTypeEnum {

	/**
	 * 全部发送
	 */
	ALL("00"),
	/**
	 * 上线
	 */
	ONLINE("01"),
	/**
	 * 下线
	 */
	OFFLINE("02"),
	/**
	 * 指定发送
	 */
	SINGLE("03");

	private String value;

	MsgTypeEnum(String type) {
		value = type;
	}

	public String getValue() {
		return value;
	}

}
```

#### MessageDto

```java
package com.lfj.blog.common.chatSocketio.vo;

import java.io.Serializable;

/**
 * MessageDto
 */
public class MessageDto implements Serializable {

	/**
	 * 源客户端用户名
	 */
	private String sourceUserName;

	/**
	 * 目标客户端用户名
	 */
	private String targetUserName;

	/**
	 * 消息类型
	 */
	private String msgType;

	/**
	 * 消息内容
	 */
	private String msgContent;

	/**
	 * 空构造方法
	 */
	public MessageDto() {
	}

	/**
	 * 构造方法
	 *
	 * @param sourceUserName
	 * @param targetUserName
	 * @param msgType
	 * @param msgContent
	 */
	public MessageDto(String sourceUserName, String targetUserName, String msgType, String msgContent) {
		this.sourceUserName = sourceUserName;
		this.targetUserName = targetUserName;
		this.msgType = msgType;
		this.msgContent = msgContent;
	}

	public String getSourceUserName() {
		return sourceUserName;
	}

	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}

	public String getTargetUserName() {
		return targetUserName;
	}

	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
}

```

使用

```java
import com.corundumstudio.socketio.annotation.OnEvent;

@OnEvent("sendMsg")
	public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, MessageDto messageDto) {
		if (messageDto != null) {
			// 全部发送
			clientMap.forEach((key, value) -> {
				if (value != null) {
					socketIOServer.getClient(value).sendEvent("receiveMsg", messageDto);
				}
			});
		}
	}
```



### 7、定义事件处理接口和默认实现

#### 定义 IEventHandler 接口

```java
package com.lfj.blog.common.chatSocketio.socketHandler;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:47
 * 定义事件处理接口和默认实现
 * 连接的处理使用前端传入Authorization,可以传入token,做校验
 */

import com.corundumstudio.socketio.SocketIOClient;
import com.lfj.blog.common.chatSocketio.cache.ClientCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public interface IEventHandler {

	void onConnect(SocketIOClient client);

	void onDisConnect(SocketIOClient client);


	default void connect(@NotNull SocketIOClient client) {


		//因为定义的用户的参数为Authorization，你也可以定义其他名称 客户端请求 http://localhost:9974?Authorization=12345

		//下面两种是加了命名空间的，请求对应命名空间的方法（就类似进了不同的房间玩游戏）
		//客户端请求 http://localhost:9974/Chat?Authorization=12345
		//客户端请求 http://localhost:9974/Test?Authorization=12345
		String token = client.getHandshakeData().getSingleUrlParam("Authorization");
		//同一个页面sessionid一样的
		UUID sessionId = client.getSessionId();

		if (ObjectUtils.isEmpty(token)) {
			System.err.println("客户端" + sessionId + "建立websocket连接失败，Authorization不能为null");
			client.disconnect();
			return;
		} else {
			System.out.println("客户端" + sessionId + "建立websocket连接成功");
			//保存用户的信息在缓存里面  //将token和clientId对应 方便推送时候使用
			ClientCache.saveClient(token, sessionId, client);
		}
	}

	/**
	 * 关闭连接
	 *
	 * @param client 客户端的SocketIO
	 */
	default void disconnect(@NotNull SocketIOClient client) {
		//定义用户的参数为Authorization
		String token = client.getHandshakeData().getSingleUrlParam("Authorization");

		//sessionId,页面唯一标识
		UUID sessionId = client.getSessionId();

		//clientCache.deleteUserCacheByUserId(userId);
		//只会删除用户某个页面会话的缓存，不会删除该用户不同会话的缓存，
		// 比如：用户同时打开了谷歌和QQ浏览器，当你关闭谷歌时候，只会删除该用户谷歌的缓存会话
		ClientCache.deleteSessionClientByUserId(token, sessionId);
		System.out.println("客户端" + sessionId + "断开websocket连接成功");
	}
}
```

#### 实现

##### ChatMessageEventHandler

命名空间为 /Chat

```java
package com.lfj.blog.common.chatSocketio.socketHandler.impl;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:49
 * 几种消息接收和发送模式
 */

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.lfj.blog.common.chatSocketio.socketHandler.IEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnClass(SocketIOServer.class)
@Slf4j
public class ChatMessageEventHandler implements IEventHandler {
	private static int testPushCount = 0;
	private final SocketIOServer socketIOServer;
	private String namespace = "/Chat";

	public ChatMessageEventHandler(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}

	public static String generateRandomString(int length) {
		// 可以根据需求修改生成的字符集
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 从字符集中随机选择一个字符
			int randomIndex = random.nextInt(characters.length());
			sb.append(characters.charAt(randomIndex));
		}
		return sb.toString();
	}

	@Override
	@OnConnect
	public void onConnect(SocketIOClient client) {
		connect(client);
	}

	@Override
	@OnDisconnect
	public void onDisConnect(SocketIOClient client) {
		disconnect(client);
	}

	@OnEvent(value = "aaaa")
	public void onEvent(@NotNull SocketIOClient client, AckRequest request, String data) {
		System.out.println(data);
		UUID sessionId = client.getSessionId();
		socketIOServer.getNamespace(namespace).
				getClient(sessionId).sendEvent("bbbb", "点对点消息的返回" + Math.random());
	}

	/**
	 * 测试无限推送
	 */
	@OnEvent(value = "testPush")
	public void onTestPushEvent(@NotNull SocketIOClient client, AckRequest request, String data) {
		UUID sessionId = client.getSessionId();
		Runnable runnable = () -> {
			testPushCount++;
			int thisTestPushCount = testPushCount;
			for (; ; ) {
				if (thisTestPushCount < testPushCount) {
					break;
				}
				socketIOServer.getNamespace(namespace).getClient(sessionId).
						sendEvent("testPush", generateRandomString(1024 * 200));
				try {
					TimeUnit.MILLISECONDS.sleep(900);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * 测试加入房间
	 */
	@OnEvent(value = "joinRoom")
	public void onTestJoinRoomEvent(@NotNull SocketIOClient client, AckRequest request, String data) {
		client.leaveRoom(data);
		client.joinRoom(data);
	}

	/**
	 * 测试房间发送信息(类似于订阅式广播消息)
	 */
	@OnEvent(value = "testRoom")
	public void onTestRoomEvent(SocketIOClient client, AckRequest request, String data) {
		socketIOServer.getNamespace(namespace).getRoomOperations("room1").sendEvent("testRoom", "房间里的消息" + Math.random());
	}

	/**
	 * 测试发送广播消息
	 */
	@OnEvent(value = "testBroadcast")
	public void onTestBroadcastEvent(SocketIOClient client, AckRequest request, String data) {
		socketIOServer.getNamespace(namespace).getBroadcastOperations().sendEvent("testBroadcast", "广播的消息" + Math.random());
	}

}


```

##### TestMessageEventHandler

 命名空间为/Test

```java
package com.lfj.blog.common.chatSocketio.SocketHandler2;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 22:08
 */

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.lfj.blog.common.chatSocketio.socketHandler.IEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
@ConditionalOnClass(SocketIOServer.class)
@Slf4j
public class TestMessageEventHandler implements IEventHandler {
	private static int testPushCount = 0;
	private final SocketIOServer socketIOServer;
	private String namespace = "/test";

	public TestMessageEventHandler(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}

	public static String generateRandomString(int length) {
		// 可以根据需求修改生成的字符集
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 从字符集中随机选择一个字符
			int randomIndex = random.nextInt(characters.length());
			sb.append(characters.charAt(randomIndex));
		}
		return sb.toString();
	}

	@Override
	@OnConnect
	public void onConnect(SocketIOClient client) {
		connect(client);
	}

	@Override
	@OnDisconnect
	public void onDisConnect(SocketIOClient client) {
		disconnect(client);
	}

	@OnEvent(value = "aaaa")
	public void onEvent(SocketIOClient client, AckRequest request, String data) {
		log.debug("发来消息：" + data);
		UUID sessionId = client.getSessionId();
		socketIOServer.getNamespace(namespace).getClient(sessionId).sendEvent("bbbb", "点对点消息的返回" + Math.random());
	}

	/**
	 * 测试无限推送
	 */
	@OnEvent(value = "testPush")
	public void onTestPushEvent(SocketIOClient client, AckRequest request, String data) {
		UUID sessionId = client.getSessionId();
		Runnable runnable = () -> {
			testPushCount++;
			int thisTestPushCount = testPushCount;
			for (; ; ) {
				if (thisTestPushCount < testPushCount) {
					break;
				}
				socketIOServer.getNamespace(namespace).getClient(sessionId).
						sendEvent("testPush", generateRandomString(1024 * 200));
				try {
					TimeUnit.MILLISECONDS.sleep(900);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * 测试加入房间
	 */
	@OnEvent(value = "joinRoom")
	public void onTestJoinRoomEvent(SocketIOClient client, AckRequest request, String data) {
		client.leaveRoom(data);
		client.joinRoom(data);
	}

	/**
	 * 测试房间发送信息(类似于订阅式广播消息)
	 */
	@OnEvent(value = "testRoom")
	public void onTestRoomEvent(SocketIOClient client, AckRequest request, String data) {
		socketIOServer.getNamespace(namespace).getRoomOperations("room1").sendEvent("testRoom", "房间里的消息" + Math.random());
	}

	/**
	 * 测试发送广播消息
	 */
	@OnEvent(value = "testBroadcast")
	public void onTestBroadcastEvent(SocketIOClient client, AckRequest request, String data) {
		socketIOServer.getNamespace(namespace).getBroadcastOperations().sendEvent("testBroadcast", "广播的消息" + Math.random());
	}

}
```

### 8、常用方法

> 其他的一些测试我写在下面的代码上，自己去测试才能更好的理解

#### 1、加入房间

```java
    //加入房间
    @OnEvent("joinRoom")
    public void joinRooms(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {

        client.joinRoom(data);

        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("加入房间","成功");
        }

    }
```

#### 2、离开房间

```java
    //离开房间
    @OnEvent("leaveRoom")
    public void leaveRoom(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {

        client.leaveRoom(data);

        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("离开房间","成功");
        }

    }
```

#### 3、获取用户所有房间

```java
    //获取该用户所有房间
    @OnEvent("getUserRooms")
    public void getUserRooms(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {

        String userId = client.getHandshakeData().getSingleUrlParam("userId");

        Set<String> allRooms = client.getAllRooms();

        for (String room:allRooms){
            System.out.println("房间名称："+room);
        }

        log.info("服务器收到消息,客户端用户id：{} | 客户发送的消息：{} | 是否需要返回给客户端内容:{} ",userId,data,ackRequest.isAckRequested());


        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("你好","哈哈哈");
        }


    }
```

#### 4、发送消息给指定的房间

```java
   @OnEvent("sendRoomMessage")
    public void sendRoomMessage(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {


        String userId = client.getHandshakeData().getSingleUrlParam("userId");


        Set<String> allRooms = client.getAllRooms();

        for (String room:allRooms){
            log.info("房间：{}",room);
            //发送给指定空间名称以及房间的人，并且排除不发给自己
            socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);

            //发送给指定空间名称以及房间的人，包括自己
            //socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message", data);;
        }



        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("发送消息到指定的房间","成功");
        }

    }
```

#### 5、广播消息给指定的Namespace下所有客户端

```java
//广播消息给指定的Namespace下所有客户端
    @OnEvent("sendNamespaceMessage")
    public void sendNamespaceMessage(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {


        socketIoServer.getNamespace("/socketIO").getBroadcastOperations().sendEvent("message",client, data);;


        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("发送消息到指定的房间","成功");
        }

    }
```

#### 6、点对点发送

```java
    	
	//点对点
    @OnEvent("sendMsg")
    public void sendMessageOne(String userId) throws JsonProcessingException {


        HashMap<UUID, SocketIOClient> userClient = clientCache.getUserClient(userId);

        for (UUID sessionId : userClient.keySet()) {
            socketIoServer.getNamespace("/socketIO").getClient(sessionId).sendEvent("message", "这是点对点发送");
        }

    }
```

## 前端

### 例子

```js
  socket = io("http://localhost:9974/Chat", {
    query: "Authorization=刘丰洁",
    // autoConnect: false,
    transports: ["websocket"],
  });

  // ==============以下使用命名空间chat========================

  //监听广播消息
  socket.on("testNamespace", function (data) {
    console.log("接收到消息:" + data);
  });

  //监听点对点消息
  socket.on("bbbb", function (data) {
    //....收到消息后具体操作
    //  console.log(data);
    console.log(data);
  });

  //监听后端无限推送的点对点消息
  socket.on("testPush", function (data) {
    console.log("接收到消息的次数:" + ++i);
  });

  //监听加入房间的反馈
  socket.on("testJoinRoom", function (data) {
    console.log("接收到消息:" + data);
  });

  //监听房间消息
  socket.on("testRoom", function (data) {
    console.log("接收到消息:" + data);
  });

  //监听广播消息
  socket.on("testBroadcast", function (data) {
    console.log("接收到消息:" + data);
  });
};

// 发送消息
const send1 = () => {
  socket.emit("aaaa", "aaaaaa");
};

// 触发无限推送
const send2 = () => {
  socket.emit("testPush", "begin");
};

// 发送加入房间消息
const send3 = () => {
  socket.emit("joinRoom", "room1");
};

// 发送房间消息
const send4 = () => {
  socket.emit("testRoom", "testRoomData");
};

// 发送广播消息
const send5 = () => {
  socket.emit("testBroadcast", "testBroadCastData");
};
```

### 封装:crossed_swords:

```ts
import { io } from 'socket.io-client'

// // 连接服务器
let socketHttpUrl = 'http://localhost:9974/chat'    // 命名空间为/Chat
// let socketHttpUrl = 'http://localhost:9974/Test' // 命名空间为/Test
// let socketHttpUrl = 'http://localhost:9974'      // 无命名空间

const socket = io(socketHttpUrl, {
    query: "Authorization=刘丰洁",  // 参数 // 改成动态的
    autoConnect: false,         // 是否自动连接
      reconnection: true,       // 是否自动重新连接
    reconnectionAttempts: 3,    // 放弃之前尝试重新连接的次数，默认值 Infinity
    reconnectionDelay: 3000,    // 每次连接等待时间 默认值 1000
    transports: ["websocket", "polling"], // 优先使用websocket连接方式
  });

//监听器，不论事件名称是什么，都会执行相同的回调函数
socket.onAny((event, ...args) => {
    // console.log(event, args);
});

//当客户端与服务器成功建立连接时，Socket.IO 客户端会触发名为 'connect' 的事件
socket.on('connect', () => { }) //连接socket

socket.io.on("reconnect", (attempt) => {
    ElMessage({
        message: '重新连接成功',
        type: 'success',
    })
});

socket.io.on('error', (error) => {
    ElMessage.error('网络连接错误' + error)
})

export {
    socket
}

/**
 * 使用
// socket.connect();     // 手动触发连接
// socket.disconnect()   // 手动断开连接
// 接收消息   socket.on(事件的名称, (data...)=>{ //回调数据 })
// 发送消息   socket.emit(事件的名称, 发送的数据, (data...)=>{//回调数据}) //发送的数据和回调参数可省
 */


```



# 总结

**//后端**

```java
@OnEvent(value = "aaaa")
public void onEvent(@NotNull SocketIOClient client, AckRequest ackRequest, String data) {

    //回调
    if (ackRequest.isAckRequested()) {
        //返回给客户端，说我接收到了
        ackRequest.sendAckData("你好", "哈哈哈");  // 参数2是可变参数
    }

    //
    System.out.println(data);
    UUID sessionId = client.getSessionId();
    socketIOServer.getNamespace(namespace).
        // 发送
        getClient(sessionId).sendEvent("bbbb", "点对点消息的返回" + Math.random());  // 参数2是可变参数
}
```

**//前端**

```js
// 发送  
socket.emit("aaaa", "aaaaaa", (data) => {
	console.error(data);
});


// 接收
socket.on("bbbb", function (data) {
    //....收到消息后具体操作
    console.error(data);
});

/**
 * 使用
// socket.connect();     // 手动触发连接
// socket.disconnect()   // 手动断开连接
// 接收消息   socket.on(事件的名称, (data...)=>{ //回调数据 })
// 发送消息   socket.emit(事件的名称, 发送的数据, (data...)=>{//回调数据}) // 参数2参数3为可选
 */
```



