# 简要

Spring 总共大约有 20 个模块，由 1300 多个不同的文件构成。而这些组件被分别整合在6 个模块中：

1. 核心容器（Core Container）
2. AOP（Aspect Oriented Programming）
3. 设备支持（Instrmentation）
4. 数据访问及集成（Data Access/Integeration）
5. Web报文发送（Messaging）
6. Test测试

**Spring两大核心 : **

+ DI：依赖注入（Dependency Injection）
+  AOP：面向对象面编程（Aspect Oriented Programming）

# 1. IOC与DI

DI：依赖注入（Dependency Injection）：依赖其他容器（比如spring）来创建和维护所需要的组件，并将其注入到应用程序中。

IOC：控制反转（Inversion of Control）：它是一种控制权的转移。即组件与组件之间的依赖由主动变为被动。也就是说：应用程序本身不再负责组件的创建、维护等，而是将控制权移交出去。从这一点来说，几乎所有的框架都是IOC框架。

IOC只是将组件控制权移交出去，但并没有说明组件如何获取。而DI明确说明：组件依赖Spring容器获取。 所以可以这样说：DI是IOC思想的一种具体实现。

# 2. DI（依赖注入）

依赖注入（Dependency Injection）是一种设计模式，也是Spring框架的核心概念之一。其作用是去除组件之间的依赖关系，实现解耦合。 也就是说：所谓依赖注入，是指工程中需要的组件无须自己创建，而是依赖于外部环境注入。

Spring实现依赖注入有三种方式：注解方式（官方推荐方式）、xml配置文件方式、javaConfig方式。

## 2.1.xml方式

下面使用 Spring 来重构dao层组件与service层组件。 也就是说：由Spring创建dao层组件和service层组件，并使用Spring将dao层组件注入给service层组件。

例子

### 2.1.1.添加Spring依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.neusoft</groupId>
  <artifactId>springtest</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <build>
    <plugins>
      <!-- 设置jdk版本 -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
          <encoding>utf-8</encoding>
        </configuration>
      </plugin>
    </plugins>
  </build>
  <properties>
    <!-- spring 版本号 -->
    <spring.version>5.2.8.RELEASE</spring.version>
  </properties>
  <dependencies>
    <!-- 此依赖会关联引用Spring中的所有基础jar包 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>
  </dependencies>
</project>
```

### 2.1.2.创建dao接口与实现类

**UserDao.java**

```java
package com.neusoft.dao;
import com.neusoft.po.User;
public interface UserDao {
    public User getUser();
}
```

**UserDaoImpl.java**

```java
package com.neusoft.dao.impl;
import com.neusoft.dao.UserDao;
import com.neusoft.po.User;
public class UserDaoImpl implements UserDao{
    @Override
    public User getUser() {
        return new User(1,"test","111");
    }
}
```

### 2.1.3.创建service接口与实现类

**UserService.java**

```java
package com.neusoft.service;
import com.neusoft.po.User;
public interface UserService {
    public User getUser();
}
```

**UserServiceImpl.java**

```java
package com.neusoft.service.impl;
import com.neusoft.dao.UserDao;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
public class UserServiceImpl implements UserService{
    private UserDao userDao;
    @Override
    public User getUser() {
        return userDao.getUser();
    }
    public UserDao getUserDao() {
        return userDao;
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

### 2.1.4.创建Spring配置文件

在类路径下创建spring.xml配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
  xmlns="http://www.springframework.org/schema/beans"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
  http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context 
  http://www.springframework.org/schema/context/spring-context.xsd
  http://www.springframework.org/schema/aop 
  http://www.springframework.org/schema/aop/spring-aop-4.1.xsd">
  
   <!--类注册-->
  <bean id="userDao" class="com.neusoft.dao.impl.UserDaoImpl"></bean>
  <bean id="userService" class="com.neusoft.service.impl.UserServiceImpl">
    <property name="userDao" ref="userDao"/>
  </bean>
  
</beans>
```

1. Spring配置文件就相当于一个容器。此容器中负责创建对象，并实现对象与对象之间的装配。
2. java中每一个类都是一个bean。所以上面的bean标签，就是在容器中创建一个java对象。
3. bean标签中的class属性，就是类名； id属性，就是对象名。
4. property标签，是给bean的属性注入其它对象。name属性，就是对象属性名； ref属性，就是给属性注入的对象。（如果想要注入基本数据类型，那么使用value属性）
5. 给bean的属性注入其它对象，默认使用 get/set 方法注入。也可以使用其它方式注入：构造方法注入、P命名空间注入等。

### 2.1.5. 测试

```java
package com.neusoft;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
public class MySpringTest {
    public static void main(String[] args) {
        //读取Spring配置文件,获取Spring容器
        ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
        //通过Spring容器的getBean方法获得对象
        UserService service = (UserService)context.getBean("userService");
        User user = service.getUser();
        System.out.println(user);
    }
}
```

## 2.3.注解方式

注解（Annotation），也叫元数据。它是一种代码级别的说明，是jdk1.5之后引入的一个特性。

注解的作用：

1. 编写文档：通过代码里标识的元数据生成文档。
2. 代码分析：通过代码里标识的元数据对代码进行分析。
3. 编译检查：通过代码里标识的元数据让编译器能够实现基本的编译检查。

```java
//@Override就是一个编译检查注解
@Override
public int saveBusiness(String businessName) {}
```

注解基本语法：@注解名称(属性=属性值)

### 2.3.1.修改dao实现类

```java
package com.neusoft.dao.impl;
import org.springframework.stereotype.Component;
import com.neusoft.dao.UserDao;
import com.neusoft.po.User;

@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public User getUser() {
        return new User(1,"test","111");
    }
}
```

@Component：创建此类的对象，并放入到Spring容器中。 @Component("xxxx")：创建此类的对象，取一个对象名，并放入到Spring容器中。

### 2.3.2.修改Service实现类

```java
package com.neusoft.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.neusoft.dao.UserDao;
import com.neusoft.po.User;
import com.neusoft.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public User getUser() {
        return userDao.getUser();
    }
    //注意：UserDao属性自动注入，所以就可以不用get/set方法了
}
```

@Autowired：默认按照类型在Spring容器寻找对象，并注入到属性中。 所以此时要注意：UserDao接口的实现类只能有一个。

### 2.3.3.修改Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
  xmlns="http://www.springframework.org/schema/beans"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
  http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context 
  http://www.springframework.org/schema/context/spring-context.xsd
  http://www.springframework.org/schema/aop 
  http://www.springframework.org/schema/aop/spring-aop-4.1.xsd">
  <!--开启注解扫描，设置需要扫描的包  -->
  <context:component-scan base-package="com.neusoft"/>
</beans>
```

