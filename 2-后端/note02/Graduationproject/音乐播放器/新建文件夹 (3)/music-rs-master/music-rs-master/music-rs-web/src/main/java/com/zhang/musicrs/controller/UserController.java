package com.zhang.musicrs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhang.musicrs.service.IUserService;
import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.entity.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhangChaojie
 * @Description: TODO(普通用户的Controller层代码)
 * @date 2022/4/26 20:02
 */
@RestController
public class UserController {
    // 用户服务
    @Autowired
    private IUserService iUserService;
    // 日志管理
    // getLogger是使用静态绑定的ILoggerFactory实例返回与作为参数传递的类相对应的记录器。这里是从LoggerFactory获取一个和HelloController类绑定的日志记录器
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/hello")
    public String hello() {
        return "hello,音乐播放系统";
    }

    /**
     * 用户登录
     *
     * @param user 前端数据封装成用户，查询数据验证
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    // 使用@RequestBody，前端传过来的数据必须是json格式
    public Result userLogin(@RequestBody UserDO user) {
        logger.info("POST /login");
        System.out.println(user);
        return iUserService.userLogin(user);
    }

    /**
     * 用户注册
     *
     * @param user 前端数据封装成用户，添加到数据库中
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result addUser(@RequestBody UserDO user) {
        logger.info("POST /register");
        System.out.println(user);
        return iUserService.addUser(user);
    }

    /**
     * 相似音乐好友
     *
     * @param uid 前端传过来的用户id
     * @return 相似音乐好友数据，json格式
     */
    @RequestMapping(value = "/recommendUsers", method = RequestMethod.POST)
    public Result getUserRecommendUsers(@RequestBody String uid) {
        logger.info("POST /recommendUsers");
        System.out.println("好友推荐，当前用户id：" + uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iUserService.getRecommendUsers(uid_str);
    }

    /**
     * 音乐推荐
     *
     * @param uid 前端传过来的用户id
     * @return 相似音乐好友数据，json格式
     */
    @RequestMapping(value = "/recommendSongs", method = RequestMethod.POST)
    public Result getUserRecommendSongs(@RequestBody String uid) {
        logger.info("POST /recommendSongs");
        System.out.println("音乐推荐，当前用户id：" + uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iUserService.getRecommendSongs(uid_str);
    }

    /**
     * 更新用户信息
     *
     * @param user 要更新的用户信息
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestBody UserDO user) {
        logger.info("POST /updateUser");
        System.out.println(user);
        return iUserService.updateUser(user);
    }

    /**
     * 更新密码
     *
     * @param userInfo 用户部分信息，包括用户id，旧密码，新密码
     * @return
     */
    @RequestMapping(value = "/updatePw", method = RequestMethod.POST)
    public Result updatePs(@RequestBody String userInfo) {
        logger.info("POST /updatePw");
        System.out.println(userInfo);
        // 解析json数据
        JSONObject user_json = JSON.parseObject(userInfo);
        String uid = user_json.getString("uid");
        String oldPw = user_json.getString("oldPw");
        String newPw = user_json.getString("newPw");
        // 更新密码
        return iUserService.updatePw(uid, oldPw, newPw);
    }
}
