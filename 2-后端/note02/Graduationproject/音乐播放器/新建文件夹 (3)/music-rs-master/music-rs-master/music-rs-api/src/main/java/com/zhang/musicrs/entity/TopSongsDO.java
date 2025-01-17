package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class TopSongsDO {
    // 歌曲id
    private String songName;
    // 相似歌曲的id合集，中间使用逗号隔开
    private String topSongs;
    // private List<Song> topsongs;

    public TopSongsDO() {
    }

    public String getSongName() {
        return songName;
    }

    public TopSongsDO setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getTopSongs() {
        return topSongs;
    }

    public TopSongsDO setTopSongs(String topSongs) {
        this.topSongs = topSongs;
        return this;
    }

    @Override
    public String toString() {
        return "TopSongs{" +
                "songName='" + songName + '\'' +
                ", topSongs='" + topSongs + '\'' +
                '}';
    }
}