context:component-scan标签中的base-package属性，设置需要扫描的包。 会到指定包（包括指定包下的所有子包）中扫描类、方法、属性上面是否有注解。（如有多个，可使用逗号分隔）

### 2.3.4.测试

```java
package com.neusoft;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
public class MySpringTest {
    public static void main(String[] args) {
        //读取Spring配置文件,获取Spring容器
        ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
        //通过Spring容器的getBean方法获得对象
        UserService service = (UserService)context.getBean("userService");
        User user = service.getUser();
        System.out.println(user);
    }
}
```

除了@Component这个泛指组件的注解外，Spring还提供了与@Component功能相同的三个语义化注解。

1. @Service 业务层组件
2. @Controller 控制层组件
3. @Repository 数据层组件

修改上面代码，使用@Repository 和 @Service 替换 dao 与 service 组件上的注解。





@Scope() 注解：

设置注解的作用域

| 参数      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| singleton | 单实例的(`单例`)(默认)   ----全局有且仅有一个实例            |
| prototype | 多实例的(`多例`)                ---- 每次获取Bean的时候会有一个新的实例 |
| reqeust   | 同一次请求 ----request：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前`HTTP request`内有效 |
| session   | 同一个会话级别 ---- session：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前`HTTP session`内有效 |

**例子1**

在UserServiceImpl中添加@Scope注解：

```java
@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService{ }
```

在测试类中测试多例：

```java
ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
UserService service1 = (UserService)context.getBean("userService");
UserService service2 = (UserService)context.getBean("userService");
System.out.println(service1==service2);      //false
```

**例子2**

```cpp
public class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
```

```java
@Configuration
public class ProtoTypeConfig {

    @Scope("singleton")
    @Bean
    public Person person() {
        return new Person("李四", 55);
    }
```

测试代码

```java
@Test
public void test4() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ProtoTypeConfig.class);
    Person person1 = ctx.getBean(Person.class);
    Person person2 = ctx.getBean(Person.class);
    System.out.println("person1 HashCode  " + person1.hashCode());
    System.out.println("person2 HashCode  " + person2.hashCode());
    System.out.println(person1 == person2);  // true
}
```

## 2.4.javaConfig方式

==@Configuration+@Bean  bean注册(人为) —> 用于配置类==

javaConfig，是在 Spring 3.0 开始从一个独立的项目并入到 Spring 中的。javaConfig 可以看成一个用于完成 Bean 装配的 Spring 配置文件，即 Spring 容器，只不过该容器不是 XML文件，而是由程序员使用 java 自己编写的 java 类。

一个类中只要标注了@Configuration注解，这个类就可以为spring容器提供Bean定义的信息了，或者说这个类就成为一个spring容器了。

