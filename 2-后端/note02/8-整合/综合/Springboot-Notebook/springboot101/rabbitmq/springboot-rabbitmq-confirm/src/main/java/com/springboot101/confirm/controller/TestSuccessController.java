package com.springboot101.confirm.controller;

import com.springboot101.confirm.config.MqConfirmConfig;
import com.springboot101.confirm.sender.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 生产者代码
 * @Description: 生产者-发送消息测试接口-正确情况
 */
@Slf4j
@Controller
@RequestMapping
public class TestSuccessController {

    @Autowired
    private SendMessage sendMessage;

    //交换机的发布确认
    @GetMapping("/TestSuccessController1/{message}")
    public void sendConfirmMsg1(@PathVariable String message){
        //向一个存在的交换机发送消息
        sendMessage.sendMessage("confirm.fanout", "confirm_test_queue",  message);
    }

    //回退消息并重发
    @GetMapping("/TestSuccessController2/{message}")
    public void sendConfirmMsg2(@PathVariable String message){
        //向一个存在的队列发送消息
        sendMessage.sendMessage("jindao.direct", "confirm.direct.queue1",  message);
    }

    //备份交换机
    @GetMapping("/TestSuccessController3/{message}")
    public void sendConfirmMsg(@PathVariable String message){
        //向一个存在的队列发送消息
        sendMessage.sendMessage(MqConfirmConfig.CONFIRM_EXCHANGE, MqConfirmConfig.CONFIRM_EXCHANGE_ROUTING_KEY,  message);
    }
}