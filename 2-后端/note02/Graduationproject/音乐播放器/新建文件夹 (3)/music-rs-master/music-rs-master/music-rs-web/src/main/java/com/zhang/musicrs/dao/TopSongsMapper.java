package com.zhang.musicrs.dao;

import com.zhang.musicrs.entity.TopSongsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TopSongsMapper {
    /**
     * 根据音乐iid查找相似音乐
     *
     * @param iid 音乐id
     * @return 相似音乐id合集，中间使用逗号隔开
     */
    TopSongsDO queryByIid(@Param("iid") String iid);
}
