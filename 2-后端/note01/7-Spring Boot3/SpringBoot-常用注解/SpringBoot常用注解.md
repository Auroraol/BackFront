# SpringBoot常用注解

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

@Controller            返回: 视图页面 

@Controller + @ResponseBody   返回:  json/xml/类对象数据(不推荐)

@RestController     返回:  json/xml/类等数据

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
| @RequestBody  | 传递对象,  比如: 前端发送json封装的对象给后端，后端使用java对象来接收(传递对象)反之也可以用它 |

| 注解                 | 备注                                                         |
| -------------------- | ------------------------------------------------------------ |
| @Service             | bean注册, ==一般加在service目录里面的实现类上==              |
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

![image-20230922223722214](SpringBoot常用注解.assets/image-20230922223722214.png)

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
| @Service("?")        | bean注册, ==一般加在service目录里面的类上==                  |
| @Component("?")      | bean注册,  和 Service 没有啥区别,  Component是比较通用的东西 |
| @Autowired           | ①自动加载类(个人理解就是不用new)      ②或者使用@Autowired+@Qualifier![image-20231008233349225](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231008233349225.png) |
| @Resource(name="?")  | ①如果该接口有多个实现,    用@Resource，并指定name <br>②@AutoWired只适合spring框架，而@Resource扩展性更好 |
| @Configuration+@Bean | bean注册(人为) —> 用于配置类   // bean下是注入的对象         |
| @Values              | 从配置文件中取参数                                           |
| @Repository("?")     | 用于将 DAO 层 (spring应用)                                   |

注: "?"可选

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

在Spring框架中，@Service注解用于标识一个类为服务层组件，表示该类提供业务逻辑的处理。当使用@Service注解标注一个类时，Spring会自动将该类实例化并将其作为一个Bean进行管理。

然而，即使使用了@Service注解标注了一个类，我们仍然需要使用@Autowired注解来进行依赖注入，以便在其他类中使用该服务。这是因为@Autowired注解告诉Spring容器在需要该服务的地方自动注入对应的实例。

简而言之，@Service注解用于标识一个类为服务层组件，让Spring容器实例化并管理该类的实例。而@Autowired注解用于在其他类中自动注入对应的服务实例，以便使用该服务的功能。

### 2.2  @Resource



### 2.3  @Component, @Repository, @Service, @Controller

我们一般使用 @Autowired 注解让 Spring 容器帮我们自动装配 bean。要想把类标识成可用于 @Autowired 注解自动装配的 bean 的类，可以采用以下注解实现：

●@Component ：通用的注解，可标注任意类为 Spring 组件。如果一个 Bean 不知道属于哪个层，可以使用@Component 注解标注；

●@Repository : 对应持久层即 Dao 层，主要用于数据库相关操作；

●@Service : 对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao 层；

●@Controller : 对应 Spring MVC 控制层，主要用于接受用户请求并调用 Service 层返回数据给前端页面。

### 2.4  @RestController

@RestController注解是@Controller和@ResponseBody的合集，表示这是个控制器 bean，并且是将函数的返回值直接填入 HTTP 响应体中，是 REST 风格的控制器。

现在都是前后端分离，说实话我已经很久没有用过@Controller。如果你的项目太老了的话，就当我没说。

单独使用 @Controller 不加 @ResponseBody的话一般使用在要返回一个视图的情况，这种情况属于比较传统的 Spring MVC 的应用，对应于前后端不分离的情况。@Controller+@ResponseBody 返回 JSON 或 XML 形式数据

### 2.5  @Scope

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

### 2.6  @Configuration

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

### 2.7  @Bean

[总结](./@Bean注解.md)

### 2.8 使用ApplicationContextAware

- `@Autowire` 适用于大多数场景，当无法满足装配条件时，系统启动将报异常。适用于那些我们自己管理的Bean。

-  `applicationContext.getBean(name);` 适用于手动获取。适用那些最终使用者可能不是我们自己的情况

  ApplicationContextAware 是一个接口，它提供一个方法 setApplicationContext ，当 spring 注册完成之后，会把 ApplicationContext 对象以参数的方式传递到方法里，在方法里我们可以实现自己的逻辑，去获取自己的 bean，当前对接的断言等；

  一般用在被封装的工具包， starter 包中，方便给其它开发人员调用

`applicationContext`场景如下

**1.做一个公共jar类发布 提供给其他项目组使用的场景**

**2.项目工程内，通用工具类utils，比如一些时间类，调用类等**

