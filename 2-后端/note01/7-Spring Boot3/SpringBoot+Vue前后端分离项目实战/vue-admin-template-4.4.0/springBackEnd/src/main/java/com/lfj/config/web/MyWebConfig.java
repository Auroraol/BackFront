package com.lfj.config.web;

import com.lfj.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: LFJ
 * @Date: 2023-09-26 21:28
 */

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
	@Autowired
	private JwtValidateInterceptor jwtValidateInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
		registration.addPathPatterns("/**") //拦截-->找自定义拦截器
				.excludePathPatterns(
						"/user/login",    //放行不需要拦截的
						"/user/info",
						"/user/logout",
						"/error",
						"/swagger-ui/**",             // 放行swagger
						"/swagger-resources/**",
						"/v3/**");
	}
}
