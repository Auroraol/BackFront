package com.zhang.musicrs.dao;

import com.zhang.musicrs.entity.LoveSongDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZhangChaojie
 * @Description: TODO(歌曲收藏记录数据库操作接口)
 * @date 2022/4/26 16:39
 */
@Mapper
@Repository
public interface LoveSongMapper {
    /**
     * 添加用户歌曲收藏记录
     * @param loveSong 要添加的记录
     */
    void addLoveSong(LoveSongDO loveSong);

    /**
     * 更新歌曲收藏记录，更新删除状态和时间戳
     * @param loveSong 新的记录
     */
    void updateLoveSong(LoveSongDO loveSong);

    /**
     * 根据用户id查询用户播放记录集合
     * @param uid
     * @return
     */
    List<LoveSongDO> queryByUid(@Param("uid") String uid);

    /**
     * 根据用户id和歌曲id查找唯一播放记录
     * @param loveSong
     * @return
     */
    LoveSongDO queryByUidAndIid(LoveSongDO loveSong);
}