标注了@Configuration和标注了@Component的类一样是一个Bean，可以被Spring的 context:component-scan 标签扫描到。类中的每个标注了@Bean的方法都相当于提供了一个Bean的定义信息。

```java
package com.neusoft;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.neusoft.dao.UserDao;
import com.neusoft.dao.impl.UserDaoImpl;
import com.neusoft.service.UserService;
import com.neusoft.service.impl.UserServiceImpl;
@Configuration
public class AppConfig {
    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }
    @Bean
    public UserService userService() {
        //这里不能声明接口类型
        UserServiceImpl userService = new UserServiceImpl();
        //配置依赖关系（需要set方法）
        userService.setUserDao(userDao());
        return userService;
    }
}
```

### 2.4.2.测试

```java
ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
UserService service = (UserService)context.getBean("userService");
User user = service.getUser();
System.out.println(user);

/*
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
context.register(AppConfig.class);
context.refresh();
UserService service = (UserService)context.getBean("userService");
User user = service.getUser();
System.out.println(user);
*/
```

# 3.AOP（面向切面）

## 3.1 概要

AOP：全称是 Aspect Oriented Programming 即：面向切面编程。

简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的基础上，对我们的已有方法进行增强。

即当需要扩展功能时，传统方式采用纵向继承方式实现。但这种方式有很多缺点。 比如：父类方法名称改变时，子类也要修改。给多个方法扩展功能时，子类也需要修改。 因此，spring的AOP，实际上是采用横向抽取机制，取代传统的纵向继承体系。

实现AOP示意图：

```xml
1. 目标类 原始类
	指的是 业务（Service）类（核心功能 --> 业务运算 DAO调用）
2. 目标方法，原始方法
	目标类(原始类)中的方法 就是目标方法(原始方法)
3. 额外功能（附加功能）
```

静态代理

+ Service接口 提供方法
+ ServiceImpl类 实现接口的方法- 核心功能
+ ServiceProxy类 实现接口的方法- 再加上额外功能，同时调用 ServiceImpl类 的对应方法

缺点

+ 不方便管理，每一个Service都需要一个Proxy类
+ 不方便管理，额外功能修改起来可能会改很多的地方

![image-20230928213259692](spring笔记.assets/image-20230928213259692.png)

## 3.2 使用注解方式实现AOP

下面例子功能为：在运行业务方法前，输出一段日志。

### 3.2.1 添加aspectj依赖

Aspectj是一个基于java的、面向切面的AOP框架。Spring2.0之后增加了对Aspectj切点表达式的支持。而实际开发中一般都使用Aspectj方式来实现AOP。所以还要导入Aspectj相关jar包。

```xml
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.8.7</version>
</dependency>
```

### 3.2.2 抽取方面代码封装通知对象

在实际开发中，除了业务逻辑这个主要功能之外，还需要处理许多辅助功能。 比如：日志、异常处理、事务、输入验证、安全等等，我们将这些代码称为：**方面代码**。而方面代码，就是我们要抽取出来的。

下面抽取日志方面代码：

```java
package com.neusoft.advice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Component
@Aspect     //@Aspect定义此类为方面代码，即是一个通知。
public class MyAdvice {
    @Before("execution(* com.neusoft.service.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinpoint){
        System.out.println("【前置通知日志】" + joinpoint.toString());
    }
}
```

1. @Aspect注解：定义此类为方面代码，即是一个通知。
2. @Before注解：定义一个前置通知。即在目标方法执行前切入此注解标注的方法。
3. execution() 是一个Aspect表达式，语法为：execution(返回值类型 包名.类名.方法名 (参数) 异常)

```java
/*
* 例如：execution(* com.neusoft.service.impl.*.*(..))
* 第一个 *：所有的返回值类型
* 第二个 *：所有的类
* 第三个 *：所有的方法
* 第四个 .. ：所有的参数
*/
```

### 3.2.3 修改Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
  xmlns="http://www.springframework.org/schema/beans"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
  http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context 
  http://www.springframework.org/schema/context/spring-context.xsd
  http://www.springframework.org/schema/aop 
  http://www.springframework.org/schema/aop/spring-aop-4.1.xsd">
  <!--开启注解扫描，设置需要扫描的包  -->
  <context:component-scan base-package="com.neusoft"/>
  <!-- 声明自动为spring容器中那些配置@Aspect切面的bean创建代理，织入切面。 -->
  <aop:aspectj-autoproxy/>
