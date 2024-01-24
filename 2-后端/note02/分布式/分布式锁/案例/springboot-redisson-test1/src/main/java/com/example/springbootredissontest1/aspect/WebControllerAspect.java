package com.example.springbootredissontest1.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Janwes
 * @version 1.0.0
 * @package com.janwes.aspect
 * @date 2022/3/20 15:43
 * @description
 */
@Aspect
@Component
public class WebControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebControllerAspect.class);

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(* com.example.springbootredissontest1.controller.*.*(..))")
    public void executeService() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();

        // 获取当前request请求属性对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取请求方法
        String method = request.getMethod();
        // 获取类全路径名
        String clazzName = joinPoint.getTarget().getClass().getName();
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取被调用的请求方法名
        String methodName = methodSignature.getName();
        // 获取传入目标方法的参数对象
        Object[] args = joinPoint.getArgs();
        JSONObject jsonObject = new JSONObject();
        if (Objects.nonNull(args)) {
            // 获取方法参数名
            String[] parameterNames = methodSignature.getParameterNames();
            // 获取方法参数注解
            Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

            for (int var15 = 0; var15 < parameterAnnotations.length; ++var15) {
                Annotation[] parameterAnnotation = parameterAnnotations[var15];
                int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
                String parameterName = parameterNames[paramIndex];
                Object parameterValue = args[paramIndex];
                if (parameterValue instanceof String || parameterValue instanceof Boolean || parameterValue instanceof Integer) {
                    jsonObject.put(parameterName, parameterValue);
                }
                for (int var16 = 0; var16 < parameterAnnotation.length; ++var16) {
                    Annotation annotation = parameterAnnotation[var16];
                    // 如果注解为ResponseBody 请求体
                    if (annotation instanceof RequestBody) {
                        jsonObject.put(parameterName, JSON.toJSONString(parameterValue));
                    }
                }
            }
        }
        // 获取被代理的对象字节码对象
        Class<?> clazz = joinPoint.getTarget().getClass();
        // 获取类上的Api注解和注解值
        Api api = clazz.getAnnotation(Api.class);
        String apiValue = "--";
        if (Objects.nonNull(api)) {
            apiValue = StringUtils.isEmpty(Arrays.toString(api.tags())) ? (StringUtils.isEmpty(api.value()) ? "--" : api.value()) : Arrays.toString(api.tags());
        }
        // 获取请求方法上的ApiOperation注解及注解值
        ApiOperation apiOperation = methodSignature.getMethod().getAnnotation(ApiOperation.class);
        String apiOperationValue = Objects.isNull(apiOperation) ? "--" : apiOperation.value();
        // result的值为被拦截方法的返回值
        Object result = joinPoint.proceed();

        long endTimeMillis = System.currentTimeMillis();
        LOGGER.info("api request  === method:{} url:{} Api:{} ApiOperation:{}", method, url, apiValue, apiOperationValue);
        LOGGER.info("api request  === parameters:{}", jsonObject);
        LOGGER.info("api response === result:{}", JSON.toJSONString(result));
        LOGGER.info("api response === runtime:{}", (endTimeMillis - startTimeMillis) + " ms");
        return result;
    }
}