既然是工具类，那么使用方法肯定是 类.方法名直接使用，也就是需要[静态方法](https://so.csdn.net/so/search?q=静态方法&spm=1001.2101.3001.7020)，此时如果需要注入一些service类，就不能用注解了。但是一般就是类.方法名直接使用

@Autowired只能在调用[自动装配](https://so.csdn.net/so/search?q=自动装配&spm=1001.2101.3001.7020)对象的对象的方法不是静态方法时使用，因为静态方法能在对象没[实例化](https://so.csdn.net/so/search?q=实例化&spm=1001.2101.3001.7020)时调用，但此时自动装配对象还未装配。

ApplicationContext().getBean()则所用情况都可以使用，尤其在静态方法中，只能用这种方法

例子

```java
public abstract class BaseSmsCodeServiceImpl implements SmsCodeService{
        
	@Autowired
	private StringRedisTemplate redisTemplate;
}
```

等价

```java
public abstract class BaseSmsCodeServiceImpl implements SmsCodeService,
	InitializingBean, ApplicationContextAware 
    
    private ApplicationContext applicationContext; //Spring的ApplicationContext的持有者,可以获取spring容器中的bean

    private StringRedisTemplate redisTemplate;
        
//从应用程序上下文中获取 StringRedisTemplate 类型的 Bean，并进行必要的校验，确保该 Bean 的正确性和可用性
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (this.applicationContext == null) {
			this.applicationContext = applicationContext;
		}
	}

// 在读取配置文件后
	@Override
	public void afterPropertiesSet() {
		if (this.redisTemplate == null) {
			this.redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
		}
		Assert.notNull(this.redisTemplate, "There is no one available StringRedisTemplate bean");
	}
}
```

implements  ApplicationContextAware 了也可以使用getBean()去拿到Bean相关的东西

```java
applicationContext.getBean(rateLimiter.extra())
```

## 3、处理常见的 HTTP 请求类型

| 注解            | 说明                        |
| --------------- | --------------------------- |
| @RequestMapping | url统一前缀或者表示get,post |
| @GetMapping     | 前端显示数据                |
| @PutMapping     | 后端接受数据                |
| @DeleteMapping  | 删除数据                    |

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

## 4、传值

<strong style="color:red">注意: 一些注解是前后端发分离项目使用</strong>

### axios中get/post请求方式

![image-20240322212114235](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240322212114235.png)

####  get请求

**params**

![image-20240326225858933](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240326225858933.png)

例子

```js
// method
const params = {
    id: '123456789',
    name: '张三'
}
test(params)

// api
export function test (params) {
  return axios({
    url: url,
    method: 'GET',
    params: params
  })
}

// 后台
@GetMapping("/test")
public Result test(@RequestParam Map<String, Object> map) {
    return Res.ok();
}
```

####  post请求

 **params**

![image-20240326225836095](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240326225836095.png)

例子

```js
// method
const params = {
    id: '123456789',
    name: '张三'
}
test(params)

// api
export function test (params) {
  return axios({
    url: url,
    method: 'POST',
    params: params
  })
}

// 后台
@PostMapping("/test")
public Result test(@RequestParam Map<String, Object> map) {
    return Res.ok();
}

```

**data**

![image-20240326225819562](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240326225819562.png)

例子

```js
// 实体类
@Data
public class TestEntity {
    Long id;
    String name;
}

// method
const params = {
    id: '123456789',
    name: '张三'
}
test(params)

// api
export function test (params) {
  return axios({
    url: url,
    method: 'POST',	
    data: params
  })
}

@PostMapping("/test")
public Result test(@RequestBody TestEntity testEntity) {
    return Res.ok();
}

```

### 前端传值

| 注解          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| @RequestParam | 动态参数(RequestParam  带?)                                  |
| @PathVariable | 动态参数 (PathVariable 不带?)                                |
| @RequestBody  | 传递对象,  比如: 前端发送json封装的对象给后端，后端使用java对象来接收(传递对象)反之也可以用它 |

#### 4.1 @PathVariable("xxx")     /

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

#### 4.2 @RequestParam("xxx")   ?

RequestParam  带?  可以设置默认值, 不传值

例子 http://localhost:8090/param?age=参数?name=参数

```java
@GetMapping("/param")
public String hello(@RequestParam(value = "age",   defaultValue = "0") int age,      @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    //required 表示可以不传值
    ....
}
```

##### 前端接口

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

![image-20230925145552245](SpringBoot常用注解.assets/image-20230925145552245.png)

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

<img src="SpringBoot常用注解.assets/image-20230925143036839.png" alt="image-20230925143036839" style="zoom:67%;" />

##### 后端接口

注:  两个参数的时候可以这样@RequestParam Map<String, Object> map

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

![image-20230925142927286](SpringBoot常用注解.assets/image-20230925142927286.png)



#### 4.3 @RequestBody

用于读取 Request 请求（可能是 POST,PUT,DELETE,GET 请求）的 body 部分并且Content-Type 为 application/json 格式的数据，接收到数据之后会自动将数据绑定到 Java 对象上去。系统会使用HttpMessageConverter或者自定义的HttpMessageConverter将请求的 body 中的 json 字符串转换为 java 对象。

<font color=red>@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)，所以只能发送POST请求。[@RequestBody只能和@PostMapping使用]</font>

注意:   使用默认submit提交 的HTML表单数据，默认使用application/x-www-form-urlencoded或multipart/form-data作为Content-Type。是不能使用这个的RequestBody, 使用 

axios方式发起的ajax请求, 在post方式下, 提交的数据自动转成json, 不需要手动转换, 其他方式需要

```java
	@PostMapping("/user/login")
    @ResponseBody //返回响应数据
	public Result<Map<String, Object>> login(@RequestBody XUser user) {
		// 登录操作
		Map<String, Object> data = xUserService.login(user);
		if (data != null)
			return Result.success(data);
		return Result.fail(20000, "用户密码错误");
	}
```

##### 三种形式

+ json
+ 对象

###### json —— 对象

> 前端传入对象

 **前端接口**

```js
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data  //必须是json数据
  })
}
```

 **后端接口**

controller

```java
@PostMapping("/login")
public Result<Map<String,Object>> login(@RequestBody User user){  //[核心]
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

![image-20230923094816855](SpringBoot常用注解.assets/image-20230923094816855.png)

###### json —— 两个变量

> 前端发送json数据, 仅限两个

**前端**

```
{
"username": "lfj",
"password": "123456"
}
```

**后端**

```java
@PostMapping("/login")
public ResponseResult<Token> userLogin(@RequestBody Map<String, String> loginData) {  //[核心]
		String username = loginData.get("username");
		String password = loginData.get("password");
		return userService.usernameLogin(username, password);
}
```

###### 表单默认方式

表单提交的默认方式(`<input type="submit" value="提交"/>`)

```html
<form action="${pageContext.request.contextPath}/orderInfo"  <!--请求url-->
      method="post">
    <table border="1">
        <tr>
            <td colspan="2">
                订单id:<input type="text" name="orderId" value="1">
            </td>
        </tr>
        <tr>
            <td>商品Id</td>
            <td>商品名称</td>
        </tr>
        <tr>
            <td>
                <input name="productInfo.proId" value="1"  <!--属性-->
                       type="text">
            </td>
            <td>
                <input name="productInfo.proName"
                       value="三文鱼" type="text">
            </td>
        </tr>
        <tr>
            <td>
                <input name="productInfo.proId" value="2"
                       type="text">
            </td>
            <td>
                <input name="productInfo.proName" value="红牛"
                       type="text">
            </td>
        </tr>
    </table>
	<!--表单默认方式-->
    <input type="submit" value="提交"/>
	<!--axios方式发起的ajax请求-->
	<button onclick="submitOrder()">提交</button>
</form>
```

Controller层只能使用@RequestParam

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/orderInfo")
public class OrderController {

    @PostMapping
    public String handleOrderInfo(@RequestParam String orderId,
                                  @RequestParam HashMap<String, String> productInfo) {

        // Create Order object
        Order order = new Order();
        order.setOrderId(orderId);

        // Create Product objects and add them to the order
        HashMap<String, Product> products = new HashMap<>();
        for (String key : productInfo.keySet()) {
            Product product = new Product();
            product.setProId(productInfo.get(key));
            product.setProName(productInfo.get("productInfo.proName"));
            products.put(key, product);
        }

        order.setProductInfo(products);

        // You can now process the order object as needed (e.g., save to a database)

        // Redirect to a success page or return a response
        return "redirect:/successPage";
    }
}
```

### 后端传值

| 方法          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| 转发          | url地址栏不会产生变化                                        |
| 重定向        | url地址栏会发生变化                                          |
| @ResponseBody | 一般在@Controller中使用: @ResponseBody+@Controller = @RestController,   后端发送java对象 ==记忆: 返回对象,用于指示方法返回的对象会被自动转换为JSON格式的数据== |

一般情况下是该类全是返回给前端数据的就直接使用RestController,  可以就不用写ResponseBody了, 但是写了RestController该类总就不能返回视图页面了, 比如: 

```java
@RestController
public class FieldController {

	@RequestMapping("/toLogin")
	public String toLogin(Model model){
		model.addAttribute("user",new User());
		return "loginPage";  // 不会跳转到loginPage  
	}
}
```

运行结果

![image-20231227205724351](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231227205724351.png)

### 总结

> SpringMVC和SpringBoot接收参数的方式是一样一样的。

#### 一、传非json参数

***下面代码是get、post请求都支持，但是把参数放到路径上，一半这种情况下都用get请求。***

**涉及到的注解：**

- @[RequestParam](https://so.csdn.net/so/search?q=RequestParam&spm=1001.2101.3001.7020)主要用于在SpringMVC后台控制层获取参数，它有三个常用参数：defaultValue = “0”, required = false, value = “xxx”；defaultValue 表示设置默认值，required 表示该参数是否必传，value 值表示接受的传入的参数的key。
- @[PathVariable](https://so.csdn.net/so/search?q=PathVariable&spm=1001.2101.3001.7020)用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数。

```java
	/**
	 * PathVariable(value = "参数名", required = 默认值)  注:没有defaultValue
	 * http://localhost:8080/test2/1234/john/true/2023-01-01 12:00:00
	 * param: id name gender birth
	 * get post都可以, body传参一般都用get
	 **/
	@RequestMapping("/test2/{id}/{name}/{gender}/{birth}")    //PathVariable顾名思义需要固定url路径
	public String testParam2(@PathVariable("id") Integer id,
							 @PathVariable(value = "name") String name,
							 @PathVariable(value = "gender", required = false) Boolean gender,
							 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
							 @PathVariable(value = "birth",  required = false) Date birth){
		//为空时指定默认值
		if (birth == null) {
			birth = new Date("2023-12-12 12:20:30");
		}
		System.out.println("test param2");
		System.out.println(id + "--" + name+ "--" + gender+ "--" + birth);
		return "index";
	}
```

![image-20231017232927928](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231017232927928.png)

![image-20231017232910914](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231017232910914.png)

----

```java
	/**
	* http://localhost:8989/xxx/../test1?id=1&name=zzz&gender=false&birth=2018-12-12 12:20:30
	 * param: id name gender birth
	 * get post都可以, body传参一般都用get
	 **/
	@RequestMapping("/test1")
	public String testParam1(@RequestParam("id") Integer id,
							 @RequestParam(value = "name", required = false, defaultValue = "李刚") String name,
							 @RequestParam(value = "gender", required = false) Boolean gender,
							 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
								 @RequestParam(value = "birth", defaultValue = "2023-12-12 12:20:30") Date birth){
		System.out.println("test param1");
		System.out.println(id + "--" + name+ "--" + gender+ "--" + birth);
		return "index";
	}
```

![ee7e4784bc05ac129d2fe726f031029](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/ee7e4784bc05ac129d2fe726f031029.png)

<img src="SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231017225634040.png" alt="image-20231017225634040" style="zoom:80%;" />

![85f31c8b622a32867c261d32b654b5f](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/85f31c8b622a32867c261d32b654b5f.png)

![image-20231017225653260](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231017225653260.png)

#### 二、传json参数

***1、单个实体接收参数***

```javascript
    /**
     * http://localhost/toUser/add6
     * body: {"username":"张刚","password":"123456"}
     * get post都可以
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add6")
    public void add6(@RequestBody User user) {
        log.info("打印参数:{}--{}", user.getUserName(), user.getPassword());
    }
```

***2、实体嵌套实体接收参数***

注解：

+ @RequestBody：该注解会把接收到的参数转为json格式

实体

```java
@Data
public class People {
    private Integer count;
    private String peoplename;
    private Student student;
}
```

```java
@Data
public class Student {
    private String studentname;
}
```

```javascript
	/**
	 * http://localhost:8080/test3
	 * body: {"peoplename":"张刚","student":{"studentname":"你好"}}
	 * get post都可以, body传参一般都用post
	 * @param
	 */
	@RequestMapping("/test3")
	@ResponseBody
	public People testParam3(@RequestBody People people) {
		System.out.println("打印参数:" + people.getPeoplename() + people.getStudent().getStudentname());
		return people;
	}
```

![image-20231017231530119](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20231017231530119.png)

```
打印参数:张刚你好
```

***3、实体嵌套List接收参数***

实体

```java
@Data
@ToString
public class People {
    private Integer count;
    private String peoplename;
    private List<Student> student;
}
```

```java
@Data
@ToString
public class Student {
    private String studentname;
}
```

```javascript
    /**
     * http://localhost:8080/add8
     * body: {"peoplename":"张刚","student":[{"studentname":"你好"},{"studentname":"很好"}]}
     * get post都可以, body传参一般都用post
     * @param
     */
    @RequestMapping(value = "/add8")
    public void add8(@RequestBody People people) {
        List<Student> student = people.getStudent();
        student.stream().forEach(System.out::println);
        log.info("打印参数:{}--{}", people.getPeoplename());
    }
```

***4、Map接收参数***

```javascript
  /**
     * http://localhost:80/add9
     * body: {"peoplename":"张刚","student":[{"studentname":"你好"},{"studentname":"很好"}]}
     * get post都可以, body传参一般都用post
     *
     * @param
     */
    @RequestMapping(value = "/add9")
    public void add7(@RequestBody Map<String, Object> map) {
        log.info(map.get("peoplename").toString());
        List<Map<String, Object>> studentMapList = (List<Map<String, Object>>) map.get("student");
        studentMapList.stream().forEach(System.out::println);
    }
```

#### 三、 综合代码[总结]:crossed_swords:

##### json数据

前端

```js
document.querySelector('#addBtn').addEventListener('click', function () {
  const bookname = document.querySelector('#addForm [name=bookname]').value
  const author = document.querySelector('#addForm [name=author]').value
  const publisher = document.querySelector('#addForm [name=publisher]').value

  const data = {
    bookname: bookname,
    author: author,
    publisher: publisher,
    appkey: 'laotang110022'
  };

  axios({
    method: 'POST',
    url: 'http://www.itcbc.com:3006/api/addbook',
    data: JSON.stringify(data),  // json数据  //注意: axios可以自动转换为json数据
    headers: {
      'Content-Type': 'application/json' // Set Content-Type header to application/json
    }
  }).then(({ data: res }) => {
    myModal.hide();
    document.querySelector('#addForm').reset();
    renderBooks();
  });
});

```

后端

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @PostMapping("/api/addbook")
    public void addBook(@RequestBody BookRequest bookRequest) {
        // Handle the book data received from the frontend
        // Perform necessary operations, e.g., saving the book to a database
    }
}

```

##### 非json

前端

```js
document.querySelector('#addBtn').addEventListener('click', function () {
  const bookname = document.querySelector('#addForm [name=bookname]').value
  const author = document.querySelector('#addForm [name=author]').value
  const publisher = document.querySelector('#addForm [name=publisher]').value

  const data = {
    bookname: bookname,
    author: author,
    publisher: publisher,
    appkey: 'laotang110022'
  };

  axios({
    method: 'POST',
    url: 'http://www.itcbc.com:3006/api/addbook',
    data: data, // 非json数据
    headers: {
      'Content-Type': 'application/json' // Set Content-Type header to application/json
    }
  }).then(({ data: res }) => {
    myModal.hide();
    document.querySelector('#addForm').reset();
    renderBooks();
  });
});
```

后端

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @PostMapping("/api/addbook")
    public void addBook(
            @RequestParam String bookname,
            @RequestParam String author,
            @RequestParam String publisher,
            @RequestParam String appkey
    ) {
        // Handle the form data received from the frontend
        // Perform necessary operations, e.g., saving the book to a database
    }
}
```

##### 总结:crown:

代码见  [请求参数.zip](请求参数.zip) 使用的是springMVC框架

```java
package cn.lfj.controller;

import cn.lfj.entity.People;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: LFJ
 * @Date: 2023-10-17 17:20
 */

@Controller
public class FrontParameter {

	/**
	 * RequestParam(value = "参数名", required = false(表示该值非必须), defaultValue = "默认值")
	* http://localhost:8989/test1?id=1&name=zzz&gender=false&birth=2018-12-12 12:20:30
	 * param: id name gender birth
	 * get post都可以, body传参一般都用get
	 **/
	@RequestMapping("/test1")
	public String testParam1(@RequestParam("id") Integer id,
							 @RequestParam(value = "name", required = false, defaultValue = "李刚") String name,
							 @RequestParam(value = "gender", required = false) Boolean gender,
							 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
								 @RequestParam(value = "birth", defaultValue = "2023-12-12 12:20:30") Date birth){
		System.out.println("test param1");
		System.out.println(id + "--" + name+ "--" + gender+ "--" + birth);
		return "index";
	}


	/**
	 * PathVariable(value = "参数名", required = 默认值)  注:没有defaultValue
	 * http://localhost:8080/test2/1234/john/true/2023-01-01 12:00:00
	 * param: id name gender birth
	 * get post都可以, body传参一般都用get
	 **/
	@RequestMapping("/test2/{id}/{name}/{gender}/{birth}")    //PathVariable顾名思义需要固定url路径
	public String testParam2(@PathVariable("id") Integer id,
							 @PathVariable(value = "name") String name,
							 @PathVariable(value = "gender", required = false) Boolean gender,
							 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
							 @PathVariable(value = "birth",  required = false) Date birth){
		//为空时指定默认值
		if (birth == null) {
			birth = new Date("2023-12-12 12:20:30");
		}
		System.out.println("test param2");
		System.out.println(id + "--" + name+ "--" + gender+ "--" + birth);
		return "index";
	}


	/**
	 * http://localhost:8080/test3
	 * body: {"peoplename":"张刚","student":{"studentname":"你好"}}
	 * get post都可以, body传参一般都用post
	 * @param
	 */
	@RequestMapping("/test3")
	@ResponseBody
	public People testParam3(@RequestBody People people) {
		System.out.println("打印参数:" + people.getPeoplename() + people.getStudent().getStudentname());
		return people;
	}

}
```

==**详情见 springmvc**==

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

properties文件

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

### 5.2 @ConfigurationProperties方式(推荐)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

properties文件

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
    
    private App app;
    @Data
    public static class App {
        private MicroApplet microApplet;

        @Data
        public static class MicroApplet {
            private String appId = "";
            private String secretId = "";
        }
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

## 6、参数校验(常用)

数据的校验的重要性就不用说了，即使在前端对数据进行校验的情况下，我们还是要对传入后端的数据再进行一遍校验，避免用户绕过浏览器直接通过一些 HTTP 工具直接向后端请求一些违法数据。

JSR(Java Specification Requests） 是一套 JavaBean 参数校验的标准，它定义了很多常用的校验注解，我们可以直接将这些注解加在我们 JavaBean 的属性上面，这样就可以在需要校验的时候进行校验了，非常方便！

校验的时候我们实际用的是 Hibernate Validator 框架。Hibernate Validator 是 Hibernate 团队最初的数据校验框架，Hibernate Validator 4.x 是 Bean Validation 1.0（JSR 303）的参考实现，Hibernate Validator 5.x 是 Bean Validation 1.1（JSR 349）的参考实现，目前最新版的 Hibernate Validator 6.x 是 Bean Validation 2.0（JSR 380）的参考实现。

SpringBoot 项目的 spring-boot-starter-web 依赖中已经有 hibernate-validator 包，不需要引用相关依赖。如下图所示（通过 idea 插件—Maven Helper 生成）：

<img src="SpringBoot常用注解.assets/image-20230925151012885.png" alt="image-20230925151012885" style="zoom: 67%;" />

非 SpringBoot 项目需要自行引入相关依赖包，这里不多做讲解

需要注意的是： 所有的注解，推荐使用 JSR 注解，即javax.validation.constraints，而不是org.hibernate.validator.constraints

(1) 一般spring-boot-starter-web会自动传入hibernate-validator依赖6.0.1版本:

```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

（2）如果没有，就手动传入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

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

1.声明需要被校验的字段

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated        //
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

2.在controller上声明需要对数据进行校验(在需要验证的参数上加上了`@Valid`注解)，如果验证失败，它将抛出`MethodArgumentNotValidException`

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

### 6.3. 验证请求参数

```java
@RestController
@RequestMapping("/api")
@Validated
public class PersonController {
    @GetMapping("/person/{id}")
    public ResponseEntity<Integer> getPersonByID(@DecimalMax(0) @Max(value = 5,message = "超过 id 的范围了")  @PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(id);
    }
}
```

### 6.4 自定义(例子1)

##### 一、注解定义

要实现扩展 validate 框架的注解比较简单，直接在注解上添加 @Constraint 并指定校验的实现类即可

```java
@Inherited
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartFilesValidator.class, MultipartFileValidator.class})
public @interface MultipartFileVerify {

    String message() default "文件校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 文件类型限制
     */
    FileType[] value() default {};

    /**
     * 不允许上传的文件类型，不指定则不作限制
     */
    FileType[] notAllow() default {};

    /**
     * 文件大小限制，小于 0 表示不作限制；单位：千字节（KB）
     */
    long maxSize() default -1L;
}
12345678910111213141516171819202122232425262728
```

message、groups、[payload](https://so.csdn.net/so/search?q=payload&spm=1001.2101.3001.7020) 为必须项，其他的为自定义，此自定义注解的意思是该注解的只能用在 PARAMETER（方法参数） 上，并且为运行时可用，有两个校验类，分别为多文件 `MultipartFilesValidator` 与 单文件 `MultipartFileValidator` 校验实现。

##### 二、校验类实现

校验类必须实现 `ConstraintValidator` 接口

![image-20220409012014198](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/23b3c7461d630aa5f25750c14d90ad5b.png)

[泛型](https://so.csdn.net/so/search?q=泛型&spm=1001.2101.3001.7020) A 表示自定义注解，T 表示你校验类需要传递的参数。

单文件校验类的实现

```java
/**
 * 单个 MultipartFile 校验
 */
public class MultipartFileValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile> {
    @Resource
    private AbstractMultipartFileValidator abstractMultipartFileValidator;

    private MultipartFileVerify multipartFileValid;

    @Override
    public void initialize(MultipartFileVerify constraintAnnotation) {
        this.multipartFileValid = constraintAnnotation;
    }

    
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return abstractMultipartFileValidator.isFileValid(multipartFileValid, value);
    }
}
12345678910111213141516171819202122
```

多文件校验的实现

```java
/**
 * 兼容多 MultipartFile 校验
 */
public class MultipartFilesValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile[]> {

    @Resource
    private AbstractMultipartFileValidator abstractMultipartFileValidator;

    private MultipartFileVerify multipartFileValid;

    @Override
    public void initialize(MultipartFileVerify constraintAnnotation) {
        this.multipartFileValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) {
            return true;
        }
        for (MultipartFile multipartFile : value) {
            if (!abstractMultipartFileValidator.isFileValid(multipartFileValid, multipartFile)) {
                return false;
            }
        }
        return true;
    }
}
12345678910111213141516171819202122232425262728
```

写两个校验类是因为 ConstraintValidator 在校验时只能传一个参数呀，当同时上传多个文件就不能满足了，接口的方法参数类型必须与 ConstraintValidator T 泛型一致才行。这样就扩展出了一个文件校验的注解，AbstractMultipartFileValidator 这个抽象类作用是在责任链中校验传过来的文件格式是否一致。

### 6.5 自定义(例子2)

##### 自定义注解

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
    validatedBy = {MobileNoValidator.class}  // 指定校验的实现类
)
public @interface MobileNo {
    boolean nullable() default false;

    boolean blankable() default false;
    
    // 下面的message, groups和payload也是必须添加的
    String message() default "手机号格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

该自定义注解类中用到了四种元注解，最后一个注解@Constraint表示校验此注解的校验器类，可以多个。值得一提的是除了自定义的message、nullable和blankable属性外，下面的groups和payload也是必须添加的。

##### 注解校验类

```java
//  ConstraintValidator<自定义注解类, 校验的数据类型>  
public class MobileNoValidator implements ConstraintValidator<MobileNo, String> {
    private boolean nullable;
    private boolean blankable;
    private String message;

