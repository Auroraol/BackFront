package com.example.ssm.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.ssm.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-09-11 23:32:06
* @Entity com.example.ssm.entity.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {
	int insertAll(User user);
}




