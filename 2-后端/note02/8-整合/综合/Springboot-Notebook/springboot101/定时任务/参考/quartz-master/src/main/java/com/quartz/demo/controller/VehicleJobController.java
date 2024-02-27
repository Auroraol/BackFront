package com.quartz.demo.controller;

import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;
import com.quartz.demo.service.TimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "定时任务", tags = {"定时任务"})
@Slf4j
@Validated
@RestController
@RequestMapping("/quartz")
public class VehicleJobController {

    @Autowired
    private TimedTaskService timedTaskService;

    @ApiOperation("添加定时任务")
    @PostMapping("/add")
    public ResultBO add(@Validated @RequestBody AddRequest addRequest) throws Exception {

        System.out.println("传过来的添加定时任务参数为:{}" + addRequest);
        ResultBO resultBO = timedTaskService.addQuartzJob(addRequest);
        return resultBO;
    }

    @ApiOperation("删除定时任务")
    @GetMapping("/delete/{taskId}")
    public ResultBO delete(@ApiParam(value = "定时任务id", required = true) @PathVariable String taskId) throws Exception {

        System.out.println("传过来的删除定时任务id为:{}" + taskId);
        ResultBO resultBO = timedTaskService.deleteQuartzJob(Integer.valueOf(taskId));
        return resultBO;
    }
}