    public MobileNoValidator() {
    }

    //初始化方法
    @Override
    public void initialize(MobileNo constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
        this.message = constraintAnnotation.message();
        this.blankable = constraintAnnotation.blankable();
    }

    //自定义的校验逻辑 isValid(校验的数据, ConstraintValidatorContext context)
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!this.nullable) {
                //禁止默认消息返回
                context.disableDefaultConstraintViolation();
                //自定义返回消息
                context.buildConstraintViolationWithTemplate("手机号不能为空").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } else if (value.length() == 0) {
            if (!this.blankable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("手机号不能为空白").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } else if (!AppUtils.isMobile(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate((String)StringUtils.defaultIfBlank(this.message, "手机号格式不对")).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
```

### 6.6 封住全局异常处理器

单独拦截参数校验的异常：`javax.validation.ConstraintViolationException`，`org.springframework.web.bind.MethodArgumentNotValidException`

```java
     /**
     * RequestParam 参数格式校验不通过 异常
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ConstraintViolation<?>> list = new ArrayList<>(constraintViolations);
        ConstraintViolation<?> constraintViolation = list.get(0);
        return new ApiResponse(INVALID_REQUEST.getErrorCode(),constraintViolation.getMessage());
    }
    
 	/**
     *  RequestBody 参数校验不通过 异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return new ApiResponse(INVALID_REQUEST.getErrorCode(),fieldErrors.get(0).getDefaultMessage());
    }
```

## 7、全局处理 Controller 层异常

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

**添加位置**

1. 接口实现类或接口实现方法上，而不是接口类中。
2. 访问权限：public 的方法才起作用。@Transactional 注解应该只被应用到 public 方法上，这是由 Spring [AOP](https://so.csdn.net/so/search?q=AOP&spm=1001.2101.3001.7020) 的本质决定的。
3. 系统设计：将标签放置在需要进行事务管理的方法上，而不是放在所有接口实现类上：只读的接口就不需要事务管理，由于配置了@Transactional就需要AOP拦截及事务的处理，可能影响系统性能。

在要开启事务的方法上使用@Transactional注解即可!

```java
@Transactional(rollbackFor = Exception.class)
public void save() {
  ......
}
```

 Exception 分为运行时异常 RuntimeException 和非运行时异常。在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚。

@Transactional 注解一般用在可以作用在类或者方法上。

+ 作用于类：当把@Transactional 注解放在类上时，表示所有该类的public 方法都配置相同的事务属性信息。

+ 作用于方法：当类配置了@Transactional，方法也配置了@Transactional，方法的事务会覆盖类的事务配置信息。

[@Transactional 详解-CSDN博客](https://blog.csdn.net/jiangyu1013/article/details/84397366)

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

![image.png](SpringBoot常用注解.assets/1606053840338-ea7d0cce-cf13-4723-98e3-97a8d6b2257f.png)

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

![image.png](SpringBoot常用注解.assets/1606140839529-0652ddeb-fb59-4553-9e3f-01706004206f.png)



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

## 13. Lombok注解

### **@AllArgsConstructor**  

+ 添加一个有参数的构造器

### **@NoArgsConstructor**

+ 添加一个无参数的构造器

### **@Data**

- 在编译时会自动加入Getter,Setter,equals,canEqual,hasCode,toString等方法

### **@Builder**

+ Builder的作用之一是为了解决在某个类有很多构造函数的情况，也省去写很多构造函数的麻烦，在设计模式中的思想是：用一个内部类去实例化一个对象，避免一个类出现过多构造函数

例子

```typescript
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
public class test1 {
    String name;
    String age;
    String sex;
}
```

测试

```java
 public static void main(String[] args) {
        //使用@Builder注解后，可以直接通过Builder设置字段参数
        test1 t1=new test1.test1Builder()
                .name("wang")
                .age("12")
                .sex("man")
                .build();
     
        System.out.println("name is"+t1.getName()+'\n'+"age is :"+t1.getAge());
 }
```

### @EqualsAndHashCode(callSuper = true)

举个简单的例子：
这边先定义一个分类对象 Parent，有一个属性：code

```handlebars
@Data
public class Parent {
    /**
     * 父类编码
     */
    private String code;
}
```

再定义一个子类对象 Child，一一个属性：name

```handlebars
@Data
public class Child extends Parent {
    /**
     * 子类名称
     */
    private String name;
}
```

在方法中 new 两个 Child 对象：childTest1、childTest2
对这两个 Child 对象的自有属性 name 都赋值为：Child；但是对继承的父类属性 code 进行不同的赋值

```handlebars
Child childTest1 = new Child();
childTest1.setCode("1");
childTest1.setName("child");

Child childTest2 = new Child();
childTest2.setCode("2");
childTest2.setName("child");
```

根据使用过程中，这两个对象肯定是不一样的，但是，在不加 @EqualsAndHashCode(callSuper = true) 注解的情况下对这两个对象进行比较得到的结果却是 true

```handlebars
boolean isSame = Objects.equals(childTest1,childTest2);
log.info("testEquals -> childTest1:{}, childTest2:{}", childTest1, childTest2);
log.info("testEquals -> :{}", isSame);
```

![在这里插入图片描述](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/20210624104324895.png)
@EqualsAndHashCode(callSuper = true) 注解的作用就是将其父类属性也进行比较，下面是 Child 类加了注解后运行的结果：

```handlebars
@EqualsAndHashCode(callSuper = true)
@Data
public class Child extends Parent {
    /**
     * 子类名称
     */
    private String name;
}
```

![在这里插入图片描述](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/20210624104351617.png)

### @RequiredArgsConstructor

使用 `@RequiredArgsConstructor` 注解的类需要满足以下条件：

- 类中至少要有一个被 `final` 或 `@NonNull` 注解修饰的字段。
- 类不能显式定义任何构造函数。

```java
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyClass {
    private final String name;
    
    @NonNull
    private int age;
    private String address;
    
    // 自动生成的构造函数
    // public MyClass(String name, int age) {
    //     this.name = name;
    //     this.age = age;
    // }
}

```



# java自带注解

## @PostConstruct

@[PostConstruct](https://so.csdn.net/so/search?q=PostConstruct&spm=1001.2101.3001.7020)是Java自带的注解，在方法上加该注解会在项目启动的时候执行该方法，在spring容器初始化的时候执行该方法。

从Java EE5规范开始，Servlet中增加了两个影响[Servlet生命周期](https://so.csdn.net/so/search?q=Servlet生命周期&spm=1001.2101.3001.7020)的注解，@PostConstruct和@PreDestroy，这两个注解被用来修饰一个非静态的void（）方法。只会执行一次。这可以确保初始化代码在Bean实例化之后立即执行，并且不会重复执行。

**语法：**

```java
@PostConstruct
public void init(){}
```

# 文档注释

## 标签

| **标签**      | **描述**                                               | **示例**                                                     |
| :------------ | :----------------------------------------------------- | :----------------------------------------------------------- |
| @author       | 标识一个类的作者                                       | @author description                                          |
| @deprecated   | 指名一个过期的类或成员                                 | @deprecated description                                      |
| {@docRoot}    | 指明当前文档根目录的路径                               | Directory Path                                               |
| @exception    | 标志一个类抛出的异常                                   | @exception exception-name explanation                        |
| {@inheritDoc} | 从直接父类继承的注释                                   | Inherits a comment from the immediate surperclass.           |
| {@link}       | 插入一个到另一个主题的链接                             | {@link name text}                                            |
| {@linkplain}  | 插入一个到另一个主题的链接，但是该链接显示纯文本字体   | Inserts an in-line link to another topic.                    |
| @param        | 说明一个方法的参数                                     | @param parameter-name explanation                            |
| @return       | 说明返回值类型                                         | @return explanation                                          |
| @see          | 指定一个到另一个主题的链接                             | @see anchor                                                  |
| @serial       | 说明一个序列化属性                                     | @serial description                                          |
| @serialData   | 说明通过writeObject( ) 和 writeExternal( )方法写的数据 | @serialData description                                      |
| @serialField  | 说明一个ObjectStreamField组件                          | @serialField name type description                           |
| @since        | 标记当引入一个特定的变化时                             | @since release                                               |
| @throws       | 和 @exception标签一样.                                 | The @throws tag has the same meaning as the @exception tag.  |
| {@value}      | 显示常量的值，该常量必须是static属性。                 | Displays the value of a constant, which must be a static field. |
| @version      | 指定类的版本                                           | @version info                                                |

## 例子

```java
import java.io.*;
/**
* This class demonstrates documentation comments.
* @author Ayan Amhed
* @version 1.2
*/
public class SquareNum {
   /**
   * This method returns the square of num.
   * This is a multiline description. You can use
   * as many lines as you like.
   * @param num The value to be squared.
   * @return num squared.
   */
   public double square(double num) {
      return num * num;
   }
   /**
   * This method inputs a number from the user.
   * @return The value input as a double.
   * @exception IOException On input error.
   * @see IOException
   */
   public double getNumber() throws IOException {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader inData = new BufferedReader(isr);
      String str;
      str = inData.readLine();
      return (new Double(str)).doubleValue();
   }
   /**
   * This method demonstrates square().
   * @param args Unused.
   * @return Nothing.
   * @exception IOException On input error.
   * @see IOException
   */
   public static void main(String args[]) throws IOException
   {
      SquareNum ob = new SquareNum();
      double val;
      System.out.println("Enter value to be squared: ");
      val = ob.getNumber();
      val = ob.square(val);
      System.out.println("Squared value is " + val);
   }
}
```



# 使用切面类(AOP)

**AOP面向切面编程可以让我们实现一些与业务逻辑无关的功能,  如日志、事务、安全等。**

## AOP日志记录方式

- 扫描包的方式。传入的参数在请求头里面(企业常用)
- 自定义注解

### 切面相关注解

@Aspect -- 作用是把当前类标识为一个切面供容器读取
@Pointcut -- (切入点):就是带有通知的连接点，在程序中主要体现为书写切入点表达式
@Before -- 标识一个前置增强方法，相当于BeforeAdvice的功能
@AfterReturning -- 后置增强，相当于AfterReturningAdvice，方法退出时执行
@AfterThrowing -- 异常抛出增强，相当于ThrowsAdvice
@After -- final增强，不管是抛出异常或者正常退出都会执行
@Around -- 环绕增强，相当于MethodInterceptor, if

### **AOP五种通知工作**

前置通知：在目标方法调用之前执行，可以获得切入点信息；
后置通知：在目标方法执行后执行，目标方法有异常不执行；
异常通知：在目标方法抛出异常时执行，可以获取异常信息；
最终通知：在目标方法执行后执行，无论是否有异常都执行；
环绕通知：最强大的通知类型，在目标方法执行前后操作，可以阻止目标方法执行。

### 方法1:  扫描包的方式

![image-20240119205313618](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119205313618.png)

#### pom依赖

```xml
<!--AOP的依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<!--其他-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.79</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

#### aspect

```java
package com.example.springbootaop.aspect;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 20:30
 */

@Aspect
@Component
public class WebLogAspect {

	private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	/**定义切入点----以 controller 包下所有以*Ticket结尾的公共方法。 */
	@Pointcut("execution(public * com.example.springbootaop.controller..*.*Ticket(..))")
	public void webLog() {
	}

	/**
	 * 在切点之前植入
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 开始打印请求日志
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 打印请求相关参数
		logger.info("========================================== Start ==========================================");
		// 打印请求 url
		logger.info("URL            : {}", request.getRequestURL().toString());
		// 打印 Http method
		logger.info("HTTP Method    : {}", request.getMethod());
		// 打印调用 controller 的全路径以及执行方法
		logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());
		// 打印请求的 IP
		logger.info("IP             : {}", request.getRemoteAddr());
		// 打印请求入参
		logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
	}

	/**
	 * 在切点之后织入
	 *
	 * @throws Throwable
	 */
	@After("webLog()")
	public void doAfter() throws Throwable {
		logger.info("=========================================== End ===========================================");
		// 每个请求之间空一行
		logger.info("");
	}

	/**
	 * 环绕
	 *
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("webLog()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		// 打印出参
		logger.info("Response Args  : {}", new Gson().toJson(result));
		// 执行耗时
		logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
		return result;
	}


	/**
	 *
	 * @param proceedingJoinPoint 切面
	 * @return
	 * @throws Throwable
	 */
//	@Around("webLog()")
//	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//		long start = System.currentTimeMillis();
//		Object result = proceedingJoinPoint.proceed();
//		logger.info("Request Params       : {}", getRequestParams(proceedingJoinPoint));
//		logger.info("Result               : {}", result);
//		logger.info("Time Cost            : {} ms", System.currentTimeMillis() - start);
//
//		return result;
//	}

	/**
	 * 获取入参
	 *
	 * @param proceedingJoinPoint
	 *
	 * @return
	 */
	private Map<String, Object> getRequestParams(ProceedingJoinPoint proceedingJoinPoint) {
		Map<String, Object> requestParams = new HashMap<>();

		// 参数名
		String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
		// 参数值
		Object[] paramValues = proceedingJoinPoint.getArgs();

		for (int i = 0; i < paramNames.length; i++) {
			Object value = paramValues[i];

			// 如果是文件对象
			if (value instanceof MultipartFile) {
				MultipartFile file = (MultipartFile) value;
				// 获取文件名
				value = file.getOriginalFilename();
			}

			requestParams.put(paramNames[i], value);
		}

		return requestParams;
	}

}

```

#### controller

```java
package com.example.springbootaop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 20:33
 */
@Controller
public class TestController {
	@GetMapping("index")
	@ResponseBody
	public String index(){
		return "Hello";
	}

	// com.example.springbootaop.aspect.WebLogAspect配置了controller总以xxxTicket结尾的方法
	@GetMapping("world")
	@ResponseBody
	public String indexTicket(){
		return "world";
	}
}

```

#### **运行结果**

网页输入:  localhost:8080/world

![image-20240119204202614](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119204202614.png)

控制台打印

![image-20240119204027727](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119204027727.png)

### 方法2: 自定义注解方式

![image-20240119212632540](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119212632540.png)

#### pom依赖

```xml
<!--AOP的依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

#### aspect

#####  自定义注解

```java
package com.example.springbootaopannotation.aspect;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 21:05
 */
import java.lang.annotation.*;

/**
 * 用于统计耗时注解
 */
@Documented //用于描述其它类型的annotation应该被作为被标注的程序成员的公共API因此可以被例如javadoc此类的工具文档化.Documented是一个标记注解,没有成员.表示该注解将被包含在 Javadoc 中
@Target({ElementType.METHOD, ElementType.TYPE}) //表示该注解可以用于方法和类。
@Retention(RetentionPolicy.RUNTIME) //表示该注解在运行时保留，可以通过反射获取。
public @interface TakeTime {

	String methodName() default "";
}

// 使用  @TakeTime(methodName: xxx)
```

- `@Target({ElementType.METHOD, ElementType.TYPE})`: 表示该注解可以用于方法和类。
- `@Retention(RetentionPolicy.RUNTIME)`: 表示该注解在运行时保留，可以通过反射获取。
- `@Documented`: 表示该注解将被包含在 Javadoc 中。
- `@Inherited`: 表示该注解可以被子类继承。

##### 定义切面类

```java
package com.example.springbootaopannotation.aspect;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 21:12
 */

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * 耗时统计
 */
@Slf4j
@Aspect
@Component
public class TakeTimeAspect {
	//统计请求的处理时间
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	ThreadLocal<Long> endTime = new ThreadLocal<>();

	/**定义切入点----以 aspect 包下带有@TakeTime注解的方法 */
	@Pointcut("@annotation(com.example.springbootaopannotation.aspect.TakeTime)")
	public void TakeTime() {
	}

	@Before("TakeTime()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 获取方法的名称
		String methodName = joinPoint.getSignature().getName();
		// 获取方法入参
		Object[] param = joinPoint.getArgs();
		StringBuilder sb = new StringBuilder();
		for (Object o : param) {
			sb.append(o + ";");
		}
		log.info("进入《{}》 方法,参数为: {}", methodName,sb.toString());

		System.out.println("System.currentTimeMillis(): "+System.currentTimeMillis());
		System.out.println("new Date(): "+new Date());
		startTime.set(System.currentTimeMillis());
		log.info("方法开始时间:" +startTime.get());
		//接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		//记录请求的内容
		log.info("请求URL:" + request.getRequestURL().toString());
		log.info("请求METHOD:" + request.getMethod());
	}

	@AfterReturning(returning = "ret", pointcut = "TakeTime()")
	public void doAfterReturning(Object ret) {
		//处理完请求后，返回内容
		log.info("方法返回值:" + JSON.toJSONString(ret));
		endTime.set(System.currentTimeMillis());
		log.info("方法结束时间" +endTime.get());
		log.info("方法结束时间" +new Date());
		log.info("方法执行时间:" + (endTime.get() - startTime.get()));
	}
}
```

#### controller

```java
package com.example.springbootaopannotation.controller;

import com.example.springbootaopannotation.aspect.TakeTime;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 21:16
 */

@Controller
public class TestController {

	@RequestMapping("/loadForTestVariableCategories")
	@TakeTime(methodName = "loadForTestVariableCategories")
	public void loadForTestVariableCategories(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int n = 0;
		for (int i = 0; i < 100000; i++) {
			n += i;
		}
	}
}
```

#### 运行结果

网页输入:  localhost:8080/loadForTestVariableCategories

![image-20240119213124521](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119213124521.png)

控制台打印

![image-20240119212558464](SpringBoot%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3.assets/image-20240119212558464.png)

## 案例2

```java
package com.springboot101.limit.aspect;

import com.springboot101.limit.enmu.LimitType;

import java.lang.annotation.*;

/**
 * @description redis限流注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimter {

    /**
     * 名字
     */
    String name() default "";

    /**
     * key
     */
    String key() default "";

    /**
     * Key的前缀
     */
    String prefix() default "";

    /**
     * 过期时间，单位秒
     */
    int period();

    /**
     * 单位时间限制通过请求数
     */
    int count();

    /**
     * 限流的类型(用户自定义key 或者 请求ip)
     */
    LimitType limitType() default LimitType.CUSTOMER;

    /**
     * 返回值
     */
    String message() default "false";
}

```



```java
package com.springboot101.limit.aspect;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.RateLimiter;
import com.springboot101.limit.enmu.LimitType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @description 限流切面实现
 * @date 2020/4/8 13:04
 */
@Aspect
@Configuration
@Slf4j
public class RateLimterAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimterAspect.class);

    private static final String UNKNOWN = "unknown";

    @Resource
    private  RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimter.lua")));
        log.info("RateLimterAspect[分布式限流处理器]脚本加载完成");
    }

