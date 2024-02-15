package com.quartz.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quartz.demo.entity.Task;
import com.quartz.demo.service.TaskService;
import com.quartz.demo.mapper.TaskMapper;
import org.springframework.stereotype.Service;

/**
 * 任务实现
* @author 16658
* @description 针对表【t_task】的数据库操作Service实现
* @createDate 2024-02-09 18:10:28
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}




