package com.springboot101.limit.controller;

import com.alibaba.fastjson2.JSON;
import com.springboot101.limit.dao.OrderInfoDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 
 * @Description: 用于演示处理时间字段的不同情况。  不用看
 */
@Controller
public class TimeController {

    /**
     * 映射 GET 请求 "/timeTest" 到此方法，演示在视图中处理时间字段。
     *
     * @param model 用于在视图中传递数据的 Model 对象。
     * @return 返回视图名称 "index"。
     */
    @GetMapping("/timeTest")
    public String timeTest(Model model) {
        OrderInfoDao order = new OrderInfoDao();
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(new Date());
        System.out.println(order.getCreateTime());
        System.out.println(order.getCreateTime());

        model.addAttribute("order",order);
        System.out.println(JSON.toJSONString(order));
        return "index";
    }

    /**
     * 映射 GET 请求 "/timeTest1" 到此方法，演示在 JSON 响应中处理时间字段。
     *
     * @return 返回包含时间字段的 OrderInfo 对象，将以 JSON 格式响应。
     */
    @GetMapping("/timeTest1")
    @ResponseBody
    public OrderInfoDao timeTest1() {
        OrderInfoDao order = new OrderInfoDao();
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(new Date());
        return order;
    }
}