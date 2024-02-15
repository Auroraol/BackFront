package com.quartz.demo.mapper;
import org.apache.ibatis.annotations.Param;

import com.quartz.demo.entity.TimedTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 16658
* @description 针对表【t_timed_task】的数据库操作Mapper
* @createDate 2024-02-09 18:12:48
* @Entity com.quartz.demo.entity.TimedTask
*/

@Mapper
public interface TimedTaskMapper extends BaseMapper<TimedTask> {

	void deleteByTaskId(Integer taskId);

	List<TimedTask> selectByTaskId(@Param("taskId") Integer taskId);
}




