package com.zhang.musicrs.config;

import com.zhang.musicrs.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhangChaojie
 * @Description: TODO(Web配置)
 * @date 2022/4/24 12:42
 */

/**
 * 拦截器利用AOP思想实现，是横切进去的。拦截器只会拦截访问的控制器方法， 如果访问的是jsp/html/css/image/js是不会进行拦截的
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // =========================== 注册拦截器 ==========================
    // 使用构造器注入
    private final LoginInterceptor loginInterceptor;

    @Autowired
    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截路径（登录路径等等）
        List<String> asList = Arrays.asList("/register", "/login", "/hello","/swagger-ui/*");
        registry.addInterceptor(loginInterceptor).excludePathPatterns(asList);
    }
    // =========================== 注册拦截器 ==========================
}