</beans>
```

aop:aspectj-autoproxy标签：声明自动为spring容器中那些配置@Aspect切面的bean创建代理，织入切面。

### 3.2.4 测试

```java
public static void main(String[] args) {
    //读取Spring配置文件,获取Spring容器
    ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
    //通过Spring容器的getBean方法获得对象
    UserService service = (UserService)context.getBean("userService");
    User user = service.getUser();
    System.out.println(user);
}
```

## 3.3 五种通知类型

方面代码一般也称为通知：定义一个“切面”要实现的功能。通知有五种：

1. 前置通知：在某连接点（JoinPoint 就是要织入的业务方法）之前执行的通知。
2. 后置通知：当某连接点退出时执行的通知（不论是正常结束还是发生异常）。
3. 返回通知：（最终通知）在这里可以得到业务方法的返回值。但在发生异常时无法得到返回值。
4. 环绕通知：包围一个连接点的通知，也就是在业务方法执行前和执行后执行的通知。
5. 异常通知：在业务方法发生异常时执行的通知。

```java
package com.neusoft.advice;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class MyAdvice {
    //定义通用Aspect表达式，下面通知方法就可以引用此方法的规则了
    @Pointcut("execution(* com.neusoft.service.impl.*.*(..))")
    private void anyMethod(){}
    @Before("anyMethod()")
    public void beforeMethod(JoinPoint joinpoint){
        System.out.println("【前置通知日志】" + joinpoint.toString());
    }
    @After("anyMethod()")
    public void afterMethod(JoinPoint joinpoint){
        System.out.println("后置通知日志" + joinpoint.toString());
    }
    @AfterReturning(pointcut="anyMethod()",returning="result")
    public void afterReturnning(JoinPoint joinpoint,Object result){
        System.out.println("返回通知日志" + joinpoint.toString());
    }
    @AfterThrowing(pointcut="anyMethod()",throwing="ex")
    public void afterThrowing(JoinPoint joinpoint,Exception ex){
        System.out.println("异常通知日志" + joinpoint.toString());
    }
    @Around("anyMethod()")
    public Object aroundMethod(ProceedingJoinPoint pjp) {
        Object obj = null;
        try{
            System.out.println("环绕通知日志" + pjp.toString());
            obj = pjp.proceed();
        }catch(Throwable e){
            e.printStackTrace();
        }
        return obj;
    }
}
```

注意：

1. 如果配置了环绕通知，那么业务方法的执行将在环绕通知中的obj = pjp.proceed();这段代码时开始执行。此时要注意：如果环绕通知方法不写返回值，或者obj = pjp.proceed()这段代码如果不取得返回值，那么返回通知也不能取得返回值。
2. 有了环绕通知，异常通知也将失去作用。
3. 实际上，如果要配置环绕通知，那么其他通知就失去意义了。因为在环绕通知中，也可以在方法执行前、执行后做方面代码，包括获取返回值、做异常处理等。

## 3.4 Spring动态代理的两种形式

### 3.4.1 两种动态代理

动态代理是一种常用的设计模式，广泛应用于框架中，Spring框架的AOP特性就是应用动态代理实现的。

<img src="spring笔记.assets/image-20230928214106218.png" alt="image-20230928214106218" style="zoom: 80%;" />

实现动态代理有两种形式：

1. jdk动态代理：根据目标类接口获取代理类实现规则，生成代理对象。这个代理对象，也是目标类接口的一个实现类。
2. cglib动态代理：根据目标类本身获取代理类实现规则，生成代理对象。这个代理对象，也是目标类的一个子类。 （如果目标类为final，则不能使用CGLib实现动态代理）

SpringAOP可以自动在jdk动态代理和CGLib动态代理之间进行切换，规则如下：

1. 如果目标对象实现了接口，采用jdk动态代理实现aop。
2. 如果目标对象没有实现接口，采用CGLib动态代理实现aop。
3. 如果目标对象实现了接口，但仍然想要使用CGLIB实现aop，可以手动进行配置。

我们通过获取执行代码所耗费的时间，来实际测试两种动态代理方式的性能对比。

1. jdk动态代理测试：

```java
public static void main(String[] args) {
    ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
    long begin = System.currentTimeMillis();
    //使用接口
    UserService service = (UserService)context.getBean("userService");
    User user = service.getUser();
    long end = System.currentTimeMillis();
    System.out.println("执行时间："+(end-begin));
}
```

1. cglib动态代理测试：

```java
public static void main(String[] args) {
    ApplicationContext context = new  ClassPathXmlApplicationContext("spring.xml");
    long begin = System.currentTimeMillis();
    //不使用接口
    UserServiceImpl service = (UserServiceImpl)context.getBean("userService");
    User user = service.getUser();
    long end = System.currentTimeMillis();
    System.out.println("执行时间："+(end-begin));
}
```

注意：

1. dao层不能再实现接口。
2. service层不能再实现接口。



