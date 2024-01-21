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
//        try {
//            String luaScript = buildLuaScript();
//            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
//            Number count = (Number) redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
//            logger.info("Access try count is {} for name={} and key = {}", count, name, key);
//            if (count != null && count.intValue() <= limitCount) {
//                return proceedingJoinPoint.proceed();
//            } else {
//                throw new RuntimeException("You have been dragged into the blacklist");
//            }
//        } catch (Throwable e) {
//            if (e instanceof RuntimeException) {
//                throw new RuntimeException(e.getLocalizedMessage());
//            }
//            throw new RuntimeException("server exception");
//        }


    }
//        /**
//     * @param pjp 切点对象，用于获取被注解方法的信息。
//     * @return 被拦截方法的执行结果。
//     * @description 切面方法，处理被 @Limit 注解标记的方法的限流逻辑。 环绕增强
//     * @date 2020/4/8 13:04
//     */
//    @Around("execution(public * *(..)) && @annotation(com.springboot101.limit.aspect.RateLimter)")
//    public Object interceptor(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        RateLimter limitAnnotation = method.getAnnotation(RateLimter.class);
//        LimitType limitType = limitAnnotation.limitType();
//        String name = limitAnnotation.name();
//        String key;
//        int limitPeriod = limitAnnotation.period();
//        int limitCount = limitAnnotation.count();
//
//        /**
//         * 根据限流类型获取不同的key ,如果不传我们会以方法名作为key
//         */
//        switch (limitType) {
//            case IP:
//                key = getIpAddress();
//                break;
//            case CUSTOMER:
//                key = limitAnnotation.key();
//                break;
//            default:
//                key = StringUtils.upperCase(method.getName());
//        }
//
//        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnnotation.prefix(), key));
//        try {
//            String luaScript = buildLuaScript();
//            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
//            Number count = (Number) limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
//            logger.info("Access try count is {} for name={} and key = {}", count, name, key);
//            if (count != null && count.intValue() <= limitCount) {
//                return pjp.proceed();
//            } else {
//                throw new RuntimeException("You have been dragged into the blacklist");
//            }
//        } catch (Throwable e) {
//            if (e instanceof RuntimeException) {
//                throw new RuntimeException(e.getLocalizedMessage());
//            }
//            throw new RuntimeException("server exception");
//        }
//    }

//    /**
//     * 构建 Redis Lua 限流脚本。
//     *
//     * @return Lua 限流脚本字符串。
//     */
//    public String buildLuaScript() {
//        StringBuilder lua = new StringBuilder();
//        lua.append("local c");
//        lua.append("\nc = redis.call('get',KEYS[1])");
//        // 调用不超过最大值，则直接返回
//        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
//        lua.append("\nreturn c;");
//        lua.append("\nend");
//        // 执行计算器自加
//        lua.append("\nc = redis.call('incr',KEYS[1])");
//        lua.append("\nif tonumber(c) == 1 then");
//        // 从第一次调用开始限流，设置对应键值的过期
//        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
//        lua.append("\nend");
//        lua.append("\nreturn c;");
//        return lua.toString();
//    }

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