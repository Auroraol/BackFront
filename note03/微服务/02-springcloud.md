# 分布式核心

## 分布式中的远程调用

在微服务架构中，通常存在多个服务之间的远程调用的需求。远程调用通常包含两个部分：序列化和通
信协议。常见的序列化协议包括json、xml、hession、protobuf、thrift、text、bytes等，目前主流的
远程调用技术有基于HTTP的RESTful接口以及基于TCP的RPC协议。

### (1)RESTful接口

REST，即Representational State Transfer的缩写，如果一个架构符合REST原则，就称它为RESTful架
构。
**资源（Resources）**
所谓"资源"，就是网络上的一个实体，或者说是网络上的一个具体信息。它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的实在。你可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的URI。要获取这个资源，访问它的URI就可以，因此URI就成了每一个资源的地址或独一无二的识别符。REST的名称"表现层状态转化"中，省略了主语。"表现层"其实指的是"资源"（Resources）的"表现层"。
**表现层（Representation）**
"资源"是一种信息实体，它可以有多种外在表现形式。我们把"资源"具体呈现出来的形式，叫做它的"表现层"（Representation）。比如，文本可以用txt格式表现，也可以用HTML格式、XML格式、JSON格式表现，甚至可以采用二进制格式；图片可以用JPG格式表现，也可以用PNG格式表现。URI只代表资源的实体，不代表它的形式。严格地说，有些网址最后的".html"后缀名是不必要的，因为这个后缀名表示格式，属于"表现层"范畴，而URI应该只代表"资源"的位置。
**状态转化（State Transfer）**
访问一个网站，就代表了客户端和服务器的一个互动过程。在这个过程中，势必涉及到数据和状态的变化。互联网通信协议HTTP协议，是一个无状态协议。这意味着，所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生"状态转化"（State Transfer）。客户端用到的手段，只能是HTTP协议。具体来说，就是HTTP协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源。

综合上面的解释，我们总结一下什么是RESTful架构：

+ 每一个URI代表一种资源；
+ 客户端和服务器之间，传递这种资源的某种表现层；
+ 客户端通过四个HTTP动词，对服务器端资源进行操作，实现"表现层状态转化"

### (2)RPC协议

RPC（Remote Procedure Call ） 一种进程间通信方式。允许像调用本地服务一样调用远程服务。RPC
框架的主要目标就是让远程服务调用更简单、透明。RPC框架负责屏蔽底层的传输方式（TCP或者UDP）、序列化方式（XML/JSON/二进制）和通信细节。开发人员在使用的时候只需要了解谁在什么位置提供了什么样的远程服务接口即可，并不需要关心底层通信细节和调用过程。

<img src="02-springcloud.assets/image-20231012155913144.png" alt="image-20231012155913144" style="zoom: 80%;" />

### (3)区别与联系

| 比较项   | RESTful    | RPC         |
| -------- | ---------- | ----------- |
| 通讯协议 | HTTP       | 一般使用TCP |
| 性能     | 低         | 高          |
| 灵活度   | 高         | 低          |
| 应用     | 微服务架构 | SOA架构     |

  1、HTTP相对更规范，更标准，更通用，无论哪种语言都支持http协议。如果你是对外开放API，例如
  开放平台，外部的编程语言多种多样，你无法拒绝对每种语言的支持，现在开源中间件，基本最先支持
  的几个协议都包含RESTful。
  2、 RPC 框架作为架构微服务化的基础组件，它能大大降低架构微服务化的成本，提高调用方与服务提
  供方的研发效率，屏蔽跨进程调用函数（服务）的各类复杂细节。让调用方感觉就像调用本地函数一样
  调用远端函数、让服务提供方感觉就像实现一个本地函数一样来实现服务。

## 分布式中的CAP原理

# 微服务技术

**微服务技术对比:**

![image-20231011154954629](02-springcloud.assets/image-20231011154954629.png)

需求:

![image-20231011154934631](02-springcloud.assets/image-20231011154934631.png)

# 常见微服务框架

## SpringCloud

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

## ServiceComb

Apache ServiceComb 是业界第一个Apache微服务顶级项目， 是一个开源微服务解决方案,致力于帮助企业、用户和开发者将企业应用轻松微服务化上云，并实现对微服务应用的高效运维管理。其提供一站式开源微服务解决方案，融合SDK框架级、0侵入ServiceMesh场景并支持多语言。

## ZeroC ICE

ZeroC IceGrid 是ZeroC公司的杰作，继承了CORBA的血统，是新一代的面向对象的分布式系统中间件。作为一种微服务架构，它基于RPC框架发展而来，具有良好的性能与分布式能力。

# 微服务springcloud

## 微服务架构

微服务架构的提出者：马丁福勒 https://martinfowler.com/articles/microservices.html