//    private final RedisTemplate<String, Serializable> limitRedisTemplate;

//    public LimitInterceptor(@Autowired RedisTemplate redisTemplate) {
//        this.limitRedisTemplate = redisTemplate;
//    }

//    @Autowired
//    public LimitInterceptor(RedisTemplate<String, Serializable> limitRedisTemplate) {
//        this.limitRedisTemplate = limitRedisTemplate;
//    }

    /**定义切入点----以aspect包下带有 @RateLimter注解 的方法*/
    @Pointcut("@annotation(com.springboot101.limit.aspect.RateLimter)")
    public void rateLimiter() {}

    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RateLimter rateLimiter) throws Throwable {
        if (log.isDebugEnabled()){
            log.debug("RateLimterAspect[分布式限流处理器]开始执行限流操作");
        }

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the Annotation @RateLimter must used on method!");
        }

        Method method = signature.getMethod();
        /*
        RateLimter limitAnnotation = method.getAnnotation(RateLimter.class);
        // 注解参数
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        if (log.isDebugEnabled()){
            log.debug("RateLimterAspect[分布式限流处理器]参数值为-limitPeriod={},limitCount={}", limitPeriod, limitCount);
        }
         */
        // @RateLimter注解携带的参数
        LimitType limitType = rateLimiter.limitType();
        String name = rateLimiter.name();
        String key;
        int limitPeriod = rateLimiter.period();
        int limitCount = rateLimiter.count();
        if (log.isDebugEnabled()){
            log.debug("RateLimterAspect[分布式限流处理器]参数值为-limitPeriod={},limitCount={}", limitPeriod, limitCount);
        }
        // 限流提示语
        String message = rateLimiter.message();
        if (StringUtils.isEmpty(message)) {
            message = "false";
        }
        /**
         * 根据限流类型获取不同的key ,如果不传我们会以方法名作为key
         */
        switch (limitType) {
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                key = rateLimiter.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        // key拼接前缀
        //ImmutableList<String> keys = ImmutableList.of(StringUtils.join(rateLimiter.prefix(), key));

        //执行Lua脚本
        List<String> keyList = new ArrayList<>();

        // 设置key值为注解中的值
        keyList.add(StringUtils.join(rateLimiter.prefix(), key));

        //调用脚本并执行
        try {
            Long result = (Long) redisTemplate.execute(getRedisScript, keyList, limitPeriod, limitCount);
            if (result != null && result == 0) {
                String msg = "由于超过单位时间=" + limitPeriod + "-允许的请求次数=" + limitCount + "[触发限流]";
                log.debug(msg);
                if (!message.equals("false"))
                    return message;
                else
                    throw new RuntimeException("你已被列入黑名单");
            }
            if (log.isDebugEnabled()) {
                log.debug("RateLimterAspect[分布式限流处理器]限流执行结果-result={},请求[正常]响应", result);
            }
            return proceedingJoinPoint.proceed();
        }catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }

    /**
     * 获取客户端 IP 地址。
     *
     * @return 客户端 IP 地址。
     */
    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
