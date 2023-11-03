package com.example.experiment01.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2023-09-21 08:27:05
* @Entity com.example.experiment01.entity.TbUser
*/
public interface TbUserMapper extends BaseMapper<TbUser> {
	List<TbUser> selectById(@Param("id") Integer id);
}