简而言之，微服务架构样式[[1\]](https://martinfowler.com/articles/microservices.html#footnote-etymology)是一种将单个应用程序开发为一组小服务的方法，每个小服务都在自己的进程中运行并与轻量级机制（通常是HTTP资源API）进行通信。这些服务围绕业务功能构建，并且可以由全自动部署机制独立部署。这些服务的集中管理几乎没有，它可以用不同的编程语言编写并使用不同的数据存储技术。

1、 **微服务架构只是一个样式，一个风格。**

2、 将一个完成的项目，拆分成多个子系统去分别开发。

3、 每一个子系统都是单独的运行在自己的容器（tomcat）中。

4、 每一个模块都是需要相互通讯的。 Http，RPC，MQ。

5、 每一个模块之间是没有依赖关系的，单独的部署。

6、 可以使用多种语言去开发不同的模块。

7、 使用MySQL数据库，Redis，ES去存储数据，也可以使用多个MySQL（集群和主从）数据库。

<font color=red>总结：将复杂臃肿的单体应用进行细粒度的划分，每个拆分出来的服务各自打包部署。</font>

<img src="02-springcloud.assets/image-20231011161027823.png" alt="image-20231011161027823" style="zoom: 88%;" />

## SpringCloud介绍

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

## 微服务相关技术概念

### 服务注册与发现

服务注册：

+ 服务实例将自身服务信息注册到注册中心。这部分服务信息包括服务所在主机IP和提供服务
  的Port，以及暴露服务自身状态以及访问协议等信息。

服务发现：

+ 服务实例请求注册中心获取所依赖服务信息。服务实例通过注册中心，获取到注册到其中的
  服务实例的信息，通过这些信息去请求它们提供的服务。

### 负载均衡

负载均衡是高可用网络基础架构的关键组件，通常用于将工作负载分布到多个服务器来提高网站、应
用、数据库或其他服务的性能和可靠性。

<img src="02-springcloud.assets/image-20231012160721266.png" alt="image-20231012160721266" style="zoom: 67%;" />

### 熔断

熔断这一概念来源于电子工程中的断路器（Circuit Breaker）。在互联网系统中，当下游服务因访问压
力过大而响应变慢或失败，上游服务为了保护系统整体的可用性，可以暂时切断对下游服务的调用。这
种牺牲局部，保全整体的措施就叫做熔断。

<img src="02-springcloud.assets/image-20231012160810030.png" alt="image-20231012160810030" style="zoom:67%;" />

### 链路追踪

随着微服务架构的流行，服务按照不同的维度进行拆分，一次请求往往需要涉及到多个服务。互联网应
用构建在不同的软件模块集上，这些软件模块，有可能是由不同的团队开发、可能使用不同的编程语言
来实现、有可能布在了几千台服务器，横跨多个不同的数据中心。因此，就需要对一次请求涉及的多个
服务链路进行日志记录，性能监控即链路追踪

<img src="02-springcloud.assets/image-20231012160840105.png" alt="image-20231012160840105" style="zoom: 80%;" />

### API网关

随着微服务的不断增多，不同的微服务一般会有不同的网络地址，而外部客户端可能需要调用多个服务
的接口才能完成一个业务需求，如果让客户端直接与各个微服务通信可能出现：

+ 客户端需要调用不同的url地址，增加难度
+ 再一定的场景下，存在跨域请求的问题
+ 每个微服务都需要进行单独的身份认证

针对这些问题，API网关顺势而生

API网关直面意思是将所有API调用统一接入到API网关层，由网关层统一接入和输出。一个网关的基本
功能有：统一接入、安全防护、协议适配、流量管控、长短链接支持、容错能力。有了网关之后，各个
API服务提供团队可以专注于自己的的业务逻辑处理，而API网关更专注于安全、流量、路由等问题。

<img src="02-springcloud.assets/image-20231012160936577.png" alt="image-20231012160936577" style="zoom:80%;" />

## SpringCloud中的核心组件

**Spring Cloud的本质是在 Spring Boot 的基础上，增加了一堆微服务相关的规范，**并对应用上下文（Application Context）进行了功能增强。既然 Spring Cloud 是规范，那么就需要去实现，目前Spring Cloud 规范已有 Spring官方，Spring Cloud Netflix，Spring Cloud Alibaba等实现。通过组件化的方式，Spring Cloud将这些实现整合到一起构成全家桶式的微服务技术栈。

### Spring Cloud Netflix组件

| 组件名称 | 作用           |
| -------- | -------------- |
| Eureka   | 服务注册中心   |
| Ribbon   | 客户端负载均衡 |
| Feign    | 声明式服务调用 |
| Hystrix  | 客户端容错保护 |
| Zuul API | 服务网关       |

### Spring Cloud原生及其他组件

| 组件名称      | 作用                               |
| ------------- | ---------------------------------- |
| Consul        | 服务注册中心(Eureka替换方案Consul) |
| Config        | 分布式配置中心                     |
| Gateway API   | 服务网关                           |
| Sleuth/Zipkin | 分布式链路追踪                     |

### Spring Cloud Alibaba组件

| 组件名称 | 作用                              |
| -------- | --------------------------------- |
| Nacos    | 服务注册中心(Eureka替换方案Nacos) |
| Sentinel | 客户端容错保护                    |

## SpringCloud的体系结构

![image-20231012162024891](02-springcloud.assets/image-20231012162024891.png)

从上图可以看出Spring Cloud各个组件相互配合，合作支持了一套完整的微服务架构。

+ 注册中心负责服务的注册与发现，很好将各服务连接起来
+ 断路器负责监控服务之间的调用情况，连续多次失败进行熔断保护。
+ API网关负责转发所有对外的请求和服务
+ 配置中心提供了统一的配置信息管理服务,可以实时的通知各个服务获取最新的配置信息
+ 链路追踪技术可以将所有的请求数据记录下来，方便我们进行后续分析各个组件又提供了功能完善的dashboard监控平台,可以方便的监控各组件的运行状况

## 完整的SpringCloud框架图

<img src="02-springcloud.assets/1637465756656-e8c886f8-aca3-4165-896c-84daa6eb4ad7.png" alt="img" style="zoom:200%;" />

![image-20231011152511989](02-springcloud.assets/image-20231011152511989.png)

# 服务拆分原则

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

 **架构改变**

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



# ①服务的注册与发现

## 常见的注册中心

<img src="02-springcloud.assets/image-20231012220358442.png" alt="image-20231012220358442" style="zoom:80%;" />

<img src="02-springcloud.assets/image-20231012171337803.png" alt="image-20231012171337803" style="zoom:67%;" />

## 服务的注册与发现-Eureka

### Eureka的基础知识

Eureka是Netflix开发的服务发现框架，SpringCloud将它集成在自己的子项目spring-cloud-netflix中，实现SpringCloud的服务发现功能。

<img src="02-springcloud.assets/image-20231012171549576.png" alt="image-20231012171549576" style="zoom:80%;" />

上图简要描述了Eureka的基本架构，由3个角色组成：
1、Eureka Server

+ 提供服务注册和发现

2、Service Provider

+ 服务提供方
+ 将自身服务注册到Eureka，从而使服务消费方能够找到

3、Service Consumer

+ 服务消费方
+ 从Eureka获取注册服务列表，从而能够消费服务

**Eureka就是帮助我们维护所有服务的信息，以便服务之间的相互调用**

<img src="02-springcloud.assets/image-20231011165044735.png" alt="image-20231011165044735" style="zoom:67%;" />

### Eureka使用

代码见 [Eureka案例.zip](..\code\Eureka案例.zip) 

![image-20231012165205993](02-springcloud.assets/image-20231012165205993.png)

#### 1.1 Maven工程根pom内容

 创建一个父工程，并且在父工程中指定SpringCloud的版本，并且将packaing修改为pom

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.lfj</groupId>
    <artifactId>spring_cloud_demo</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>

<!--   服务[子模块(创建时自动添加)] -->
    <modules>
        <module>product_service</module>
        <module>order_service</module>
        <module>eureka_server</module>
        <module>import_test</module>
    </modules>

<!--    所有springboot项目都必须继承自 spring-boot-starter-parent -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
    </parent>

<!--    在properties标签内可以把版本号作为变量进行声明，方便maven依赖标签用${变量名}的形式动态获取版本号-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>
<!--    <properties>-->
<!--        <maven.compiler.source>8</maven.compiler.source>-->
<!--        <maven.compiler.target>8</maven.compiler.target>-->
<!--        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>-->
<!--        <java.version>1.8</java.version>-->
<!--        <spring-boot.version>2.7.9</spring-boot.version>-->
<!--        <spring-cloud.version>2021.0.6</spring-cloud.version>-->
<!--        <spring-cloud-alibaba.version>2021.0.4.0</spring-cloud-alibaba.version>-->
<!--        <mysql.version>8.0.31</mysql.version>-->
<!--        <mybatis.version>2.2.2</mybatis.version>-->
<!--        <mybatis-plus.version>3.5.2</mybatis-plus.version>-->
<!--        <mybatis-plus-generator.version>3.5.3</mybatis-plus-generator.version>-->
<!--        <velocity.version>2.3</velocity.version>-->
<!--        <swagger-spring-boot-starter.version>1.9.0.RELEASE</swagger-spring-boot-starter.version>-->
<!--    </properties>-->
<!--     使用: <version>${spring-cloud-alibaba.version}</version> -->

<!--    公共依赖-->
<!--    dependencies即使在子项目中不写该依赖项，那么子项目仍然会从父项目中继承该依赖项（全部继承）-->
    <dependencies>
        <!-- web起步依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--Spring Boot内部模块都使用Commons Logging来记录日志-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </dependency>
        <!-- springboot单元测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- lombok日志-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version>
        </dependency>
    </dependencies>

<!--    私有依赖-->
<!--   dependencyManagement子项目中声明依赖，是不会从父项目中继承下来的-->
    <dependencyManagement>
        <dependencies>
            <!--SpringCloud依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

    <!-- 打包依赖-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <!--SpringBoot应用打包插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

创建eureka的server，创建SpringBoot工程，并且导入依赖，在启动类中添加注解，编写yml文件

#### 1.2 创建EurekaServer

![image-20231012165553052](02-springcloud.assets/image-20231012165553052.png)

##### 1.2.1 导入依赖

```xml
    <dependencies>
        <!-- Eureka服务端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>
```

##### 1.2.2 启动类添加注解

@EnableEurekaServer用于激活eureakaserver

```java
@SpringBootApplication
@EnableEurekaServer  //激活eureakaserver
public class EurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class,args);
	}
}
```

##### 1.2.3 编写yml

```yaml
# Spring
spring:
  application:
    name: eureka-server # 应用名称

# server      
server:
  port: 9000 #端口

#配置eureka server
eureka:
  instance:
    hostname: localhost   # localhost
  client:
    #当前的eureka服务是单机版的
    register-with-eureka: false #是否将自己注册到注册中心
#    register-with-eureka: true #是否将自己注册到注册中心
    fetch-registry: false #是否从eureka中获取注册信息
    service-url: #配置暴露给Eureka Client的请求地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false #关闭自我保护
    eviction-interval-timer-in-ms: 4000 #剔除服务间隔


#测试eureka服务是多机版的
#iaed复制模拟两个EurekaServer
#端口9000 , 8000
#两个server需要相互注册
```

打开浏览器访问http://localhost9000即可进入EurekaServer内置的管理控制台

#### 1.3 创建EurekaClient

EurekaClient: 就是各种各样的服务

<img src="02-springcloud.assets/image-20231012165914682.png" alt="image-20231012165914682" style="zoom:67%;" />

##### 1.3.1 导入依赖

```xml
<!--eureka客户端依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

##### 1.3.2 在启动类上添加注解(看情况)

```java
@SpringBootApplication
@EntityScan("cn.lfj.order.entity")
//激活eurekaClient
//@EnableEurekaClient
//@EnableDiscoveryClient
/*
从Spring Cloud Edgware版本开始， @EnableDiscoveryClient 或@EnableEurekaClient 可省略。
只需加上相关依赖，并进行相应配置，即可将微服务注册到服务发现组件上。
*/
public class OrderApplication {

	/**
	 * 使用spring提供的RestTemplate发送http请求到商品服务
	 *      1.创建RestTemplate对象交给容器管理
	 *      2.在使用的时候,调用其方法完成操作 (getXX,postxxx)
	 * * @LoadBalanced : 是ribbon提供的负载均衡的注解
	 */
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class,args);
	}
}
```

##### 1.3.3 编写配置文件

```properties
server:
  port: 9002 #端口
  
