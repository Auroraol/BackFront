package com.springboot101.controller.socket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@CrossOrigin("*")
@Controller
@RequestMapping("/socket")
public class SocketOpsController {

    @Resource
    private WebSocketServer webSocket;

    @RequestMapping("/index")
    public String sse() {
        return "socket";
    }

    /**
    
     * 变更数据
     */
    @GetMapping(path = "publish")
    @ResponseBody
    public String publish(@RequestParam String message,@RequestParam String userId) {
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        webSocket.sendOneMessage(userId, message);
        return "success";
    }
}

