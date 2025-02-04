# 持久化框架-JPA & MyBatis

## 1. 选择JPA：

- 当您的项目需要遵循Java的标准规范，并且希望代码更加面向对象时，JPA可能是更好的选择。
- 如果您的团队对ORM框架有经验，可以更容易地上手JPA。
- 当您希望更少地编写SQL查询并且使用JPQL进行查询时，JPA可能更适合。

## 2. 选择MyBatis：

- 如果您的项目需要执行复杂的SQL查询或需要更多的性能控制，MyBatis可能是更好的选择。
- 如果您的团队对SQL编写和数据库性能优化有丰富的经验，可以考虑使用MyBatis。
- 当您需要处理与数据库交互更多细节时，MyBatis可以提供更灵活的选择。

# SpringBoot 整合 MyBatis

[MyBatis-Plus官方](https://baomidou.com/)

## 整合SSM场景——方式一

SpringBoot 整合 Spring、SpringMVC、MyBatis 进行数据访问场景开发

在 MyBatis 3.3.0 版本及以后，对于 MyBatis 实体类不再强制要求手动实现 `Serializable` 接口。不再需要为你的 MyBatis 实体类显式添加 `implements Serializable`。

### 1. 创建SSM整合项目

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.1</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

<img src="SpringBoot3-数据访问.assets/image-20230906082137094.png" alt="image-20230906082137094" style="zoom:67%;" />

### 2. 配置数据源

application.properties

```properties
spring.datasource.url=jdbc:mysql://192.168.200.100:3306/demo
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
```

安装MyBatisX 插件，帮我们生成Mapper接口的xml文件即可

### 3. 配置MyBatis

```properties
#指定mapper映射文件位置
mybatis.mapper-locations=classpath:/mapper/*.xml
#参数项调整,打开驼峰命名规则
mybatis.configuration.map-underscore-to-camel-case=true
```

**1,2总结:**

```properties
# 1、先配置数据源信息
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=123456


# 2、配置整合MyBatis
mybatis.mapper-locations=classpath:/mapper/*.xml
# 打开驼峰命名规则
mybatis.configuration.map-underscore-to-camel-case=true
```

### 4. 简单使用

+ 配置文件

  ```properties
  # 1、先配置数据源信息
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.type=com.zaxxer.hikari.HikariDataSource
  spring.datasource.url=jdbc:mysql://localhost:3306/test
  spring.datasource.username=root
  spring.datasource.password=123456
  
  
  # 2、配置整合MyBatis
  mybatis.mapper-locations=classpath:/mapper/*.xml
  # 打开驼峰命名规则
  mybatis.configuration.map-underscore-to-camel-case=true
  ```

+ **入口函数**

  ```java
  package com.atguigu.boot3.ssm;
  
  import com.atguigu.boot3.starter.robot.annotation.EnableRobot;
  import org.mybatis.spring.annotation.MapperScan;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  /**
   * 1、@MapperScan【批量扫描注解】； 告诉MyBatis，扫描哪个包下面的所有接口
   * 2、使用mybatis.mapper-locations，告诉MyBatis，每个接口的xml文件都在哪里
   * 3、MyBatis自动关联绑定。
   */
  @EnableRobot
  @MapperScan(basePackages = "com.atguigu.boot3.ssm.mapper")   //添加扫描
  @SpringBootApplication
  public class Boot305SsmApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(Boot305SsmApplication.class, args);
      }
  
  }
  ```

- **编写entity**

  <font  color = red>entity包：数据库表的实体对象</font>

  ```java
  package com.atguigu.boot3.ssm.entity;
  
  import lombok.Data;
  
  @Data  // 自动生成 get put ....
  @NoArgsConstructor //自动生成无参构造器
  @AllArgsConstructor //自动生成全参构造器
  public class TUser {  // 和数据库的字段对应起来
      private Long id;
      private String loginName;
      private String nickName;
      private String passwd;
  }
  ```

- **编写Mapper**

  + 方法一:  使用MapperXML

    使用`mybatisx`插件，快速生成MapperXML

    ![image-20230708173420239](SpringBoot3-数据访问.assets/image-20230708173420239.png)

    ```java
    package com.atguigu.boot3.ssm.mapper;
    
    import com.atguigu.boot3.ssm.bean.TUser;
    import org.apache.ibatis.annotations.Param;
    
    @Mapper
    public interface UserMapper {
    
        /**
         * 1、每个方法都在Mapper文件中有一个sql标签对应。
         * 2、所有参数都应该用@Param进行签名，以后使用指定的名字在SQL中取值
         */
        TUser getUserById(@Param("id") Long id);
    }
    ```

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
    <mapper namespace="com.atguigu.boot3.ssm.mapper.UserMapper">
        <!--    接口的全类名和namespace的值是一一对应的
                select id,login_name loginName,nick_name nickName,passwd from t_user where id=#{id}
        -->
    
        <select id="getUserById" resultType="com.atguigu.boot3.ssm.bean.TUser">
            select *
            from t_user
            where id = #{id}
        </select>
    </mapper>
    ```

  + 方法二:  使用注解

    ```java
    package com.qingge.springboot.mapper;
    import com.qingge.springboot.entity.User;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Select;
    import java.util.List;
    
    @Mapper
    public interface UserMapper{
        @Select("SELECT fron sys_user")  //
        List<User>findALl();
        
        int insert();
    }
    ```

- 测试代码

  ```java
  package com.atguigu.boot3.ssm.controller;
  
  import com.atguigu.boot3.ssm.bean.TUser;
  import com.atguigu.boot3.ssm.mapper.UserMapper;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RestController;
  
  
  @RestController
  public class UserController {
  
      @Autowired
      UserMapper userMapper; //
  
  
      /**
       * 返回User的json数据
       * @param id
       * @return
       */
      @GetMapping("/user/{id}")
      public TUser getUser(@PathVariable("id") Long id){  //前端的动态值传给id
          TUser user = userMapper.getUserById(id);
          return user;
      }
  }
  ```

## 整合SSM场景——方式二:crossed_swords:

### IDEA中使用自带的数据库(重要)

<img src="SpringBoot3-数据访问.assets/image-20231011211119939.png" alt="image-20231011211119939" style="zoom:67%;" />

> 首先打开IDEA页面
> <img src="SpringBoot3-数据访问.assets/image-20230911234743463.png" alt="image-20230911234743463" style="zoom: 80%;" />

> 添加MySQL数据库
>
> <img src="SpringBoot3-数据访问.assets/image-20230911234811564.png" alt="image-20230911234811564" style="zoom: 80%;" />

> 配置
> ![image-20230911234829000](SpringBoot3-数据访问.assets/image-20230911234829000.png)

> 选择自己用的数据库
>
> ![image-20230911234909372](SpringBoot3-数据访问.assets/image-20230911234909372.png)

> 查看数据库中的表结构
> ![image-20230911234924676](SpringBoot3-数据访问.assets/image-20230911234924676.png)

> 查看表的SQL语句
>
> ![image-20230911234939139](SpringBoot3-数据访问.assets/image-20230911234939139.png)

> 新建查询，执行SQL语句
> ![image-20230911233447754](SpringBoot3-数据访问.assets/image-20230911233447754.png)

### MyBatisX 插件

![image-20230911233548515](SpringBoot3-数据访问.assets/image-20230911233548515.png)

设置项目基本路径、包路径、[编码格式](https://so.csdn.net/so/search?q=编码格式&spm=1001.2101.3001.7020)，实体类包名称、实体类名![image-20230911234703690](SpringBoot3-数据访问.assets/image-20230911234703690.png)

配置生成mapper文件，service文件的位置

![image-20230912170343770](SpringBoot3-数据访问.assets/image-20230912170343770.png)

ptions选项作用大致如下：

- comment：可能和生成java doc comments有关，没有效果。
- toString/hashCode/equals：是否生成相应的方法，建议勾选(和Lombok选择一个)。
- Lombok：勾选后实体类自动添加Lombok的@Data注解，建议勾选（要先安装Lombok插件）
- Actual Column：勾选后，生成的实体类属性名和表中字段名大小写会保持一致。例如，表中有字段Name，勾选该选项后生成的属性名也为Name，未勾选则为name，建议根据实际需要勾选。
- Actual Column Annotation：是否对所有属性名都加上注解标明对应字段，例如@TableName，建议勾选。
- JSR310:Data API：是否使用新标准的时间日期API，包括 Instant、Clock、LocalDateTime、DateTimeFormatter、ZonedDateTime 以及替换 Calendar 的 Chronology 等类。建议勾选（新标准的时间日期API比老版本友好多了，强烈建议使用新版时间日期API）

### 快速生成

官网 [代码生成器（新） | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/779a6e/#快速入门)

#### ①  pom.xml

出现问题: [DataSource dataSource中dataSource下方报红线 和 com.zaxxer.hikari.hikaridatasource 报红](https://blog.csdn.net/liu_xin_xin/article/details/119613612)

**其核心引入:**

```xml
        <!--用于 DataSource.class 和 使用JdbcTemplate, 一般不需要添加
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
		-->

		<!-- mysql -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>	    
    	<!-- mybatisplus+数据库相关开始-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-core</artifactId>
            <version>3.5.3.1</version>
        </dependency>
	    <!--使用mybatisplus常用CRUD接口方式,可以不用写sql语句-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>
		<!--使用mybatisplus代码生成器, 具体用法见官网-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.5.3.1</version>
        </dependency>
    	 <!--mybatisplus+数据库相关结束-->

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

全部内容

```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>experiment01</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>experiment01</name>
    <description>experiment01</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>3.0.2</version>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter-test</artifactId>
            <version>3.0.2</version>
            <scope>test</scope>
        </dependency>
        <!--        mybatisplus+数据库相关开始-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-core</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <!--        mybatisplus+数据库相关结束-->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

#### ②  **mapper包**

**添加@Mapper注解**

注意: 有时候需要在主类添加@MapperScan("com.quartz.demo.mapper") // 指定mapper接口所在的包

#### ③  更新配置

```properties
#在较新的MySQL驱动版本中，使用com.mysql.cj.jdbc.Driver来代替com.mysql.jdbc.Driver作为数据库驱动的类名是推
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis
spring.datasource.username=root
spring.datasource.password=741106

#mybatis配置
#指定mapper映射文件位置
mybatis.mapper-locations=classpath:/mapper/*.xml
#参数项调整,打开驼峰命名规则
mybatis.configuration.map-underscore-to-camel-case=true

# web 端口
server.port=9000
```

或者

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://IP:PORT/DB_NAME?useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=CTT&allowMultiQueries=true
       type: com.zaxxer.hikari.HikariDataSource
    username: 用户名
    password: 密码
    
#mybatis-plus配置
mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
  configuration:
    # sql日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

不需要写返回值，只需要写方法名就能够快速生成CRUD

![在这里插入图片描述](SpringBoot3-数据访问.assets/45623756db034e079a06a8c2c824dac0.png)

输入insertSelective之后立即点击“**Alt+Enter**”即可mapper文件中自动生成sql

![image-20230911234021608](SpringBoot3-数据访问.assets/image-20230911234021608.png)

![image-20230911234044862](SpringBoot3-数据访问.assets/image-20230911234044862.png)

### 基础代码

**IService的使用：**

IService的使用需要另外两个接口的配合：`baseMapper`和`ServiceImpl`

第一步：实现basemapper接口

```java
public interface AdminMapper extends BaseMapper<Admin> {
     ClassDetail getById(Serializable id);
}
```

第二步：编写service类

```java
public interface AdminService extends IService<Admin> {
    ClassDetail getById(Serializable id);
}
```

第三步：编写serviceImpl，ServiceImpl里面是各种的方法实现，好奇的可以点进源码看下，两个泛型需要注意的，第一个是继承basemapper的(AdminMapper)，第二个是实体类(Admin)。

```java
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> 
    						  implements AdminService {
    @Autowired
    ClassDetailMapper classDetailMapper;

    @Override
    public ClassDetail getById(Serializable id) {
        return classDetailMapper.getDetailById(id);
    }
}

public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {...}


//或者使用 this.getBaseMapper().方法   等价于 ClassDetailMapper classDetailMapper;
```

第四步：愉快的使用啦，可以参考[IService接口解释](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/service/IService.java)或者[Mybatis-plus官网](https://mp.baomidou.com/guide/crud-interface.html#service-crud-接口)的方法解释来调用。

```java
	@Autowired
    AdminService adminService;

	void test11(){
		// adminService中有很多方法, 因为extends ServiceImpl<AdminMapper,Admin> 
        Admin admin = adminService.getById(13);
    }
```

### 所需注解

```java
//1
@Mapper
public interface UserMapper {
}


// 2
@MapperScan(basePackages = "com.atguigu.boot3.ssm.mapper")   //添加扫描
@SpringBootApplication
public class SpringBackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBackEndApplication.class, args);
 }
```

### 时间数据

#### 方法1:  @JsonFormat 注解

<strong  style="color:red">手动添加 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")</strong>

```java
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * 
 * @TableName timetest
 */
@TableName(value ="timetest")
@Data
public class Timetest implements Serializable {
    /**
     * 
     */
    @TableField(value = "Date")
    private LocalDate date;

    /**
     * 
     */
    @TableField(value = "Time")
    private LocalTime time;

    /**
     * 
     */
    @TableField(value = "DateTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //原始数据为2023-12-27T10:38:51 格式化为2023-12-27 10:38:51
	private LocalDateTime datetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
```

#### 方法2: 配置类+@JsonFormat 注解 [推荐]

使用 `@JsonComponent` 注解自定义一个全局格式化类，分别对 `Date`(旧的 API，不建议在新代码中使用) 和 `LocalDate` 类型做格式化处理。

搭配`@JsonFormat` 注解的优先级比较高，会以 `@JsonFormat` 注解的时间格式为主。

**在搭配 下述注解进行序列化反序列化操作(比如entity中时间数据存入Redis中)**

```java
// 推荐 jackson  
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)

// 等价于 FastJSON 2.x 
  	@JSONField(serializeUsing = LocalDateTimeSerializer.class,
			deserializeUsing = LocalDateTimeDeserializer.class)
```

**编写配置类**

```java
/**
 * 全局时间格式化
 */
@JsonComponent
public class DateFormatConfig{

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    /**

     * @description date 类型全局时间格式化
     * @date 2020/8/31 18:22
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {

        return builder -> {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat(pattern);
            df.setTimeZone(tz);
            builder.failOnEmptyBeans(false)
                    .failOnUnknownProperties(false)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .dateFormat(df);
        };
    }

    /**

     * @description LocalDate 类型全局时间格式化
     * @date 2020/8/31 18:22
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(
            @Autowired LocalDateTimeSerializer localDateTimeSerializer) {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeSerializer);
    }
}
```

测试

```java
@Data
public class OrderDTO {
    private LocalDateTime createTime;    // yyyy-MM-dd HH:mm:ss  

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;  //yyyy-MM-dd 
}
```

注意: 其中的日期字段并没有被更新。

```java
public OrderDTO timeTest(OrderDTO orderDTO) {
    OrderDTO dto = new OrderDTO();
    dto.setCreateTime(LocalDateTime.now());
    dto.setUpdateTime(new Date());
    // 其中的日期字段并不会更新
    System.out.println(dto.getCreateTime());  //2024-01-20T15:13:49.662446900
    System.out.println(dto.getCreateTime());  //2024-01-20T15:13:49.662446900
    
    return dto; // 添加这行，将新创建的 OrderDTO 返回给调用方
}

OrderDTO dto = timeTest();
System.out.println(dto.getCreateTime());  //2024-01-20 15:13:49.662446900
System.out.println(dto.getCreateTime());  //2024-01-20
```

### mybatis中parameterType别名图

![image-20230921195943952](SpringBoot3-数据访问.assets/image-20230921195943952.png)

### 单表查询

#### 方式一:  CRUD接口方式 - Service (增删)

客瑞的

[MyBatis-Plus（九）Service的CRUD接口1：基本查询_listbyids_Zack_tzh的博客-CSDN博客](https://blog.csdn.net/Zack_tzh/article/details/107528997)

[【SpringBoot】MyBatis-Plus实现数据列表展示_springboot前端列表展示_刘婉晴的博客-CSDN博客](https://blog.csdn.net/liuwanqing233333/article/details/127539834)

 IService接口

```xml
  <!--使用mybatisplus常用接口,可以不用写sql语句-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>
```

常用基础方法详解

```java
package com.guigu.admin;
 
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guigu.admin.bin.User;
import com.guigu.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
 
import java.util.*;
 
 
@Slf4j
@SpringBootTest
public class BootWebAdminApplicationTest {
    @Autowired
    UserService userService;
 
    //userService.saveOrUpdate()  如果主键存在，则更新该记录的相关字段, 没有保存
    
    /**
     * [插入一条数据]
     * default boolean save(T entity)
     * 返回值boolean：true/false
     * 形参：实体类对象
     */
    @Test
    void insertOneSave() {
        User user = new User();
        user.setName("张峰");
        user.setAge(12L);
        user.setId(13L);
        user.setEmail("test13@baomidou.com");
        boolean save = userService.save(user);
        log.info("插入数据结果{}", save);
    }
 
    /**
     * [批量插入数据]
     * default boolean saveBatch(Collection<T> entityList)
     * 返回值：boolean：true/false
     * 形参：List<实体类泛型>
     */
    @Test
    void inserts() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("林燕", 15L, "test14@baomidou.com"));
        users.add(new User(15L, "林玲", 15L, "test15@baomidou.com"));
        users.add(new User(16L, "林灵", 15L, "test16@baomidou.com"));
        boolean b = userService.saveBatch(users);
        log.info("批量插入数据结果{}", b);
    }
 
    /**
     * [批量：根据主键修改值]
     * default boolean saveOrUpdateBatch(Collection<T> entityList)
     * 返回值：boolean
     * 形参：List<实体类形参>
     */
    @Test
    void updates() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(9L, "霞飞"));
        users.add(new User(8L, "罗锐"));
        boolean b = userService.saveOrUpdateBatch(users);
    }
 
    /**
     * 根据主键删除值
     * default boolean removeById(Serializable id)
     * 返回值：boolean
     * 形参：主键值int类型
     */
    @Test
    void deleteId() {
        userService.removeById(1551916101577879553L);
    }
 
    /**
     * 根据条件删除记录[单条记录条件匹配]
     * default boolean removeByMap(Map<String, Object> columnMap)
     * 返回值：boolean
     * 形参：map
     * 字段—— ——>值
     * 字段—— ——>值
     * delete from user where id=7 and name="凌桦";
     */
    @Test
    void deleteMap() {
        Map userHashMap = new HashMap<>();
        userHashMap.put("id", 7L);
        userHashMap.put("name", "凌桦");
        boolean b = userService.removeByMap(userHashMap);
    }
 
    /**
     * 根据条件删除[删除单条数据]
     * default boolean remove(Wrapper<T> queryWrapper)
     * 返回值：boolean
     * 形参：QueryWrapper<实体类>
     * delete from user where age>20;
     */
    @Test
    void deleteRemove() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.gt("age", 20);
        boolean remove = userService.remove(userQueryWrapper);
    }
 
    /**
     * 批量删除[根据id批量删除]
     * default boolean removeByIds(Collection<? extends Serializable> idList)
     * 返回值：boolean
     * 形参：List
     */
    @Test
    void deleteRemoveId() {
        boolean b = userService.removeByIds(Arrays.asList(10, 11));
    }
 
    /**
     * 根据id修改实体类对象
     * default boolean updateById(T entity)
     * 返回值：boolean
     * 形参：实体类
     * <p>
     * update user set name="林海" where id=12;
     */
    @Test
    void updateId() {
        User user = new User();
        user.setId(12L);
        user.setName("林海");
        boolean b = userService.updateById(user);
    }
 
    /**
     * 根据条件修改
     *  default boolean update(T entity, Wrapper<T> updateWrapper)
     *  形参：
     *      1.实体类
     *      2.UpdateWrapper
     *  update user name="林玲" where name="罗锐";
     */
    @Test
    void updateCondition() {
        User user = new User();
        user.setName("林玲");
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("name","罗锐");
        boolean b = userService.update(user,updateWrapper);
    }
    /**
     *  批量修改
     *  default boolean updateBatchById(Collection<T> entityList)
     *  形参：ArrayList<实体类>
     *      update user name="张涛" where id=8;
     *      update user name="李杨" where id=9;
     *      update user name="王泽" where id=10;
     */
    @Test
    void updateList(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(8L,"张涛"));
        users.add(new User(9L,"李杨"));
        users.add(new User(10L,"王泽"));
        boolean b = userService.updateBatchById(users);
    }
    /**
     * 根据主键id查询[查询单条记录]
     * default T getById
     * 返回值：实体类列表
     */
    @Test
    void selectGetByID(){
        User byId = userService.getById(17L);
        log.info("查询结果{}",byId);
    }
    /**
     * 根据id值查询多条语句
     * default List<T> listByIds(Collection<? extends Serializable> idList)
     * 返回值List<io值,...>
     *
     *  select *from user where id=8;
     *  select *from user where id=12;
     *  select *from user where id=16;
     */
    @Test
    void selectListsId(){
        ArrayList<User> users1 = new ArrayList<>();
        List<User> users = userService.listByIds(Arrays.asList(8L,12L,16L));
        log.info("查询8，12，16结果{}",users);
    }
    /**
     * [查询单条记录]
     * default List<T> listByMap(Map<String, Object> columnMap)
     * 形参 HashMap<String,Object>()
     *      select *from user where name="吴峰" and age=15;
     */
    @Test
    void selectMapId(){
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("name","吴峰");
        map.put("age",15L);
        List<User> users = userService.listByMap(map);
        log.info("查询单条记录结果{}",users);
    }
    /**
     * 根据条件查询一天记录[根据条件查询]
     * default T getOne(Wrapper<T> queryWrapper)
     *
     * select *from user where name="张涛";
     */
    @Test
    void selectQueryWrapper(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name","张涛");
        User one = userService.getOne(queryWrapper);
        log.info("查询结果{}",one);
        //User(id=8, name=张涛, age=19, email=test8@baomidou.com)
    }
    /**
     *  根据实体封装类查询
     *  dMap<String, Object> getMap(Wrapper<T> queryWrapper);
     */
    @Test
    void getOne(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name","林灵");
        Map map = userService.getMap(queryWrapper);
        log.info("返回结果{}",map);
        //{name=林灵, id=16, age=15, email=test16@baomidou.com}
    }
    /**
     * 查询总记录数
     *  default int count()
     */
    @Test
    void selectSum(){
        int count = userService.count();
        log.info("查询结果{}",count);
    }
    /**
     * 根据条件查询记录总数
     * default int count(Wrapper<T> queryWrapper)
     */
    @Test
    void selectCountWra(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name","林");
        int count = userService.count(queryWrapper);
        log.info("模糊查询名字中带有林字的人数{}",count);//4
    }
    /**
     * 根据条件查询多条记录
     * default List<T> list(Wrapper<T> queryWrapper)
     *
     * 查询结果
     *   [User(id=12, name=林海, age=18, email=test12@baomidou.com),
     *   User(id=14, name=林燕, age=15, email=test14@baomidou.com),
     *   User(id=15, name=林玲, age=15, email=test15@baomidou.com),
     *   User(id=16, name=林灵, age=15, email=test16@baomidou.com)]
     */
    @Test
    void selectListWra(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name","林");
        List<User> list = userService.list(wrapper);
        log.info("模糊查询结果：{}",list);
 
    }
    /**
     * 查询所有
     * default List<T> list()
     * 
     * 运行结果
     * [User(id=8, name=张涛, age=19, email=test8@baomidou.com), 
     * User(id=12, name=林海, age=18, email=test12@baomidou.com), 
     * User(id=13, name=张峰, age=12, email=test13@baomidou.com), 
     * User(id=14, name=林燕, age=15, email=test14@baomidou.com), 
     * User(id=15, name=林玲, age=15, email=test15@baomidou.com), 
     * User(id=16, name=林灵, age=15, email=test16@baomidou.com), 
     * User(id=17, name=吴峰, age=17, email=test17@baomidou.com)]
     */
    @Test
    void selectLists(){
        List<User> list = userService.list();
        log.info("查询所有记录{}",list);
    }
 
 
}
```

#### 方式二:  条件构造器方式 -Mapper (查询, 更新)

![image-20231002145521470](SpringBoot3-数据访问.assets/image-20231002145521470.png)

![image-20231002145432486](SpringBoot3-数据访问.assets/image-20231002145432486.png)

##### **写法**

**Java的传统写法**

```java
public AjaxResult selectStudentByNumber(@RequestBody Student student) {
    // 1. 定义条件构造器
    // 要使用哪个表进行查询，那么<>内就填写哪个类
    QueryWrapper<Student> qw = new QueryWrapper();
    // 2. 构造条件构造器
    // 第一个变量是书库据表中的字段，第二个变量是sql语句查询中要查找的值
    qw.eq("stu_number", student.getStuNumber());
    
    // 3. 查询
    // 在result内，就进行完了查询
    List<Student> result = studentService.list(qw);
    // 4. 返回
    return AjaxResult.success(result);
}
```

**lambda表达式方式(推荐)**

```java
public AjaxResult selectStudentByNumber(@RequestBody Student student) {
    // 1.定义条件构造器
    LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<Student>().eq(Student::getStuNumber,stuNumber);
     // 2. 查询
    List<Student> result = studentService.list(lqw);  //在studentService具体实现中写this就行了 this.list(lqw);
    return AjaxResult.success(result);
}
```

###### xxxxMapper.

LambdaQueryWrapper：

```java
LambdaQueryWrapper<User> lambdaWrapper = new LambdaQueryWrapper<>();
lambdaWrapper.eq(User::getAge, 20).like(User::getName, "张");
```

LambdaUpdateWrapper：

```java
LambdaUpdateWrapper<User> lambdaWrapper = new LambdaUpdateWrapper<>();
lambdaWrapper.set(User::getAge, 30).eq(User::getName, "张三");
int rows = userMapper.update(null, lambdaWrapper);
```

```
    // UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
    //        .eq("id", userId);
    // userMapper.deductBalanceByIds(money, wrapper);
