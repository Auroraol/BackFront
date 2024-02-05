package com.springboot101.confirm.controller;

import com.springboot101.confirm.config.MqConfirmConfig;
import com.springboot101.confirm.sender.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 生产者代码
 * @Description: 生产者-发送消息测试接口-错误情况
 */
@Slf4j
@Controller
@RequestMapping
public class TestErrorController {

    @Autowired
    private SendMessage sendMessage;

    /*
    * 只要生产者发布消息，交换机不管是否收到消息，都会调用该类的 confirm 方法
    * */
    @GetMapping("/TestErrorController1/{message}")
    public void sendConfirmMsg1(@PathVariable String message){
        //向一个不存在的交换机发送消息
        sendMessage.sendMessage("xxxx.fanout", "confirm_test_queue", message);
    }

    /*
    * 向交换机中发送消息，指定错误的routingkey,模拟触发队列回退消息并重发消息。
    */
    @GetMapping("/TestErrorController2/{message}")
    public void sendConfirmMsg2(@PathVariable String message){
        //向一个不存在的路由发送消息
        sendMessage.sendMessage("jindao.direct", "xxxxx",  message);
    }

    /*
    * 生产者发送两条消息 一个配置正确的路由，另一个是错误的路由。预期目标是正确路由正常接收消息，错误路由传输的信息由警告队列接收。
    * */
    @GetMapping("/TestErrorController3/{message}")
    public void sendConfirmMsg(@PathVariable String message){
        //向一个不存在的路由发送消息
        sendMessage.sendMessage(MqConfirmConfig.CONFIRM_EXCHANGE, MqConfirmConfig.CONFIRM_EXCHANGE_ROUTING_KEY+222,  message);
    }
}
