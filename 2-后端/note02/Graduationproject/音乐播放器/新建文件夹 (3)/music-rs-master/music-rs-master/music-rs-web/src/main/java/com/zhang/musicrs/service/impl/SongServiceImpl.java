package com.zhang.musicrs.service.impl;

import com.zhang.musicrs.dao.LoveSongMapper;
import com.zhang.musicrs.dao.SingerMapper;
import com.zhang.musicrs.dao.SongMapper;
import com.zhang.musicrs.entity.LoveSongDO;
import com.zhang.musicrs.entity.SongDO;
import com.zhang.musicrs.service.ISongService;
import com.zhang.musicrs.util.RandomUtil;
import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.util.result.ResultEnum;
import com.zhang.musicrs.util.result.ResultUtil;
import com.zhang.musicrs.util.timeUtil.MyTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SongServiceImpl implements ISongService {
    @Autowired
    SongMapper songMapper;
    @Autowired
    SingerMapper singerMapper;
    @Autowired
    LoveSongMapper loveSongMapper;

    @Override
    public Result queryByIid(String iid) {
        // 得到查询结果
        SongDO songRes = songMapper.queryByIid(iid);
        if (songRes == null) {
            return ResultUtil.fail(500, "该歌曲不存在");
        }
        // 设置歌曲的时长
        songRes.setSongTime(MyTimeUtil.millSeconds2time(songRes.getSongTime()));
        // 格式化发行时间
        songRes.setPublishTime(MyTimeUtil.timeFormat(songRes.getPublishTime()));
        // 设置歌曲播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
        // songRes.setDownUrl(SongDetail.getSongMP3Url(songRes.getIid()));
        // 查询到该歌曲并设置播放链接
        return ResultUtil.success(songRes);
    }

    @Override
    public Result queryByKeyword(String keyWord) {
        if (keyWord == null || "".equals(keyWord)) {
            return ResultUtil.fail(400, "未搜索到数据！");
        }
        // 从数据库查询数据
        List<SongDO> songs = songMapper.queryByKeyword("%" + keyWord + "%");
        for (SongDO song : songs) {
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 格式化发行时间
            song.setPublishTime(MyTimeUtil.timeFormat(song.getPublishTime()));
            // 设置音乐播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
            // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
        }
        // 返回数据
        return ResultUtil.success(songs);
    }

    @Override
    public Result querySongsHot(int currIndex, int pageSize) {
        // 得到结果
        List<SongDO> songList = songMapper.querySongsHot(currIndex, pageSize);
        if (songList == null) {
            return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "数据不存在！");
        }
        // 设置歌曲内容
        for (int i = 0; i < songList.size(); i++) {
            SongDO song = songList.get(i);
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 格式化发行时间
            song.setPublishTime(MyTimeUtil.timeFormat(song.getPublishTime()));
            // 设置音乐播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
            // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
        }
        // 返回音乐数据
        return ResultUtil.success(songList);
    }

    @Override
    public Result querySongsNew(int currIndex, int pageSize) {
        // 得到结果
        List<SongDO> songList = songMapper.querySongsNew(currIndex, pageSize);
        if (songList == null) {
            return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "数据不存在！");
        }
        // 设置歌曲内容
        for (int i = 0; i < songList.size(); i++) {
            SongDO song = songList.get(i);
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 格式化发行时间
            song.setPublishTime(MyTimeUtil.timeFormat(song.getPublishTime()));
            // 设置音乐播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
            // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
        }
        // 返回音乐数据
        return ResultUtil.success(songList);
    }

    @Override
    public Result querySongsLimit(int currIndex, int pageSize) {
        // 得到结果
        List<SongDO> songList = songMapper.querySongsNew(currIndex, pageSize);
        if (songList == null) {
            return ResultUtil.fail(500, "数据不存在！");
        }
        // 设置歌曲内容
        for (int i = 0; i < songList.size(); i++) {
            SongDO song = songList.get(i);
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 格式化发行时间
            song.setPublishTime(MyTimeUtil.timeFormat(song.getPublishTime()));
        }
        // 返回音乐数据
        return ResultUtil.success(songList);
    }

    @Override
    public Result addSong(SongDO song) {
        // 判空
        if (song != null) {
            // 设置随机id
            song.setIid(RandomUtil.getNBitRandomDigit(10));
            // 设置歌手id
            String suid = singerMapper.queryByName(song.getSingerName());
            // 如果名字不存在，则设置默认id
            if (suid == null || "".equals(suid)) {
                suid = "00000000";
            }
            System.out.println(suid);
            song.setSingerName(suid);
            // 设置歌手id
            SongDO res = songMapper.queryByIid(song.getIid());
            // 歌曲不存在才能添加
            if (res != null) {
                return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "该歌曲id已存在，无法添加，请稍后充实！");
            }
            // 设置发行时间
            song.setPublishTime(System.currentTimeMillis() + "");
            songMapper.addSong(song);
            // 添加成功，返回提示信息
            return ResultUtil.success(song);
        } else {
            return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "歌曲信息为空，添加失败，请稍后再试！");
        }
    }

    @Override
    public Result updateSong(SongDO song) {
        if (song != null) {
            SongDO res = songMapper.queryByIid(song.getIid());
            // 歌曲存在才能更新
            if (res == null) {
                return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "该歌曲不存在，无法更新，请稍后重试！");
            }
            songMapper.updateSong(song);
            // 更新成功，返回提示信息
            return ResultUtil.success(song);
        } else {
            return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "歌曲信息为空，更新失败，请稍后再试！");
        }
    }

    @Override
    public Result deleteSongByIid(String iid) {
        if (iid != null) {
            SongDO res = songMapper.queryByIid(iid);
            // 歌曲存在才能删除
            if (res == null) {
                return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "该歌曲不存在，无法删除，请稍后重试！");
            }
            songMapper.deleteSong(iid);
            // 删除成功，返回提示信息
            return ResultUtil.success(iid);
        } else {
            return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "歌曲id为空，删除失败，请稍后再试！");
        }
    }

    @Override
    public Result queryLoveSongList(String uid) {
        // 收藏歌曲列表
        List<SongDO> songsList = new ArrayList<>();
        // 用户收藏记录列表
        List<LoveSongDO> loveSongs = loveSongMapper.queryByUid(uid);
        for (LoveSongDO loveSong : loveSongs) {
            SongDO song = songMapper.queryByIid(loveSong.getIid());
            if (song != null) {
                // 设置音乐时长
                song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
                // 格式化发行时间
                song.setPublishTime(MyTimeUtil.timeFormat(song.getPublishTime()));
                // 设置音乐播放链接，这里不需要再进行获取链接，目前数据库中的下载链接可以直接播放
                // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
                // 添加到结果数组中
                songsList.add(song);
            }
        }
        // 返回推荐歌曲列表
        return ResultUtil.success(songsList);
    }

    @Override
    public Result queryAll() {
        // 不需要播放，所以不用获得歌曲播放链接
        return ResultUtil.success(songMapper.queryAll());
    }
}