```

###### xxxxService. (一般不用)

**查询lambdaQuery**

```mysql
// com.star.learning.controller.UserController

@GetMapping("/list")
public List<User> list(UserQuery userQuery) {
    // 1.组织条件
    String username = userQuery.getUsername();
    Integer status = userQuery.getStatus();
    Integer minBalance = userQuery.getMinBalance();
    Integer maxBalance = userQuery.getMaxBalance();
    System.out.println("根据条件查询用户列表 => " + userQuery);
    // 2.查询用户
    List<User> users = userService.lambdaQuery()
            .like(username != null, User::getUsername, username)
            .eq(status != null, User::getStatus, status)
            .ge(minBalance != null, User::getBalance, minBalance)
            .le(maxBalance != null, User::getBalance, maxBalance)
            .list();
    System.out.println("查询结果 => " + users);
    return users;
}

```

**更新 lambdaUpdate**

```mysql
// com.star.learning.service.impl.UserServiceImpl

@Override
public void deductBalance(Long userId, Integer money) {
    // 1.查询用户
    User user = getById(userId);
    System.out.println(user);
    // 2.判断用户状态
    if (user == null || user.getStatus() == 2) {
        throw new RuntimeException("用户状态异常");
    }
    // 3.判断用户余额
    if (user.getBalance() < money) {
        throw new RuntimeException("用户余额不足");
    }
    // 4.扣减余额
    int remainBalance = user.getBalance() - money;
    lambdaUpdate()
            // 更新余额
            .set(User::getBalance, remainBalance)
            // 动态判断是否更新status
            .set(remainBalance == 0, User::getStatus, 2)
            // 条件
            .eq(User::getId, userId)
            // 乐观锁
            .eq(User::getBalance, user.getBalance())
            .update();


}

