package com.quartz.demo.mapper;

import com.quartz.demo.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 16658
* @description 针对表【t_task】的数据库操作Mapper
* @createDate 2024-02-09 18:10:28
* @Entity com.quartz.demo.entity.Task
*/

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




