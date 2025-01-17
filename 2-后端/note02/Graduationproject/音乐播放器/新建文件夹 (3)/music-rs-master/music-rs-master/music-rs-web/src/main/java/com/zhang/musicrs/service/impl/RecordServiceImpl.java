package com.zhang.musicrs.service.impl;

import com.zhang.musicrs.dao.RecordMapper;
import com.zhang.musicrs.dao.SongMapper;
import com.zhang.musicrs.entity.RecordDO;
import com.zhang.musicrs.entity.SongDO;
import com.zhang.musicrs.service.IRecordService;
import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.util.result.ResultUtil;
import com.zhang.musicrs.util.timeUtil.MyTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecordServiceImpl implements IRecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    SongMapper songMapper;

    @Override
    public Result addRecord(RecordDO record) {
        // 判空
        if (record == null) {
            return ResultUtil.fail(420, "添加失败，传入数据为空");
        }
        // 查询该记录是否存在，不存在则添加
        RecordDO res = recordMapper.queryByUidAndIid(record.getUid(), record.getIid());
        if (res == null) {
            // 设置播放次数和时间戳，这里数据库中存储的是十位时间戳
            record.setWeight("1").setTimestamp(System.currentTimeMillis() / 1000 + "");
            recordMapper.addRecord(record);
            return ResultUtil.success(record);
        } else {
            // 设置时间戳
            record.setTimestamp(System.currentTimeMillis() / 1000  + "");
            recordMapper.updateRecordByUidAndIid(record);
            return ResultUtil.success(record);
        }
    }

    @Override
    public Result queryRecordByUid(String uid) {
        // 判空
        if (uid == null || uid.length() == 0) {
            return ResultUtil.fail(420, "获取失败，传入参数为空");
        }
        // 获得查询结果
        List<RecordDO> records = recordMapper.queryByUid(uid);
        // 判空
        if (records == null) {
            return ResultUtil.fail(420, "获取失败，未查询到结果");
        }
        // 播放记录歌曲集合
        List<SongDO> songs = new ArrayList<>();
        for (RecordDO record : records) {
            SongDO song = songMapper.queryByIid(record.getIid());
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 设置音乐播放链接
            // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
            // 添加到结果数组中
            songs.add(song);
        }
        return ResultUtil.success(songs);
    }
}
