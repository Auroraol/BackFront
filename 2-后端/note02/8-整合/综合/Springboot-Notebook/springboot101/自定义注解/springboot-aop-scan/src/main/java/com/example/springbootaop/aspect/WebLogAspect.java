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
