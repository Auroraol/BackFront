package com.example.axiostest.controller;

import com.example.axiostest.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	
	@RequestMapping("/index2")
	public String send(){
		return "index2";
	}

	@PostMapping("/send-message")
	@ResponseBody
	public String sendMessage(@RequestBody String message) {
		// 处理接收到的消息，这里简单地返回一个响应
		return "接收信息: " + message;
	}

	@PostMapping("/send-message2")
	@ResponseBody
	public String sendMessage2(@RequestBody Message message) {
		// 处理接收到的消息，这里简单地返回一个响应
		return "接收信息: " + message.getData();
	}
}
