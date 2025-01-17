package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class LoveSongDO {
    // 歌曲id
    private String iid;
    // 用户id
    private String uid;
    // 歌曲名称
    private String name;
    // 歌手名称
    private String songName;
    // 是否收藏，1表示收藏
    private String isDelete;
    // 最近依次播放时间
    private String timestamp;

    public LoveSongDO() {
    }

    public String getIid() {
        return iid;
    }

    public LoveSongDO setIid(String iid) {
        this.iid = iid;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public LoveSongDO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public LoveSongDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public LoveSongDO setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public LoveSongDO setIsDelete(String isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public LoveSongDO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "LoveSong{" +
                "iid='" + iid + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", songName='" + songName + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
