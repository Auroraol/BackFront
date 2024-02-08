

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
@Around -- 环绕增强，相当于MethodInterceptor 

### **AOP五种通知工作**

前置通知：在目标方法调用之前执行，可以获得切入点信息；
后置通知：在目标方法执行后执行，目标方法有异常不执行；
异常通知：在目标方法抛出异常时执行，可以获取异常信息；
最终通知：在目标方法执行后执行，无论是否有异常都执行；
环绕通知：最强大的通知类型，在目标方法执行前后操作，可以阻止目标方法执行。

### 方法1:  扫描包的方式

![image-20240119205313618](REDEME.assets/image-20240119205313618.png)

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

![image-20240119204202614](REDEME.assets/image-20240119204202614.png)

控制台打印

![image-20240119204027727](REDEME.assets/image-20240119204027727.png)

### 方法2: 自定义注解方式

![image-20240119212632540](REDEME.assets/image-20240119212632540.png)

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

![image-20240119213124521](REDEME.assets/image-20240119213124521.png)

控制台打印

![image-20240119212558464](REDEME.assets/image-20240119212558464.png)

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