spring:
  application:
    name: service-order #服务名称
    
#配置Eureka
eureka:
  client:
    service-url:
      defaultZone: http://localhost:9000/eureka/,http://localhost:8000/eureka/  #有多个eurekaserver之间用,隔开
  instance:
    prefer-ip-address: true #使用ip地址注册
    instance-id: ${spring.cloud.client.ip-address}:${server.port} #向注册中心中注册服务id
    #lease-renewal-interval-in-seconds: 5 #向注册中心中注册服务id
    #lease-expiration-duration-in-seconds: 10 #续约到期的时间

#logging:
#  level:
#    root: debug

#修改ribbon的负载均衡策略   
#服务名:
# ribbon:
#	NFLoadBalancerRuleClassName: 策略
#例子
#service-product:
#  ribbon:
#    #NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
#    ConnectTimeout: 250 # Ribbon的连接超时时间
#    ReadTimeout: 1000 # Ribbon的数据读取超时时间
#    OkToRetryOnAllOperations: true # 是否对所有操作都进行重试
#    MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
#    MaxAutoRetries: 1 # 对当前实例的重试次数
```

#### 1.4 Controller

```java
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Value("${server.port}")
	private String port;

	@Value("${spring.cloud.client.ip-address}") //spring cloud 自动的获取当前应用的ip地址
	private String ip;

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Product findById(@PathVariable Long id) {
		Product product = productService.findById(id);
		product.setProductName("访问的服务地址:"+ip + ":" + port);
		return product;
	}

	@RequestMapping(value = "",method = RequestMethod.POST)
	public String save(@RequestBody Product product) {
		productService.save(product);
		return "保存成功";
	}
}
```

```java
@RestController
@RequestMapping("/order")
public class OrderController {

	//注入restTemplate对象
	@Autowired
	private RestTemplate restTemplate;   

	/**
	 * 注入DiscoveryClient :
	 *  springcloud提供的获取原数组的工具类
	 *      调用方法获取服务的元数据信息
	 *
	 */
	@Autowired
	private DiscoveryClient discoveryClient;


	/**
	 * 基于ribbon的形式调用远程微服务
	 *  1.使用@LoadBalanced声明RestTemplate
	 *  2.使用服务名称替换ip地址 比如这里的service-product
	 */
	@RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
	public Product findById(@PathVariable Long id) {
		Product product = null;
		product = restTemplate.getForObject("http://service-product/product/1",Product.class);
		return product;
	}
}
```

例子

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

####  1.5 启动类中RestTemplate配置

 RestTemplate

```java
@SpringBootApplication
@EntityScan("cn.lfj.order.entity")
public class OrderApplication {

