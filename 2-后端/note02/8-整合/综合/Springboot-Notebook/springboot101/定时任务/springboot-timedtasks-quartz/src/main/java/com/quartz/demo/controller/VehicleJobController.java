package com.quartz.demo.controller;

import com.quartz.demo.entity.TimedTask;
import com.quartz.demo.mapper.TimedTaskMapper;
import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;
import com.quartz.demo.service.TimedTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/quartz")
public class VehicleJobController {

    @Autowired
    private TimedTaskService timedTaskService;
    @Autowired
    TimedTaskMapper timedTaskMapper;

    @PostMapping("/add")
    public ResultBO add(@Validated @RequestBody AddRequest addRequest) throws Exception {

        System.out.println("传过来的添加定时任务参数为:{}" + addRequest);
        ResultBO resultBO = timedTaskService.addQuartzJob(addRequest);
        return resultBO;
    }

    @GetMapping("/delete/{taskId}")
    public ResultBO delete( @PathVariable String taskId) throws Exception {

        System.out.println("传过来的删除定时任务id为:{}" + taskId);
        ResultBO resultBO = timedTaskService.deleteQuartzJob(Integer.valueOf(taskId));
        return resultBO;
    }
}
