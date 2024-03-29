## 分库分表
 * [ShardingSphere分库分表方案](%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8//ShardingSphere%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E6%96%B9%E6%A1%88.md)
 * [01-分库分表的说明](%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8//01-%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E7%9A%84%E8%AF%B4%E6%98%8E.md)
## 分布式缓存
## 分布式ID
## 分布式锁
 * [01-分布式锁概述](%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81//01-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E6%A6%82%E8%BF%B0.md)
 * [02-Redis分布式锁](%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81//02-Redis%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81.md)
 * [03-Redission分布式锁原理](%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81//03-Redission%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%8E%9F%E7%90%86.md)
## 分布式事务
 * [01-本地事务](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//01-%E6%9C%AC%E5%9C%B0%E4%BA%8B%E5%8A%A1.md)
 * [02-Spring编程事务](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//02-Spring%E7%BC%96%E7%A8%8B%E4%BA%8B%E5%8A%A1.md)
 * [04-Spring事务的传播特性](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//04-Spring%E4%BA%8B%E5%8A%A1%E7%9A%84%E4%BC%A0%E6%92%AD%E7%89%B9%E6%80%A7.md)
 * [05-分布式事务概念](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//05-%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1%E6%A6%82%E5%BF%B5.md)
 * [06-分布式事务-2PC解决方案](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//06-%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1-2PC%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88.md)
 * [07-分布式事务-seata实现2PC代码](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//07-%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1-seata%E5%AE%9E%E7%8E%B02PC%E4%BB%A3%E7%A0%81.md)
 * [分布式事务AT模式的脏写问题](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1AT%E6%A8%A1%E5%BC%8F%E7%9A%84%E8%84%8F%E5%86%99%E9%97%AE%E9%A2%98.md)
 * [08-分布式事务之解决方案（TCC）](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//08-%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1%E4%B9%8B%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88%EF%BC%88TCC%EF%BC%89.md)
 * [09-Hmily实现TCC分布式事务](%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1//09-Hmily%E5%AE%9E%E7%8E%B0TCC%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1.md)
## 分布式限流
 * [基于 Redisson 的分布式信号量](%E5%88%86%E5%B8%83%E5%BC%8F%E9%99%90%E6%B5%81//%E5%9F%BA%E4%BA%8E
 * [%20Redisson%20%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E4%BF%A1%E5%8F%B7%E9%87%8F.md)







# springboot + redis整合

##  redis

具体细节参考: 

<img src="../../../2-%25E5%2590%258E%25E7%25AB%25AF/note02/8-%25E6%2595%25B4%25E5%2590%2588/%25E7%25BB%25BC%25E5%2590%2588/Springboot-Notebook/springboot101/redis/springboot-aop-redis-lua/README.assets/image-20240119153134142.png" alt="image-20240119153134142" style="zoom:67%;" />

### 更改Redis配置文件

1. bind配置请注释掉

2. 保护模式设置为no

3. Linux系统的防火墙设置

4. Redis服务器的IP地址和密码是否正确

5. 忘记写访问redis的服务端口号和auth密码

6. 相互ping通

   ![image-20231010193546859](../../../2-%25E5%2590%258E%25E7%25AB%25AF/note02/8-%25E6%2595%25B4%25E5%2590%2588/%25E7%25BB%25BC%25E5%2590%2588/Springboot-Notebook/springboot101/redis/springboot-aop-redis-lua/README.assets/image-20231010193546859.png)

### 启动

```shell
root@lfj-virtual-machine:/myredis# redis-server /myredis/redis.conf 
root@lfj-virtual-machine:/myredis# redis-cli -a 741106 -p 6379 -h 192.168.200.134 --raw
```

例子

```shell
lfj@lfj-virtual-machine:/myredis$ sudo su root
[sudo] password for lfj: 
root@lfj-virtual-machine:/myredis# redis-server /myredis/redis.conf
root@lfj-virtual-machine:/myredis# ps -aux|grep redis
root        4705 49.2  1.0  98656 43396 ?        Ssl  11:06   0:01 redis-server *:6379
root        4713  0.0  0.0  12116   660 pts/0    S+   11:06   0:00 grep --color=auto redis
root@lfj-virtual-machine:/myredis# redis-cli -a 741106 -p 6379 -h 192.168.200.134 --raw
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
192.168.200.134:6379> 
```

注意: 

+ 192.168.200.134为linux的ip地址
+ 741106为 redis密码
+ 6379为redis端口号

## springboot

### 配置文件

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

### pom

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

### config

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

### controller

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

### service

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

## **运行结果**

输入: `http://localhost:8888/order/add`结果如下:

```bash
2024-01-19 15:23:25.178  INFO 32336 --- [nio-8888-exec-1] c.s.limit.service.OrderService           : ***key:ord:781
2024-01-19 15:23:25.179  INFO 32336 --- [nio-8888-exec-1] c.s.limit.service.OrderService           : ***value:JD8821620b-fdfc-408b-b7a4-95bf2de9bd51
```

输入: `http://localhost:8888/order/query?keyld=781`结果如下:

![image-20240119153259848](../../../2-%25E5%2590%258E%25E7%25AB%25AF/note02/8-%25E6%2595%25B4%25E5%2590%2588/%25E7%25BB%25BC%25E5%2590%2588/Springboot-Notebook/springboot101/redis/springboot-aop-redis-lua/README.assets/image-20240119153259848.png)

**在linux中验证一下**

![image-20240119153642691](../../../2-%25E5%2590%258E%25E7%25AB%25AF/note02/8-%25E6%2595%25B4%25E5%2590%2588/%25E7%25BB%25BC%25E5%2590%2588/Springboot-Notebook/springboot101/redis/springboot-aop-redis-lua/README.assets/image-20240119153642691.png)

#
