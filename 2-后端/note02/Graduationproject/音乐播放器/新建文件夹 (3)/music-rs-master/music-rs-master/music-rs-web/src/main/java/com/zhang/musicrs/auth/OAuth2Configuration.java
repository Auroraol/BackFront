// package com.zhang.musicrs.auth;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
// import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//
// /**
//  * @author: Zhang Chaojie
//  * @comments:
//  * @since Date： 2022/11/27 19:34:13
//  */
// // 开启验证服务器
// @EnableAuthorizationServer
// @Configuration
// public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {
//
//     // @Resource
//     // private AuthenticationManager manager;
//     //
//     // /**
//     //  * 密码编码器
//     //  */
//     // private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//     //
//     // /**
//     //  * 这个方法是对客户端进行配置，一个验证服务器可以预设很多个客户端，
//     //  * 之后这些指定的客户端就可以按照下面指定的方式进行验证
//     //  *
//     //  * @param clients 客户端配置工具
//     //  */
//     // @Override
//     // public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//     //     // 这里我们直接硬编码创建，当然也可以像Security那样自定义或是使用JDBC从数据库读取
//     //     clients.inMemory()
//     //             // 客户端名称，随便起就行
//     //             .withClient("web")
//     //             // 秘钥，只与客户端分享的secret，随便写，但是注意要加密
//     //             .secret(encoder.encode("654321"))
//     //             // 自动审批，这里关闭，要的就是一会体验那种感觉
//     //             .autoApprove(false)
//     //             // 授权范围，这里我们使用全部all
//     //             .scopes("book", "user", "borrow")
//     //             // 重定向的一个地址，如果没登录则跳转到登录页面
//     //             .redirectUris("http://localhost:8082/login", "http://localhost:8201/login", "http://localhost:8301/login")
//     //             /**
//     //              * 授权模式，一共支持5种，除了之前我们介绍的四种之外，还有一个刷新Token的模式；
//     //              * 这里我们直接把五种都写上，方便一会实验，当然各位也可以单独只写一种一个一个进行测试
//     //              * 现在指定的客户端就支持这五种类型的授权方式了
//     //              */
//     //             .authorizedGrantTypes("client_credentials", "password", "implicit", "authorization_code", "refresh_token");
//     //
//     // }
//     //
//     // @Override
//     // public void configure(AuthorizationServerSecurityConfigurer security) {
//     //     // 编码器设定为BCryptPasswordEncoder
//     //     security.passwordEncoder(encoder)
//     //             // 允许客户端使用表单验证，一会我们POST请求中会携带表单信息
//     //             .allowFormAuthenticationForClients()
//     //             // 允许所有的Token查询请求
//     //             .checkTokenAccess("permitAll()");
//     // }
//     //
//     // @Resource
//     // UserDetailsService service;
//     //
//     // // @Override
//     // // public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//     // //     // 由于SpringSecurity新版本的一些底层改动，这里需要配置一下authenticationManager，才能正常使用password模式
//     // //     endpoints.userDetailsService(service)
//     // //             .authenticationManager(manager);
//     // // }
//     //
//     // // 使用JWT存储token
//     //
//     // @Resource
//     // TokenStore tokenStore;
//     //
//     // @Resource
//     // JwtAccessTokenConverter tokenConverter;
//     //
//     // /**
//     //  * 这里对AuthorizationServerTokenServices进行一下配置
//     //  */
//     // private AuthorizationServerTokenServices serverTokenServices() {
//     //     DefaultTokenServices services = new DefaultTokenServices();
//     //     // 允许Token刷新
//     //     services.setSupportRefreshToken(true);
//     //     // 添加刚刚的TokenStore
//     //     services.setTokenStore(tokenStore);
//     //     // 添加Token增强，其实就是JwtAccessTokenConverter，增强是添加一些自定义的数据到JWT中
//     //     services.setTokenEnhancer(tokenConverter);
//     //     return services;
//     // }
//     //
//     // /**
//     //  * 修改配置，使用JWT
//     //  */
//     // @Override
//     // public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//     //     // 设定为刚刚配置好的AuthorizationServerTokenServices
//     //     endpoints.tokenServices(serverTokenServices())
//     //             .userDetailsService(service)
//     //             .authenticationManager(manager);
//     // }
// }