```

## 案例3

部分代码

```java

	//在切面中实现加密和解密的功能，并确保在调用目标方法之前和之后都执行这些操作
	@Around("@annotation(pointCut)")
	public Object around(ProceedingJoinPoint joinPoint, EncryptMethod pointCut) throws Throwable {
		// 获取 @EncryptMethod 中的 type 参数值
		String type = pointCut.type();
		switch (type) {
			case ENCRYPT:
				// 执行加密操作
				return encrypt(joinPoint);
			case DECRYPT:
				// 执行解密操作
				return decrypt(joinPoint);  //环绕方法中返回值: 目标方法的执行结果
			default:
				// 如果类型不匹配，则返回 null 或其他适当的值
				return null;
		}
	}

	public Object encrypt(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取目标方法的参数
		Object[] args = joinPoint.getArgs();
		try {
			// 如果方法有参数
			if (args.length != 0) {
				for (int i = 0; i < args.length; i++) {
					Object o = args[i];
					// 如果参数是字符串类型，则对其进行加密
					if (o instanceof String) {
						args[i] = encryptValue(o);
					} else {
						// 如果参数不是字符串类型，则调用 handler 方法对其进行加密处理
						args[i] = handler(o, EncryptConstant.ENCRYPT);
					}
					//TODO 其余类型自己看实际情况加
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// 调用目标方法，并传入修改后的参数
		// 这样方法内部就能直接获取方法的参数加密后的值,
		// 不这样做方法内部方法的参数不会加密,只有返回值是加密后的 例子如下
        /*
            @EncryptMethod()
            @PostMapping("/dataEnc")
            @ResponseBody
            public Object testEncrypt(@RequestParam @EncryptField String username) {
                System.out.println("加密后的username：" + username); // 实际没打印加密后的
                return username;  // 返回值是加密后的
            }
        */
		Object result = joinPoint.proceed(args);
		return result;
	}

	public Object decrypt(ProceedingJoinPoint joinPoint) {
		Object result = null;
		try {
			// 执行被切入的方法，并获取方法返回值
			Object obj = joinPoint.proceed();
			// 检查返回值是否为空
			if (obj != null) {
				// 如果返回值是字符串类型
				if (obj instanceof String) {
					// 解密字符串类型的返回值
					result = decryptValue(obj);
				} else {
					// 如果返回值不是字符串类型，调用handler方法处理返回值
					result = handler(obj, EncryptConstant.DECRYPT);
				}
				//TODO 其余类型自己看实际情况加
			}
		} catch (Throwable e) {
			// 捕获异常并打印堆栈信息
			e.printStackTrace();
		}
		// 返回解密后的结果
		return result;
	}
```

# @AliasFor

### 用法1：自定义注解的属性互为别名(常用)

#### 简介

它可以注解到自定义注解的两个属性上，表示这两个互为别名，也就是说这两个属性其实同一个含义。

- 其中一个属性名必须是"value"
- 无论指明设置哪个属性名设置属性值，另一个属性名也是同样属性值，也可以缺省属性名。
- 若两个都指明属性值，要求值必须相同，否则会报错。
- 这样使用之后，@MyAnno(location="shanghai")可以直接写为：@MyAnno("shanghai");

#### 代码

**注解**

```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface MyAnnotation {
     @AliasFor(attribute = "location")
     String value() default "";
    
     @AliasFor(attribute = "value")
     String location() default "";
}
```

**控制器**

```java
package com.example.controller;
    
import com.example.annotation.MyAnnotation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
@RequestMapping("/hello")
public class HelloController {
    
     @MyAnnotation(value = "location")
     /*//下边这两种写法结果与上边是相同的
     @MyAnnotation("location")
     @MyAnnotation(location = "location")*/
     @RequestMapping("/test1")
     public String test1() {
         MyAnnotation myAnnotation = null;
         try {
             myAnnotation = AnnotationUtils.getAnnotation(this.getClass().getMethod("test1"), MyAnnotation.class);
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         }
    
         return "value:" + myAnnotation.value() + ";loation:" + myAnnotation.location();
     }
}
```

**测试**

前端访问：http://localhost:8080/hello/test1

前端结果（value和location都是同一个值）

### 用法2.继承父注解的属性，不重写属性名

#### 简介

子注解的属性值的读写，其实是对父注解的属性值的读写。（对继承的属性来说）

此时，只能读写继承了的属性值。

#### 代码

**注解**

```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface MyAnnotation {
     @AliasFor(attribute = "location")
     String value() default "";
    
     @AliasFor(attribute = "value")
     String location() default "";
}
```

```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
@MyAnnotation
public @interface SubMyAnnotation {
     @AliasFor(annotation = MyAnnotation.class)
     String location() default "";
    
// 这个不能写，只能有一个与父属性名同名的属性，否则报错
// @AliasFor(annotation = MyAnnotation.class)
// String value() default "";
}
```

**控制器**

```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
@MyAnnotation
public @interface SubMyAnnotation {
     @AliasFor(annotation = MyAnnotation.class)
     String location() default "";
    
// 这个不能写，只能有一个与父属性名同名的属性，否则报错
// @AliasFor(annotation = MyAnnotation.class)
// String value() default "";
}
```

**测试**

前端访问：http://localhost:8080/hello/test

**结果**

> loation(sub):location(my)
> location:
> location:location(my)

### 用法3：继承父注解的属性，并重写属性名

#### 简介

子注解的属性值的读写，其实是对父注解的属性值的读写。（对重写的属性来说）

无论指明设置哪个属性名设置属性值，另一个属性名也是同样属性值，不可以缺省属性名。

若两个都指明属性值，要求值必须相同，否则会报错。

#### 代码

```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface MyAnnotation {
     @AliasFor(attribute = "location")
     String value() default "";
    
     @AliasFor(attribute = "value")
     String location() default "";
}
```



```java
package com.example.annotation;
    
import org.springframework.core.annotation.AliasFor;
    
import java.lang.annotation.*;
    
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
@MyAnnotation
public @interface SubMyAnnotation {
     @AliasFor(attribute = "value", annotation = MyAnnotation.class)
     String subValue() default "";
    
     @AliasFor(attribute = "location", annotation = MyAnnotation.class)
     String subLocation() default "";
    
// subLocation属性写成下边这两种结果是一样的
// @AliasFor(attribute = "value", annotation = MyAnnotation.class)
// String subLocation() default "";
    
// @AliasFor(value = "location", annotation = MyAnnotation.class)
// String subLocation() default "";
//
}
```

**控制器**

```java
package com.example.controller;
    
import com.example.annotation.MyAnnotation;
import com.example.annotation.SubMyAnnotation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
@RequestMapping("/hello")
public class HelloController {
     @SubMyAnnotation(subValue = "subLocation")
// @SubMyAnnotation(subLocation = "subLocation") //这个与上边结果相同
// @SubMyAnnotation("subLocation") //缺省属性名会报错
     @RequestMapping("/test")
     public String test() {
         SubMyAnnotation subMyAnnotation = null;
         MyAnnotation myAnnotation = null;
         MyAnnotation myAnnotation1 = null;
    
         try {
             subMyAnnotation = AnnotationUtils.getAnnotation(this.getClass().getMethod("test"), SubMyAnnotation.class);
             myAnnotation = AnnotationUtils.getAnnotation(this.getClass().getMethod("test"), MyAnnotation.class);
             myAnnotation1 = AnnotatedElementUtils.getMergedAnnotation(this.getClass().getMethod("test"), MyAnnotation.class);
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         }
         return "subValue:" + subMyAnnotation.subValue() + ";subLoation:" + subMyAnnotation.subLocation() + "\n" +
                 "value:" + myAnnotation.value() + ";location:" + myAnnotation.location() + "\n" +
                 "value:" + myAnnotation1.value() + ";location:" + myAnnotation1.location();
     }
}
```

**测试**

前端访问：http://localhost:8080/hello/test

**结果**

> subValue:subLocation;subLoation:subLocation
> value:;location:
> value:subLocation;location:subLocation





# 补充

[Springboot中常用注解@Accessors/@Data/@JsonInclude(JsonInclude.Include.NON_NULL)/@EqualsAndHashCode-CSDN博客](https://blog.csdn.net/studyday1/article/details/127067186)



# 针对前端json数据

## @JsonInclude注解的常用属性

### 3.1 Include.NON_NULL

Include.NON_NULL属性指定序列化过程中，忽略null值。

```java
public class Person {
  private String name;
  private Integer age;
  private String address;
  // getter and setter omitted
}
```

在上面的代码中，我们希望在Java对象转换为JSON时，忽略空地址。我们可以使用@JsonInclude注解，加上Include.NON_NULL属性：

```java
public class Person {
  private String name;
  private Integer age;
  
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String address;
  // getter and setter omitted
}
```

这样，当序列化Person对象时，如果address属性为null，则忽略address属性的序列化。

### 3.2 Include.NON_EMPTY

Include.NON_EMPTY属性与Include.NON_NULL类似，但只要属性值不为null、空字符串或空数组，就进行序列化。



```java
public class Person {
  private String name;
  private Integer age;
  
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String address;
  // getter and setter omitted
}
```

Copy



这样，当address属性为null、空字符串或空数组时，忽略序列化过程。

### 3.3 Include.NON_DEFAULT

Include.NON_DEFAULT属性仅序列化与默认值不同的属性。



```java
public class Person {
  private String name;
  private Integer age;
  
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private Boolean married = false;
  // getter and setter omitted
}
```

Copy



在上面的代码中，married属性的默认值为false。当married属性为false时，不进行序列化。如果married属性为true，则进行序列化。

### 3.4 Include.CUSTOM

Include.CUSTOM属性允许开发人员使用自定义的序列化过滤器。

下面的Java代码定义了一个自定义序列化过滤器，用于序列化Person类的所有属性。



```java
public static class MyCustomFilter extends SimpleBeanPropertyFilter {
  @Override
  public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
    if (include(writer)) {
      writer.serializeAsField(pojo, jgen, provider);
    } else if (!jgen.canOmitFields()) {
      writer.serializeAsOmittedField(pojo, jgen, provider);
    }
  }
 
  @Override
  protected boolean include(PropertyWriter writer) {
    // your custom condition here
    return true;
  }
}
```

Copy



然后，在Person类中使用@JsonInclude注解，指定序列化过程中使用上面定义的自定义序列化过滤器：



```java
public class Person {
  private String name;
  private Integer age;
  
  @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = MyCustomFilter.class)
  private String address;
  // getter and setter omitted
}
```

Copy



这样，当序列化Person对象时，就使用自定义的序列化过滤器。

## @JsonSerialize和@JsonDeserialize的使用详解

#### 背景：

在项目中处理数据时需要对所有的金额格式化操作，具体做法是，后端接收的金额类数据单位需要由万元转换为元在存入数据库，而返回到前端的金额类数据又必须由元转换为万元返回，以便保持数据一致。

如果用传统方式，在保存和返回时加上转换的处理，需要复杂且繁琐的操作，jackson提供了JsonSerialize和JsonDeserialize注解来优雅的解决这个问题，项目采用的springboot框架，而springboot框架默认配置json转换工具就是jackson。如此，使用注解解决问题很nice了。

 

#### 注解简介：

\1. @JsonSerialize：json序列化注解，用于字段或get方法上，作用于getter()方法，将java对象序列化为json数据。

```java
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //include里面包含了序列化的范围和作用的规则，本行作用是属性为null的时候不进行序列化操作。
 
@JsonSerialize(using = Bean.class) //Bean 为实现类,Bean需要继承JsonSerializer<>,泛型就是作用字段的类型。
```

\2. @JsonDeserialize：json[反序列化](https://so.csdn.net/so/search?q=反序列化&spm=1001.2101.3001.7020)注解，用于字段或set方法上，作用于setter()方法，将json数据反序列化为java对象。使用方法同@JsonSerialize类似。

3.常用于对数据进行简单的特殊处理，比如本次项目实践用到的，对金额类数据进行格式化操作。



需要注意的是，该注解只在json序列化和反序列化的时候触发，其他时候并不生效！

注意：json序列化及反序列化注解通常用在前后端传值上，作用于get,set方法上，但并不是重写get,set方法，而是类似于补充，追加。

理解注解的作用和触发很重要，这能帮助我们知道该怎样来使用它！

 

#### 项目实践：

1.使用

```java
@JsonSerialize(using = BudgetSerializer.class)
@JsonDeserialize(using = BudgetDeserializer.class)
private BigDecimal applyBudget;
```

由于字段用于存储金额，所以采用了BigDecimal类。

2.自定义实现类

首先需要自定义序列化及反序列化实现类，继承JsonSerializer<BigDecimal>类和JsonDeserializer<BigDecimal>，由于字段采用BigDecimal，所以泛型也使用BigDecimal。

```
@Slf4j
public class BudgetSerializer extends JsonSerializer<BigDecimal> {
 
    @Override
    public void serialize(BigDecimal s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        BigDecimal format = s;
        if (format != null) {
            // 元转万元
            format = format.divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_DOWN);
            log.debug("元格式化万元：前 {}, 后 {}", s, format);
        }
 
        jsonGenerator.writeNumber(format);
    }
}
```

作用：在返回给前端金额参数的时候，把数据库中的数据由元格式化为万元，就是除10000的操作。


```
@Slf4j
public class BudgetDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            if (jsonParser == null || jsonParser.getText() == null) {
                return null;
            }
            String s = jsonParser.getText();
            BigDecimal format = new BigDecimal(StringUtils.isBlank(s) ? "0" : s);
 
            // 万元转元
            format = format.multiply(new BigDecimal("10000"));
            log.debug("万元格式化元：前 {}, 后 {}", s, format);
            return format;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
