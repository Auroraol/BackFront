// package com.zhang.musicrs.auth;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
// /**
//  * @author: Zhang Chaojie
//  * @comments:
//  * @since Date： 2022/11/27 17:52:32
//  */
// @Configuration
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//     // /**
//     //  * WebSecurityConfigurerAdapter过期问题：https://blog.csdn.net/qiaohao0206/article/details/125571568
//     //  * <p>
//     //  * 使用org.springframework.security.web.SecurityFilterChain Bean 配置 HttpSecurity ，使用 WebSecurityCustomizer org.springframework.context.annotation.Bean 配置 WebSecurity
//     //  */
//     //
//     // @Override
//     // protected void configure(HttpSecurity http) throws Exception {
//     //     http.authorizeRequests()
//     //             .anyRequest().authenticated()
//     //             .and()
//     //             // 使用表单登录
//     //             .formLogin().permitAll();
//     // }
//     //
//     // @Override
//     // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//     //     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//     //     // 直接创建一个用户，懒得搞数据库了
//     //     auth.inMemoryAuthentication()
//     //             .passwordEncoder(encoder)
//     //             .withUser("test").password(encoder.encode("123456")).roles("USER");
//     // }
//     //
//     // /**
//     //  * 这里需要将AuthenticationManager注册为Bean，在OAuth配置中使用
//     //  */
//     // @Bean
//     // @Override
//     // public AuthenticationManager authenticationManagerBean() throws Exception {
//     //     return super.authenticationManagerBean();
//     // }
//     //
//     // @Bean
//     // @Override
//     // public UserDetailsService userDetailsServiceBean() throws Exception {
//     //     return super.userDetailsServiceBean();
//     // }
//     //
//     // // 使用JWT
//     //
//     // /**
//     //  * Token 转换器，将其转换为JWT
//     //  */
//     // @Bean
//     // public JwtAccessTokenConverter tokenConverter() {
//     //     JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//     //     // 对称密钥，资源服务器需要相同的对称密钥
//     //     converter.setSigningKey("lbwnb");
//     //     return converter;
//     // }
//     //
//     // /**
//     //  * token存储方式修改为JWT存储
//     //  */
//     // @Bean
//     // public TokenStore tokenStore(JwtAccessTokenConverter converter) {
//     //     return new JwtTokenStore(converter);
//     // }
// }
