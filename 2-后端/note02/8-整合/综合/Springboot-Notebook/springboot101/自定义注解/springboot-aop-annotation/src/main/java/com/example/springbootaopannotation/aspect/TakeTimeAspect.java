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
