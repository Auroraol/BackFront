package com.zhang.musicrs.controller;

import com.alibaba.fastjson.JSON;
import com.zhang.musicrs.entity.RecordDO;
import com.zhang.musicrs.service.IRecordService;
import com.zhang.musicrs.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangChaojie
 * @Description: TODO(播放记录)
 * @date 2022/5/01 23:47
 */
@RestController
public class RecordController {
    @Autowired
    IRecordService iRecordService;
    // 日志管理
    // getLogger是使用静态绑定的ILoggerFactory实例返回与作为参数传递的类相对应的记录器。这里是从LoggerFactory获取一个和HelloController类绑定的日志记录器
    public static final Logger logger = LoggerFactory.getLogger(RestController.class);

    /**
     * 添加播放记录
     * @param record
     * @return
     */
    @RequestMapping(value = "/addRecord", method = RequestMethod.POST)
    public Result addRecord(@RequestBody RecordDO record) {
        logger.info("POST /addRecord");
        System.out.println(record);
        return iRecordService.addRecord(record);
    }

    /**
     * 获得用户的播放记录
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getRecordSong", method = RequestMethod.POST)
    public Result getRecord(@RequestBody String uid) {
        logger.info("POST /getRecordSong");
        System.out.println(uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iRecordService.queryRecordByUid(uid_str);
    }
}
