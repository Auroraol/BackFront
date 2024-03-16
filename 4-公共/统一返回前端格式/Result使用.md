# 后端响应结果工具类Result

**作用:**

+ 结果工具类，用于后端给前端的响应
+ 统一返回前端格式可以提高代码复用性和维护性
+ 使用泛型，包容各种类型，允许传入自定义code和message
+ 可以快速响应成功或失败

**具体代码见:** 

<img src="Result%E4%BD%BF%E7%94%A8.assets/image-20240125195548668.png" alt="image-20240125195548668" style="zoom:67%;" />

**项目结构**

<img src="Result%E4%BD%BF%E7%94%A8.assets/image-20240125194932672.png" alt="image-20240125194932672" style="zoom: 60%;" />

# 使用注解，统一参数校验

一个需求：实现一个注册用户的功能，在controller 层，他会先进行校验参数，如下：

```
@RestController
@RequestMapping
public class UserController {

    @RequestMapping("addUser")
    public String addUser(UserParam userParam) {

        if (StringUtils.isEmpty(userParam.getUserName())) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(userParam.getPhone())) {
            return "手机号不能为空";
        }
        if (userParam.getPhone().length() > 11) {
            return "手机号不能超过11";
        }
        if (StringUtils.isEmpty(userParam.getEmail())) {
            return "邮箱不能为空";
        }

        //省略其他参数校验

        //todo 插入用户信息表
        return "SUCCESS";
    }

}
```

一个需求：编辑用户信息。实现编辑用户信息前，也是先校验信息，如下：

```
    @RequestMapping("editUser")
    public String editUser(UserParam userParam) {

        if (StringUtils.isEmpty(userParam.getUserName())) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(userParam.getPhone())) {
            return "手机号不能为空";
        }
        if (userParam.getPhone().length() > 11) {
            return "手机号不能超过11";
        }
        
        if (StringUtils.isEmpty(userParam.getEmail())) {
            return "邮箱不能为空";
        }

        //省略其他参数校验

        //todo 编辑用户信息表
        return "SUCCESS";
    }
```

可以使用注解的方式，来进行参数校验，这样代码更加简洁，也方便统一管理。

实际上， `spring boot`有个`validation`的组件，我们可以拿来即用。引入这个包即可：

```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
 </dependency>
```

引入包后，参数校验就非常简洁啦，如下：

```java
public class UserParam {

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "手机号不能为空")
    @Max(value = 11)
    private String phone;

    @NotNull(message = "邮箱不能为空")
    private String email;
```

然后在`UserParam`参数对象中，加入`@Validated`注解，把错误信息接收到`BindingResult`对象，代码如下：

```java
    @RequestMapping("addUser")
    public String addUser(@Validated UserParam userParam, BindingResult result) {
        
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            return fieldErrors.get(0).getDefaultMessage();
        }

        //todo 插入用户信息表
        return "SUCCESS";
    }
```

# 统一响应格式封装

## 统一管理响应状态码

 **添加枚举类**

```java
package com.example.template.enums;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 11:03
 * 统一管理响应状态码
 */
public enum ResponseCodeEnum {
	SUCCESS(200000,"响应成功"),
	//后端服务异常以500开头
	SYSTEM_ERROR(500000,"服务异常，请稍后再试"),
	OPERATION_ERROR(500001,"操作失败，请稍后再试"),
	//后端服务异常以400开头
	DATA_PARAM_ERROR(400000,"传入参数错误"),
	ACCOUNT_ALREADY_EXISTS(400001,"账号已存在，请登录"),
	ACCOUNT_NOT_FOUND(400002,"账号不存在"),
	ACCOUNT_LOCK(400003,"账号已锁定，请联系管理员解锁"),
	ACCOUNT_ERROR(400004,"账户密码不匹配"),
	TOKEN_ERROR(401000,"token 已失效,请重新登录");

	private final int code;
	private final String message;

	ResponseCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
```

## 通用的响应实体类

**统一返回格式**

