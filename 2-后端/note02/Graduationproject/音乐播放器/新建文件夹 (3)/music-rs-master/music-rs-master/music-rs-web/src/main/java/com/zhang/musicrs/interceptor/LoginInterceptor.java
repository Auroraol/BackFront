package com.zhang.musicrs.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author ZhangChaojie
 * @Description: TODO(拦截器)
 * @date 2022/5/11 11:17
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 日志管理
    public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    // redis工具类
    // private final RedisUtils redisUtils;

    // @Autowired
    // public LoginInterceptor(RedisUtils redisUtils) {
    //     this.redisUtils = redisUtils;
    // }

    // // 这个方法是在访问接口之前执行的，我们只需要在这里写验证登录状态的业务逻辑，就可以在用户调用指定接口之前验证登录状态了
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     logger.info("进入LoginInterceptor拦截器");
    //     //不拦截路径（登录路径等等）
    //     List<String> asList = Arrays.asList("/register", "/login", "/hello","/adminLogin");
    //     String uri = request.getRequestURI();
    //     System.out.println(uri);
    //     //1.设置放行路径
    //     if (asList.contains(uri)) {
    //         return true;
    //     }

    //     //2.拿到请求头里面的token（如果是第一次登录，那么是没有请求头的）
    //     String token = request.getHeader("u-token");
    //     if (token == null || "".equals(token) || "c4b01c09-af0d-4667-8071-5cece0941c4a".equals(token)) {
    //         response.setContentType("application/json; charset=utf-8");
    //         String failMsg = JSON.toJSONString(ResultUtil.fail(400, "没有验证信息或已失效"));
    //         //2.1 拦截请求并返回信息给前台 （前台后置拦截器就是根据这里面返回的json数据，来判读并跳转到登录界面）
    //         response.getWriter().print(failMsg);
    //         response.getWriter().flush();
    //         logger.info("没有验证信息或已失效");
    //         return false;
    //     }

    //     //3、如果有token，那么就根据这个token从redis查询登录用户信息，如果redis里面还没过期，那么就正常放行，没有就进行拦截，并返回信息，叫他重新登录
    //     String tokenUser = redisUtils.get(token);
    //     if (tokenUser == null) {
    //         response.setContentType("application/json; charset=utf-8");
    //         // 要返回的提示信息
    //         String failMsg = JSON.toJSONString(ResultUtil.fail(400, "验证信息或已失效"));
    //         //3.1 拦截请求并返回信息给前台 （前台后置拦截器就是根据这里面返回的json数据，来判读并跳转到登录界面）
    //         response.getWriter().print(failMsg);
    //         response.getWriter().flush();
    //         return false;
    //     }

    //     //4.如果没有过期，那么就重新将token和登录用户信息存到redis
    //     redisUtils.set(token, tokenUser, 60 * 30);

    //     return true;
    // }
}
