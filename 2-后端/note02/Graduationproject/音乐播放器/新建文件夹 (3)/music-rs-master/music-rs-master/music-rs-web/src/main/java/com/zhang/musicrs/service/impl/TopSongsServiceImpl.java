package com.zhang.musicrs.service.impl;

import com.zhang.musicrs.dao.SongMapper;
import com.zhang.musicrs.dao.TopSongsMapper;
import com.zhang.musicrs.entity.SongDO;
import com.zhang.musicrs.service.ITopSongsService;
import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopSongsServiceImpl implements ITopSongsService {
    @Autowired
    private TopSongsMapper topSongsMapper;
    @Autowired
    private SongMapper songMapper;

    @Override
    public Result querySimilarSongs(String iid) {
        // 相似歌曲列表
        List<SongDO> songList = new ArrayList<>();
        // 相似歌曲id集合
        List<String> iidList = Arrays.asList(topSongsMapper.queryByIid(iid).getTopSongs().split(","));
        for (String tempId : iidList) {
            // songList.add(songMapper.queryByIid(tempId).setDownUrl(SongDetail.getSongMP3Url(tempId)));
            // 设置歌曲播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
            songList.add(songMapper.queryByIid(tempId));
        }
        return ResultUtil.success(songList);
    }
}
