package com.zhang.musicrs.auth;// package com.zhang.authservice.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
//
// import javax.annotation.Resource;
//
// /**
//  * @author: Zhang Chaojie
//  * @comments: SpringBoot 2.7 版本之后 WebSecurityConfigurerAdapter 过时，改用新的方式实现Security配置，主要是 HttpSecurity 和 WebSecurity
//  * @since Date： 2022/11/27 18:46:33
//  */
// @Configuration
// @EnableWebSecurity
// public class SecurityConfigurationV2 {
//     @Resource
//     AuthenticationConfiguration authenticationConfiguration;
//
//     /**
//      * 创建有权限的用户，这个其实应该从数据库中读取，这里只是模拟
//      */
//     @Bean
//     UserDetailsService userDetailsService() {
//         InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//         users.createUser(User.withUsername("test").password(passwordEncoder().encode("123456")).roles("USER").build());
//         return users;
//     }
//
//     /**
//      * HttpSecurity 相关配置
//      */
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//         httpSecurity.authorizeRequests()
//                 .anyRequest().authenticated()
//                 .and()
//                 // 使用表单登录
//                 .formLogin().permitAll();
//         return httpSecurity.build();
//     }
//
//     /**
//      * WebSecurity相关配置
//      */
//     @Bean
//     public WebSecurityCustomizer webSecurityCustomizer() {
//         // Lambda表达式
//         // return web -> web.ignoring().antMatchers("/login");
//
//         // 普通模式
//         return new WebSecurityCustomizer() {
//             @Override
//             public void customize(WebSecurity web) {
//                 web.ignoring().antMatchers("/hello");
//             }
//         };
//     }
//
//     /**
//      * 这里需要将AuthenticationManager注册为Bean，在OAuth配置中使用
//      */
//     @Bean
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }
//
//     /**
//      * 密码编码器，强散列哈希加密实现
//      */
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }

