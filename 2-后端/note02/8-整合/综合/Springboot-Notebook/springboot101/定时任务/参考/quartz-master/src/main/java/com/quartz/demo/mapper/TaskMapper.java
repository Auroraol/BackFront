package com.quartz.demo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.quartz.demo.pojo.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * @PackgeName: com.quartz.demo.mapper
 * @ClassName: TaskMapper
 * @Author: zjy
 * Date: 2020/6/14 11:02
 * project name: quartz
 * @Version:
 * @Description:
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
