package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class SongDO {
    // 歌曲id
    private String iid;
    // 歌曲名称
    private String songName;
    // 歌手名称
    private String singerName;
    // 所在专辑
    private String album;
    // 播放次数
    private String playcnt;
    // 歌曲url
    private String songUrl;
    // 播放url
    private String downUrl;
    // 时长
    private String songTime;
    // 图片url
    private String picUrl;
    // 发行时间
    private String publishTime;

    public SongDO() {
    }

    public String getIid() {
        return iid;
    }

    public SongDO setIid(String iid) {
        this.iid = iid;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public SongDO setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getSingerName() {
        return singerName;
    }

    public SongDO setSingerName(String singerName) {
        this.singerName = singerName;
        return this;
    }

    public String getAlbum() {
        return album;
    }

    public SongDO setAlbum(String album) {
        this.album = album;
        return this;
    }

    public String getPlaycnt() {
        return playcnt;
    }

    public SongDO setPlaycnt(String playcnt) {
        this.playcnt = playcnt;
        return this;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public SongDO setSongUrl(String SongUrl) {
        this.songUrl = SongUrl;
        return this;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public SongDO setDownUrl(String downUrl) {
        this.downUrl = downUrl;
        return this;
    }

    public String getSongTime() {
        return songTime;
    }

    public void setSongTime(String songTime) {
        this.songTime = songTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Song{" +
                "iid='" + iid + '\'' +
                ", songName='" + songName + '\'' +
                ", singerName='" + singerName + '\'' +
                ", album='" + album + '\'' +
                ", playcnt='" + playcnt + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", songTime='" + songTime + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
