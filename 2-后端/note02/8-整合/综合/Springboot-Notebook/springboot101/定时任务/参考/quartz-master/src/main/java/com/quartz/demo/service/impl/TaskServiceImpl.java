package com.quartz.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.quartz.demo.mapper.TaskMapper;
import com.quartz.demo.pojo.Task;
import com.quartz.demo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @PackgeName: com.quartz.demo.service.impl
 * @ClassName: TaskServiceImpl
 * @Author: zjy
 * Date: 2020/6/14 11:00
 * project name: quartz
 * @Version:
 * @Description:
 */
@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
}