```java
package com.example.template.common.vo;

import com.example.template.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 11:02
 * 通用的响应实体类
 */
@Data
@NoArgsConstructor
public class ResponseResult<T>{
	private int code;
	private T data;
	private String message;

	/**
	 * 响应成功的方法
	 *
	 * @param data 获取的数据
	 * @param <T>  泛型类型
	 * @return 响应实体
	 */
	public static <T> ResponseResult<T> success(T data) {
		return success(data, ResponseCodeEnum.SUCCESS.getMessage());
	}

	/**
	 * 响应成功的方法，可以自定义提示信息
	 *
	 * @param data    获取的数据
	 * @param message 提示信息
	 * @param <T>     泛型类型
	 * @return 响应实体
	 */
	public static <T> ResponseResult<T> success(T data, String message) {
		ResponseResult<T> result = new ResponseResult<>();
		result.setData(data);
		result.setCode(ResponseCodeEnum.SUCCESS.getCode());
		result.setMessage(message);
		return result;
	}


	/**
	 * 响应失败的方法
	 * @param code
	 * @param message
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseResult<T> fail(int code, String message) {
		ResponseResult<T> result = new ResponseResult<>();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	private static <T> ResponseResult<T> fail(ResponseCodeEnum responseCode) {
		ResponseResult<T> result = new ResponseResult<>();
		result.setCode(responseCode.getCode());
		result.setMessage(responseCode.getMessage());
		return result;
	}

	// 服务异常
	public static <T> ResponseResult<T> systemError() {
		return fail(ResponseCodeEnum.SYSTEM_ERROR);
	}

	// 操作失败
	public static <T> ResponseResult<T> operationError() {
		return fail(ResponseCodeEnum.OPERATION_ERROR);
	}

	// 传入参数错误
	public static <T> ResponseResult<T> dataParamError() {
		return fail(ResponseCodeEnum.DATA_PARAM_ERROR);
	}

	//账号已存在
	public static <T> ResponseResult<T> accountAlreadyExists() {
		return fail(ResponseCodeEnum.ACCOUNT_ALREADY_EXISTS);
	}

	//账号不存在
	public static <T> ResponseResult<T> accountNotFoundError() {
		return fail(ResponseCodeEnum.ACCOUNT_NOT_FOUND);
	}

	//账号己锁定
	public static <T> ResponseResult<T> accountLock() {
		return fail(ResponseCodeEnum.ACCOUNT_LOCK);
	}

	// 账户密码不匹配
	public static <T> ResponseResult<T> accountError( ) {
		return fail(ResponseCodeEnum.ACCOUNT_ERROR);
	}

	// token已失效,请重新登录
	public static <T> ResponseResult<T> tokenError( ) {
		return fail(ResponseCodeEnum.TOKEN_ERROR);
	}
}
```

## 使用Spring提供的ResponseBodyAdvice来实现统一响应格式的封装

```java
package com.example.template.handler;

import com.example.template.common.vo.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 11:04
 * 使用Spring提供的ResponseBodyAdvice来实现统一响应格式的封装
 * 重写了其中的supports和beforeBodyWrite方法。
 * 其中，supports方法用于判断当前返回类型是否需要进行处理，beforeBodyWrite方法则用于对返回结果进行封装
 */
@RestControllerAdvice
@Slf4j
public class RestResponseBodyAdviceHandler implements ResponseBodyAdvice<Object> {
	@Resource
	private ObjectMapper objectMapper;
	private final String stringConverter="org.springframework.http.converter.StringHttpMessageConverter";
	/**
	 * true:代表支持我们在响应前端的时候做一些处理(调用beforeBodyWrite方法)
	 * false:不支持
	 * @param returnType
	 * @param converterType
	 * @return
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		log.info("supports:{}",returnType.getDeclaringClass().getName());
		/**
		 * 排除swagger-ui请求返回数据增强
		 */
		return !returnType.getDeclaringClass().getName().contains("springfox");
	}

	@SneakyThrows
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {

		/**
		 * 当接口返回到类型消息转换器是StringHttpMessageConverter,才需要把它转换成string
		 */
		if(stringConverter.equalsIgnoreCase(selectedConverterType.getName())){
			HttpHeaders headers= response.getHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			return objectMapper.writeValueAsString(ResponseResult.success(body));
		}
		/**
		 *
		 * 如果响应结果已经是DataResult类型，则直接返回
		 */
		if(body instanceof ResponseResult){
			return body;
		}

		return ResponseResult.success(body);
	}
}
```

## **测试效果**

```java
	@GetMapping("/test1")
	public Map<String, Object> test1(){
		Map<String, Object> data = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		return data;
	}
	/*
	// 不使用ResponseBodyAdvice进行统一响应格式封装, 可以使用以下方式
	@GetMapping("/test1")
	public Map<String, Object> test1(){
		Map<String, Object> data1 = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		return ResponseResult.success(data1);
	}
	
	@GetMapping("/test1")
	public ResponseResult<User> test1(){
		return ResponseResult.success(new User());
	}
	* */
	@GetMapping("/test2")
	public User test2(){
		User user = new User();
		user.setId(100);
		user.setName("jindao");
		return user;
	}

```

![image-20240125153349489](Result%E4%BD%BF%E7%94%A8.assets/image-20240125153349489.png)

![image-20240125153332894](Result%E4%BD%BF%E7%94%A8.assets/image-20240125153332894.png)