```

lambdaQuery与lambdaUpdate

1. 等于

```mysql
//EQ 就是 EQUAL等于
taskFlowService.lambdaQuery().eq(TaskFlow::getCreateTime,DateUtil.now())
```

2. 不等于

```mysql
//NE就是 NOT EQUAL不等于
taskFlowService.lambdaQuery().ne(TaskFlow::getCreateTime,DateUtil.now());
```

3. 大于

```mysql
//GT 就是 GREATER THAN大于
taskFlowService.lambdaQuery().gt(TaskFlow::getCreateTime,DateUtil.now());
```

4. 小于

```mysql
//LT 就是 LESS THAN小于
taskFlowService.lambdaQuery().lt(TaskFlow::getCreateTime,DateUtil.now());
```

5. 大于等于

```mysql
//GE 就是 GREATER THAN OR EQUAL 大于等于
taskFlowService.lambdaQuery().ge(TaskFlow::getCreateTime,DateUtil.now());
```

6. 小于等于

```mysql
//LE 就是 LESS THAN OR EQUAL 小于等于
taskFlowService.lambdaQuery().le(TaskFlow::getCreateTime,DateUtil.now());
```

7. 根据id查询对象

```mysql
Student one = studentService.lambdaQuery().eq(Student::getSno, 1).one();
```

8. 带条件的查询集合

```mysql
List<Student> studentList = studentService.lambdaQuery().eq(Student::getAddress, "上海").list();
```

9. 带条件的删除

```mysql
studentService.lambdaUpdate().eq(Student::getName,"张三").eq(Student::getAge,15).remove();
```

 10 .带条件的修改

```mysql
studentService.lambdaUpdate().set(Student::getAddress,"湖南").eq(Student::getSno,1).update();
```

11. 分页查询

```mysql
IPage<Student> studentIPage = studentService.page(new Page(current,size),new QueryWrapper<Student>().like(StrUtil.isNotBlank(name),"name",name));
```

 

用法

```java
/**
 * SQL:SELECT * FROM student WHERE (stu_name = ? AND id = ?)
 */
public AjaxResult selectStudent(@RequestBody Student student) {
        QueryWrapper qw = new QueryWrapper();
        Map map = new HashMap<>();
        map.put("id","2");
        map.put("stu_name","某某");
        qw.allEq(map);
        List<User> list = studentService.list(qw);
        return AjaxResult.success(list);
}
```

```java
 LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAge, 3);
        List<User> userList = userMapper.selectList(wrapper);

        userList.forEach(user -> {
            System.out.println(user);
            user.setName("testWlw1131-4");
            user.setUpdateTime(null);
            user.setCreateTime(null);
            //userMapper.updateById(user);
        });

        //mybatis-plus 自带的批量更新
        //userService.updateBatchById(userList,2);
        userService.updateBatchById(userList);
        
Execute SQL：
    UPDATE
        user 
    SET
        name='testWlw1131-4',
        age=3,
        email='1903202403@qq.com',
        version=8,
        update_time='2021-11-13 20:12:49.602' 
    WHERE
        id=1413764856826785803 
        AND version=7 
        AND deleted=0

```

[MyBatis-Plus——条件构造器_mybatis-plus条件构造器-CSDN博客](https://blog.csdn.net/Yangyeon_/article/details/129452576)

##### **总结**

```java
public class SelectTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void selectById() {
         // 按id查询
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }

    @Test
    public void selectBatchIds() {
        List<Long> ids = Arrays.asList(
            1087982257332887553L,
            1094590409767661570L,
            1094592041087729666L
        );
         // 按id集合查询
        List<User> list = userMapper.selectBatchIds(ids);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        //map的key指代的是mysql表中的列名，并非java实体的属性名
        map.put("name", "张雨琪");
        map.put("manager_id", 1088248166370832385L);
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

     
    @Test
    public void selectList_all() {
         // 查询所有
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

     /**
	 * 根据名称查询(常用)
	 *
	 * @param name
	 * @return
	 */
	private Category selectByBName(String name) {
		QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(Category::getName, name);
		return this.getOne(queryWrapper, false);
	}

    
    /**
     * 名字中包含雨，并且年龄小于40
     * SELECT * FROM `user`
     * WHERE `name` LIKE '%雨%' AND `age`< 40
     */
    @Test
    public void selectList_like_lt() {
//        QueryWrapper<User> query = Wrappers.<User>query();
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "雨").lt("age", 40);
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }
    
    //
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
	queryWrapper.lambda()
        .like(User::getName, "雨")  // 使用Lambda表达式指定属性名
        .between(User::getAge, 20, 40)  // 使用Lambda表达式指定属性名
        .isNotNull(User::getEmail);  // 使用Lambda表达式指定属性名

	List<User> list = userMapper.selectList(queryWrapper);


    /**
     * 名字中包含雨，并且年龄大于等于20且小于等于40，并且email不为空
     * SELECT * FROM `user`
     * WHERE `name` LIKE '%雨%' AND `age` <= 40 AND `age` >= 20 AND `email` IS NOT NULL
     */
    @Test
    public void selectList_between_isNotNull() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "雨").between("age", 20, 40).isNotNull("email");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 姓赵或者年龄大于等于25，按照年龄降序排列，年龄相同则按照id升序排列
     * SELECT * FROM `user`
     * WHERE `name` LIKE '赵%' OR `age` >= 25 ORDER BY `age` DESC , `id` ASC;
     */
    @Test
    public void selectList_or_orderByDesc_orderByAsc() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.likeRight("name", "赵").or().ge("age", 20)
            .orderByDesc("age").orderByAsc("id");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日，且直属上级姓王
     * SELECT * FROM `user`
     * WHERE DATE_FORMAT(create_time,'%Y-%m-%d')='2019-02-14'
     * AND manager_id IN (SELECT id FROM `user` WHERE `name` LIKE '王%')
     */
    @Test
    public void selectList_apply_inSql() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.apply("DATE_FORMAT(create_time,'%Y-%m-%d')={0}", "2019-02-14")
            .inSql("manager_id", "SELECT id FROM `user` WHERE `name` LIKE '王%'");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 姓王且（年龄小于40或邮箱不为空）
     * SELECT * FROM `user`
     * WHERE `name` LIKE '王%' AND (`age`< 40 OR `email` IS NOT NULL)
     */
    @Test
    public void selectList_and_lambda() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.likeRight("name", "王")
            .and(q -> q.lt("age", 40).or().isNotNull("email"));
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 姓王且或者（年龄小于40且年龄大于20且邮箱不为空）
     * SELECT * FROM `user`
     * WHERE `name` LIKE '王%' OR (`age`< 40 AND `age` > 20  AND `email` IS NOT NULL)
     */
    @Test
    public void selectList_or_lambda() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.likeRight("name", "王")
            .or(q -> q.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * （年龄小于40或邮箱不为空）且姓王
     * SELECT * FROM `user`
     * WHERE (`age`< 40 OR `email` IS NOT NULL) AND `name` LIKE '王%'
     */
    @Test
    public void selectList_nested() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.nested(q -> q.lt("age", 40).or().isNotNull("email"))
            .likeRight("name", "王");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 年龄为30,31,34,35
     * SELECT * FROM `user` WHERE `age` IN (30,31,34,35);
     */
    @Test
    public void selectList_in() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.in("age", Arrays.asList(30, 31, 34, 35));
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 返回只满足条件的一条（只调用最后一次，有sql注入的风险）
     * SELECT * FROM `user` WHERE `age` IN (30,31,34,35) LIMIT 1;
     */
    @Test
    public void selectList_last() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 只查询指定字段
     * SELECT `name`,`age` FROM `user` WHERE `age` IN (30,31,34,35) LIMIT 1;
     */
    @Test
    public void selectList_select_include() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.select("name", "age").in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 排除指定字段
     */
    @Test
    public void selectList_select_exclude() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1")
            .select(
                User.class,
                info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id")
            );
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 条件判断
     */
    @Test
    public void selectList_condition() {
        String name = "刘";
        String email = "";
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(name), "name", name)
            .like(StringUtils.isNotEmpty(email), "email", email);
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 实体类作为条件构造器
     * 默认是等值查询，可以在实体类属性中设置自定义条件
     * 获取用户名以 "刘" 开头、年龄小于 32 并且 manager_id 字段等于 "1088248166370832385" 的用户列表。
     */
    @Test
    public void selectList_entity() {
        User whereUser = new User();
        whereUser.setName("刘");//name like "刘"
        whereUser.setAge(32);//age<30
        QueryWrapper<User> query = new QueryWrapper<>(whereUser);
        query.eq("manager_id", "1088248166370832385");

        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * allEq  全部等王
     */
    @Test
    public void selectList_allEq() {
        QueryWrapper<User> query = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "刘明强");
        params.put("age", 31);
        params.put("email", null);
//        query.allEq(params,false);//第二个参数表示如果列值为null是否按IS NULL查询，false则忽略null列的查询
        query.allEq((k, v) -> !k.equals("name"), params, false);//第一个参数是过滤器
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * selectMaps的应用场景1：当表中的列特别多，但实际只需要几个列时，这时返回一个实体类有些不必要
     */
    @Test
    public void selectMaps() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "雨").lt("age", 40).select("name", "age");
        List<Map<String, Object>> maps = userMapper.selectMaps(query);
        maps.forEach(System.out::println);
    }

    /**
     * selectMaps的应用场景2：查询统计结果
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄，并且只取年龄总和小于100的组
     * SELECT AVG(age) avg_age,MIN(age) min_age,MAX(age) max_age
     * FROM `user`
     * GROUP BY `manager_id`
     * HAVING SUM(age)<100;
     */
    @Test
    public void selectMaps2() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.select("AVG(age) avg_age", "MIN(age) min_age", "MAX(age) max_age")
            .groupBy("manager_id")
            .having("SUM(age)<{0}", 100);
        List<Map<String, Object>> maps = userMapper.selectMaps(query);
        maps.forEach(System.out::println);
    }


    /**
     * selectObjs只返回第一列，其它列被遗弃
     * 应用场景：只需返回一列的时候
     */
    @Test
    public void selectObjs() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "雨").lt("age", 40).select("name", "age");
        List<Object> list = userMapper.selectObjs(query);
        list.forEach(System.out::println);
    }

    /**
     * 返回总记录数
     */
    @Test
    public void selectCount() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "雨").lt("age", 40);
        Integer count = userMapper.selectCount(query);
        System.out.println("总记录数：" + count);
    }

    /**
     * selectOne：只能查询一条记录，查询到多条会报错
     */
    @Test
    public void selectOne() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.like("name", "刘红雨").lt("age", 40);
        User user = userMapper.selectOne(query);
        System.out.println(user);
    }

    /**
     * lambda条件构造器
     */
    @Test
    public void lambdaQueryWrapper1() {
//        LambdaQueryWrapper<User> lambdaQ = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambdaQ = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQ = Wrappers.<User>lambdaQuery();

        lambdaQ.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> list = userMapper.selectList(lambdaQ);
        list.forEach(System.out::println);
    }

    /**
     * lambda条件构造器:防误写（例如列名"name"可能被误写）
     */
    @Test
    public void lambdaQueryWrapper2() {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.likeRight(User::getName, "王")
            .and(q -> q.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);
    }

    /**
     * 链式lambda条件构造器：更优雅的书写方式
     */
    @Test
    public void lambdaQueryChainWrapper() {
        List<User> list = new LambdaQueryChainWrapper<User>(userMapper)
            .likeRight(User::getName, "王")
            .and(
                q -> q
                    .lt(User::getAge, 40)
                    .or()
                    .isNotNull(User::getEmail)
            )
            .list();
        list.forEach(System.out::println);
    }

}
```

 比如我们想查age等于23并且school_id等于300的

> sql语句为：select * FROM student where age='23' or school_id='300'

 最直接的方法： 

> List<Student> students = studentMapper
>
> .selectList(Wrappers.lambdaQuery(Student.class)
>
> .eq(Student::getAge, "23")
>
> .or()
>
> .eq(Student::getSchoolId,300));

 也可以用下面的方法

>  用mybatis-plus则为：
>
>  ```
>  List<Student> students = studentMapper.selectList(Wrappers.lambdaQuery(Student.class)
>  .eq(Student::getAge, "23")
>  .or(s -> s.eq(Student::getSchoolId, 300)));
>  ```

**比如我们想查 age等于25或者姓张的同学**

> sql语句：select * FROM student where age='25' or `name` LIKE '张%'

用mybatis-plus最直接的方法

> ```
> List<Student> students = studentMapper
> .selectList(Wrappers.lambdaQuery(Student.class)
> .eq(Student::getAge, "25")
> .or()
> .likeRight(Student::getName, "张"));
> ```

 也可以用以下方法

> ```
> List<Student> students = studentMapper
> .selectList(Wrappers.lambdaQuery(Student.class)
> .eq(Student::getAge, "25")
> .or(s->s.likeRight(Student::getName,"张")));
> ```

#### 方式三:  使用注解方式

mapper中使用注解的方式:

```java
@Select("SELECT * FROM users WHERE id = #{id}")
User getUserById(Long id);
@Insert("INSERT INTO users (name, age) VALUES (#{name}, #{age})")
void addUser(User user);
@Update("UPDATE users SET name = #{name}, age = #{age} WHERE id = #fid}")
void updateUser(User user);
@Delete("DELETE FROM users WHERE id = #{id}")
void deleteUser(Long id);
```

#### 方式四:  使用xml方式

比较简单一般由插件直接生成

#### 总结

 LambdaQueryWrapper的使用

- `eq(column, value)`: 等于
- `ne(column, value)`: 不等于
- `gt(column, value)`: 大于
- `ge(column, value)`: 大于等于
- `lt(column, value)`: 小于
- `le(column, value)`: 小于等于
- `like(column, value)`: 模糊查询
- `notLike(column, value)`: 不模糊查询
- `in(column, valueList)`: 在指定集合中
- `notIn(column, valueList)`: 不在指定集合中
- `isNull(column)`: 为空
- `isNotNull(column)`: 不为空
- `orderByAsc(column)`: 升序排序
- `orderByDesc(column)`: 降序排序

```mysql
LambdaQueryWrapper<xxx> queryWrapper = new LambdaQueryWrapper<>();
queryWrapper.xxx....
List<xxx> list = userMapper.selectList(queryWrapper);   // 查询单表数据
Integer count = userMapper.selectCount(queryWrapper);   // 查询数量
IPage<xxx> userIPage = userMapper.selectPage(new Page<>(current, size) , queryWrapper); // 分页
```

### 多表查询

代码见[代码experiment02](https://github.com/Auroraol/BackFront/tree/master/2-后端/note01/7-Spring Boot3/sql/experiment02))

MP提供了大量单表查询的方法，但是没有多表的操作，所以涉及到多表的查询时，需要我们自己实现

**单表查询插件可以自动生成映射类型**

```xml
    <resultMap id="BaseResultMap" type="com.example.experiment01.entity.TbProduct">
            <id property="id" column="id" jdbcType="INTEGER"/>
            <result property="name" column="NAME" jdbcType="VARCHAR"/>
            <result property="price" column="price" jdbcType="DOUBLE"/>
    </resultMap>
