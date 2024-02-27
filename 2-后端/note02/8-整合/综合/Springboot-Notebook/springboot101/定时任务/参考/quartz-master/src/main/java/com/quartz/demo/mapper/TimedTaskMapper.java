package com.quartz.demo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.quartz.demo.pojo.TimedTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PackgeName: com.quartz.demo.mapper
 * @ClassName: TimedTaskMapper
 * @Author: zjy
 * Date: 2020/6/14 10:17
 * project name: quartz
 * @Version:
 * @Description:
 */
@Mapper
public interface TimedTaskMapper extends BaseMapper<TimedTask> {

    void deleteByTaskId(Integer taskId);

    List<TimedTask> selectByTaskId(Integer taskId);
}