```

作用：在后端接收前端金额参数的时候，把前端的数据由万元格式化为元，就是乘10000的操作。

## @JsonIgnore的使用方法及其效果

1. 作用：在json序列化时将java bean中的一些属性忽略掉，序列化和[反序列化](https://so.csdn.net/so/search?q=反序列化&spm=1001.2101.3001.7020)都受影响。

2. 使用方法：一般标记在属性或者方法上，返回的json数据即不包含该属性。

3. 场景模拟：

   需要把一个List<HistoryOrderBean>转换成json格式的数据传递给前台。但实体类中基本属性字段的值都存储在快照属性字段中。此时我可以在业务层中做处理，把快照属性字段的值赋给实体类中对应的基本属性字段。最后，我希望返回的json数据中不包含这两个快照字段，那么在实体类中快照属性上加注解@JsonIgnore，那么最后返回的json数据，将不会包含goodsInfo和extendsInfo两个属性值。

```
public class HistoryOrderBean {

    //基本属性字段
    private String insurantName;
    private String insuranceName;
    private String insurancePrice;
    private String insurancePicture;
    private String insuranceLimit;

    //快照属性字段
    @JsonIgnore
    private String goodsInfo;      //快照基本信息
    @JsonIgnore  
    private String extendsInfo;    //快照扩展属性信息

}
```

4.注解失效：
如果注解失效，可能是因为你使用的是fastJson，尝试使用对应的注解来忽略字段，注解为：@JSONField(serialize = false)，使用方法一样。



# 注解顺序

注意:  注解的名字字符的顺序来的。字符对应的编码小，相当于优先级值小，优先级大，先执行。

但是为了美观, 统一写法顺序

entity

```java
@Data
@Accessors(chain = true)  // //链式, enity没必要, vo可以用
@EqualsAndHashCode(callSuper = true)  //在不加 @EqualsAndHashCode(callSuper = true) 注解的情况下对这两个对象进行比较得到的结果却是 true
@JsonInclude(JsonInclude.Include.NON_NULL) //返回忽略为空的
@ApiModel(value="Client对象", description="客户端表")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @NotBlank(message = "客户端秘钥不能为空")
    @ApiModelProperty(value = "客户端秘钥")
    private String clientSecret;
}

```

ArticleLikeServiceImpl

```java
@Log4j2
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements IArticleLikeService {

    @Autowired
    private IArticleService articleService;
}
```



```java
@RestController
@RequestMapping("/user")
@Log4j2
@Api(tags = "用户服务", value = "/user")
public class ArticleLikeController extends BaseController {

    @Autowired
    private IArticleLikeService articleLikeService;

    @Autowired
    private ArticleRecommendService articleRecommendService;


    @DeleteMapping("/cancel")
    @ApiOperation(value = "取消文章点赞")
    public ApiResponse cancel(@ApiParam("文章id") @NotNull(message = "文章id不能为空") @RequestParam("articleId") Integer articleId) {
        articleLikeService.cancel(articleId);
        articleRecommendService.asyncRefresh(articleId);
        return createResponse();
    }
    
        @PostMapping("/update")
    @ApiOperation(value = "更新用户基本信息", notes = "需要传accessToken，请求的json中id字段必传，更新不为null的项")
    public ApiResponse update(@Validated @RequestBody UpdateUserRequest request) {
        userService.update(request);
        return createResponse();
    }

}
```

