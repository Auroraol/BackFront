package com.lfj.mapper;
import java.util.List;

import com.lfj.entity.XUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 16658
* @description 针对表【x_user】的数据库操作Mapper
* @createDate 2023-09-22 21:13:25
* @Entity com.lfj.entity.XUser
*/
@Mapper
public interface XUserMapper extends BaseMapper<XUser> {
	List<String> getRoleNamesByUserId(@Param("id") int id);
}




