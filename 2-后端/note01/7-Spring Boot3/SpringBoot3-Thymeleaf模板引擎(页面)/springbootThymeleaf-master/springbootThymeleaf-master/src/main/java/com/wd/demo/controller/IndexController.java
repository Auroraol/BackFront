package com.wd.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 初始界面，选择登录或者注册
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