```

#### 理解一对一, 多对一和一对多

```java
//人类
public class Person implements Serializable{
private Integer id;
private String name;
private String sex;
private Integer age;

private Card card;   //人和身份证是一对一的关系
}
// 对这个数据实体操作时---->使用association
```

```java
package com.glj.pojo;
 
import java.io.Serializable;
 
//学生类
public class Student implements Serializable {
private Integer id;
private String name;
private String sex;
private Integer age;
       
private Clazz clazz;   //学生与班级是多对一的关系
}
// 对这个数据实体操作时---->使用association
```

```java
package com.glj.pojo;
 
import java.io.Serializable;
import java.util.List;
 
// 班级类
public class Clazz implements Serializable{
private Integer id;
private String code;
private String name;
      
private List<Student> students;  //班级与学生是一对多的关系
}
// 对这个数据实体操作时---->使用collection
```

#### 使用Xml方式

注意: 需要配合修改对应的实体类

**多表查询:**

[association]()关联 用于一对一或多对一查询

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.experiment01.mapper.TbPersonMapper">

    <resultMap id="BaseResultMap" type="com.example.experiment01.entity.TbPerson">
            <id property="id" column="id" jdbcType="INTEGER"/>
            <result property="name" column="name" jdbcType="VARCHAR"/>
            <result property="age" column="age" jdbcType="INTEGER"/>
            <result property="sex" column="sex" jdbcType="VARCHAR"/>
            <result property="cardId" column="card_id" jdbcType="INTEGER"/>
    </resultMap>

    <sql id="Base_Column_List">
        id,name,age,
        sex,card_id
    </sql>

    <!-- 嵌套查询：通过执行另外一条SQL映射语句来返回预期的特殊类型 -->
    <select id="findPersonById" parameterType="Integer" resultMap="IdCardWithPersonResult">
        SELECT * from tb_person where id=#{id}
    </select>
    <resultMap type="com.example.experiment01.entity.TbPerson" id="IdCardWithPersonResult">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="age" column="age"/>
        <result property="sex" column="sex"/>
        <!-- 一对一：association使用select属性引入另外一条SQL语句 -->
        <association property="cardId" column="card_id" javaType="com.example.experiment01.entity.TbIdcard"
                     select="com.example.experiment01.mapper.IdCardMapper.findAllById"/>
    </resultMap>

    <!-- 嵌套结果：使用嵌套结果映射来处理重复的联合结果的子集 -->
    <select id="findPersonById2" parameterType="Integer"
            resultMap="IdCardWithPersonResult2">
        SELECT p.*,idcard.code
        from tb_person p,tb_idcard idcard
        where p.card_id=idcard.id
          and p.id= #{id}
    </select>
    <resultMap type="com.example.experiment01.entity.TbPerson" id="IdCardWithPersonResult2">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="age" column="age"/>
        <result property="sex" column="sex"/>
        <association property="cardId" javaType="com.example.experiment01.entity.TbIdcard">
            <!--表示cardId里有的东西 -->
            <id property="id" column="card_id"/>
            <result property="code" column="code"/>
        </association>
    </resultMap>


</mapper>

```

[collection](https://so.csdn.net/so/search?q=collection&spm=1001.2101.3001.7020) 意为集合 用于一对多查询

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xxx.xxx.mapper.xxxxMapper">
    <!-- 
		id
		number
		productList
    -->
    <!-- 多对多嵌套查询：通过执行另外一条SQL映射语句来返回预期的特殊类型 -->
    <select id="函数名" parameterType="参数类型" resultMap="结果集名1">
        <!-- sql语句 -->
        select * from tb_orders WHERE id=#{id}
    </select>
    <resultMap type="com.xxx.xxx.entity.实体类" id="结果集名1">
        <!--property是实体类属性, colum是sql语句查出的字段
			colum能当做参数给property
		-->
        <id property="id" column="id"/>
        <result property="number" column="number"/>
         <!-- collection 多表关联映射  ofType表示属性集合中元素的类型--> 
        <collection property="productList" column="id" ofType="com.xxx.xxx.entity.关联的实体类"
                   <!--select调用其他mapper的函数(这里出现报错)--> 
                    select="com.itheima.mapper.ProductMapper.findProductById">
        </collection>
    </resultMap>

    <!-- 多对多嵌套结果查询：查询某订单及其关联的商品详情 -->
    <select id="函数名" parameterType="参数类型" resultMap="结果集名2">
        select o.*,p.id as pid,p.name,p.price
        from tb_orders o,tb_product p,tb_ordersitem  oi
        WHERE oi.orders_id=o.id
          and oi.product_id=p.id
          and o.id=#{id}
    </select>
    
    <!-- 自定义手动映射类型 -->
    <resultMap type="com.example.experiment01.entity.TbOrders" id="结果集名2">
        <id property="id" column="id"/>
        <result property="number" column="number"/>
        <!-- collection 多表关联映射 --> 
        <collection property="productList" ofType="com.xxx.xxx.entity.关联的实体类">
            <!--property是实体类属性, colum是sql语句查出的字段-->
            <id property="id" column="pid"/>
            <result property="name" column="name"/>
            <result property="price" column="price"/>
        </collection>
    </resultMap>
</mapper>
```

ps:  当结果就是实体类并且没有联表使用resultMap="实体类"可以不用写resultMap了

```xml
    <select id="findProductById" parameterType="Integer" 		              resultType="com.example.experiment01.entity.TbProduct"> 
        SELECT * from tb_product where id IN(
            SELECT product_id FROM tb_ordersitem  WHERE orders_id = #{id}
        )
    </select>
```

## 分页查询

### **分页插件的配置（必须）**

```java
package com.example.demo.config;

@Configuration
@MapperScan("scan.your.mapper.package")
public class MybatisPlusConfig {

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        return interceptor;
    }
}
```

**使用MyBatis Plus提供的方法**

| 方法                                                    | 功能                                                         |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Page<实体类> Page(当前页码,每页显示的总记录数)          | 创建一个Page对象,存储查询到的`分页数据`和`分页相关的信息`    |
| Page<实体类> selectPage(Page page,Wrapper queryWrapper) | BaseMapper提供的分页查询的方法,条件构造器为null时表示查所有  |
| Page<实体类> page(Page page,Wrapper queryWrapper)       | IService接口提供的分页查询的方法,条件构造器为null时表示查所有 设置分页的相关参数(当前页码,每页显示的总记录数) |

**获取Page对象存储的`分页数据`和`分页相关的信息`的方法,用来给前端`设置页码中分页相关的超链接和按钮`**

| 方法名                   | 功能                                   |
| ------------------------ | -------------------------------------- |
| List <实体类> getRecords | 获取查询到的分页数据                   |
| long getCurrent          | 获取当前页的页码                       |
| long getSize             | 获取每页显示的总记录数                 |
| long getTotal            | 获取总记录条数(注意状态是未删除的记录) |
| long getPages            | 获取总页数                             |
| boolean hasPrevious      | 判断是否有上一页                       |
| boolean hasNext          | 判断是否有下一页                       |

### 单表查询

1、启用分页插件

```java
@Configuration
public class CustomMyBatisPlusConfig {
 
    /**
     * 分页插件,一缓和二缓遵循mybatis的规则
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
```

```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper; // 假设你有一个 UserMapper 接口

    public Page<User> getUserListWithPagination(int current, int size) {
        // 创建分页对象
        Page<User> page = new Page<>(current, size);

        // 第一个参数是分页对象，第二个参数是查询条件（可以是 QueryWrapper 等）
        // selectPage 方法会在查询时自动添加分页条件，并返回分页查询结果
        return userMapper.selectPage(page, null);
    }
}
```

2、测试分页查询

```java
@Test
public void testSelectPage(){
    // 1. 创建分页对象, 即当前页码数和每页显示的记录数
    Page<User> page=new Page<>(2,5);
    // 2. 构造查询条件
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.gt("age", 18);
    // 3. 执行分页查询 //条件构造器queryWrapper为null表示查询所有数据
    userMapper.selectPage(page, queryWrapper); //selectPage()会在查询时自动添加分页条件，并返回分页查询结果
    // 4. 获取分页后查询出的记录
    List<User> records = page.getRecords();
    records.forEach(System.out::println);
    // 5.获取Page对象存储的分页数据和分页相关的信息的方法
    System.out.println("当前页："+page.getCurrent());
    System.out.println("每页显示的条数："+page.getSize());
    System.out.println("总记录数(只包含删除状态是未删除的数据)："+page.getTotal());
    System.out.println("总页数："+page.getPages());
    System.out.println("是否有上一页："+page.hasPrevious());
    System.out.println("是否有下一页："+page.hasNext());
}

// 或者
Page<DigitalLibraryDO> digitalLibraryDOPage = digitalLibraryMapper.selectPage(new Page<>(curPage, pageSize), new QueryWrapper<DigitalLibraryDO>());

// 或者 (推荐)
LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
queryWrapper.like(User::getUsername , "k");
IPage<User> userIPage = userMapper.selectPage(new Page<>(1 , 2) , queryWrapper);

System.out.println("总页数： "+userIPage.getPages());
System.out.println("总记录数： "+userIPage.getTotal());
userIPage.getRecords().forEach(System.out::println);
```

```
User(id=3, name=Tom, age=28, email=test3@baomidou.com, isDeleted=null) 
User(id=4,name=Sandy, age=21, email=test4@baomidou.com, isDeleted=null) 
User(id=5, name=Billie,age=24, email=test5@baomidou.com, isDeleted=null) 
User(id=8, name=ybc1, age=21,email=null, isDeleted=null)
User(id=9, name=ybc2, age=22, email=null, isDeleted=null)
当前页：2 每页显示的条数：5 总记录数(只包含删除状态是未删除的数据)：12 总页数：3 是否有上一页：true 是否有下一页：true	
```

**例子**

[Mybatis-Plus](https://so.csdn.net/so/search?q=Mybatis-Plus&spm=1001.2101.3001.7020) 中的分页查询接口主要有两个：IPage 和 Page。

1. IPage 接口：
   IPage 是 Mybatis-Plus 中的分页结果集接口，它继承了 [Mybatis](https://so.csdn.net/so/search?q=Mybatis&spm=1001.2101.3001.7020) 的 RowBounds 接口，提供了一系列的分页查询方法。该接口主要用于返回分页后的数据结果。
2. Page 类：
   Page 类是 IPage 接口的默认实现类，实现了 IPage 接口中的方法。在进行[分页查询](https://so.csdn.net/so/search?q=分页查询&spm=1001.2101.3001.7020)时，通常会创建一个 Page 对象，并设置相关的分页参数。

以下是 IPage 和 Page 类的常用参数：

- current：当前页数，必须大于等于 1，默认值为 1。
- size：每页条数，默认值为 10。
- total：总条数，默认值为 0。
- records：当前页数据集合，默认值为空集合。
- searchCount：是否进行 count 查询，默认值为 true，表示会统计总条数。
- pages：总页数，通过计算得出。
- optimizeCountSql：是否优化 count 查询，默认值为 true。
- hitCount：是否对 count 进行 limit 优化，默认值为 false。
- countId：count 查询的列名，默认为 null，表示所有列。
- maxLimit：设置最大的 limit 查询限制，默认值为 -1，表示不限制。

举个栗子：

```java
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public IPage<User> getUserList(int currentPage, int pageSize) {
        // 创建分页对象, 即当前页码数和每页显示的记录数
        Page<User> page = new Page<>(currentPage, pageSize);

        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 18);

        // 执行分页查询
        IPage<User> userPage = userMapper.selectPage(page, queryWrapper);

        return userPage;
    }
}
```

在这个例子中，我们通过构造一个 Page 对象来设置当前页和每页条数。然后通过 QueryWrapper 构造查询条件，在调用 `userMapper.selectPage(page, queryWrapper)` 方法进行分页查询。

### 联表查询

#### 例子1

**第一步: 在`UserMapper`中自定义接口方法根据年龄查询用户列表并按照分页显示**

从MyBatis Plus自带的selectPage方法可以看出要想使用分页插件的功能,方法的第一个参数需要接收一个Page<实体类>对象并且返回值也是一个Page对象

```java
@Repository
public interface UserMapper extends BaseMapper<User> {
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
}
```

**第三步: 在`UserMapper.xml`中编写自定义SQL,因为最后的结果集是要封装到User对象的属性中,所以返回的参数是User对象**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.atguigu.mybatisplus.mapper.UserMapper">
    <!--IPage<User> selectPageVo(Page<User> page, Integer age);-->
    <select id="selectPageVo" resultType="User">
        SELECT id,user_name,age,email FROM t_user WHERE age > #{age}
    </select>
</mapper>
```

**第四步: 在测试类中测试自定义的接口方法**

```java
@Test
public void testSelectPageVo(){
    //设置分页参数
    Page<User> page = new Page<>(1, 5);
    userMapper.selectPageVo(page, 20);
    //获取分页数据
    List<User> list = page.getRecords();
    list.forEach(System.out::println);
    System.out.println("当前页："+page.getCurrent());
    System.out.println("每页显示的条数："+page.getSize());
    System.out.println("总记录数："+page.getTotal());
    System.out.println("总页数："+page.getPages());
    System.out.println("是否有上一页："+page.hasPrevious());
    System.out.println("是否有下一页："+page.hasNext());
}
```

#### 例子2

##### 分页查询实体

```java
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页查询实体
 */
@Data
public class PageQuery {
 
