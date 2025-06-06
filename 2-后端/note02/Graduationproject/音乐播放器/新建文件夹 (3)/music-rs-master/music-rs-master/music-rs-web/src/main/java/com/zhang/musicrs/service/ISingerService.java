package com.zhang.musicrs.service;

public interface ISingerService {
    /**
     * 根据歌手姓名查找歌手id
     * @param name 歌手姓名
     * @return
     */
    String queryUidByName(String name);
}
