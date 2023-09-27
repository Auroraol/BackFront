//package com.example.jwttest.config;
//
//import org.apache.catalina.Realm;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
///**
// * @Author: LFJ
// * @Date: 2023-09-26 13:24
// */
//@Configuration
//public class ShiroConfig {
//	//  shiro中的过滤器 交给spring容器管理
//	@Bean
//	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
//		System.out.println("securityManager = " + securityManager);
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		//配置拦截的规则
//		Map<String, Filter> filters = new HashMap<>();
//		filters.put("jwt", new JwtAuthenticationFilter());
//		shiroFilterFactoryBean.setFilters(filters);
//		LinkedHashMap<String, String> map = new LinkedHashMap<>();
//		//放行登录请求  anon  可匿名访问
//		map.put("/user/login", "anon");
//		map.put("/user/add", "anon");
//		map.put("/register.html", "anon");
//		// 放行静态资源
//		map.put("/dist/**", "anon");
//		// 放行验证码请求
//		map.put("/captcha/getCaptcha", "anon");
//		//已登录或“记住我”的用户才能访问
//		map.put("/**", "user");
//		//放行所有携带token请求的访问
//		map.put("/**", "jwt");
//		// 设置默认的登录页
//		shiroFilterFactoryBean.setLoginUrl("/login.html");
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//		return shiroFilterFactoryBean;
//	}
//
//	// 将安全管理器交由spring管理
//	@Bean
//	public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
//		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//
//		// 设置一周免登录
//		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//		SimpleCookie rmbme = new SimpleCookie("rmbme");
//		rmbme.setMaxAge(60*60*24*7);
//		cookieRememberMeManager.setCookie(rmbme);
//		defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
//
//		defaultWebSecurityManager.setRealm(realm);
//		return defaultWebSecurityManager;
//	}
//
//	// 将自定义域对象 交给spring管理
//	@Bean
//	public Realm realm() {
//		CustomerRealm customerRealm = new CustomerRealm();
//
//		//设置凭证匹配器  MD5
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
//		hashedCredentialsMatcher.setHashIterations(20);
//		customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);
//
//		// 开启shiro的缓存  开启全局缓存
//		customerRealm.setCachingEnabled(true);
//		//将认证和授权缓存写入redis   分布式缓存
//		customerRealm.setCacheManager(new RedisCacheManager());
//		// 设置认证缓存
//		customerRealm.setAuthenticationCachingEnabled(true);
//		customerRealm.setAuthenticationCacheName("authentication");
//
//		// 设置授权缓存
//		customerRealm.setAuthorizationCachingEnabled(true);
//		customerRealm.setAuthorizationCacheName("authorization");
//
//		return customerRealm;
//	}
//}
