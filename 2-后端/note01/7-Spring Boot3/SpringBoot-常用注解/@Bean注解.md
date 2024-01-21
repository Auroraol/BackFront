# 一、介绍

## 1、Spring注解分类

从广义上Spring注解可以分为两类：

+ 用于注册Bean
  + 比如@Component , @Repository , @ Controller , @Service , @Configration这些注解就是用于注册Bean，放进IOC容器中，一来交给spring管理方便解耦，二来还可以进行二次使用

+ 用于使用Bean
  + 比如@Autowired , @Resource注解

## 1.1  Bean

bean 是由 Spring IoC 容器实例化、组装和管理的对象

## 1.2 Spring Bean 的生命周期概念

**对于普通的 Java 对象:**   通过 new 实例化的对象,  该对象不再被使用，由 Java 自动进行垃圾回收。

**对于Spring 中的对象是 bean: **  bean 和普通的 Java 对象没啥大的区别，只不过 Spring 不再自己去 new 对象了，而是由 IoC 容器去帮助我们实例化对象并且管理它，需要哪个对象，去问 IoC 容器要即可。IoC 其实就是解决对象之间的耦合问题，Spring Bean 的生命周期完全由容器控制。

<font color=red>**在 Java 中一个普通的实例化的对象也被称之为 Bean 对象，在框架这一块我们遇到的对象就是以 Bean 对象称呼**</font>

# 二、Spring Bean 的生命周期

Spring 中的 bean 的作用域

| 参数      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| singleton | 单实例的(`单例`)(默认)   ----全局有且仅有一个实例            |
| prototype | 多实例的(`多例`)                ---- 每次获取Bean的时候会有一个新的实例 |
| reqeust   | 同一次请求 ----request：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前`HTTP request`内有效 |
| session   | 同一个会话级别 ---- session：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前`HTTP session`内有效 |

对于普通的 Java 对象来说，它们的生命周期就是：

- 实例化
- 该对象不再被使用时通过垃圾回收机制进行回收

而对于 Spring Bean 的生命周期来说：

- 实例化 Instantiation
- 属性赋值 Populate
- 初始化 Initialization
- 销毁 Destruction

实例化 -> 属性赋值 -> 初始化 -> 销毁

主要逻辑:

```java
protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
    BeanWrapper instanceWrapper = null;
    if (mbd.isSingleton()) {
        instanceWrapper = (BeanWrapper)this.factoryBeanInstanceCache.remove(beanName);
    }

    if (instanceWrapper == null) {
    	// 实例化阶段
        instanceWrapper = this.createBeanInstance(beanName, mbd, args);
    }

    ...

    Object exposedObject = bean;

    try {
    	// 属性赋值阶段
        this.populateBean(beanName, mbd, instanceWrapper);
        // 初始化阶段
        exposedObject = this.initializeBean(beanName, exposedObject, mbd);
    } catch (Throwable var18) {
        ...
    }

    ...
}
```

至于销毁，是在容器关闭时调用的，详见 `ConfigurableApplicationContext#close()`

# 三、@Bean注解总结[核心]

+ Spring的@Bean注解用于告诉方法，产生一个Bean对象，然后这个Bean对象交给Spring管理。 产生这个Bean对象的方法Spring只会调用一次，随后这个Spring将会将这个Bean对象放在自己的IOC容器中。

+ @Bean注解的另一个好处就是能够动态获取一个Bean对象，能够根据环境不同得到不同的Bean对象

+ @Bean就放在方法上，就是让方法去产生一个Bean，然后交给Spring容器，剩下的就别管了

+ @Bean注解能把第三方库中的类实例加入IOC容器中并交给spring管理(@Component , @Repository , @Controller , @Service 这些注解只局限于自己编写的类). <font color=red>第三方的类是别人编写的，可能在类上根本就没有那些注解，而你又不能去修改那些第三方的类，这种情况下，还要实现 IOC 就可以使用 @Bean 将第三方的类注入</font>

**例子**

```java
@Configuration
public class AppConfig {

    // 使用@Bean 注解表明myBean需要交给Spring进行管理
    // 未指定bean 的名称，默认采用的是 "方法名" + "首字母小写"的配置方式
    @Bean
    public MyBean myBean(){
        return new MyBean();
    }
}

public class MyBean {

    public MyBean(){
        System.out.println("MyBean Initializing");
    }
}
```

