package com.example.demo.confiq;

import com.example.demo.service.UserService;
import com.example.demo.service.impl.AdminServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

//@EnableWebMvc //禁用boot的默认配置
@Configuration //这是一个配置类,给容器中放一个 WebMvcConfigurer 组件，就能自定义底层
public class MyConfig  /*implements WebMvcConfigurer*/ {
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override //配置静态资源
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/static/**")
						.addResourceLocations("classpath:/a/", "classpath:/b/")
						.setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
			}

			@Override //配置拦截器
			public void addInterceptors(InterceptorRegistry registry) {

			}

//			@Override //配置一个能把对象转为yaml的messageConverter
//			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//				converters.add(new MyYamlHttpMessageConverter());
//			}

		};
	}


	/*
	* 人为加注解
	* */
	@Bean("adminServiceImpl")
	public UserService AdminServiceImpl(){
		return  new AdminServiceImpl();
	}

	@Bean
	public UserService UserServiceImpl (){
		return  new UserServiceImpl();
	}
}

