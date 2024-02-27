package com.quartz.demo.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * @PackgeName: com.quartz.demo.pojo
 * @ClassName: Task
 * @Author: zjy
 * Date: 2020/6/14 10:53
 * project name: quartz
 * @Version:
 * @Description:
 */
@Data
@TableName("t_task")
public class Task {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private int deleteFlag = 0;
}
