# 项目结构

![image-20230709103935383](spring boot3.assets/image-20230709103935383.png)

**注意:  可能会写一个mapper包  里面写数据库操作的接口, 然后在生成Mapper接口的xml文件写sql语句**

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
| @Component           | bean注册, 和 Service 没有啥区别,  Component是比较通用的东西  |
| @Autowired           | //@Resource =@Autowired+@Qualifie<br> //自动加载类, 理解就是不用new<br/>//如果接口实现只有一个，那么用@Autowired就可以了，也不需要指定名字.<br/>//如果接口有多个实现,那么用@Resource，并指定name<br/>//或者使用@Autowired+@Qualifier+Qualifier的value值. |
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

![img](spring boot3.assets/868422e97ac64147951736389c4eeabf.png)

```java
一、查询需求

1、名字中包含雨并且年龄小于40

       name like '%雨%' and age<40

2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空

   name like '%雨%' and age between 20 and 40 and email is not null

3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列

   name like '王%' or age>=25 order by age desc,id asc

4、创建日期为2019年2月14日并且直属上级为名字为王姓

      date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')

5、名字为王姓并且（年龄小于40或邮箱不为空）

    name like '王%' and (age<40 or email is not null)

6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）

    name like '王%' or (age<40 and age>20 and email is not null)

7、（年龄小于40或邮箱不为空）并且名字为王姓

    (age<40 or email is not null) and name like '王%'

8、年龄为30、31、34、35

    age in (30、31、34、35) 

9、只返回满足条件的其中一条语句即可

limit 1

二、select中字段不全部出现的查询

10、名字中包含雨并且年龄小于40(需求1加强版)

第一种情况：select id,name

                  from user

                  where name like '%雨%' and age<40

第二种情况：select id,name,age,email

                  from user

                  where name like '%雨%' and age<40

三、统计查询：

11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。

并且只取年龄总和小于500的组。

select avg(age) avg_age,min(age) min_age,max(age) max_age

from user

group by manager_id

having sum(age) <500

```



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

# Spring-Boot-操作-Redis

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





# 反序列化(非跨平台)

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