    //页码
    private Integer pageNo = 1;
 
    //分页大小
    private Integer pageSize = 10;
 
    //排序字段
    private String sortBy;
 
    //是否升序
    private Boolean isIncrease = true;
 
    /**
     * PageQuery转Page
     *
     * @param orderItem 排序条件
     * @return Page对象
     */
    public <T> Page<T> toMyBatisPlusPage(OrderItem... orderItem) {
        //TODO:构建分页条件
        //分页条件
        Page<T> page = new Page<>(pageNo, pageSize);
        //排序条件
        if (StringUtils.hasText(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isIncrease));
        } else if (orderItem != null) {
            //默认按照更新时间排序-[此处改为默认按照id排序]
            page.addOrder(orderItem);
        }
        //返回
        return page;
    }
 
    /**
     * PageQuery转Page:根据排序字段排序
     *
     * @param orderBy    排序字段
     * @param isIncrease 是否升序
     * @return Page对象
     */
    public <T> Page<T> toMyBatisPlusPage(String orderBy, Boolean isIncrease) {
        return toMyBatisPlusPage(new OrderItem(orderBy, isIncrease));
    }
 
    /**
     * PageQuery转Page:根据Id排序
     *
     * @return Page对象
     */
    public <T> Page<T> toMyBatisPlusPageSortById() {
        return toMyBatisPlusPage(new OrderItem("id", true));
    }
 
    /**
     * PageQuery转Page:根据create_time排序
     *
     * @return Page对象
     */
    public <T> Page<T> toMyBatisPlusPageSortByCreateTime() {
        return toMyBatisPlusPage(new OrderItem("create_time", true));
    }
 
    /**
     * PageQuery转Page:根据update_time排序
     *
     * @return Page对象
     */
    public <T> Page<T> toMyBatisPlusPageSortByUpdateTime() {
        return toMyBatisPlusPage(new OrderItem("update_time", true));
    }
}
```

##### 分页响应实体

```java

package com.example.demo.demos.model.dto;
 
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
 
/**
 * 分页查询结果
 */
@Data
public class PageDTO<T> {
 
    //总条数
    private Long total;
 
    //总页数
    private Long pages;
 
    //集合
    private List<T> list;
 
