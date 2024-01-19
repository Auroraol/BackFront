package com.dynamic.aspect;

import com.dynamic.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 实现@DS注解的AOP切面
 *
 * @date 2023/11/27 11:02
 */
@Aspect      //定义切面类
@Component
@Slf4j
public class DSAspect {

    /**定义切入点----以 aspect 包下带有@DS注解的方法 */
    @Pointcut("@annotation(com.dynamic.aspect.DS)")
    public void dynamicDataSource() {
    }

    // 环绕增强
    @Around("dynamicDataSource()")
    public Object datasourceAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature(); //用于获取方法的详细信息
        Method method = signature.getMethod();   //从方法签名获取具体的 Method 对象。
        DS ds = method.getAnnotation(DS.class);  //通过反射获取该方法上的 DS 注解，如果存在的话。
        if (Objects.nonNull(ds)) {
            //如果方法上存在 DS 注解，则切换数据源为注解指定的数据源，使用
            DataSourceContextHolder.setDataSource(ds.value());
        }
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.removeDataSource();
        }
    }
}
/*
这段代码的作用是在执行被 dynamicDataSource() 切入点匹配的方法前后，根据方法上的 DS 注解来切换数据源。
这对于实现动态数据源切换的场景非常有用，例如在多数据源的环境中动态选择使用哪个数据源。
*/