	/**
	 * * @LoadBalanced : 是ribbon提供的负载均衡的注解
	 */
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class,args);
	}
}
```

#### 1.6 运行结果

![image-20231012135734355](02-springcloud.assets/image-20231012135734355.png)

![image-20231012141307042](02-springcloud.assets/image-20231012141307042.png)

![img](02-springcloud.assets/K[}9CLWPJDHTUG_PT6ZP9.png)

### Eureka中的元数据

Eureka的元数据有两种：标准元数据和自定义元数据。

+ 标准元数据：主机名、IP地址、端口号、状态页和健康检查等信息，这些信息都会被发布在服务注
  册表中，用于服务之间的调用。
+ 自定义元数据：可以使用eureka.instance.metadata-map配置，符合KEY/VALUE的存储格式。这
  些元数据可以在远程客户端中访问。

在程序中可以使用DiscoveryClient 获取指定微服务的所有元数据信息

```java
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTemplateTest {
	@Autowired
	private DiscoveryClient discoveryClient;
    @Test
    public void test() {
        //根据微服务名称从注册中心获取相关的元数据信息
        List<ServiceInstance> instances = discoveryClient.getInstances("shopservice-
        product");
        for (ServiceInstance instance : instances) {
        	System.out.println(instance);
        }
    }
}
```

### Eureka的细节

EurekaClient启动是，将自己的信息注册到EurekaServer上，EurekaSever就会存储上EurekaClient的注册信息。 当EurekaClient调用服务时，本地没有注册信息的缓存时，去EurekaServer中去获取注册信息。 EurekaClient会通过心跳的方式去和EurekaServer进行连接。（默认30sEurekaClient会发送一次心跳请求，如果超过了90s还没有发送心跳信息的话，EurekaServer就认为你宕机了，将当前EurekaClient从注册表中移除）

+ EurekaClient会每隔30s去EurekaServer中去更新本地的注册表

+ Eureka的自我保护机制，统计15分钟内，如果一个服务的心跳发送比例低于85%，EurekaServer就会开启自我保护机制
  + 不会从EurekaServer中去移除长时间没有收到心跳的服务。
  + EurekaServer还是可以正常提供服务的。
  + 网络比较稳定时，EurekaServer才会开始将自己的信息被其他节点同步过去
  + 一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）验证完自我保护机制开启后，并不会马上呈现到web上，而是默认需等待 5 分钟（可以通过
    eureka.server.wait-time-in-ms-when-sync-empty 配置），即 5 分钟后你会看到下面的提示信息：

<img src="02-springcloud.assets/image-20231012200956234.png" alt="image-20231012200956234" style="zoom: 50%;" />

​                如果关闭自我保护: 通过设置eureka.enableSelfPreservation=false 来关闭自我保护功能。

+ CAP定理，C - 一致性，A-可用性，P-分区容错性，这三个特性在分布式环境下，只能满足2个，而且分区容错性在分布式环境下，是必须要满足的，只能在AC之间进行权衡。

  + 如果选择CP，保证了一致性，可能会造成你系统在一定时间内是不可用的，如果你同步数据的时间比较长，造成的损失大。

  + Eureka就是一个AP的效果，高可用的集群，Eureka集群是无中心，Eureka即便宕机几个也不会影响系统的使用，不需要重新的去推举一个master，也会导致一定时间内数据是不一致。

+ BASE理论:  是对CAP的扩充，在强一致性和高可用性无法同时满足的情况下，寻求一种中间解决方案。其中就有最终一致性的表述。

  + BA: Basic Availability基本可用性；

  + S: Soft state柔性状态；

  + E: Eventual consistency最终一致性；

  + ```java
    //基本可用性（ Basic Availability）:
    也就是允许有一定的异常。比如第一次刷出现网络异常，再次刷新就好了。保持基本可用就行。到底允许多大的异常占比，就看用户的接受度了。
    
    //柔性状态（Soft state）:
    柔性状态，或者软状态，也就是一种中间状态；其实就是在无法做到强一致性的情况下，在数据还没有同步之前，允许存在的一个状态。
    
    //最终一致性（Eventual consistency）:
    也就是经过一段时间之后，达成一致。这里的一段时间可以是几秒、也可能是几分钟、甚至几小时。（此过程是异步进行的）
    读己之写一致性:
    	自己能马上读取到自己写入的内容。比如博客，提交之后，自己马上可以看到，其他人可能要等一会才能看到。
    因果一致性:
    	因果一致性，也就是一种逻辑顺序上的一致性。比如先有问题才有回答。因为一致性不是很及时、或者存在地区差异导致数据同步差异，例如：北京网友提问，上海网友看到了并回答了问题。因为某些原因，上海网友的回答快。读同步到了南京地区，但北京的问题还没有同步到南京。这时就保证先有问题才能看到回答。问题没有同步、回答同步了，逻辑上也不能只显示回答，而没有问题。这时就要进行因果一致性。
    
    在一致性研究上，还出现过很多类似的一致性名词，类似：[Sequential 和 Linearizability]
    
    //总结:
    BASE理论是CAP的实践指导理论。因为对于系统来说，最好就是同时满足CAP，但是强CAP满足不了，我们就折中选择弱CAP，也就是BASE理论。
    在BASE理论中，我们关注的是最终一致性。需要研究的也就是最终一致性。
    ```

### Eureka的高可用

如果程序的正在运行，突然Eureka宕机了。

+ 如果调用方访问过一次被调用方了，Eureka的宕机不会影响到功能。
+ 如果调用方没有访问过被调用方，Eureka的宕机就会造成当前功能不可用。

Eureka Server可以通过运行多个实例并相互注册的方式实现高可用部署，Eureka Server实例会彼此增
量地同步信息，从而确保所有节点数据一致。事实上，节点之间相互注册是Eureka Server的默认行
为。

<img src="02-springcloud.assets/image-20231012212923767.png" alt="image-20231012212923767" style="zoom: 80%;" />



#### 搭建 Eureka Server高可用集群

准备多台Eureka, 让服务注册到多台**Eureka**

```yaml
#指定应用名称
spring:
	application:
		name: shop-eureka-server
---
#执行peer1的配置信息
spring:
	profiles: eureka1
server:
	port: 8761
eureka:
	instance:
		hostname: eureka1
	client:
		service-url:
			defaultZone: http://eureka2:8762/eureka
---

#执行peer2的配置信息
spring:
	profiles: eureka2
server:
	port: 8762
eureka:
	instance:
		hostname: eureka2
	client:
		service-url:
			defaultZone: http://eureka1:8761/eureka
```

在配置文件中通过连字符（---）将文件分为三个部分，第一部分为应用名称，第二部分和第三部
分是根据不同的profiles选项动态添加，可以在IDEA启动时进行激活配置使用IDEA启动历次EurekaServerApplicaion分别激活eureka1和eureka2配置。

访问http://eureka1:8761和http://eureka1:8762/。会发现注册中心SHOP-EUREKA-SERVER 已经有两个节点，并且registered-replicas (相邻集群复制节点)中已经包含对方。

#### 服务注册到Eureka Server集群

如果需要将微服务注册到Eureka Server集群只需要修改yml配置文件即可让多台Eureka之间相互通讯

```yaml
eureka:
  client:
    register-with-eureka: true      # 注册到Eureka上
    fetch-registry: true           # 从Eureka拉取信息
     service-url:
      defaultZone: http://eureka1:8761/eureka/,http://eureka1:8761/eureka/  # Eureka Server端口地址
```

### Eureka中的常见问题

服务注册慢
默认情况下，服务注册到Eureka Server的过程较慢。SpringCloud官方文档中给出了详细的原因

![image-20231012214841562](02-springcloud.assets/image-20231012214841562.png)大致含义：服务的注册涉及到心跳，默认心跳间隔为30s。在实例、服务器、客户端都在本地缓存中具
有相同的元数据之前，服务不可用于客户端发现（所以可能需要3次心跳）。可以通过配置
eureka.instance.leaseRenewalIntervalInSeconds (心跳频率)加快客户端连接到其他服务的过
程。在生产中，最好坚持使用默认值，因为在服务器内部有一些计算，他们对续约做出假设。

服务节点剔除问题
默认情况下，由于Eureka Server剔除失效服务间隔时间为90s且存在自我保护的机制。所以不能有效而
迅速的剔除失效节点，这对开发或测试会造成困扰。解决方案如下：
Eureka Server：
配置关闭自我保护，设置剔除无效节点的时间间隔

```yaml
eureka:
    instance:
    	hostname: eureka1
    client:
    	service-url:
    		defaultZone: http://eureka2:8762/eureka
    server:
    	enable-self-preservation: false #关闭自我保护
    	eviction-interval-timer-in-ms: 4000 #剔除时间间隔,单位:毫秒
```

Eureka Client：
配置开启健康检查，并设置续约时间

```yaml
eureka:
    client:
        healthcheck: true #开启健康检查(依赖spring-boot-actuator)
        serviceUrl:
        	defaultZone: http://eureka1:8761/eureka/,http://eureka1:8761/eureka/
    instance:
        preferIpAddress: true
        lease-expiration-duration-in-seconds: 10 #eureka client发送心跳给server端后，续
        约到期时间（默认90秒）
        lease-renewal-interval-in-seconds: 5 #发送心跳续约间隔
```



监控页面显示ip
在Eureka Server的管控台中，显示的服务实例名称默认情况下是微服务定义的名称和端口。为了更好
的对所有服务进行定位，微服务注册到Eureka Server的时候可以手动配置示例ID。配置方式如下

```yaml
eureka:
	instance:
		instance-id: ${spring.cloud.client.ip-address}:${server.port}
#spring.cloud.client.ip-address:获取ip地址
```

![image-20231012214933786](02-springcloud.assets/image-20231012214933786.png)

### Eureka的安全性

实现Eureka认证

导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

编写config配置类

```java
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

```properties
# 指定用户名和密码
spring:
  security:
    user:
      name: root
      password: root
```

其他服务想注册到Eureka上需要添加用户名和密码

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://用户名:密码@localhost:8761/eureka
```

## 服务的注册与发现-Consul

Eureka闭源影响: 在Euraka的GitHub上，宣布Eureka 2.x闭源。近这意味着如果开发者继续使用作为 2.x 分支上现有工作repo 一部分发布的代码库和工件，则将自负风险。

### Eureka的替换方案

**Zookeeper**
ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实
现，是Hadoop和Hbase的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包
括：配置维护、域名服务、分布式同步、组服务等。

**Consul**
consul是近几年比较流行的服务发现工具，工作中用到，简单了解一下。consul的三个主要应用场景：
服务发现、服务隔离、服务配置。

**Nacos**
Nacos 是阿里巴巴推出来的一个新开源项目，这是一个更易于构建云原生应用的动态服务发现、配置管
理和服务管理平台。Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性
集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。Nacos 帮助您更敏捷和容易地
构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原
生范式) 的服务基础设施。

### consul 概述

<img src="02-springcloud.assets/image-20231013114719711.png" alt="image-20231013114719711" style="zoom: 50%;" />

Consul 是 HashiCorp 公司推出的开源工具，用于实现分布式系统的服务发现与配置。与其它分布式服
务注册与发现的方案，Consul 的方案更“一站式”，内置了服务注册与发现框 架、分布一致性协议实
现、健康检查、Key/Value 存储、多数据中心方案，不再需要依赖其它工具（比如 ZooKeeper 等）。
使用起来也较 为简单。Consul 使用 Go 语言编写，因此具有天然可移植性(支持Linux、windows和
Mac OS X)；安装包仅包含一个可执行文件，方便部署，与 Docker 等轻量级容器可无缝配合

**Consul 的优势：**

+ 使用 Raft 算法来保证一致性, 比复杂的 Paxos 算法更直接. 相比较而言, zookeeper 采用的是
  Paxos, 而 etcd 使用的则是 Raft。
+ 支持多数据中心，内外网的服务采用不同的端口进行监听。 多数据中心集群可以避免单数据中心
  的单点故障,而其部署则需要考虑网络延迟, 分片等情况等。 zookeeper 和 etcd 均不提供多数据中
  心功能的支持。
+ 支持健康检查。 etcd 不提供此功能。
+ 支持 http 和 dns 协议接口。 zookeeper 的集成较为复杂, etcd 只支持 http 协议。
+ 官方提供 web 管理界面, etcd 无此功能。
+ 综合比较, Consul 作为服务注册和配置管理的新星, 比较值得关注和研究。

**特性：**

+ 服务发现
+ 健康检查
+ Key/Value 存储
+ 多数据中心

### consul与Eureka的区别

（1）一致性
Consul强一致性（CP）

+ 服务注册相比Eureka会稍慢一些。因为Consul的raft协议要求必须过半数的节点都写入成功才认
  为注册成功
+ Leader挂掉时，重新选举期间整个consul不可用。保证了强一致性但牺牲了可用性。

Eureka保证高可用和最终一致性（AP）

+ 服务注册相对要快，因为不需要等注册信息replicate到其他节点，也不保证注册信息是否
  replicate成功
+ 当数据出现不一致时，虽然A, B上的注册信息不完全相同，但每个Eureka节点依然能够正常对外提
  供服务，这会出现查询服务信息时如果请求A查不到，但请求B就能查到。如此保证了可用性但牺
  牲了一致性。

（2）开发语言和使用

eureka就是个servlet程序，跑在servlet容器中
Consul则是go编写而成，安装启动即可

### consul的下载与安装

Consul 不同于 Eureka 需要单独安装，访问Consul 官网下载 Consul 的最新版本，我这里是consul1.5x。根据不同的系统类型选择不同的安装包，从下图也可以看出 Consul 支持所有主流系统。

<img src="02-springcloud.assets/image-20231013115651356.png" alt="image-20231013115651356" style="zoom: 33%;" />

在linux虚拟中下载consul服务

```
## 从官网下载最新版本的Consul服务
wget https://releases.hashicorp.com/consul/1.5.3/consul_1.5.3_linux_amd64.zip
##使用unzip命令解压
unzip consul_1.5.3_linux_amd64.zip
##将解压好的consul可执行命令拷贝到/usr/local/bin目录下
cp consul /usr/local/bin
##测试一下
consul
```

启动consul服务

```
##已开发者模式快速启动，-client指定客户端可以访问的ip地址
[root@node01 ~]# consul agent -dev -client=0.0.0.0
==> Starting Consul agent...
Version: 'v1.5.3'
Node ID: '49ed9aa0-380b-3772-a0b6-b0c6ad561dc5'
Node name: 'node01'
Datacenter: 'dc1' (Segment: '<all>')
Server: true (Bootstrap: false)
Client Addr: [127.0.0.1] (HTTP: 8500, HTTPS: -1, gRPC: 8502, DNS: 8600)
Cluster Addr: 127.0.0.1 (LAN: 8301, WAN: 8302)
Encrypt: Gossip: false, TLS-Outgoing: false, TLS-Incoming: false,
Auto-Encrypt-TLS: false
```

启动成功之后访问： http://IP:8500 ，可以看到 Consul 的管理界面

![image-20231013115810024](02-springcloud.assets/image-20231013115810024.png)

### consul的基本使用

Consul 支持健康检查,并提供了 HTTP 和 DNS 调用的API接口完成服务注册，服务发现，以及K/V存储
这些功能。接下来通过发送HTTP请求的形式来了解一下Consul

#### 服务注册与发现

**（1）注册服务**
通过postman发送put请求到http://192.168.74.101:8500/v1/catalog/register地址可以完成服务注册

```
{
    "Datacenter": "dc1",
    "Node": "node01",
    "Address": "192.168.74.102",
    "Service": {
        "ID":"mysql-01",
        "Service": "mysql",
        "tags": ["master","v1"],
        "Address": "192.168.74.102",
        "Port": 3306
    }
}
```

**（2）服务查询**

通过postman发送get请求到http://192.168.74.101:8500/v1/catalog/services查看所有的服务列表

![image-20231013120024776](02-springcloud.assets/image-20231013120024776.png)



通过postman发送get请求到http://192.168.74.101:8500/v1/catalog/service/服务名查看具体的服务详
情
![image-20231013120028965](02-springcloud.assets/image-20231013120028965.png)

**（3）服务删除**
通过postman发送put请求到http://192.168.74.101:8500/v1/catalog/deregister删除服务

```
{
    "Datacenter": "dc1",
    "Node": "node01",
    "ServiceID": "mysql-01"
}
```

#### Consul的KV存储

可以参照Consul提供的KV存储的API完成基于Consul的数据存储

<img src="02-springcloud.assets/image-20231013120210235.png" alt="image-20231013120210235" style="zoom:67%;" />

### 案例

#### 基于consul的服务注册

**（1）复制一份新的工程进行配置**
拷贝一份新的工程，起名为shop_consul_demo ，并导入相关的子模块

<img src="02-springcloud.assets/image-20231013120539875.png" alt="image-20231013120539875" style="zoom:67%;" />



**（2）修改微服务的相关pom文件**

修改每个服务里的pom文件，添加SpringCloud提供的基于Consul的依赖

```xml
<!--SpringCloud提供的基于Consul的服务发现-->
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
<!--actuator用于心跳检查-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

注意: 

+ spring-cloud-starter-consul-discovery 是SpringCloud提供的对consul支持的相关依赖。
+ spring-boot-starter-actuator 适用于完成心跳检测响应的相关依赖。

**（3）修改配置文件**

修改每个服务里的application.yml配置文件，添加consul服务发现的相关配置信息

```yaml
spring:
...省略
    cloud:
    	consul: #consul相关配置
    	host: 192.168.74.101 #ConsulServer请求地址
    	port: 8500 #ConsulServer端口
    	discovery:
            #是否注册
            register: true
            #实例ID
            instance-id: ${spring.application.name}-1
            #服务实例名称
            service-name: ${spring.application.name}
            #服务实例端口
            port: ${server.port}
            #健康检查路径
            healthCheckPath: /actuator/health
            #健康检查时间间隔
            healthCheckInterval: 15s
            #开启ip地址注册
            prefer-ip-address: true
            #实例的请求ip
            ip-address: ${spring.cloud.client.ip-address}
```

其中spring.cloud.consul 中添加consul的相关配置

+ host：表示Consul的Server的请求地址
+ port：表示Consul的Server的端口
+ discovery：服务注册与发现的相关配置
  + instance-id ： 实例的唯一id（推荐必填），spring cloud官网文档的推荐，为了保证生成一
    个唯一的id ，也可以换成
  + ${spring.application.name}:${spring.cloud.client.ipAddress}
  + prefer-ip-address：开启ip地址注册
  + ip-address：当前微服务的请求ip

**在控制台中查看服务列表**

打开ConsulServer的管理控制台，可以发现三个微服务已经全部注册到Consul中了。

![image-20231013121321656](02-springcloud.assets/image-20231013121321656.png)

#### 基于consul的服务发现
由于SpringCloud对Consul进行了封装。对于在消费者端获取服务提供者信息和Eureka是一致的。同样
使用DiscoveryClient 完成调用获取微服务实例信息

### consul高可用集群

<img src="02-springcloud.assets/image-20231013121655555.png" alt="image-20231013121655555" style="zoom: 67%;" />

此图是官网提供的一个事例系统图，图中的Server是consul服务端高可用集群，Client是consul客户
端。consul客户端不保存数据，客户端将接收到的请求转发给响应的Server端。Server之间通过局域网
或广域网通信实现数据一致性。每个Server或Client都是一个consul agent。Consul集群间使用了
GOSSIP协议通信和raft一致性算法。上面这张图涉及到了很多术语：
Agent——agent是一直运行在Consul集群中每个成员上的守护进程。通过运行 consul agent来启
动。
agent可以运行在client或者server模式。指定节点作为client或者server是非常简单的，除非有其
他agent实例。所有的agent都能运行DNS或者HTTP接口，并负责运行时检查和保持服务同步。
Client——一个Client是一个转发所有RPC到server的代理。这个client是相对无状态的。client唯
一执行的后台活动是加入LAN
gossip池。这有一个最低的资源开销并且仅消耗少量的网络带宽。
Server——一个server是一个有一组扩展功能的代理，这些功能包括参与Raft选举，维护集群状
态，响应RPC查询，与其他数据中心交互WANgossip和转发查询给leader或者远程数据中心。
DataCenter——虽然数据中心的定义是显而易见的，但是有一些细微的细节必须考虑。例如，在
EC2中，多个可用区域被认为组成一个数据中心？我们定义数据中心为一个私有的，低延迟和高带
宽的一个网络环境。这不包括访问公共网络，但是对于我们而言，同一个EC2中的多个可用区域可
以被认为是一个数据中心的一部分。
Consensus——在我们的文档中，我们使用Consensus来表明就leader选举和事务的顺序达成一
致。由于这些事务都被应用到有限状态机上，Consensus暗示复制状态机的一致性。
Gossip——Consul建立在Serf的基础之上，它提供了一个用于多播目的的完整的gossip协议。
Serf提供成员关系，故障检测和事件广播。更多的信息在gossip文档中描述。这足以知道gossip使
用基于UDP的随机的点到点通信。
LAN Gossip——它包含所有位于同一个局域网或者数据中心的所有节点。 WAN
Gossip——它只包含Server。这些server主要分布在不同的数据中心并且通常通过因特网或者广
域网通信。
在每个数据中心，client和server是混合的。一般建议有3-5台server。这是基于有故障情况下的可用性
和性能之间的权衡结果，因为越多的机器加入达成共识越慢。然而，并不限制client的数量，它们可以
很容易的扩展到数千或者数万台。
同一个数据中心的所有节点都必须加入gossip协议。这意味着gossip协议包含一个给定数据中心的所有
节点。这服务于几个目的：第一，不需要在client上配置server地址。发现都是自动完成的。第二，检
测节点故障的工作不是放在server上，而是分布式的。这是的故障检测相比心跳机制有更高的可扩展
性。第三：它用来作为一个消息层来通知事件，比如leader选举发生时。
每个数据中心的server都是Raft节点集合的一部分。这意味着它们一起工作并选出一个leader，一个有
额外工作的server。leader负责处理所有的查询和事务。作为一致性协议的一部分，事务也必须被复制
到所有其他的节点。因为这一要求，当一个非leader得server收到一个RPC请求时，它将请求转发给集
群leader。
server节点也作为WAN gossip Pool的一部分。这个Pool不同于LAN Pool，因为它是为了优化互联网更
高的延迟，并且它只包含其他Consul server节点。这个Pool的目的是为了允许数据中心能够以lowtouch
的方式发现彼此。这使得一个新的数据中心可以很容易的加入现存的WAN gossip。因为server都
运行在这个pool中，它也支持跨数据中心请求。当一个server收到来自另一个数据中心的请求时，它随
即转发给正确数据中想一个server。该server再转发给本地leader。
这使得数据中心之间只有一个很低的耦合，但是由于故障检测，连接缓存和复用，跨数据中心的请求都
是相对快速和可靠的。

#### Consul的核心知识

**Gossip协议**
传统的监控，如ceilometer，由于每个节点都会向server报告状态，随着节点数量的增加server的压力
随之增大。在所有的Agent之间（包括服务器模式和普通模式）运行着Gossip协议。服务器节点和普通
Agent都会加入这个Gossip集群，收发Gossip消息。每隔一段时间，每个节点都会随机选择几个节点发
送Gossip消息，其他节点会再次随机选择其他几个节点接力发送消息。这样一段时间过后，整个集群都
能收到这条消息。示意图如下。

<img src="02-springcloud.assets/image-20231013121804451.png" alt="image-20231013121804451" style="zoom:67%;" />

<img src="02-springcloud.assets/Gossip协议-169717256633112.gif" alt="Gossip协议" style="zoom:50%;" />

**RAFT一致性算法**

<img src="02-springcloud.assets/image-20231013121821723.png" alt="image-20231013121821723" style="zoom:80%;" />

为了实现集群中多个ConsulServer中的数据保持一致性，consul使用了基于强一致性的RAFT算法。
在Raft中，任何时候一个服务器可以扮演下面角色之一：

1. Leader: 处理所有客户端交互，日志复制等，一般一次只有一个Leader.
2. Follower: 类似选民，完全被动
3. Candidate（候选人）: 可以被选为一个新的领导人。
Leader全权负责所有客户端的请求，以及将数据同步到Follower中（同一时刻系统中只存在一个
Leader）。Follower被动响应请求RPC，从不主动发起请求RPC。Candidate由Follower向Leader转换
的中间状态

关于RAFT一致性算法有一个经典的动画http://thesecretlivesofdata.com/raft/，其中详细介绍了选
举，数据同步的步骤。

#### Consul 集群搭建

<img src="02-springcloud.assets/image-20231013121916672.png" alt="image-20231013121916672" style="zoom:80%;" />

首先需要有一个正常的Consul集群，有Server，有Leader。这里在服务器Server1、Server2、Server3
上分别部署了Consul Server。（这些服务器上最好只部署Consul程序，以尽量维护Consul Server的稳
定）

服务器Server4和Server5上通过Consul Client分别注册Service A、B、C，这里每个Service分别部署在
了两个服务器上，这样可以避免Service的单点问题。（一般微服务和Client绑定）
在服务器Server6中Program D需要访问Service B，这时候Program D首先访问本机Consul Client提供
的HTTP API，本机Client会将请求转发到Consul Server，Consul Server查询到Service B当前的信息返
回

<img src="02-springcloud.assets/image-20231013122013106.png" alt="image-20231013122013106" style="zoom:67%;" />

+ Agent 以 client 模式启动的节点。在该模式下，该节点会采集相关信息，通过 RPC 的方式向
  server 发送。Client模式节点有无数个，官方建议搭配微服务配置
+ Agent 以 server 模式启动的节点。一个数据中心中至少包含 1 个 server 节点。不过官方建议使用
  3 或 5 个 server 节点组建成集群，以保证高可用且不失效率。server 节点参与 Raft、维护会员信
  息、注册服务、健康检查等功能。

**（2） 安装consul并启动**

+ 在每个consul节点上安装consul服务，下载安装过程和单节点一致。

```
##从官网下载最新版本的Consul服务
wget https://releases.hashicorp.com/consul/1.5.3/consul_1.5.3_linux_amd64.zip
##使用unzip命令解压
unzip consul_1.5.3_linux_amd64.zip
##将解压好的consul可执行命令拷贝到/usr/local/bin目录下
cp consul /usr/local/bin
##测试一下
consul
```

+ 启动每个consul server节点

```
##登录s1虚拟机，以server形式运行
consul agent -server -bootstrap-expect 3 -data-dir /etc/consul.d -node=server-1
-bind=192.168.74.101 -ui -client 0.0.0.0 &
##登录s2 虚拟机，以server形式运行
consul agent -server -bootstrap-expect 2 -data-dir /etc/consul.d -node=server-2
-bind=192.168.74.102 -ui -client 0.0.0.0 &
##登录s3 虚拟机，以server形式运行
consul agent -server -bootstrap-expect 2 -data-dir /etc/consul.d -node=server-3
-bind=192.168.74.103 -ui -client 0.0.0.0 &
```

-server： 以server身份启动。
-bootstrap-expect：集群要求的最少server数量，当低于这个数量，集群即失效。
-data-dir：data存放的目录，更多信息请参阅consul数据同步机制
-node：节点id，在同一集群不能重复。

-bind：监听的ip地址。
-client：客户端的ip地址(0.0.0.0表示不限制)
& ：在后台运行，此为linux脚本语法
至此三个Consul Server模式服务全部启动成功

```
##在本地电脑中使用client形式启动consul
consul agent -client=0.0.0.0 -data-dir /etc/consul.d -node=client-1
```

**（3） 每个节点加入集群**

在s2，s3，s4 服务其上通过consul join 命令加入 s1中的consul集群中

```
##加入consul集群
consul join 192.168.74.101
```

**（4） 测试**

在任意一台服务器中输入 consul members查看集群中的所有节点信息

```
##查看consul集群节点信息
consul members
```

![image-20231013122520940](02-springcloud.assets/image-20231013122520940.png)

![image-20231013122544153](02-springcloud.assets/image-20231013122544153.png)

### Consul 常见问题

**（1）节点和服务注销**

当服务或者节点失效，Consul不会对注册的信息进行剔除处理，仅仅标记已状态进行标记（并且不可使
用）。如果担心失效节点和失效服务过多影响监控。可以通过调用HTTP API的形式进行处理
节点和服务的注销可以使用HTTP API:

+ 注销任意节点和服务：/catalog/deregister
+ 注销当前节点的服务：/agent/service/deregister/:service_id

如果某个节点不继续使用了，也可以在本机使用consul leave命令，或者在其它节点使用consul forceleave
节点Id。

**（2）健康检查与故障转移**

在集群环境下，健康检查是由服务注册到的Agent来处理的，那么如果这个Agent挂掉了，那么此节点
的健康检查就处于无人管理的状态。

从实际应用看，节点上的服务可能既要被发现，又要发现别的服务，如果节点挂掉了，仅提供被发现的
功能实际上服务还是不可用的。当然发现别的服务也可以不使用本机节点，可以通过访问一个Nginx实
现的若干Consul节点的负载均衡来实现。

### ③服务的注册与实现-nacos

见[SpringCloud alibaba笔记](./06-SpringCloud alibaba.md)



# 负载均衡-Ribbon

## Ribbon概要

是 Netflixfa 发布的一个负载均衡器，有助于控制 HTTP 和 TCP客户端行为。在 SpringCloud 中，
Eureka一般配合Ribbon进行使用，Ribbon提供了客户端负载均衡的功能，Ribbon利用从Eureka中读
取到的服务信息，在调用服务节点提供的服务时，会合理的进行负载。
在SpringCloud中可以将注册中心和Ribbon配合使用，Ribbon自动的从注册中心中获取服务提供者的
列表信息，并基于内置的负载均衡算法，请求服务

## Ribbon的主要作用

服务调用

+ 基于Ribbon实现服务调用， 是通过拉取到的所有服务列表组成（服务名-请求路径的）映射关系。借助RestTemplate 最终进行调用

负载均衡

+ 当有多个服务提供者时，Ribbon可以根据负载均衡的算法自动的选择需要调用的服务地址

## @LoadBalanced注解

使用Ribbon完成客户端负载均衡往往是从一个注解开始的

```java
/**
* 基于Ribbon的服务调用与负载均衡
*/
@LoadBalanced
@Bean
public RestTemplate getRestTemplate() {
	return new RestTemplate();
}
```

这个注解的主要作用，查看源码

```java
/**
* Annotation to mark a RestTemplate bean to be configured to use a
LoadBalancerClient
* @author Spencer Gibb
*/
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface LoadBalanced {
}
```

通过注释可以知道@LoadBalanced注解是用来给RestTemplate做标记，方便我们对RestTemplate添加
一个LoadBalancerClient，以实现客户端负载均衡。

## 案例

基于Ribbon实现订单调用商品服务

坐标依赖

在springcloud提供的服务发现的jar中以及包含了Ribbon的依赖。所以这里不需要导入任何额外的坐标

### 服务提供者

修改**shop_service_product**模块中ProductController#findById() 方法如下

```java
@Value("${server.port}")
private String port;

@Value("${spring.cloud.client.ip-address}")
private String ip;

@GetMapping("/{id}")
public Product findById(@PathVariable Long id) {
    Product product = productService.findById(id);
    //设置端口
    product.setProductDesc("调用shop-service-product服务,ip:"+ip+",服务提供者端口:"+port);
    return product;
}
```

### 服务消费者

修改服务消费者**shop_service_order**模块中的启动类OrderApplication ，在创建RestTemplate方法上添加@LoadBalanced 注解

```java
    /**
    * 基于Ribbon的服务调用与负载均衡
    */
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
```

在shop_service_order的OrderController 中添加下单方法，并使用RestTemplate完成服务调用

```java
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/buy/{id}")
    public Product order() {
        //通过restTemplate调用商品微服务
        //Product product =
        restTemplate.getForObject("http://127.0.0.1:9002/product/1", Product.class);
        
        // 调用shop_service_product服务,  服务名替代以前的ip地址
        Product product = restTemplate.getForObject("http://shop-serviceproduct/product/1", Product.class);
        
    	return product;
	}
```

代码测试

浏览器中请求http://localhost:9001/order/buy/1查看展示效果如下，已经可以在订单微服务中已服务
名称的形式调用商品微服务获取数据
![image-20231013124118699](02-springcloud.assets/image-20231013124118699.png)

## Ribbon-负载均衡原理

![image-20231012145943929](02-springcloud.assets/image-20231012145943929.png)

流程

![image-20231012150023178](02-springcloud.assets/image-20231012150023178.png)



在搭建网站时，如果单节点的 web服务性能和可靠性都无法达到要求；或者是在使用外网服务时，经常
担心被人攻破，一不小心就会有打开外网端口的情况，通常这个时候加入负载均衡就能有效解决服务问
题。

负载均衡是一种基础的网络服务，其原理是通过运行在前面的负载均衡服务，按照指定的负载均衡算
法，将流量分配到后端服务集群上，从而为系统提供并行扩展的能力。

负载均衡的应用场景包括流量包、转发规则以及后端服务，由于该服务有内外网个例、健康检查等功
能，能够有效提供系统的安全性和可用性。

<img src="02-springcloud.assets/image-20231013142846121.png" alt="image-20231013142846121" style="zoom: 80%;" />

## 客户端负载均衡与服务端负载均衡

**服务端负载均衡**

先发送请求到负载均衡服务器或者软件，然后通过负载均衡算法，在多个服务器之间选择一个进行访
问；即在服务器端再进行负载均衡算法分配

**客户端负载均衡**

客户端会有一个服务器地址列表，在发送请求前通过负载均衡算法选择一个服务器，然后进行访问，这
是客户端负载均衡；即在客户端就进行负载均衡算法分配

## Ribbon-负载均衡策略

![img](02-springcloud.assets/1653807308164-8b996298-f327-4d8e-9a80-551e56d7b77d.png)

![image-20231012162831091](02-springcloud.assets/image-20231012162831091.png)

第一种：作用全部微服务

第二种：作用某个微服务

![img](02-springcloud.assets/1653814544318-3addd47b-3de3-47da-ac1a-0c993a1f1fdc.png)

```xml
#修改ribbon的负载均衡策略   
服务名:
 ribbon:
	NFLoadBalancerRuleClassName: 策略
```

例子

```xml
service-product:
  ribbon:
	NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
    ConnectTimeout: 250 # Ribbon的连接超时时间
    ReadTimeout: 1000 # Ribbon的数据读取超时时间
    OkToRetryOnAllOperations: true # 是否对所有操作都进行重试
    MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
    MaxAutoRetries: 1 # 对当前实例的重试次数
```

## Ribbon-饥饿加载

![img](02-springcloud.assets/1653823307515-a92e8446-73a9-4f31-a647-1ed0ebfd8768.png)

```xml
ribbon:
    eager-load:
        enabLed: true # 开启饥饿加载
        clients: 
			-userservice # 指定对userservice这个服务饥饿加载
			-xxxservice  # 指定对xxxservice这个服务饥饿加载
```

## 自动装配

根据SpringBoot中的自动装配规则可以在spring-cloud-netflix-ribbon-2.1.0.RELEASE.jar 中可以找到spring.factories

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
```

找到自动装配的类RibbonAutoConfiguration

```java
@Configuration
@Conditional({RibbonAutoConfiguration.RibbonClassesConditions.class})
@RibbonClients
@AutoConfigureAfter(
name =
{"org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration"}
)
@AutoConfigureBefore({LoadBalancerAutoConfiguration.class,
AsyncLoadBalancerAutoConfiguration.class})
@EnableConfigurationProperties({RibbonEagerLoadProperties.class,
ServerIntrospectorProperties.class})
public class RibbonAutoConfiguration {
    @Bean
    public SpringClientFactory springClientFactory() {
    SpringClientFactory factory = new SpringClientFactory();
    factory.setConfigurations(this.configurations);
    return factory;
    }
    @Bean
    @ConditionalOnMissingBean({LoadBalancerClient.class})
        public LoadBalancerClient loadBalancerClient() {
        return new RibbonLoadBalancerClient(this.springClientFactory());
    }
    //省略
}
```







```xml
    <!--对应的nacos版本为 2.0.4, Sentinel Version 1.8.5, RocketMQ Version 4.9.4,Seata Version 1.5.2 -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
```





