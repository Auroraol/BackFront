package com.github.xiaolyuh.annotation;

import com.github.xiaolyuh.support.CacheMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示调用的方法（或类中的所有方法）的结果是可以被缓存的。
 * 当该方法被调用时先检查缓存是否命中，如果没有命中再调用被缓存的方法，并将其返回值放到缓存中。
 * 这里的value和key都支持SpEL 表达式
 *
 * @author olafwang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {

    /**
     * 别名是 {@link #cacheNames}.
     *
     * @return String[]
     */
    @AliasFor("cacheNames")
    String[] value() default {};

    /**
     * 缓存名称，支持SpEL表达式
     *
     * @return String[]
     */
    @AliasFor("value")
    String[] cacheNames() default {};

    /**
     * 描述
     *
     * @return String
     */
    String depict() default "";

    /**
     * 缓存key，支持SpEL表达式
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     *
     * @return String
     */
    String key() default "";

    /**
     * 生成缓存的条件，默认空全部缓存。支持SpEL表达式
     * @return
     */
    String condition() default "";

    /**
     * 缓存模式(只使用一级缓存或者二级缓存)
     *
     * @return boolean
     */
    CacheMode cacheMode() default CacheMode.ALL;

    /**
     * 一级缓存配置
     *
     * @return FirstCache
     */
    FirstCache firstCache() default @FirstCache();

    /**
     * 二级缓存配置
     *
     * @return SecondaryCache
     */
    SecondaryCache secondaryCache() default @SecondaryCache();
}
