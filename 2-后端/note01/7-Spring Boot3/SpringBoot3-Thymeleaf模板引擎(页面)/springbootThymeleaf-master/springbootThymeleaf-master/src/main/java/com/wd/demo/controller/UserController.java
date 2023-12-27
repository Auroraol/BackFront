package com.wd.demo.controller;

import com.wd.demo.db1.service.IUserService;
import com.wd.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    IUserService userService;


    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 判断能否成功登录
     * @param request
     * @return
     */
    @RequestMapping("/dologin")
    public String login(HttpServletRequest request){
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findByNameAndPassword(name, password);
        if(user != null){
            return "redirect:/productList";
        }else{
            return "login";
        }
    }

    /**
     * 注册界面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    /**
     * 判断是否成功注册
     * @param request
     * @return
     */
    @RequestMapping("/doregister")
    public String register(HttpServletRequest request){
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        List<User> userList =  userService.findByName(name);
        if(userList.size() == 0 && password.equals(password2)){
            User user = new User();
            user.setUserName(name);
            user.setUserPassword(password);
            userService.saveUser(user);
            return "login";
        }else{
            return "register";
        }
    }

}