# 统一异常处理封装

## 自定义的异常类

```java
package com.example.template.exception;

import com.example.template.common.vo.ResponseResult;
import com.example.template.enums.ResponseCodeEnum;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 14:02
 * 自定义的异常类-业务中发生的异常
 */
public class BizException extends RuntimeException {
	private int code;

	public BizException(ResponseCodeEnum responseCode) {
		super(responseCode.getMessage());
		this.code = responseCode.getCode();
	}

//	public BizException(ResponseResult responseResult) {
//		super(responseResult.getMessage());
//		this.code = responseResult.getCode();
//	}
	// Getter 方法
	public int getCode() {
		return code;
	}
}

```

## 统一异常处理

```java
package com.example.template.handler.exception;

import com.example.template.common.vo.ResponseResult;
import com.example.template.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 13:47
 * 统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	//如果抛出BizException的话, 调用
	@ExceptionHandler(BizException.class)
	public ResponseResult BizExceptionHandler(BizException e) {
		log.error("业务异常：{}", e.getMessage());
		return ResponseResult.fail(e.getCode(), e.getMessage());
	}

	//如果抛出Exception的话, 调用
	@ExceptionHandler(Exception.class)
	public ResponseResult exceptionHandler(Exception e) {
		log.error("系统异常：{}", e.getMessage());
		return ResponseResult.systemError();
	}
}

```

## **测试效果**

```java
     /**
	 * 异常处理
	 */

	// 方式1: 直接返回错误
	@GetMapping("/test3")
	public ResponseResult test3(){
		return ResponseResult.accountAlreadyExists();
	}

	// 方式2: 进行统一异常处理封装, 通过throw new xxx进行调用
	@GetMapping("/test4")
	public Map<String, Object> test4(){
		Map<String, Object> data = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		int a = 1;
		if (a==1) {
			throw new BizException(ResponseCodeEnum.ACCOUNT_LOCK);
		}
		return data;
	}
```

![image-20240125153502967](Result%E4%BD%BF%E7%94%A8.assets/image-20240125153502967.png)

![image-20240125153520913](Result%E4%BD%BF%E7%94%A8.assets/image-20240125153520913.png)

# 工具类

> **作用:  用于任何地方发起响应数据**
>
> 实现了在任何地方方便地输出响应数据到HttpServletResponse中的功能。

```java
package com.lfj.blog.utils;

import com.alibaba.fastjson2.JSON;
import com.lfj.blog.common.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2024-03-10 23:14
 * response 输出响应工具, 用于任何地方发起响应数据, 不需要PostMapping()等等
 * 实现了在任何地方方便地输出响应数据到HttpServletResponse中的功能。
 */
@Slf4j
public class ResponseUtil {

	static final String ENCODING = "UTF-8";
	static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * 输出前端内容以及状态指定
	 *
	 * @param response
	 * @param status
	 * @param content
	 */
	public static void output(HttpServletResponse response, Integer status, String content) {
		ServletOutputStream servletOutputStream = null;
		try {
			response.setCharacterEncoding(ENCODING);
			response.setContentType(CONTENT_TYPE);
			response.setStatus(status);
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(content.getBytes());
		} catch (Exception e) {
			log.error("response output error: ", e);
		} finally {
			if (servletOutputStream != null) {
				try {
					servletOutputStream.flush();
					servletOutputStream.close();
				} catch (IOException e) {
					log.error("response output IO close error:", e);
				}
			}
		}
	}


	/**
	 * response 输出JSON
	 *
	 * @param response
	 * @param status    response 状态
	 * @param result
	 */
	public static void output(HttpServletResponse response, Integer status, ResponseResult result) {
		response.setStatus(status);
		output(response, result);
	}


	/**
	 * response 输出JSON
	 *
	 * @param response
	 * @param result
	 */
	public static void output(HttpServletResponse response, ResponseResult result) {
		ServletOutputStream servletOutputStream = null;
		try {
			response.setCharacterEncoding(ENCODING);
			response.setContentType(CONTENT_TYPE);
			servletOutputStream = response.getOutputStream();
//			System.out.println(result.getMessage());
			servletOutputStream.write(JSON.toJSONString(result).getBytes());
			log.info("Response output successful");
		} catch (Exception e) {
			log.error("response output error:", e);
		} finally {
			if (servletOutputStream != null) {
				try {
					servletOutputStream.flush();
					servletOutputStream.close();
				} catch (IOException e) {
					log.error("response output IO close error:", e);
				}
			}
		}
	}

}

```

使用

```java
public void handle(HttpServletRequest httpServletRequest, HttpServletResponse 		httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
	ResponseUtil.output(httpServletResponse, ResponseResult.noPermission());
}
```

