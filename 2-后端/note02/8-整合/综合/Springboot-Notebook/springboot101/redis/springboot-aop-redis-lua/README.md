# springboot-aop-redis-lua
springboot-aop-redis-lua  实现的分布式限流方案

## springboot + redis整合

###  redis

具体细节参考: 

<img src="README.assets/image-20240119153134142.png" alt="image-20240119153134142" style="zoom:67%;" />

#### 更改Redis配置文件

1. bind配置请注释掉

2. 保护模式设置为no

3. Linux系统的防火墙设置

4. Redis服务器的IP地址和密码是否正确

5. 忘记写访问redis的服务端口号和auth密码

6. 相互ping通

   ![image-20231010193546859](README.assets/image-20231010193546859.png)

#### 启动

```shell
root@lfj-virtual-machine:/myredis# redis-server /myredis/redis.conf 
root@lfj-virtual-machine:/myredis# redis-cli -a 741106 -p 6379 -h 192.168.200.134 --raw
```

注意: 

+ 192.168.200.134为linux的ip地址
+ 741106为 redis密码
+ 6379为redis端口号

### springboot

#### 配置文件

```properties
# ===========================redis单机===========================
spring.redis.database=0
#修改为自己真实IP
spring.redis.host=192.168.200.134  //redis的ip地址
spring.redis.port=6379
spring.redis.password=741106
spring.redis.lettuce.pool.max-active=8
spring.redis.1ettuce.pool.max-wait=-1ms
spring.redis.1ettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0
```

#### pom

部分

```xml
 <!-- SpringBoot 与Redis整合依赖 -->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
 <dependency>
     <groupId>org.apache.commons</groupId>
     <artifactId>commons-pool2</artifactId>
 </dependency>
```

#### config

两种方法都行

```java
package com.springboot101.limit.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 13:54
 */
@Configuration
public class RedisConfig {

	/**
	 * *redis序列化的工具定置类，下面这个请一定开启配置
	 * *127.0.0.1:6379> keys *
	 * *1) “ord:102” 序列化过
	 * *2)“\xaclxedlxeelx05tixeelaord:102” 野生，没有序列化过
	 * *this.redisTemplate.opsForValue(); //提供了操作string类型的所有方法
	 * *this.redisTemplate.opsForList();// 提供了操作List类型的所有方法
	 * *this.redisTemplate.opsForset(); //提供了操作set类型的所有方法
	 * *this.redisTemplate.opsForHash(); //提供了操作hash类型的所有方认
	 * *this.redisTemplate.opsForZSet(); //提供了操作zset类型的所有方法
	 * param LettuceConnectionFactory
	 * return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		//pom导入对应的依-->导包
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		// 设置key序列化方式string
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		//	// 源代码private RedisSerializer<String> stringSerializer = RedisSerializer.string();
		// 设置value的序列化方式json，使用GenericJackson2JsonRedisSerializer替换默认序列化
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
 }
//@Configuration
//public class RedisConfig {
//
//	//GenericJackson2JsonRedisSerializer
//	@Bean
//	@ConditionalOnMissingBean(name = "redisTemplate")
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
//		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		template.setConnectionFactory(factory);
//
//		//String的序列化方式
//		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//		// 使用GenericJackson2JsonRedisSerializer 替换默认序列化(默认采用的是JDK序列化)
//		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//
//		//key序列化方式采用String类型
//		template.setKeySerializer(stringRedisSerializer);
//		//value序列化方式采用jackson类型
//		template.setValueSerializer(genericJackson2JsonRedisSerializer);
//
//		//hash的key序列化方式也是采用String类型
//		template.setHashKeySerializer(stringRedisSerializer);
//		//hash的value也是采用jackson类型
//		template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//		template.afterPropertiesSet();
//		return template;
//	}
//}
```

#### controller

```java
package com.springboot101.limit.controller;

import com.springboot101.limit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 14:47
 */
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/order/add")
	public void addOrder() {
		orderService.addOrder();
	}
	@GetMapping("/order/query")
	public String queryOrder(Integer keyId) {
		return orderService.getOrderById(keyId);
	}
}

```

#### service

```java
package com.springboot101.limit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 14:47
 */
@Service
@Slf4j
public class OrderService {
	@Autowired
	private RedisTemplate redisTemplate;

	public static final String ORDER_KEY="ord:";

	public void addOrder() {
		int keyId = ThreadLocalRandom.current().nextInt(1000) + 1;
		String serialNo = UUID.randomUUID().toString();
		String key = ORDER_KEY+keyId;
		String value = "JD" + serialNo;

		redisTemplate.opsForValue().set(key, value);  // redisTemplate.opsForValue()表示String类型
		log.info("***key:{}", key);
		log.info("***value:{}", value);

	}

	public String getOrderById(Integer keyId) {
		return (String)redisTemplate.opsForValue().get(ORDER_KEY+keyId); // 取
	}
}

```

### **运行结果**

输入: `http://localhost:8888/order/add`结果如下:

```bash
2024-01-19 15:23:25.178  INFO 32336 --- [nio-8888-exec-1] c.s.limit.service.OrderService           : ***key:ord:781
2024-01-19 15:23:25.179  INFO 32336 --- [nio-8888-exec-1] c.s.limit.service.OrderService           : ***value:JD8821620b-fdfc-408b-b7a4-95bf2de9bd51
```

输入: `http://localhost:8888/order/query?keyld=781`结果如下:

![image-20240119153259848](README.assets/image-20240119153259848.png)

**在linux中验证一下**

![image-20240119153642691](README.assets/image-20240119153642691.png)

## 实现的分布式限流方案