    /**
     * Page转换为PageDTO
     *
     * @param page  MyBatis Plus分页对象
     * @param clazz 类型参数
     * @return PageDTO
     */
    public static <PO, VO> PageDTO<VO> of(Page<PO> page, Class<VO> clazz) {
        //TODO:构建Vo结果
        PageDTO<VO> pageDTO = new PageDTO<>();
        //总条数
        pageDTO.setTotal(page.getTotal());
        //总页数
        pageDTO.setPages(page.getPages());
        //当前页数据
        List<PO> records = page.getRecords();
        //转换为vo
        if (CollectionUtils.isEmpty(records)) {
            pageDTO.setList(Collections.emptyList());
            return pageDTO;
        }
        //for
        List<VO> voList = new ArrayList<>(records.size());
        records.forEach(po -> {
            try {
                VO vo = clazz.newInstance();
                BeanUtils.copyProperties(po, vo, clazz);
                voList.add(vo);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        pageDTO.setList(voList);
        //返回结果
        return pageDTO;
    }
 
 
    /**
     * Page转换为PageDTO-【自定义PO->VO的转换方法】
     *
     * @param page  MyBatis Plus分页对象
     * @param convertor PO->VO的转换逻辑
     * @return PageDTO
     */
    public static <PO, VO> PageDTO<VO> of(Page<PO> page, Function<PO,VO> convertor) {
        //TODO:构建Vo结果
        PageDTO<VO> pageDTO = new PageDTO<>();
        //总条数
        pageDTO.setTotal(page.getTotal());
        //总页数
        pageDTO.setPages(page.getPages());
        //当前页数据
        List<PO> records = page.getRecords();
        //转换为vo
        if (CollectionUtils.isEmpty(records)) {
            pageDTO.setList(Collections.emptyList());
            return pageDTO;
        }
        List<VO> voList = records.stream().map(convertor).collect(Collectors.toList());
        pageDTO.setList(voList);
        //返回结果
        return pageDTO;
    }
}
```



```java
@Override
    public PageDTO<ProductVo> queryProductsPage(ProductQuery productQuery) {
        //获取参数
        String name = productQuery.getName();
        Boolean status = productQuery.getStatus();
        Double minPrice = productQuery.getMinPrice();
        Double maxPrice = productQuery.getMaxPrice();
        //TODO:构建分页条件
        //调用封装好的方法
        Page<Product> page = productQuery.toMyBatisPlusPageSortById();
        //TODO:分页查询
        Page<Product> pageResult = lambdaQuery()
                .like(name != null && name != "", Product::getName, name)
                .eq(status != null, Product::getStatus, status)
                .ge(minPrice != null && minPrice != 0, Product::getPrice, minPrice)
                .le(maxPrice != null && maxPrice != 0, Product::getPrice, maxPrice)
                .page(page);
        //TODO:构建Vo结果
        //方式1:默认转换PO->VO
//        return PageDTO.of(pageResult, ProductVo.class);
        //方式2:自定义转换PO->VO
        return PageDTO.of(pageResult,(product -> {
            //自定义转换逻辑
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            return productVo;
        }));
    }
```

##### 接口测试

![image-20240407215429933](SpringBoot-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240407215429933.png)

 查询结果：

```javascript

{
    "code": 200,
    "data": {
        "total": 3,
        "pages": 2,
        "list": [
            {
                "id": 2,
                "name": "冰可乐",
                "price": 12.12,
                "address": "淮阳",
                "status": false,
                "createTime": "2024-02-18 00:35:07",
                "updateTime": null,
                "enumState": 2,
                "info": "{\"bar\": \"baz\", \"balance\": 7.77, \"active\": false}"
            },
            {
                "id": 4,
                "name": "雪糕",
                "price": 38.47,
                "address": "阜阳",
                "status": true,
                "createTime": "2024-02-18 01:23:12",
                "updateTime": null,
                "enumState": 1,
                "info": "{\"bar\": \"baz\", \"balance\": 7.77, \"active\": false}"
            }
        ]
    },
    "message": "ok"
}
```

###  例子3(推荐)

#### queryWrapper

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageQueryWrapper {

	private Integer status;

	private Long offset;

	private Long limit;

	private Integer categoryId;

	private Integer tagId;

	private String title;

	private String orderBy;

	private String start;

	private String end;
}
```

```java
package com.lfj.blog.service.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lfj.blog.entity.Article;
import com.lfj.blog.entity.Category;
import com.lfj.blog.entity.Tag;
import com.lfj.blog.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ArticleVo对象
 * 文章返回前端对象
 * 文章详细对象
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "ArticleVo对象", description = "文章详细对象")
public class ArticleVo extends Article {

	@Schema(description = "作者信息")
	private User user;

	@Schema(description = "标签列表")
	private List<Tag> tagList;

	@Schema(description = "分类列表,顺序:root node2 node3")
	private List<Category> categoryList;

	@Schema(description = "上一篇")
	private Article previous;

	@Schema(description = "下一篇")
	private Article next;

	@Schema(description = "推荐分数")
	private Double recommendScore;

}
```

#### 编写mapper

```java
	/**
	 * 分页查询
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<ArticleVo> selectArticleVoPage(ArticlePageQueryWrapper queryWrapper);
```

ArticleMapper.xml

```xml
    <sql id="Article_Column_List">
        inner_a.id,inner_a.original, inner_a.user_id, inner_a.category_name, inner_a.category_id, inner_a.title, inner_a.summary, inner_a.content, inner_a.html_content, inner_a.cover, inner_a.status, inner_a.view_count,
        inner_a.comment_count, inner_a.like_count, inner_a.collect_count, inner_a.publish_time, inner_a.update_time, inner_a.reproduce, inner_a.deleted
    </sql>


<!--多表查询, ArticleVo封装Article实体数据部分数据+User部分数据+tag部分数据-->
    <resultMap id="articleVoResultMap" type="com.lfj.blog.service.vo.ArticleVo">
        <id property="id" column="id"/>
        <result property="original" column="original"/>
        <result property="categoryName" column="category_name"/>
        <result property="userId" column="user_id"/>
        <result property="categoryId" column="category_id"/>
        <result property="title" column="title"/>
        <result property="summary" column="summary"/>
        <result property="content" column="content"/>
        <result property="htmlContent" column="html_content"/>
        <result property="cover" column="cover"/>
        <result property="status" column="status"/>
        <result property="viewCount" column="view_count"/>
        <result property="commentCount" column="comment_count"/>
        <result property="likeCount" column="like_count"/>
        <result property="collectCount" column="collect_count"/>
        <result property="publishTime" column="publish_time"/>
        <result property="updateTime" column="update_time"/>
        <result property="reproduce" column="reproduce"/>
        <result property="deleted" column="deleted"/>
        <!--Article和User 一对一的关系-->
        <association property="user" javaType="com.lfj.blog.entity.User">
            <id property="id" column="u_user_id"/>
            <result property="nickname" column="nickname"/>
            <result property="avatar" column="avatar"/>
        </association>
        <!--Article和Tag 多对一的关系-->
        <collection property="tagList" ofType="com.lfj.blog.entity.Tag">
            <!--将 column 属性设置为 "tag_id"。这样，在查询结果中，MyBatis 就会根据 tag_id 列的值来填充标签集合 tagList 中的每个标签对象的 id 属性。-->
            <id property="id" column="tag_id"/>
            <result property="name" column="tag_name"/>
        </collection>
    </resultMap> 

    <!--分页查询文章-->
    <select id="selectArticleVoPage" resultMap="articleVoResultMap">
        select
        outer_a.id,
        outer_a.original,
        outer_a.user_id,
        outer_a.category_name,
        outer_a.category_id,
        outer_a.title,
        outer_a.summary,
        outer_a.cover,
        outer_a.status,
        outer_a.view_count,
        outer_a.comment_count,
        outer_a.like_count,
        outer_a.collect_count,
        outer_a.publish_time,
        outer_a.update_time,
        outer_a.reproduce,
        outer_a.deleted,
        u.id u_user_id,
        u.avatar,
        u.nickname,
        t.id tag_id,
        t.name tag_name
        from
        -- 表子查询
        (
        select
        <include refid="Article_Column_List"/>
        ,(
        inner_a.view_count * 0.15
        + inner_a.comment_count * 0.35
        + inner_a.like_count * 0.25
        + inner_a.collect_count * 0.25
        ) as score
        from article inner_a

        where
        inner_a.deleted = 0 -- 未删除的
        <if test="status != null">
            and inner_a.status = #{status}
        </if>
        <if test="title != null and title != ''">
            and inner_a.title like concat('%',concat(#{title}, '%')) --生成字符串 "%apple%"，用于进行模糊匹配操作
        </if>
        <if test="categoryId != null">
            and inner_a.category_id = #{categoryId}
        </if>
        <if test="start != null and end != null">
            and inner_a.publish_time >= #{start} and inner_a.publish_time <![CDATA[<]]> #{end} --使用了 CDATA 标记来包裹比较符号小于
            --以防止标签。
        </if>
        <if test="tagId != null">
            and inner_a.id in (select inner_a_t.article_id from article_tag inner_a_t where inner_a_t.tag_id = #{tagId})
        </if>
        -- 排序
        <choose>
            <when test="orderBy == 'publish_time'">
                order by inner_a.publish_time desc -- 从大到小
            </when>
            <otherwise>
                order by inner_a.view_count desc
            </otherwise>
        </choose>
        limit #{offset},#{limit} -- 分页
        ) outer_a
        left join user u on u.id = outer_a.user_id
        left join article_tag a_t on a_t.article_id = outer_a.id
        left join tag t on t.id = a_t.tag_id
        -- 排序
        <choose>
            <when test="orderBy == 'publish_time'">
                order by outer_a.publish_time desc
            </when>
            <otherwise>
                order by score desc
            </otherwise>
        </choose>
    </select>
```

+ `<choose>` 是 MyBatis 中的一个元素，用于在多个条件中选择一个进行处理。它包含了一个或多个 `<when>` 元素和一个可选的 `<otherwise>` 元素。

当使用 `<choose>` 元素时，会逐个检查 `<when>` 元素的条件，如果某个条件为真，则执行该条件下的语句块。如果所有条件都不为真，则执行 `<otherwise>` 元素下的语句块（可选）。

分析

```
{
  "code": 200000,
  "message": "成功",
  "data": {
    "id": 2,
    "original": 1,
    "categoryName": "一级分类二",
    "categoryId": 2,
    "title": "string",
    "summary": "string",
    "htmlContent": "string",
    "cover": "string",
    "status": 0,
    "viewCount": 0,
    "commentCount": 0,
    "likeCount": 0,
    "collectCount": 0,
    "publishTime": "2020-01-02 20:24:16",
    "updateTime": "2020-01-02 20:24:16",
    "user": {
      "id": 1,
      "nickname": "小管家",
      "avatar": "https://poile-img.nos-eastchina1.126.net/me.png"
    },
    "tagList": [
      {
        "id": 1,
        "name": "测试"
      }
    ],
    "previous": {
      "id": 1,
      "title": "标题"
    },
    "next": {
      "id": 3,
      "title": "string2"
    }
  }
}
```



```
"<if></if>" 是一个条件判断语句
```



```xml
    <sql id="Article_Column_List">
        inner_a.id,inner_a.original, inner_a.user_id, inner_a.category_name, inner_a.category_id, inner_a.title, inner_a.summary, inner_a.content, inner_a.html_content, inner_a.cover, inner_a.status, inner_a.view_count,
        inner_a.comment_count, inner_a.like_count, inner_a.collect_count, inner_a.publish_time, inner_a.update_time, inner_a.reproduce, inner_a.deleted
    </sql>
```

这段代码是一个定义了一个 SQL 片段的 `<sql>` 标签，其 ID 为 "Article_Column_List"。该 SQL 片段包含了以下列名：

- `inner_a.id`: 文章的 ID
- `inner_a.original`: 是否原创
- `inner_a.user_id`: 用户 ID
- `inner_a.category_name`: 分类名称
- `inner_a.category_id`: 分类 ID
- `inner_a.title`: 文章标题
- `inner_a.summary`: 文章摘要
- `inner_a.content`: 文章内容
- `inner_a.html_content`: 文章 HTML 内容
- `inner_a.cover`: 文章封面
- `inner_a.status`: 文章状态
- `inner_a.view_count`: 浏览次数
- `inner_a.comment_count`: 评论次数
- `inner_a.like_count`: 点赞次数
- `inner_a.collect_count`: 收藏次数
- `inner_a.publish_time`: 发布时间
- `inner_a.update_time`: 更新时间
- `inner_a.reproduce`: 是否转载
- `inner_a.deleted`: 是否已删除

通过定义这个 SQL 片段，可以在其他 SQL 查询中使用 `<include>` 标签引用它，从而避免重复编写列名的代码。







#### 返回前端对象

```java

/**
 * 文章返回前端对象
 * 文章详细对象
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ArticleVo对象", description = "文章详细对象")
public class ArticleVo extends Article {

	@ApiModelProperty("作者信息")
	private User user;

	@ApiModelProperty("标签列表")
	private List<Tag> tagList;

	@ApiModelProperty("分类列表,顺序:root node2 node3")
	private List<Category> categoryList;

	@ApiModelProperty("上一篇")
	private Article previous;

	@ApiModelProperty("下一篇")
	private Article next;

	@ApiModelProperty("推荐分数")
	private Double recommendScore;

}

```



#### ArticleServicelmpl

```java
@Override
	public IPage<ArticleVo> selectPublishedArticleVoPage(long current, long size,
														 Integer categoryId, Integer tagId,
														 String yearMonth, String title, String orderBy) {
		// 查询start-end时期的文章
		String[] startAndEndOfMonth = getStartAndEndOfMonth(yearMonth);
		String start = startAndEndOfMonth[0];
		String end = startAndEndOfMonth[1];
		int count = selectPageCount(ArticleStatusEnum.NORMAL.getStatus(), categoryId, tagId, start, end, title);
		if (count == 0) {
			return new Page<>(current, size);
		}
		//自定义Wrapper
		ArticlePageQueryWrapper queryWrapper = new ArticlePageQueryWrapper();
		queryWrapper.setOffset((current - 1) * size);
		queryWrapper.setLimit(size);
		queryWrapper.setCategoryId(categoryId);
		queryWrapper.setTagId(tagId);
		queryWrapper.setTitle(title);
		queryWrapper.setOrderBy(orderBy);
		queryWrapper.setStart(start);
		queryWrapper.setEnd(end);
		queryWrapper.setStatus((ArticleStatusEnum.NORMAL.getStatus()));  // 文章状态正常的
        //
		List<ArticleVo> articleVoList = articleMapper.selectArticleVoPage(queryWrapper);
		// 1. 创建分页对象, 即当前页码数和每页显示的记录数
		Page<ArticleVo> page = new Page<>(current, size, count);
        // 2. 获取分页后查询出的记录
		page.setRecords(articleVoList);
		return page;
	}
```

### 例子4 (推荐)

```java
@Data
public class EquipmentDoorPageVO {
    @ApiModelProperty("设备自增id")
    private Integer id;

    @ApiModelProperty("设备名称")
    private String equipmentName;

    @ApiModelProperty("设备序列号")
    private String equipmentSerialNumber;

    @ApiModelProperty("设备ip地址")
    private String equipmentIp;

    @ApiModelProperty("设备型号id")
    private Integer productId;

    @ApiModelProperty("设备型号")
    private String productName;

    @ApiModelProperty(value = "门数量")
    private Integer doorCount;

    @ApiModelProperty(value = "读头数量")
    private Integer readHeadCount;
}
```

```mysql
Page<EquipmentDoorPageVO> page = equipmentMapper.getEquipmentDoorPage(new Page<>(query.getCurPage(), query.getPageSize()), query, businessTye, regionIds);
```

```java
Page<EquipmentDoorPageVO> getEquipmentDoorPage(Page<EquipmentDoorPageVO> page, @Param("query") EquipmentDoorPageQuery query, @Param("businessType") Integer businessType, @Param("regionIds") List<Integer> regionIds);
```

```mysql
    <select id="getEquipmentDoorPage" resultType="com.fungxi.server.equipment.model.vo.EquipmentDoorPageVO">
        select
        e.id,
        e.equipment_name,
        e.equipment_serial_number,
        e.equipment_ip,
        e.product_id,
        apt.product_chinese_name productName
        from
        (
        (
        SELECT equipment.*, 0 isRelate FROM equipment inner join apply_product apt on apt.id=equipment.product_id and
        apt.business_type = #{businessType}
        UNION
        (SELECT e.*,eba.is_association isRelate FROM equipment e INNER JOIN equipment_business_association eba ON e.id =
        eba.equipment_id and eba.business_type = #{businessType} and eba.is_association = 1))
        ) e
        inner join apply_product apt on apt.id=e.product_id
        inner join region re on re.id = e.region_id
        <where>
            <if test="query.equipmentIp!=null and query.equipmentIp!=''">
                and e.equipment_ip like CONCAT('%',#{query.equipmentIp},'%')
            </if>
            <if test="query.equipmentSerialNumber !=null and query.equipmentSerialNumber!=''">
                and e.equipment_serial_number like CONCAT('%',#{query.equipmentSerialNumber},'%')
            </if>
            <if test="query.productName != null and query.productName != ''">
                AND apt.product_chinese_name = #{query.productName}
            </if>
            <if test="query.equipmentName!=null and query.equipmentName!=''">
                AND e.equipment_name like CONCAT('%',#{query.equipmentName},'%')
            </if>
            <if test="regionIds.size > 0">
                AND e.region_id in
                <foreach collection="regionIds" item="regionId" separator="," open="(" close=")">
                    #{regionId}
                </foreach>
            </if>
        </where>
    </select>
```

## MySQL数据类型为 json

参考:

+ [Mybatis和Mybatis-Plus对MySQL中json类型处理 - 简书 (jianshu.io)](https://wsa.jianshu.io/p/7a4653704acb)

+ [MyBatisPlus实现数据库JSON数据自动转换_mybatis-plus实体类复杂对象字段json自动相互转换-CSDN博客](https://blog.csdn.net/weixin_52195362/article/details/136451432)

有时候在数据库中我们需要将数据以JSON的形式存储，例如：图片地址数组列表。

在Java项目中可能是以这种形式来存储的：List< String > ，在数据库中是以JSON形式例如：[“http://地址1”，“http://地址2”,…]。现在需要在将Java实体对象存储到数据库中时能够将对应的List< String >数据自动转换为JSON数组，同时在查询出对象时能够将JSON数组自动转换为List< String >。

### JsonTypeHandler

处理器实现, 需要继承 **BaseTypeHandler< T >** 类，实现我们的转换逻辑。其他类型将泛型 **T** 替换为具体的实体类即可。

```java
package com.lfj.blog.config.mybatis.handler;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedTypes(value = { List.class })
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class JsonStringArrayTypeHandler extends BaseTypeHandler<List<String>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, JSON.toJSONString(parameter));
	}

	@Override
	@SneakyThrows
	public List<String> getNullableResult(ResultSet rs, String columnName) {
		String reString = rs.getString(columnName);
		return JSON.parseArray(reString,String.class);
	}

	@Override
	@SneakyThrows
	public List<String> getNullableResult(ResultSet rs, int columnIndex) {
		String reString = rs.getString(columnIndex);
		return JSON.parseArray(reString,String.class);
	}

	@Override
	@SneakyThrows
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) {
		String reString = cs.getString(columnIndex);
		return JSON.parseArray(reString,String.class);
	}
}
```

### Java实体类

+ 在实体类加上`@TableName(autoResultMap = true)`（不设置autoResultMap属性的话存入没问题，但是取出的时候该字段是null）
+  在`JSON`字段映射的属性加上`@TableField(typeHandler = JacksonTypeHandler.class)`；

```java
/**
* 测试实体
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "test",autoResultMap = true)
public class Test implements Serializable {
    /**
     * 数据库主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description="数据库主键")
    private Long id;

    /**
     * 工作情况图片列表,json数组
     */
    @TableField(value = "img_list",typeHandler = JsonStringArrayTypeHandler.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Schema(description="工作情况图片列表,json数组")
    @Size(max = 255,message = "工作情况图片列表,json数组最大长度要小于 255")
    private List<String> imgList;

    /**
     * 创建时间
     * */
    @TableField(value = "create_time")
    @Schema(description="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
```

### 配置文件

```yml
mybatis-plus:
  type-handlers-package: com.lfj.blog.config.mybatis.handler  #增加此项配置, 写JsonTypeHandler所在包
```

###  SQL文件

如果启动报错：No typehandler found for content....
那么在对应的mapper文件里面对应的JSON字段 添加typeHandler

```xml
<resultMap id="BaseResultMap" type="com.test.entity.User">
        <id column="id" property="id" />
        <result column="name" property="name" />
        <result column="content" property="content" typeHandler="com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler"/>
    </resultMap>
```

在`xml`中写`sql`语句时，需要将使用到 `JSON`字段的地方配置

```sql
<insert id="insertUser" parameterType="com.test.entity.User">
        insert into 
            user 
        values(#{id},#{name},#{content,jdbcType=OTHER,typeHandler=com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler})
    </insert>
```

## 自动配置原理

**SSM整合总结：**

1. **导入** `mybatis-spring-boot-starter`
2. 配置**数据源**信息
3. 配置mybatis的`**mapper接口扫描**`与`**xml映射文件扫描**`
4. 编写bean，mapper，生成xml，编写sql 进行crud。**事务等操作依然和Spring中用法一样**
5. 效果：

6. 1. 所有sql写在xml中
   2. 所有`mybatis配置`写在`application.properties`下面

- `jdbc场景的自动配置`： 

- - `mybatis-spring-boot-starter`导入 `spring-boot-starter-jdbc`，jdbc是操作数据库的场景
  - `Jdbc`场景的几个自动配置

- - - org.springframework.boot.autoconfigure.jdbc.**DataSourceAutoConfiguration**

- - - - **数据源的自动配置**
      - 所有和数据源有关的配置都绑定在`DataSourceProperties`
      - 默认使用 `HikariDataSource`

- - - org.springframework.boot.autoconfigure.jdbc.**JdbcTemplateAutoConfiguration**

- - - - 给容器中放了`JdbcTemplate`操作数据库

- - - org.springframework.boot.autoconfigure.jdbc.**JndiDataSourceAutoConfiguration**
    - org.springframework.boot.autoconfigure.jdbc.**XADataSourceAutoConfiguration**

- - - - **基于XA二阶提交协议的分布式事务数据源**

- - - org.springframework.boot.autoconfigure.jdbc.**DataSourceTransactionManagerAutoConfiguration**

- - - - **支持事务**

- - **具有的底层能力：数据源、**`JdbcTemplate`、**事务**

- `MyBatisAutoConfiguration`：配置了MyBatis的整合流程

- - `mybatis-spring-boot-starter`导入 `mybatis-spring-boot-autoconfigure（mybatis的自动配置包）`，
  - 默认加载两个自动配置类：

- - - org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration
    - org.mybatis.spring.boot.autoconfigure.**MybatisAutoConfiguration**

- - - - **必须在数据源配置好之后才配置**
      - 给容器中`SqlSessionFactory`组件。创建和数据库的一次会话
      - 给容器中`SqlSessionTemplate`组件。操作数据库

- - **MyBatis的所有配置绑定在**`MybatisProperties`
  - 每个**Mapper接口**的**代理对象**是怎么创建放到容器中。详见**@MapperScan**原理：

- - - 利用`@Import(MapperScannerRegistrar.class)`批量给容器中注册组件。解析指定的包路径里面的每一个类，为每一个Mapper接口类，创建Bean定义信息，注册到容器中。

如何分析哪个场景导入以后，开启了哪些自动配置类。

找：`classpath:/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`文件中配置的所有值，就是要开启的自动配置类，但是每个类可能有条件注解，基于条件注解判断哪个自动配置类生效了。

##  快速定位生效的配置

```plain
#开启调试模式，详细打印开启了哪些自动配置
debug=true
# Positive（生效的自动配置）  Negative（不生效的自动配置）
```

## 扩展：整合其他数据源

###  Druid 数据源

暂不支持 `SpringBoot3`

- 导入`druid-starter`
- 写配置
- 分析自动配置了哪些东西，怎么用

Druid官网：https://github.com/alibaba/druid

# SpringBoot基于注解来动态切换数据源

具体代码见: 后端/note02/8-整合/综合/Springboot-Notebook/springboot101/数据库

## 数据库设置

### 数据库

<img src="SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115190721627.png" alt="image-20240115190721627" style="zoom: 80%;" />

### 表

分别在两个数据库中创建一张相同的表

```sql
DROP TABLE if EXISTS t_dynamic_datasource_data;

CREATE TABLE IF NOT EXISTS `t_dynamic_datasource_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
```

在mydruid中插入如下数据

```sql
insert into t_dynamic_datasource_data (source_name) value ('dynamic_datasource_master');
```

在mydruid2中插入如下数据

```sql
INSERT INTO t_dynamic_datasource_data (source_name) VALUE ('dynamic_datasource_slave');
```

## 配置文件

```yaml
mybatis:
    configuration:
        map-underscore-to-camel-case: true
    mapper-locations: classpath:/mapper/*.xml
server:
    port: 9000
spring:
    datasource:
        druid:
            master:
                driver-class-name: com.mysql.cj.jdbc.Driver
                password: 741106
                type: com.zaxxer.hikari.HikariDataSource
                url: jdbc:mysql://localhost:3306/mydruid
                username: root
            slave:
                driver-class-name: com.mysql.cj.jdbc.Driver
                password: 741106
                type: com.zaxxer.hikari.HikariDataSource
                url: jdbc:mysql://localhost:3306/mydruid2
                username: root
```

## 配置类

![image-20240115201912607](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115201912607.png)

具体代码见:  后端/note02/8-整合/综合/Springboot-Notebook/springboot101/数据库

## 自定义注解

![image-20240115202028155](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115202028155.png)

具体代码见:  后端/note02/8-整合/综合/Springboot-Notebook/springboot101/数据库

## entity

```kotlin
package com.dynamic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 实体类
 */
@TableName("t_dynamic_datasource_data")
@Data
public class DynamicDatasourceData {

    private Long id;
    private String sourceName;
}
```

## mapper

```kotlin
package com.dynamic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dynamic.entity.DynamicDatasourceData;
import org.apache.ibatis.annotations.Mapper;
/**
 * mapper
 */
@Mapper
public interface DynamicDatasourceDataMapper extends BaseMapper<DynamicDatasourceData> {
}
```

## controller

```kotlin
package com.dynamic.controller;

import com.dynamic.aspect.DS;
import com.dynamic.config.DataSourceContextHolder;
import com.dynamic.dao.DynamicDatasourceDataMapper;
import com.dynamic.entity.DynamicDatasourceData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 动态数据源切换
 *
 * @date 2023/11/27 11:02
 */
@RestController
public class DynamicSwitchController {

    @Resource
    private DynamicDatasourceDataMapper dynamicDatasourceDataMapper;

    // 不使用@DS注解
    @GetMapping("/switchDataSource/{datasourceName}")
    public String switchDataSource(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContextHolder.setDataSource(datasourceName);
        DynamicDatasourceData dynamicDatasourceData = dynamicDatasourceDataMapper.selectOne(null);
        DataSourceContextHolder.removeDataSource();
        return dynamicDatasourceData.getSourceName();
    }

    // 使用@DS注解
    // 切换为数据源1
    @DS(value = "master")
    @GetMapping("/dbMaster")
    public String dbMaster() {
        DynamicDatasourceData dynamicDatasourceData = dynamicDatasourceDataMapper.selectOne(null);
        return dynamicDatasourceData.getSourceName();
    }

    // 切换为数据源2
    @DS(value = "slave")
    @GetMapping("/dbSlave")
    public String dbSlave() {
        DynamicDatasourceData dynamicDatasourceData = dynamicDatasourceDataMapper.selectOne(null);
        return dynamicDatasourceData.getSourceName();
    }


    /**
     * 验证一下事物控制
     */
//    @Transactional(rollbackFor = Exception.class)
    @DS(value = "slave")
    @GetMapping("/dbTestTransactional")
    public void dbTestTransactional() {

        DynamicDatasourceData datasourceData = new DynamicDatasourceData();
        datasourceData.setSourceName("test");
        dynamicDatasourceDataMapper.insert(datasourceData);

        DynamicDatasourceData datasourceData1 = new DynamicDatasourceData();
        datasourceData1.setSourceName("TestTest");
        dynamicDatasourceDataMapper.insert(datasourceData1);
    }
}
```

测试结果[localhost:9000](http://localhost:9000/)

**不使用注解**

![image-20240115195102297](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115195102297.png)

![image-20240115195121511](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115195121511.png)

**使用注解**

![image-20240115195202911](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115195202911.png)

![image-20240115195149842](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240115195149842.png)

# SpringBoot 整合 JPA

具体代码见:

<img src="SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240120144148147.png" alt="image-20240120144148147" style="zoom:67%;" />

## **表结构**

```sql
CREATE TABLE `meta_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(32) NOT NULL DEFAULT '' COMMENT '分组',
  `profile` varchar(32) NOT NULL DEFAULT '' COMMENT 'profile 目前用在应用环境 取值 dev/test/pro',
  `desc` varchar(64) NOT NULL DEFAULT '' COMMENT '解释说明',
  `deleted` int(4) NOT NULL DEFAULT '0' COMMENT '0表示有效 1表示无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `group_profile` (`group`,`profile`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='业务配置分组表';
```

## **java写法**

```java
@Data
@Entity  //实体类注解，自动建表必须添加
@Table(name = "meta_group") //设置生成的表名,不添加默认表名为实体类名
public class MetaGroupPO { 
    @Id   //标注主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增策略
    private Integer id;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`profile`")
    private String profile;

    @Column(name = "`desc`")
    private String desc;

    @Column(name = "`deleted`")
    private Integer deleted;

    @Column(name = "`create_time`")  //设置生成的字段名,不添加默认字段为变量名
    private Timestamp createTime;

    @Column(name = "`update_time`")
    private Timestamp updateTime;
}
```

**详细说明**

@Column注解： 可以不加

```
@Column注释定义了将成员属性映射到关系表中的哪一列和该列的结构信息，属性如下：
　　1）name：映射的列名。如：映射tbl_user表的name列，可以在name属性的上面或getName方法上面加入；
　　2）unique：是否唯一；
　　3）nullable：是否允许为空；
　　4）length：对于字符型列，length属性指定列的最大字符长度；
　　5）insertable：是否允许插入；
　　6）updatetable：是否允许更新；
　　7）columnDefinition：定义建表时创建此列的DDL；
　　8）secondaryTable：从表名。如果此列不建在主表上（默认是主表），该属性定义该列所在从表的名字
```

@Id注解：

```
@Id注释指定表的主键，它可以有多种生成方式：
　　1）TABLE：容器指定用底层的数据表确保唯一；
　　2）SEQUENCE：使用数据库德SEQUENCE列莱保证唯一（Oracle数据库通过序列来生成唯一ID）；
　　3）IDENTITY：使用数据库的IDENTITY列莱保证唯一；
　　4）AUTO：由容器挑选一个合适的方式来保证唯一；
　　5）NONE：容器不负责主键的生成，由程序来完成。
```

其中与@Id一起使用的还有另外两个注解：@GeneratedValue、@GenericGenerator，参考[@GeneratedValue与@GenericGenerator](https://blog.csdn.net/u011781521/article/details/72210980)

1. **@CreatedDate**:
   - **框架**：Spring Data JPA 提供。
   - **作用**：用于标注实体对象的创建时间字段，通常与 Spring Data JPA 的 `@EntityListeners(AuditingEntityListener.class)` 配合使用，实现在实体对象创建时自动填充创建时间的功能。
2. **@LastModifiedDate**:
   - **框架**：Spring Data JPA 提供。
   - **作用**：标注实体对象的最后修改时间字段，通常与 Spring Data JPA 的 `@EntityListeners(AuditingEntityListener.class)` 配合使用，实现在实体对象修改时自动填充最后修改时间的功能。

## JPA的Repository详解

### Spring Data JPA

Spring Data是Spring提供的操作数据的框架，Spring Data JPA是Spring Data的一个模块，通过Spring data 基于jpa标准操作数据的模块。
Spring Data的核心能力，就是基于JPA操作数据，并且可以简化操作持久层的代码。
它使用一个叫作Repository的接口类为基础，它被定义为访问底层数据模型的超级接口。而对于某种具体的数据访问操作，则在其子接口中定义。
Spring Data可以让我们只定义接口，只要遵循spring data的规范，就无需写实现类，不用写sql语句直接查询数据。

### Repository

- Repository

  提供了findBy + 属性 方法

  - CrudRepository

    继承了Repository 提供了对数据的增删改查

    - PagingAndSortRepository:

      继承了CrudRepository 提供了对数据的分页和排序，缺点是只能对所有的数据进行分页或者排序，不能做条件判断

      - JpaRepository： 继承了PagingAndSortRepository
        开发中经常使用的接口，主要继承了PagingAndSortRepository，对返回值类型做了适配

- JpaSpecificationExecutor
  提供多条件查询

#### CrudRepository

CrudRepository继承Repository，添加了一组对数据的增删改查的方法
![在这里插入图片描述](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hmeF8xOTk0,size_16,color_FFFFFF,t_70-17056397101662.png)

#### PagingAndSortingRepository

PagingAndSortingRepository继承CrudRepository，添加了一组分页排序相关的方法
![在这里插入图片描述](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/20200317154215841.png)

#### JpaRepository

JpaRepository继承PagingAndSortingRepository，添加了一组JPA规范相关的方法。对继承父接口中方法的返回值进行了适配,因为在父类接口中通常都返回迭代器，需要我们自己进行强制类型转化。而在JpaRepository中，直接返回了List。
**`开发中最常用JpaRepository`**
<img src="SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240119124952997.png" style="zoom:67%;" />

### JpaSpecificationExecutor

这个接口比较特殊，单独存在，没有继承以上接口。主要提供了多条件查询的支持，并且可以在查询中添加分页和排序。因为这个接口单独存在，因此需要配合以上说的接口使用。
![在这里插入图片描述](SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/20200317155238320.png)

### JpaRepository查询功能

#### Jpa方法命名规则

JpaRepository支持接口规范方法名查询。意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现，目前支持的关键字如下。

| Keyword           | Sample                         | JPQL snippet               |
| ----------------- | ------------------------------ | -------------------------- |
| And               | findByNameAndPwd               | where name= ? and pwd =?   |
| Or                | findByNameOrSex                | where name= ? or sex=?     |
| Is,Equals         | findById,findByIdEquals        | where id= ?                |
| Between           | findByIdBetween                | where id between ? and ?   |
| LessThan          | findByIdLessThan               | where id < ?               |
| LessThanEquals    | findByIdLessThanEquals         | where id <= ?              |
| GreaterThan       | findByIdGreaterThan            | where id > ?               |
| GreaterThanEquals | findByIdGreaterThanEquals      | where id > = ?             |
| After             | findByIdAfter                  | where id > ?               |
| Before            | findByIdBefore                 | where id < ?               |
| IsNull            | findByNameIsNull               | where name is null         |
| isNotNull,NotNull | findByNameNotNull              | where name is not null     |
| Like              | findByNameLike                 | where name like ?          |
| NotLike           | findByNameNotLike              | where name not like ?      |
| StartingWith      | findByNameStartingWith         | where name like ‘?%’       |
| EndingWith        | findByNameEndingWith           | where name like ‘%?’       |
| Containing        | findByNameContaining           | where name like ‘%?%’      |
| OrderBy           | findByIdOrderByXDesc           | where id=? order by x desc |
| Not               | findByNameNot                  | where name <> ?            |
| In                | findByIdIn(Collection<?> c)    | where id in (?)            |
| NotIn             | findByIdNotIn(Collection<?> c) | where id not in (?)        |
| True              | findByAaaTue                   | where aaa = true           |
| False             | findByAaaFalse                 | where aaa = false          |
| IgnoreCase        | findByNameIgnoreCase           | where UPPER(name)=UPPER(?) |
| top               | findTop10                      | top 10/where ROWNUM <=10   |

#### 使用方法

使用时自定义接口继承JpaRepository，传入泛型，第一个参数为要操作的实体类，第二个参数为该实体类的主键类型

```java
public interface SpuRepository extends JpaRepository<Spu, Long> {
    Spu findOneById(Long id);

    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);

    Page<Spu> findByRootCategoryIdOrderByCreateTime(Long cid, Pageable pageable);
}
```

#### 解析过程

**1.** Spring Data JPA框架在进行方法名解析时，会先把方法名多余的前缀截取掉，比如find，findBy，read，readBy，get，getBy，然后对剩下的部分进行解析。
**2.** 假设创建如下查询findByCategoryId(),框架在解析该方法时，首先剔除findBy，然后对剩下的属性进行解析，假设查询实体为Spu

```java
(1)先判断categoryId（根据POJO 规范，首字母变为小写）是否为查询实体的一个属性，如果是，则表示根据该属性进行查询；如果没有该属性，继续第二步；

(2)从右往左截取第一个大写字母开头的字符串此处为Id），然后检查剩下的字符串是否为查询实体的一个属性，如果是，则表示根据该属性进行查询；
如果没有该属性，则重复第二步，继续从右往左截取；最后假设user为查询实体的一个属性；

(3)接着处理剩下部分（CategoryId），先判断用户所对应的类型是否有categoryId属性，如果有，则表示该方法最终是根据"Spu.categoryId"的取值进行查询;
否则继续按照步骤2的规则从右往左截取。

(4)可能会存在一种特殊情况，比如Spu包含一个categoryId 的属性，也有一个 rootCategoryId属

性，此时会存在混淆。可以明确在属性之间加上"_" 以显式表达意图，比如

"findByRoot_CategoryId()" 
12345678910111213
```

**3.** 特殊参数
可以直接在方法的参数上加入分页或排序的参数，比如：

```java
Page<Spu> findByCategoryId(Long cid, Pageable pageable);

List<Spu> findByCategoryId(Long cid, Sort sort);
123
```

**4.** JPA的`@NamedQueries`

```java
(1)1：在实体类上使用@NamedQuery
@NamedQuery(name = "Spu.findByRootCategoryId",query = "select s from Spu s where s.rootCategoryId >= ?1")
(2)在自己实现的DAO的Repository接口里面定义一个同名的方法，示例如下：
public List<Spu> findByRootCategoryId(Long rootCategoryId);
(3)然后Spring会先找是否有同名的NamedQuery，如果有，那么就不会按照接口定义的方法来解析。
12345
```

**5.** 使用`@Query`
在方法上标注@Query来指定本地查询
参数nativeQuery默认为false，nativeQuery=false时，value参数写的是JPQL，JPQL是用来操作model对象的

```java
@Query(value="select s from Spu s where s.title like %?1" )
public List<Spu> findByTitle(String title);
12
```

nativeQuery=true时，value参数写的是原生sql

```java
@Query(value="select * from spu s where s.title like %?1",nativeQuery=true )
public List<Spu> findByTitle(String title);
12
```

**6.** 使用@Param命名化参数

```java
@Query(value = "select s from Spu s where s.title in (:titles)")
List<Spu> findByTitle(@Param("titles") List<String> titles);
```

## 基本使用

###  实体类

```java
package com.springboot101.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity   //实体类注解，自动建表必须添加
@Table(name = "t_user")  //设置生成的表名,不添加默认表名为实体类名
public class User {

    @Id   //标注主键
    @GeneratedValue(strategy = GenerationType.AUTO)   //id自增策略
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Column(name = "create_time")  //设置生成的字段名,不添加默认字段为变量名
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
```

### 编写xxxRepository

```java
package com.springboot101.repository;

import com.springboot101.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

### service

```java
package com.springboot101.service;

import com.springboot101.po.User;
import com.springboot101.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserService {

    @Resource
    private UserRepository userRepository;


    public void insertUser(User user){

        userRepository.save(user);
    }
}
```

参考

<img src="SpringBoot3-%E6%95%B0%E6%8D%AE%E8%AE%BF%E9%97%AE.assets/image-20240124213604318.png" alt="image-20240124213604318" style="zoom:67%;" />

# 实现Serializable接口的作用和必要性(了解)

显式添加 `implements Serializable`:

+ 存储到数据库
+ 网络传输

注意: 

+ 如果网络传输也就是前后端联调使用地是json字符串也不用序列化，

+ 存储mysql和redis的时候mysql会自己映射以及redis的也有自己的一套序列化方式，比如在config类里定义redisTemplate的序列化方式

在 MyBatis 3.3.0 版本及以后，对于 MyBatis 实体类不再强制要求手动实现 `Serializable` 接口。你不再需要为你的 MyBatis 实体类显式添加 `implements Serializable`。

```java
public class User implements Serializable {

    private int id;

//    transient 标记的字段不会参与序列化
//    private transient String name;

    private String name;

    private int age;

    private String address;

    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("name", String.class),
            new ObjectStreamField("age", Integer.class)
    };

    ...

}
```

比如一个对象

```
@Entity
@Table(name = "test", schema = "example")
public class Test implements Serializable{
    private int id;
    private String name;
    ……
}
```

如果没有用implements Serializable时，数据以此Test对象封装存进数据库，当要取出时，即使**SQL**在数据库中能正确返回数据，但sessionFactory.getCurrentSession().createNativeQuery(**SQL**,Test.class).list()返回的数据中字段内容都是空（数据条目数量正常不受影响），从而会导致诸如Method threw 'javax.persistence.PersistenceException' exception.等错误。

总而言之，就是保障数据完璧归赵的取或读。

# 其他

手动写的实体类

```java
@REntity   //Redis的基础上实现的
@Data
public class OrderInfo {

    @RId
    private Integer id;

    @RIndex
    private String name;

    @RIndex
    private Integer age;
}
```

service

```java
@Autowired
OrderInfo orderInfo;  无法自动装配。找不到 'OrderInfo' 类型的 Bean。
```

推荐使用

```
OrderInfo orderInfo = new OrderInfo();
```





# xml 总结

```xml
    <!-- 查询上一篇和下一篇
    具体来说，这个查询可以分为两部分：
第一个子查询选取比给定 id 小的文章中 id 最大的一篇，并且符合 deleted = 0 和 status = 0 的条件。这一部分用于获取上一篇文章的信息。
第二个子查询选取比给定 id 大的文章中 id 最小的一篇，并且同样符合 deleted = 0 和 status = 0 的条件。这一部分用于获取下一篇文章的信息。
然后，这两个子查询的结果通过 union all 进行合并，最终将上一篇和下一篇文章的信息组合在一起返回。
    -->
    <select id="selectPreAndNext" resultMap="BaseResultMap">
        (select a.id,
                a.title
         from article a
         where a.id <![CDATA[<]]> #{id}
           and a.deleted = 0
           and a.status = 0
         order by a.id desc limit 1)
        union all
        (select b.id,
                b.title
         from article b
         where b.id > #{id}
           and b.deleted = 0
           and b.status = 0
         order by b.id limit 1)
    </select>
```

## MyBatis中的动态SQL

[MyBatis——动态SQL的四个常用标签（＜if＞、＜where＞、＜foreach＞、＜sql＞）](https://blog.csdn.net/weixin_43823808/article/details/114393656)



## MyBatis foreach 标签常用方法总结

[MyBatis之foreach的用法_mybatis foreach数组-CSDN博客](https://blog.csdn.net/qq_20236937/article/details/130704480?ops_request_misc=%7B%22request%5Fid%22%3A%22172362581016800207051703%22%2C%22scm%22%3A%2220140713.130102334.pc%5Fall.%22%7D&request_id=172362581016800207051703&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~hot_rank-4-130704480-null-null.142^v100^pc_search_result_base7&utm_term=mybatis之foreach用法&spm=1018.2226.3001.4187)





## `<resultMap>`

**适用:** 

+ 如果实体的每个字段都和数据库中的字段名称 是不一样的，就采用的是ResultMap来对实体字段和数据库的字段进行统一的映射

+ 如果只是一两个字段是不一致的，则直接对不同的那两个字段起别名进行字段对应即可！

**可以配置mybatis驼峰命名规则自动转换: **

- 使用前提：

  + 数据库表设计按照规范“字段名中各单词使用下划线"_"划分”；
  + 即数据库(xxx_yyy)自动和java(xxxYyy)匹配

- 使用好处：

  + 省去mapper.xml文件中繁琐编写表字段列表与表实体类属性的映射关系，即编写resultMap

  + 只需要为resultType指定数据库表对应

  + 但是考虑程序的安全性以及映射灵活性，通常开发中还是将resultMap结合使用。

    

# 总结:crown:

## VO/DTO

适用: 

+ 单表查询且想要查询的不只是该表存在的字段  (不一定非得写xml, 可以都单表查询出来再拼接成vo)
+ 多表查询

### 分页配置

开启MybatisPlus的[分页插件](https://so.csdn.net/so/search?q=分页插件&spm=1001.2101.3001.7020)

```java
@Configuration
@MapperScan("com.example.nb_road.mapper")
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor=new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```

### 实体类及VO

实体类：路段。路段中的roadId代表了所属道路的id，因此需要联表查询所属道路的名称。

```java
package com.example.nb_road.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "section")
@Data
public class RoadSection implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long roadId;
    
    private Double length;
    
    private String start;
    
    private String end;
    
    private String coordinates;
    
    private Integer state;
    
    private Integer isDeleted;
}
```

VO类在继承实体类的基础上，增加道路名称属性。

```java
package com.example.nb_road.entity.vo;

import com.example.nb_road.entity.RoadSection;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoadSectionVO extends RoadSection {
    private String roadName;
}
```

### Mapper接口

```java
@Repository
public interface RoadSectionMapper extends BaseMapper<RoadSection> {
    /**
     * 自定义分页查询
     *
     * @param page     分页对象
     * @param roadName 其中一个查询参数，路段名称
     * @return 包含的查询结果的分页对象
     */
    IPage<RoadSectionVO> selectPage(IPage<RoadSectionVO> page, @Param("roadName") String roadName);
}
```

### Mapper文件（xml）

这里做了一个条件判断，如果给定的路段名称为空，那么就不包含该项。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.nb_road.mapper.RoadSectionMapper">
    <select id="selectPage" resultType="com.example.nb_road.entity.vo.RoadSectionVO">
        select s.id, r.road_name
        from section s join road r on s.road_id = r.id
        <where>
            <if test="roadName != null and roadName != ''">and r.road_name = # {roadName}</if>
        </where>
    </select>
</mapper>
```

### 测试

```java
@Slf4j
@SpringBootTest
class RoadSectionMapperTest {
    @Autowired
    private RoadSectionMapper roadSectionMapper;
    
    @Test
    void testPage(){
        // 构建分页对象
        IPage<RoadSectionVO> page = new Page<>(1, 2);
        // 调用查询方法
        IPage<RoadSectionVO> roadSectionIPage = roadSectionMapper.selectPage(page, "雪里段");
        // 从分页对象中取出查询结果
        List<RoadSectionVO> records = roadSectionIPage.getRecords();
        records.forEach(System.out::println);
    }
}
```

### 控制台输出

```
JDBC Connection [HikariProxyConnection@994185757 wrapping com.mysql.cj.jdbc.ConnectionImpl@7a2ab862] will not be managed by Spring
==>  Preparing: SELECT COUNT(*) AS total FROM section s JOIN road r ON s.road_id = r.id WHERE r.road_name = ?
==> Parameters: 雪里段(String)
<==    Columns: total
<==        Row: 3
<==      Total: 1
==>  Preparing: select s.id, r.road_name from section s join road r on s.road_id = r.id WHERE r.road_name = ? LIMIT ?
==> Parameters: 雪里段(String), 2(Long)
<==    Columns: id, road_name
<==        Row: 1, 雪里段
<==        Row: 2, 雪里段
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2a53f215]
RoadSectionVO(roadName=雪里段)
RoadSectionVO(roadName=雪里段)
```



##  LambdaQueryWrapper

适用:  单表查询且想要查询的就是该表存在的字段

```java
LambdaQueryWrapper<xxx> queryWrapper = new LambdaQueryWrapper<>();
queryWrapper.xxx....

List<xxx> list = userMapper.selectList(queryWrapper);   // 查询单表数据
Integer count = userMapper.selectCount(queryWrapper);   // 查询数量
IPage<xxx> userIPage = userMapper.selectPage(new Page<>(current, size) , queryWrapper); // 分页
```

LambdaWrapper去实现去重查询

```java
@Override
    public Integer getCountAbPressure(String customerId, LocalDateTime firstDay, LocalDateTime lastDay, List<String> list, Integer type) {
        QueryWrapper<CustomerBodyMetricsEntity> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT 去重字段")
                .lambda()
                .eq(CustomerBodyMetricsEntity::getCustomerId, customerId)
                .ge(CustomerBodyMetricsEntity::getVersion, DateTimeUtil.dateTimeToTimestamp(firstDay))
                .le(CustomerBodyMetricsEntity::getVersion, DateTimeUtil.dateTimeToTimestamp(lastDay))
                .in(CustomerBodyMetricsEntity::getMetric, list)
                .ne(CustomerBodyMetricsEntity::getLabel, 20);
        return this.count(wrapper);
    }
```

