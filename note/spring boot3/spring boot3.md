#  项目结构

![image-20230709103935383](spring boot3.assets/image-20230709103935383.png)

**注意:  可能会写一个mapper包  里面写数据库操作的接口, 然后在生成Mapper接口的xml文件写sql语句**和dao包一样

![image-20230926223348288](spring boot3.assets/image-20230926223348288.png)

Spring Boot中html、js、Css、图片等应该放在什么位置:

+ <img src="spring boot3.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5pmT6aOO5q6L5pyIKO-_o861KCPvv6Mpfg==,size_12,color_FFFFFF,t_70,g_se,x_16.png" alt="img" style="zoom:67%;" />

+ `注意`：这里是不要在路径中写出 [static](https://so.csdn.net/so/search?q=static&spm=1001.2101.3001.7020) 文件夹的

+ ![img](spring boot3.assets/98bc1ffb552747aeb7041ada00347ddb.png)

  ```
  由于我在template 文件夹下放置了 html 文件，所以这里的路径就是
  先用 ..  返回上一级--> 再直接Css 文件夹---> 具体的.css文件
  ```

  

![img](spring boot3.assets/1a794e78c2442b821d58e8cd0222a015.png)

# SpringBoot3-快速入门

[(79条消息) java: 错误: 无效的源发行版:17_无效的源发行版17_G666666_的博客-CSDN博客](https://blog.csdn.net/G666666_/article/details/128127980)

## 1. 环境要求

| 环境&工具          | 版本（or later） |
| ------------------ | :--------------: |
| SpringBoot         |      3.0.5+      |
| IDEA               |    2021.2.1+     |
| Java               |       17+        |
| Maven              |       3.5+       |
| Tomcat             |      10.0+       |
| Servlet            |       5.0+       |
| GraalVM Community  |      22.3+       |
| Native Build Tools |     0.9.19+      |

## 2. SpringBoot是什么

SpringBoot 帮我们简单、快速地创建一个独立的、生产级别的 **Spring 应用（说明：SpringBoot底层是Spring）**

大多数 SpringBoot 应用只需要编写少量配置即可快速整合 Spring 平台以及第三方技术

**特性：**

- 快速创建独立 Spring 应用

- - SSM：导包、写配置、启动运行

- 直接嵌入Tomcat、Jetty or Undertow（无需部署 war 包）【Servlet容器】

- - linux  java tomcat mysql： war 放到 tomcat 的 webapps下
  - jar： java环境；  java -jar

- **重点**：提供可选的starter，简化应用**整合**

- - **场景启动器**（starter）：web、json、邮件、oss（对象存储）、异步、定时任务、缓存...
  - 导包一堆，控制好版本。
  - 为每一种场景准备了一个依赖； **web-starter。mybatis-starter**

- **重点：**按需自动配置 Spring 以及 第三方库

- - 如果这些场景我要使用（生效）。这个场景的所有配置都会自动配置好。
  - **约定大于配置**：每个场景都有很多默认配置。
  - 自定义：配置文件中修改几项就可以

- 提供生产级特性：如 监控指标、健康检查、外部化配置等

- - 监控指标、健康检查（k8s）、外部化配置

- 无代码生成、**无xml**

<font color= red> 总结：简化开发，简化配置，简化整合，简化部署，简化监控，简化运维。</font>

##  3. 快速体验

场景：浏览器发送**/hello**请求，返回"**Hello,Spring Boot 3!**"

### 1. 创建项目

**Spring Initializr 自动创建向导**

一键自动创建好整个项目结构

![image-20230706231923634](spring boot3.assets/image-20230706231923634.png)



![image-20230706231644386](spring boot3.assets/image-20230706231644386.png)

**maven 项目(pom.xml文件)**

```xml
<!--    所有springboot项目都必须继承自 spring-boot-starter-parent -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.0.5</version>
    </parent>
```

### 2. 导入场景

maven 项目(pom.xml文件)

==场景启动器==

```xml
    <dependencies>
<!--        web开发的场景启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

导入相关的场景，拥有相关的功能。

默认支持的所有场景：https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters

- 官方提供的场景：命名为：`spring-boot-starter-*`
- 第三方提供场景：命名为：`*-spring-boot-starter`

场景一导入，万物皆就绪

**补充(了解):**

#### 1. 依赖管理机制

思考：

1、为什么导入`starter-web`所有相关依赖都导入进来？

- 开发什么场景，导入什么**场景启动器。**
- **maven依赖传递原则。A-B-C： A就拥有B和C**
- 导入 场景启动器。 场景启动器 自动把这个场景的所有核心依赖全部导入进来

2、为什么版本号都不用写？

- 每个boot项目都有一个父项目`spring-boot-starter-parent`
- parent的父项目是`spring-boot-dependencies`
- 父项目 **版本仲裁中心**，把所有常见的jar的依赖版本都声明好了。
- 比如：`mysql-connector-j`

3、自定义版本号

- 利用maven的就近原则

- - 直接在当前项目`properties`标签中声明父项目用的版本属性的key
  - 直接在**导入依赖的时候声明版本**

4、**第三方的jar包**

- boot父项目没有管理的需要自行声明好,需要声明版本号

- ```xml
  <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
  <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.2.16</version>
  </dependency>
  ```

### 3. 主程序

```java
@SpringBootApplication //这是一个SpringBoot应用
public class Boot302DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
```

### 4. 业务

```java
@RestController
public class HelloController {

    @GetMapping("/hello")  //浏览器发送hello请求
    public String hello(){

        return "Hello,Spring Boot 3!";
    }

}
```

### 5. 测试

默认启动访问： localhost:8080

### 6. 打包

```xml
<!--    SpringBoot应用打包插件-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

`mvn clean package`   把项目打成可执行的jar包(放在target目录中)

`java -jar demo.jar` 启动项目

修改配置（外部放一个application.properties文件）、监控、健康检查

## 4. 自动配置机制

**默认的包扫描规则**

- `@SpringBootApplication` 标注的类就是主程序类

- **SpringBoot只会扫描主程序所在的包及其下面的子包，自动的component-scan功能**

- 自定义扫描路径

  + 方法1:   @SpringBootApplication(scanBasePackages = "com.atguigu")

  - 方法2:    @ComponentScan("com.atguigu") 直接指定扫描的路径

**按需加载自动配置**

- - 导入场景`spring-boot-starter-web`
  - 场景启动器除了会导入相关功能依赖，导入一个`spring-boot-starter`，是所有`starter`的`starter`，基础核心starter
  - `spring-boot-starter`导入了一个包 `spring-boot-autoconfigure`。包里面都是各种场景的`AutoConfiguration`**自动配置类**
  - 虽然全场景的自动配置都在 `spring-boot-autoconfigure`这个包，但是不是全都开启的。

- - - 导入哪个场景就开启哪个自动配置

总结： 导入场景启动器、触发 `spring-boot-autoconfigure`这个包的自动配置生效、容器中就会具有相关场景的功能

## 5. 显示网址

```java
@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableApplicationContext application = SpringApplication.run(DemoApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		log.info("\n----------------------------------------------------------\n\t" +
				"Application  is running! Access URLs:\n\t" +
				"Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
				"External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
				"----------------------------------------------------------");
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		log.info("当前项目进程号：" + jvmName.split("@")[0]);
	}
}

```



# 注解

| 注解                   | 备注     |
| ---------------------- | -------- |
| @SpringBootApplication | 入口函数 |

| 注解            | 备注                                                         |
| --------------- | ------------------------------------------------------------ |
| @Controller     | 1.Controller +RequestMapping+GetMapping、<br/>2.引人freemarker<<br/>#   配置thymeleaf场景<br/>#   spring.thymeleaf<br/>spring.thymeleaf.prefix=classpath:templates/  <br/>spring.thymeleaf.suffix=.html<br/>#开发期间关闭，上线以后开启<br/>spring.thymeleaf.cache=falsebr/><br/>4、在application.properties中配置freemarker相关的信息<br/>5、返回的是页面<br/>6.  ==接收前端数据== |
| @RestController | 1. 返回的是输出结果,返回对象，如json ，是==提供前端获取数据==、提交数据<br/>2. 结合 @RequestMapping、@GetMapping、@PostMapping...... |
| @RequestBody    | 前端发送json封装的对象给后端，后端使用java对象来接收(传递对象) |
| @RequestMapping | url前缀                                                      |

@Controller            返回: 视图页面 

@Controller + @ResponseBody   返回:  json/xml/类对象数据

@RestController    返回:  json/xml/类数据

```java
@Controller
public class HelloController {
    
    @GetMapping(value="/hello")
    @ResponseBody
    public String say(){//返回json 数据  
        return "gril";
    }
    
    @GetMapping(value="/hello1")
    public String say1(){//返回视图
        return "sys/index1";
    }
}
```

```java
@Controller
@ResponseBody
public class HelloController {
   // 都返回json数据到页面
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){
        return "sys/index1"; //返回视图
    }
}
```

上面的两种方法等价于下面的方式

```java
@Controller
@RestController 
public class HelloController {
   // 都返回json数据到页面
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){
        return "sys/index1"; //返回视图
    }
}
```

RequestMapping

```java
@RestController  
@RequestMapping("/action")
public class UserActionController {

	@GetMapping("/show")  // url: xxxx/action/show
	public User show(){
		return userService.show();
	}

}
```



| 注解           | 备注                                                         |
| -------------- | ------------------------------------------------------------ |
| @GetMapping    | 前端显示数据,  请求从服务器获取特定资源                      |
| @PostMapping   | 在服务器上创建一个新的资源                                   |
| @PutMapping    | 后端接受数据,  更新服务器上的资源                            |
| @DeleteMapping | 从服务器删除特定的资源                                       |
| @PatchMapping  | 更新服务器上的资源,  一般实际项目中,PUT 不够用了之后才用 PATCH 请求去更新数据。 |
| @CrossOrigin   | 跨域                                                         |



| 注解          | 备注                                                         |
| ------------- | ------------------------------------------------------------ |
| @RequestParam | 动态参数(RequestParam  带?)                                  |
| @PathVariable | 动态参数 (PathVariable 不带?)                                |
| @ResponseBody | 一般在@Controller中使用: @ResponseBody+@Controller = @RestController,   后端发送java对象 |

| 注解                 | 备注                                                         |
| -------------------- | ------------------------------------------------------------ |
| @Service             | bean注册, ==一般加在service目录里面的类上==                  |
| @Component           | bean注册, 和 Service 没有啥区别,  Component是比较通用的东西  |
| @Autowired           | //@Resource =@Autowired+@Qualifie<br> //自动加载类, 理解就是不用new<br/>//如果接口实现只有一个，那么用@Autowired就可以了，也不需要指定名字.<br/>//如果接口有多个实现,那么用@Resource，并指定name<br/>//或者使用@Autowired+@Qualifier+Qualifier的value值. |
| @Configuration+@Bean | bean注册(人为) —> 用于配置类                                 |
| @Values              | 从配置文件中取参数                                           |

其他注解:  controller --->servicer-->DAo(Mapper)【底层框架】SSM框架----->mapper. . .. . @select、@insertJPA ---

==详情见demo代码==

**@Controller:**

```java
@Slf4j
@Controller
public class ParamController {


	/**
	 * 动态参数(RequestParam  带?) + 带数据返回页面(方法2)
	 */
	@GetMapping("/param")
	public String hello(@RequestParam(value = "age",   defaultValue = "0") int age,         //required 表示可以不传值
						@RequestParam(value = "name", required = false, defaultValue = "") String name,  //访问: http://localhost:8090/data2?age=参数?name=参数
						Model model){

		//把需要给页面共享的数据放到model中
		String text = "<span style='color:red'>"+name+"</span>";
		model.addAttribute("msg",text);
		model.addAttribute("age",18);

		return "view";
	}

	/**
	 * 动态参数 (PathVariable 不带?)   ps:看路径匹配章节
	 */
	@GetMapping("/a*/b?/{p1:[a-f]+}/**")
	public String hello(HttpServletRequest request, @PathVariable("p1") String path
			, Model model) {

		model.addAttribute("msg",path);
		return "view";
	}

	/**
	 * 在@Controller中希望返回json格式
	 * @return
	 */
	@GetMapping("/ReBody")
	@ResponseBody
	public User getUser(){
		User user = new User(1000, "张四", "男" , 12);
		//log. info("使用@GetMapping + @ResponseBody");
		return user;
	}
    
     /**
     * 来到首页
     * @return
     */
    @GetMapping("/")
    public String index(){

        return "index";
    }
}

```

**@RestController:**

```java
@Slf4j
@RestController   // 返回对象
@RequestMapping("/action")
public class UserActionController {


	/*
	* @Autowired   //自动加载类
	* private UserService userService;  //报错UserService接口里有多个实现类
	*/
	@Autowired
	@Qualifier("adminServiceImpl")    //首字母小写
	private UserService userService;

	/* @Resource   和 Autowired 没有啥区别,  @Resource可以指定UserServiceImpl类
	* @Resource(name = "AdminServiceImpl")
	* private UserService userService;  // ok
	*/
	@Resource(name = "UserServiceImpl")
	private UserService userService1;  // ok


	@GetMapping("/show")
	public User show(){
		return userService.show();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable("id") int id){
		return userService.get(id);
	}


	/*
	 * 新增数据
	 * 假设前端发送数据  前端发过来的数据用RequestBody修饰
	 */
	@PostMapping
	public User addUser(@RequestBody User user){
		return user;
	}


	@Value("${robot.name}")
	private String naem;    // name ={robot.name}



}

```

**@Configuration+@Bean  :**  

```java
package com.example.demo.confiq;

import com.example.demo.service.UserService;
import com.example.demo.service.impl.AdminServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

//@EnableWebMvc //禁用boot的默认配置
@Configuration //这是一个配置类,给容器中放一个 WebMvcConfigurer 组件，就能自定义底层
public class MyConfig  /*implements WebMvcConfigurer*/ {
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override //配置静态资源
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/static/**")
						.addResourceLocations("classpath:/a/", "classpath:/b/")
						.setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
			}

			@Override //配置拦截器
			public void addInterceptors(InterceptorRegistry registry) {

			}
		};
	}


	/*
	* 人为加注解
	* */
	@Bean("adminServiceImpl")
	public UserService AdminServiceImpl(){
		return  new AdminServiceImpl();
	}

	@Bean
	public UserService UserServiceImpl (){
		return  new UserServiceImpl();
	}
}
```



## 01-组件注册

### @Configuration 和  @Bean

@Configuration：声明这个类是一个配置类，加入ioc容器中。

@Bean：注册组件。默认的beanId是函数名，可以用value属性进行指定。

```java
@Configuration
public class MyConfig {

    @Bean
    public Person person() {
        return new Person("ZhangSan", 10);
    }
}
```

加载配置类：

```java
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);

Person person = applicationContext.getBean(Person.class);
System.out.println(person);

String[] names = applicationContext.getBeanNamesForType(Person.class);
Arrays.asList(names).forEach(System.out::println);
```

### @ComponentScan 和 @ComponentScans

包扫描，扫描包下面的`@Controller`、`@Service`等组件类

[@ComponentScan ]() 

-  value 

要扫描的包，等同于`basePackages`

-  basePackages 

同`value`

-  includeFilters 

指定只包含的组件

属性值是一个`@Filter`数组

-  excludeFilters 

指定排除的组件。

属性值是一个`@Filter`数组

示例:

扫描`com.study`包下的组件，排除`@Controller`、`@Service`注解的类

```java
@Configuration
@ComponentScan(value = "com.study", 
               excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
public class MyConfig {

    @Bean
    public Person person() {
        return new Person("ZhangSan", 10);
    }
}
```

示例2：

扫描`com.study`包下的组件, 只包含`@Controller`、`@Service`注解的类。（需要先禁用默认的过滤规则`useDefaultFilters=false`，因为默认的过滤规则是全部扫描）

```java
@Configuration
@ComponentScan(value = "com.study",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
public class MyConfig {

    @Bean
    public Person person() {
        return new Person("ZhangSan", 10);
    }
}
```

### @ComponentScan.Filter 

==配置包扫描的过滤规则==

```
@Filter
```

-  type 

要排除的方式:`FilterType`。

- Annotation：按照注解

- Assignable_type：按照给定的类

- AspectJ：使用AspectJ表达式

- Regex：使用正则表达式

- Custom：使用自定义规则，该规则必须是`TypeFilter`的实现类

-  value 

要排除的类型，同`classes`

-  classes 

同`value`

-  pattern 

正则表达式



自定义`TypeFilter`规则：

+ MetadataReader：读取到的当前正在扫描的类的信息
+ MetadataReaderFactory：可以获取到其他任何类的信息

```java
public class MyTypeFilter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前正在扫描类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前的资源信息
        metadataReader.getResource();

        String className = classMetadata.getClassName();

        return "com.study.book.dao.BookDao".equals(className);
    }
}
```

配置：

```java
@Configuration
@ComponentScan(value = "com.study",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        })
public class MyConfig {
    // ...
}
```

### @Scope

调整作用域。

+  value 

取值：

- ConfigurableBeanFactory.SCOPE_SINGLETON ： "singleton"，单实例（默认值），ioc容器启动就会调用方法创建对象到ioc容器中。

- ConfigurableBeanFactory.SCOPE_PROTOTYPE ： "prototype"，多实例，ioc容器启动时并不会调用方法创建对象，每次获取时才会调用方法创建对象。

- WebApplicationContext.SCOPE_REQUEST："request"，同一个请求创建一个实例

- WebApplicationContext.SCOPE_SESSION："session"，同一个session创建一个实例



-  scopeName 

同'value'

 



例如：

```java
@Bean
@Scope
public Person person() {
    return new Person("ZhangSan", 10);
}
```

### [@Lazy ]()

针对单实例的Bean。

单实例Bean默认在容器启动时创建对象。懒加载在第一次使用Bean时才创建对象并初始化。

例如：

```java
@Bean
@Lazy
public Person person() {
    System.out.println("方法调用了。");
    return new Person("ZhangSan", 10);
}
```



[01-组件注册 (yuque.com)](https://www.yuque.com/tmfl/spring/raspye#91320b51)

## 1. 常用注解

1、@SpringBootApplication

这是 Spring Boot 最最最核心的注解，用在 Spring Boot 主类上，标识这是一个 Spring Boot 应用，用来开启 Spring Boot 的各项能力。

其实这个注解就是 @SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan 这三个注解的组合，也可以用这三个注解来代替 @SpringBootApplication 注解。

2、@EnableAutoConfiguration

允许 Spring Boot 自动配置注解，开启这个注解之后，Spring Boot 就能根据当前类路径下的包或者类来配置 Spring Bean。

如：当前类路径下有 Mybatis 这个 JAR 包，MybatisAutoConfiguration 注解就能根据相关参数来配置 Mybatis 的各个 Spring Bean。

3、@[Configuration](https://so.csdn.net/so/search?q=Configuration&spm=1001.2101.3001.7020)

这是 Spring 3.0 添加的一个注解，用来代替 applicationContext.xml 配置文件，所有这个配置文件里面能做到的事情都可以通过这个注解所在类来进行注册。

4、@SpringBootConfiguration

这个注解就是 @Configuration 注解的变体，只是用来修饰是 Spring Boot 配置而已，或者可利于 Spring Boot 后续的扩展。

5、@ComponentScan

这是 Spring 3.1 添加的一个注解，用来代替配置文件中的 [component](https://so.csdn.net/so/search?q=component&spm=1001.2101.3001.7020)-scan 配置，开启组件扫描，即自动扫描包路径下的 @Component 注解进行注册 bean 实例到 context 中。

6、@Conditional

这是 Spring 4.0 添加的新注解，用来标识一个 Spring Bean 或者 Configuration 配置文件，当满足指定的条件才开启配置。

7、@ConditionalOnBean

组合 @Conditional 注解，当容器中有指定的 Bean 才开启配置。

8、@ConditionalOnMissingBean

组合 @Conditional 注解，和 @ConditionalOnBean 注解相反，当容器中没有指定的 Bean 才开启配置。

9、@ConditionalOnClass

组合 @Conditional 注解，当容器中有指定的 Class 才开启配置。

10、@ConditionalOnMissingClass

组合 @Conditional 注解，和 @ConditionalOnMissingClass 注解相反，当容器中没有指定的 Class 才开启配置。

11、@ConditionalOnWebApplication

组合 @Conditional 注解，当前项目类型是 WEB 项目才开启配置。

12、@ConditionalOnNotWebApplication

组合 @Conditional 注解，和 @ConditionalOnWebApplication 注解相反，当前项目类型不是 WEB 项目才开启配置。

13、@ConditionalOnProperty

组合 @Conditional 注解，当指定的属性有指定的值时才开启配置。

14、@ConditionalOnExpression

组合 @Conditional 注解，当 SpEL 表达式为 true 时才开启配置。

15、@ConditionalOnJava

组合 @Conditional 注解，当运行的 Java JVM 在指定的版本范围时才开启配置。

16、@ConditionalOnResource

组合 @Conditional 注解，当类路径下有指定的资源才开启配置。

17、@ConditionalOnJndi

组合 @Conditional 注解，当指定的 JNDI 存在时才开启配置。

18、@ConditionalOnCloudPlatform

组合 @Conditional 注解，当指定的云平台激活时才开启配置。

19、@ConditionalOnSingleCandidate

组合 @Conditional 注解，当指定的 class 在容器中只有一个 Bean，或者同时有多个但为首选时才开启配置。

20、@ConfigurationProperties

用来加载额外的配置（如 .properties 文件），可用在 @Configuration 注解类，或者 @Bean 注解方法上面。

关于这个注解的用法可以参考《
Spring Boot读取配置的几种方式》这篇文章。

21、@EnableConfigurationProperties

一般要配合 @ConfigurationProperties 注解使用，用来开启对 @ConfigurationProperties 注解配置 Bean 的支持。

22、@AutoConfigureAfter

用在自动配置类上面，表示该自动配置类需要在另外指定的自动配置类配置完之后。

如 Mybatis 的自动配置类，需要在数据源自动配置类之后。

@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisAutoConfiguration {
23、@AutoConfigureBefore

这个和 @AutoConfigureAfter 注解使用相反，表示该自动配置类需要在另外指定的自动配置类配置之前。

24、@Import

这是 Spring 3.0 添加的新注解，用来导入一个或者多个 @Configuration 注解修饰的类，这在 Spring Boot 里面应用很多。

25、@ImportResource

这是 Spring 3.0 添加的新注解，用来导入一个或者多个 Spring 配置文件，这对 Spring Boot 兼容老项目非常有用，因为有些配置无法通过 Java Config 的形式来配置就只能用这个注解来导入。

SpringBoot摒弃XML配置方式，改为**全注解驱动**

### 1. 组件注册

demo3

```
@Configuration**、**@SpringBootConfiguration**

**@Bean**、**@Scope**

**@Controller、 @Service、@Repository、@Component**

**@Import**

@ComponentScan
```

**步骤：**

**1、@Configuration 编写一个配置类**

**2、在配置类中，自定义方法给容器中注册组件。配合@Bean**

**3、或使用@Import 导入第三方的组件**

```
package com.atguigu.boot.config;

//import com.alibaba.druid.FastsqlException;
import com.alibaba.druid.FastsqlException;
import com.atguigu.boot.bean.Pig;
import com.atguigu.boot.bean.Sheep;
import com.atguigu.boot.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

//@Import(FastsqlException.class) //给容器中放指定类型的组件，组件的名字默认是全类名

/**
 * 1、开启Sheep组件的属性绑定
 * 2、默认会把这个组件自己放到容器中
 */
@EnableConfigurationProperties(Sheep.class) //导入第三方写好的组件进行属性绑定
//SpringBoot默认只扫描自己主程序所在的包。如果导入第三方包，即使组件上标注了 @Component、@ConfigurationProperties 注解，也没用。因为组件都扫描不进来
@SpringBootConfiguration //这是一个配置类，替代以前的配置文件。配置类本身也是容器中的组件
//@Configuration
public class AppConfig {



    @Bean
    @ConfigurationProperties(prefix = "pig")
    public Pig pig(){
        return new Pig(); //我们自己new新pig
    }
    /**
     * 1、组件默认是单实例的
     * @return
     */
    @Scope("prototype")
    @Bean("userHaha") //替代以前的Bean标签。 组件在容器中的名字默认是方法名，可以直接修改注解的值
    public User user01(){
        var user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }

//    @Bean
//    public FastsqlException fastsqlException(){
//
//        return new FastsqlException();
//    }

}
```



### 2. 条件注解

如果注解指定的**条件成立**，则触发指定行为

***@ConditionalOnXxx***

**@ConditionalOnClass：如果类路径中存在这个类，则触发指定行为**

**@ConditionalOnMissingClass：如果类路径中不存在这个类，则触发指定行为**

**@ConditionalOnBean：如果容器中存在这个Bean（组件），则触发指定行为**

**@ConditionalOnMissingBean：如果容器中不存在这个Bean（组件），则触发指定行为**

场景：

- 如果存在`FastsqlException`这个类，给容器中放一个`Cat`组件，名cat01，
- 否则，就给容器中放一个`Dog`组件，名dog01
- 如果系统中有`dog01`这个组件，就给容器中放一个 User组件，名zhangsan 
- 否则，就放一个User，名叫lisi

```

```



**@ConditionalOnBean（value=组件类型，name=组件名字）：判断容器中是否有这个类型的组件，并且名字是指定的值**

@ConditionalOnRepositoryType (org.springframework.boot.autoconfigure.data)
@ConditionalOnDefaultWebSecurity (org.springframework.boot.autoconfigure.security)
@ConditionalOnSingleCandidate (org.springframework.boot.autoconfigure.condition)
@ConditionalOnWebApplication (org.springframework.boot.autoconfigure.condition)
@ConditionalOnWarDeployment (org.springframework.boot.autoconfigure.condition)
@ConditionalOnJndi (org.springframework.boot.autoconfigure.condition)
@ConditionalOnResource (org.springframework.boot.autoconfigure.condition)
@ConditionalOnExpression (org.springframework.boot.autoconfigure.condition)
**@ConditionalOnClass** (org.springframework.boot.autoconfigure.condition)
@ConditionalOnEnabledResourceChain (org.springframework.boot.autoconfigure.web)
**@ConditionalOnMissingClass** (org.springframework.boot.autoconfigure.condition)
@ConditionalOnNotWebApplication (org.springframework.boot.autoconfigure.condition)
@ConditionalOnProperty (org.springframework.boot.autoconfigure.condition)
@ConditionalOnCloudPlatform (org.springframework.boot.autoconfigure.condition)
**@ConditionalOnBean** (org.springframework.boot.autoconfigure.condition)
**@ConditionalOnMissingBean** (org.springframework.boot.autoconfigure.condition)
@ConditionalOnMissingFilterBean (org.springframework.boot.autoconfigure.web.servlet)
@Profile (org.springframework.context.annotation)
@ConditionalOnInitializedRestarter (org.springframework.boot.devtools.restart)
@ConditionalOnGraphQlSchema (org.springframework.boot.autoconfigure.graphql)
@ConditionalOnJava (org.springframework.boot.autoconfigure.condition)

### 3. 属性绑定

**@ConfigurationProperties： 声明组件的属性和配置文件哪些前缀开始项进行绑定**

**@EnableConfigurationProperties：快速注册注解：**

- **场景：**SpringBoot默认只扫描自己主程序所在的包。如果**导入第三方包**，即使组件上标注了 @Component、@ConfigurationProperties 注解，也没用。因为组件都扫描不进来，此时使用这个注解就可以快速进行属性绑定并把组件注册进容器

将容器中任意**组件（Bean）的属性值**和**配置文件**的配置项的值**进行绑定**

- **1、给容器中注册组件（@Component、或者@Bean）**
- **2、使用@ConfigurationProperties 声明组件和配置文件的哪些配置项进行绑定**



![image-20230707001510474](spring boot3.assets/image-20230707001510474.png)



更多注解参照：[Spring注解驱动开发](https://www.bilibili.com/video/BV1gW411W7wy)【1-26集】

# YAML配置文件

**痛点**：SpringBoot 集中化管理配置，`application.properties`

**问题**：配置多以后难阅读和修改，**层级结构辨识度不高**



YAML 是 "YAML Ain't a Markup Language"（YAML 不是一种标记语言）。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（是另一种标记语言）。

- 设计目标，就是**方便人类读写**
- **层次分明**，更适合做配置文件
- 使用`.yaml`或 `.yml`作为文件后缀

## 1. 基本语法

- **大小写敏感**
- 使用**缩进表示层级关系，k: v，使用空格分割k,v(相当于等于)**
- 缩进时不允许使用Tab键，只允许**使用空格**。换行
- 缩进的空格数目不重要，只要**相同层级**的元素**左侧对齐**即可
- **# 表示注释**，从这个字符一直到行尾，都会被解析器忽略。



支持的写法：

- **对象**：**键值对**的集合，如：映射（map）/ 哈希（hash） / 字典（dictionary）
- **数组**：一组按次序排列的值，如：序列（sequence） / 列表（list）
- **纯量**：单个的、不可再分的值，如：字符串、数字、bool、日期

### 例子1

配置类

~~~java
@Component
@ConfigurationProperties(prefix = "person") //和配置文件person前缀的所有配置进行绑定
@Data //自动生成JavaBean属性的getter/setter
public class Person {
    private String name;
    private Integer age;
    private Date birthDay;
    private Boolean like;
    private Child child; //嵌套对象
    private List<Dog> dogs; //数组（里面是对象）
    private Map<String,Cat> cats; //表示Map
}

@Data
public class Dog {
    private String name;
    private Integer age;
}

@Data
public class Child {
    private String name;
    private Integer age;
    private Date birthDay;
    private List<String> text; //数组
}

@Data
public class Cat {
    private String name;
    private Integer age;
}
~~~

**properties表示法**

```Properties
person.name=张三
person.age=18
person.birthDay=2010/10/12 12:12:12
person.like=true
person.child.name=李四
person.child.age=12
person.child.birthDay=2018/10/12
person.child.text[0]=abc
person.child.text[1]=def
person.dogs[0].name=小黑
person.dogs[0].age=3
person.dogs[1].name=小白
person.dogs[1].age=2
person.cats.c1.name=小蓝
person.cats.c1.age=3
person.cats.c2.name=小灰
person.cats.c2.age=2
```

**yaml表示法**

application.yml

```yaml
person:
  name: 张三
  age: 18
  birth-day: 2010/10/10 12:12:12
  like: true
  child:
    name: 李四
    age: 20
    birthDay: 2018/10/10
    text: ["abc","def"]
  dogs:
    - name: 小黑
      age: 3
    - name: 小白
      age: 2
  cats:
    c1:
      name: 小蓝
      age: 3
    c2: {name: 小绿,age: 2} #对象也可用{}表示
```

### 例子2(配置类)

```yml
# JWT配置
jwt:
  secret: "!Q@W#E$R^Y&U"
  issuer: "HZSTYGZPT"
  expire-date: 3600000

```

配置类

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private String issuer;
    private Long expireDate;
}
```

使用

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public void someMethod() {
        String secret = jwtConfig.getSecret();
        String issuer = jwtConfig.getIssuer();
        Long expireDate = jwtConfig.getExpireDate();

        // 使用配置参数进行JWT操作
    }
}

```

### 例子3(注解)

```yml
# JWT配置
jwt:
  secret: "!Q@W#E$R^Y&U"
  issuer: "HZSTYGZPT"
  expire-date: 3600000
```

使用

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expire-date}")
    private Long expireDate;

    public void someMethod() {
        // 使用配置参数进行JWT操作
        System.out.println("Secret: " + secret);
        System.out.println("Issuer: " + issuer);
        System.out.println("Expire Date: " + expireDate);
    }
}
```



## 3. 细节

- birthDay 推荐写为 birth-day
- **文本**：

- - **单引号**不会转义【\n 则为普通字符串显示】
  - **双引号**会转义【\n会显示为**换行符**】

- **大文本**

- - `|`开头，大文本写在下层，**保留文本格式**，**换行符正确显示**
  - `>`开头，大文本写在下层，折叠换行符,变成空格

- **多文档合并**

- - 使用`---`可以==把多个yaml文档合并在一个文档中==，每个文档区依然认为内容独立

## 4. 小技巧：lombok

简化JavaBean 开发。自动生成构造器、getter/setter、自动生成Builder模式等

```java
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>compile</scope>
</dependency>
```

使用

```java
@Data //自动生成JavaBean属性的getter/setter
@NoArgsConstructor //自动生成无参构造器
@AllArgsConstructor //自动生成全参构造器
```

# ==SpringBoot常用注解==

## 1、 @SpringBootApplication

一般不会主动去使用它。这个注解是 Spring Boot 项目的基石，创建 SpringBoot 项目之后会默认在主类加上。

```java
@SpringBootApplication
public class SpringSecurityJwtGuideApplication {
      public static void main(java.lang.String[] args) {
        SpringApplication.run(SpringSecurityJwtGuideApplication.class, args);
    }
}
```

可以把 @SpringBootApplication看作是 @Configuration、@EnableAutoConfiguration、@ComponentScan 注解的集合。根据 SpringBoot 官网，这三个注解的作用分别是：

+ @EnableAutoConfiguration：启用 SpringBoot 的自动配置机制；

+ @ComponentScan： 扫描被@Component、@Service、@Controller注解修饰的 bean，注解默认会扫描该类所在的包下所有的类；

+ @Configuration：允许在 Spring 上下文中注册额外的 bean 或导入其他配置类

| 注解                   | 备注     |
| ---------------------- | -------- |
| @SpringBootApplication | 入口函数 |

| 注解            | 备注                                                         |
| --------------- | ------------------------------------------------------------ |
| @Controller     | 1.Controller +RequestMapping+GetMapping、<br/>2.引人freemarker<<br/>#   配置thymeleaf场景<br/>#   spring.thymeleaf<br/>spring.thymeleaf.prefix=classpath:templates/  <br/>spring.thymeleaf.suffix=.html<br/>#开发期间关闭，上线以后开启<br/>spring.thymeleaf.cache=falsebr/><br/>4、在application.properties中配置freemarker相关的信息<br/>5、返回的是页面<br/>6.  ==接收前端数据== |
| @RestController | 1. 返回的是输出结果,返回对象，如json ，是==提供前端获取数据==、提交数据<br/>2. 结合 @RequestMapping、@GetMapping、@PostMapping...... |
| @RequestBody    | 前端发送json封装的对象给后端，后端使用java对象来接收(传递对象) |
| @RequestMapping | url前缀                                                      |

@Controller            返回: 视图页面 

@Controller + @ResponseBody   返回:  json/xml/类对象数据

@RestController    返回:  json/xml/类数据

```java
@Controller
public class HelloController {
    
    @GetMapping(value="/hello")
    @ResponseBody
    public String say(){//返回json 数据  
        return "gril";
    }
    
    @GetMapping(value="/hello1")
    public String say1(){//返回视图
        return "sys/index1";
    }
}
```

```java
@Controller
@ResponseBody
public class HelloController {
   // 都返回json数据到页面
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){
        return "sys/index1"; //返回视图
    }
}
```

上面的两种方法等价于下面的方式

```java
@Controller
@RestController 
public class HelloController {
   // 都返回json数据到页面
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){
        return "sys/index1"; //返回视图
    }
}
```

RequestMapping

```java
@RestController  
@RequestMapping("/action")
public class UserActionController {

	@GetMapping("/show")  // url: xxxx/action/show
	public User show(){
		return userService.show();
	}

}
```

| 注解           | 备注                                                         |
| -------------- | ------------------------------------------------------------ |
| @GetMapping    | 前端显示数据,  请求从服务器获取特定资源                      |
| @PostMapping   | 在服务器上创建一个新的资源                                   |
| @PutMapping    | 后端接受数据,  更新服务器上的资源                            |
| @DeleteMapping | 从服务器删除特定的资源                                       |
| @PatchMapping  | 更新服务器上的资源,  一般实际项目中,PUT 不够用了之后才用 PATCH 请求去更新数据。 |
| @CrossOrigin   | 跨域                                                         |

| 注解          | 备注                                                         |
| ------------- | ------------------------------------------------------------ |
| @RequestParam | 动态参数(RequestParam  带?)                                  |
| @PathVariable | 动态参数 (PathVariable 不带?)                                |
| @ResponseBody | 一般在@Controller中使用: @ResponseBody+@Controller = @RestController,   后端发送java对象 |

| 注解                 | 备注                                                         |
| -------------------- | ------------------------------------------------------------ |
| @Service             | bean注册, ==一般加在service目录里面的类上==                  |
| @Controller          | 控制层组件, controller目录里面的类上                         |
| @Repository          | 数据层组件, dao目录里面的类上                                |
| @Component           | bean注册, 和 Service 没有啥区别,  Component是比较通用的东西  |
| @Autowired           | //@Resource =@Autowired+@Qualifie<br>//自动加载类, 理解就是不用new<br/>//如果接口实现只有一个，那么用@Autowired就可以了，也不需要指定名字.<br/>//如果接口有多个实现,那么用@Resource，并指定name<br/>//或者使用@Autowired+@Qualifier+Qualifier的value值. |
| @Configuration+@Bean | bean注册(人为) —> 用于配置类                                 |
| @Values              | 从配置文件中取参数                                           |

**@RestController和@Controller的区别**

- @RestController加在类上面的注解，使得类里面的每个方法都将json/xml返回数据加返回到前台页面中。

```java
@RestController
public class HelloController {
 
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
}
```

![image-20230922223722214](spring boot3.assets/image-20230922223722214.png)

- @Controller加在类上面的注解，使得类里面的每个方法都返回一个视图页面。

```java
@Controller
public class HelloController {
 
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
}
```

![在这里插入图片描述](spring boot3.assets/20200828101924480.png#pic_center)

- @Controller和@ResponseBody（加在方法/类上面）一起使用，和@RestController的作用相同。

```java
@Controller
public class HelloController {
    
    @GetMapping(value="/hello")
    @ResponseBody
    public String say(){//返回json 数据  
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){//返回视图
        return "sys/index1";
    }
}

```

```java
@Controller
@ResponseBody
public class HelloController {
   // 都返回json数据到页面
    @GetMapping(value="/hello")
    public String say(){
        return "gril";
    }
    @GetMapping(value="/hello1")
    public String say1(){
        return "sys/index1";
    }
}
```

## 2、Spring Bean 相关

| 注解                 | 备注                                                         |
| -------------------- | ------------------------------------------------------------ |
| @Service             | bean注册, ==一般加在service目录里面的类上==                  |
| @Component           | bean注册, 和 Service 没有啥区别,  Component是比较通用的东西  |
| @Autowired           | //@Resource =@Autowired+@Qualifie<br> //自动加载类, 理解就是不用new<br/>//如果接口实现只有一个，那么用@Autowired就可以了，也不需要指定名字.<br/>//如果接口有多个实现,那么用@Resource，并指定name<br/>//或者使用@Autowired+@Qualifier+Qualifier的value值. |
| @Configuration+@Bean | bean注册(人为) —> 用于配置类                                 |
| @Values              | 从配置文件中取参数                                           |
| @Repository          | 用于将 DAO 层 (spring应用)                                   |

### 2.1  @Autowired

自动导入对象到类中（自动装配），被注入的类同样要被 Spring 容器管理，比如：Service 类注入到 Controller 类中。

```java
@Service
public class UserService {
  ......
}

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   private UserService userService;
   ......
}
```

### 2.2  @Component, @Repository, @Service, @Controller

我们一般使用 @Autowired 注解让 Spring 容器帮我们自动装配 bean。要想把类标识成可用于 @Autowired 注解自动装配的 bean 的类，可以采用以下注解实现：

●@Component ：通用的注解，可标注任意类为 Spring 组件。如果一个 Bean 不知道属于哪个层，可以使用@Component 注解标注；

●@Repository : 对应持久层即 Dao 层，主要用于数据库相关操作；

●@Service : 对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao 层；

●@Controller : 对应 Spring MVC 控制层，主要用于接受用户请求并调用 Service 层返回数据给前端页面。

### 2.3  @RestController

@RestController注解是@Controller和@ResponseBody的合集，表示这是个控制器 bean，并且是将函数的返回值直接填入 HTTP 响应体中，是 REST 风格的控制器。

现在都是前后端分离，说实话我已经很久没有用过@Controller。如果你的项目太老了的话，就当我没说。



单独使用 @Controller 不加 @ResponseBody的话一般使用在要返回一个视图的情况，这种情况属于比较传统的 Spring MVC 的应用，对应于前后端不分离的情况。@Controller+@ResponseBody 返回 JSON 或 XML 形式数据

### 2.4  @Scope

声明 Spring Bean 的作用域，使用方法:

```java
@Bean
@Scope("singleton")
public Person personSingleton() {
    return new Person();
}
```

四种常见的 Spring Bean 的作用域：

●singleton : 唯一 bean 实例，Spring 中的 bean 默认都是单例的；

●prototype : 每次请求都会创建一个新的 bean 实例；

●request : 每一次 HTTP 请求都会产生一个新的 bean，该 bean 仅在当前 HTTP request 内有效；

●session : 每一次 HTTP 请求都会产生一个新的 bean，该 bean 仅在当前 HTTP session 内有效。

### 2.5  @Configuration

一般用来声明配置类，可以使用 @Component注解替代，不过使用Configuration注解声明配置类更加语义化。

```java
@Configuration
public class AppConfig {
    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl();
    }
}
```



## 3、处理常见的 HTTP 请求类型

| @GetMapping    | 前端显示数据 |
| -------------- | ------------ |
| @PutMapping    | 后端接受数据 |
| @DeleteMapping | 删除数据     |

5 种常见的请求类型:

- **GET** ：请求从服务器获取特定资源。举个例子：`GET /users`（获取所有学生）
- **POST** ：在服务器上创建一个新的资源。举个例子：`POST /users`（创建学生）
- **PUT** ：更新服务器上的资源（客户端提供更新后的整个资源）。举个例子：`PUT /users/12`（更新编号为 12 的学生）
- **DELETE** ：从服务器删除特定的资源。举个例子：`DELETE /users/12`（删除编号为 12 的学生）
- **PATCH** ：更新服务器上的资源（客户端提供更改的属性，可以看做作是部分更新），使用的比较少

### 3.1 GET 请求

@GetMapping("users") 等价于@RequestMapping(value="/users",method=RequestMethod.GET)

```java
@GetMapping("/users")
public ResponseEntity<List<User>> getAllUsers() {
 return userRepository.findAll();
}
```

### 3.2 POST 请求

@PostMapping("users") 等价于@RequestMapping(value="/users",method=RequestMethod.POST)

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
 return userRespository.save(user);
}
```

### 3.3 PUT 请求

@PutMapping("/users/{userId}") 等价于@RequestMapping(value="/users/{userId}",method=RequestMethod.PUT)

```java
@PutMapping("/users/{userId}")
public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId,
  @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
  ......
}
```

### 3.4 DELETE 请求

@DeleteMapping("/users/{userId}")等价于@RequestMapping(value="/users/{userId}",method=RequestMethod.DELETE)

```java
@DeleteMapping("/users/{userId}")
public ResponseEntity deleteUser(@PathVariable(value = "userId") Long userId){
  ......
}
```

### 3.5 PATCH 请求

一般实际项目中，我们都是 PUT 不够用了之后才用 PATCH 请求去更新数据。

```java
@PatchMapping("/profile")
  public ResponseEntity updateStudent(@RequestBody StudentUpdateRequest studentUpdateRequest) {
        studentRepository.updateDetail(studentUpdateRequest);
        return ResponseEntity.ok().build();
    }
```



## 4、前端传值

| @RequestParam | 动态参数(RequestParam  带?)   |
| ------------- | ----------------------------- |
| @PathVariable | 动态参数 (PathVariable 不带?) |

### 4.1 @PathVariable

PathVariable 不带?  

例子 http://localhost:8090/paramage=/参数1/test/参数2

```java
//
@GetMapping("/{id}/test/{name}")
public Result<XUser> getUserById(@PathVariable("id") Integer id) {
   .....
}
```

例子2：

```java
@GetMapping("/klasses/{klassId}/teachers")
public List<Teacher> getKlassRelatedTeachers(
         @PathVariable("klassId") Long klassId,
         @RequestParam(value = "type", required = false) String type ) {
...
}
```

如果前端请求的 url 是：/klasses/123456/teachers?type=web

那么后端服务获取到的数据就是：klassId=123456,  type=web

### 4.2 @RequestParam

RequestParam  带?

例子 http://localhost:8090/param?age=参数?name=参数

```java
@GetMapping("/param")
public String hello(@RequestParam(value = "age",   defaultValue = "0") int age,      @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    //required 表示可以不传值
    ....
}
```

#### 前端接口

```js
getUserList (searchModel){}
    return request ({
        url: "/user/list",
        method: "get",
        params:{
            pageNo: searchModel.pageNo,
            pageSize: searchModel.pageSize,
            username: searchModel.username,
            phone: searchModel.phone,
        },
	});
}
```

调用

![image-20230925145552245](spring boot3.assets/image-20230925145552245.png)

```vue
getUserList () {
    userApi.getUserList(this.searchModel).then((response) => {
        this.userList = response.data.rows;
        this.total = response.data.total;
	});
}
```

==注意:  searchModel为前端参数,   response为后端服务器返回的结果==

后端响应结果:

<img src="spring boot3.assets/image-20230925143036839.png" alt="image-20230925143036839" style="zoom:67%;" />

#### 后端接口

```java
@GetMapping("/user/list")
public Result<?> getUserListPage(@RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam("pageNo") Long pageNo,
                                 @RequestParam("pageSize") Long pageSize) {
    LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper();
    //等价wrapper.eq(username != null, XUser::getUsername, username);
    wrapper.eq(StringUtils.hasLength(username), XUser::getUsername, username);
    wrapper.eq(phone != null, XUser::getPhone, phone);
    wrapper.orderByDesc(XUser::getId);
    // 分页
    Page<XUser> page = new Page<>(pageNo, pageSize);
    xUserService.page(page, wrapper);

    Map<String, Object> data = new HashMap<>();
    data.put("total", page.getTotal());   //page.getTotal()得添加配置,具体见官网
    data.put("rows", page.getRecords());  //结果集

    return Result.success(data);
    /**data数据:
		 * {
		 *     "code": 20000,
		 *     "message": "success",
		 *     "data": {
		 *         "total": 6,
		 *         "rows": [
		 *             {
		 *                 "id": 1,
		 *                 "username": "admin",
		 *                 "password": "123456",
		 *                 "email": "super@aliyun.com",
		 *                 "phone": "18677778888",
		 *                 "status": 1,
		 *                 "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
		 *                 "deleted": 0
		 *             }
		 *         ]
		 *     }
		 * }
		 * **/
}
```

测试效果

![image-20230925142927286](spring boot3.assets/image-20230925142927286.png)

### 4.3 @RequestBody

用于读取 Request 请求（可能是 POST,PUT,DELETE,GET 请求）的 body 部分并且Content-Type 为 application/json 格式的数据，接收到数据之后会自动将数据绑定到 Java 对象上去。系统会使用HttpMessageConverter或者自定义的HttpMessageConverter将请求的 body 中的 json 字符串转换为 java 对象。

```java
	@PostMapping("/user/login")
	public Result<Map<String, Object>> login(@RequestBody XUser user) {
		// 登录操作
		Map<String, Object> data = xUserService.login(user);
		if (data != null)
			return Result.success(data);
		return Result.fail(20000, "用户密码错误");
	}
```

#### 前端接口

```js
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}
```

#### 后端接口

controller

```java
@PostMapping("/login")
public Result<Map<String,Object>> login(@RequestBody User user){
    Map<String,Object> data = userService.login(user);
    if(data != null){
        return Result.success(data);
    }
    return Result.fail(20002,"用户名或密码错误");
}
```

service

```java
@Override	
public Map<String, Object> login(XUser user) {
    //根据用户名和密码查询
    LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(XUser::getUsername,user.getUsername());
    wrapper.eq(XUser::getPassword,user.getPassword());
    XUser loginUser = this.baseMapper.selectOne(wrapper);
    //结果不为空,则生成token,并将用户信息存入redis
    if(loginUser != null) {
        // 暂时用UUID，终极方案是jwt
        String key = "user:" + UUID.randomUUID();
        // 存入redis
        loginUser.setPassword(null); //密码不存
        redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
        // 返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", key);
        return data;
    }
    return null;
}
```

测试效果

![image-20230923094816855](spring boot3.assets/image-20230923094816855.png)

## 5、读取配置信息

将一些常用的配置信息比如阿里云 oss、发送短信、微信认证的相关配置信息等等放到配置文件中。

我们的数据源application.yml内容如下：：

```yml
wuhan2020: 2020年初武汉爆发了新型冠状病毒，疫情严重，但是，我相信一切都会过去！武汉加油！中国加油！
my-profile:
  name: Guide哥
  email: koushuangbwcx@163.com
library:
  location: 湖北武汉加油中国加油
  books:
    - name: 天才基本法
      description: 二十二岁的林朝夕在父亲确诊阿尔茨海默病这天，得知自己暗恋多年的校园男神裴之即将出国深造的消息——对方考取的学校，恰是父亲当年为她放弃的那所。
    - name: 时间的秩序
      description: 为什么我们记得过去，而非未来？时间“流逝”意味着什么？是我们存在于时间之内，还是时间存在于我们之中？卡洛·罗韦利用诗意的文字，邀请我们思考这一亘古难题——时间的本质。
    - name: 了不起的我
      description: 如何养成一个新习惯？如何让心智变得更成熟？如何拥有高质量的关系？ 如何走出人生的艰难时刻？
```

### 5.1  @value(常用)

使用 @Value("${property}") 读取比较简单的配置信息：

properties文件

```properties
robot.name=小红
```

使用

```java
@Value("${robot.name}")
private String naem;    // name ={robot.name}
```

### 5.2  @ConfigurationProperties(常用)

通过@ConfigurationProperties读取配置信息并与 bean 绑定。

```java
@Component
@ConfigurationProperties(prefix = "library")
class LibraryProperties {
    @NotEmpty
    private String location;
    private List<Book> books;
    @Setter
    @Getter
    @ToString
    static class Book {
        String name;
        String description;
    }
  省略getter/setter
  ......
}
```

你可以像使用普通的 Spring bean 一样，将其注入到类中使用。

### 5.3  PropertySource（不常用）

@PropertySource读取指定 properties 文件

```java
@Component
@PropertySource("classpath:website.properties")
class WebSite {
    @Value("${url}")
    private String url;
  省略getter/setter
  ......
}
```

## 6、参数校验

数据的校验的重要性就不用说了，即使在前端对数据进行校验的情况下，我们还是要对传入后端的数据再进行一遍校验，避免用户绕过浏览器直接通过一些 HTTP 工具直接向后端请求一些违法数据。

JSR(Java Specification Requests） 是一套 JavaBean 参数校验的标准，它定义了很多常用的校验注解，我们可以直接将这些注解加在我们 JavaBean 的属性上面，这样就可以在需要校验的时候进行校验了，非常方便！

校验的时候我们实际用的是 Hibernate Validator 框架。Hibernate Validator 是 Hibernate 团队最初的数据校验框架，Hibernate Validator 4.x 是 Bean Validation 1.0（JSR 303）的参考实现，Hibernate Validator 5.x 是 Bean Validation 1.1（JSR 349）的参考实现，目前最新版的 Hibernate Validator 6.x 是 Bean Validation 2.0（JSR 380）的参考实现。

SpringBoot 项目的 spring-boot-starter-web 依赖中已经有 hibernate-validator 包，不需要引用相关依赖。如下图所示（通过 idea 插件—Maven Helper 生成）：

<img src="spring boot3.assets/image-20230925151012885.png" alt="image-20230925151012885" style="zoom: 67%;" />



非 SpringBoot 项目需要自行引入相关依赖包，这里不多做讲解

需要注意的是： 所有的注解，推荐使用 JSR 注解，即javax.validation.constraints，而不是org.hibernate.validator.constraints

### 6.1   常用的字段验证的注解

- `@NotEmpty` 被注释的字符串的不能为 null 也不能为空
- `@NotBlank` 被注释的字符串非 null，并且必须包含一个非空白字符
- `@Null` 被注释的元素必须为 null
- `@NotNull` 被注释的元素必须不为 null
- `@AssertTrue` 被注释的元素必须为 true
- `@AssertFalse` 被注释的元素必须为 false
- `@Pattern(regex=,flag=)`被注释的元素必须符合指定的正则表达式
- `@Email` 被注释的元素必须是 Email 格式
- `@Min(value)`被注释的元素必须是一个数字，其值必须大于等于指定的最小值
- `@Max(value)`被注释的元素必须是一个数字，其值必须小于等于指定的最大值
- `@DecimalMin(value)`被注释的元素必须是一个数字，其值必须大于等于指定的最小值
- `@DecimalMax(value)` 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
- `@Size(max=, min=)`被注释的元素的大小必须在指定的范围内
- `@Digits (integer, fraction)`被注释的元素必须是一个数字，其值必须在可接受的范围内
- `@Past`被注释的元素必须是一个过去的日期
- `@Future` 被注释的元素必须是一个将来的日期

### 6.2. 验证请求体

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Person {
    @NotNull(message = "classId 不能为空")  // 表示classId必须不会空
    private String classId;
    @Size(max = 33)
    @NotNull(message = "name 不能为空")
    private String name;
    @Pattern(regexp = "((^Man$|^Woman$|^UGM$))", message = "sex 值不在可选范围")
    @NotNull(message = "sex 不能为空")
    private String sex;
    @Email(message = "email 格式不正确")
    @NotNull(message = "email 不能为空")
    private String email;
}
```

我们在需要验证的参数上加上了`@Valid`注解，如果验证失败，它将抛出`MethodArgumentNotValidException`

```java
@RestController
@RequestMapping("/api")
public class PersonController {
    @PostMapping("/person")
    public ResponseEntity<Person> getPerson(@RequestBody @Valid Person person) {
        return ResponseEntity.ok().body(person);
    }
}
```

### 6.3. 验证请求参数(常用)

@Validated 告诉 Spring 去校验方法参数

```java
@RestController
@RequestMapping("/api")
@Validated
public class PersonController {
    @GetMapping("/person/{id}")
    public ResponseEntity<Integer> getPersonByID(@Valid @PathVariable("id") @Max(value = 5,message = "超过 id 的范围了") Integer id) {
        return ResponseEntity.ok().body(id);
    }
}
```

### 7、全局处理 Controller 层异常

Spring 项目必备的全局处理 Controller 层异常

相关注解：

1. `@ControllerAdvice` :注解定义全局异常处理类
2. `@ExceptionHandler` :注解声明异常处理方法

如果方法参数不对的话就会抛出MethodArgumentNotValidException，来处理这个异常。

```java
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 请求参数异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
       ......
    }
}
```

## 8、 数据库相关

使用插件后不用考虑

### 8.1. 创建表

@Entity声明一个类对应一个数据库实体。

@Table 设置表明

```java
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    省略getter/setter......
}
```

### 8.2. 创建主键

@Id ：声明一个字段为主键。

使用@Id声明之后，我们还需要定义主键的生成策略。我们可以使用 @GeneratedValue 指定主键生成策略。

1.通过 @GeneratedValue直接使用 JPA 内置提供的四种主键生成策略来指定主键生成策略。

```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

JPA 使用枚举定义了 4 中常见的主键生成策略，如下：

枚举替代常量的一种用法

```java
public enum GenerationType {
    /**
     * 使用一个特定的数据库表格来保存主键
     * 持久化引擎通过关系数据库的一张特定的表格来生成主键,
     */
    TABLE,
    /**
     *在某些数据库中,不支持主键自增长,比如Oracle、PostgreSQL其提供了一种叫做"序列(sequence)"的机制生成主键
     */
    SEQUENCE,
    /**
     * 主键自增长
     */
    IDENTITY,
    /**
     *把主键生成策略交给持久化引擎(persistence engine),
     *持久化引擎会根据数据库在以上三种主键生成 策略中选择其中一种
     */
    AUTO
}
```

@GeneratedValue注解默认使用的策略是GenerationType.AUTO

```
public @interface GeneratedValue {
    GenerationType strategy() default AUTO;
    String generator() default "";
}
```

一般使用 MySQL 数据库的话，使用GenerationType.IDENTITY策略比较普遍一点（分布式系统的话需要另外考虑使用分布式 ID）。

2.通过 @GenericGenerator声明一个主键策略，然后 @GeneratedValue使用这个策略

```java
@Id
@GeneratedValue(generator = "IdentityIdGenerator")
@GenericGenerator(name = "IdentityIdGenerator", strategy = "identity")
private Long id;
```

等价于：

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

jpa 提供的主键生成策略有如下几种：

```
public class DefaultIdentifierGeneratorFactory
  implements MutableIdentifierGeneratorFactory, Serializable, ServiceRegistryAwareService {
 @SuppressWarnings("deprecation")
 public DefaultIdentifierGeneratorFactory() {
  register( "uuid2", UUIDGenerator.class );
  register( "guid", GUIDGenerator.class );   // can be done with UUIDGenerator + strategy
  register( "uuid", UUIDHexGenerator.class );   // "deprecated" for new use
  register( "uuid.hex", UUIDHexGenerator.class );  // uuid.hex is deprecated
  register( "assigned", Assigned.class );
  register( "identity", IdentityGenerator.class );
  register( "select", SelectGenerator.class );
  register( "sequence", SequenceStyleGenerator.class );
  register( "seqhilo", SequenceHiLoGenerator.class );
  register( "increment", IncrementGenerator.class );
  register( "foreign", ForeignGenerator.class );
  register( "sequence-identity", SequenceIdentityGenerator.class );
  register( "enhanced-sequence", SequenceStyleGenerator.class );
  register( "enhanced-table", TableGenerator.class );
 }
 public void register(String strategy, Class generatorClass) {
  LOG.debugf( "Registering IdentifierGenerator strategy [%s] -> [%s]", strategy, generatorClass.getName() );
  final Class previous = generatorStrategyToClassNameMap.put( strategy, generatorClass );
  if ( previous != null ) {
   LOG.debugf( "    - overriding [%s]", previous.getName() );
  }
 }
}
```



### 8.3. 设置字段类型

@Column 声明字段。

示例：

设置属性 userName 对应的数据库字段名为 user_name，长度为 32，非空

```
@Column(name = "user_name", nullable = false, length=32)
private String userName;
```

设置字段类型并且加默认值，这个还是挺常用的。

```
Column(columnDefinition = "tinyint(1) default 1")
private Boolean enabled;
```



### 8.4. 指定不持久化特定字段

@Transient ：声明不需要与数据库映射的字段，在保存的时候不需要保存进数据库 。

如果我们想让secrect 这个字段不被持久化，可以使用 @Transient关键字声明。

```
Entity(name="USER")
public class User {
    ......
    @Transient
    private String secrect; // not persistent because of @Transient
}
```

除了 @Transient关键字声明， 还可以采用下面几种方法：

```
static String secrect; // not persistent because of static
final String secrect = “Satish”; // not persistent because of final
transient String secrect; // not persistent because of transient
```

一般使用注解的方式比较多。

### 8.5. 声明大字段

@Lob:声明某个字段为大字段。

```
@Lob
private String content;
```

更详细的声明：

```java
@Lob
//指定 Lob 类型数据的获取策略， FetchType.EAGER 表示非延迟 加载，而 FetchType. LAZY 表示延迟加载 ；
@Basic(fetch = FetchType.EAGER)
//columnDefinition 属性指定数据表对应的 Lob 字段类型
@Column(name = "content", columnDefinition = "LONGTEXT NOT NULL")
private String content;
```



### 8.6. 创建枚举类型的字段

可以使用枚举类型的字段，不过枚举字段要用@Enumerated注解修饰。

```java
public enum Gender {
    MALE("男性"),
    FEMALE("女性");
    private String value;
    Gender(String str){
        value=str;
    }
}
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    省略getter/setter......
}
```

数据库里面对应存储的是 MAIL/FEMAIL。

### 8.7. 增加审计功能

只要继承了 AbstractAuditBase的类都会默认加上下面四个字段。

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AbstractAuditBase {
    @CreatedDate
    @Column(updatable = false)
    @JsonIgnore
    private Instant createdAt;
    @LastModifiedDate
    @JsonIgnore
    private Instant updatedAt;
    @CreatedBy
    @Column(updatable = false)
    @JsonIgnore
    private String createdBy;
    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;
}
```

我们对应的审计功能对应地配置类可能是下面这样的（Spring Security 项目）:

```java
@Configuration
@EnableJpaAuditing
public class AuditSecurityConfiguration {
    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}
```

简单介绍一下上面设计到的一些注解：

1@CreatedDate: 表示该字段为创建时间时间字段，在这个实体被 insert 的时候，会设置值

2@CreatedBy :表示该字段为创建人，在这个实体被 insert 的时候，会设置值 @LastModifiedDate、@LastModifiedBy同理。

@EnableJpaAuditing：开启 JPA 审计功能。

### 8.8. 删除/修改数据

@Modifying 注解提示 JPA 该操作是修改操作,注意还要配合@Transactional注解使用。

```
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String userName);
}
```

### 8.9. 关联关系

- `@OneToOne` 声明一对一关系
- `@OneToMany` 声明一对多关系
- `@ManyToOne`声明多对一关系
- `MangToMang`声明多对多关系

## 9、事务 @Transactional

在要开启事务的方法上使用@Transactional注解即可!

```
@Transactional(rollbackFor = Exception.class)
public void save() {
  ......
}
```

我们知道 Exception 分为运行时异常 RuntimeException 和非运行时异常。在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚。

@Transactional 注解一般用在可以作用在类或者方法上。

●作用于类：当把@Transactional 注解放在类上时，表示所有该类的public 方法都配置相同的事务属性信息。

●作用于方法：当类配置了@Transactional，方法也配置了@Transactional，方法的事务会覆盖类的事务配置信息。

## 10、Json 数据处理

###  10.1. 过滤 json 数据

@JsonIgnoreProperties 作用在类上用于过滤掉特定字段不返回或者不解析。

```java
//生成json时将userRoles属性过滤
@JsonIgnoreProperties({"userRoles"})
public class User {
    private String userName;
    private String fullName;
    private String password;
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();
}
```

@JsonIgnore一般用于类的属性上，作用和上面的@JsonIgnoreProperties 一样。

```java
public class User {
    private String userName;
    private String fullName;
    private String password;
   //生成json时将userRoles属性过滤
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();
}
```



### 10.2. 格式化 json 数据

@JsonFormat一般用来格式化 json 数据。：

比如：

```java
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
private Date date;
```



### 10.3. 扁平化对象

```java
@Getter
@Setter
@ToString
public class Account {
    @JsonUnwrapped
    private Location location;
    @JsonUnwrapped
    private PersonInfo personInfo;
  @Getter
  @Setter
  @ToString
  public static class Location {
     private String provinceName;
     private String countyName;
  }
  @Getter
  @Setter
  @ToString
  public static class PersonInfo {
    private String userName;
    private String fullName;
  }
}
```

未扁平化之前：

```java
{
    "location": {
        "provinceName":"湖北",
        "countyName":"武汉"
    },
    "personInfo": {
        "userName": "coder1234",
        "fullName": "shaungkou"
    }
}
```

使用@JsonUnwrapped 扁平对象之后：

```java
@Getter
@Setter
@ToString
public class Account {
    @JsonUnwrapped
    private Location location;
    @JsonUnwrapped
    private PersonInfo personInfo;
    ......
}
{
  "provinceName":"湖北",
  "countyName":"武汉",
  "userName": "coder1234",
  "fullName": "shaungkou"
}
```



## 11、测试相关

@ActiveProfiles一般作用于测试类上， 用于声明生效的 Spring 配置文件。

```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public abstract class TestBase {
  ......
}
```

@Test声明一个方法为测试方法

@Transactional被声明的测试方法的数据会回滚，避免污染测试数据。

@WithMockUser Spring Security 提供的，用来模拟一个真实用户，并且可以赋予权限。

```java
@Test
@Transactional
@WithMockUser(username = "user-id-18163138155", authorities = "ROLE_TEACHER")
void should_import_student_success() throws Exception {
    ......
    }
```



## 12、高级特性

### 12.1 @Schedule

@Schedule注解是Spring实现定时任务的一种方式，相比Java起周期性线程池实现定时任务，这种纯注解的方式更简洁。

#### 12.1.1 使用方法

- 在springboot启动类加上注解：`**@EnableScheduling**`；
- 新建一个定时任务类，需要添加`**@Component**`或者`**@Service**`注解；
- 在任务类中添加具体的定时任务方法，方法上用`**@Schedule**`注解修饰，注解里有两个重要参数：

- - **fixedRate**：指定两次任务执行的时间间隔(**毫秒**)，前一个任务开始与下一个任务开始的间隔，需要注意：如果任务执行的时间大于间隔，会导致阻塞，因为@Schedule默认是单线程的；
  - **fixedDelay**：指定两次任务执行的时间间隔(毫秒)，前一个任务结束与下一个任务开始的间隔。

- 一个定时任务类中可以有多个定时任务方法，一个项目里必然可以有多个定时任务类。

举例：

springBoot启动类：

```java
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
```

定时任务类：

```java
@Component
public class ScheduleTaskDelay {

    @Scheduled(fixedRate = 3000)
    @Scheduled(fixedRate = 3000)
    @Async
    public void task()
    {
        System.out.println(Thread.currentThread().getName() + " start time is: " + new Date());
        System.out.println(Thread.currentThread().getName() + " is processing...");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " schedule task is finished");
        System.out.println(Thread.currentThread().getName() + " end time is: " + new Date());
    }
}
```

注意：

- 定时任务具体的执行情况可以通过@Schedule里的参数设置，比如fixedRate、fixedDelay和corn表达式；
- Schedule默认是单线程执行，如果需要异步模式，可以加上@Async注解。

#### 12.1.2 cron表达式

cron表达式是一个字符串，作为@Schedule注解里的参数，用来制定粒度更为详细的定时任务，一个corn表达式是一个由六至七个字段组成由空格分隔的字符串，其中6个字段是必须的，而剩下的一个是可选的，corn表达式的位从左至右依次为：

| 字段名        | 允许的值         | 允许的特殊字符  |
| ------------- | ---------------- | --------------- |
| 秒            | 0-59             | , - * /         |
| 分            | 0-59             | , - * /         |
| 小时          | 0-23             | , - * /         |
| 日            | 1-31             | , - * ? / L W C |
| 月            | 1-12 or JAN-DEC  | , - * /         |
| 周几          | 1-7 or SUN-SAT   | , - * ? / L C # |
| 年 (可选字段) | empty, 1970-2099 | , - * /         |

说明：

●'*' 字符可以用于所有字段，在“分”字段中设为"*"表示"每一分钟"的含义；

●'?' 字符可以用在“日”和“周几”字段. 它用来指定 '不明确的值'. 这在你需要指定这两个字段中的某一个值而不是另外一个的时候会被用到。在后面的例子中可以看到其含义；

●'-' 字符被用来指定一个值的范围，比如在“小时”字段中设为"10-12"表示"10点到12点"；

●',' 字符指定数个值。比如在“周几”字段中设为"MON,WED,FRI"表示"the days Monday, Wednesday, and Friday"；

●'/' 字符用来指定一个值的的增加幅度. 比如在“秒”字段中设置为"0/15"表示"第0, 15, 30, 和 45秒"。而 "5/15"则表示"第5, 20, 35, 和 50". 在'/'前加"*"字符相当于指定从0秒开始. 每个字段都有一系列可以开始或结束的数值。对于“秒”和“分”字段来说，其数值范围为0到59，对于“小时”字段来说其为0到23, 对于“日”字段来说为0到31, 而对于“月”字段来说为1到12。"/"字段仅仅只是帮助你在允许的数值范围内从开始"第n"的值。 因此 对于“月”字段来说"7/6"只是表示7月被开启而不是“每六个月”, 请注意其中微妙的差别；

●'L'字符可用在“日”和“周几”这两个字段。它是"last"的缩写, 但是在这两个字段中有不同的含义。例如,“日”字段中的"L"表示"一个月中的最后一天" —— 对于一月就是31号对于二月来说就是28号（非闰年）。而在“周几”字段中, 它简单的表示"7" or "SAT"，但是如果在“周几”字段中使用时跟在某个数字之后, 它表示"该月最后一个星期×" —— 比如"6L"表示"该月最后一个周五"。当使用'L'选项时,指定确定的列表或者范围非常重要，否则你会被结果搞糊涂的；

●'W' 可用于“日”字段。用来指定历给定日期最近的工作日(周一到周五) 。比如你将“日”字段设为"15W"，意为: "离该月15号最近的工作日"。因此如果15号为周六，触发器会在14号即周五调用。如果15号为周日, 触发器会在16号也就是周一触发。如果15号为周二,那么当天就会触发。然而如果你将“日”字段设为"1W", 而一号又是周六, 触发器会于下周一也就是当月的3号触发,因为它不会越过当月的值的范围边界。'W'字符只能用于“日”字段的值为单独的一天而不是一系列值的时候；

●'L'和'W'可以组合用于“日”字段表示为'LW'，意为"该月最后一个工作日"；

●'#' 字符可用于“周几”字段。该字符表示“该月第几个周×”，比如"6#3"表示该月第三个周五( 6表示周五而"#3"该月第三个)。再比如: "2#1" = 表示该月第一个周一而 "4#5" = 该月第五个周三。注意如果你指定"#5"该月没有第五个“周×”，该月是不会触发的；

●'C' 字符可用于“日”和“周几”字段，它是"calendar"的缩写。 它表示为基于相关的日历所计算出的值（如果有的话）。如果没有关联的日历, 那它等同于包含全部日历。“日”字段值为"5C"表示"日历中的第一天或者5号以后"，“周几”字段值为"1C"则表示"日历中的第一天或者周日以后"；

●对于“月份”字段和“周几”字段来说合法的字符都不是大小写敏感的。

常用的corn表达式举例：

| 含义                                                         | corn表达式                 |
| ------------------------------------------------------------ | -------------------------- |
| 每天中午十二点触发                                           | "0 0 12 * * ?"             |
| 每天早上10：15触发                                           | "0 15 10 ? * *"            |
| 每天早上10：15触发                                           | "0 15 10 * * ?"            |
| 每天早上10：15触发                                           | "0 15 10 * * ? *"          |
| 2005年的每天早上10：15触发                                   | "0 15 10 * * ? 2005"       |
| 每天从下午2点开始到2点59分每分钟一次触发                     | "0 * 14 * * ?"             |
| 每天从下午2点开始到2：55分结束每5分钟一次触发                | "0 0/5 14 * * ?"           |
| 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 | "0 0/5 14,18 * * ?"        |
| 每天14:00至14:05每分钟一次触发                               | "0 0-5 14 * * ?"           |
| 三月的每周三的14：10和14：44触发                             | "0 10,44 14 ? 3 WED"       |
| 每个周一、周二、周三、周四、周五的10：15触发                 | "0 15 10 ? * MON-FRI"      |
| 每月15号的10：15触发                                         | "0 15 10 15 * ?"           |
| 每月的最后一天的10：15触发                                   | "0 15 10 L * ?"            |
| 每月最后一个周五的10：15触发                                 | "0 15 10 ? * 6L"           |
| 每月最后一个周五的10：15触发                                 | "0 15 10 ? * 6L"           |
| 2002年至2005年的每月最后一个周五的10：15触发                 | "0 15 10 ? * 6L 2002-2005" |
| 每月的第三个周五的10：15触发                                 | "0 15 10 ? * 6#3"          |

具体用到了再查，更多关于corn表达式的见参考链接。

#### 12.1.3 使用注意点

由于@Schedule注解默认是单线程，这会给我们实际使用时带来一些“坑”，比如我用@Scheduled(fixedRate = 3000)建立了一个定时任务的方法，但是这个方法的实现，执行定时任务的时间大于fixedRate的值，结果就不是我们期望的每隔3秒都会执行一次任务，因为未执行的任务会被阻塞，因此需要异步地执行周期性任务。



将@Schedule注解的周期性任务异步化有两种方案：

- 使用@Async注解实现异步任务，这种方式比较简单，在定时任务的方法上加上@Async注解，注意启动类要加上@EnableAsync才会生效；
- 通过SchedulingConfigurer类手动设置定时任务的线程池大小，不使用@Async注解，如下：

```java
@Configuration
public class AppConfig implements SchedulingConfigurer {
    @Bean
    public Executor taskExecutor() {
    	//指定定时任务线程数量，可根据需求自行调节
        return Executors.newScheduledThreadPool(3);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }
}
```

两种方案各有优缺点：

| 比较 | 方案一                         | 方案二               |
| ---- | ------------------------------ | -------------------- |
| 优点 | 注解方式使用简单，代码量少     | 配置灵活，线程数可控 |
| 缺点 | 线程数不可控，可能存在资源浪费 | 需要增加编码         |

### 12.2 @Async

上面介绍@Schedule注解的时候提到了@Async注解，这个注解就是SpringBoot提供异步功能的方案。使用时需要在springBoot启动类上添加@EnableAsync注解，在需要异步的方法上添加@Async注解，搭配CompletableFuture可以获得异步任务的执行结果。

举例：

controller：

```java
package com.springboot.study.async.controller;

import com.springboot.study.async.service.impl.AsyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/study")
public class AsyncController {
    @Autowired
    private AsyncServiceImpl asyncService;

    @GetMapping("/async/something")
    public String asyncMethod() {
        asyncService.exampleMethod("Jerry");
        asyncService.exampleMethod("Tom");
        asyncService.exampleMethod("Cissie");
        asyncService.exampleMethod("Jack");
        return "sucess";
    }
}
```

service：

```java
package com.springboot.study.async.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncServiceImpl {
    @Async
    public String exampleMethod(String msg)
    {
        log.info(Thread.currentThread().getName() + " 正在打印：{}", msg);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
```

用postman发请求：http://localhost:8080/study/async/something

![image.png](https://cdn.nlark.com/yuque/0/2020/png/1536187/1606053840338-ea7d0cce-cf13-4723-98e3-97a8d6b2257f.png)

结果：

```plain
22:01:14.740 [pool-1-thread-4] INFO  com.springboot.study.async.service.impl.AsyncServiceImpl - pool-1-thread-4 正在打印：Jack
22:01:14.740 [pool-1-thread-1] INFO  com.springboot.study.async.service.impl.AsyncServiceImpl - pool-1-thread-1 正在打印：Jerry
22:01:14.740 [pool-1-thread-3] INFO  com.springboot.study.async.service.impl.AsyncServiceImpl - pool-1-thread-3 正在打印：Cissie
22:01:14.740 [pool-1-thread-2] INFO  com.springboot.study.async.service.impl.AsyncServiceImpl - pool-1-thread-2 正在打印：Tom
```

从打印结果看，调用service里的exampleMethod方法已经是不同的线程再调用，实现了异步的效果。

如果想让指定的线程池执行异步任务，可以在`@Async`直接指定线程池名称：`@Async`("线程池名称或者前缀")，详细的方法见参考链接。



从打印结果看，调用service里的exampleMethod方法已经是不同的线程再调用，实现了异步的效果。

如果想让指定的线程池执行异步任务，可以在@Async直接指定线程池名称：@Async("线程池名称或者前缀")，详细的方法见参考链接。

`**@Async**`**注解会在以下几个场景失效**，也就是说明明使用了`@Async`注解，但就没有走多线程：

- 异步方法使用static关键词修饰；
- 异步类不是一个Spring容器的bean（一般使用注解`@Component`和`@Service`，并且能被Spring扫描到）；
- SpringBoot应用中没有添加`@EnableAsync`注解；
- 在同一个类中，一个方法调用另外一个有@Async注解的方法，注解不会生效。原因是@Async注解的方法，是在代理类中执行的。

需要注意的是： 异步方法使用注解@Async的返回值只能为void或者Future及其子类，当返回结果为其他类型时，方法还是会异步执行，但是返回值都是null。

### 12.3 @EventListener

@EventListener是Springboot通过注解实现监听的一种方式，再介绍使用方法前， 先说一下监听机制：

![image.png](https://cdn.nlark.com/yuque/0/2020/png/1536187/1606140839529-0652ddeb-fb59-4553-9e3f-01706004206f.png)



监听机制用到了设计模式里的“观察者模式”，监听者ABC在监听一个事件，当事件发布者发布事件后，正在监听该事件的ABC会触发对应的处理逻辑。监听机制可以抽象出来三个模块：

- 被监听的事件；
- 事件发布；
- 监听者及监听者监听到事件后对应的处理逻辑。

当系统对并发要求比较高，或者出于更高层次的设计时，可以用消息队列来实现这种监听机制，但也会同时引入消息队列对应的复杂性问题。



这里以网上购物时下订单及支付订单为例说明监听机制及介绍@EventListener如何使用。网上选好商品时确定下单，然而实际如何支付这个订单，是用微信还是支付宝，是当场付钱还是半小时后付钱，说明如何支付订单是个复杂的逻辑，且下单和支付订单是两个应该解耦的逻辑，此时就可以使用监听机制（实际项目里应使用消息队列来实现监听机制），支付服务监听订单服务的下单事件，一旦监听到有下单任务，就触发支付服务执行支付业务。

举例：

订单服务类（对应事件发布者）

```
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl {

    /**
     * 这里传入ApplicationContext实例有点奇怪，实际项目中肯定不会传这个，
     * 传这个的目的是调用applicationContext.publishEvent方法触发监听的事件
     * @param applicationContext
     */
    public void createOrder(ApplicationContext applicationContext)
    {
        // 前面是创建订单的逻辑，后面是下单成功后的逻辑
        log.info(Thread.currentThread().getName() + " 下单成功");

        // 触发支付业务
        applicationContext.publishEvent(new OrderSuccessEvent(this, "下单成功"));

        log.info(Thread.currentThread().getName() + " 下单任务结束");
    }
}
```

被监听/发布的事件：

```
package com.springboot.study.enentListener;

import org.springframework.context.ApplicationEvent;

public class OrderSuccessEvent extends ApplicationEvent {
    private String msg;

    public OrderSuccessEvent(Object source, String msg)
    {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

支付服务类（对应监听者及监听者监听到事件后对应的处理逻辑）：

```
package com.springboot.study.enentListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentImpl {

    @Async("doSomethingExecutor")
    @EventListener
    public void eventListener(OrderSuccessEvent orderSuccessEvent)
    {
        log.info(Thread.currentThread().getName() + " 开始支付业务");
        // 这里写具体支付业务逻辑
        log.info(Thread.currentThread().getName() + " 支付业务结束");
    }
}
```

注意：

●用publishEvent方法触发监听事件的发布，不一定用applicationContext调用；

●监听事件要继承ApplicationEvent类；

●监听方法用@EventListener注解修饰，且入参为监听事件类。

#  日志配置

<img src="spring boot3.assets/image-20230707232608508.png" alt="image-20230707232608508" style="zoom:50%;" />

规范：项目开发不要编写`System.out.println()`，应该用**日志**记录信息

![img](spring boot3.assets/1680232037132-d2fa8085-3847-46f2-ac62-14a6188492aa-16886581349967.png)

## 1.  简介

1. Spring使用commons-logging作为内部日志，但底层日志实现是开放的。可对接其他日志框架。

1. 1. spring5及以后 commons-logging被spring直接自己写了。

1. 支持 jul，log4j2,logback。SpringBoot 提供了默认的控制台输出配置，也可以配置输出为文件。
2. logback是默认使用的。
3. 虽然**日志框架很多**，但是我们不用担心，使用 SpringBoot 的**默认配置就能工作的很好**。



**SpringBoot怎么把日志默认配置好的**

1、每个`starter`场景，都会导入一个核心场景`spring-boot-starter`

2、核心场景引入了日志的所用功能`spring-boot-starter-logging`

3、默认使用了`logback + slf4j` 组合作为默认底层日志

4、`日志是系统一启动就要用`，`xxxAutoConfiguration`是系统启动好了以后放好的组件，后来用的。

5、日志是利用**监听器机制**配置好的。`ApplicationListener`。

6、日志所有的配置都可以通过修改配置文件实现。以`logging`开始的所有配置。

## 2. 日志格式

```shell
2023-03-31T13:56:17.511+08:00  INFO 4944 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-03-31T13:56:17.511+08:00  INFO 4944 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.7]
```

默认输出格式：

- 时间和日期：毫秒级精度
- 日志级别：ERROR, WARN, INFO, DEBUG, or TRACE.
- 进程 ID
- ---： 消息分割符
- 线程名： 使用[]包含
- Logger 名： 通常是产生日志的**类名**
- 消息： 日志记录的内容

注意： logback 没有FATAL级别，对应的是ERROR

默认值：参照：`spring-boot`包`additional-spring-configuration-metadata.json`文件

默认输出格式值：`%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}`

```
可修改为：`'%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} ===> %msg%n'`
```

![image-20230707233253434](spring boot3.assets/image-20230707233253434.png)

![image-20230707233300815](spring boot3.assets/image-20230707233300815.png)



## 3. 记录日志

```java
Logger logger = LoggerFactory.getLogger(getClass());

或者使用Lombok的@Slf4j注解
```

```java
@Slf4j
@RestController
public class HelloController {
//    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/h/**")
    public String hello(String a,String b){

        for (int i = 0; i < 1000; i++) {
            log.trace("trace 日志.....");
            log.debug("debug 日志.....");
            //SpringBoot底层默认的日志级别 info
            log.info("info 日志..... 参数a:{} b:{}",a,b);
            log.warn("warn 日志...");
            log.error("error 日志...");
        }
        return "hello";
    }
}
```

## 4. 日志级别

- 由低到高：`ALL,TRACE, DEBUG, INFO, WARN, ERROR,FATAL,OFF`；

- - **只会打印指定级别及以上级别的日志**
  - ALL：打印所有日志
  - TRACE：追踪框架详细流程日志，一般不使用
  - DEBUG：开发调试细节日志
  - INFO：关键、感兴趣信息日志
  - WARN：警告但不是错误的信息日志，比如：版本过时
  - ERROR：业务错误日志，比如出现各种异常
  - FATAL：致命错误日志，比如jvm系统崩溃
  - OFF：关闭所有日志记录

- 不指定级别的所有类，都使用root指定的级别作为默认级别
- SpringBoot日志**默认级别是** **INFO**

1. 在application.properties/yaml中配置logging.level.<logger-name>=<level>指定日志级别

   ![image-20230707234017551](spring boot3.assets/image-20230707234017551.png)

2. level可取值范围：`TRACE, DEBUG, INFO, WARN, ERROR, FATAL, or OFF`，定义在 `LogLevel`类中

3. root 的logger-name叫root，可以配置logging.level.root=warn，代表所有未指定日志级别都使用 root 的 warn 级别



## 5. 日志分组

比较有用的技巧是：

将相关的logger分组在一起，统一配置。SpringBoot 也支持。比如：Tomcat 相关的日志统一设置

在application.properties/yaml中配置:

 #把这些包org.apache.catalina,org.apache.coyote,org.apache.tomca分成tomcat组:

```java
logging.group.tomcat=org.apache.catalina,org.apache.coyote,org.apache.tomcat 
logging.level.tomcat=trace
```

SpringBoot 预定义两个组

| Name | Loggers                                                      |
| ---- | ------------------------------------------------------------ |
| web  | org.springframework.core.codec, org.springframework.http, org.springframework.web, org.springframework.boot.actuate.endpoint.web, org.springframework.boot.web.servlet.ServletContextInitializerBeans |
| sql  | org.springframework.jdbc.core, org.hibernate.SQL, org.jooq.tools.LoggerListener |

## 6. 文件输出

SpringBoot 默认只把日志写在控制台，如果想额外记录到文件，可以在application.properties中添加logging.file.name or logging.file.path配置项。

| logging.file.name | logging.file.path | 示例     | 效果                             |
| ----------------- | ----------------- | -------- | -------------------------------- |
| 未指定            | 未指定            |          | 仅控制台输出                     |
| **指定**          | 未指定            | my.log   | 写入指定文件。可以加路径         |
| 未指定            | **指定**          | /var/log | 写入指定目录，文件名为spring.log |
| **指定**          | **指定**          |          | 以logging.file.name为准          |

![image-20230707234409337](spring boot3.assets/image-20230707234409337.png)

## 7. 文件归档与滚动切割

归档：每天的日志单独存到一个文档中。

切割：每个文件10MB，超过大小切割成另外一个文件。

1. 每天的日志应该独立分割出来存档。如果使用logback（SpringBoot 默认整合），可以通过application.properties/yaml文件指定日志滚动规则。
2. 如果是其他日志系统，需要自行配置（添加log4j2.xml或log4j2-spring.xml）
3. 支持的滚动规则设置如下

| 配置项                                               | 描述                                                         |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| logging.logback.rollingpolicy.file-name-pattern      | 日志存档的文件名格式（默认值：${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz） |
| logging.logback.rollingpolicy.clean-history-on-start | 应用启动时是否清除以前存档（默认值：false）                  |
| logging.logback.rollingpolicy.max-file-size          | 存档前，每个日志文件的最大大小（默认值：10MB）               |
| logging.logback.rollingpolicy.total-size-cap         | 日志文件被删除之前，可以容纳的最大大小（默认值：0B）。设置1GB则磁盘存储超过 1GB 日志后就会删除旧日志文件 |
| logging.logback.rollingpolicy.max-history            | 日志文件保存的最大天数(默认值：7).                           |

```
#\u5F52\u6863\u3001\u5207\u5272
#logging.logback.rollingpolicy.file-name-pattern=${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz
#logging.logback.rollingpolicy.max-file-size=1MB
```

## 8. 自定义配置

通**常我们配置 application.properties 就够了。当然也可以自定义。比如：**

| 日志系统                | 自定义                                                       |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy |
| Log4j2                  | log4j2-spring.xml or log4j2.xml                              |
| JDK (Java Util Logging) | logging.properties                                           |

如果可能，我们建议您在日志配置中使用`-spring` 变量（例如，`logback-spring.xml` 而不是 `logback.xml`）。如果您使用标准配置文件，spring 无法完全控制日志初始化。

最佳实战：自己要写配置，配置文件名加上 `xx-spring.xml`

```java
<?xml version="1.0" encoding="UTF-8"?>

<!-- 配置文件修改时重新加载，默认true -->
<configuration scan="true">

    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->
    <property name="CATALINA_BASE" value="logs"></property>

    <!-- 控制台输出 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder charset="UTF-8">
            <!-- 输出日志记录格式 -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==哈哈== [%thread] %-5level ==哈哈== %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 第一个文件输出,每天产生一个文件 -->
    <appender name="FILE1" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 输出文件路径+文件名 -->
            <fileNamePattern>${CATALINA_BASE}/aa.%d{yyyyMMdd}.log</fileNamePattern>
            <!-- 保存30天的日志 -->
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <!-- 输出日志记录格式 -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 第二个文件输出,每天产生一个文件 -->
    <appender name="FILE2" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${CATALINA_BASE}/bb.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${CATALINA_BASE}/bb.%d{yyyyMMdd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="CUSTOM" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${CATALINA_BASE}/custom.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>${CATALINA_BASE}/custom.%d{yyyy-MM-dd}.log</fileNamePattern>
            <!-- keep 30 days' worth of history -->
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder charset="UTF-8">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 设置日志输出级别 -->
    <root level="ERROR">
        <appender-ref ref="CONSOLE" />
    </root>
    <logger name="file1" level="DEBUG">
        <appender-ref ref="FILE1" />
    </logger>
    <logger name="file1" level="INFO">
        <appender-ref ref="FILE2" />
    </logger>
    <!-- 自定义logger -->
    <logger name="custom" level="INFO">
        <appender-ref ref="CUSTOM" />
    </logger>
</configuration>
```

## 9. 切换日志组合

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
//spring-boot-starter-web中的spring-boot-starter有一个默认的日志场景
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>  //排除
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

1. **业务中使用slf4j-api记录日志。不要再 sout 了**
2. 导入任何第三方框架，先排除它的日志包，因为Boot底层控制好了日志

1. 修改 `application.properties` 配置文件，就可以调整日志的所有行为。(如果不够，可以编写日志框架自己的配置文件放在类路径下就行，比如`logback-spring.xml`，`log4j2-spring.xml`)
2. 如需对接**专业日志系统**，也只需要把 logback 记录的**日志**灌倒 **kafka**之类的中间件，这和SpringBoot没关系，都是日志框架自己的配置，**修改配置文件即可**

**再加上自定义配置**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，你会看到log4j2内部各种详细输出-->
<!--monitorInterval：Log4j能够自动检测修改配置 文件和重新配置本身，设置间隔秒数-->
<configuration status="WARN" monitorInterval="30">
    <!--先定义所有的appender-->
    <appenders>
        <!--这个输出控制台的配置-->
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式-->
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] ====- %l - %m%n"/>
        </console>
        <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用-->
        <File name="log" fileName="log/test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>
        <!-- 这个会打印出所有的info及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileInfo" fileName="${sys:user.home}/logs/info.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
        <RollingFile name="RollingFileWarn" fileName="${sys:user.home}/logs/warn.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy属性如不设置，则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <RollingFile name="RollingFileError" fileName="${sys:user.home}/logs/error.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <!--过滤掉spring和mybatis的一些无用的DEBUG信息-->
        <logger name="org.springframework" level="INFO"></logger>
        <logger name="org.mybatis" level="INFO"></logger>
        <root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>
</configuration>
```







![image-20230708095956979](spring boot3.assets/image-20230708095956979.png)



# 0. WebMvcAutoConfiguration原理

## 1. 生效条件

```java
@AutoConfiguration(after = { DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class }) //在这些自动配置之后
@ConditionalOnWebApplication(type = Type.SERVLET) //如果是web应用就生效，类型SERVLET、REACTIVE 响应式web
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class) //容器中没有这个Bean，才生效。默认就是没有
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)//优先级
@ImportRuntimeHints(WebResourcesRuntimeHints.class)
public class WebMvcAutoConfiguration { 
}
```





![image-20230708100818121](spring boot3.assets/image-20230708100818121.png)

## 2. 效果

1. 放了两个Filter：

1. 1.  `HiddenHttpMethodFilter`；页面表单提交Rest请求（GET、POST、PUT、DELETE）
   2. `FormContentFilter`： 表单内容Filter，GET（数据放URL后面）、POST（数据放请求体）请求可以携带数据，PUT、DELETE 的请求体数据会被忽略

1. 给容器中放了`WebMvcConfigurer`组件；给SpringMVC添加各种定制功能

1. 1. 所有的功能最终会和配置文件进行绑定
   2. WebMvcProperties： `spring.mvc`配置文件
   3. WebProperties： `spring.web`配置文件

```java
	@Configuration(proxyBeanMethods = false)
	@Import(EnableWebMvcConfiguration.class) //额外导入了其他配置
	@EnableConfigurationProperties({ WebMvcProperties.class, WebProperties.class })
	@Order(0)
	public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer, ServletContextAware{
        
    }
```



## 3. WebMvcConfigurer接口

提供了配置SpringMVC底层的所有组件入口

![image-20230708100656878](spring boot3.assets/image-20230708100656878.png)

##  4. addResourceHandlers源码

**静态资源规则**

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    //1、
    addResourceHandler(registry, this.mvcProperties.getWebjarsPathPattern(),
            "classpath:/META-INF/resources/webjars/");
    addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
        registration.addResourceLocations(this.resourceProperties.getStaticLocations());
        if (this.servletContext != null) {
            ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
            registration.addResourceLocations(resource);
        }
    });
}
```

1. 规则一：访问： `/webjars/**`路径就去 `classpath:/META-INF/resources/webjars/`下找资源.

1. 1. maven 导入依赖

1. 规则二：访问： `/**`路径就去 `静态资源默认的四个位置找资源`

1. 1. `classpath:/META-INF/resources/`
   2. `classpath:/resources/`
   3. `classpath:/static/`
   4. `classpath:/public/`

1. 规则三：**静态资源默认都有缓存规则的设置**

1. 1. 所有缓存的设置，直接通过**配置文件**： `spring.web`
   2. cachePeriod： 缓存周期； 多久不用找服务器要新的。 默认没有，以s为单位
   3. cacheControl： **HTTP缓存**控制；[https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching#概览)
   4. **useLastModified**：是否使用最后一次修改。配合HTTP Cache规则

如果浏览器访问了一个静态资源 `index.js`，如果服务这个资源没有发生变化，下次访问的时候就可以直接让浏览器用自己缓存中的东西，而不用给服务器发请求。

```java
registration.setCachePeriod(getSeconds(this.resourceProperties.getCache().getPeriod()));
registration.setCacheControl(this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl());
registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
```

![image-20230708102630887](spring boot3.assets/image-20230708102630887.png)

## 5. EnableWebMvcConfiguration 源码

```java
//SpringBoot 给容器中放 WebMvcConfigurationSupport 组件。
//我们如果自己放了 WebMvcConfigurationSupport 组件，Boot的WebMvcAutoConfiguration都会失效。
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebProperties.class)
public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration implements ResourceLoaderAware 
{

    
}
```

1. `HandlerMapping`： 根据请求路径 ` /a` 找那个handler能处理请求

1. 1. `WelcomePageHandlerMapping`： 

1. 1. 1. 访问 `/**`路径下的所有请求，都在以前四个静态资源路径下找，欢迎页也一样
      2. 找`index.html`只要静态资源的位置有一个 `index.html`页面，项目启动默认访问 





## 6. 为什么容器中放一个`WebMvcConfigurer`就能配置底层行为

1. WebMvcAutoConfiguration 是一个自动配置类，它里面有一个 `EnableWebMvcConfiguration`
2. `EnableWebMvcConfiguration`继承与 `DelegatingWebMvcConfiguration`，这两个都生效
3. `DelegatingWebMvcConfiguration`利用 DI 把容器中 所有 `WebMvcConfigurer `注入进来
4. 别人调用 ``DelegatingWebMvcConfiguration`` 的方法配置底层规则，而它调用所有 `WebMvcConfigurer`的配置底层方法。



## 7. WebMvcConfigurationSupport

提供了很多的默认设置。

判断系统中是否有相应的类：如果有，就加入相应的`HttpMessageConverter`

```java
jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) &&
				ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
jackson2SmilePresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
```



# 1. Web场景

## 1. 自动配置

1、整合web场景

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

2、引入了 `autoconfigure`功能

3、`@EnableAutoConfiguration`注解使用`@Import(AutoConfigurationImportSelector.class)`批量导入组件

4、加载 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 文件中配置的所有组件

5、所有自动配置类如下

```plain
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration
====以下是响应式web场景和现在的没关系======
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration
================以上没关系=================
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
```



6、绑定了配置文件的一堆配置项

- 1、SpringMVC的所有配置 `spring.mvc`
- 2、Web场景通用配置 `spring.web`
- 3、文件上传配置 `spring.servlet.multipart`
- 4、服务器的配置 `server`: 比如：编码方式



## 2. 默认效果

默认配置：

1. 包含了 ContentNegotiatingViewResolver 和 BeanNameViewResolver 组件，**方便视图解析**
2. **默认的静态资源处理机制**： 静态资源放在 static 文件夹下即可直接访问
3. **自动注册**了 **Converter**,GenericConverter,**Formatter**组件，适配常见**数据类型转换**和**格式化需求**
4. **支持** **HttpMessageConverters**，可以**方便返回**json等**数据类型**
5. **注册** MessageCodesResolver，方便**国际化**及错误消息处理
6. **支持 静态** index.html
7. **自动使用**ConfigurableWebBindingInitializer，实现消息处理、数据绑定、类型转化、数据校验等功能

**重要：**

- *如果想保持* ***boot mvc 的默认配置****，并且自定义更多的 mvc 配置，如：****interceptors\****,* ***formatters\****,* ***view controllers\*** *等。可以使用**@Configuration**注解添加一个* *WebMvcConfigurer* *类型的配置类，并不要标注* *@EnableWebMvc*
- *如果想保持 boot mvc 的默认配置，但要自定义核心组件实例，比如：**RequestMappingHandlerMapping**,* *RequestMappingHandlerAdapter**, 或**ExceptionHandlerExceptionResolver**，给容器中放一个* *WebMvcRegistrations* *组件即可*
- *如果想全面接管 Spring MVC，**@Configuration* *标注一个配置类，并加上* *@EnableWebMvc**注解，实现* *WebMvcConfigurer* *接口*

# 2. 静态资源

## 1. 默认规则

### 1. 静态资源映射

静态资源映射规则在 WebMvcAutoConfiguration 中进行了定义：

1. /webjars/** 的所有路径 资源都在 classpath:/META-INF/resources/webjars/
2. /** 的所有路径 资源都在 classpath:/META-INF/resources/、classpath:/resources/、classpath:/static/、classpath:/public/
3. 所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值
1. 1. period： 缓存间隔。 默认 0S；
   2. cacheControl：缓存控制。 默认无；
   3. useLastModified：是否使用lastModified头。 默认 false；

### 2. 静态资源缓存

1. 所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值

1. 1. period： 缓存间隔。 默认 0S；
   2. cacheControl：缓存控制。 默认无；
   3. useLastModified：是否使用lastModified头。 默认 false；

### 3. 欢迎页

欢迎页规则在 WebMvcAutoConfiguration 中进行了定义：

+ 在**静态资源**目录下找 index.html
+ 没有就在 templates下找index模板页

###  4. Favicon

网页图标:  在静态资源目录下找 favicon.ico

### 5. 缓存实验

```Properties
server.port=9000

#1、spring.web：
# 1.配置国际化的区域信息
# 2.静态资源策略(开启、处理链、缓存)

#开启静态资源映射规则
spring.web.resources.add-mappings=true

#设置缓存
#spring.web.resources.cache.period=3600
##缓存详细合并项控制，覆盖period配置：
## 浏览器第一次请求服务器，服务器告诉浏览器此资源缓存7200秒，7200秒以内的所有此资源访问不用发给服务器请求，7200秒以后发请求给服务器
spring.web.resources.cache.cachecontrol.max-age=7200
#使用资源 last-modified 时间，来对比服务器和浏览器的资源是否相同没有变化。相同返回 304
spring.web.resources.cache.use-last-modified=true
```

## 2. 自定义静态资源规则

自定义静态资源路径、自定义缓存规则

###  配置方式

`spring.mvc`： 静态资源访问前缀路径

`spring.web`：

- 静态资源目录
- 静态资源缓存策略

```Properties
#1、spring.web：
# 1.配置国际化的区域信息
# 2.静态资源策略(开启、处理链、缓存)

#开启静态资源映射规则
spring.web.resources.add-mappings=true

#设置缓存
spring.web.resources.cache.period=3600
##缓存详细合并项控制，覆盖period配置：
## 浏览器第一次请求服务器，服务器告诉浏览器此资源缓存7200秒，7200秒以内的所有此资源访问不用发给服务器请求，7200秒以后发请求给服务器
spring.web.resources.cache.cachecontrol.max-age=7200
## 共享缓存
spring.web.resources.cache.cachecontrol.cache-public=true
#使用资源 last-modified 时间，来对比服务器和浏览器的资源是否相同没有变化。相同返回 304
spring.web.resources.cache.use-last-modified=true

#自定义静态资源文件夹位置
spring.web.resources.static-locations=classpath:/a/,classpath:/b/,classpath:/static/

#2、 spring.mvc
## 2.1. 自定义webjars路径前缀
spring.mvc.webjars-path-pattern=/wj/**
## 2.2. 静态资源访问路径前缀
spring.mvc.static-path-pattern=/static/**
```

### 代码方式

- 容器中只要有一个 WebMvcConfigurer 组件。配置的底层行为都会生效
- @EnableWebMvc //禁用boot的默认配置

```java
@Configuration //这是一个配置类
public class MyConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //保留以前规则
        //自己写新的规则。
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/a/","classpath:/b/")
                .setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
    }
}
```



```Java
@Configuration //这是一个配置类,给容器中放一个 WebMvcConfigurer 组件，就能自定义底层
public class MyConfig  /*implements WebMvcConfigurer*/ {


    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/a/", "classpath:/b/")
                        .setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
            }
        };
    }

}
```





# 3. 路径匹配

**Spring5.3** 之后加入了更多的请求路径匹配的实现策略；

以前只支持 AntPathMatcher 策略, 现在提供了 **PathPatternParser** 策略。并且可以让我们指定到底使用那种策略。

## 1. Ant风格路径用法

Ant 风格的路径模式语法具有以下规则：

- *：表示**任意数量**的字符。
- ?：表示任意**一个字符**。
- ** ：表示**任意数量的目录**。
- {}：表示一个命名的模式**占位符**。
- []：表示**字符集合**，例如[a-z]表示小写字母。

例如：

- *.html 匹配任意名称，扩展名为.html的文件。
- /folder1/*/*.java 匹配在folder1目录下的任意两级目录下的.java文件。
- /folder2/**/*.jsp 匹配在folder2目录下任意目录深度的.jsp文件。
- /{type}/{id}.html 匹配任意文件名为{id}.html，在任意命名的{type}目录下的文件。

注意：Ant 风格的路径模式语法中的特殊字符需要转义，如：

- 要匹配文件路径中的星号，则需要转义为\\*。
- 要匹配文件路径中的问号，则需要转义为\\?。

## 2. 模式切换

AntPathMatcher 与 PathPatternParser

+ PathPatternParser 在 jmh 基准测试下，有 6~8 倍吞吐量提升，降低 30%~40%空间分配率
+ PathPatternParser 兼容 AntPathMatcher语法，并支持更多类型的路径模式
+ PathPatternParser =="**" 多段匹配的支持仅允许在模式末尾使用==

```java
    @GetMapping("/a*/b?/{p1:[a-f]+}")
    public String hello(HttpServletRequest request, 
                        @PathVariable("p1") String path) {

        log.info("路径变量p1： {}", path);
        //获取请求路径
        String uri = request.getRequestURI();
        return uri;
    }
```

![image-20230708104510232](spring boot3.assets/image-20230708104510232.png)

## 3. 总结

+ 使用默认的路径匹配规则(PathPatternParser规则)

+ 如果路径中间需要有 **，替换成ant风格路径

  ```Properties
  # 改变路径匹配策略：
  # ant_path_matcher 老版策略；
  # path_pattern_parser 新版策略；
  spring.mvc.pathmatch.matching-strategy=ant_path_matcher
  ```

  

# 4. 内容协商

**一套系统适配多端数据返回**

<img src="spring boot3.assets/1681217799861-dde49224-a767-489b-80b7-7d8d503e33cf.png" alt="img" style="zoom: 80%;" />

## 1.  多端内容适配

### 1. 默认规则

1. **SpringBoot 多端内容适配**。

1. 1. **基于请求头内容协商**：（默认开启）

1. 1. 1. 客户端向服务端发送请求，携带HTTP标准的**Accept请求头**。

1. 1. 1. 1. **Accept**: `application/json`、`text/xml`、`text/yaml`
         2. 服务端根据客户端**请求头期望的数据类型**进行**动态返回**

      2. <img src="spring boot3.assets/image-20230708105033059.png" alt="image-20230708105033059" style="zoom: 80%;" />

1. 1. **基于请求参数内容协商**：（需要开启）

1. 1. 1. 发送请求 GET /projects/spring-boot?format=json 
      2. 匹配到 @GetMapping("/projects/spring-boot") 
      3. 根据**参数协商**，优先返回 json 类型数据【**需要开启参数匹配设置**】
      4. 发送请求 GET /projects/spring-boot?format=xml,优先返回 xml 类型数据

### 2. 效果演示

请求同一个接口，可以返回json和xml不同格式数据

```java
    /**
     * 1、默认支持把对象写为json。因为默认web场景导入了jackson处理json的包;jackson-core
     * 2、jackson也支持把数据写为xml。导入xml相关依赖
     * @return
     */
    @GetMapping("/person")
    public Person person(/*@RequestBody Person person*/){
        Person person = new Person();
        person.setId(1L);
        person.setUserName("张三");
        person.setEmail("aaa@qq.com");
        person.setAge(18);
        return person;
    }
```

1. 引入支持写出xml内容依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

2. 标注注解

```java
@JacksonXmlRootElement  // 可以写出为xml文档
@Data
public class Person {
    private Long id;
    private String userName;
    private String email;
    private Integer age;
}
```

3. 开启基于请求参数的内容协商

```properties
# 开启基于请求参数的内容协商功能。 默认参数名：format。 默认此功能不开启
spring.mvc.contentnegotiation.favor-parameter=true
# 指定内容协商时使用的参数名。默认是 format
spring.mvc.contentnegotiation.parameter-name=type
```

4. 效果

![img](spring boot3.assets/1681220124448-e8611612-97bc-4823-9b00-20dd9d579abf.png)![image.png](spring boot3.assets/1681220145378-86fabd90-a78c-4f60-9efa-eb2960915832.png)

### 3. 配置协商规则与支持类型

1.修改**内容协商方式**

```properties
#使用参数进行内容协商
spring.mvc.contentnegotiation.favor-parameter=true  
#自定义参数名，默认为format
spring.mvc.contentnegotiation.parameter-name=myparam 
```

2.大多数 MediaType 都是开箱即用的。也可以**自定义内容类型，如：**

```properties
spring.mvc.contentnegotiation.media-types.yaml=text/yaml
```

## 2. 自定义内容返回

### 1. 增加yaml返回支持

导入依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
```

把对象写出成YAML

```java
    public static void main(String[] args) throws JsonProcessingException {
        Person person = new Person();
        person.setId(1L);
        person.setUserName("张三");
        person.setEmail("aaa@qq.com");
        person.setAge(18);

        
        //把对象写出成YAML
        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);

        String s = mapper.writeValueAsString(person);
        System.out.println(s);
    }
```

编写配置

```properties
#新增一种媒体类型
spring.mvc.contentnegotiation.media-types.yaml=text/yaml
```

增加`HttpMessageConverter`组件，专门负责把对象写出为yaml格式

```java
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override //配置一个能把对象转为yaml的messageConverter
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyYamlHttpMessageConverter());
            }
        };
    }
```

### 3. 写HttpMessageConverter

```java
public class MyYamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private ObjectMapper objectMapper = null; //把对象转成yaml

    public MyYamlHttpMessageConverter(){
        //告诉SpringBoot这个MessageConverter支持哪种媒体类型  //媒体类型
        //spring.mvc.contentnegotiation.media-types.yaml=text/yaml
        super(new MediaType("text", "yaml", Charset.forName("UTF-8")));
        
        YAMLFactory factory = new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        this.objectMapper = new ObjectMapper(factory);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        //只要是对象类型，不是基本类型
        return true;
    }spring.mvc.contentnegotiation.media-types.yaml=text/yaml

    @Override  //@RequestBody
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override //@ResponseBody 把对象怎么写出去
    protected void writeInternal(Object methodReturnValue, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        //try-with写法，自动关流
        try(OutputStream os = outputMessage.getBody()){
            this.objectMapper.writeValue(os,methodReturnValue);
        }

    }
}
```





## 3. 内容协商原理

`HttpMessageConverter`

- `HttpMessageConverter` 怎么工作？合适工作？
- 定制 `HttpMessageConverter`  来实现多端内容协商
- 编写`WebMvcConfigurer`提供的`configureMessageConverters`底层，修改底层的`MessageConverter`

1. `@ResponseBody`由`HttpMessageConverter`处理

标注了`@ResponseBody`的返回值 将会由支持它的 `HttpMessageConverter`写给浏览器

1. 如果controller方法的返回值标注了 `@ResponseBody `注解

1. 1. 请求进来先来到`DispatcherServlet`的`doDispatch()`进行处理
   2. 找到一个 `HandlerAdapter `适配器。利用适配器执行目标方法
   3. `RequestMappingHandlerAdapter`来执行，调用`invokeHandlerMethod（）`来执行目标方法
   4. 目标方法执行之前，准备好两个东西

1. 1. 1. `HandlerMethodArgumentResolver`：参数解析器，确定目标方法每个参数值
      2. `HandlerMethodReturnValueHandler`：返回值处理器，确定目标方法的返回值改怎么处理

1. 1. `RequestMappingHandlerAdapter` 里面的`invokeAndHandle()`真正执行目标方法
   2. 目标方法执行完成，会返回**返回值对象**
   3. **找到一个合适的返回值处理器** `HandlerMethodReturnValueHandler`
   4. 最终找到 `RequestResponseBodyMethodProcessor`能处理 标注了 `@ResponseBody`注解的方法
   5. `RequestResponseBodyMethodProcessor` 调用`writeWithMessageConverters `,利用`MessageConverter`把返回值写出去

上面解释：`@ResponseBody`由`HttpMessageConverter`处理

2`HttpMessageConverter` 会**先进行内容协商**

1. 1. 遍历所有的`MessageConverter`看谁支持这种**内容类型的数据**
   2. 默认`MessageConverter`有以下
   3. ![img](spring boot3.assets/1681275459547-89d8d651-b52f-4d47-bff9-6db123624424.png)
   4. 最终因为要`json`所以`MappingJackson2HttpMessageConverter`支持写出json
   5. jackson用`ObjectMapper`把对象写出去



2. `WebMvcAutoConfiguration`提供几种默认`HttpMessageConverters`

- `EnableWebMvcConfiguration`通过 `addDefaultHttpMessageConverters`添加了默认的`MessageConverter`；如下：

- - `ByteArrayHttpMessageConverter`： 支持字节数据读写
  - `StringHttpMessageConverter`： 支持字符串读写
  - `ResourceHttpMessageConverter`：支持资源读写
  - `ResourceRegionHttpMessageConverter`: 支持分区资源写出
  - `AllEncompassingFormHttpMessageConverter`：支持表单xml/json读写
  - `MappingJackson2HttpMessageConverter`： 支持请求响应体Json读写

默认8个：

![img](spring boot3.assets/1681302411019-0c0425aa-6679-4b2b-a456-b31c151c6e83.png)

系统提供默认的MessageConverter 功能有限，仅用于json或者普通返回数据。额外增加新的内容协商功能，必须增加新的`HttpMessageConverter`



# 5. ==Thymeleaf模板引擎(页面)==

由于 **SpringBoot** 使用了**嵌入式 Servlet 容器**。所以 **JSP** 默认是**不能使用**的。

如果需要**服务端页面渲染**，优先考虑使用 模板引擎。

两种模式:

1. `前后分离模式`： `@RestController `响应JSON数据

2. `前后不分离模式`：@Controller + Thymeleaf模板引擎

==模板引擎页面默认放在 src/main/resources/templates==

**SpringBoot** 包含以下模板引擎的自动配置

- FreeMarker
- Groovy
- **Thymeleaf**
- Mustache

**Thymeleaf官网**：https://www.thymeleaf.org/ 

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<title>Good Thymes Virtual Grocery</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" media="all" th:href="@{/css/gtvg.css}" />
</head>
<body>
	<p th:text="#{home.welcome}">Welcome to our grocery store!</p>
</body
</html>
```

## 1. Thymeleaf 简介

Thymeleaf 是新一代 Java 模板引擎，与 Velocity、FreeMarker 等传统 Java 模板引擎不同，Thymeleaf 支持 HTML 原型，其文件后缀为“.html”，因此它可以直接被浏览器打开，此时浏览器会忽略未定义的 Thymeleaf 标签属性，展示 thymeleaf 模板的静态页面效果；当通过 Web 应用程序访问时，Thymeleaf 会动态地替换掉静态内容，使页面动态显示。

## 1. Thymeleaf整合

<img src="spring boot3.assets/image-20230925213352491.png" alt="image-20230925213352491" style="zoom: 67%;" />

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

ps:  刷新meavn

自动配置原理

1. 开启了 org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration 自动配置
2. 属性绑定在 ThymeleafProperties 中，对应配置文件 spring.thymeleaf 内容
3. 所有的模板页面默认在 `classpath:/templates`文件夹下
4. 默认效果
1. 1. **所有的模板页面在 `classpath:/templates/`下面找**
   2. 找后缀名为`.html`的页面

```properties
# 配置thymeleaf场景
#spring.thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
#开发期间关闭，上线以后开启
spring.thymeleaf.cache=false
#spring.thymeleaf.check-template=false
```

## 2. 基础语法

在HTML中添加xmlns:th="http://www.thymeleaf.org" 

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
```

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

### 1. 核心用法

`**th:xxx**`**：动态渲染指定的 html 标签属性值、或者th指令（遍历、判断等）**

- `th:text`：标签体内文本值渲染

- - `th:utext`：不会转义，显示为html原本的样子。

- `th:属性`：标签指定属性渲染
- `th:attr`：标签任意属性渲染
- `th:if``th:each``...`：其他th指令
- 例如：

```html
<p th:text="${content}">原内容</p>
<a th:href="${url}">登录</a>
<img src="../../images/gtvglogo.png" 
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```

`表达式`**：用来动态取值**

- `${}`**：变量取值；使用model共享给页面的值都直接用${}**
- `@{}`**：url路径；**
- `#{}`：国际化消息
- `~{}`：片段引用
- `*{}`：变量选择：需要配合th:object绑定对象



例子:

```xml
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>你好：<span th:text="${msg}"></span></h1>

<hr/>
th:text： 替换标签体的内容； 会转义
th:utext: 替换标签体的内容; 不会转义html标签，真正显示为html该有的样式
<h1 th:text="${msg}">哈哈</h1>
<h1 th:utext="${msg}">呵呵</h1>
<hr/>
<hr/>
th: 任意html属性； 动态替换任意属性的值
<img th:src="@{${imgUrl}}" src="1.jpg" style="width:300px;"/>
<br/>
th:attr：任意属性指定
<img src="1.jpg" style="width:300px;" th:attr="src=${imgUrl},style=${style}"/>
<br/>
th：其他指令
<img th:src="@{${imgUrl}}" th:style="${style}" th:if="${show}">


<br/>
2.jpg  @{} 专门用来取各种路径
<img src="/static/2.jpg" style="width:300px;" th:src="@{/static/2.jpg}">

3. 使用工具类
转大写
<h1 th:text="${#strings.toUpperCase(name)}"></h1>
<h1 th:text="${'前缀：'+name+'后缀'}"></h1>
拼串
<h1 th:text="|前缀哈哈 ${name} 后缀呵呵|"></h1>
</body>
</html>
```



```java
package com.atguigu.web.controller;

import com.atguigu.web.bean.Person;
import com.atguigu.web.service.AService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * @author lfy
 * @Description
 * @create 2023-04-13 16:37
 */
@Controller //适配 服务端渲染   前后不分离模式开始
public class WelcomeController {

    @Autowired
    AService aService;

    /**
     * 利用模板引擎跳转到指定页面
     * @return
     */
    @GetMapping("/well")
    public String hello(@RequestParam("name") String name,
                        Model model, HttpServletRequest request){
				// 页面发送一个参数name,比如localhost:9000/well?name=李四


        //把需要给页面共享的数据放到model中
        String text = "<span style='color:red'>"+name+"</span>";
        model.addAttribute("msg",text);


        model.addAttribute("name",name);

        aService.a();

        //路径是动态的
        model.addAttribute("imgUrl","/4.jpg");
        //数据库查出的样式
        model.addAttribute("style","width: 400px");

        model.addAttribute("show",false);

        //模板的逻辑视图名
        //物理视图 =  前缀 + 逻辑视图名 + 后缀
        //真实地址 = classpath:/templates/welcome.html
        return "welcome";
    }
}

```

**系统工具&内置对象：**[**详细文档**](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#appendix-a-expression-basic-objects)

- `param`：请求参数对象
- `session`：session对象
- `application`：application对象
- `#execInfo`：模板执行信息
- `#messages`：国际化消息
- `#uris`：uri/url工具
- `#conversions`：类型转换工具
- `#dates`：日期工具，是`java.util.Date`对象的工具类
- `#calendars`：类似#dates，只不过是`java.util.Calendar`对象的工具类
- `#temporals`： JDK8+ `**java.time**` API 工具类
- `#numbers`：数字操作工具
- `#strings`：字符串操作
- `#objects`：对象操作
- `#bools`：bool操作
- `#arrays`：array工具
- `#lists`：list工具
- `#sets`：set工具
- `#maps`：map工具
- `#aggregates`：集合聚合工具（sum、avg）
- `#ids`：id生成工具

### 2. 语法示例

**表达式：**

- 变量取值：${...}
- url 取值：@{...}
- 国际化消息：#{...}
- 变量选择：*{...}
- 片段引用: ~{...}

**常见：**

- 文本： 'one text'，'another one!',...
- 数字： 0,34,3.0,12.3,...
- 布尔：true、false
- null: null
- 变量名： one,sometext,main...

**文本操作：**

- 拼串： +

- 文本替换：| The name is ${name} |

  ```xml
  拼串
  <h1 th:text="|前缀哈哈 ${name} 后缀呵呵|"></h1>
  ```

  

**布尔操作：**

- 二进制运算： and,or
- 取反：!,not

**比较运算：**

- 比较：>，<，<=，>=（gt，lt，ge,le）
- 等值运算：==,!=（eq，ne）

**条件运算：**

- if-then： (if)?(then)
- if-then-else: (if)?(then):(else)
- default: (value)?:(defaultValue)

**特殊语法：**

- 无操作：_

**所有以上都可以嵌套组合**

```plain
'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
```



## 3. 属性设置

1. th:href="@{/product/list}"
2. th:attr="class=${active}"
3. th:attr="src=@{/images/gtvglogo.png},title=${logo},alt=#{logo}"  //批量设置属性
4. th:checked="${user.active}"

```html
<p th:text="${content}">原内容</p>
<a th:href="${url}">登录</a>
<img src="../../images/gtvglogo.png" 
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```

## 4. 遍历

语法：  `th:each="元素名,迭代状态 : ${集合}"`

```html
<tr th:each="prod : ${prods}">
  <td th:text="${prod.name}">Onions</td>
  <td th:text="${prod.price}">2.41</td>
  <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>

// 加上迭代状态
<tr th:each="prod,iterStat : ${prods}" th:class="${iterStat.odd}? 'odd'">
  <td th:text="${prod.name}">Onions</td>
  <td th:text="${prod.price}">2.41</td>
  <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>
```

iterStat 有以下属性：

- index：当前遍历元素的索引，从0开始
- count：当前遍历元素的索引，从1开始
- size：需要遍历元素的总数量
- current：当前正在遍历的元素对象
- even/odd：是否偶数/奇数行
- first：是否第一个元素
- last：是否最后一个元素

```java
th:each属性用于迭代循环，语法：th:each="obj,iterStat:${objList}"
迭代对象可以是Java.util.List,java.util.Map,数组等;
iterStat 称作状态变量，属性有：
    index:当前迭代对象的index（从0开始计算）
    count: 当前迭代对象的index(从1开始计算)
    size:被迭代对象的大小
    current:当前迭代变量
    even/odd:布尔值，当前循环是否是偶数/奇数（从0开始计算）
    first:布尔值，当前循环是否是第一个
    last:布尔值，当前循环是否是最后一个
```

```html
<ol>  
       <li>List循环：  
            <table border="1">  
              <tr>  
                <th>用户名</th>  
                <th>邮箱</th>  
                <th>管理员</th>  
                <th>状态变量：index</th>  
                <th>状态变量：count</th>  
                <th>状态变量：size</th>  
                <th>状态变量：current.userName</th>  
                <th>状态变量：even</th>  
                <th>状态变量：odd</th>  
                <th>状态变量：first</th>  
                <th>状态变量：last</th>  
              </tr>  
              <tr  th:each="user,userStat : ${list}">  
                <td th:text="${user.userName}">Onions</td>  
                <td th:text="${user.email}">test@test.com.cn</td>  
                <td th:text="${user.isAdmin}">yes</td>  
                 <th th:text="${userStat.index}">状态变量：index</th>  
                <th th:text="${userStat.count}">状态变量：count</th>  
                <th th:text="${userStat.size}">状态变量：size</th>  
                <th th:text="${userStat.current.userName}">状态变量：current</th>  
                <th th:text="${userStat.even}">状态变量：even****</th>  
                <th th:text="${userStat.odd}">状态变量：odd</th>  
                <th th:text="${userStat.first}">状态变量：first</th>  
                <th th:text="${userStat.last}">状态变量：last</th>  
				<a th:href="@{/kickout(username=${user.username})}">下线</a>
              </tr>  
            </table>  
        </li>  
        <li>Map循环：  
            <div th:each="mapS:${map}">  
            <div th:text="${mapS}"></div>  
            </div>  
        </li>  
        <li>数组循环：  
            <div th:each="arrayS:${arrays}">  
            <div th:text="${arrayS}"></div>  
            </div>  
        </li>  
        </ol>
```

## 5. 判断

### th:if

```html
<a
  href="comments.html"
  th:href="@{/product/comments(prodId=${prod.id})}"
  th:if="${not #lists.isEmpty(prod.comments)}"
  >view</a
```



### th:switch

```html
<div th:switch="${user.role}">
  <p th:case="'admin'">User is an administrator</p>
  <p th:case="#{roles.manager}">User is a manager</p>
  <p th:case="*">User is some other thing</p>
</div>
```





![image-20230708141942361](spring boot3.assets/image-20230708141942361.png)

## 6. 属性优先级

- 片段
- 遍历
- 判断

```html
<ul>
  <li th:each="item : ${items}" th:text="${item.description}">Item description here...</li>
</ul>
```

| Order | Feature          | Attributes                           |
| ----- | ---------------- | ------------------------------------ |
| 1     | 片段包含         | th:insert th:replace                 |
| 2     | 遍历             | th:each                              |
| 3     | 判断             | th:if th:unless th:switch th:case    |
| 4     | 定义本地变量     | th:object th:with                    |
| 5     | 通用方式属性修改 | th:attr th:attrprepend th:attrappend |
| 6     | 指定属性修改     | th:value th:href th:src ...          |
| 7     | 文本值           | th:text th:utext                     |
| 8     | 片段指定         | th:fragment                          |
| 9     | 片段移除         | th:remove                            |

## 7. 行内写法

```
[[...]] or [(...)]
<p>Hello, [[${session.user.name}]]!</p>
```



## 8. 变量选择

```html
<div th:object="${session.user}">
  <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
</div>
```

等同于

```html
<div>
  <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
</div
```



## 9. 模板布局

- 定义模板： `th:fragment`
- 引用模板：`~{templatename::selector}`
- 插入模板：`th:insert`、`th:replace`

```html
<footer th:fragment="copy">&copy; 2011 The Good Thymes Virtual Grocery</footer>

<body>
  <div th:insert="~{footer :: copy}"></div>
  <div th:replace="~{footer :: copy}"></div>
</body>
<body>
  结果：
  <body>
    <div>
      <footer>&copy; 2011 The Good Thymes Virtual Grocery</footer>
    </div>

    <footer>&copy; 2011 The Good Thymes Virtual Grocery</footer>
  </body>
</body>
```



## 10. devtools工具

```xml
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
      </dependency>
```

修改页面后；`ctrl+F9`刷新效果, 不用重复启动代码；

**注意: java代码的修改，如果`devtools`热启动了，可能会引起一些bug，难以排查**







# 6. 国际化

国际化的自动配置参照`MessageSourceAutoConfiguration`



**实现步骤**：

1. Spring Boot 在类路径根下查找messages资源绑定文件。文件名为：messages.properties
2. 多语言可以定义多个消息文件，命名为`messages_区域代码.properties`。如：

1. 1. `messages.properties`：默认
   2. `messages_zh_CN.properties`：中文环境
   3. `messages_en_US.properties`：英语环境

2. ```
   
   ```

在**程序中**可以==自动注入== `MessageSource`组件，获取国际化的配置项值

1. 在**页面中**可以使用表达式 ` #{}`获取国际化的配置项值

```java
    @Autowired  //国际化取消息用的组件
    MessageSource messageSource;
    @GetMapping("/haha")
    public String haha(HttpServletRequest request){

        Locale locale = request.getLocale();
        //利用代码的方式获取国际化配置文件中指定的配置项的值
        String login = messageSource.getMessage("login", null, locale);
        return login;
    }
```

# 7. 错误处理

## 1. 默认机制

**错误处理的自动配置**都在`ErrorMvcAutoConfiguration`中，两大核心机制：

- \1. SpringBoot 会**自适应****处理错误**，**响应页面**或**JSON数据**
- \2. **SpringMVC的错误处理机制**依然保留，**MVC处理不了**，才会**交给boot进行处理**

![img](spring boot3.assets/1681723795095-828d2034-1e6c-4d98-8e47-573dd6b5463b.svg)



**发生错误以后，转发给/error.xml**，SpringBoot在底层写好一个 BasicErrorController的组件，专门处理这个请求

```java
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE) //返回HTML
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections
			.unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
	}

	@RequestMapping  //返回 ResponseEntity, JSON
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		if (status == HttpStatus.NO_CONTENT) {
			return new ResponseEntity<>(status);
		}
		Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
		return new ResponseEntity<>(body, status);
	}
```

- 错误页面是这么解析到的

```java
//1、解析错误的自定义视图地址
ModelAndView modelAndView = resolveErrorView(request, response, status, model);
//2、如果解析不到错误页面的地址，默认的错误页就是 error
return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
```



容器中专门有一个错误视图解析器

```java
@Bean
@ConditionalOnBean(DispatcherServlet.class)
@ConditionalOnMissingBean(ErrorViewResolver.class)
DefaultErrorViewResolver conventionErrorViewResolver() {
    return new DefaultErrorViewResolver(this.applicationContext, this.resources);
}
```

SpringBoot解析自定义错误页的默认规则

```java
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		ModelAndView modelAndView = resolve(String.valueOf(status.value()), model);
		if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
			modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
		}
		return modelAndView;
	}

	private ModelAndView resolve(String viewName, Map<String, Object> model) {
		String errorViewName = "error/" + viewName;
		TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName,
				this.applicationContext);
		if (provider != null) {
			return new ModelAndView(errorViewName, model);
		}
		return resolveResource(errorViewName, model);
	}

	private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
		for (String location : this.resources.getStaticLocations()) {
			try {
				Resource resource = this.applicationContext.getResource(location);
				resource = resource.createRelative(viewName + ".html");
				if (resource.exists()) {
					return new ModelAndView(new HtmlResourceView(resource), model);
				}
			}
			catch (Exception ex) {
			}
		}
		return null;
	}
```

容器中有一个默认的名为 error 的 view； 提供了默认白页功能

```java
@Bean(name = "error")
@ConditionalOnMissingBean(name = "error")
public View defaultErrorView() {
    return this.defaultErrorView;
}
```

封装了JSON格式的错误信息

```java
	@Bean
	@ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
	public DefaultErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes();
	}
```



规则：

1. **解析一个错误页**

1. 1. 如果发生了500、404、503、403 这些错误

1. 1. 1. 如果有**模板引擎**，默认在 `classpath:/templates/error/精确码.html`
      2. 如果没有模板引擎，在静态资源文件夹下找  `精确码.html`

1. 1. 如果匹配不到`精确码.html`这些精确的错误页，就去找`5xx.html`，`4xx.html`**模糊匹配**

1. 1. 1. 如果有模板引擎，默认在 `classpath:/templates/error/5xx.html`
      2. 如果没有模板引擎，在静态资源文件夹下找  `5xx.html`

2. 如果模板引擎路径`templates`下有 `error.html`页面，就直接渲染



## 2. 自定义错误响应

### 1. 自定义json响应

使用@ControllerAdvice + @ExceptionHandler 进行统一异常处理

### 2. 自定义页面响应

根据boot的错误页面规则，自定义页面模板

## 3. 应用

- **前后分离**

- - 后台发生的所有错误，`@ControllerAdvice + @ExceptionHandler`进行统一异常处理。

    ```java
    package com.atguigu.web.handler;
    
    @ControllerAdvice //这个类是集中处理所有 @Controller 发生的错误
    public class GlobalExceptionHandler {
        /**
         * GlobalExceptionHandler 全局错误类
         * 1、@ExceptionHandler 标识一个方法处理错误，默认只能处理这个类发生的指定错误
         * 2、@ControllerAdvice 统一处理所有错误
         * @param e
         * @return
         */
        @ResponseBody //对象写出为json
        @ExceptionHandler(Exception.class)
        public String handleException(Exception e){
    
            return "Ohho~~~统一处理，原因："+e.getMessage();
        }
    }
    ```

- **前后不分离**(服务端页面渲染)

- - **不可预知的一些，HTTP码表示的服务器或客户端错误**

- - - 给`classpath:/templates/error/`下面，放常用精确的错误码页面。`500.html`，`404.html`
    - 给`classpath:/templates/error/`下面，放通用模糊匹配的错误码页面。 `5xx.html`(服务端错误)，`4xx.html`(客户端错误)

- - **发生业务错误**

- - - **核心业务**，每一种错误，都应该代码控制，**跳转到自己定制的错误页**。
    - **通用业务**，`classpath:/templates/error.html`页面，**显示错误信息**。

![image-20230708150117388](spring boot3.assets/image-20230708150117388.png)

页面，JSON，可用的Model数据如下

![img](spring boot3.assets/1681724501227-077073b7-349d-414f-8916-a822eb86c772.png)

```
取出错误信息例子:
错误堆栈：[[${trace}]]
```



# 8. 嵌入式容器(服务器)

**Servlet容器**：管理、运行**Servlet组件**（Servlet、Filter、Listener）的环境，一般指**服务器**

## 1. 自动配置原理

- SpringBoot 默认嵌入Tomcat作为Servlet容器。
- **自动配置类**是`ServletWebServerFactoryAutoConfiguration`，`EmbeddedWebServerFactoryCustomizerAutoConfiguration`
- 自动配置类开始分析功能。`xxxxAutoConfiguration`

```java
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(ServerProperties.class)
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
		ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
		ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
		ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
public class ServletWebServerFactoryAutoConfiguration {
    
}
```

1. `ServletWebServerFactoryAutoConfiguration` 自动配置了嵌入式容器场景
2. 绑定了`ServerProperties`配置类，所有和服务器有关的配置 `server`
3. `ServletWebServerFactoryAutoConfiguration` 导入了 嵌入式的三大服务器 `Tomcat`、`Jetty`、`Undertow`

1. 1. 导入 `Tomcat`、`Jetty`、`Undertow` 都有条件注解。系统中有这个类才行（也就是导了包）
   2. 默认  `Tomcat`配置生效。给容器中放 TomcatServletWebServerFactory
   3. 都给容器中 `ServletWebServerFactory`放了一个 **web服务器工厂（造web服务器的）**
   4. **web服务器工厂 都有一个功能，**`getWebServer`获取web服务器
   5. TomcatServletWebServerFactory 创建了 tomcat。

1. ServletWebServerFactory 什么时候会创建 webServer出来。
2. `ServletWebServerApplicationContext`ioc容器，启动的时候会调用创建web服务器
3. Spring**容器刷新（启动）**的时候，会预留一个时机，刷新子容器。`onRefresh()`
4. refresh() 容器刷新 十二大步的刷新子容器会调用 `onRefresh()`；

```java
	@Override
	protected void onRefresh() {
		super.onRefresh();
		try {
			createWebServer();
		}
		catch (Throwable ex) {
			throw new ApplicationContextException("Unable to start web server", ex);
		}
	}
```

总结:  **Web场景的Spring容器启动，在onRefresh的时候，会调用创建web服务器的方法。**

Web服务器的创建是通过WebServerFactory搞定的。容器中又会根据导了什么包条件注解，启动相关的 服务器配置，默认`EmbeddedTomcat`会给容器中放一个 `TomcatServletWebServerFactory`，导致项目启动，自动创建出Tomcat。

## 2. 自定义

![img](spring boot3.assets/1681725850466-2ecf12f4-8b66-469f-9d5d-377a33923b3c.png)

**切换服务器:** (其实没啥用,还是得用响应式编程)

```properties
<properties>
    <servlet-api.version>3.1.0</servlet-api.version>
</properties>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <!-- Exclude the Tomcat dependency -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- Use Jetty instead -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

## 3. 应用

- 修改`server`下的相关配置就可以修改**服务器参数**
- 通过给容器中放一个`ServletWebServerFactory`，来禁用掉SpringBoot默认放的服务器工厂，实现自定义嵌入**任意服务器**。



# 9. 全面接管SpringMVC(全手动模式)

- 全手动模式

- - `@EnableWebMvc` : 禁用默认配置
  - `WebMvcConfigurer`组件：定义MVC的底层行为

- SpringBoot 默认配置好了 SpringMVC 的所有常用特性。

- 如果我们需要全面接管SpringMVC的所有配置并**禁用默认配置**，仅需要编写一个`WebMvcConfigurer`配置类，并标注 `@EnableWebMvc` 即可

  ```java
  package com.atguigu.web.config;
  
  //全面接管SpringMVC
  @EnableWebMvc  //禁用mvc的默认功能
  @Configuration
  public class WebConfig implements WebMvcConfigurer {
  
  }
  ```

**WebMvcAutoConfiguration 到底自动配置了哪些规则**

SpringMVC自动配置场景给我们配置了如下所有**默认行为**

1. `WebMvcAutoConfiguration`web场景的自动配置类

1. 1. 支持RESTful的filter：HiddenHttpMethodFilter
   2. 支持非POST请求，请求体携带数据：FormContentFilter
   3. 导入`EnableWebMvcConfiguration`：

1. 1. 1. `RequestMappingHandlerAdapter`
      2. `WelcomePageHandlerMapping`： **欢迎页功能**支持（模板引擎目录、静态资源目录放index.html），项目访问/ 就默认展示这个页面.
      3. `RequestMappingHandlerMapping`：找每个请求由谁处理的映射关系
      4. `ExceptionHandlerExceptionResolver`：默认的异常解析器 
      5. `LocaleResolver`：国际化解析器
      6. `ThemeResolver`：主题解析器
      7. `FlashMapManager`：临时数据共享
      8. `FormattingConversionService`： 数据格式化 、类型转化
      9. `Validator`： 数据校验`JSR303`提供的数据校验功能
      10. `WebBindingInitializer`：请求参数的封装与绑定
      11. `ContentNegotiationManager`：内容协商管理器

1. 1. `WebMvcAutoConfigurationAdapter**`配置生效，它是一个`WebMvcConfigurer`，定义mvc底层组件

1. 1. 1. 定义好 `WebMvcConfigurer` **底层组件默认功能；所有功能详见列表**
      2. 视图解析器：`InternalResourceViewResolver`
      3. 视图解析器：`BeanNameViewResolver`,**视图名（controller方法的返回值字符串）**就是组件名
      4. 内容协商解析器：`ContentNegotiatingViewResolver`
      5. 请求上下文过滤器：`RequestContextFilter`: 任意位置直接获取当前请求
      6. 静态资源链规则
      7. `ProblemDetailsExceptionHandler`：错误详情

1. 1. 1. 1. SpringMVC内部场景异常被它捕获：

1. 1. 定义了MVC默认的底层行为: `WebMvcConfigurer`

##  @EnableWebMvc 禁用默认行为

1. `@EnableWebMvc`给容器中导入 `DelegatingWebMvcConfiguration`组件，

​        他是 `WebMvcConfigurationSupport`

1. `WebMvcAutoConfiguration`有一个核心的条件注解, `@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`，容器中没有`WebMvcConfigurationSupport`，`WebMvcAutoConfiguration`才生效.
2. @EnableWebMvc 导入 `WebMvcConfigurationSupport` 导致 `WebMvcAutoConfiguration` 失效。导致禁用了默认行为

- @EnableWebMVC 禁用了 Mvc的自动配置
- WebMvcConfigurer 定义SpringMVC底层组件的功能类

##  WebMvcConfigurer 功能

定义扩展SpringMVC底层功能

| 提供方法                           | 核心参数                              | 功能                                                         | 默认                                                         |
| ---------------------------------- | ------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| addFormatters                      | FormatterRegistry                     | **格式化器**：支持属性上@NumberFormat和@DatetimeFormat的数据类型转换 | GenericConversionService                                     |
| getValidator                       | 无                                    | **数据校验**：校验 Controller 上使用@Valid标注的参数合法性。需要导入starter-validator | 无                                                           |
| addInterceptors                    | InterceptorRegistry                   | **拦截器**：拦截收到的所有请求                               | 无                                                           |
| configureContentNegotiation        | ContentNegotiationConfigurer          | **内容协商**：支持多种数据格式返回。需要配合支持这种类型的HttpMessageConverter | 支持 json                                                    |
| configureMessageConverters         | List<HttpMessageConverter<?>>         | **消息转换器**：标注@ResponseBody的返回值会利用MessageConverter直接写出去 | 8 个，支持byte，string,multipart,resource，json              |
| addViewControllers                 | ViewControllerRegistry                | **视图映射**：直接将请求路径与物理视图映射。用于无 java 业务逻辑的直接视图页渲染 | 无 <mvc:view-controller>                                     |
| configureViewResolvers             | ViewResolverRegistry                  | **视图解析器**：逻辑视图转为物理视图                         | ViewResolverComposite                                        |
| addResourceHandlers                | ResourceHandlerRegistry               | **静态资源处理**：静态资源路径映射、缓存控制                 | ResourceHandlerRegistry                                      |
| configureDefaultServletHandling    | DefaultServletHandlerConfigurer       | **默认 Servlet**：可以覆盖 Tomcat 的DefaultServlet。让DispatcherServlet拦截/ | 无                                                           |
| configurePathMatch                 | PathMatchConfigurer                   | **路径匹配**：自定义 URL 路径匹配。可以自动为所有路径加上指定前缀，比如 /api | 无                                                           |
| configureAsyncSupport              | AsyncSupportConfigurer                | **异步支持**：                                               | TaskExecutionAutoConfiguration                               |
| addCorsMappings                    | CorsRegistry                          | **跨域**：                                                   | 无                                                           |
| addArgumentResolvers               | List<HandlerMethodArgumentResolver>   | **参数解析器**：                                             | mvc 默认提供                                                 |
| addReturnValueHandlers             | List<HandlerMethodReturnValueHandler> | **返回值解析器**：                                           | mvc 默认提供                                                 |
| configureHandlerExceptionResolvers | List<HandlerExceptionResolver>        | **异常处理器**：                                             | 默认 3 个 ExceptionHandlerExceptionResolver ResponseStatusExceptionResolver DefaultHandlerExceptionResolver |
| getMessageCodesResolver            | 无                                    | **消息码解析器**：国际化使用                                 | 无                                                           |



## 三种方式

| 方式         | 用法                                                         | 效果                         |                                                           |
| ------------ | ------------------------------------------------------------ | ---------------------------- | --------------------------------------------------------- |
| **全自动**   | 直接编写控制器逻辑                                           |                              | 全部使用**自动配置默认效果**                              |
| **手自一体** | `@Configuration` +   配置`WebMvcConfigurer`+ *配置 WebMvcRegistrations* | **不要标注** `@EnableWebMvc` | **保留自动配置效果** **手动设置部分功能** 定义MVC底层组件 |
| **全手动**   | `@Configuration` +   配置`WebMvcConfigurer`                  | **标注** `@EnableWebMvc`     | **禁用自动配置效果** **全手动设置**                       |

总结：

**给容器中写一个配置类**`@Configuration`**实现** `WebMvcConfigurer`**但是不要标注** `@EnableWebMvc`**注解，实现手自一体的效果。**



# Web新特性

RFC 7807: https://www.rfc-editor.org/rfc/rfc7807

**错误信息**返回新格式

原理

```java
@Configuration(proxyBeanMethods = false)
//配置过一个属性 spring.mvc.problemdetails.enabled=true
@ConditionalOnProperty(prefix = "spring.mvc.problemdetails", name = "enabled", havingValue = "true")
static class ProblemDetailsErrorHandlingConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseEntityExceptionHandler.class)
    ProblemDetailsExceptionHandler problemDetailsExceptionHandler() {
        return new ProblemDetailsExceptionHandler();
    }
}
```

1. `ProblemDetailsExceptionHandler `是一个 `@ControllerAdvice`集中处理系统异常

2. 处理以下异常。如果系统出现以下异常，会被SpringBoot支持以 `RFC 7807`规范方式返回错误数据

   ```
   	@ExceptionHandler({
   			HttpRequestMethodNotSupportedException.class, //请求方式不支持
   			HttpMediaTypeNotSupportedException.class,
   			HttpMediaTypeNotAcceptableException.class,
   			MissingPathVariableException.class,
   			MissingServletRequestParameterException.class,
   			MissingServletRequestPartException.class,
   			ServletRequestBindingException.class,
   			MethodArgumentNotValidException.class,
   			NoHandlerFoundException.class,
   			AsyncRequestTimeoutException.class,
   			ErrorResponseException.class,
   			ConversionNotSupportedException.class,
   			TypeMismatchException.class,
   			HttpMessageNotReadableException.class,
   			HttpMessageNotWritableException.class,
   			BindException.class
   		})
   ```

   效果：

   **默认响应错误的json。状态码 405**

   ```json
   {
       "timestamp": "2023-04-18T11:13:05.515+00:00",
       "status": 405,
       "error": "Method Not Allowed",
       "trace": "org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported\r\n\tat org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.handleNoMatch(Ra.core.StandardWrapperValve.invoke(StandardWrapperValve.java:166)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org
       "message": "Method 'POST' is not supported.",
       "path": "/list"
   }
   ```

   **开启ProblemDetails返回, 使用新的MediaType**

   `Content-Type: application/problem+json`+ 额外扩展返回

   ![img](spring boot3.assets/1681816524680-e75cbe89-f90c-4ac4-8247-ec850308df65-168880261763211.png)

   ```json
   {
       "type": "about:blank",
       "title": "Method Not Allowed",
       "status": 405,
       "detail": "Method 'POST' is not supported.",
       "instance": "/list"
   }
   ```

   使用:

   ```
   //配置过一个属性 spring.mvc.problemdetails.enabled=true
   ```

   

## 2. 函数式Web

   

`SpringMVC 5.2` 以后 允许我们使用**函数式**的方式，**定义Web的请求处理流程**。

函数式接口

Web请求处理的方式：

1. `@Controller + @RequestMapping`：**耦合式** （**路由**、**业务**耦合）
2. **函数式Web**：分离式（路由、业务分离）

### 1. 场景

场景：User RESTful - CRUD

- GET /user/1  获取1号用户
- GET /users   获取所有用户
- POST /user  **请求体**携带JSON，新增一个用户
- PUT /user/1 **请求体**携带JSON，修改1号用户
- DELETE /user/1 **删除**1号用户 

### 2. 核心类

- **RouterFunction**
- **RequestPredicate**
- **ServerRequest**
- **ServerResponse**

### 3. 示例

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class MyRoutingConfiguration {

    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

    @Bean
    public RouterFunction<ServerResponse> routerFunction(MyUserHandler userHandler) {
        return route()
                .GET("/{user}", ACCEPT_JSON, userHandler::getUser)
                .GET("/{user}/customers", ACCEPT_JSON, userHandler::getUserCustomers)
                .DELETE("/{user}", ACCEPT_JSON, userHandler::deleteUser)
                .build();
    }

}


```





```
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class MyUserHandler {

    public ServerResponse getUser(ServerRequest request) {
        ...
        return ServerResponse.ok().build();
    }

    public ServerResponse getUserCustomers(ServerRequest request) {
        ...
        return ServerResponse.ok().build();
    }

    public ServerResponse deleteUser(ServerRequest request) {
        ...
        return ServerResponse.ok().build();
    }

}
```





# 3、SpringBoot3-数据访问

## 整合SSM场景

SpringBoot 整合 Spring、SpringMVC、MyBatis 进行数据访问场景开发

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

<img src="spring boot3.assets/image-20230906082137094.png" alt="image-20230906082137094" style="zoom:67%;" />

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

## 简单使用

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

    ![image-20230708173420239](spring boot3.assets/image-20230708173420239.png)

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

## ==IDEA中使用自带的数据库(重要)==

> 首先打开IDEA页面
> <img src="spring boot3.assets/image-20230911234743463.png" alt="image-20230911234743463" style="zoom: 80%;" />

> 添加MySQL数据库
>
> <img src="spring boot3.assets/image-20230911234811564.png" alt="image-20230911234811564" style="zoom: 80%;" />

> 配置
> ![image-20230911234829000](spring boot3.assets/image-20230911234829000.png)

> 选择自己用的数据库
>
> ![image-20230911234909372](spring boot3.assets/image-20230911234909372.png)

> 查看数据库中的表结构
> ![image-20230911234924676](spring boot3.assets/image-20230911234924676.png)

> 查看表的SQL语句
>
> ![image-20230911234939139](spring boot3.assets/image-20230911234939139.png)

> 新建查询，执行SQL语句
> ![image-20230911233447754](spring boot3.assets/image-20230911233447754.png)

### MyBatisX 插件

![image-20230911233548515](spring boot3.assets/image-20230911233548515.png)

设置项目基本路径、包路径、[编码格式](https://so.csdn.net/so/search?q=编码格式&spm=1001.2101.3001.7020)，实体类包名称、实体类名![image-20230911234703690](spring boot3.assets/image-20230911234703690.png)

配置生成mapper文件，service文件的位置

![image-20230912170343770](spring boot3.assets/image-20230912170343770.png)

ptions选项作用大致如下：

- comment：可能和生成java doc comments有关，没有效果。
- toString/hashCode/equals：是否生成相应的方法，建议勾选(和Lombok选择一个)。
- Lombok：勾选后实体类自动添加Lombok的@Data注解，建议勾选（要先安装Lombok插件）
- Actual Column：勾选后，生成的实体类属性名和表中字段名大小写会保持一致。例如，表中有字段Name，勾选该选项后生成的属性名也为Name，未勾选则为name，建议根据实际需要勾选。
- Actual Column Annotation：是否对所有属性名都加上注解标明对应字段，例如@TableName，建议勾选。
- JSR310:Data API：是否使用新标准的时间日期API，包括 Instant、Clock、LocalDateTime、DateTimeFormatter、ZonedDateTime 以及替换 Calendar 的 Chronology 等类。建议勾选（新标准的时间日期API比老版本友好多了，强烈建议使用新版时间日期API）

### 快速生成

官网 [代码生成器（新） | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/779a6e/#快速入门)

#### pom.xml

```xml
	    <!--        mybatisplus+数据库相关开始-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-core</artifactId>
            <version>3.5.3.1</version>
        </dependency>
	    <!--使用mybatisplus常用接口,可以不用写sql语句-->
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
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--mybatisplus+数据库相关结束-->
```

#### 更新配置

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis
spring.datasource.username=root
spring.datasource.password=741106


mybatis.mapper-locations=classpath:/mapper/*.xml

mybatis.configuration.map-underscore-to-camel-case=true

# web 端口
server.port=9000
```

不需要写返回值，只需要写方法名就能够快速生成CRUD

![在这里插入图片描述](spring boot3.assets/45623756db034e079a06a8c2c824dac0.png)

输入insertSelective之后立即点击“**Alt+Enter**”即可mapper文件中自动生成sql

![image-20230911234021608](spring boot3.assets/image-20230911234021608.png)

![image-20230911234044862](spring boot3.assets/image-20230911234044862.png)

### 代码

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
		// adminService中有很多方法
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

### CRUD接口

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





### mybatis中parameterType别名图

![image-20230921195943952](spring boot3.assets/image-20230921195943952.png)

### 单表查询

#### 条件构造器方式

![image-20231002145521470](spring boot3.assets/image-20231002145521470.png)

![image-20231002145432486](spring boot3.assets/image-20231002145432486.png)

例子

```java

/**
     * 1、名字中包含雨并且年龄小于40
     *     name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     *    name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").between("age" ,20 ,40).isNotNull("email");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     *    name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or().gt("age", 25).orderByDesc("age").orderByAsc("id");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
```

4、创建日期为2019年2月14日并且直属上级为名字为王姓

> date_format(create_time,‘%Y-%m-%d’)=‘2019-02-14’ and manager_id in (select id from user where name like ‘王%’)

```java
apply(sql,prams)
inSql("字段",sql子查询)



QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}","2019-02-14").inSql("manager_id","select id from user where name like '王%' ");
List<User> userList = userMapper.selectList(queryWrapper);
userList.forEach(System.out::println);

// 此处写法存在sql注入问题，不建议使用
userQuery.apply("date_format(create_time,'%Y-%m-%d')='2019-02-14' or true or true").inSql("manager_id","select id from user where name like '王%'");

queryWrapper.apply("phone","888888").inSql("username","select username from user where id = 3");

date_format(日期，'格式')：将日期按照格式进行插入或者返回。例如：date_format(now(),'%Y-%m-%d')。
动态条件构造器：apply。范围条件构造器：insql。
注意：如果{0}替换为实际值，可能会造成sql注入。
```



```java
public class SelectTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void selectById() {
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
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
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
     * allEq
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
> ```
> List<Student> students = studentMapper.selectList(Wrappers.lambdaQuery(Student.class)
> .eq(Student::getAge, "23")
> .or(s -> s.eq(Student::getSchoolId, 300)));
> ```

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



#### **使用条件构造器进行查询**

1. Java的传统写法

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

1. [lambda表达式](https://so.csdn.net/so/search?q=lambda表达式&spm=1001.2101.3001.7020)方式(推荐)

```java
public AjaxResult selectStudentByNumber(@RequestBody Student student) {
    // 1.定义条件构造器
    LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<Student>().eq(Student::getStuNumber,stuNumber);
     // 2. 查询
    List<Student> result = studentService.list(lqw);
    return AjaxResult.success(result);
}
```

##### 用法：

##### allEq：全部等于

```plaintext
// params : key为数据库字段名,value为字段值
allEq(Map<R, V> params)
```

> 举例：
> allEq({id:1,name:"某某",age:null})--->id = 1 and name = '某某' and age is null

代码演示：

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



##### eq：等于 =

```java
eq(R column, Object val)
```

> 举例：
> eq("name", "某某")--->name = '某某'

[MyBatis-Plus——条件构造器_mybatis-plus条件构造器-CSDN博客](https://blog.csdn.net/Yangyeon_/article/details/129452576)





#### 使用注解方式

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

#### 使用xml方式

比较简单一般由插件直接生成

### 多表查询

代码见[代码experiment02](C:\Users\16658\Documents\GitHub\java_note\note\spring boot3\sql\experiment02)

MP提供了大量单表查询的方法，但是没有多表的操作，所以涉及到多表的查询时，需要我们自己实现

**单表查询插件自动生成映射类型**

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

## 自动配置原理

**SSM整合总结：**

1. **导入** `mybatis-spring-boot-starter`
2. 配置**数据源**信息
3. 配置mybatis的`**mapper接口扫描**`与`**xml映射文件扫描**`
4. 编写bean，mapper，生成xml，编写sql 进行crud。**事务等操作依然和Spring中用法一样**
5. 效果：

1. 1. 所有sql写在xml中
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

### 1. Druid 数据源

暂不支持 `SpringBoot3`

- 导入`druid-starter`
- 写配置
- 分析自动配置了哪些东西，怎么用

Druid官网：https://github.com/alibaba/druid





## 8. 按例





# ==单元测试==

默认自动引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 基本使用

 <font color = red>在spring测试中不要使用静态变量或者main方法！！</font>

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceImplTest {
    @Autowired
    TestServiceImpl testServiceImpl;

    @DisplayName("测试1")
    @Test
    void contextLoads() {
        int sum = helloService.sum(1, 2);
        System.out.println(sum);
    }
    
    @DisplayName("测试2")
    @Test
    void test01(){
    	System.out.println("aaaa");
    }

    @BeforeAll //所有测试方法运行之前先运行这个;  只打印一次
    static void initALl() {
    	Syste.out.println("hello");
    }
    
    @BeforeEach //每个测试方法运行之前先运行这个;  每个方法运行打印一次
    void init() {
    	System.out.println("world");
    }
}
```

<img src="spring boot3.assets/image-20230925135045442.png" alt="image-20230925135045442" style="zoom:67%;" />

## Assert.assertThat使用

| 方法              | 说明                                 |
| ----------------- | ------------------------------------ |
| assertEquals      | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals   | 判断两个对象或两个原始类型是否不相等 |
| assertSame        | 判断两个对象引用是否指向同一个对象   |
| assertNotSame     | 判断两个对象引用是否指向不同的对象   |
| assertTrue        | 判断给定的布尔值是否为 true          |
| assertFalse       | 判断给定的布尔值是否为 false         |
| assertNull        | 判断给定的对象引用是否为 null        |
| assertNotNull     | 判断给定的对象引用是否不为 null      |
| assertArrayEquals | 数组断言                             |
| assertAll         | 组合断言                             |
| assertThrows      | 异常断言                             |
| assertTimeout     | 超时断言                             |
| fail              | 快速失败                             |

### 一般匹配符

```java
// allOf匹配符表明如果接下来的所有条件必须都成立测试才通过，相当于“与”（&&）
assertThat(testedNumber, allOf( greaterThan(8), lessThan(16)));

// anyOf匹配符表明如果接下来的所有条件只要有一个成立则测试通过，相当于“或”（||）
assertThat(testedNumber, anyOf(greaterThan(16), lessThan(8)));

// anything匹配符表明无论什么条件，永远为true
assertThat(testedNumber, anything());

// is匹配符表明如果前面待测的object等于后面给出的object，则测试通过
assertThat(testedString, is("developerWorks"));

// not匹配符和is匹配符正好相反，表明如果前面待测的object不等于后面给出的object，则测试通过
assertThat(testedString, not("developerWorks"));

// nullValue()匹配符断言被测object的值为null
assertThat(object, nullValue());

// notNullValue()匹配符断言被测object的值不为null
assertThat(object,notNullValue());

// 断言testedObject为Cheddar的实例
assertThat(testedObject, is(Cheddar.class));
```

###  字符串相关

```java
// containsString匹配符表明如果测试的字符串testedString包含子字符串"developerWorks"则测试通过
assertThat(testedString, containsString("developerWorks"));

// endsWith匹配符表明如果测试的字符串testedString以子字符串"developerWorks"结尾则测试通过
assertThat(testedString, endsWith("developerWorks")); 

// startsWith匹配符表明如果测试的字符串testedString以子字符串"developerWorks"开始则测试通过
assertThat(testedString, startsWith("developerWorks")); 

// equalTo匹配符表明如果测试的testedValue等于expectedValue则测试通过，equalTo可以测试数值之间，字
// 符串之间和对象之间是否相等，相当于Object的equals方法，与上面介绍的is方法一样
assertThat(testedValue, equalTo(expectedValue)); 

// equalToIgnoringCase匹配符表明如果测试的字符串testedString在忽略大小写的情况下等于"developerWorks"
// 则测试通过
assertThat(testedString, equalToIgnoringCase("developerWorks")); 

// equalToIgnoringWhiteSpace匹配符表明如果测试的字符串testedString在忽略头尾的任意个空格的情况下等
// 于"developerWorks"则测试通过，注意：字符串中的空格不能被忽略
assertThat( testedString, equalToIgnoringWhiteSpace( "developerWorks" ) );
```

###  数值相关

```java
// closeTo匹配符表明如果所测试的浮点型数testedDouble在20.0±0.5范围之内则测试通过
assertThat(testedDouble, closeTo(20.0, 0.5));

// reaterThan匹配符表明如果所测试的数值testedNumber大于16.0则测试通过
assertThat(testedNumber, greaterThan(16.0));

// lessThan匹配符表明如果所测试的数值testedNumber小于16.0则测试通过
assertThat(testedNumber, lessThan (16.0));

// greaterThanOrEqualTo匹配符表明如果所测试的数值testedNumber大于等于16.0则测试通过
assertThat(testedNumber, greaterThanOrEqualTo (16.0));

// lessThanOrEqualTo匹配符表明如果所测试的数值testedNumber小于等于16.0则测试通过
assertThat( testedNumber, lessThanOrEqualTo (16.0));
```

###  Collection相关

```java
// hasEntry匹配符表明如果测试的Map对象mapObject含有一个键值为"key"对应元素值为"value"的Entry项
// 则测试通过
assertThat( mapObject, hasEntry( "key", "value" ) );

// hasItem匹配符表明如果测试的迭代对象iterableObject含有元素“element”项则测试通过
assertThat(iterableObject, hasItem ("element"));

// hasKey匹配符表明如果测试的Map对象mapObject含有键值“key”则测试通过
assertThat(mapObject, hasKey ("key"));

// hasValue匹配符表明如果测试的Map对象mapObject含有元素值“value”则测试通过
assertThat(mapObject, hasValue ("key"));
```

## 使用@AutoWired遇到空指针

[Spring boot test测试中@Autowired不起作用_未名who的博客-CSDN博客](https://blog.csdn.net/qq_2300688967/article/details/80054766)

[Spring 单元测试时使用@AutoWired为空 解决方法_在test中使用autowired空指针异常_梦彧-Z的博客-CSDN博客](https://blog.csdn.net/weixin_45852395/article/details/121427862)

1，问题：如下所示：

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
 
public class MoveSysUserTest {
    @Autowired
    private MoveSysUser moveSysUser;
 
    @Test
    public void testMoveSysUser() {
        boolean res = moveSysUser.moveSysUser();
        System.out.println(res);
    }
}
```

此时运行，会提示moveSysUser为空，报空指针异常

2，解决方法

方法1: 添加注解(或者继承一个添加了以上注解的类)：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
```

```properties
	<!--如果只缺@RunWith这一项，只用导入junit.4.12这一个包就可以了-->
	<dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>compile</scope>
    </dependency>
```

方法2:   <font color = red>在spring测试中不要使用静态变量或者main方法！！</font>



# 登录模块

三种方式:

1. jwt
2. Jwt+redis
3. redis  + UUID

# Spring-Boot-操作-Redis内存数据库

在 Redis 出现之前，缓存框架各种各样，有了 Redis ，缓存方案基本上都统一了

[Window下Redis的安装和部署详细图文教程（Redis的安装和可视化工具的使用）_redis安装_明金同学的博客-CSDN博客](https://blog.csdn.net/weixin_44893902/article/details/123087435)

**Redis下载地址：**

**windows版本readis下载（GitHub）：**

https://github.com/tporadowski/redis/releases **（推荐使用）**

https://github.com/MicrosoftArchive/redis/releases

**官网下载（无Windows版本）：** https://redis.io/download

**Redis中文网站：** [http://www.redis.cn](http://www.redis.cn/)

所有版本这里都有：https://download.redis.io/releases/（下载后是个Linux的压缩文件，需要下载、解压和编译）

**发行说明：**

https://raw.githubusercontent.com/redis/redis/5.0/00-RELEASENOTES

> Redis 支持 32 位和 64 位。根据你所使用的系统和实际情况进行选择，这里我下载 **Redis-x64-xxx.zip**压缩包到磁盘，解压后，将文件夹重新命名为 **redis**。

> Windows下的`.msi`安装和`.zip`格式区别：
>
> `.msi`是Windows installer开发出来的程序安装文件，它可以让你安装，修改，卸载你所安装的程序。说白了.[msi](https://so.csdn.net/so/search?q=msi&spm=1001.2101.3001.7020)就是Windows installer的数据包，把所有和安装文件相关的内容封装在一个包里。此外：它还包含有关安装过程自己的信息。例如：安装序列、目标文件夹路径、安装选项和控制安装过程的属性。
> `.zip`是一个压缩包，解压之后即可，不需要安装

## 一、zip压缩包方式下载安装

### 1、下载Redis压缩包

这里我在GitHub中下载window用的 5.0 版本`Redis-x64-5.0.14.1.zip`。

https://github.com/tporadowski/redis/releases

![image-20220222192232158](spring boot3.assets/10af1c0dfe7518318e4ae07621edbf7b.png)

![image-20220222193742807](spring boot3.assets/80fb20015a384165fb21080c65738751.png)

### 2、解压到文件夹

将下载的压缩包解压到指定的文件夹中，如：**D:\Redis**，内容如下：

![image-20220222193650737](spring boot3.assets/7f930d382215f61d7e9f4d26767144da.png)

### 3、启动Redis服务

在Redis的安装目录下打开cmd窗口，然后执行命令来启动服务：

```bash
redis-server.exe redis.windows.conf
```

**切换到redis目录：**

直接在Redis目录路径处输入cmd回车也可以进入命令窗口

> 可以打开cmd使用 cd 命令切换到redis所在的目录：`cd /d d:\redis`

<img src="spring boot3.assets/9fe540bb7d95a372fa3a5e47d9dc6b0f.png" alt="image-20220222194625942"  />

> **cd 切换目录命令示例：**
>
> 例：cd // 显示当前目录
>
> 例：cd … // 进入父目录
>
> 例：cd /d d: // 进入上次d盘所在的目录（或在直接输入：d:）
>
> 例：cd /d d:\ // 进入d盘根目录
>
> 例：cd d: // 显示上次d盘所在的目录
>
> 例：cd /d d:\src // 进入d:\src目录

随后使用`redis-server.exe redis.windows.conf`命令来启动redis服务：
![image-20220222195029989](spring boot3.assets/8c8ebf59f638ab01ce77be0a41a25d1e.png)

> 默认端口为6379，出现图上的图标说明redis服务启动成功。命令里面的 `redis.windows.conf` 可以省略，省略后，使用`redis-server.exe`命令会使用默认的配置。

为了方便，建议把Redis路径配置到系统变量Path值中，这样就省得再输路径了。
![image-20220222200324333](spring boot3.assets/09a29e9f777a63958800e8033d50b2fe.png)

### 4、打开[Redis客户端](https://so.csdn.net/so/search?q=Redis客户端&spm=1001.2101.3001.7020)进行连接

我们使用`redis-cli.exe`命令来打开Redis客户端：

```bash
redis-cli.exe -h 127.0.0.1 -p 6379
```

![image-20220222201138167](spring boot3.assets/5f11bd1117c3549657c19460d51282b8.png)
在命令中输入ping命令来检测redis服务器与redis客户端的连通性，返回`PONG`则说明连接成功了。
![image-20220222200941270](spring boot3.assets/cfa4cd2ec80c3aa623d3d06c2a75aa0a.png)
如果出现连接不成功，注意服务打开以后，另启一个 cmd 窗口到Redis所在的目录执行命令，**原来的Redis启动窗口不要关闭**，不然就无法访问服务端了。
![image-20220222200048603](spring boot3.assets/cfe24afd99d68e9fa59a2a6dd4f74443.png)

如果连接成功，到此Redis的安装和部署也就完成了。

### 5、使用一些基础操作来测试

**下面我们可以来进行一些基础操作来进行测试**

Redis默认拥有16个数据库，初始默认使用0号库，在命令行中通过`select`命令将数据库切换到8号数据库：

```bash
select 8 
```

![image-20220222201909816](spring boot3.assets/248edb3809f099536c7945d2c4e4fa39.png)
在命令中通过`set`命令设置键值，通过`get`命令取出键值：
![image-20220222203508802](spring boot3.assets/6a32cdcc2445203d764d1568229b243c.png)
在命令中通过`shutdown`命令来关闭redis服务：
![image-20220222202441339](spring boot3.assets/09cc3752110886f250c9d1c574dfc088.png)
在Redis服务启动的cmd窗口中会出现服务关闭的提醒，如下图：
![image-20220222202546165](spring boot3.assets/f6242cd4cb27e0cd25df2976bd85d3f8.png)

**Redis常用的服务指令**

卸载服务：`redis-server --service-uninstall`

开启服务：`redis-server --service-start`

停止服务：`redis-server --service-stop`

**Redis windows下一直保持启动状态**

```
进入 DOS窗口

在进入redis的安装目录

输入：redis-server --service-install redis.windows.conf --loglevel verbose ( 安装redis服务 )

输入：redis-server --service-start ( 启动服务 )

输入：redis-server --service-stop （停止服务）
```

## 二、使用可视化工具

**Redis Desktop Manager**

需要安装使用，0.9.4以上是要收费的

**下载地址：**https://github.com/uglide/RedisDesktopManager/releases/download/0.9.3/redis-desktop-manager-0.9.3.817.exe

**详情：**https://blog.csdn.net/u012688704/article/details/82251338

下载完成之后直接点击安装，无需任何配置直接连接。

![image-20220222220951840](spring boot3.assets/cb80fd3b65a5b818165975f4b921516f.png)
界面如下：
![image-20220222221011889](spring boot3.assets/0468fb35ece2920eb8248c10e13ada20.png)

## 三、整合redis

在 Redis 出现之前，缓存框架各种各样，有了 Redis ，缓存方案基本上都统一了

1. pom

   ```xml
   <!-- redis -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   ```

2. yml

   ```yaml
   spring:
     redis:
       host: localhost
       port: 6379
   ```

   ```yaml
   //或者
   spring:
     redis:
         host: r-bp1nc7reqesxisgxpipd.redis.rds.aliyuncs.com  #访问地址
         port: 6379  
         password: 123  #密码
         client-type: jedis
         jedis:
           pool:
             max-active: 10
   ```

3. 配置类

   ```java
   @Configuration
   public class MyRedisConfig {
       @Resource
       private RedisConnectionFactory factory;
   
       @Bean
       public RedisTemplate redisTemplate(){
           RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
           redisTemplate.setConnectionFactory(factory);
           redisTemplate.setKeySerializer(new StringRedisSerializer());
   
           Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
           redisTemplate.setValueSerializer(serializer);
   
           ObjectMapper om = new ObjectMapper();
           om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
           om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
           om.setTimeZone(TimeZone.getDefault());
           om.configure(MapperFeature.USE_ANNOTATIONS, false);
           om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
           om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
           om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
           serializer.setObjectMapper(om);
   
           return redisTemplate;
       }
   }
   ```

   ```java
   @Autowired
   private RedisTemplate redisTemplate;
   
   // 存入redis
   loginUser.setPassword(null); //密码不存
   redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);// 传入了XUser对象
   
   
   // -------------------------------------
   // 从redis查询token
   Object obj = redisTemplate.opsForValue().get(token);  //  <!-- redis -->
   // 反序列化 json对象反序列化成user对象
   XUser user = JSON.parseObject(JSON.toJSONString(obj), XUser.class);
   ```

   测试redis中保存

   ![image-20230923105605180](spring boot3.assets/image-20230923105605180.png)

[springboot之使用redistemplate优雅地操作redis-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/609263)





# 反序列化(json)

\[fastjson2 介绍及使用_西凉的悲伤的博客-CSDN博客](https://blog.csdn.net/qq_33697094/article/details/128114939)

## 一、导入fastjson2依赖

```xml
<dependency>
    <groupId>com.alibaba.fastjson2</groupId>
    <artifactId>fastjson2</artifactId>
    <version>2.0.26</version>
</dependency>
```

```java
// 从redis查询token
Object obj = redisTemplate.opsForValue().get(token);  //  <!-- redis -->
// 反序列化 json对象反序列化成user对象
XUser user = JSON.parseObject(JSON.toJSONString(obj), XUser.class);
```

## 二、json对象与json数组的创建

### json对象创建

```java
        JSONObject info = new JSONObject();
        info.put("name", "张三");
        info.put("age", "18");
        info.put("地理", "70");
        info.put("英语", "60");
```

### json数组创建

```java
		JSONObject info1 = new JSONObject();
        info1.put("name", "张三");
        info1.put("age", "18");
        JSONObject info2 = new JSONObject();
        info2.put("name", "李四");
        info2.put("age", "19");

        //把上面创建的两个json对象加入到json数组里
        JSONArray array = new JSONArray();
        array.add(info1);
        array.add(info2);

        JSONArray array = new JSONArray();
        array.add("1班");
        array.add("2班");
        array.add("3班");
```

## 三、json对象取值与json数组遍历取值

### json对象取值

```java
        JSONArray array = new JSONArray();
        array.add("1班");
        array.add("2班");
        array.add("3班");
        JSONObject school = new JSONObject();
        school.put("schoolName", "第一中学");
        school.put("teacher", "刘梅");

        JSONObject info = new JSONObject();
        info.put("name", "张三");
        info.put("age", "18");
        info.put("gradle",array);
        info.put("schoolInfo",school);

        //从info中取值
        System.out.println(info.getString("name")); //张三
        System.out.println(info.getIntValue("age"));//18
        System.out.println(info.getJSONArray("gradle"));//["1班","2班","3班"]
        System.out.println(info.getJSONObject("schoolInfo"));//{"schoolName":"第一中学","teacher":"刘梅"}
12345678910111213141516171819
```

### json数组遍历取值

```java
        JSONObject info1 = new JSONObject();
        info1.put("name", "张三");
        info1.put("age", "18");
        JSONObject info2 = new JSONObject();
        info2.put("name", "李四");
        info2.put("age", "19");

        JSONArray array = new JSONArray();
        array.add(info1);
        array.add(info2);
        //遍历获取json数组中对象的值
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            System.out.println(json.getString("name"));
            System.out.println(json.getString("age"));
        }
```

## 四、json对象与字符串的转换

### json对象与字符串的转换

```java
		JSONObject info = new JSONObject();
        info.put("name", "张三");
        info.put("age", "18");
        info.put("地理", "70");
        info.put("英语", "60");

        //JSON对象转字符串
        String str = JSON.toJSONString(info);
        //JSON字符串转JSON对象
        JSONObject json = JSONObject.parseObject(str);
12345678910
```

### json字符串的字节数组转json对象

```java
        String str = "{\"name\":\"张三\",\"age\":\"18\",\"地理\":\"70\",\"英语\":\"60\"}";

        byte[] bytes = str.getBytes();
        JSONObject data = JSON.parseObject(bytes);

12345
```

## 五、json数组与字符串的转换

```java
        String text = "[\"张三\",\"李四\",\"王五\"]";
        //json字符串转json数组
        JSONArray data = JSON.parseArray(text);
        //json数组转json字符串
        String str = JSONArray.toJSONString(data);
12345
```

## 六、json字符串转java对象的转换

Student类如下：

```java
@Data
public class Student {
    public String name;
    public int age;
}
```

### json字符串转java对象的转换

```java
Student student = new Student("张三", 18);

//Student对象转JSON字符串
String studentStr = JSON.toJSONString(student);
//JSON字符串转Student对象
Student data = JSON.parseObject(studentStr, Student.class);
```

### java对象转byte数组转换

```java
Student student = new Student("张三", 18);

//Student对象转字节数组
byte[] text = JSON.toJSONBytes(student);
//字节数组转Student对象
Student data = JSON.parseObject(text, Student.class);
```

## 七、json字符串与Map转换

### json字符串转Map

```java
        String str="{\n" +
                "\"gradle\":\"高一\",\n" +
                "\"number\":\"2\",\n" +
                "\"people\":[{\"name\":\"张三\",\"age\":\"15\",\"phone\":\"123456\"},\n" +
                "         {\"name\":\"李四\",\"age\":\"16\",\"phone\":\"78945\"}]\n" +
                "}";

        //json字符串转Map
        Map<String, Object> map = JSONObject.parseObject(str, new TypeReference<Map<String, Object>>() {});
        System.out.println(map.get("gradle").toString());
        System.out.println(map.get("number").toString());
        System.out.println(map.get("people").toString());

12345678910111213
```

### Map转json字符串

(注意：如果直接使用JSON.toJSONString(map)转换，因为"测试1"的值为null，转换的结果就会是 {“测试2”:“hello”})

```java
		Map<String,String> map=new HashMap<>();
        map.put("测试1",null);
        map.put("测试2","hello");

		//{"测试2":"hello","测试1":null}
        String str = JSON.toJSONString(map, JSONWriter.Feature.WriteMapNullValue);

1234567
```

如果你使用的是老的fastjson1,可以像下面这样转换：

```java
		Map<String,String> map=new HashMap<>();
		map.put("测试1",null);
		map.put("测试2","hello");
		String str = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue) ;
1234
```

## 八、json数组转List

```java
        String str="{\n" +
                "\"gradle\":\"高一\",\n" +
                "\"number\":\"2\",\n" +
                "\"people\":[{\"name\":\"张三\",\"age\":\"15\",\"phone\":\"123456\"},\n" +
                "         {\"name\":\"李四\",\"age\":\"16\",\"phone\":\"78945\"}]\n" +
                "}";

        JSONObject strJson = JSONObject.parseObject(str);
        //获取people数组
        JSONArray people = strJson.getJSONArray("people");
        //json数组转List
        List<Map> list = people.toJavaList(Map.class);

12345678910111213
```

如果你使用的是老的fastjson1,可以像下面这样转换：

```java
        String str="{\n" +
                "\"gradle\":\"高一\",\n" +
                "\"number\":\"2\",\n" +
                "\"people\":[{\"name\":\"张三\",\"age\":\"15\",\"phone\":\"123456\"},\n" +
                "         {\"name\":\"李四\",\"age\":\"16\",\"phone\":\"78945\"}]\n" +
                "}";

        JSONObject strJson=JSONObject.parseObject(str);//字符串转json对象
        String people = String.valueOf(strJson.getJSONArray("people"));
        List<Map<String,String>> list = (List<Map<String,String>>) JSONArray.parse(people);
12345678910
```

## 九、json[字符串格式化](https://so.csdn.net/so/search?q=字符串格式化&spm=1001.2101.3001.7020)

有时候我们想把 json 字符串格式化输出，也就是该缩进的缩进该换行的换行，让它更美观的输出，可以像下面这样：

```java
        String str = "[{\"isSendPhone\":\"true\",\"id\":\"22258352\",\"phoneMessgge\":\"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。\",\"readsendtime\":\"9\",\"countdown\":\"7\",\"count\":\"5\",\"serviceName\":\"流程助手\",\"startdate\":\"2022-02-09 00:00:00.0\",\"insertTime\":\"2023-02-02 07:00:38.0\",\"enddate\":\"2023-02-08 23:59:59.0\",\"emailMessage\":\"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。\",\"phone\":\"\",\"companyname\":\"顾问有限责任公司\",\"serviceId\":\"21\",\"isSendeMail\":\"true\",\"email\":\"\"},{\"isSendPhone\":\"true\",\"eid\":\"7682130\",\"phoneMessgge\":\"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。\",\"readsendtime\":\"9\",\"countdown\":\"15\",\"count\":\"50\",\"serviceName\":\"经理人自助服务\",\"startdate\":\"2022-02-17 00:00:00.0\",\"insertTime\":\"2023-02-02 07:00:38.0\",\"enddate\":\"2023-02-16 23:59:59.0\",\"emailMessage\":\"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。\",\"phone\":\"\",\"companyname\":\"生物科技股份有限公司\",\"serviceId\":\"2\",\"isSendeMail\":\"true\",\"email\":\"\"}]";
        
        str =  str.trim();
        String formatStr = null;
        if (str.startsWith("[")) {
            JSONArray data = JSON.parseArray(str);
            formatStr = JSONArray.toJSONString(data, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
        } else {
            JSONObject strJson = JSONObject.parseObject(str);
            formatStr = JSON.toJSONString(strJson, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
        }
        System.out.println(formatStr);
```

输出结果：

```java
[
	{
		"isSendPhone":"true",
		"id":"22258352",
		"phoneMessgge":"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
		"readsendtime":"9",
		"countdown":"7",
		"count":"5",
		"serviceName":"流程助手",
		"startdate":"2022-02-09 00:00:00.0",
		"insertTime":"2023-02-02 07:00:38.0",
		"enddate":"2023-02-08 23:59:59.0",
		"emailMessage":"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
		"phone":"",
		"companyname":"XX顾问有限责任公司",
		"serviceId":"21",
		"isSendeMail":"true",
		"email":""
	},
	{
		"isSendPhone":"true",
		"eid":"7682130",
		"phoneMessgge":"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
		"readsendtime":"9",
		"countdown":"15",
		"count":"50",
		"serviceName":"经理人自助服务",
		"startdate":"2022-02-17 00:00:00.0",
		"insertTime":"2023-02-02 07:00:38.0",
		"enddate":"2023-02-16 23:59:59.0",
		"emailMessage":"为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
		"phone":"",
		"companyname":"XX科技股份有限公司",
		"serviceId":"2",
		"isSendeMail":"true",
		"email":""
	}
]
```



# jwt

[一文搞懂JWT以及SpringBoot如何整合JWT_springboot jwt starter_五维星空的博客-CSDN博客](https://blog.csdn.net/qq_38074535/article/details/127231999)

[SpringBoot 中使用 JWT 案例分享详解_springboot+jwt_Java Fans的博客-CSDN博客](https://hh419.blog.csdn.net/article/details/131191030?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromBaidu~Rate-1-131191030-blog-127231999.235^v38^pc_relevant_sort_base1&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromBaidu~Rate-1-131191030-blog-127231999.235^v38^pc_relevant_sort_base1&utm_relevant_index=1)

[SpringBoot + jwt 详解+使用案例_springboot jwt_一位不知名民工的博客-CSDN博客](https://blog.csdn.net/zkcJava/article/details/119935284)

##  认证流程

JWT（JSON Web Token）是一种用于身份验证和授权的开放标准（RFC 7519），它使用JSON格式传输信息，可以在不同系统之间安全地传递数据。JWT由三部分组成：头部、载荷和签名。头部包含算法和类型信息，载荷包含用户信息和其他元数据，签名用于验证JWT的真实性和完整性。JWT的优点包括可扩展性、跨平台、无状态和安全性高等。它被广泛应用于Web应用程序、移动应用程序和API等领域。

**JWT 身份认证流程**

<img src="spring boot3.assets/image-20230925164002188.png" alt="image-20230925164002188" style="zoom:80%;" />

1. 首先，前端通过Web表单将⾃⼰的⽤⼾名和密码发送到后端的接⼝。这⼀过程⼀般是⼀个HTTP **POST请求**。建议的⽅式是通过SSL加密的传输(https协议) ，从⽽避免敏感信息被嗅探。
2. 后端核对⽤⼾名和密码成功后，将**⽤⼾的id等其他信息作为JWT Payload (负载)**，将其与头部分别 进⾏Base64编码拼接后签名，形成⼀个JWT(Token)。形成的JWT就是⼀个形同11. zzz. xxx的字符 串。**token head.payload.signature**
3. 后端将JWT字符串作为登录成功的返回结果**返回给前端**。 前端可以将返回的结果保存在 localStorage或sessionStorage上， 退出登录时前端删除保存的JWT即可。
4. 前端在每次请求时将JWT**放⼊HTTP Header中的Authorization位**。 (解决XSS和XSRF问题)
5. 后端检查是否存在，如存在验证JWT的有效性。
   - 检查签名是否正确;
   - 检查Token是否过期;
   - 检查Token的接收⽅是否是⾃⼰(可选)
6. 验证通过后后端使⽤JWT中包含的⽤⼾信息进⾏其他逻辑操作，返回相应结果。

##  JWT具体包含信息

==使用json的方式存储, 一般搭配反序列化使用==

**header**

标头通常由两部分组成: **令牌的类型(即JWT)** 和**所使⽤的签名算法**，例如HMAC、SHA256或RSA。它会使⽤Base64编码组成JWT结构的第⼀部分。

注意:Base64是⼀ 种编码，也就是说，它是可以被翻译回原来的样⼦来的。它并不是⼀种加密过程。

```json
{
    "alg":"HS256",
    "typ":"JWT"
}
```

**Payload**

令牌的第⼆部分是有效负载，其中包含声明。**声明是有关实体(通常是⽤⼾)和其他数据的声明**。同样的，它会使⽤Base64编码组成JWT结构的第⼆部分。

```json
{
    "sub" : "HS256"
    "name" : "yjiewei"
    "admin" : "true"
}
```

**Signature**

header和payload都是结果Base64编码过的，中间⽤.隔开，第三部分就是前⾯两部分合起来做签 名，密钥绝对⾃⼰保管好，签名值同样做Base64编码拼接在JWT后⾯。（签名并编码）

```java
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```

算出签名以后，把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（.）分隔，就可以返回给用户

其结构看起来是这样的

```java
xxxxx.yyyyy.zzzzz
```

## 相关函数

### 存

JWT是一种用于在网络间传递声明的开放标准（RFC 7519），通常用于身份认证和授权。JWT由三部分组成：头部（Header）、载荷（Payload）、签名（Signature）。下面是JWT中常用的方法及其对应的作用：

1. **create()**： 创建一个JWT实例。

1. **withIssuer(String issuer)**： 设置JWT的签发者。

1. **withSubject(String subject)**： 设置JWT的主题。

1. **withAudience(String... audience)**： 设置JWT的接收者。

1. **withIssuedAt(Date issuedAt)**： 设置JWT的签发时间。

1. **withExpiresAt(Date expiresAt)**： 设置JWT的过期时间。

1. **withNotBefore(Date notBefore)**： 设置JWT的生效时间。

1. **withJWTId(String id)**： 设置JWT的唯一标识。

1. **withClaim(String name, Boolean value)**： 添加一个布尔类型的声明。

1. **withClaim(String name, Integer value)**： 添加一个整数类型的声明。

1. **withClaim(String name, Long value)**： 添加一个长整数类型的声明。

1. **withClaim(String name, Double value)**： 添加一个[双精度浮点数](https://so.csdn.net/so/search?q=双精度浮点数&spm=1001.2101.3001.7020)类型的声明。

1. **withClaim(String name, String value)**： 添加一个字符串类型的声明。

1. **withArrayClaim(String name, String[] array)**：添加一个字符串数组类型的声明。

1. **sign(Algorithm algorithm)**： 使用指定的算法对JWT进行签名。

1. **verify(Algorithm algorithm)**： 使用指定的算法对JWT进行验证。

1. **getHeader()**： 获取JWT的头部信息。

1. **getPayload()**：获取JWT的载荷信息。

1. **getSignature()**：获取JWT的签名信息。

使用JWT创建一个包含用户ID和用户名的JWT，并使用HS256算法对其进行签名。代码示例：

```java
javaCopy codeStringuserId="1234567890";
Stringusername="john.doe";
Stringsecret="mySecret";
Algorithmalgorithm= Algorithm.HS256;
Stringtoken= JWT.create()
    .withClaim("userId", userId)         // 放入用户信息, 可以通过key再取出来
    .withClaim("username", username)
    .sign(algorithm);
```

上面的代码中，首先创建一个JWT实例，然后使用**withClaim**方法添加一个**userId**和一个**username**声明，最后使用**sign**方法对JWT进行签名，并将签名后的JWT保存在**token**变量中。

### 取

DecodedJWT类它提供了一些常用的方法，下面是一些常用方法及其对应的作用：

1. **getClaim(String name)**：获取[JWT](https://so.csdn.net/so/search?q=JWT&spm=1001.2101.3001.7020)中指定名称的声明，返回一个**Claim**对象，可以通过**asXXX()**方法获取该声明的值，如**asInt()**、**asString()**等。如果指定名称的声明不存在，则返回一个空的**Claim**对象。

1. **getIssuer()**： 获取JWT的签发者。

1. **getSubject()**： 获取JWT的主题。

1. **getAudience()**： 获取JWT的接收者。

1. **getIssuedAt()**： 获取JWT的签发时间。

1. **getExpiresAt()**： 获取JWT的过期时间。

1. **getNotBefore()** ： 获取JWT的生效时间。

1. **getId()**： 获取JWT的唯一标识。

1. **getHeader()**： 获取JWT的头部信息。

1. **getAlgorithm()** 获取JWT使用的[加密算法](https://so.csdn.net/so/search?q=加密算法&spm=1001.2101.3001.7020)。

使用DecodedJWT类解码JWT，从中获取用户的ID和用户名，然后根据用户ID查询用户信息并返回。

代码示例：

```java
 String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwidXNlck5hbWUiOiJhZG1pbiIsImlhdCI6MTUxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
DecodedJWT jwt = JWT.decode(jwtToken);
String userId = jwt.getClaim("userId").asString();
String username = jwt.getClaim("username").asString();
User user = userService.getUserById(userId);
return user;
```

上面的代码中，首先使用**JWT.decode**方法解码JWT，然后通过**getClaim**方法获取JWT中的**userId**和**username**声明，最后根据**userId**查询用户信息并返回。

## JWT整合

### 1. Maven依赖

```xml
<!-- JWT -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.10.3</version>
</dependency>
```

### 2.JWT工具类

JWT生成token和验签代码

具体测试代码见 [代码](C:\Users\16658\Documents\GitHub\java_note\note\spring boot3\JwtTest)

```java
package com.example.jwttest.utils;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwttest.entity.JsonResult;
import com.example.jwttest.entity.UserVo;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

		private static final String SECRET = "!Q@W#E$R^Y&U";
		//token签发者
		private static final String ISSUSRE = "HZSTYGZPT";
		//token过期时间(有效期)
		public static final Long EXPIRE_DATE = 60 * 1000*60L;

		/**
		 *  生成token
		 * @param userVo
		 * @return
		 */
		public static String Token(UserVo userVo){
			//当前时间
			Date now = new Date();
			//创建过期时间
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DATE,7);  //7天过期
			//1. header
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			String token = JWT.create()
					.withIssuer(ISSUSRE)
					.withIssuedAt(now)
					.withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
					.withClaim("userName", userVo.getName())   // 存放user中的内容,UserTokenDTO中不带有敏感信息，如password字段不会出现在token中
					.withClaim("userId", userVo.getId())
					.sign(algorithm);
			return token;
		}

		/**
		 *  生成token
		 * @param userVo
		 * @param expireDate 过期时间
		 * @return
		 */
//		public static String Token(UserVo userVo, Long expireDate){
//			//当前时间
//			Date now = new Date();
//			//创建过期时间
//			Date expire = new Date(now.getTime() + expireDate);
//			//1. header
//			Algorithm algorithm = Algorithm.HMAC256(SECRET);
//			String token = JWT.create()
//					.withIssuer(ISSUSRE)
//					.withIssuedAt(now)
//					.withExpiresAt(expire)
//					.withClaim("userName", userVo.getName())
//					.withClaim("userId", userVo.getId())
//					.sign(algorithm);
//			return token;
//		}


		// 个人一般使用这个
		/**
		 *  生成token
		 * @param data  任意自定义对象
		 * @param expireDate 过期时间
		 * @return
		 */
		public static <T> String Token(T data, Long expireDate){
			//当前时间
			Date now = new Date();
			//创建过期时间
			Date expire = new Date(now.getTime() + expireDate);
			//1. header
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			String token = JWT.create()
					.withIssuer(ISSUSRE)
					.withIssuedAt(now)
					.withExpiresAt(expire)
					.withSubject(JSON.toJSONString(data))
					.sign(algorithm);
			return token;
		}

		/* 调用
		*UserVo user1 = new UserVo();
		*user1.setName("神神叨叨");
		*user1.setId(100);
		* 设置UserVo的相关属性
		*Long expireDate = 1000 * 60 * 60 * 24L; // 设置为1天过期时间
		* n天:    24L * 60 * 60 * 1000 * n
		* n小时:  60L * 60 * 1000 * n
		*String token = jwtUtil.Token(user1, expireDate);
		* */

		/**
		 *  生成token
		 * @param map
		 * @return
		 */
		public static String createToken(Map<String,String> map){
			//创建过期时间
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DATE,7);  //7天过期

			//创建builder对象
			JWTCreator.Builder builder = JWT.create();
			//遍历map
			map.forEach((k,v)->{
				builder.withClaim(k,v);
			});
			String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SECRET));
			return token;
		}

//		/**
//		 *  验证token
//		 *  验证过程中如果有异常，则抛出；
//		 *  如果没有,则返回 DecodedJWT 对象来获取用户信息;
//		 * @param token
//		 */
		public static JsonResult verifyToken(String token, String username){
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			try {
				JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("userName", username).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(token);
				// DecodedJWT类中封装函数可以token中存放的信息
				return new JsonResult();
			}catch (SignatureVerificationException e) {
				//验证的签名不一致
				throw new SignatureVerificationException(algorithm);
			}catch (TokenExpiredException e){
				throw new TokenExpiredException("token is alreadey expired");
			}catch (AlgorithmMismatchException e){
				throw new AlgorithmMismatchException("签名算法不匹配");
			}catch (InvalidClaimException e){
				throw new InvalidClaimException("校验的claims内容不匹配");
			}catch (Exception e){
				e.printStackTrace();
			}
			return new JsonResult("2000","用户和jwt-token校验失败");
		}

		/**
		 *  验证token
		 *  验证过程中如果有异常，则抛出；
		 *  如果没有,则返回 DecodedJWT 对象来获取用户信息;
		 * @param token
		 */
		public static DecodedJWT verify(String token){
			return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
		}

		/**
		 *
		 * @return 返回指定对象
		 * **/
		public static <T> T verify(String token, Class<T> clazz){
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
			String subject = decodedJWT.getSubject();
			// 将JWT主体转换为指定类型的对象并返回
			return JSON.parseObject(subject, clazz);
		}
}
```

通用JsonResult

```java
package com.example.jwttest.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-09-26 14:14
 */

/**
 * 通用JsonResult
 */
@Data
public class JsonResult {
	private static final long serialVersionUID =-123847128341023033L;
	@JSONField
	private boolean success = true;
	@JSONField
	private String message = null;
	@JSONField
	private String errorCode = "0";
	@JSONField
	private String errorMsg = "";
	@JSONField
	private Integer total = 0;
	@JSONField
	private List data = new ArrayList();
//	private T data;

	public JsonResult() {

	}

	/**
	 * 当有异常时，直接throw一个实现ErrorCode的异常类
	 * 通过global异常处理器，就可以把jsonResult封装起来，这样代码简洁优美
	 * 如果没有BaseException可以注释掉这个方法
	 */
//    public JsonResult(BaseException exception) {
//        if (exception != null) {
//            success = false;
//            errorCode = exception.getErrorCode();
//            errorMsg = exception.getErrorMsg();
//        }
//    }

	/**
	 *  虽然很多人都写为isSuccess(),但强烈不建议，因为相当于getSuccess()
	 *  可以用idea的自动生成下，如果有isSuccess()，就不会生成getSuccess()
	 */
	public boolean successFlag() {
		return success;
	}

	public JsonResult(List data) {
		if (data != null && data.size() > 0) {
			this.data = data;
			this.message = "操作成功";
		}else {
			this.message = "操作失败";
			this.errorCode = "405";
			this.errorMsg = "返回值为null";
		}
	}
//
	public <T> void JsonResult(T data) {
		if (data != null) {
			this.data.add(data);
			this.message = "操作成功";
		}else {
			this.message = "操作失败";
			this.errorCode = "405";
			this.errorMsg = "返回值为null";
		}
	}
	//  失败情况的构造  只用errorCode，errorMsg即可
	public JsonResult(String errorCode,String errorMsg) {
		this.success=false;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}

}

```

测试'

```java
package com.example.jwttest;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwttest.entity.UserVo;
import com.example.jwttest.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class JwtTestApplicationTests {

	@Autowired
	private JwtUtil jwtUtil;

	@DisplayName("测试1  Token(UserVo userVo)")
	@Test
	void test01() {
		UserVo user = new UserVo();
		user.setName("miaowing");
		user.setId(100);

		String token = jwtUtil.Token(user);
		System.out.println(token);
	}

	@DisplayName("测试2 createToken(Map<String,String> map)")
	@Test
	void test02(){
		HashMap<String, String> map = new HashMap<>();
		map.put("userName", "小明");
		map.put("userId", "11");
		String token = jwtUtil.createToken(map);
		System.out.println(token);
	}


	@DisplayName("verify(String token)")
	@Test
	void  test03(){
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuWwj-aYjiIsImV4cCI6MTY5NjMyODY0NiwidXNlcklkIjoiMTEifQ.d8Y7G2-mFur7s6lA5lKf-JxwqFENYmgLlij6-g874wc";
		jwtUtil.verifyToken(token, "小明");
	}

	@DisplayName("verify(String token, Class<T> clazz)")
	@Test
	void test04(){
		UserVo user1 = new UserVo();
		user1.setName("神神叨叨");
		user1.setId(100);
		// 设置UserVo的相关属性
		Long expireDate = 1000 * 60 * 60 * 24L; // 设置为1天过期时间
		String token = jwtUtil.Token(user1, expireDate);

		//解析
		UserVo user2 = jwtUtil.verify(token,UserVo.class);
		System.out.println(user2.getName());

	}

}
```

符合预期

### 3. WebMvcConfigurer注册拦截器

3.4可以看WebMvcConfigurer 和 WebMvcConfigurationSupport文章

```java
package com.xxx.xxx.config.web;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 注册拦截器
     * @return
     */
    @Bean
    public JwtInterceptor getJwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**")   //其他接口保护 token验证
                .excludePathPatterns("/user/**")  //所有用户接口都放行
                .excludePathPatterns("/favicon.ico");
    }

    /**
     * 解决 No mapping for GET /favicon.ico 访问静态资源图标
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler("/qy/**") 所有/qy/开头的请求 都会去后面配置的路径下查找资源
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        //图片真实存放的路径
        //registry.addResourceHandler("/upload/avatar/**").addResourceLocations("file:"+System.getProperty("user.dir")+"/upload/avatar/");
        super.addResourceHandlers(registry);
    }
}
```

或者

```java
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport  {
   
	@Autowired
	private TokenHandler tokenHandler;
    


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        String checkLogin = "/pushlogin/checkIsCanLogin";
        String login = "/pushlogin/login";
        String getVerifyCode = "/common/send";
        String verfifyMethod = "/common/validationCode";
        excludePath.add(checkLogin);
        excludePath.add(login);
        excludePath.add(getVerifyCode);
        excludePath.add(verfifyMethod);
        registry.addInterceptor(tokenHandler).excludePathPatterns(excludePath);
    }
}
```





### 4.自定义jwt拦截器(token校验)

实现 HandlerInterceptor,   看有没有token认证

```java
package com.xxx.xxx.interceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String AUTH = "authorization";
    private static final String AUTH_USER_NAME = "user-name";

    @Value("${spring.profiles.active}")
    private String profiles;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setContentType("application/json;charset=utf-8");

        //如果是开发环境，则不需要token。直接通过
        if(StrUtil.isNotEmpty(profiles) && profiles.equals("dev")){
            return true;
        }


        //如果接口或者类上有@IgnoreToken注解，意思该接口不需要token就能访问，需要放行
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //先从类上获取该注解，判断类上是否加了IgnoreToken ，代表不需要token，直接放行
        IgnoreToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreToken.class);
        if(annotation == null){
            //再从方法上获取该注解
            if(method.isAnnotationPresent(IgnoreToken.class)){
                //annotation = handlerMethod.getMethodAnnotation(IgnoreToken.class);
                annotation = method.getAnnotation(IgnoreToken.class);
                log.info("请求方法 {} 上有注解 {} ",method.getName(),annotation);
            }
        }
        if(annotation != null){
            return true;
        }

        String token = getParams(request, AUTH);
        String username = getParams(request, AUTH_USER_NAME);

        if (StrUtil.isEmpty(token)) {
            throw new ValidationException("Authorization不允许为空，请重新登录");
        }

        if (StrUtil.isEmpty(username)) {
            throw new ValidationException("用户名不允许为空，请重新登录");
        }
        JsonResult jsonResult = JWTUtil.verifyToken(token, username);
        if (jsonResult.getCode() != 200) {
            log.error("token valid error!");
            return false;
        }
        return true;
    }
```

或者

```java
@Component
@Slf4j
public class TokenHandler implements HandlerInterceptor{
	
		@Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
	 
	        String token = request.getHeader("Authentication");
	        if (token != null){
	            boolean result = TokenUtil.verify(token);
	            if(result){
	                log.info("通过拦截器");
	                return true;
	            }
	        }
	        log.info("认证失败");
	        
	        return false;
	   }
	
}
```

### 5. 用户登录时验证用户信息后，返回Token信息

```java
 @PostMapping("/user/jwtLogin")
    public JsonResult jwtLogin(String username, String password,HttpServletResponse response)  {
        //1. 判断用户名密码是否为空
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("用户名或者密码为空");
        }

        MUser user = userService.getUserByUsername(username);

        if(user ==null) {
            throw new ValidationException("用户名或者密码错误");
        }
        //对密码进行加密
        password = SecureUtil.md5(password);
        if(!password.equalsIgnoreCase(user.getPassword())){
            throw new ValidationException("用户名或者密码错误");
        }

        UserVo userVo = new UserVo();
        userVo.setUserId(user.getId());
        userVo.setUsername(user.getUsername());

        //生成token
        String token = JWTUtil.Token(userVo);

        //写入token
        userVo.setToken(token);
        //刷新token的key
        userVo.setRefreshToken(UUID.randomUUID().toString());
        //存储用户信息到redis中
        redisTemplate.opsForValue().set(userVo.getRefreshToken(),userVo,JWTUtil.EXPIRE_DATE, TimeUnit.SECONDS);

        response.setHeader("authorization",token);
        //response.setHeader("user-name",username);

        return new JsonResult<>(userVo);

    }
```

### 6. 续签的方法

```java
@GetMapping("/user/refresh")
    public JsonResult refreshToken(String refreshToken){
        //根据 key 为 refreshToken，获取当前登录的用户信息是否还在有效期内
        UserVo userVo = (UserVo) redisTemplate.opsForValue().get(refreshToken);

        if(userVo ==null){
            //告诉用户 token已经失效，需要重新登录
            return new JsonResult().error("用户信息已不存在，需重新登录");
        }

        //重新生成token
        String jwt = JWTUtil.Token(userVo);
        userVo.setToken(jwt);
        userVo.setRefreshToken(UUID.randomUUID().toString());


        //删除之前的用户信息
        redisTemplate.delete(refreshToken);
        //将刷新后的值存入redis  
        redisTemplate.opsForValue().set(userVo.getRefreshToken(),userVo,JWTUtil.EXPIRE_DATE, TimeUnit.SECONDS);
        return new JsonResult<>(userVo);
    }

```

### 四、学习中的坑

#### 1.在使用jjwt创建JWT时setSubject和setClaims值覆盖

1. 在创建jwt过程中，如果前面已经设置了setSubject，后面再设置setClaims的时候，会将前面内置的subject内容覆盖掉，但是claims里面的内容可以正常显示；原因是在setClaims的方法里面，已经有setSubject，所以会将之前设置的内容覆盖掉。
   解决方法：
   （1）使用setClaims这个方法的时候要将它移动到上面，避免覆盖。
   例如：改之前代码：

```java
 String token = Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject("11221")
                .setClaims(map)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,SIGN_KEY)
                .compact();
```

测试结果：
![在这里插入图片描述](spring boot3.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3prY0phdmE=,size_16,color_FFFFFF,t_70.png)
改之后代码块：

```javascript
String token = Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(map)
                .setSubject("11221")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,SIGN_KEY)
                .compact();
1234567
```

测试结果：
![在这里插入图片描述](spring boot3.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3prY0phdmE=,size_16,color_FFFFFF,t_70-169563155729232.png)
（2）不使用 builder.setClaims(map) 封装 。

```java
@Test
    void createJWT(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId","1111");
        map.put("name","张某");
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+604800*1000);
        System.out.println("=======第一种写法=======");
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(map)
                .setSubject("11221213131")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,SIGN_KEY)
                .compact();
        System.out.println(token);

        System.out.println("=======第二种写法=======");
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject("11221")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY);
        //第一种
        map.forEach((k,v)->{
            jwtBuilder.claim(k,v);
        });
        //第二种
        Set<String> keys = map.keySet();
        for (String key : keys){
            jwtBuilder.claim(key,map.get(key));
        }
        //第三种
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        String token1 = jwtBuilder.compact();
        System.out.println(token1);
```

1. 载荷部分存在两个属性：payload和claims。两个属性均可作为载荷，jjwt中二者只能设置其一，如果同时设置，在终端方法compact() 中将抛出异常。

# WebMvcConfigurer 和 WebMvcConfigurationSupport

WebMvcConfigurer 是一个接口，用于配置全局的[SpringMVC](https://so.csdn.net/so/search?q=SpringMVC&spm=1001.2101.3001.7020)的相关属性，采用JAVABean的方式来代替传统的XML配置文件，**提供了跨域设置、静态资源处理器、类型转化器、注册拦截器、页面跳转，消息转换器**等能力。

WebMvcConfigurationSupport是webmvc的配置类，如果在springboot项目中，有配置类继承了WebMvcConfigurationSupport，那么webmvc的自动配置类WebMvcAutoConfiguration就会失效。

官方推荐

- 实现WebMvcConfigurer (推荐)
- 继承WebMvcConfigurationSupport

## 一、WebMvcConfigurer

==**提供了跨域设置、静态资源处理器、类型转化器、注册拦截器、页面跳转，消息转换器**==

**WebMvcConfigurer实现要加@EnableWebMv:**

```
@EnableWebMvc注解类上导入了DelegatingWebMvcConfiguration类，该类是WebMvcConfigurationSupport的子类，该类除了实例化WebMvcConfigurationSupport实例以外，另一个作用就是收集BeanFactory中所有WebMvcConfigurer的实现，汇集到WebMvcConfigurerComposite中，在WebMvcConfigurationSupport实例化过程中会分别调用这些实现，将相应的实例传入这些实现中，供开发者在此基础上添加自定义的配置。

这也就是在WebMvcConfigurerAdapter子类上要加@EnableWebMvc的原因，因为要先实例化WebMvcConfigurationSupport。**
```

注意：springboot下不需要添加@EnableWebMvc

**可以存在多个WebMvcConfigurer的实现:**

```
一般来讲一个应用中一个WebMvcConfigurer的已经足够，设计成收集多个是不是有些多余？从springboot的autoconfigure机制来看并不多余，反而更灵活，比如我要写一个mybatis的AutoConfiguration和JPA的AutoConfiguration，我就可以在不同的AutoConfiguration里面定义一个WebMvcConfigurer的实现，里面只配置与mybatis或JPA相关的配置，这样需要那个启用那个，不需要人工通过注释代码来转换mybatis和JPA，
```

### 常用的方法API

```java
// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
void addInterceptors(InterceptorRegistry var1);
/* 视图跳转控制器 */
void addViewControllers(ViewControllerRegistry registry);
// 这个方法是用来配置静态资源的，比如html，js，css，等等
void addResourceHandlers(ResourceHandlerRegistry registry);
/* 默认静态资源处理器 */
void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
/* 这里配置视图解析器*/
void configureViewResolvers(ViewResolverRegistry registry);
/* 配置内容裁决的一些选项*/
void configureContentNegotiation(ContentNegotiationConfigurer configurer);
/** 解决跨域问题 **/
public void addCorsMappings(CorsRegistry registry) ;
//拓展mvc框架的消息转换器
void extendMessageConverters(List<HttpMessageConverter<?>> var1);
```

 例子

addInterceptors：拦截器

```java
@Autowired
    private LoginInterceptor loginInterceptor;
    // 这个方法用来注册拦截器，自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").
                excludePathPatterns("/employee/login", "/employee/register","/backend/**","/front/**");
    }
```

 addViewControllers：页面跳转, 比如登录跳转或错误页面跳转

以前写SpringMVC的时候，如果需要访问一个页面，必须要写Controller类，然后再写一个方法跳转到页面，感觉好麻烦，其实重写WebMvcConfigurer中的addViewControllers方法即可达到效果了

```java
  @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toLogin").setViewName("login");
     
        registry.addViewController("/403").setViewName("/403");
        registry.addViewController("/404").setViewName("/404");
    }
```

值的指出的是，在这里重写addViewControllers方法，并不会覆盖WebMvcAutoConfiguration（Springboot自动配置）中的addViewControllers（在此方法中，Spring Boot将“/”映射至index.html），这也就意味着自己的配置和Spring Boot的自动配置同时有效，这也是我们推荐添加自己的MVC配置的方式。

 extendMessageConverters : 消息转换器

```java
//拓展mvc框架的消息转换器
    @Autowired
    private CustomObjectMapper customObjectMapper;
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置消息转换器,底层使用jackson将Java对象转成json
        messageConverter.setObjectMapper(customObjectMapper);
        //将我们自定义的消息转换器追加到mvc框架的集合转换器中
        converters.add(0, messageConverter);
    }
```

### 基本使用

==**WebMvcConfigurer提供了跨域设置、静态资源处理器、类型转化器、注册拦截器、页面跳转，消息转换器**==

```java
package com.syh.pdd.config.web;
 
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
 
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
 
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    // 用户头像
    @Value("${file.userImage.writePath}")
    private String userImageWritePath;
    @Value("${file.userImage.readPath}")
    private String userImageReadPath;
 
    /**
     * 映射文件路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
 
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
 
        registry.addResourceHandler("BJTPReadpath")
                .addResourceLocations("file:"+ "BJTPSavepath");
 
 
                // 图片回显路径，“/**”是当前文件夹以及子文件夹
        registry.addResourceHandler(userImageReadPath)
                // 图片存放路径
                .addResourceLocations("file:" + userImageWritePath);
    }
 
    /**
     * 跨域配置添加
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
              //  .allowedOrigins("*")
                .allowedOriginPatterns("*")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }
 
    //解决中文乱码问题
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //解决中文乱码
        converters.add(responseBodyConverter());
        //解决 添加解决中文乱码后 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
        converters.add(messageConverter());
    }
    
    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }
    
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
 
    /**
     *  格式化返回的内容
     *  https://my.oschina.net/u/3681868/blog/3075150
     * */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置格式化内容
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }
 
    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //放行路径
        List<String> jwtExcludePatterns = new ArrayList();
        // 登录接口放行
        jwtExcludePatterns.add("/api/user/login");
        // 验证码放行
        jwtExcludePatterns.add("/api/user/getVerify/**");
        // 前端更换头像请求，没有走拦截器，此处放行
        jwtExcludePatterns.add("/api/user/updatePicture");
        // 对所有api开头的访问路径，都通过MyInterceptor拦截器进行拦截，MyInterceptor代码在下面
       	// addPathPatterns() 拦截-->找自定义拦截器(一般是看有没有token)    
        // excludePathPatterns() 放行
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns(jwtExcludePatterns);
    }
 
    /*解析器  使用方法在文章末尾*/
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
```

### 自定义拦截器(token校验)

**什么是拦截器**：在AOP（Aspect-Oriented Programming）中用于在某个方法或字段被访问之前，进行拦截然后在之前或之后加入某些操作。拦截是AOP的一种实现策略

**为什么需要拦截器**：在做身份认证或者是进行日志的记录时，我们需要通过拦截器达到我们的目的。最常用的登录拦截、或是权限校验、或是防重复提交、或是根据业务像12306去校验购票时间,总之可以去做很多的事情

**自定义拦截器**

一般是看有没有token认证

```xml
        <dependency>
            <groupId>net.minidev</groupId>
            <artifactId>json-smart</artifactId>
        </dependency>
        <dependency>
            <groupId>com.vaadin.external.google</groupId>
            <artifactId>android-json</artifactId>
            <version>0.0.20131108.vaadin1</version>
            <scope>compile</scope>
        </dependency>
```

```java
package com.xxx.xxx.config.web;
 
import com.syh.pdd.Utils.token.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * 自定义拦截器类token认证
 * 注意：可以实现多个拦截器，只需要继续实现HandlerInterceptor即可            
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // ==========!!!!!!注意注意注意   注意注意   注意注意   ========================
        /*
         * 前端在请求的时候会发送一个OPTION请求来验证本次请求是否安全，
         * 但是springboot的拦截器会拦截所有请求。因为第一次是OPTION没有携带JWT，所以验证失败
         * */
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        // 获取token
        String token = request.getHeader("token");
        // 校验token
        if (JwtUtil.checkToken(token)) {
            log.info(request.getRequestURL() + ">>>" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "通过token验证");
            return true; // 放行
        } else{
            //设置response状态
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            //设置失败响应数据
            JSONObject res = new JSONObject();
            res.put("status","101010");
            res.put("msg","登录过期，请重新登录");
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
            return false; // 拦截
        }
    }
 
    /**
     * 访问控制器方法后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }
 
    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
```

**WebMvcConfigurer注册拦截器**

```java
/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //放行路径
        List<String> jwtExcludePatterns = new ArrayList();
        // 登录接口放行
        jwtExcludePatterns.add("/system/user/login");
        // 验证码放行
        jwtExcludePatterns.add("/system/user/getVerify/**");
        // 对所有图片资源放行
        jwtExcludePatterns.add("/Project/saveFile/**");
 
        // 拦截路径：对所有访问路径，都通过MyInterceptor类型的拦截器进行拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(jwtExcludePatterns);
 
    }
}
```

### 简单案例

#### 定义拦截器

```java
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        //System.out.println(request.getRequestURI() +" 待验证："+token);
        if(token != null){
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + " 放行...");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI() + " 禁止访问...");
        
        // 服务器返回
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(20003,"jwt令牌无效，请重新登录")));
        return false;
    }
}
```

#### WebMvcConfigurer注册拦截器

```java
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        registration.addPathPatterns("/**") //拦截-->找自定义拦截器
                .excludePathPatterns(
                        "/user/login",
                        "/user/info",
                        "/user/logout",
                        "/error",
                        "/swagger-ui/**",             // 放行swagger
                        "/swagger-resources/**",
                        "/v3/**");
    }
}
```

## 二、WebMvcConfigurationSupport

```java
package com.xxx.xxx.config.web;
 
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
 
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
 
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
 
    // 自定义一个拦截器
    @Autowired
    UserArgumentResolver userArgumentResolver;
 
 
    /**
     * 映射文件路径配置
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
 
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
 
 
        /**
         * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
         用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
         */
        //registry.addResourceHandler("/pic/**").addResourceLocations("file:E:/pic/");
 
 
 
        registry.addResourceHandler("BJTPReadpath")
                .addResourceLocations("file:"+ "BJTPSavepath");
 
 
        //阿里云(映射路径去除盘符)
        //registry.addResourceHandler("/ueditor/image/**").addResourceLocations("/upload/image/");
        //registry.addResourceHandler("/ueditor/video/**").addResourceLocations("/upload/video/");
 
        //用户图片路径
        registry.addResourceHandler("/Project/saveFile/user/userImg/**"(网络路径，其实可以任意定义))
                .addResourceLocations("file:D:/JAVA/Project/saveFile/user/userImg/"(储存路径));
 
        super.addResourceHandlers(registry);
    }
 
 
    /**
     * 跨域配置添加
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法 springboot较高版本不能使用*号
                // .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }
 
 
    //解决中文乱码问题
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //解决中文乱码
        converters.add(responseBodyConverter());
        //解决 添加解决中文乱码后 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
        converters.add(messageConverter());
    }
 
 
    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }
    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
 
 
    /**
     *  格式化返回的内容（格式转换器）
     *  https://my.oschina.net/u/3681868/blog/3075150
     * */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        // 生成JSON时,将所有Long转换成String
        //SimpleModule simpleModule = new SimpleModule();
        //simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        //simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        //objectMapper.registerModule(simpleModule);
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置格式化内容
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }
 
    /**
     * 解析器，该方法可实现可不实现，需要自定义。作用在调用Controller方法的参数传入之前，有返回值
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // userArgumentResolver该类对Controller传入的参数做了具体处理
        resolvers.add(userArgumentResolver);
    }
}
```

#### 解析器

**（1）自定义解析器**

```java
@Configuration
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
 
    @Autowired
    Userservice userservice;
 
    // 此方法返回true，下面的参数才会执行
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 获取传入参数的类型
        Class<?> type = methodParameter.getParameterType();
        // 如果参数类型有为User类的则符合,进入resolveArgument方法
        if (UserPojo.class == type) {
            return true;
        }
        return false;
    }
 
    // 该方法为拦截方法，将结果返回给controller
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
 
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        
        String userTick = CookieUtil.getCookieValue(request, "userTicket");
        if (StringUtils.isBlank(userTick)) {
            return null;
        }
        UserPojo userPojo = userservice.getUserByCookie(userTick, request, response);
        if (userPojo == null) {
            return null;
        }
        return userPojo;
    }
}
```

**（2）在controller中使用**

```java
@GetMapping("goods")
public Result showGoods(UserPojo user){
    // 注意：这里的User参数不是由前端传入的，而是由addArgumentResolvers方法处理之后传进来的
    log.info(user.toString());
    // 根据处理之后传入的参数判断是否登录
    if (user == null)
        return Result.error();
    return Result.ok();
}
```

# web安全

+ Apache Shiro
+ Spring Security
+ Fllter

## 集成springsecurity

[SpringSecurity (yuque.com)](https://www.yuque.com/aomsir/study/zdogyy#neGfS)

## 安全架构

### 认证： Authentication

who are you?
登录系统，用户系统

认证就是让系统知道我们是谁。认证的方式有很多，常见的账号密码登录，手机验证码登录，指纹登录，刷脸登录等等

![img](spring boot3.assets/1667737807166-7e3d7227-7dfd-4813-9f47-8bf22efe5f23.png)

### 授权：  Authorization

what are you allowed to do?
权限管理，用户授权

 授权是用户认证通过后根据用户的权限来控制用户访问资源的过程，拥有资源的访问权限则正常访问，没有权限则拒绝访问。

![img](spring boot3.assets/1667737821930-2ef90891-5801-4c29-b4b4-9f4025374260.png)

![image.png](spring boot3.assets/1667781052294-95ee4c11-16d2-4ed8-9cee-9b05ff52ca4b.png)

扩展.权限模型

**1.RBAC(Role Based Access Controll)**

用户（t_user）
○id,username,password，xxx
○1,zhangsan
○2,lisi 
●用户_角色（t_user_role）【N对N关系需要中间表】
○zhangsan, admin
○zhangsan,common_user
○lisi, hr
**○**lisi, common_user
●角色（t_role）
○id,role_name
○admin
○hr
○common_user
●角色_权限(t_role_perm)
○admin, 文件r
○admin, 文件w
○admin, 文件执行
○admin, 订单query，create,xxx
**○**hr, 文件r
●权限（t_permission）
○id,perm_id
○文件 r,w,x
○订单 query,create,xxx

**2. ACL(Access Controll List)**

直接用户和权限挂钩
●用户（t_user）
○zhangsan
○lisi
●用户_权限(t_user_perm)
○zhangsan,文件 r
○zhangsan,文件 x
○zhangsan,订单 query
●权限（t_permission）
○id,perm_id
○文件 r,w,x
○订单 query,create,xxx

## Spring Security 原理(了解)

Spring Security利用 FilterChainProxy 封装一系列拦截器链，实现各种安全拦截功能

Servlet三大组件：Servlet、Filter、Listener

### 1. 过滤器链架构

![img](spring boot3.assets/1682513527616-e1e9054a-9049-4005-8c92-86392f3012a2.png)

### 2. FilterChainProxy

![img](spring boot3.assets/1682513900851-013516c0-b3d4-4a09-823a-e924a5fa8f2c.png)

### 3. SecurityFilterChain

![image.png](spring boot3.assets/1683548784456-8c66fd8e-783e-4f89-b81f-d3f2771a3ef9.png)

##  常用函数

### 1. HttpSecurity

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/match1/**")
      .authorizeRequests()
        .antMatchers("/match1/user").hasRole("USER")
        .antMatchers("/match1/spam").hasRole("SPAM")
        .anyRequest().isAuthenticated();
  }
}
```

### 2. MethodSecurity

```java
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SampleSecureApplication {
}

@Service
public class MyService {

  @Secured("ROLE_USER")
  public String secure() {
    return "Hello Security";
  }

}
```

核心

- **WebSecurityConfigurerAdapter**
- @**EnableGlobalMethodSecurity**： 开启全局方法安全配置

- - @Secured
  - @PreAuthorize
  - @PostAuthorize

- **UserDetailService： 去数据库查询用户详细信息的service（用户基本信息、用户角色、用户权限）**

## 基本使用

**Web安全性运行**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}
```

## 整合springsecurity

<img src="spring boot3.assets/image-20230926100844425.png" alt="image-20230926100844425" style="zoom:67%;" />

```xml
   <!-- spring security 安全框架 -->
    <dependency>          
        <groupId>org.springframework.boot</groupId>  
        <artifactId>spring-boot-starter-security</artifactId>  
    </dependency>  
```

 首页

```
<p>Click <a th:href="@{/hello}">here</a> to see a greeting.</p>
```

Hello页

```java
<!DOCTYPE html>
<html
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:th="https://www.thymeleaf.org"
  xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity6"
>
  <head>
    <title>Hello World!</title>
  </head>
  <body>
    <h1 th:inline="text">
      Hello <span th:remove="tag" sec:authentication="name">thymeleaf</span>!
    </h1>
    <form th:action="@{/logout}" method="post">
      <input type="submit" value="Sign Out" />
    </form>
  </body>
</html>
```

 login页

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
  <head>
    <title>Spring Security Example</title>
  </head>
  <body>
    <div th:if="${param.error}">Invalid username and password.</div>
    <div th:if="${param.logout}">You have been logged out.</div>
    <form th:action="@{/login}" method="post">
      <div>
        <label> User Name : <input type="text" name="username" /> </label>
      </div>
      <div>
        <label> Password: <input type="password" name="password" /> </label>
      </div>
      <div><input type="submit" value="Sign In" /></div>
    </form>
  </body>
</html>
```

配置类

 视图控制

```java
package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
}
```

**Security配置**

```java
package com.atguigu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author lfy
 * @Description
 * @create 2023-03-08 16:54
 */

/**
 * 1、自定义请求授权规则：http.authorizeHttpRequests
 * 2、自定义登录规则：http.formLogin
 * 3、自定义用户信息查询规则：UserDetailsService
 * 4、开启方法级别的精确权限控制：@EnableMethodSecurity + @PreAuthorize("hasAuthority('world_exec')")
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


         //请求授权
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()  //1、首页和/home页所有人都允许
                        .anyRequest().authenticated() //2、剩下的任意请求都需要 认证（登录）
                )
            
        //表单登录
        //3、表单登录功能：开启默认表单登录功能；Spring Security提供默认登录页
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login").permitAll(); //自定义登录页位置，并且所有人都能访问
        });
        
        // 退出
        http.logout((logout) -> logout.permitAll());

        return http.build();
    }

    //表单登录---查询用户详情
    @Bean
    public UserDetailsService userDetailsService() {
        // 实际开发的从数据库拿
        UserDetails zhangsan = User.withUsername("zhangsan")
                .password(passwordEncoder.encode("123456")) //使用密码加密器加密密码进行存储
                .roles("admin", "hr")
                .authorities("file_read", "file_write")
                .build();

        UserDetails lisi = User.withUsername("lisi")
                .password(passwordEncoder.encode("123456"))
                .roles("hr")
                .authorities("file_read")
                .build();

        UserDetails wangwu = User.withUsername("wangwu")
                .password(passwordEncoder.encode("123456"))
                .roles("admin")
                .authorities("file_write","world_exec")  //权限标识
                .build();

        //默认内存中保存所有用户信息
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(zhangsan,lisi,wangwu);
        return manager;
    }
    
    @Bean //密码加密器
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
```

**controller**

```java
package com.atguigu.boot3.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 21:45
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello!Spring Security";
    }

    @PreAuthorize("hasAuthority('world_exec')")   // 满足world_exec权限
    @GetMapping("/world")
    public String world(){
        return "Hello World!!!";
    }
}
```

```java
package com.atguigu.boot3.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 21:48
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
```



# 整合Swagger3.0详细使用

==springboot3 + Swagger3.0 会报错能官方更新吧==

## 一、简介

[Swagger](https://so.csdn.net/so/search?q=Swagger&spm=1001.2101.3001.7020) 是一个规范和完整的框架，用于生成、描述、调用和可视化RESTful风格的 Web 服务。常见组件如下：

- Swagger UI：提供了一个可视化的UI页面展示描述文件，可以在该页面中对相关接口进行查阅或做一些简单的接口请求
- Swagger Editor： 是一个基于Angular开源的编辑器，我们可以基于YAML等语法定义我们的RESTful API，支持实时预览
- Swagger Inspector：是API测试和生成文档的工具
- Swagger Codegen：是一个开源的代码生成器，通过Codegen 可以将描述文件生成html格式和cwiki形式的接口文档，同时也能生成多钟语言的服务端和客户端的代码
- Swagger Hub：是Swagger API 的一个集成服务网站，可提供 Swagger 的企业级服务需求

| 注解               | 含义（括号里是常用属性）                                     |
| ------------------ | ------------------------------------------------------------ |
| @API               | 用于类上，表示标识这个类是swagger的资源（tags，value，hidden） |
| @ApiOperation      | 用于方法上，表示一个http请求的动作（value，notes，httpMethod，hidden ） |
| @ApiParam          | 用于方法，参数或字段说明，表示对参数的添加元数据，说明或是否必填等（name，value，required） |
| @ApiModel          | 用于类上，表示对类进行说明，用于参数用实体类接收（value，description） |
| @ApiModelProperty  | 用于方法或字段，表示对model属性的说明或者数据操作更改（value，name，dataType，required，example，hidden） |
| @ApiIgnore         | 用于类，方法或方法参数上，表示这个类，或者方法或者参数被忽略（value） |
| @ApiResponses      | 用于方法上，方法返回对象的说明（多个 @ApiResponse）          |
| @ApiResponse       | 用于方法上，每个参数的说明（code，message，response）        |
| @ApiImplicitParams | 用于方法上，包含一组参数说明（多个 @ApiImplicitParam）       |
| @ApiImplicitParam  | 用于方法上，表示单独的请求参数（name，value，required，paramType，dataType，readOnly，allowEmptyValue，defaultValue） |

## 二 、依赖

```xml
<!---->
<!--未配置SpringBoot配置注释处理器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
<!--Swagger文档工具-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

## 三、核心配置类

**application.yml**

```yaml
swagger:
  enable: true
  title: Spring Boot整合Swagger3的演示案例
  description: 用户信息操作
  version: 1.0.0
  author: Alian
  url: https://blog.csdn.net/Alian_1223
  email: zhanglian1223@163.com
```

###  SwaggerProperties.java

==SwaggerProperties只配置:  文档相关的信息==

```java
package com.alian.swagger.config.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable = false;

    /**
     * 项目信息
     */
    private String title = "";

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 版本信息
     */
    private String version = "";

    /**
     * 作者
     */
    private String author = "";

    /**
     * url
     */
    private String url = "";

    /**
     * email
     */
    private String email = "";

}
```

### SwaggerConfig.java

整合过程的注意点：

- 注解@EnableOpenApi和@Configuration不能少
- 我这里使用的版本是DocumentationType.OAS_30
- 为了安全起见，建议生产上需要关闭掉文档生成功能，也就是本例中swaggerProperties.getEnable()为false；
- 生成https路径的文档，我个人觉得必要不大，测试或者开发环境http已经满足需求了
- 如果使用了apis(RequestHandlerSelectors.basePackage(“com.xxxx.xxx”))，而文档路径不在一个包下，则可以扫描共同的父路径；生成api的设置建议使用apis(RequestHandlerSelectors.withClassAnnotation(Api.class))，扫类上有@Api注解的类，虽说使用apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))扫描有@ApiOperation注解的方法，但是这个方法缺少一个类的说明信息，这几种方式都屏蔽掉basic-error-controller的文档。

```java
package com.alian.swagger.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .enable(swaggerProperties.getEnable())//生产禁用
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//方法一、扫描类上有@Api的，推荐，不会显示basic-error-controller
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//方法二、扫描方法上有@ApiOperation，但缺少类信息，不会显示basic-error-controller
//                .apis(RequestHandlerSelectors.basePackage("com.alian.swagger.controller"))//按包扫描,也可以扫描共同的父包，不会显示basic-error-controller
//                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())//标题
                .description(swaggerProperties.getDescription())//描述
                .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))//作者信息
                .version(swaggerProperties.getVersion())//版本号
                .build();
    }

}

```

### Swagger授权token配置

```java
package com.xxx.xxx.config;

import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableOpenApi
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lantu"))  //包名
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private SecurityScheme securityScheme() {
        //return new ApiKey("Authorization", "Authorization", "header");
        return new ApiKey("X-Token", "X-Token", "header"); // token数据
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(
                new SecurityReference("X-Token", authorizationScopes));
    }

        /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())//标题
                .description(swaggerProperties.getDescription())//描述
                .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))//作者信息
                .version(swaggerProperties.getVersion())//版本号
                .build();
    }
}
```

### 效果图

启动项目后，通过源码分析我们项目的访问路径可以为如下两种（记得不要漏了项目名swagger）：

- http://localhost:8080/swagger/swagger-ui/
- http://localhost:8080/swagger/swagger-ui/index.html

主页图：

![image-20230926223617371](spring boot3.assets/image-20230926223617371.png)

测试接口（根据姓名获取用户信息）：

![image-20230926223638731](spring boot3.assets/image-20230926223638731.png)





# 读取配置文件常用方式

[Spring Boot读取配置文件常用方式](https://blog.csdn.net/Alian_1223/article/details/118891954)

## @ConfigurationProperties方式(推荐)

```yml
# JWT配置
jwt:
  secret: "!Q@W#E$R^Y&U"
  issuer: "HZSTYGZPT"
  expire-date: 3600000
  app:
  	micro-applet:
  		app-id: wx4etd7e3803c6c555
  		secret-id: e6fa5627cc57437ac8cbe5e988288f80
```

注意: 

+ <font color = red>.properties类型文件映射规则，短横线(-)后面的首个字母会变成大写，同时注意有内部类时的写法</font>

+ 如果有内部类对象，加上@Data，不然无法映射数据
+ 配置类上记得加上@Component注解

配置类

```java
package com.xxx.xxx.config.properties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private String issuer;
    private Long expireDate;
    
    private MicroApplet microApplet;

    @Data
    public static class MicroApplet {
        private String appId = "";
        private String secretId = "";
    }
}
```

使用

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtProperties jwtProperties;

    public void someMethod() {
        
        // 使用配置参数进行JWT操作
        String secret = jwtProperties.getSecret();
        String issuer = jwtProperties.getIssuer();
        Long expireDate = jwtProperties.getExpireDate();
    }
}
```

## @Value方式

```yml
# JWT配置
jwt:
  secret: "!Q@W#E$R^Y&U"
  issuer: "HZSTYGZPT"
  expire-date: 3600000
```

使用

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expire-date}")
    private Long expireDate;

    public void someMethod() {
        // 使用配置参数进行JWT操作
        System.out.println("Secret: " + secret);
        System.out.println("Issuer: " + issuer);
        System.out.println("Expire Date: " + expireDate);
    }
}
```

## @PropertySource方式

有时候会有一些特殊意义的配置，会单独用一个配置文件存储，比如数据库配置连接参数，同样我们在application.properties同级目录新建一个配置文件alian.properties，内容如下：
**alian.properties**

```yaml
#博客用户
csdn.user-name=Alian
#博客密码
csdn.password=123456
#博客地址
csdn.blog-url=https://blog.csdn.net/Alian_1223
#博客描叙
csdn.blog-desp=来来来，一起聊干货！！！
```

新建一个配置类ALianProperties.java来完成映射
**ALianProperties.java**

```java
package com.alian.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "csdn")
@PropertySource(value = "classpath:alian.properties", encoding = "UTF-8", ignoreResourceNotFound = true) 
public class ALianProperties {

    private String userName;

    private String password;

    private String blogUrl;

    private String blogDesp;
}
```

注意：

- @ConfigurationProperties(value = “csdn”)表示配置文件里属性的前缀都是csdn开头
- @PropertySource中value属性表示指定配置文件的路径，encoding属性表示指定的是读取配置文件时的编码，记得和文件alian.properties的编码保持一致，ignoreResourceNotFound属性值为true时没找到指定配置文件的时候不报错
- 配置类上记得加上@Component注解
- .yml 格式不支持@PlaceSource注解

**测试**
**PlaceSourceService .java**

```java
package com.alian.properties.service;

import com.alian.properties.config.ALianProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PlaceSourceService {

    //直接使用spring的注解@Autowired引入即可
    @Autowired
    private ALianProperties ALianProperties;

    @PostConstruct
    public void testPlaceSource() {
        System.out.println("-------------------PlaceSource测试开始-------------------");
        System.out.println("Properties测试获取的数据库地址：" + ALianProperties.getBlogUrl());
        System.out.println("Properties测试获取的数据库用户名：" + ALianProperties.getUserName());
        System.out.println("Properties测试获取的数据库密码：" + ALianProperties.getPassword());
        System.out.println("Properties测试获取的数据库连接参数：" + ALianProperties.getBlogDesp());
        System.out.println("-------------------PlaceSource测试结束-------------------");
    }
}
```

运行结果：

```
-------------------PlaceSource测试开始-------------------
Properties测试获取的数据库地址：https://blog.csdn.net/Alian_1223
Properties测试获取的数据库用户名：Alian
Properties测试获取的数据库密码：123456
Properties测试获取的数据库连接参数：来来来，一起聊干货！！！
-------------------PlaceSource测试结束-------------------
```

## Environment对象获取

使用很简单，直接使用spring的注解@Autowired引入即可

```java
    @Autowired
    private Environment environment;
```

注意：Environment 是org.springframework.core.env.Environment

我们继续在配置文件application.properties中新增如下配置，用于测试Environment 方式获取配置文件的值

```yaml
#定义Environment的变量测试
#系统组
envir.system.group=Alian
#系统组
envir.system.level=1
#系统名称
envir.system.name=administrator
#系统密码
envir.system.password=e6fa5927cc37437ac6cbe5e988288f80
```

**测试**

**EnvironmentService.java**

```java
package com.alian.properties.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EnvironmentService {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void testEnvironment() {
        System.out.println("-------------------Environment测试开始-------------------");
        System.out.println("Environment测试获取的系统组：" + environment.getProperty("envir.system.group"));
        System.out.println("Environment测试获取的系统级别：" + environment.getProperty("envir.system.level"));
        System.out.println("Environment测试获取的系统名：" + environment.getProperty("envir.system.name"));
        System.out.println("Environment测试获取的系统密码：" + environment.getProperty("envir.system.password"));
        //如果配置文件未设置该key的值，则使用默认值
        System.out.println("Environment测试获取的默认值设置：" + environment.getProperty("envir.system.init", "未设置初始化参数"));
        System.out.println("-------------------Environment测试结束-------------------");
    }
}
```

运行结果：

```
-------------------Environment测试开始-------------------
Environment测试获取的系统组：Alian
Environment测试获取的系统级别：1
Environment测试获取的系统名：administrator
Environment测试获取的系统密码：e6fa5927cc37437ac6cbe5e988288f80
Environment测试获取的默认值设置：未设置初始化参数
-------------------Environment测试结束-------------------
```

注意：

- 使用Environment对象获取配置文件的值，最好使用带默认值的方法：getProperty(“配置项key”,“默认值”)，避免null值
- 使用Environment对象还可以获取到一些系统的启动信息，当然如果配置项过多也会有维护管理方面的问题

## 原生方式

**LoadPropertiesService.java**

```java
package com.alian.properties.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

@Service
public class LoadPropertiesService {

    @PostConstruct
    public void testLoadProperties() {
        System.out.println("-------------------LoadProperties测试开始-------------------");
        Properties props = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("load.properties")), StandardCharsets.UTF_8);
            props.load(inputStreamReader);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("LoadProperties测试获取的功能名称：" + props.getProperty("function.name"));
        System.out.println("LoadProperties测试获取的功能描述：" + props.getProperty("function.desp"));
        System.out.println("-------------------LoadProperties测试开始-------------------");
    }
}
```

运行结果：

```
-------------------LoadProperties测试开始-------------------
LoadProperties测试获取的功能名称：loadProperties
LoadProperties测试获取的功能描述：原生获取配置文件的值
-------------------LoadProperties测试开始-------------------
```

注意：

- 读取流的时候指定好编码，保证和文件的编码一致，否则会导致乱码

# Nginx高性能服务

## 一、引言

### 1.1 代理问题

客户端到底要将请求发送给哪台服务器。

| 发送给服务器1还是服务器2                                     |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646185298458-8bfc884b-abca-473e-bcec-ea1f4a802f78.png) |

### 1.2 负载均衡问题

如果所有客户端的请求都发送给了服务器1，那么服务器2将没有任何意义

| 负载均衡问题                                                 |
| ------------------------------------------------------------ |
| 服务器1访问数据库客户端服务器2![image.png](spring boot3.assets/1646185332482-13171c40-c632-4005-97b7-70d50edf2147.png) |

### 1.3 资源优化

客户端发送的请求可能是申请动态资源的，也有申请静态资源，但是都是去Tomcat中获取的

| 静态资源访问                                                 |
| ------------------------------------------------------------ |
| 客户端数据库服务器1本地静态资源![image.png](spring boot3.assets/1646185343154-aab9cb15-8b23-45f3-9273-b97bc442941c.png) |

### 1.4 Nginx处理

| 在搭建集群后，使用Nginx                                      |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646185354953-4e9aecee-5fe6-41b3-91f5-2722120838a9.png) |

## 二、Nginx概述

Nginx是由俄罗斯人研发的，应对Rambler的网站，并且2004年发布的第一个版本。

Nginx (engine x) 是一个高性能的HTTP和反向代理web服务器，同时也提供了IMAP/POP3/SMTP服务。Nginx是由伊戈尔·赛索耶夫为俄罗斯访问量第二的Rambler.ru站点（俄文：Рамблер）开发的，第一个公开版本0.1.0发布于2004年10月4日。2011年6月1日，nginx 1.0.4发布。

其特点是占有内存少，并发能力强，事实上nginx的并发能力在同类型的网页服务器中表现较好，中国大陆使用nginx网站用户有：百度、京东、新浪、网易、腾讯、淘宝等。在全球活跃的网站中有12.18%的使用比率，大约为2220万个网站。

Nginx 是一个安装非常的简单、配置文件非常简洁（还能够支持perl语法）、Bug非常少的服务。Nginx 启动特别容易，并且几乎可以做到7*24不间断运行，即使运行数个月也不需要重新启动。你还能够不间断服务的情况下进行软件版本的升级。

Nginx代码完全用C语言从头写成。官方数据测试表明能够支持高达 50,000 个并发连接数的响应。

### 2.1 Nginx的特点

+ 稳定性极强。 7*24小时不间断运行。
+ Nginx提供了非常丰富的配置实例。
+ 占用内存小，并发能力强。

| Nginx引入前后,客户端请求处理流程的对比                       |
| ------------------------------------------------------------ |
| ![image-20231002175402614](spring boot3.assets/image-20231002175402614.png) |

### 2.2 Nginx作用

> Http代理，反向代理：作为web服务器最常用的功能之一，尤其是反向代理。

**正向代理:  代理客户端**
![img](spring boot3.assets/kuangstudy46bdad36-d3e0-43b0-a223-43360b7e8fc7.png)
**反向代理:  代理服务端**
![img](spring boot3.assets/kuangstudy62a15097-6e2a-4dbe-bcf5-f0d7cab81089.png)

> Nginx提供的负载均衡策略有2种：内置策略和扩展策略。内置策略为轮询，加权轮询，Ip hash。扩展策略，就天马行空，只有你想不到的没有他做不到的。

轮询
![img](spring boot3.assets/kuangstudy4d33dfac-1949-4b2d-abb8-fe0b6e65b8dc.png)
加权轮询
![img](spring boot3.assets/kuangstudyb1e3e440-4159-4259-a174-528b56cb04b2.png)
iphash对客户端请求的ip进行hash操作，然后根据hash结果将同一个客户端ip的请求分发给同一台服务器进行处理，可以解决session不共享的问题。
![img](spring boot3.assets/kuangstudy64acb9a3-cd1a-4c0e-a1fa-9b220046a95a.png)

> 动静分离，在我们的软件开发中，有些请求是需要后台处理的，有些请求是不需要经过后台处理的（如：css、html、jpg、js等等文件），这些不需要经过后台处理的文件称为静态文件。让动态网站里的动态网页根据一定规则把不变的资源和经常变的资源区分开来，动静资源做好了拆分以后，我们就可以根据静态资源的特点将其做缓存操作。提高资源响应的速度。

![img](spring boot3.assets/kuangstudyedb1bbd6-e530-4aba-8fde-68658a10e73f.png)

目前，通过使用Nginx大大提高了我们网站的响应速度，优化了用户体验，让网站的健壮性更上一层楼！软件层面一般常用Nginx来做反向代理服务器，它的性能非常好，用来做负载均衡。

## 三、Nginx的安装

### 3.1 windows下安装并使用

**1、下载nginx**

http://nginx.org/en/download.html 下载稳定版本。
以nginx/Windows-1.16.1为例，直接下载 nginx-1.16.1.zip。
下载后解压，解压后如下：

![img](spring boot3.assets/kuangstudyb5a1d538-352c-4aa6-88f7-23d18f0588ab.png)

**2、启动nginx**

有很多种方法启动nginx

(1)直接双击nginx.exe，双击后一个黑色的弹窗一闪而过

(2)打开cmd命令窗口，切换到nginx解压目录下，输入命令 `nginx.exe` ，回车即可

**3、检查nginx是否启动成功**

直接在浏览器地址栏输入网址 [http://localhost:80](http://localhost/) 回车，出现以下页面说明启动成功！

![img](spring boot3.assets/kuangstudya21688c8-159e-4caa-8e65-3dc056b6b78e.png)

**4、配置监听**

nginx的配置文件是conf目录下的nginx.conf，默认配置的nginx监听的端口为80，如果80端口被占用可以修改为未被占用的端口即可。

![img](spring boot3.assets/kuangstudyf23105c4-b0b2-4e22-a1bf-b8098f40c144.png)

当我们修改了nginx的配置文件nginx.conf 时，不需要关闭nginx后重新启动nginx，只需要执行命令

```
 nginx -s reload 即可让改动生效
```

**5、关闭nginx**

如果使用cmd命令窗口启动nginx， 关闭cmd窗口是不能结束nginx进程的，可使用两种方法关闭nginx

(1)输入nginx命令 

```
nginx -s stop(快速停止nginx) 或 nginx -s quit(完整有序的停止nginx)
```

(2)使用taskkill `taskkill /f /t /im nginx.exe`

```
taskkill是用来终止进程的，/f是强制终止 ./t终止指定的进程和任何由此启动的子进程。/im示指定的进程名称 .
```

### 3.2 linux下安装

**1、安装gcc**

安装 nginx 需要先将官网下载的源码进行编译，编译依赖 gcc 环境，如果没有 gcc 环境，则需要安装：

```
yum install gcc-c++
```

**2、PCRE pcre-devel 安装**

PCRE(Perl Compatible Regular Expressions) 是一个Perl库，包括 perl 兼容的正则表达式库。nginx 的 http 模块使用 pcre 来解析正则表达式，所以需要在 linux 上安装 pcre 库，pcre-devel 是使用 pcre 开发的一个二次开发库。nginx也需要此库。命令：

```
yum install -y pcre pcre-devel
```

**3、zlib 安装**

zlib 库提供了很多种压缩和解压缩的方式， nginx 使用 zlib 对 http 包的内容进行 gzip ，所以需要在 Centos 上安装 zlib 库。

```
yum install -y zlib zlib-devel
```

**4、OpenSSL 安装**
OpenSSL 是一个强大的安全套接字层密码库，囊括主要的密码算法、常用的密钥和证书封装管理功能及 SSL 协议，并提供丰富的应用程序供测试或其它目的使用。
nginx 不仅支持 http 协议，还支持 https（即在ssl协议上传输http），所以需要在 Centos 安装 OpenSSL 库。

```
yum install -y openssl openssl-devel
```

  **一次性安装，执行如下命令**

```
yum install gcc openssl openssl-devel pcre pcre-devel zlib zlib-devel -y
```

**5、下载安装包**

手动下载.tar.gz安装包，地址：https://nginx.org/en/download.html

![img](spring boot3.assets/kuangstudyf51b946d-fda4-4675-b913-2084e028a5c0.png)

下载完毕上传到服务器上 /root

**6、解压**

```
tar -zxvf nginx-1.18.0.tar.gzcd nginx-1.18.0
```

![img](spring boot3.assets/kuangstudyd8290598-ede7-4b4b-875b-2f447a9c001f.png)

**7、配置**

使用默认配置，在nginx根目录下执行

```
./configuremakemake install
```

查找安装路径： `whereis nginx`

![img](spring boot3.assets/kuangstudyf80f8dc2-d5df-4bc2-933d-6ce11f388f6e.png)

### 3.3 使用Docker-Compose安装

编写docker-compose.yml

```yml
version: '3.1'
services:
  nginx:
    restart: always
    image: daocloud.io/library/nginx:latest
    container_name: nginx
    ports:
      - 80:80
```

```shell
docker compose -f docker-compose.yml up -d
```

##  Nginx的核心配置文件nginx.conf

<img src="spring boot3.assets/image-20231005130251506.png" alt="image-20231005130251506" style="zoom:67%;" />

### 结构说明

1、全局块：配置影响nginx全局的指令。一般有运行nginx服务器的用户组，nginx进程pid存放路径，日志存放路径，配置文件引入，允许生成worker process数等。

2、events块：配置影响nginx服务器或与用户的网络连接。有每个进程的最大连接数，选取哪种事件驱动模型处理连接请求，是否允许同时接受多个网路连接，开启多个网络连接序列化等。

3、http块：可以嵌套多个server，配置代理，缓存，日志定义等绝大多数功能和第三方模块的配置。如文件引入，mime-type定义，日志自定义，是否使用sendfile传输文件，连接超时时间，单连接请求数等。

4、server块：配置虚拟主机的相关参数，一个http中可以有多个server。

5、location块：配置请求的路由，以及各种页面的处理情况。

```
...              #全局块

events {         #events块
   ...
}

http      #http块
{
    ...   #http全局块
    server        #server块
    { 
        ...       #server全局块
        location [PATTERN]   #location块
        {
            ...
        }
        location [PATTERN] 
        {
            ...
        }
    }
    server
    {
      ...
    }
    ...     #http全局块
}
```

/etc/nginx/nginx.conf

### nginx.conf

```yaml
########### 每个指令必须有分号结束。#################
#配置用户或者组，默认为nobody nobody，以什么用户启动 nginx程序，涉及到文件的读写权限，一般应该用root。
user root;  

#允许生成的进程数，默认为1。标配一个cpu起一个进程。
worker_processes auto;  

#指定nginx进程运行文件存放地址
pid /nginx/pid/nginx.pid;   

#制定日志路径，级别。这个设置可以放入全局块，http块，server块，级别以此为：debug|info|notice|warn|error|crit|alert|emerg
#error_log log/error.log debug;  

events {
	#设置网路连接序列化，防止惊群现象发生，默认为on
    #accept_mutex on;  
     
    #设置一个进程是否同时接受多个网络连接，默认为off
    #multi_accept on; 
    
    #事件驱动模型，select|poll|kqueue|epoll|resig|/dev/poll|eventport
    #use epoll;     
    
    #最大连接数，可以根据实际情况扩大，生产可扩大到10240
    worker_connections  10240;    
}


http {
	#文件扩展名与文件类型映射表
    include       mime.types; 
      
    #默认文件类型，默认为text/plain
    default_type  application/octet-stream; 
    
    #取消服务日志
    #access_log off; 
    
    #自定义格式    
    #log_format myFormat '$remote_addr–$remote_user [$time_local] $request $status $body_bytes_sent $http_referer $http_user_agent $http_x_forwarded_for'; 
    
    #combined为日志格式的默认值
    #access_log log/access.log myFormat;  

	#允许sendfile方式传输文件，默认为off，可以在http块，server块，location块。
    sendfile on; 
    
    #每个进程每次调用传输数量不能大于设定的值，默认为0，即不设上限。
    sendfile_max_chunk 100k;  
    
    #连接超时时间，默认为75s，可以在http，server，location块。
    keepalive_timeout 75;  
    
    #接收客户端请求体超时
    client_body_timeout 20s;
    
    #客户端连接nginx超时, 建议5s内,接收客户端header超时时间,超时返回408
    client_header_timeout 10s;

	#限制请求体的大小，若超过所设定的大小，返回413错误。
    client_max_body_size 10M;


	#Nginx分配给请求数据的Buffer大小，如果请求的数据小于client_body_buffer_size直接将数据先在内存中存储。
	#大于该值小于client_max_body_size，就会将数据先存储到临时文件（用户组要有读写权限）中，client_body_temp 指定的路径中，默认该路径值是/tmp/。
    client_body_buffer_size 128k;
    
    #开启自定义错误重定向，不存在会返回404页面
    fastcgi_intercept_errors on;

	#热备  负载均衡配置
    upstream mysvr { 
      #服务器资源
      server 127.0.0.1:7878;
      server 192.168.10.121:3333 backup;  
    }

    server {
    	#单连接请求上限次数。
        keepalive_requests 120; 
        #监听端口
        listen       4545;   
        #监听地址 
        server_name  127.0.0.1;    
        #请求的url过滤，正则匹配，~为区分大小写，~*为不区分大小写。     
        location  ~*^.+$ {   
        	#根目录,设置目录
           #root path;  
           #设置默认页
           #index index.htm;  
           #请求转向mysvr 定义的服务器列表  反向代理
           proxy_pass  http://mysvr;    # 如果是4545请求代理到mysvr中 
            #拒绝的ip
           #deny 127.0.0.1; 
            #允许的ip
           #allow 172.18.5.54;           
        } 
    }
}

```

例子

```yaml
worker_processes  1;		
error_log  /var/log/nginx/error.log warn;
# 以上统称为全局块， 
# worker_processes他的数值越大，Nginx的并发能力就越强  集群
# error_log 代表Nginx的错误日志存放的位置

events {
    worker_connections  1024;
}
# events块
# worker_connections他的数值越大，Nignx并发能力越强

http {  #text/html text/plian  xml  images:jpg png gif
    include       /etc/nginx/mime.types; #媒体类型 http 头部信息 contenttype=application/json
    default_type  application/octet-stream;
    server {
        listen       80;
        server_name  localhost;
		#代理

        location / { #策略
            root   /usr/share/nginx/html; #webapps资源
            index  index.html index.htm; #设计程序入口
        }
		# location块
		# root：将接收到的请求根据/usr/share/nginx/html去查找静态资源
		# index： 默认去上述的路径中找到index.html或者index.htm
    }
	# server块
	# listen： 代表Nginx监听的端口号
	# localhost：代表Nginx接收请求的ip
}
# http块
# include代表引入一个外部的文件 -> /mime.types中放着大量的媒体类型
# include /etc/nginx/conf.d/*.conf; -> 引入了conf.d目录下的以.conf为结尾的配置文件
```

 修改docker-compose文件

为了方便修改Nginx配置，修改yml文件

```yml
version: '3.1'
services:
  nginx:
    restart: always
    image: daocloud.io/library/nginx:latest
    container_name: nginx
    ports:
      - 80:80
    volumes:
      - /opt/docker_nginx/conf.d/:/etc/nginx/conf.d
```

案例

```
upstream lb{
    server 127.0.0.1:8080 weight=3;
    server 127.0.0.1:8081 weight=1;
}
location / {
    proxy_pass http://lb;
}
```

<img src="spring boot3.assets/image-20231005144345955.png" alt="image-20231005144345955" style="zoom:67%;" />

### 案例1：页面转发，80端口指向index，前后台可用

```clike
 server {
        listen       80;
        server_name  localhost;
        location / {
            root   /home/user01/dist/;
            try_files $uri $uri/ /index.html;
            index  index.html index.htm;
        }
```

### 案例2：接口转发

[详细代码](C:\Users\16658\Documents\GitHub\java_note\note\spring boot3\Docker Compose实战\test01)

**java**

```properties
# 端口号
server:
  port: 2001
```

controller

```java
@RequestMapping("/v1")
@RestController
public class HelloController {

	@Value("${server.port}")
	private int port;

	@GetMapping("")
	public String test() {
		return "invoke url /,port="+port;
	}

	@GetMapping("/test1")
	public String test1() {
		return "invoke url /test1,port="+port;
	}

	@GetMapping("/test2")
	public String test2() {
		return "invoke url /test2,port="+port;
	}
}
```

测试

```c
http://127.0.0.1:2001/v1
http://127.0.0.1:2001/v1/test1
http://127.0.0.1:2001/v1/test2
```

<img src="spring boot3.assets/image-20231005161542288.png" alt="image-20231005161542288" style="zoom:67%;" />

**nginx**

修改nginx.conf文件

```json

worker_processes  1;

events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;
    
    keepalive_timeout  65;

    server {
        listen       90;
        server_name  127.0.0.1;

		location / {
            root   html; 
            index  index.html index.htm; 
        }
        location /t {
            proxy_pass http://127.0.0.1:2001/v1;
        }
		location /t1 {
            proxy_pass http://127.0.0.1:2001/v1/test1;
        }
		location /t2 {
            proxy_pass http://127.0.0.1:2001/v1/test2;
        }
    }
}
```

![image-20231005161708036](spring boot3.assets/image-20231005161708036.png)

测试

```c
http://127.0.0.1:90/t
http://127.0.0.1:90/t1
http://127.0.0.1:90/t2
```

<img src="spring boot3.assets/image-20231005161801375.png" alt="image-20231005161801375" style="zoom:67%;" />

### 案例3：文件路径转发。80/uploadFile指向文件路径

```clike
server {  
		location /uploadFile/{
            proxy_pass http://localhost:48081/;
        }  
}

   server {  
        listen         48081;        
        server_name  localhost;   
        charset utf-8; 
        root    /home/user01/java/file; 
        location / {
            autoindex on;             
            autoindex_exact_size off; 
            autoindex_localtime on;  
        } 
   }
```

简略配置：

```clike
location /uploadFile{
 		root    /home/user01/java/file; 
}
123
```

### 案例4:：端口转发，将本地端口指向其他地址

```clike
server {
	listen 127.0.0.1:8279;
	location / { 
		proxy_pass http://ip:8279;
	}
}
123456
```

### 案例5：负载均衡，将一个端口指向多个服务

```clike
server {
		listen 8888;
		location / { 
			#通过代理将请求发送给 upstream 命名的HTTP 服务
			proxy_pass http://myserver;
		}
	}
	
#定义一个 HTTP 服务组
upstream  myserver{
		#用server定义HTTP 地址。后面不写默认轮询。
		server 127.0.0.1:8080 max_fails=5 fail_timeout=10s weight=10;
		server 127.0.0.1:8081 backup;
		server 127.0.0.1:8082 down;
	}
123456789101112131415
upstream  myserver{
	#相同的客户端ip请求相同的服务器。
	server 127.0.0.1:8080;
	server 127.0.0.1:8081;
	ip_hash;
}
123456
```

- weight，加权轮询。
- down，表示当前的server暂时不参与负载均衡。
- backup，预留的备份机器。当其他所有的非backup机器出现故障或者忙的时候，才会请求backup机器，因此这台机器的压力最轻。
- max_fails，允许请求失败的次数，默认为1。当超过最大次数时，返回proxy_next_upstream 模块定义的错误。
- fail_timeout，在经历了max_fails次失败后，暂停服务的时间。max_fails可以和fail_timeout一起使用。



### 配置文件新增端口转发配置技巧

1、在nginx.conf中http加入一行include tcpconf/*.conf;

```clike
http {
include tcpconf/*.conf;
}
```

2、在nginx.conf目录下，新建文件夹tcpconf，其他配置文件命名.conf，文件中写入转发的配置。例如下面，然后重启生效。

```clike
server {
    listen 127.0.0.1:8279;
   location / { 
   proxy_pass http://ip:8279;
	}
}
```

## 四、Nginx的反向代理【`重点`】

### 4.1 正向代理和反向代理介绍

正向代理：

- 正向代理服务是由客户端设立的。
- 客户端了解代理服务器和目标服务器都是谁。
- 帮助咱们实现突破访问权限，提高访问的速度，对目标服务器隐藏客户端的ip地址。

| 正向代理                                                     |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646185385404-ac5227e7-c77e-4250-87df-45a1450fb465.png) |

反向代理：

- 反向代理服务器是配置在服务端的。
- 客户端是不知道访问的到底是哪一台服务器。
- 达到负载均衡，并且可以隐藏服务器真正的ip地址。

![img](spring boot3.assets/1646185399829-129dce1f-afbc-428a-9fa9-1fd5163d655e.png)

### 4.2 基于Nginx实现反向代理

准备一个目标服务器。

 启动了之前的tomcat服务器。

 编写nginx的配置文件，通过Nginx访问到tomcat服务器。

```json
server{
  listen 80;
  server_name localhost;
    # 基于反向代理访问到Tomcat服务器
  location / {
    proxy_pass http://192.168.199.109:8080/;
  }
}
```



### 4.3 关于Nginx的location路径映射



优先级关系如下： 资源管理  其二我的nginx

 

- location = /路径：优先级最高，精准匹配，一旦匹配， 不再去找其他匹配项。
- location ^~ /路径：优先级次之，字符串匹配，一旦匹配， 不再去找其他匹配项。
- location ~ 正则表达式：如果有多个location的正则能匹配的话，则使用正则表达式最长的那个。
- location ~* 正则表达式：和location ~ 正则表达式相同，不过当前方式不区分大小写。
- location /路径：常规方式，匹配前缀，优先级最低。

 

举个栗子：

```json
# 直接匹配
location = / {
  # 精准匹配，主机名后面不能带任何的字符串
}

# 常规匹配
location /xxx {
  # 匹配所有以/xxx开头的路径
}

# 正则匹配
location ~ /xxx {
  # 匹配所有以/xxx开头的路径
}

# 匹配开头路径
location ^~ /images/ {
  # 匹配所有以/images开头的路径，匹配后，不再删选其他选项
}

# 正则匹配后缀
location ~* \.(gif|jpg|png)$ {
  # 匹配以gif或者jpg或者png为结尾的路径
}

# 全部通配
location / {
  # 匹配全部路径  
}
```

## 五、Nginx负载均衡【`重点`】

------

Nginx为我们默认提供了三种负载均衡的策略：

-  轮询：将客户端发起的请求，平均的分配给每一台服务器。 
-  权重：会将客户端的请求，根据服务器的权重值不同，分配不同的数量。 
-  ip_hash：基于发起请求的客户端的ip地址不同，他始终会将请求发送到指定的服务器上。 



### 5.1 轮询

想实现Nginx轮询负载均衡机制只需要在配置文件中添加以下内容

```json
upstream 名字 {
  server ip:port;
  server ip:port;
  ...
}
server {
  listen 80;
  server_name localhost;
  
  location / {
    proxy_pass http://upstream的名字/;
  }
}
```

### 5.2 权重

实现权重的方式

```json
权重比例：1-9数字
upstream 名字 {
  server ip:port weight=权重比例;
  server ip:port weight=权重比例;
  ...
}
server {
  listen 80;
  server_name localhost;
  
  location / {
    proxy_pass http://upstream的名字/;
  }
}
```

#### 5.3 ip_hash

ip_hash实现

```json
upstream 名字 {
  ip_hash;
  server ip:port;
  server ip:port;
  ...
}
server {
  listen 80;
  server_name localhost;
  
  location / {
    proxy_pass http://upstream的名字/;
  }
}
```

## 六、Nginx动静分离【`重点`】

```
Nginx的并发能力公式：

​	worker_processes * worker_connections / 4 | 2 = Nginx最终的并发能力

动态资源需要/4，静态资源需要/2.
Nginx通过动静分离，来提升Nginx的并发能力，更快的给用户响应。
```

### 6.1 动态资源代理

使用proxy_pass动态代理

```json
# 配置如下
location / {
  proxy_pass 路径;
}
```

### 6.2 静态资源代理

使用root静态代理

```json
# 配置如下
location / {
  root 静态资源路径;
  index 默认访问路径下的什么资源;
  autoindex on; # 代表展示静态资源全的全部内容，以列表的形式展开。
}

server{
  listen 80;
  server_name localhost;
  location / {
    root /opt/docker_nginx/upload;  #容器内部资源路径，这个你可以数据卷映射，宿主机上上传资源文件，但是在这里必须配置容映射的路径
    index *.html *.png; #如果配置了多个资源，需要列表显示（autoindex on），需要用*配置所有资源，（切记不要制定资源全名）
    autoindex on;
  }
}


# 先修改docker，添加一个数据卷，映射到Nginx服务器的一个目录
# 添加了index.html和1.jpg静态资源
# 修改配置文件
```

#  Docker虚拟化容器技术

+ Docker概述
+ Docker安装
+ Docker命令
+ 镜像命令
+ 容器命令
+ 操作命令
+ Docker镜像!
+ 容器数据卷！
+ DockerFile
+ Docker网络原理
+ IDEA 整合Docker
+ Docker Compose
+ Docker Swarm

CI\CD Jenkins

##  一、引言 

### 1.1 环境不一致

我本地运行没问题啊：由于环境不一致，导致相同的程序，运行结果却不一致。

```
一款产品：开发--上线 两套环境！应用环境，应用配置！
开发-运维。问题:   我在我的电脑上可以运行!版本更新,导致服务不可用!对于运维来说,考验就十分大?
环境配置是十分的麻烦,每一个机器都要部署环境(集群Redis、 ES、Hadoop.....) !费时费力。
发布一个项目(jar+ (Redis MySQL jdk ES）)， 项目能不能都带上环境安装打包！
之前在服务器配置一个应用的环境 Redis MySQL jdk ES Hadoop ,配置超麻烦了，不能够跨平台。
```

### 1.2 隔离性

哪个哥们又写死循环了，怎么这么卡：在多用户的操作系统下，会因为其他用户的操作失误影响到你自己编些的程序。

 ### 1.3 弹性伸缩 

淘宝在双11的时候，用户量暴增：需要很多很多的运维人员去增加部署的服务器，运维成本过高的问题。

##  二、Docker介绍 

### 2.1 Docker的由来

| Docker的作者已经离开了维护Docker的团队                       |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646184354442-27cdc328-368a-4049-97f6-fc5c692df8e3.png) |

###  2.2 Docker的思想

+ 集装箱：会将所有需要的内容放到不同的集装箱中，谁需要这些环境就直接拿到这个集装箱就可以了。 
+ 标准化： 
  + 运输的标准化：Docker有一个码头，所有上传的集装箱都放在了这个码头上，当谁需要某一个环境，就直接指派大海疼去搬运这个集装箱就可以了。
  + 命令的标准化：Docker提供了一些列的命令，帮助我们去获取集装箱等等操作。
  + 提供了REST的API：衍生出了很多的图形化界面，Rancher。

+ 隔离性：Docker在运行集装箱内的内容时，会在Linux的内核中，单独的开辟一片空间，这片空间不会影响到其他程序。 
+ 中央仓库|注册中心：超级码头，上面放的就是集装箱 
+ 镜像：就是集装箱 
+  容器：运行起来的镜像 

### 2.3 Docker的作用

**应用更快速的交付和部署**

```
传统:一堆帮助文档,安装程序
Docker :打包镜像发布测试,一键运行
```

**更便捷的升级和扩缩容**

```
使用了Docker之后，我们部署应用就和搭积木一样！
项目打包为一个镜像,扩展服务器A!服务器B
```

**更简单的系统运维**

```
在容器化之后，我们的开发，测试环境都是高度一致的。
```

**更高效的计算资源利用**

```
Docker是内核级别的虚拟化,可以再一个物理机上可以运行很多的容器实例!服务器的性能可以被压榨到极致。
```

##  三、Docker的安装

![image-20231002190403275](spring boot3.assets/image-20231002190403275.png)

**镜像（image）:**

docker镜像就好比是一个模板,可以通过这个模板来创建容器服务, tomcat镜像===> run==> tomcat01容器(提供服务器) ,
通过这个镜像可以创建多个容器(最终服务运行或者项目运行就是在容器中的)。

**容器(container) :**

Docker利用容器技术,独立运行一个或者一个组应用，通过镜像来创建的。
启动，停止，删除，基本命令！目前就可以把这个容器理解为就是一个简易的linux系統

**仓库(repository):**

仓库就是存放镜像的地方！仓库分为公有仓库和私有仓库！
Docker Hub（默认是国外的）
阿里云…..都有容器服务器(配置镜像加速!)

### 云服务器安装Docker

**下载Docker依赖的环境**

```shell
yum -y install yum-utils device-mapper-persistent-data lvm2
```

**指定Docker镜像源**

```shell
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

**安装Docker**

```shell
yum makacache fast
yum -y install docker-ce
```

**或者官方的一键安装方式：**

```arduino
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
```

**启动Docker并测试**

```shell
# 启动Docker服务
systemctl start docker
# 设置开机自动启动
systemctl enable docker
# 测试
docker ps
```

![image-20231002194321227](spring boot3.assets/image-20231002194321227.png)

**测试一下Docker**

```shell
docker run hello-world
```

![image.png](spring boot3.assets/1647311103937-730e3834-b4a4-42d4-8aaa-8913cd937a32.png)

![image-20231002195531945](spring boot3.assets/image-20231002195531945.png)

## 四、Docker的中央仓库【重点】

+ Docker官方的中央仓库：这个仓库是镜像最全的，但是下载速度较慢。 https://hub.docker.com/ 

+ 国内的镜像网站：网易蜂巢，daoCloud等，下载速度快，但是镜像相对不全。 

  + https://c.163yun.com/hub#/home 

  + http://hub.daocloud.io/   （推荐使用） 

+ 在公司内部会采用私服的方式拉取镜像，需要添加配置，如下…… 

```shell
# 需要创建/etc/docker/daemon.json，并添加如下内容
{
	"registry-mirrors": ["https://registry.docker-cn.com"],
	"insecure-registries": ["ip:port"]   
}
# 重启两个服务
systemctl daemon-reload
systemctl restart docker
```

### 直接配置阿里云镜像加速

![image-20231002200251218](spring boot3.assets/image-20231002200251218.png)

**使用**

![image-20231002200422844](spring boot3.assets/image-20231002200422844.png)

##  五、常用命令

<img src="spring boot3.assets/image-20231002221406584.png" alt="image-20231002221406584" style="zoom: 67%;" />

### 5.1 镜像命令

#### 搜索镜像

```shell
sudo docker search 镜像名称
```

比如搜索Hadoop的镜像信息 docker search hadoop

![image-20230308154908968](spring boot3.assets/f1922e38afa8e23443440683f11abc94.png)

+ NAME:镜像名称
+ DESCRIPTION:镜像说明
+ STARS:点赞数量
+ OFFICIAL:是否是官方的
+ AUTOMATED：是否自动构建的

**访问 Nginx 镜像库地址**

 https://hub.docker.com/_/nginx?tab=tags可以通过 Sort by 查看其他版本的 Nginx，默认是最新版本 nginx:latest。

<img src="spring boot3.assets/image-20231004171527942.png" alt="image-20231004171527942" style="zoom:67%;" />

可以找到其他你想要的版本

<img src="spring boot3.assets/image-20231004171623676.png" alt="image-20231004171623676" style="zoom:67%;" />

#### 拉取镜像

```shell
sudo docker pull 镜像名
sudo docker pull 镜像名:Tag
```

<img src="spring boot3.assets/image-20231002210442940.png" alt="image-20231002210442940" style="zoom: 80%;" />

#### 查看本地全部镜像

```shell
sudo docker images
```

![image-20230308154454634](spring boot3.assets/bd8687b93dbd03751961b1e43c44c5b2.png)

+ REPOSITORY：表示镜像的仓库源

+ TAG：镜像的标签

+ IMAGE ID：镜像ID

+ CREATED：镜像创建时间

+ SIZE：镜像大小

注意：如果镜像后面不加TAG，默认使用latest的镜像

#### 删除本地镜像

```shell
docker image rmi -f 镜像名/镜像ID     # 删除1个镜像
docker image rmi -f 镜像名 镜像名         #删除多个镜像
```

![image-20230308170230613](spring boot3.assets/37043aee7c6768894b2d0c44a4304c06.png)

#### 镜像的导入导出（重点）

 **保存镜像**

将我们的镜像 保存为tar 压缩文件 这样方便镜像转移和保存 ,然后 可以在任何一台安装了docker的服务器上 加载这个镜像

```shell
docker save 镜像名/镜像ID -o 镜像保存在哪个位置与名字
```

  **加载镜像**

任何装 docker 的地方加载镜像保存文件,使其恢复为一个镜像

```shell
docker load -i 镜像保存文件位置
```

**修改镜像名称和版本**

```
docker tag 镜像id 新镜像名称:版本
```

-----

总结

```shell
# 将本地的镜像导出
docker save -o 导出的路径 镜像id
# 加载本地的镜像文件
docker load -i 镜像文件
# 修改镜像名称和版本
docker tag 镜像id 新镜像名称:版本
```

如果因为网络原因可以通过硬盘的方式传输镜像，虽然不规范，但是有效，但是这种方式导出的镜像名称和版本都是null，需要手动修改

#### commit 更新镜像

```shell
docker commit 提交容器成为一个新的副本

#命令和git原理类似
docker commit -m="提交的描述信息" -a="作者" 容器id 目标镜像名:[TAG]
```

**实战测试**

**更新镜像之前，需要使用镜像来创建一个容器。**

```shell
:~$ docker run -t -i ubuntu:15.10 /bin/bash
root@e218edb10161:/#
```

在完成操作之后，输入 exit 命令来退出这个容器。通过命令 docker commit 来提交容器副本。

```shell
:~$ docker commit -m="has update" -a="jeflee2324" e218edb10161 jeflee2324/ubuntu:v2
```

各个参数说明：

+ -m: 提交的描述信息
+ -a: 指定镜像作者
+ e218edb10161：容器 ID
+ jeflee2324/ubuntu:v2:  指定要创建的目标镜像名

**例子**

```shell
#1、启动一个默认的tomcat
#2、发现这个默认的tomcat是没有webapps应用,镜像的原因,官方的镜像默认webapps下面是没有文件的!
#3、我自己拷贝进去了基本的文件
# 4、将我们操作过的容器通过commit提交为一个镜像
```

![image-20231003222735725](spring boot3.assets/image-20231003222735725.png)

### 5.2 容器操作

> 有了镜像才可以创建容器

#### 运行容器

运行容器需要制定具体镜像，如果镜像不存在，会直接下载

```shell
# 简单操作
docker run 镜像的标识|镜像名称[:tag]

# 常用的参数
docker run -d -p 宿主机端口:容器端口 --name 容器名称 镜像的标识|镜像名称[:tag]
# -d：代表后台运行容器
# -p 宿主机端口:容器端口：为了映射当前Linux的端口和容器的端口
# --name 容器名称：指定容器的名称

docker run -d -p 8081:8080 --name tomcat b8
```

```shell
docker run -it -d --name 要取的别名 -p 宿主机端口:容器端口 -v 宿主机文件存储位置:容器内文件位置 镜像名:Tag /bin/bash 

-it 表示 与容器进行交互式启动
-d 表示可后台运行容器 （守护式运行）  
--name 给要运行的容器 起的名字  
/bin/bash  交互路径
-p 将容器的端口映射到宿主机上，通过宿主机访问内部端口
-v 将容器内的指定文件夹挂载到宿主机对应位置
```

#### 退出容器

```shell
exit            # 直接容器停止并退出
Ctrl + p + q    # 容器不停止退出
```

#### 查看正在运行的容器

查看全部正在运行的容器信息

```shell
docker ps [-qa]
# -a：查看全部的容器，包括没有运行
# -q：只查看容器的标识
```

![img](spring boot3.assets/a954baa77e0097150b1c74514921e11f.png)

#### 查看容器中进程信息 ps

```shell
docker top 容器id
```

![image-20231002220025497](spring boot3.assets/image-20231002220025497.png)

#### 网络端口的快捷方式

docker port 可以查看指定 （ID 或者名字）容器的某个确定端口映射到宿主机的端口号。

```shell
:~$ docker port bf08b7f2cd89
5000/tcp -> 0.0.0.0:5000
:~$ docker port wizardly_chandrasekhar
5000/tcp -> 0.0.0.0:5000
```

#### 查看容器日志

查看容器日志，以查看容器运行的信息

```shell
docker logs -f -t --tail  容器id

-tf              # 显示日志
-tf --tail number # 显示日志条数
[root@kuangshen /]# docker logs -tf --tail 10 dce7b86171bf
```

#### 查看镜像的元数据

```shell
docker inspect 容器id
```

![image-20231002220308497](spring boot3.assets/image-20231002220308497.png)

#### 进入容器内容部（重中重）

```shell
docker exec -it 容器id bash  #或者docker exec -it 容器ID sh
#退出容器：exit
```

#### 进入正在运行中的容器

```cobol
sudo docker attach 容器ID/容器名
```

#### 主机内容和容器内容相互拷贝(没用)

> 此拷贝是一个手动过程，未来使用 -v 卷的技术，可以实现自动

将宿主机的文件复制到容器内部的指定目录

```shell
docker cp 文件名称 容器id:容器内部路径
```

![image-20231002221107526](spring boot3.assets/image-20231002221107526.png)

#### 重启&启动&停止&删除容器

容器的启动，停止，删除等操作，后续经常会使用到

```shell
# 重新启动容器
docker restart 容器id        # 启动停止运行的容器
docker start 容器id          # 停止指定的容器（删除容器前，需要先停止容器）
```

停止

```shell
docker stop 容器名/容器ID     # 停止全部容器
docker stop $(docker ps -qa) # 删除指定容器
```

删除

```shell
#删除一个容器
docker rm -f 容器名/容器ID
#删除多个容器 空格隔开要删除的容器名或容器ID
docker rm -f 容器名/容器ID 容器名/容器ID 容器名/容器ID
#删除全部容器
docker rm -f $(docker ps -aq)
```

#### 导入和导出容器

##### 导出容器

```shell
docker export 容器ID > 文件名
```

如：docker export 97 > ubuntu.tar.gz

<img src="spring boot3.assets/image-20231002213745064.png" alt="image-20231002213745064" style="zoom:80%;" />

##### 导入容器

```shell
cat 文件名 | docker import - 镜像用户/镜像名:镜像版本号（镜像用户和版本号可以不写）
cat test.tar.gz | docker import - ubuntu2
```

<img src="spring boot3.assets/image-20231002213731425.png" alt="image-20231002213731425" style="zoom:80%;" />

 

## 六、Docker应用 

 [docker常用软件安装](https://blog.csdn.net/qq_43430759/article/details/126345572)

### 一 tomcat

#### 1.1 查找镜像

```bash
# 最新版的tomcat里面webapps改了名字，如果使用的话注意进入容器修改名称。这里我们使用tomcat8，不需要做修改。
docker search tomcat8-jdk8
```

![image-20231002232400893](spring boot3.assets/image-20231002232400893.png)

#### 1.2 拉取镜像到本地

```bash
docker pull billygoo/tomcat8-jdk8
```

![image-20231002232352140](spring boot3.assets/image-20231002232352140.png)

#### 1.3 创建容器实例

```bash
docker run -d -p 8080:8080 --name mystery_tomcat8 billygoo/tomcat8-jdk8
```

#### 1.4 使用

```bash
# 浏览器访问
ip地址：8080
```

![image-20231002232337485](spring boot3.assets/image-20231002232337485.png)

### 二 nginx

#### 2.1 查找镜像

```bash
docker search nginx
```

![image-20231002232326184](spring boot3.assets/image-20231002232326184.png)

#### 2.2 拉取镜像到本地

```bash
# 这里就默认安装latest版本
docker pull nginx
```

![image-20231002232317239](spring boot3.assets/image-20231002232317239.png)

#### 2.3 创建容器实例

```bash
# 1 创建之前先准备数据卷文件夹
mkdir -p /mydata/nginx/conf
mkdir -p /mydata/nginx/log
mkdir -p /mydata/nginx/html
```

\

![image-20231002235421110](spring boot3.assets/image-20231002235421110.png)

```shell
# 2 第一次创建nginx容器
docker run -d -p 3389:80 --name nginx nginx:latest
# 3 复制配置文件到主机上
docker cp nginx:/etc/nginx/nginx.conf /mydata/nginx/conf/nginx.conf
# 将容器conf.d文件夹下内容复制到宿主机
docker cp nginx:/etc/nginx/conf.d /mydata/nginx/conf/conf.d
# 将容器中的html文件夹复制到宿主机
docker cp nginx:/usr/share/nginx/html /mydata/nginx/
# 复制完之后删除容器，过河拆桥
docker rm -f nginx
# 4 第二次创建nginx容器
docker run -d -p 3389:80 --name mystery_nginx -v /mydata/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /mydata/nginx/conf/conf.d:/etc/nginx/conf.d -v /mydata/nginx/log:/var/log/nginx -v /mydata/nginx/html:/usr/share/nginx/html nginx:latest
```

#### 2.4 使用

外网测试

```
浏览器直接输入ip即可（80端口可以省略）
```

![image-20231002232302011](spring boot3.assets/image-20231002235504999.png)

内网测试

```
[root@lfj home]# curl localhost:3389
```

![image-20231003214648419](spring boot3.assets/image-20231003214648419.png)

### 三 mysql5.7

#### 3.1 查找镜像

```bash
docker search mysql:5.7
```

![image-20231003232219063](spring boot3.assets/image-20231003232219063.png)

#### 3.2 拉取镜像到本地

```bash
docker pull mysql:5.7
```

#### 3.3 创建容器实例

```bash
# 参数说明：-d 后台守护进程创建容器、-p 端口映射、--privileged 赋予容器内root用户真正的root权限、-v 文件挂载 -e 设置mysql参数，这里设置root密码、--name 设置创建的容器实例名称
docker run -d -p 3306:3306 --privileged=true -v /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql -v /mydata/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=root  --name mystery_mysql mysql:5.7
```

#### 3.4 使用

##### 3.4.1 修改配置文件

```bash
vim /mydata/mysql/conf/my.cnf
```

```bash
# 添加如下内容
[client]
default_character_set=utf8
[mysqld]
collation_server = utf8_general_ci
character_set_server = utf8
123456
```

##### 3.4.2 查看字符集是否修改成功

<img src="spring boot3.assets/image-20231003232416137.png" alt="image-20231003232416137"  />

##### 3.4.3 操作数据库

![image-20231003232433654](spring boot3.assets/image-20231003232433654.png)
远程操作，这里使用datagrip

![image-20231003232451777](temp.assets/image-20231003232451777.png)

### 附：防火墙开放端口方法

如果是阿里云或华为云之类的ESC服务器，还需要去控制台配置安全组规则。

```bash
firewall-cmd --zone=public --add-port=15672/tcp --permanent
firewall-cmd --reload
```

## 七、数据卷【`重点`】

 数据卷：将宿主机的一个目录映射到容器的一个目录中。可以在宿主机中操作目录中的内容，那么容器内部映射的文件，也会跟着一起改变。**容器的持久化和数据共享同步操作！**

#### 7.1 创建数据卷

创建数据卷之后，默认会存放在一个目录下 **/var/lib/docker/volumes/数据卷名称/_data**

```shell
如何确定是具名挂载还是匿名挂载，还是指定路径挂载！
-v 容器内路径  # 匿名挂载
-v 卷名：容器内路径 # 具名挂载
/宿主机路径::容器内路径 # 指定路径挂载
```

```shell
docker volume create 数据卷名称
```

#### 7.2 查看数据卷详情

查看数据卷的详细信息，可以查询到存放路径，创建时间等等

```shell
docker volume inspect 数据卷名称
```

#### 7.3 查看全部数据卷

查看全部数据卷信息

```shell
docker volume ls
```

![img](spring boot3.assets/1647324763299-d644aedc-1c8f-4148-9659-f99a38e9d3a0-1696260400703156.png)

#### 7.4 删除数据卷

```shell
docker volume rm 数据卷名称
```

#### 7.5 容器映射数据卷[重点]

映射有两种方式：

- 通过数据卷名称映射，如果数据卷不存在。Docker会帮你自动创建，会将容器内部自带的文件，存储在默认的存放路径中。
- 通过路径映射数据卷，直接指定一个路径作为数据卷的存放位置。但是这个路径下是空的。

```shell
# 通过数据卷名称映射docker run -v 数据卷名称:容器内部的路径 镜像id
# 通过路径映射数据卷docker run -v 路径:容器内部的路径 镜像id   

docker run -d -p 8081:8080 --name tomcat -v[volume] /opt/tocmat:/usr/local/tomcat/webapps/ROOT b8
```

#### 7.6 通过Dockerfile挂载(推荐)

**编写dockerfile**

```shell
 vim dockerfile
```

![image-20231003230529074](spring boot3.assets/image-20231003230529074.png)

**将其制作为镜像**

```shell
docker build -f dockerfile路径 -t 镜像名称[:tag] .
```

![image-20231003230126680](spring boot3.assets/image-20231003230126680.png)

**启动容器**

![image-20231003230305737](spring boot3.assets/image-20231003230305737.png)

#### 7.7 数据卷容器

==容器之间配置信息的传递,数据卷容器的生命周期一直持续到没有容器使用为止。但是一旦持久化到了本地，这个时候，本地的数据是不会删除的==

![image-20231003230910597](spring boot3.assets/image-20231003230910597.png)



<img src="spring boot3.assets/image-20231003231411163.png" alt="image-20231003231411163"  />

<img src="spring boot3.assets/image-20231003231555770.png" alt="image-20231003231555770"  />

例子

![image-20231003231909315](spring boot3.assets/image-20231003231909315.png)

## 八  Docker 容器网络连接

![image-20231004162956923](spring boot3.assets/image-20231004162956923.png)

### 端口暴露

在使用 `docker run` 命令时，以下是有关设置宿主机端口和容器端口的一些详细信息：

- `-p` 参数用于将宿主机端口映射到容器端口。
- 宿主机端口是您希望暴露给外部访问的端口，可以是任何未被占用的可用端口。
- 容器端口是容器中正在监听的端口。这是您的应用程序在容器内部使用的端口。

以下是 `docker run` 命令的示例，说明了如何设置宿主机和容器端口映射：

```
docker run -it -d --name my-container -p 8080:80 my-image:tag /bin/bash
```

在上面的示例中，**我们将宿主机的 8080 端口映射到容器的 80 端口。这意味着当您在宿主机上访问 `localhost:8080` 时，流量将被转发到容器的 80 端口。**

请根据您的需求和应用程序的要求替换示例中的值。确保宿主机端口未被占用，以及容器中正在运行的应用程序正在监听指定的容器端口

![image-20231002231435742](spring boot3.assets/image-20231002231435742.png)

### 网络端口映射

#### 原理

<img src="spring boot3.assets/image-20231004164744482.png" alt="image-20231004164744482" style="zoom:80%;" />

**例子**

tomcat01 和 tomcat02 是公用的一个路由器，docker0。所有的容器不指定网络的情况下，都是 docker0 路由的，docker会给我们的容器分配一个默认的可用IP

<img src="spring boot3.assets/image-20231004164419727.png" alt="image-20231004164419727" style="zoom:67%;" />

#### 设置

创建了一个 python 应用的容器

```shell
:~$ docker run -d -P training/webapp python app.py
```

两种方式的区别是:

- **-P :**是容器内部端口**随机**映射到主机的高端口。(自动)

- **-p :** 是容器内部端口绑定到**指定**的主机端口。

```shell
:~$ docker run -d -p 5000:5000 training/webapp python app.py
```

![image-20231004165218942](spring boot3.assets/image-20231004165218942.png)

另外，可以指定容器绑定的网络地址，比如绑定 127.0.0.1。这样我们就可以通过访问 127.0.0.1:5001 来访问容器的 5000 端口。

```shell
:~$ docker run -d -p 127.0.0.1:5001:5000 training/webapp python app.py
```

![image-20231004165321277](spring boot3.assets/image-20231004165321277.png)

上面的例子中，默认都是绑定 tcp 端口，如果要绑定 UDP 端口，可以在端口后面加上 **/udp**。

```plain
:~$ docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py
```

![image-20231004165353658](spring boot3.assets/image-20231004165353658.png)

### Docker 容器网络互联

端口映射并不是唯一把 docker 连接到另一个容器的方法。如果你有多个容器之间需要互相连接，推荐使用 Docker Compose。下面是其中一种方式

####  新建网络

下面先创建一个新的 Docker 网络。

```shell
$ docker network create -d bridge test-net
```

并使用以下命令查询网络：

```shell
[lee@localhost docker]$ docker network ls
```

![image-20231004165759645](spring boot3.assets/image-20231004165759645.png)

参数说明：

**-d**：参数指定 Docker 网络类型，有 bridge、overlay。

其中 overlay 网络类型用于 Swarm mode，暂时忽略它。

#### 连接容器

运行一个容器并连接到新建的 test-net 网络:

```shell
$ docker run -itd --name test1 --network test-net ubuntu /bin/bash
```

打开新的终端，再运行一个容器并加入到 test-net 网络:

```shell
$ docker run -itd --name test2 --network test-net ubuntu /bin/bash
```

然后回到第一个终端，用       docker ps         进行查询

![image-20231004165854613](spring boot3.assets/image-20231004165854613.png)

下面通过 ping 来证明 test1 容器和 test2 容器建立了互联关系。

进入 test1 容器

```shell
docker exec -it test1 /bin/bash ping test2
```

在 test1 容器输入以下命令：

点击图片查看大图：

<img src="spring boot3.assets/image-20231004170159129.png" alt="image-20231004170159129" style="zoom:75%;" />

同理在 test2 容器也会成功连接到:(切换终端)

```shell
docker exec -it test2 /bin/bash
```

<img src="spring boot3.assets/image-20231004170148238.png" alt="image-20231004170148238" style="zoom: 70%;" />

## 九、Dockerfile自定义镜像【`重点`】

可以从中央仓库下载一个镜像，也可以自己手动去制作一个镜像，需要通过Dockerfile去指定自定义镜像的信息

### 9.1 Docker镜像加载原理

**docker的镜像实际上由一层一层的文件系统组成,这种层级的文件系统UnionFS**

![image-20231003215610508](spring boot3.assets/image-20231003215610508.png)

平时安装进虚拟机的CentOS都是好几个G，Docker才200M

![image-20231003215701434](spring boot3.assets/image-20231003215701434.png)

对于一个精简的OS, rootfs可以很小,只需要包含最基本的命令,工具和程序库就可以了,因为底层直接用主机的kernel 自己只需要提供rootfs就可以了。由此可见对于不同的linux发行版, bootfs基本是一致的, rootfs会有差别,因此不同的发行版可以公用bootfs.  **虚拟机是分钟级别，容器是秒级！**

### 9.2 分层理解

![image-20231003220412051](spring boot3.assets/image-20231003220412051.png)

所有的Docker镜像都起始于一个基础镜像层,当进行修改或增加新的内容时,就会在当前镜像层之上,创建新的镜像层。假如基于Ubuntu Linux 16.04创建一个新的镜像,这就是新镜像的第一层;如果在该镜像中添加Python包,就会在基础镜像层之上创建第二个镜像层;如果继续添加一个安全补丁,就会创建第三个镜像层。
该镜像当前已经包含3个镜像层,如下图所示

<img src="spring boot3.assets/image-20231003220514796.png" alt="image-20231003220514796" style="zoom:67%;" />

特点

+ Docker镜像都是只读的,当容器启动时,一个新的可写层被加载到镜像的顶部
  + <img src="spring boot3.assets/image-20231003221145247.png" alt="image-20231003221145247" style="zoom:80%;" />

+ 这一层就是我们通常说的容器层，容器之下的都叫镜像层！
  + ![image-20231003221412282](spring boot3.assets/image-20231003221412282.png)

### 9.4 DockerFile

#### 9.4.1 DockerFile介绍

dockerfile 是用来构建dokcer镜像的文件！命令参数脚本！文本内容包含了一条条构建镜像所需的指令和说明。

构建步骤:

1. 编写一个 dockerfile 文件
2. docker build 构建成为一个镜像
3. docker run运行镜像
4. docker push发布镜像(DockerHub、阿里云镜像仓库!)

官方镜像都是基础包，很多功能没有，通常会自己搭建自己的镜像！

<img src="spring boot3.assets/image-20231003233238490.png" alt="image-20231003233238490" style="zoom: 67%;" />

#### 9.4.2 DockerFile构建过程

**基础知识**

1、每个保留关键字（指令）都是必须是大写字母
2、执行从上到下顺序执行
3、#表示注释
4、每一个指令都会创建提交一个新的镜像层，并提交

如下图:

<img src="spring boot3.assets/image-20231003233459495.png" alt="image-20231003233459495" style="zoom:67%;" />

**Dockerfile(properties)**

创建自定义镜像就需要创建一个Dockerfile，如下为Dockerfile的语言

```shell
from: 指定当前自定义镜像依赖的环境 tocmat
copy: 将相对路径下的内容复制到自定义镜像中 /opt/springboot.jar
workdir: 声明镜像的默认工作目录 /opt/resource
run: 执行的命令，可以编写多个
cmd: 需要执行的命令（在workdir下执行的，cmd可以写多个，只以最后一个为准） java -jar

# 举个例子，制作SSM容器镜像，而且ssm.war要放在Dockerfile的同级目录下
from daocloud.io/library/tomcat:8.5.15-jre8
copy ssm.war /usr/local/tomcat/webapps
```

 **通过Dockerfile制作镜像**

编写完Dockerfile后需要通过命令将其制作为镜像，并且要在Dockerfile的当前目录下，之后即可在镜像中查看到指定的镜像信息，注意最后的 .

```shell
docker build -t 镜像名称[:tag] .
```

docker build 指令用来编译Dockerfile文件，默认的情况下 docker build 会在当前的上下文目录中查找Dockerfile文件进行编译

-f : 参数可以用来指定Dockerfile文件，如果文件名为Dockerfile，可以不加这一句`-f Dockerfile`

-t : 参数用来设置镜像的名字及标签

#### 9.4.3 DockerFile指令

<img src="spring boot3.assets/image-20231003234256240.png" alt="image-20231003234256240" style="zoom:80%;" />

##### COPY

复制指令，从上下文目录中复制文件或者目录到容器里指定路径。

格式：

```plain
COPY [--chown=<user>:<group>] <源路径1>...  <目标路径>
COPY [--chown=<user>:<group>] ["<源路径1>",...  "<目标路径>"]
```

**[--chown=:]**：可选参数，用户改变复制到容器内文件的拥有者和属组。

**<源路径>**：源文件或者源目录，这里可以是通配符表达式，其通配符规则要满足 Go 的 filepath.Match 规则。例如：

```plain
COPY hom* /mydir/
COPY hom?.txt /mydir/
```

**<目标路径>**：容器内的指定路径，该路径不用事先建好，路径不存在的话，会自动创建。

##### ADD

ADD 指令和 COPY 的使用格式一致（同样需求下，官方推荐使用 COPY）。功能也类似，不同之处如下：

- ADD 的优点：在执行 <源文件> 为 tar 压缩文件的话，压缩格式为 gzip, bzip2 以及 xz 的情况下，会自动复制并解压到 <目标路径>。

- ADD 的缺点：在不解压的前提下，无法复制 tar 压缩文件。会令镜像构建缓存失效，从而可能会令镜像构建变得比较缓慢。具体是否使用，可以根据是否需要自动解压来决定。

##### CMD

类似于 RUN 指令，用于运行程序，但二者运行的时间点不同:

- CMD 在docker run 时运行。

- RUN 是在 docker build。

**作用**：为启动的容器指定默认要运行的程序，程序运行结束，容器也就结束。CMD 指令指定的程序可被 docker run 命令行参数中指定要运行的程序所覆盖。

**注意**：如果 Dockerfile 中如果存在多个 CMD 指令，仅最后一个生效。

格式：

```plain
CMD <shell 命令> 
CMD ["<可执行文件或命令>","<param1>","<param2>",...] 
CMD ["<param1>","<param2>",...]  # 该写法是为 ENTRYPOINT 指令指定的程序提供默认参数
```

推荐使用第二种格式，执行过程比较明确。第一种格式实际上在运行的过程中也会自动转换成第二种格式运行，并且默认可执行文件是 sh。

##### ENTRYPOINT

entrypoint类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖，而且这些命令行参数会被当作参数送给 ENTRYPOINT 指令指定的程序。

但是, 如果运行 docker run 时使用了 --entrypoint 选项，此选项的参数可当作要运行的程序覆盖 ENTRYPOINT 指令指定的程序。

**优点**：在执行 docker run 的时候可以指定 ENTRYPOINT 运行所需的参数。

**注意**：如果 Dockerfile 中如果存在多个 ENTRYPOINT 指令，仅最后一个生效。

格式：

```plain
ENTRYPOINT ["<executeable>","<param1>","<param2>",...]
```

可以搭配 CMD 命令使用：一般是变参才会使用 CMD ，这里的 CMD 等于是在给 ENTRYPOINT 传参，以下示例会提到。

**示例：**

假设已通过 Dockerfile 构建了 nginx:test 镜像：

```shell
FROM nginx

ENTRYPOINT ["nginx", "-c"] # 定参
CMD ["/etc/nginx/nginx.conf"] # 变参
```

1、不传参运行

```plain
$ docker run  nginx:test    # EPOSITORY：AG
```

容器内会默认运行以下命令，启动主进程。

```plain
nginx -c /etc/nginx/nginx.conf
```

2、传参运行

```plain
$ docker run  nginx:test -c /etc/nginx/new.conf
```

容器内会默认运行以下命令，启动主进程(/etc/nginx/new.conf:假设容器内已有此文件)

```plain
nginx -c /etc/nginx/new.conf
```

**示例2：**

```shell
#编写 dockerfile 文件
[root@kuangshen dockerfile]# vim dockerfile-cmd-test
FROM centos
CMD ["1s","-a"] #变参

# 想追加一个命令 -l    ls -al
[root@kuangshen dockerfile]# docker run dd8e4401d72f -l
docker: Error response from daemon: OCI runtime create failed: container_linux.go:349: starting
container process caused "exec: \"-1\": executable file not found in SPATH": unknown.
#cmd的清理下 -1 替换了CMD ["Is"，"-a"] 命令，-1 不是命令所以报错！
```

```shell
[root@kuangshen dockerfile]# cat dockerfile-cmd-entrypoint
FROM centos
ENTRYPOIT ["Is","-a"]  #定参

# 想追加一个命令 -l    ls -al
[root@kuangshen dockerfile]# docker run dd8e4401d72f -l
.dockerenv
bin
dev
etc
...
```

##### ENV

设置环境变量，定义了环境变量，那么在后续的指令中，就可以使用这个环境变量。

格式：

```plain
ENV <key> <value>
ENV <key1>=<value1> <key2>=<value2>...
```

以下示例设置 NODE_VERSION = 7.2.0 ， 在后续的指令中可以通过 $NODE_VERSION 引用：

```plain
ENV NODE_VERSION 7.2.0

RUN curl -SLO "https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-x64.tar.xz" \
  && curl -SLO "https://nodejs.org/dist/v$NODE_VERSION/SHASUMS256.txt.asc"
```

##### ARG

构建参数，与 ENV 作用一至。不过作用域不一样。ARG 设置的环境变量仅对 Dockerfile 内有效，也就是说只有 docker build 的过程中有效，构建好的镜像内不存在此环境变量。

构建命令 docker build 中可以用 --build-arg <参数名>=<值> 来覆盖。

格式：

```plain
ARG <参数名>[=<默认值>]
```

##### VOLUME

定义匿名数据卷。在启动容器时忘记挂载数据卷，会自动挂载到匿名卷。

作用：

- 避免重要的数据，因容器重启而丢失，这是非常致命的。

- 避免容器不断变大。

格式：

```plain
VOLUME ["<路径1>", "<路径2>"...]
VOLUME <路径>
```

在启动容器 docker run 的时候，我们可以通过 -v 参数修改挂载点。

##### EXPOSE

仅仅只是声明端口。

作用：

- 帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射。

- 在运行时使用随机端口映射时，也就是 docker run -P 时，会自动随机映射 EXPOSE 的端口。

格式：

```plain
EXPOSE <端口1> [<端口2>...]
```

##### WORKDIR

指定工作目录。用 WORKDIR 指定的工作目录，会在构建镜像的每一层中都存在。（WORKDIR 指定的工作目录，必须是提前创建好的）。

docker build 构建镜像过程中的，每一个 RUN 命令都是新建的一层。只有通过 WORKDIR 创建的目录才会一直存在。

格式：

```plain
WORKDIR <工作目录路径>
```

##### USER

用于指定执行后续命令的用户和用户组，这边只是切换后续命令执行的用户（用户和用户组必须提前已经存在）。

格式：

```plain
USER <用户名>[:<用户组>]
```

##### HEALTHCHECK

用于指定某个程序或者指令来监控 docker 容器服务的运行状态。

格式：

```shell
HEALTHCHECK [选项] CMD <命令>：设置检查容器健康状况的命令
HEALTHCHECK NONE：如果基础镜像有健康检查指令，使用这行可以屏蔽掉其健康检查指令

HEALTHCHECK [选项] CMD <命令> : 这边 CMD 后面跟随的命令使用，可以参考 CMD 的用法。
```

#### 9.4.4 案例

**例子1**

![image-20231004140707324](spring boot3.assets/image-20231004140707324.png)

原生的centos

![image-20231004140616937](spring boot3.assets/image-20231004140616937.png)

增加之后的镜像

![image-20231004140629675](spring boot3.assets/image-20231004140629675.png)

**例子2**

**1. 构建tomcat镜像, 创建一个tomcat文件夹放需要的文件**

![image-20231004143101909](spring boot3.assets/image-20231004143101909.png)

**2. 编写DockerFile**

![image-20231004144042476](spring boot3.assets/image-20231004144042476.png)

**3. 构建镜像**

-f 可以不加默认自动搜索 DockerFile文件

![image-20231004144357415](spring boot3.assets/image-20231004144357415.png)

**4、 启动镜像**
**5、访问测试**
**6、发布项目(由于做了卷挂载,我们直接在本地编写项目就可以发布了!)**

启动容器,并进行卷挂载

![image-20231004145606886](spring boot3.assets/image-20231004145606886.png)

可以在主机中挂载的目录里编写项目

![image-20231004145747864](spring boot3.assets/image-20231004145747864.png)

效果

![image-20231004145827943](spring boot3.assets/image-20231004145827943.png)

#### 9.4.5 发布镜像

##### Docker Hub官网发布

> DockerHub

1、地址 https://hub.docker.com/注册的账号
2、在服务器上提交镜像

**登录**

登录需要输入用户名和密码，登录成功后，我们就可以从 docker hub 上拉取自己账号下的全部镜像。

```plain
$ docker login
```

<img src="spring boot3.assets/image-20231004153142792.png" alt="image-20231004153142792" style="zoom:67%;" />

**推送镜像**

```shell
# 增加一个 tag
[root@kuangshen tomcat]# docker tag f8559daf1fc2 kuangshen/tomcat:1.0

# docker psuh上去即可
[root@kuangshen tomcat]# docker push kuangshen/tomcat:1.0
The push refers to repository [docker.io/kuangshen/tomcat]
fcc7fccb8e04: Preparing
b5577f344233: Preparing
bdcb94365850: Preparing
```

**退出**

退出 docker hub 可以使用以下命令：

```plain
$ docker logout
```

##### 阿里云镜像服务发布

> 阿里云镜像服务上

1、登录阿里云
2、找到容器镜像服务
3、创建命名空间

<img src="spring boot3.assets/image-20231004152911221.png" alt="image-20231004152911221" style="zoom:83%;" />

![image-20231004152417681](spring boot3.assets/image-20231004152417681.png)

### 9.5 Spring Boot + Docker实战

**java**

```properties
# 端口号
server:
  port: 2001
```

controller

```java
@RequestMapping("/v1")
@RestController
public class HelloController {

	@Value("${server.port}")
	private int port;

	@GetMapping("")
	public String test() {
		return "invoke url /,port="+port;
	}

	@GetMapping("/test1")
	public String test1() {
		return "invoke url /test1,port="+port;
	}

	@GetMapping("/test2")
	public String test2() {
		return "invoke url /test2,port="+port;
	}
}
```

测试

```c
http://127.0.0.1:2001/v1
http://127.0.0.1:2001/v1/test1
http://127.0.0.1:2001/v1/test2
```

#### 1.编写DockerFile

```yml
# 该镜像需要依赖的基础镜像
# 使用 Amazon Corretto 17 (jdk17)作为基础镜像
FROM amazoncorretto:17
# 指定维护者的名字
MAINTAINER jindao "1665834268@qq.com"

# 将本地文件夹挂载到当前容器
# VOLUME ["/tmp"] 

# 使用东八区时间环境
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# 将指定目录下的jar包复制到docker容器的/目录下
COPY /my-app.jar /app.jar

# CMD ["--server.port=8080"]
# 声明服务运行在8080端口
EXPOSE 2001:

# 切换WORKDIR
# WORKDIR /opt/web/

# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-jar","app.jar"]
```

#### 2.构建镜像

在构建前，请确保 **xxx.jar** 文件与**Dockerfile**在同一个目录

```shell
# 构建镜像laker-java:v1
docker build -t laker-java:v1 .
```

![img](spring boot3.assets/4fefaab84a8542eb85a0978dc6fe0a1c.png)

#### 3.查看编译的镜像

```bash
docker image ls 或者 docker images
```

#### 4.启动镜像测试

```bash
docker run -d -p 8080:2001 --name laker-v1 laker-java:v1
```

- -d 是后台运行
- -p 8080:8080 是端口映射 (宿主机端口:容器端口)
- –name laker-v1 给容器取 名字
- 最后跟的 laker-java:v1 是我打包好的镜像名称。

测试

![image-20231005194312611](spring boot3.assets/image-20231005194312611.png)

![image-20231005194451414](spring boot3.assets/image-20231005194451414.png)

#### 5.创建私有仓库

// 没用直接用官方的方法

官方的Docker hub是一个用于管理公共镜像的好地方，我们可以在上面找到我们想要的镜像，也可以把我们自己的镜像推送上去。这个可以通过[开源软件](https://so.csdn.net/so/search?q=开源软件&spm=1001.2101.3001.7020)Registry来达成目的。

官方在**Docker hub**上提供了[registry](https://hub.docker.com/_/registry)的镜像，我们可以直接使用该registry镜像来构建一个容器，搭建我们自己的私有仓库服务。

##### 5.1查询registry镜像

```bash
docker search registry
```

![img](spring boot3.assets/801724b9703747cb9baaa18df8e18dc5.png)

##### 5.2加载镜像

> 选用官方的第一个镜像，也是stars数最多的

```bash
docker pull registry
```

##### 5.3运行镜像容器

```bash
docker run -d \
 --name registry2 \
 --restart=always \
 -p 5000:5000 \
 -v /lfj/registry:/var/lib/registry \
 registry
```

- -d：后台运行
- –name registry2：指定容器名
- –restart=always：设置开机自动启动
- -p 5000:5000：端口映射宿主机，通过宿主机地址访问
- -v /laker/registry:/var/lib/registry：把镜像存储目录挂载到本地，方便管理和持久化，如果不加，重启后镜像都会丢失
- -v /laker/registry/srv-config.yml:/etc/docker/registry/config.yml：把配置文件挂载到本地，方便修改和保存

##### 5.4检查容器

**docker ps**

> docker ps -a 是查询所有状态的容器。

![img](spring boot3.assets/ba994804968e4a28a23a05982bdc5d2b.png)

**检查仓库镜像信息**

```
curl -XGET http://127.0.0.1:5000/v2/_catalog
```

![img](spring boot3.assets/c1c49862b47f4d6a8925d782d7b653c8.png)

**也可以在浏览器上测试：**

![img](spring boot3.assets/57a91d6303d34e4eac54a36fe9c9a6d8.png)

#### 6.搭建Registry web

**地址**：https://hub.docker.com/r/[hyper](https://so.csdn.net/so/search?q=hyper&spm=1001.2101.3001.7020)/docker-registry-web

##### 6.1首先搜索并拉取镜像

```
docker search docker-registry-web
docker pull hyper/docker-registry-web　　　　# 这个镜像用的人较多
```

```
registry:
 url: http://10.224.77.100:5000/v2
 name: localhost:5000
 readonly: false
 auth:
   enabled: false
```



##### 6.2运行一个registry web容器

```bash
docker run -d \
	--name registry-web \
	--restart=always \
	-v /laker/registry-web/web-config.yml:/conf/config.yml:ro \
	-p 8000:8080 \
	hyper/docker-registry-web
```

##### 6.3验证

**在浏览器访问**：http://ip:8000

![img](spring boot3.assets/f80d2d05214c44519c60f66b3961c2ab.png)

#### 7.推送镜像到私有仓库

创建好私有仓库之后，就可以使用 `docker tag` 来标记一个镜像，然后推送它到仓库。例如私有仓库地址为 `10.0.0.1:5000`。

```bash
# 重新标记镜像
docker tag laker-java:v1 10.0.0.1:5000/laker-java:v1
# 推送镜像
docker push 10.0.0.1:5000/laker-java:v1
```

**前提条件**

想让本网段的其他主机也能把镜像推送到私有仓库。你就得把例如 `10.0.0.1:5000` 这样的内网地址作为私有仓库地址，这时你会发现无法成功推送镜像。

这是因为 Docker 默认不允许非 `HTTPS` 方式推送镜像。我们可以通过 Docker 的配置选项来取消这个限制，或者查看下一节配置能够通过 `HTTPS` 访问的私有仓库。

请在 `/etc/docker/daemon.json` 中写入如下内容（如果文件不存在请新建该文件）

配置非https访问：

```
{
  "registry-mirror": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ],
  "insecure-registries": [
    "10.0.0.1:5000"
  ]
}
```

然后重启Docker

```bash
systemctl daemon-reload    # 守护进程重启
systemctl restart docker    # 重启docker服务
```

Windows系统在下图所示位置配置，注意要重启哦。

![img](spring boot3.assets/3df02796f0434215abdb906ebdd3f3e7.png)

**推送截图示例**

![img](spring boot3.assets/7eb39cfd02274732be5fcc84ea3c69a0.png)

**可以在Registry Web上查看镜像。**

![img](spring boot3.assets/784c8efc585d47c88b202294a90dc9e5.png)

#### 8.服务器拉取镜像部署

```bash
docker pull 10.0.0.1:5000/laker-java:v1
```

**结果如下图**：

![img](spring boot3.assets/80b371ba0b544e35a165c86a214f95ec.png)

##### 8.1 资源限制

- 内存
- cpu
- 磁盘

在生产环境中，建议每个容器都添加相应的资源限制。下面给出一些执行`docker run`命令启动容器时可以传递的资源限制参数：

```css
  --cpus                          限制 CPU 配额
  -m, --memory                    限制内存配额
  --pids-limit                    限制容器的 PID 个数
```

例如我想要启动一个 1 核 2G 的容器，并且限制在容器内最多只能创建 1000 个 PID，启动命令如下：

```lua
$ docker run -it --cpus=1 -m=2048m --pids-limit=1000 busybox sh
```

推荐在生产环境中限制 CPU、内存、PID 等资源，这样即便应用程序有漏洞，也不会导致主机的资源完全耗尽，最大限度降低安全风险。

文章知识点与官方知识档案匹配，可进一步学习相关知识

## 十、Docker-Compose【`重点`】

+ Docker Compose是一个工具，用于定义和运行多容器应用程序的工具；
+ Docker Compose通过yml文件定义多容器的docker应用；
+ Docker Compose通过一条命令根据yml文件的定义去创建或管理多容器；
+ <img src="spring boot3.assets/image-20231002224038346-169646749658721.png" alt="image-20231002224038346" style="zoom: 67%;" />

#### 基本安装

[Docker Compose介绍和安装](https://blog.csdn.net/juanxiaseng0838/article/details/127553225)

Docker Compose安装的最新的版本Docker Compose version v2.12.2，对于Mac和Windows安装好Docker以后，就已经安装好Docker Compose，不需要手动安装，这里的安装方式是基于Linux的Cnetos的，可以参考[官方网站](https://docs.docker.com/compose/install/)去安装。

```shell
[root@localhost ~]# sudo yum install docker-compose-plugin
已加载插件：fastestmirror, langpacks
Loading mirror speeds from cached hostfile
 * base: mirror.lzu.edu.cn
 * extras: ftp.ksu.edu.tw
 * updates: mirror.lzu.edu.cn
正在解决依赖关系
--> 正在检查事务
---> 软件包 docker-compose-plugin.x86_64.0.2.12.2-3.el7 将被 安装
--> 解决依赖关系完成
```

![image-20231002194648410](spring boot3.assets/image-20231002194648410-169646749658822.png)

查看安装情况

```shell
[root@localhost ~]# docker compose version
Docker Compose version v2.12.2
```

#### Docker Compose基本命令

Docker Compose命令基本上和Docker相差不多，主要就是对Docker Compose生命周期控制、日志格式等相关命令，可以通过docker-compose --help进行帮助。

```shell
[root@localhost ~]# docker compose --help
 
Usage:  docker compose [OPTIONS] COMMAND
 
Docker Compose
 
Options:
      --ansi string                Control when to print ANSI control characters ("never"|"always"|"auto") (default "auto")
      --compatibility              Run compose in backward compatibility mode
      --env-file string            Specify an alternate environment file.
  -f, --file stringArray           Compose configuration files
      --profile stringArray        Specify a profile to enable
      --project-directory string   Specify an alternate working directory
                                   (default: the path of the, first specified, Compose file)
  -p, --project-name string        Project name
 
Commands:
  build       Build or rebuild services
  convert     Converts the compose file to platform's canonical format
  cp          Copy files/folders between a service container and the local filesystem
  create      Creates containers for a service.
  down        Stop and remove containers, networks
  events      Receive real time events from containers.
  exec        Execute a command in a running container.
  images      List images used by the created containers
  kill        Force stop service containers.
  logs        View output from containers
  ls          List running compose projects
  pause       Pause services
  port        Print the public port for a port binding.
  ps          List containers
  pull        Pull service images
  push        Push service images
  restart     Restart service containers
  rm          Removes stopped service containers
  run         Run a one-off command on a service.
  start       Start services
  stop        Stop services
  top         Display the running processes
  unpause     Unpause services
  up          Create and start containers
  version     Show the Docker Compose version information
 
Run 'docker compose COMMAND --help' for more information on a command.
```

安装

```shell
docker compose -f docker-compose.yml up -d
```

例子

```shell
#构建建启动nignx容器
docker-compose up -d nginx
#进入nginx容器中
docker-compose exec nginx bash
#将会停止UP命令启动的容器，并删除容器
docker-compose down   
#显示所有容器
docker-compose ps 
#重新启动nginx容器
docker-compose restart nginx 
#构建镜像
docker-compose build nginx  
#不带缓存的构建
docker-compose build --no-cache nginx 
#查看nginx的日志
docker-compose logs  nginx 
#查看nginx的实时日志
docker-compose logs -f nginx
#验证（docker-compose.yml）文件配置，
#当配置正确时，不输出任何内容，当文件配置错误，输出错误信息
docker-compose config  -q
#以json的形式输出nginx的docker日志
docker-compose events --json nginx
#暂停nignx容器
docker-compose pause nginx
#恢复ningx容器
docker-compose unpause nginx
#删除容器
docker-compose rm nginx  
#停止nignx容器
docker-compose stop nginx   
#启动nignx容器
docker-compose start nginx
```

#### Docker-Compose模板文件

**image**
image是指定服务的镜像名称或镜像ID

```vbnet
services: 
    web: 
        image: hello-world 
```

**build**
利用Dockerfile自动构建镜像，然后使用镜像启动服务容器。

```cobol
build:
  context: ../
  dockerfile: path/of/Dockerfile 
```

**command**
使用command可以覆盖容器启动后默认执行的命令。

```bash
command: bundle exec thin -p 3000
```

**container_name**
指定自定义容器名称

```vbnet
container_name: app
```

**depends_on**
表示服务之间的依赖关系。

```cobol
version: "3"
services:
  web:
    build: .
    depends_on:
      - redis
  redis:
    image: redis
 
#docker compose up:按依赖顺序启动服务,redis在web之前启动。
#docker-compose stop:按依赖顺序停止服务,web在redis之前停止。
```

**pid**
将PID模式设置为主机PID模式，跟主机系统共享进程命名空间。

```vbnet
pid: "host"
```

**ports**
映射端口

```cobol
ports:
 - "8000"
 - "49022:22"
 - "127.0.0.1:8001:8001"
```

**extra_hosts**
添加主机名映射。使用与docker客户端–add-host类似

```cobol
extra_hosts:
 - "somehost:162.242.195.82"
 - "otherhost:50.31.209.229"
```

**volumes**
目录映射，可以直接使用 [主机:容器]格式，或者使用[主机:容器:ro]格式，后者对于容器来说，数据卷是只读的，可以有效保护宿主机的文件系统。

```cobol
volumes:
  # 只指定一个路径，Docker会自动在创建一个目录。
  - /var/lib/mysql
  # 主机使用绝对路径和容器目录映射
  - /opt/data:/var/lib/mysql
  # 以Compose配置文件的目录为中心的相对路径和容器目录映射
  - ./cache:/tmp/cache
  # 使用用户的相对路径（~/ 表示的目录是 /home/<用户目录>/ 或者 /root/）。
  - ~/configs:/etc/configs/:ro
```

**dns**
自定义DNS服务器。

```cobol
dns：8.8.8.8
dns：
    - 8.8.8.8    
    - 9.9.9.9
```

**dns_search**
配置DNS搜索域。

```cobol
dns_search：example.com
dns_search：
    - domain1.example.com
    - domain2.example.com
```

**entrypoint**
设置入口命令

```cobol
entrypoint: /code/entrypoint.sh
entrypoint: ["php", "-d", "memory_limit=-1", "vendor/bin/phpunit"]
entrypoint: java -jar penngo_test.jar
```

**env_file**
从文件添加环境变量

```cobol
env_file: .env
env_file:
  - ./common.env
  - ./apps/web.env
  - /opt/runtime_opts.env
```

**environment**
添加环境变量。

```cobol
environment:
  RACK_ENV: development
  SHOW: 'true'
  SESSION_SECRET:
 
environment:
  - RACK_ENV=development
  - SHOW=true
  - SESSION_SECRET
```

**external_links**
链接到docker-compose.yml外部的容器

```cobol
external_links:
  - redis_1
  - project_db_1:mysql
  - project_db_1:postgresql
```

**cap_add**
增加指定容器的内核能力（capacity）。

```cobol
cap_add:
    - ALL
```

**cap_drop**
去掉指定容器的内核能力（capacity）。

```vbnet
cap_drop:
    - NET_ADMIN
```

**cgroup_parent**
创建了一个cgroup组名称为cgroups_1:

```vbnet
cgroup_parent: cgroups_1
```

**devices**
指定设备映射关系

```vbnet
devices:
    - "/dev/ttyUSB1:/dev/ttyUSB0" 
```

**expose**
暴露端口，但不映射到宿主机，只允许能被连接的服务访问。

```cobol
expose:
    - "3000"
    - "8000" 
```

**labels**
为容器添加Docker元数据（metadata）信息。

```vbnet
labels:
- "com.example.description=Accounting webapp"
- "com.example.department=Finance"
- "com.example.label-with-empty-value"
```

**links**
链接到其它服务中的容器

```markdown
links:
    - db
    - db:database
    - redis
```

**log_driver**
指定日志驱动类型。目前支持三种日志驱动类型：

```vbnet
log_driver: "json-file"
log_driver: "syslog"
log_driver: "none" 
```

**log_opt**
日志驱动的相关参数。

**net**
设置网络模式。

```vbnet
net: "bridge"
net: "none"
net: "host"
```

```shell
mkdir prod
cd prod/
vim prometheus.yml
vim docker-compose.yml
```

```yml
version: "3"
services:
  mysql:
    image: mysql:latest
    container_name: mysql_slaver11
    restart: always
    privileged: true
    ports:
      - 3307:3306
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      TZ: Asia/Shanghai
    volumes:
      - /wuming/mysql/slaver11/data:/var/lib/mysql
      - /wuming/mysql/slaver11/log:/var/log/mysql
      - /wuming/mysql/slaver11/conf/my.cnf:/etc/mysql/my.cnf
  mycat:
    image: manondidi/mycat:latest
    container_name: mycat
    restart: always
    ports:
      - 8066:8066
    volumes:
      - /wuming/mycat/conf:/usr/local/mycat/conf
      - /wuming/mycat/logs:/usr/local/mycat/logs
  redis:
    image: redis:latest
    container_name: redis_master
  
```

```shell
docker compose -f docker-compose.yml up -d
```

#### Docker Compose实战

[Docker Compose  (cnblogs.com)](https://www.cnblogs.com/wtzbk/p/15125977.html)

构建一个如下的应用，通过Nginx转发给后端的两个Java应用;

<img src="spring boot3.assets/1627207873318-2e20675a-e606-4ff1-b7cd-b4fb346a743b-169646749658824.png" alt="img" style="zoom: 40%;" />

新建Spring Boot应用，增加一个HelloController，编写一个hello方法，返回请求的端口和IP；

```java
@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
```

![image-20231005093003008](spring boot3.assets/image-20231005093003008.png)



1. 打包Spring Boot应用；

```go
mvn clearn package
```

1. 上传文件到Linux服务器/usr/local/docker-compose-demo的目录；
2. 在/usr/local/docker-compose-demo的目录编辑Dockerfile；

```dockerfile
#指定基础镜像
FROM java:8
LABEL name="docker-compose-demo" version="1.0" author="wtz"
COPY ./getway-1.0-SNAPSHOT.jar ./docker-compose-demo.jar
#启动参数
CMD ["java","-jar","docker-compose-demo.jar"]
```

1. 编辑docker-compose.yml文件；

```yaml
version: '3.8'
services:
  nginx:
    restart: always
    image: daocloud.io/library/nginx:latest
    container_name: nginx
    ports:
      - 3389:80

  docker-compose-demo01:
    build:
      # 构建的地址
      context: /root/docker-compose-demo
      dockerfile: Dockerfile
    image: docker-compose-demo
    container_name: docker-compose-demo01
    # 选择端口
    ports:
      - 8081:8080/tcp
    restart: always

  docker-compose-demo02:
    build:
      # 构建的地址
      context: /root/docker-compose-demo
      dockerfile: Dockerfile
    image: docker-compose-demo
    container_name: docker-compose-demo02
    # 选择端口
    ports:
      - 8082:8080/tcp
    restart: always
```

1. 编写nginx.conf，实现负载均衡到每个应用，这里通过容器名称访问，因此不需要管每个容器的ip是多少，这个也是自定义网络的好处；

```bash
user nginx;
worker_processes  1;
events {
    worker_connections  1024;
}
http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;

server {
    listen 80;
    location / {
     proxy_pass http://docker-compose-demo;
     proxy_set_header  Host $host;
	     proxy_set_header  X-real-ip $remote_addr;
	     proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

upstream docker-compose-demo{
   server docker-compose-demo01:8080;
   server docker-compose-demo02:8080;
}
include /etc/nginx/conf.d/*.conf;


server {
    listen 80;
    location / {
     proxy_pass http://docker-compose-demo;
     proxy_set_header  Host $host;
	     proxy_set_header  X-real-ip $remote_addr;
	     proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

upstream docker-compose-demo{
   server docker-compose-demo01:8080;
   server docker-compose-demo02:8080;
}
include /etc/nginx/conf.d/*.conf;
}
```

1. 查看/usr/local/docker-compose-demo目录，有以下确保有以下四个文件；

![image.png](spring boot3.assets/1627196848329-0c34fa5b-93c2-42d8-aa3d-8e0940a070d1-169646749658823.png)

1. 检查docker-compose.yml的语法是否正确，如果不发生报错，说明语法没有发生错误;

```lua
docker-compose config
```

1. 启动docker-compose.yml定义的服务；

```x86asm
docker-compose up
```

![image.png](spring boot3.assets/1627204068667-c269e04b-5176-4aaa-bf7b-ee6db6e597d2-169646749658825.png)

1. 验证服务是否正确；

```csharp
#查看宿主机ip
ip add

#访问对应的服务
curl http://172.21.122.231/hello
```

![img](spring boot3.assets/1627204247825-43503cbc-884d-4e02-ab78-3ba1b661628e-169646749658826.png)



## 十一、Docker CI、CD

### 11.1 CI、CD引言

项目部署

- 将项目通过maven进行编译打包
- 将文件上传到指定的服务器中
- 将war包放到tomcat的目录中
- 通过Dockerfile将Tomcat和war包转成一个镜像，由DockerCompose去运行容器

项目更新后，需要将上述流程再次的从头到尾的执行一次，如果每次更新一次都执行一次上述操作，很费时，费力。我们就可以通过CI、CD帮助我们实现持续集成，持续交付和部署。

### 11.2 CI介绍

CI（continuous intergration）持续集成

持续集成：编写代码时，完成了一个功能后，立即提交代码到Git仓库中，将项目重新的构建并且测试。

- 快速发现错误。
- 防止代码偏离主分支。

####  搭建Gitlab服务器

实现CI，需要使用到Gitlab远程仓库，先通过Docker搭建Gitlab,安装Docker以及Docker-Compose

##### 1. 修改ssh的22端口

将ssh的默认22端口，修改为60022端口，因为Gitlab需要占用22端口

```shell
vi /etc/ssh/sshd_config
  PORT 22 -> 60022
systemctl restart sshd
```

##### 2. 编写docker-compose.yml

使用 [twang2218/gitlab-ce-zh: GitLab Community Edition (中文社区版) (github.com)](https://github.com/twang2218/gitlab-ce-zh)

docker-compose.yml文件去安装gitlab（下载和运行的时间比较长的）

```yml
version: '3.1'
services:
 gitlab:
  image: 'twang2218/gitlab-ce-zh:11.1.4'
  container_name: "gitlab"
  restart: always
  privileged: true
  hostname: 'gitlab'
  environment:
   TZ: 'Asia/Shanghai'
   GITLAB_OMNIBUS_CONFIG: |
    external_url 'http://192.168.199.110'
    gitlab_rails['time_zone'] = 'Asia/Shanghai'
    gitlab_rails['smtp_enable'] = true
    gitlab_rails['gitlab_shell_ssh_port'] = 22
  ports:
   - '80:80'
   - '443:443'
   - '22:22'
  volumes:
   - /opt/docker_gitlab/config:/etc/gitlab
   - /opt/docker_gitlab/data:/var/opt/gitlab
   - /opt/docker_gitlab/logs:/var/log/gitlab
```

```shell
docker compose -f docker-compose.yml up -d
```

##### 3. 搭建GitlabRunner

查看资料中的gitlab-runner文件即可安装

```
docker pull bitnami/gitlab-runner
```

<img src="spring boot3.assets/image-20231004175849067.png" alt="image-20231004175849067" style="zoom: 50%;" />

#### 整合项目测试

##### 创建项目

> 创建maven工程，添加web.xml文件，编写html页面

#####  编写.gitlab-ci.yml

> 编写[.gitlab-ci.yml](https://blog.csdn.net/yinying293/article/details/130836507)文件

```
stages:
  - test

test:
  stage: test
  script:
    - echo first test ci   # 输入的命令
```

#####  将maven工程推送到gitlab中

> 执行git命令推送到Gitlab

```sh
git push origin master
```

##### 查看效果

> 可以在gitlab中查看到gitlab-ci.yml编写的内容

![image.png](spring boot3.assets/1646184608235-6f2031a7-4336-4c47-a8a1-d43af481b661-169646749658827.png)

####  完善项目配置

> 添加Dockerfile以及docker-compose.yml， 并修改[.gitlab-ci.yml](https://blog.csdn.net/yinying293/article/details/130836507)文件

##### 创建Dockerfile

```
# Dockerfile
FROM daocloud.io/library/tomcat:8.5.15-jre8
COPY testci.war /usr/local/tomcat/webapps
```

##### 创建docker-compose.yml

```yml
# docker-compose.yml
version: "3.1"
services:
  testci:
    build: docker
    restart: always
    container_name: testci
    ports:
      - 8080:8080
```

##### 修改.gitlab-ci.yml

```
# ci.yml
stages:
  - test

test:
  stage: test
  script:
    - echo first test ci
    - /usr/local/maven/apache-maven-3.6.3/bin/mvn package
    - cp target/testci-1.0-SNAPSHOT.war docker/testci.war
    - docker-compose down
    - docker-compose up -d --build
    - docker rmi $(docker images -qf dangling=true)
```



| 测试效果                                                     |
| ------------------------------------------------------------ |
| ![image-20231005090053991](spring boot3.assets/image-20231005090053991.png) |



### 11.3 CD介绍

CD（持续交付，持续部署）

持续交付：将代码交付给专业的测试团队去测试

持续部署：可以直接将指定好tag的代码直接部署到生产环境中

| CICD图                                                       |
| ------------------------------------------------------------ |
| ![image-20231005090106154](spring boot3.assets/image-20231005090106154.png) |



####  安装Jenkins

##### 编写docker-compose.yml

官网：https://www.jenkins.io/

```yaml
version: "3.1"
services:
  jenkins:
   image: jenkins/jenkins
   restart: always
   container_name: jenkins
   ports:
     - 8888:8080
     - 50000:50000
   volumes:
     - ./data:/var/jenkins_home
```

#####  运行并访问Jenkins

第一次运行时，会因为data目录没有权限，导致启动失败

```plain
chmod 777 data
```

访问http://192.168.199.109:8888

访问成功后，需要输入密码，可在日志中查看

![img](spring boot3.assets/1646184745091-216797aa-dde9-447a-b57e-89ea5c366fa9-169646749658928.png)

手动指定插件安装：指定下面两个插件即可

 publish ssh.

 git param.

![img](spring boot3.assets/1646184756656-6634e5f5-fbe7-417c-b5aa-6dc1fdfbccfe-169646749658930.png)

安装成功后，需要指定上用户名和密码，登陆成功

![](spring boot3.assets/1646184767170-a7aaaf6c-0a2c-4609-b7d7-bad1deed5c57-169646749658929.png) 

####  配置Jenkins的目标服务器

执行过程为代码提交到Gitlab，Jenkins会从Gitlab中拉取代码，并在Jenkins中打包并发布到目标服务器中。

###### 点击左侧的系统设置

| 左侧导航                                                     |
| ------------------------------------------------------------ |
| ![image-20231005090142354](spring boot3.assets/image-20231005090142354.png) |

###### 选中中间区域的系统设置

| 系统设置                                                     |
| ------------------------------------------------------------ |
| ![image-20231005090236097](spring boot3.assets/image-20231005090236097.png) |

###### 搜索Publish over SSH

| Publish over SSH                                             |
| ------------------------------------------------------------ |
| ![image-20231005090214144](spring boot3.assets/image-20231005090214144.png) |



###### 点击上图新增

| 新增SSH连接                                                  |
| ------------------------------------------------------------ |
| ![image-20231005090226653](spring boot3.assets/image-20231005090226653.png) |



#### 配置GitLab免密码登录

链接Gitlab需要使用密码，我们可以通过SSH的方式，免密码登陆Gitlab拉取代码，避免每次都输入密码。

###### 登录Jenkins容器内部

```plain
docker exec -it jenkins bash
```

###### 输入生成SSH秘钥命令

```plain
ssh-keygen -t rsa -C "邮箱（随便写）"
```

######  将秘钥复制到GitLab的SSH中

| 配置密钥                                                     |
| ------------------------------------------------------------ |
| ![image-20231005090252856](spring boot3.assets/image-20231005090252856.png) |



#####  配置JDK和Maven

我们需要再Jenkins中将代码打包，需要依赖JDK和Maven的环境

######  复制软件到data目录下

| 效果                                                         |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184855683-d8f57fe3-99e2-4ee6-abd7-9109f0b15a0a-169646780456831.png) |

######  在监控界面中配置JDK和Maven

| 配置环境变量                                                 |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184866377-c50d5332-7bd2-4483-bd68-06b05b33c327-169646781651233.png) |



###### 手动拉取gitlab项目



使用SSH无密码连接时，第一次连接需要手动确定

| 手动拉取一次                                                 |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184879585-ffd1b81e-2a8a-40c3-b162-b424cc7eba01-169646784465035.png) |



####  创建maven任务

实现通过Jenkins的Maven任务，自动去Gitlab拉取代码，并在本地打包，发布到目标服务器上

###### 创建maven工程，推送到GitLab中

随便创建一个即可……



###### Jenkins的监控页面中创建maven任务

| 指定GitLab地址                                               |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184896375-d49dcde2-3fa8-45fd-94ea-19ec7f8a94fc-169646786774637.png) |

| 指定maven打包方式                                            |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184908924-d3e235d7-eaeb-4ebf-bc70-d7a862207ff7-169646786774738.png) |



###### 执行maven任务

| 立即构建，并查看日志                                         |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184919906-c4103208-ed72-438a-9322-eba1c9b388d2-169646788763041.png) |

| 控制台查看日志信息                                           |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184932397-8eccc28f-c50b-4386-ae3f-11c0da1ee201-169646788763042.png) |



| 打包成功                                                     |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646184966948-4c79a235-f337-4485-a055-c56a46a7a69d-169646790286745.png) |



####  实现持续交付持续部署



实现根据tag标签，实现持续交付和持续部署



###### 安装Persistent Parameter的插件

| 安装插件                                                     |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184983061-a74b464d-97d1-4e19-9b45-c727cac215cc-169646791663547.png) |



###### 重新指定构建项目的方式

| 根据标签构建项目                                             |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646184997560-97494ebd-d433-49a8-88f4-99be03ec13b7-169646793616649.png) |

| 自定义构建                                                   |
| ------------------------------------------------------------ |
| ![img](spring boot3.assets/1646185008509-85f9819d-45bd-4898-a746-1ac11f03bc42-169646793616750.png) |



###### 构建项目成功后，需要将内容发布到目标服务器

| 发布服务器后执行的命令                                       |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646185018307-56c7ae98-b7f3-4b0d-a996-87e950d68631-169646794788353.png) |



###### 添加程序代码

```yaml
# Dockerfile 文件
FROM daocloud.io/library/tomcat:8.5.15-jre8
COPY testcd-1.0-SNAPSHOT.war /usr/local/tomcat/webapps

# docker-compose.yml文件
version: "3.1"
services:
  testcd:
    build: docker
    restart: always
    container_name: testcd
    ports:
      - 8081:8080
```

######  测试

| 根据标签修改发布版本                                         |
| ------------------------------------------------------------ |
| ![image.png](spring boot3.assets/1646185039467-fd65c88a-f680-497e-8c59-708fddc1331e-169646795713655.png) |

# 架构模块

**ElasticSearch分布式搜索框架**

**ElasticSearch分布式搜索框架**

**SpringSession分布式会话框架**



# Maven 和 Gradle

## Maven

### Maven 的两个经典作用

#### 一.   依赖管理 也就是管理jar包

Maven 的一个核心特性就是依赖管理。

当涉及到多模块的项目（包含成百个模块或者子项目），管理依赖就变成一项困难的任务。这时用Maven 可以对处理这种情形的高度控制。

在传统的 WEB 项目中，我们必须将工程所依赖的 jar 包复制到工程中，导致了工程的变得很大。比如

idea常见普通Maven项目目录：
                   <img src="spring boot3.assets/20201204225021484.png#pic_center" alt="Alt" style="zoom: 80%;" />

**maven工程中不直接将 jar包导入到工程中，而是通过在 pom.xml** **文件中添加所需jar包的坐标**。

到仓库中找到 jar 包不会影响程序性能：
maven 中也有索引的概念，通过建立索引，可以大大提高加载 jar 包的速度，使得我们认为 jar 包基本跟放在本地的工程文件中再读取出来的速度是一样的。**Maven中仓库分为三类：**

​                                  ![](https://th.bing.com/th/id/R.e0c7d085e393ba437bf61aadde678c47?rik=cSX4nWv0RmzLTg&riu=http%3a%2f%2fcdn.imodou.com.cn%2fwp-content%2fuploads%2f2021%2f04%2fimage-1617589018144.png&ehk=b1UJfJY%2bH8tSnPJ9vnUkKXGbZgvNV8WKJZiGEMvV8%2fs%3d&risl=&pid=ImgRaw&r=0)         



+ **本地仓库：**存放在本地服务器中，当运行项目的时候，maven会自动根据配置文件查找本地仓库，再从本地仓库中调用jar包使用。

+ **远程仓库**
  当本地仓库中没有项目所需要的jar包时，那么maven会继续查找远程仓库，一般远程仓库指的是公司搭建的私有服务器，也叫私服；
  当jar包在私服中查找到之后，maven会将jar包下载到本地仓库中，下次使用的时候就不用再去找远程仓库。
  **中央仓库：**
  当远程仓库获取不到jar包时，就需要到中央仓库去查找，并下载在远程仓库中，本地仓库再从远程仓库中下载回来使用。

#### 二. 一键构建

**Maven 规范化构建流程如下：**

​           ![在这里插入图片描述](spring boot3.assets/2020120423190572.png#pic_center)

### Maven坐标

Maven通过坐标对jar包进行唯一标识。坐标通过3个元素进行定义，groupId、artifactId、version。

1. groupId：组织标识，一般为域名倒置。
2. artifactId：项目名或模块名，是当前项目中的唯一标识。
3. version：当前项目版本。

### Spring Boot应用 打包与部署

#### 打包

> 打包工具使用的是Maven

首先需要在pom.xml文件build节点增加如下配置：

```
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
        <!--打包-->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

另外，需要确认pom.xml文件的package节点配置：

```
<!--打成 jar 包,一般为默认-->
<packaging>jar</packaging>
<!--打成 war 包-->
<packaging>war</packaging>
```

在项目的根目录下，打开命令行工具，并执行以下命令：

```
mvn clean package
```

![image-20231005093824942](file://C:/Users/16658/Documents/GitHub/java_note/note/spring%20boot3/spring%20boot3.assets/image-20231005093824942.png?lastModify=1696340279)

使用Maven构建工具编译和打包应用程序。打包完成后，会在`target`目录下找到一个名为`<项目名>.jar`的JAR文件。

![image-20231005093753847](file://C:/Users/16658/Documents/GitHub/java_note/note/spring%20boot3/spring%20boot3.assets/image-20231005093753847.png?lastModify=1696340279)

测试

本地运行测试cmd 到控制台，通过 java 命令运行 jar 包

```
java -jar springbootAPP.jar
```

#### 部署

有多种方式可以部署Spring Boot应用程序，包括将其部署到独立的[服务器](https://so.csdn.net/so/search?q=服务器&spm=1001.2101.3001.7020)、容器化平台（如Docker）或云平台（如AWS、Azure等）。下面将介绍其中的一种简单的部署方式。

- 将JAR文件上传到服务器： 将打包好的JAR文件上传到目标服务器，可以使用FTP、SCP或其他文件传输工具。

- 运行应用程序： 在服务器上打开命令行工具，并导航到JAR文件所在的目录。然后执行以下命令来运行应用程序：

  ```
  java -jar <项目名>.jar
  ```

  这将启动Spring Boot应用程序。

- 配置应用程序： 默认情况下，Spring Boot应用程序将在8080端口上监听HTTP请求。



## Gradle

如果你经常使用Maven，可能会发现Maven有一些地方用的让人不太舒服：

+ Maven的配置文件是XML格式的，假如你的项目依赖的包比较多，那么XML文件就会变得非常非常长；

+ XML文件不太灵活，假如你需要在构建过程中添加一些自定义逻辑，搞起来非常麻烦；

+ Maven非常的稳定，但是相对的就是对新版java支持不足，哪怕就是为了编译java11，也需要更新内置的Maven插件；

如果你对Maven的这些缺点也有所感触，尝试gradle构建工具，这是一个全新的java构建工具，解决了Maven的一些痛点。

[Gradle使用教程 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/440595132)

[gradle使用教程，一篇就够 - 简书 (jianshu.com)]()







