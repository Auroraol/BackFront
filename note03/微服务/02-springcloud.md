# 微服务技术

微服务技术对比

![image-20231011154954629](02-springcloud.assets/image-20231011154954629.png)

需求:

![image-20231011154934631](02-springcloud.assets/image-20231011154934631.png)

# 微服务springcloud

## 1.1 微服务架构

微服务架构的提出者：马丁福勒 https://martinfowler.com/articles/microservices.html

简而言之，微服务架构样式[[1\]](https://martinfowler.com/articles/microservices.html#footnote-etymology)是一种将单个应用程序开发为一组小服务的方法，每个小服务都在自己的进程中运行并与轻量级机制（通常是HTTP资源API）进行通信。这些服务围绕业务功能构建，并且可以由全自动部署机制独立部署。这些服务的集中管理几乎没有，它可以用不同的编程语言编写并使用不同的数据存储技术。

1、 微服务架构只是一个样式，一个风格。

2、 将一个完成的项目，拆分成多个子系统去分别开发。

3、 每一个子系统都是单独的运行在自己的容器（tomcat）中。

4、 每一个模块都是需要相互通讯的。 Http，RPC，MQ。

5、 每一个模块之间是没有依赖关系的，单独的部署。

6、 可以使用多种语言去开发不同的模块。

7、 使用MySQL数据库，Redis，ES去存储数据，也可以使用多个MySQL（集群和主从）数据库。

<font color=red>总结：将复杂臃肿的单体应用进行细粒度的划分，每个拆分出来的服务各自打包部署。</font>

<img src="02-springcloud.assets/image-20231011161027823.png" alt="image-20231011161027823" style="zoom: 88%;" />

## 1.2 SpringCloud介绍

+ SpringCloud是微服务架构（SOA）落地的一套技术栈
+ SpringCloud中的大多数技术都是基于Netflix公司的技术进行二次研发
+ SpringCloud的中文社区网站：http://springcloud.cn/
+ SpringCloud的中文网：http://springcloud.cc/
+ SpringCloud集成了各种微服务功能组件，并基于SpringBoot实现了这些组件的自动装配
+ 八个技术点：
  + Eureka - 服务的注册与发现 nacos springclould alibaba
  + Robbin - 服务之间的负载均衡
  + Feign - 服务之间的通讯 resttemplate
  + Hystrix - 服务的线程隔离以及断路器
  + Zuul - 服务网关
  + Stream - 实现MQ的使用
  + Config - 动态配置
  + Sleuth - 服务追踪 链路跟踪
+ SpringCloud与SpringBoot的版本兼容问题, 具体版本需求看官网

# 完整的SpringCloud框架图

<img src="02-springcloud.assets/1637465756656-e8c886f8-aca3-4165-896c-84daa6eb4ad7.png" alt="img" style="zoom:200%;" />

![image-20231011152511989](02-springcloud.assets/image-20231011152511989.png)

# 服务拆分

## 原则

1.不同微服务，不能重复开发相同业务

2.微服务数据独立，不要访问其它微服务的数据库

3.微服务可以将自己的业务暴露为接口文其它微服务调用

<img src="02-springcloud.assets/image-20231011155811421.png" alt="image-20231011155811421" style="zoom:67%;" />

## 例子

<img src="02-springcloud.assets/image-20231011161932436.png" style="zoom:67%;" />

根据订单id查询订单功能
需求:根据订单id查询订单的同时，把订单所属的用户信息一起返回

<img src="02-springcloud.assets/image-20231011161609408.png" alt="image-20231011161609408" style="zoom:80%;" />

<img src="02-springcloud.assets/image-20231011162616804.png" alt="image-20231011162616804" style="zoom:67%;" />

## 拆分实例

[LinkinStars/MicroServiceExample: 针对微服务的各种例子实现 (github.com)](https://github.com/LinkinStars/MicroServiceExample)

代码见:  [服务拆分案例.zip](..\code\服务拆分案例.zip) 

## 项目结构

| 目录    | 名称      | 访问地址                                                     |
| ------- | --------- | ------------------------------------------------------------ |
| eureka  | 注册中心  | [http://127.0.0.1:8761](http://127.0.0.1:8761/)              |
| gateway | 网关+路由 | http://127.0.0.1:8769/order/buy http://127.0.0.1:8769/pay/pay |
| order   | 订单服务  | http://127.0.0.1:8763/buy                                    |
| pay     | 支付服务  | http://127.0.0.1:8762/pay                                    |

### 架构改变

![img](02-springcloud.assets/899548-20180913101459081-897028894.png)

![img](02-springcloud.assets/899548-20180913101520351-871058140.png)

# 创建SpringCloud项目

工程结构

<img src="02-springcloud.assets/image-20231011170317584.png" alt="image-20231011170317584" style="zoom:67%;" />

先创建一个普通的maven父项目，再创建[Eureka](https://so.csdn.net/so/search?q=Eureka&spm=1001.2101.3001.7020)，Zuul，Service等子模块对项目进行创建来实现微服务

1. 删除src目录
2. 建Module
3. 改pom
4. 写YML
5. 主启动类
6. 业务类
7. 测试类

```xml
<!-- 子模块(创建时自动添加) -->
    <modules>
        <module>Eureka_Demo</module>
    </modules>


    <!--在properties标签内可以把版本号作为变量进行声明，方便maven依赖标签用${变量名}的形式动态获取版本号-->
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
        <spring-boot.version>2.7.9</spring-boot.version>
        <spring-cloud.version>2021.0.6</spring-cloud.version>
        <spring-cloud-alibaba.version>2021.0.4.0</spring-cloud-alibaba.version>
        <mysql.version>8.0.31</mysql.version>
        <mybatis.version>2.2.2</mybatis.version>
        <mybatis-plus.version>3.5.2</mybatis-plus.version>
        <mybatis-plus-generator.version>3.5.3</mybatis-plus-generator.version>
        <velocity.version>2.3</velocity.version>
        <swagger-spring-boot-starter.version>1.9.0.RELEASE</swagger-spring-boot-starter.version>
    </properties>


    <dependencyManagement>

        <dependencies>
            <!-- SpringBoot依赖-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!--spring-cloud依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!--对应的nacos版本为 2.0.4, Sentinel Version 1.8.5, RocketMQ Version 4.9.4,Seata Version 1.5.2 -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!--数据库-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>

            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.version}</version>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-generator</artifactId>
                <version>${mybatis-plus-generator.version}</version>
            </dependency>

            <!--Velocity模板引擎-->
            <dependency>
                <groupId>org.apache.velocity</groupId>
                <artifactId>velocity-engine-core</artifactId>
                <version>${velocity.version}</version>
            </dependency>

            <!-- swagger -->
            <dependency>
                <groupId>com.spring4all</groupId>
                <artifactId>swagger-spring-boot-starter</artifactId>
                <version>${swagger-spring-boot-starter.version}</version>
            </dependency>

        </dependencies>
    </dependencyManagement>


    <!--公共依赖-->
    <dependencies>
        <!-- lombok日志-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version>
        </dependency>
        <!--  -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.15</version>
        </dependency>
    </dependencies>
```





# 服务的网关-Zuul（1.5.x)和gateway(2.X）

+ 客户端维护大量的ip和port信息，直接访问指定服务
+ 认证和授权操作，需要在每一个模块中都添加认证和授权的操作
+ 项目的迭代，服务要拆分，服务要合并，需要客户端进行大量的变化
+ 统一的把安全性校验都放在Zuul中

![img](02-springcloud.assets/1637463242659-92323230-d7cc-4bc7-b047-eba75013ed39-169651356808124.png)

## 1 Zuul的快速入门

创建Maven项目，修改为SpringBoot

导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

添加一个注解

```
@EnableEurekaClient
@EnableZuulProxy
```

编写配置文件

```yaml
# 指定Eureka服务地址
eureka:
  client:
    service-url:
      defaultZone: http://root:root@localhost:8761/eureka,http://root:root@localhost:8762/eureka

#指定服务的名称
spring:
  application:
    name: ZUUL

server:
  port: 80
```

## 2 Zuul的监控界面

导入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

访问地址：

http://localhost/actuator/routes  （http://ip:port/actuator/routes）

编写配置文件

```
# 查看zuul的监控界面（开发时，配置为*，上线，不要配置）
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

## 3 忽略服务配置

```
# zuul的配置
zuul:
  # 基于服务名忽略服务，无法查看 ，如果要忽略全部的服务  "*",默认配置的全部路径都会被忽略掉（自定义服务的配置，无法忽略的）
  ignored-services: eureka
  # 监控界面依然可以查看，在访问的时候，404
  ignored-patterns: /**/search/**
```

## 4 自定义服务配置

```
# zuul的配置
zuul:
  # 指定自定义服务(方式一 ， key（服务名）：value（路径）)
#  routes:
#    search: /ss/**
#    customer: /cc/**
  # 指定自定义服务(方式二)
  routes:
    kehu:   # 自定义名称
      path: /ccc/**     # 映射的路径
      serviceId: customer   # 服务名称
```

![img](02-springcloud.assets/1637828726592-687136fb-1435-443d-b3bd-aba558bd4f07-169651356808126.png)

## 5 灰度发布（省略）

添加一个配置类

```

@Bean
public PatternServiceRouteMapper serviceRouteMapper() {
    return new PatternServiceRouteMapper(
        "(?<name>^.+)-(?<version>v.+$)",
        "${version}/${name}");
}
```

准备一个服务，提供2个版本

```
version: v1

#指定服务的名称
spring:
  application:
    name: CUSTOMER-${version}
```

-Dversion=v2 -Dserver.port=9099



修改Zuul的配置

```
# zuul的配置
zuul:
  # 基于服务名忽略服务，无法查看  ， 如果需要用到-v的方式，一定要忽略掉
  # ignored-services: "*"
```



6 Zuul的过滤器执行流程 客户端请求发送到Zuul服务上，首先通过PreFilter链，如果正常放行，会吧请求再次转发给RoutingFilter，请求转发到一个指定的服务，在指定的服务响应一个结果之后，再次走一个PostFilter的过滤器链，最终再将响应信息交给客户端。 

zuul

PreFilter

ErrorFilter

其他服务

RoutingFilter

客户端

PostFilter

![img](02-springcloud.assets/1637463772879-390bd2d4-0d9f-41ef-8efd-4d4274ca1b7d-169651356808128.png)



**Zuul过滤器入门**
创建POJO类，继承ZuulFilter抽象类

```
@Component
public class TestZuulFilter extends ZuulFilter {}
```

指定当前过滤器的类型

```java
@Override
public String filterType() {
    return FilterConstants.PRE_TYPE;
}
```

指定过滤器的执行顺序

```java
@Override
public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
}
```

配置是否启用

```java
@Override
public boolean shouldFilter() {
    // 开启当前过滤器
    return true;
}
```

指定过滤器中的具体业务代码

```java
@Override
public Object run() throws ZuulException {
    System.out.println("prefix过滤器执行~~~");
    return null;
}
```

**PreFilter实现token校验 （作业）**

准备访问路径，请求参数传递token

http://localhost/v2/customer/version?token=123

创建AuthenticationFilter

```java
@Component
public class AuthenticationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //..
    }
    
}
```



在run方法中编写具体的业务逻辑代码

```java
@Override
public Object run() throws ZuulException {
    //1. 获取Request对象
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();

    //2. 获取token参数
    String token = request.getParameter("token");

    //3. 对比token
    if(token == null || !"123".equalsIgnoreCase(token)) {
        //4. token校验失败，直接响应数据
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
    }
    return null;
}
```

## Zuul的降级



创建POJO类，实现接口FallbackProvider

```java
@Component
public class ZuulFallBack implements FallbackProvider {}
```

重写两个方法

```
@Override
public String getRoute() {
    return "*";   // 代表指定全部出现问题的服务，都走这个降级方法
}

@Override
public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
    System.out.println("降级的服务：" + route);
    cause.printStackTrace();

    return new ClientHttpResponse() {
        @Override
        public HttpStatus getStatusCode() throws IOException {
            // 指定具体的HttpStatus
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            // 返回的状态码
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        @Override
        public String getStatusText() throws IOException {
            // 指定错误信息
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            // 给用户响应的信息
            String msg = "当前服务：" + route + "出现问题！！！";
            return new ByteArrayInputStream(msg.getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
            // 指定响应头信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    };
}
```

## Zuul动态路由

创建一个过滤器

//  执行顺序最好放在Pre过滤器的最后面

在run方法中编写业务逻辑

```java
@Override
public Object run() throws ZuulException {
    //1. 获取Request对象
    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();

    //2. 获取参数，redisKey
    String redisKey = request.getParameter("redisKey");  //路径
   

    //3. 直接判断
    设计模式23一种
    
    if(redisKey != null && redisKey.equalsIgnoreCase("customer")){
        // http://localhost:8080/customer
        context.put(FilterConstants.SERVICE_ID_KEY,"customer-v1");
        context.put(FilterConstants.REQUEST_URI_KEY,"/customer");
    }else if(redisKey != null && redisKey.equalsIgnoreCase("search")){
        // http://localhost:8081/search/1
        context.put(FilterConstants.SERVICE_ID_KEY,"search");
        context.put(FilterConstants.REQUEST_URI_KEY,"/search/1");
    }

    return null;
}
```

GATEWAY介绍

一、负载均衡

1、依赖

```java
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

2、配置文件

```yaml
spring:
  cloud:
      gateway:
        discovery:
          locator:
            enabled: true #开启网关 gateway
            lower-case-service-id: true #忽略服务器的名字大小写
        routes:
          - id: order #服务名
            uri: lb://order #lb loadbalanced （代表是负载均衡）
            #        uri: http://localhost:8012/order
            predicates:
              - Path=/test #uri http://localhost:8012/order/test
              - After=2017-01-20T17:42:47.789-07:00[America/Denver]
            filters:
              - StripPrefix=1 #忽略一级服务名 以前方式：http://localhost:8012/order/test 加上后 http://localhost:8012/test
```

3、服务者端

```yaml
eureka:
 instance:
   prefer-ip-address: true
```

二、全局跨域

1、配置文件方式

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]':
            allowedOrigins: "https://docs.spring.io"  #这里配置是需要跨域请求地址 http://localhost:8080
            allowedMethods:
            - GET
```

2、代码方式

```java
@Configuration
public class MyCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1.配置跨域
        //允许哪种请求头跨域
        corsConfiguration.addAllowedHeader("*");
        //允许哪种方法类型跨域 get post delete put
        corsConfiguration.addAllowedMethod("*");
        // 允许哪些请求源跨域
        corsConfiguration.addAllowedOrigin("*");
        // 是否携带cookie跨域
        corsConfiguration.setAllowCredentials(true);

        //允许跨域的路径
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
}
import com.flow.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        // 指定此过滤器位于NettyWriteResponseFilter之后
        // 即待处理完响应体后接着处理响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            exchange.getResponse().beforeCommit(() -> {
                exchange.getResponse().getHeaders().entrySet().stream()
                        .filter(kv -> (kv.getValue() != null && kv.getValue().size() > 1))
                        .filter(kv -> (kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                                || kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)))
                        .forEach(kv -> {
                            kv.setValue(new ArrayList<String>() {{
                                add(kv.getValue().get(0));
                            }});
                        });


                log.info("处理后的数据:{}", JsonUtils.objectToString(exchange.getResponse().getHeaders().entrySet()));
                return chain.filter(exchange);
            });

            return chain.filter(exchange);
        }));
    }

}
@Bean
    public CorsResponseHeaderFilter corsResponseHeaderFilter() {
        return new CorsResponseHeaderFilter();
    }
```

作业：



1、权限登录  登录界面 需要一个主页 

2、登录成功，需要左边菜单动态生成

3、要设置我们的按钮权限（增加 删除 修改 查询功能）

4、权限需要动态配置（t_user  t_role t_auth t_menu）



![未命名文件(4).png](02-springcloud.assets/1664506578673-63656c5e-af46-44ad-a280-4a0c71b0c8dd-169651366118530.png)

5、登录成功后需要需要当前用户基本信息  角色 权限 和菜单查出来 放到缓存中

6、前端用vue

7、登录账号 ：管理员账号 普通账号 



# 服务间的隔离及断路器-Hystrix

![img](02-springcloud.assets/1637417591369-052c6cd7-7521-4fbc-ba7f-7c9251389785-169651684744036.png)



服务降级是当服务器压力剧增的情况下，根据当前业务情况及流量对一些服务和页面有策略的降级，以此释放服务器资源以保证核心

任务的正常运行。

Hystrix主要是为了解决服务雪崩问题：

1. 降级机制：当你的某一个服务出现超时时，资源不足，出现了异常，可以执行一个降级方法，返回一个托底数据
2. 隔离：提供了一个Hystrix线程池，信号量，和tomcat的线程池相互隔离。
3. 熔断：当你的某一个服务的失败率达到一定的阈值时，自动触发降级
4. 缓存：请求缓存的功能

## 1、降级机制实现（重点）

导入依赖 customer

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

添加一个注解（启动类）

```
@EnableCircuitBreaker
```

针对某一个接口去编写他的降级方法

```
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack")
public Customer findById(@PathVariable Integer id){
    int i = 1/0;
    return searchClient.findById(id);
}

// findById的降级方法  方法的描述要和接口一致
public Customer findByIdFallBack(Integer id){
    return new Customer(-1,"",0);
}
```

在接口上添加注解

```
@HystrixCommand(fallbackMethod = "findByIdFallBack")
```

## 2、线程隔离（线程池隔离，信号量）（重点）

如果使用Tomcat的线程池去接收用户的请求，使用当前线程去执行其他服务的功能，如果某一个服务出现了故障，导致tomcat的线程大量的堆积，导致Tomcat无法处理其他业务功能。

+ Hystrix的线程池（只要加上注解hystrixcommand默认），接收用户请求采用tomcat的线程池，执行业务代码，调用其他服务时，采用Hystrix的线程池。

+ 信号量，使用的还是Tomcat的线程池，帮助我们去管理Tomcat的线程池。

Hystrix的线程池的配置

| 配置信息             | name                                             | value               |
| -------------------- | ------------------------------------------------ | ------------------- |
| 线程隔离策略         | execution.isolation.strategy                     | THREAD  ，SEMAPHORE |
| 指定超时时间         | execution.isolation.thread.timeoutInMilliseconds | 1000                |
| 是否开启超时时间配置 | execution.timeout.enabled                        | true                |
| 超时之后是否中断线程 | execution.isolation.thread.interruptOnTimeout    | true                |
| 取消任务后知否       | execution.isolation.thread.interruptOnCancel     | false               |

代码实现

```java
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
        @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
})
public Customer findById(@PathVariable Integer id) throws InterruptedException {
    System.out.println(Thread.currentThread().getName());
    Thread.sleep(300);
    return searchClient.findById(id);
}
```

信号量的配置信息

```
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
        @HystrixProperty(name = "execution.isolation.strategy",value = "SEMAPHORE")
})
public Customer findById(@PathVariable Integer id) throws InterruptedException {
    System.out.println(Thread.currentThread().getName());
    return searchClient.findById(id);
}
```

总结

```
hystrix 线程池隔离 和 信号量隔离区别
1 需要开销额外内存区去维护线程池，而信号量不用。
2 hystrix减轻tomcat的压力，信号量要开一个线程池，增加tomcat压力
3 hystrix降级的需要报出异常，信号量是不回去抛异常
```

## 3、断路器（重点）

### 3.1 断路器介绍

马丁福勒断路器论文：https://martinfowler.com/bliki/CircuitBreaker.html

在调用指定服务时，如果说这个服务的失败率达到你输入的一个阈值，将断路器从closed状态，转变为open状态，指定服务时无法被访问的，如果你访问就直接走fallback方法，在一定的时间内，open状态会再次转变为half open状态，允许一个请求发送到我的指定服务，如果成功，转变为closed，如果失败，服务再次转变为open状态，会再次循环到half open，直到断路器回到一个closed状态。![img](02-springcloud.assets/1637418306419-3a3fd7d1-c211-4fa5-986b-a46e8462b68e-169651698205440.png)

##### 

### 3.2 配置断路器的监控界面

导入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

在启动类中添加注解

```
@EnableHystrixDashboard
```

配置一个Servlet路径，指定上Hystrix的Servlet

```
@WebServlet("/hystrix.stream")
public class HystrixServlet extends HystrixMetricsStreamServlet {
}

//------------------------------------------------------------
// 在启动类上，添加扫描Servlet的注解
@ServletComponentScan("com.qf.servlet")
```

添加yml文件配置

```
hystrix:
  dashboard:
    proxy-stream-allow-list: "localhost"
```

测试直接访问http://host:port/hystrix

![img](02-springcloud.assets/1637418426081-7efbe054-cb6c-40b3-bc1b-0b9c1b56e8e4-169651703510042.png)



在当前位置输入映射好的servlet路径



##### 3.3 配置断路器的属性

断路器的属性（默认10s秒中之内请求数）

| **配置信息**                         | **name**                                 | **value** |
| ------------------------------------ | ---------------------------------------- | --------- |
| 断路器的开关                         | circuitBreaker.enabled                   | true      |
| 失败阈值的总请求数                   | circuitBreaker.requestVolumeThreshold    | 20        |
| 请求总数失败率达到%多少时            | circuitBreaker.errorThresholdPercentage  | 50        |
| 断路器open状态后，多少秒是拒绝请求的 | circuitBreaker.sleepWindowInMilliseconds | 5000      |
| 强制让服务拒绝请求                   | circuitBreaker.forceOpen                 | false     |
| 强制让服务接收请求                   | circuitBreaker.forceClosed               | false     |

具体配置方式

```java
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "70"),
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
})
```

#### 4 请求缓存（了解）

##### 4.1 请求缓存介绍  相当于我们的学习的mybatis session 缓存

- 请求缓存的声明周期是一次请求
- 请求缓存是缓存当前线程中的一个方法，将方法参数作为key，方法的返回结果作为value
- 在一次请求中，目标方法被调用过一次，以后就都会被缓存。

![img](02-springcloud.assets/1637418578167-1626d143-89c4-4c69-9d95-7b847cf891b5-169651705737044.png)

##### 4.2 请求缓存的实现

创建一个Service，在Service中调用Search服务。

```java
@Service
public class CustomerService {

    @Autowired
    private SearchClient searchClient;


    @CacheResult
    @HystrixCommand(commandKey = "findById")
    public Customer findById(@CacheKey Integer id) throws InterruptedException {
        return searchClient.findById(id);
    }

    @CacheRemove(commandKey = "findById")
    @HystrixCommand
    public void clearFindById(@CacheKey Integer id){
        System.out.println("findById被清空");
    }

}
```

使用请求缓存的注解

@CacheResult：帮助我们缓存当前方法的返回结果（必须@HystrixCommand配合使用）

@CacheRemove：帮助我们清楚某一个缓存信息（基于commandKey）

@CacheKey：指定哪个方法参数作为缓存的标识



编写Filter，去构建HystrixRequestContext

```java
@WebFilter("/*")
public class HystrixRequestContextInitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext.initializeContext();
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
```

修改Controller

```
public Customer findById(@PathVariable Integer id) throws InterruptedException {
    System.out.println(customerService.findById(id));
    System.out.println(customerService.findById(id));
    customerService.clearFindById(id);
    System.out.println(customerService.findById(id));
    System.out.println(customerService.findById(id));
    return searchClient.findById(id);
}
```

# 服务的动态配置-Config

## 1 引言

+ 配置文件分散在不同的项目中，不方便维护。
+ 配置文件的安全问题。
+ 修改完配置文件，无法立即生效。

![img](02-springcloud.assets/1637464490220-84e402d3-dc40-47cb-ad98-3b899b415ee9-169651712163252.png)



## 2 搭建Config-Server

创建Maven工程，修改为SpringBoot

导入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

添加注解

```
@EnableConfigServer
```

编写配置文件（Git的操作）

```
spring:
  cloud:
    config:
      server:
        git:
          basedir: D:\basedir    # 本地仓库的地址
          username: zjw_2301211@126.com    #  远程仓库用户名
          password: z123123   #   远程仓库密码
          uri: https://gitee.com/zhengdaxian/config-resp.git       # 远程仓库地址
```

http://localhost:8099/master/bootstrap-xxs.yml

测试（http://localhost:port/{label}/{application}-{profile}.yml）



label  就是你远程仓库分支名称

application 文件名称

"-" 中横线 必须有

profile 必须有：如果没有profile后缀 也必须随便写一个，如果有就拉取你自己的profile（dev、test、pre、pro）



bootstrap.yml 优先级别最高，如果要采用我们的config，配置文件必须采用bootstrap.yml

## 3 搭建Config-Client

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```



编写配置文件

```
# 指定Eureka服务地址
eureka:
  client:
    service-url:
      defaultZone: http://root:root@localhost:8761/eureka,http://root:root@localhost:8762/eureka

#指定服务的名称
spring:
  application:
    name: CUSTOMER-${version}
  cloud:
    config:
      discovery:
        enabled: true
        service-id: CONFIG
      profile: dev

version: v1


# CONFIG -> CUSTOMER-v1-dev.yml
```



## 4 实现动态配置

### 4.1 实现原理

![img](02-springcloud.assets/1637464979610-6ec282ad-52b5-4246-834e-cb6891f71468-169651712163254.png)





### 4.2 服务连接RabbitMQ

导入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

编写配置文件连接RabbitMQ信息

```
spring:
  rabbitmq:
    host: 43.138.66.98
    port: 5672
    username: test
    password: test
    virtual-host: /test
```



### 4.3 实现手动刷新

导入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

为customer添加一个controller

```
@RestController
@RefreshScope    //刷新
public class CustomerController {

    @Value("${env}")
    private String env;

    @GetMapping("/env")
    public String env(){
        return env;
    }
}
```

测试



\1. CONFIG在Gitee修改之后，自动拉取最新的配置信息。

\2. 其他模块需要更新的话，手动发送一个请求http://ip:port/actuator/bus-refresh，不重启项目，即可获取最新的配置信息

注意：http://ip:port/actuator/bus-refresh 手动刷新地址需要post请求

注意：添加的变量必须放在远程git仓库里面



### 4.4 内网穿透

内网穿透的官网https://natapp.cn/

注册登录

购买一个免费的隧道。

![img](02-springcloud.assets/1637465247062-2bdd08c4-bd14-4da1-8ea5-f48164e272f7-169651712163258.png)





下载客户端，并复制config.ini文件，在文件中指定authtoken

![img](02-springcloud.assets/1637465270963-007a5996-3fe8-4657-b572-d52ff928c8f6-169651712163256.png)





启动exe文件，并测试使用域名访问config接口

![img](02-springcloud.assets/1637465306062-4c336f17-6745-40c9-9f4a-6d4d65633d97-169651712163260.png)



### 4.5 实现自动刷新

配置Gitee中的WebHooks

![img](02-springcloud.assets/1637465353869-54fe0f72-f4f2-4816-952d-ca0aa6215ddc-169651727953962.png)





给Config添加一个过滤器

```
package com.qf.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@WebFilter("/*")
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        String url = new String(httpServletRequest.getRequestURI());

        //只过滤/actuator/bus-refresh请求
        if (!url.endsWith("/actuator/bus-refresh")) {
            chain.doFilter(request, response);
            return;
        }

        //获取原始的body
        String body = readAsChars(httpServletRequest);

        System.out.println("original body:   "+ body);

        //使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
        CustometRequestWrapper requestWrapper = new CustometRequestWrapper(httpServletRequest);

        chain.doFilter(requestWrapper, response);

    }

    @Override
    public void destroy() {

    }

    private class CustometRequestWrapper extends HttpServletRequestWrapper {
        public CustometRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            byte[] bytes = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.read() == -1 ? true:false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }

    public static String readAsChars(HttpServletRequest request)
    {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
```

# 服务的注册与发现-Eureka

**Eureka就是帮助我们维护所有服务的信息，以便服务之间的相互调用**

<img src="02-springcloud.assets/image-20231011165044735.png" alt="image-20231011165044735" style="zoom:67%;" />

## 1 Eureka的快速入门

### 1.1 创建EurekaServer

#### 1.1.1 创建一个父工程，并且在父工程中指定SpringCloud的版本，并且将packaing修改为pom

```xml
<packaging>pom</packaging>
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.12.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

<dependencyManagement>
   <dependencies>
       <!--SpringCloud依赖-->
       <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-dependencies</artifactId>
          <version>Hoxton.SR12</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
   </dependencies>
</dependencyManagement>
```

创建eureka的server，创建SpringBoot工程，并且导入依赖，在启动类中添加注解，编写yml文件

#### 1.1.2 导入依赖

```xml
<dependencies>
    <!--EurekaServer服务注册依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
	
    <!--web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### 1.1.3 启动类添加注解

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
```

#### 1.1.4 编写yml

```yaml
server:
  port: 8761 # 端口号

eureka:
  instance:
    hostname: localhost   # localhost
  client:
    # 当前的eureka服务是单机版的
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

### 1.2 创建EurekaClient

**创建Maven工程，修改为SpringBoot**

#### 1.2.1 导入依赖

```xml
<!--eureka客户端依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### 1.2.2 在启动类上添加注解

```java
@SpringBootApplication
@EnableEurekaClient
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class,args);
    }

}
```

#### 1.2.3 编写配置文件

```properties
# 指定Eureka服务地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

#指定服务的名称
spring:
  application:
    name: CUSTOMER
```

### 1.3 测试Eureka

```java
@Autowired
private EurekaClient eurekaClient;


@GetMapping("/customer")
public String customer(){
    //1. 通过eurekaClient获取到SEARCH服务的信息
    InstanceInfo info = eurekaClient.getNextServerFromEureka("SEARCH", false);

    //2. 获取到访问的地址
    String url = info.getHomePageUrl();
    System.out.println(url);

    //3. 通过restTemplate访问
    String result = restTemplate.getForObject(url + "/search", String.class);

    //4. 返回
    return result;
}
```

###  1.4 启动类中配置（各人配置不一样）

 RestTemplate

```java
@SpringBootApplication
@EnableEurekaClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

## Eureka的细节

EurekaClient启动是，将自己的信息注册到EurekaServer上，EurekaSever就会存储上EurekaClient的注册信息。 当EurekaClient调用服务时，本地没有注册信息的缓存时，去EurekaServer中去获取注册信息。 EurekaClient会通过心跳的方式去和EurekaServer进行连接。（默认30sEurekaClient会发送一次心跳请求，如果超过了90s还没有发送心跳信息的话，EurekaServer就认为你宕机了，将当前EurekaClient从注册表中移除）



EurekaClient会每隔30s去EurekaServer中去更新本地的注册表



Eureka的自我保护机制，统计15分钟内，如果一个服务的心跳发送比例低于85%，EurekaServer就会开启自我保护机制

●不会从EurekaServer中去移除长时间没有收到心跳的服务。

●EurekaServer还是可以正常提供服务的。

●网络比较稳定时，EurekaServer才会开始将自己的信息被其他节点同步过去









CAP定理，C - 一致性，A-可用性，P-分区容错性，这三个特性在分布式环境下，只能满足2个，而且分区容错性在分布式环境下，是必须要满足的，只能在AC之间进行权衡。

如果选择CP，保证了一致性，可能会造成你系统在一定时间内是不可用的，如果你同步数据的时间比较长，造成的损失大。

Eureka就是一个AP的效果，高可用的集群，Eureka集群是无中心，Eureka即便宕机几个也不会影响系统的使用，不需要重新的去推举一个master，也会导致一定时间内数据是不一致。

4、BASE理论

BASE理论是对CAP的扩充，在强一致性和高可用性无法同时满足的情况下，寻求一种中间解决方案。其中就有最终一致性的表述。

BASE名称由来：

●BA: Basic Availability基本可用性；

●S: Soft state柔性状态；

●E: Eventual consistency最终一致性；

4.1、基本可用性（ Basic Availability）

也就是允许有一定的异常。比如第一次刷出现网络异常，再次刷新就好了。保持基本可用就行。到底允许多大的异常占比，就看用户的接受度了。

4.2、柔性状态（Soft state）

柔性状态，或者软状态，也就是一种中间状态；其实就是在无法做到强一致性的情况下，在数据还没有同步之前，允许存在的一个状态。

4.3、最终一致性（Eventual consistency）

也就是经过一段时间之后，达成一致。这里的一段时间可以是几秒、也可能是几分钟、甚至几小时。（此过程是异步进行的）

●读己之写一致性

自己能马上读取到自己写入的内容。比如博客，提交之后，自己马上可以看到，其他人可能要等一会才能看到。

●3、因果一致性

因果一致性，也就是一种逻辑顺序上的一致性。比如先有问题才有回答。因为一致性不是很及时、或者存在地区差异导致数据同步差异，例如：北京网友提问，上海网友看到了并回答了问题。因为某些原因，上海网友的回答快。读同步到了南京地区，但北京的问题还没有同步到南京。这时就保证先有问题才能看到回答。问题没有同步、回答同步了，逻辑上也不能只显示回答，而没有问题。这时就要进行因果一致性。

在一致性研究上，还出现过很多类似的一致性名词，类似：[Sequential 和 Linearizability](https://mp.weixin.qq.com/s/CEaAdb88O4TBU5aPN47Lsw)

4.4、总结

BASE理论是CAP的实践指导理论。因为对于系统来说，最好就是同时满足CAP，但是强CAP满足不了，我们就折中选择弱CAP，也就是BASE理论。

在BASE理论中，我们关注的是最终一致性。需要研究的也就是最终一致性。



## Eureka的高可用

如果程序的正在运行，突然Eureka宕机了。
●如果调用方访问过一次被调用方了，Eureka的宕机不会影响到功能。
●如果调用方没有访问过被调用方，Eureka的宕机就会造成当前功能不可用。
搭建Eureka高可用
准备多台Eureka
让服务注册到多台**Eureka**

```
eureka:
  client:
    service-url:
      defaultZone:http://root:root@localhost:8761/eureka,http://root:root@localhost:8762/eureka
```

让多台Eureka之间相互通讯

```
eureka:
  client:
    registerWithEureka: true      # 注册到Eureka上
    fetchRegistry: true           # 从Eureka拉取信息
    serviceUrl:
      defaultZone: http://root:root@localhost:8762/eureka/
```



![img](02-springcloud.assets/1637383624612-f795193a-bb63-4913-89f5-e802eb3ac799.png)



![img](02-springcloud.assets/1637383642595-e890f871-bf1b-459a-bdc5-84e70d23668f.png)





## Eureka的安全性

实现Eureka认证

导入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

编写config配置类

```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 忽略掉/eureka/**
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
```

编写配置文件

```
# 指定用户名和密码
spring:
  security:
    user:
      name: root
      password: root
```

其他服务想注册到Eureka上需要添加用户名和密码

```
eureka:
  client:
    service-url:
      defaultZone: http://用户名:密码@localhost:8761/eureka
```

