package com.zhang.musicrs.service;

import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.entity.LoveSongDO;

public interface ILoveSongService {
    /**
     * 添加收藏记录，如果收藏记录已经存在且为未删除状态，则修改收藏时间；如果为删除状态，则修改删除状态为未删除
     * 如果不存在，则将新的收藏记录添加到表中
     * @param loveSong 要添加的收藏记录
     * @return
     */
    Result addLoveSong(LoveSongDO loveSong);

    /**
     * 取消收藏，更新收藏记录的删除状态和时间戳
     * @param loveSong 要取消的收藏记录
     * @return
     */
    Result cancelLoveSong(LoveSongDO loveSong);
}
