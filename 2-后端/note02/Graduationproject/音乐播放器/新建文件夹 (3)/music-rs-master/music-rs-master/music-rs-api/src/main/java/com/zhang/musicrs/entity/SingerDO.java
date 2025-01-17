package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class SingerDO {
    // 歌手id
    private String suid;
    // 歌手名称
    private String sname;
    // 歌手url
    private String surl;

    public SingerDO() {
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getSname() {
        return sname;
    }

    public SingerDO setSname(String sname) {
        this.sname = sname;
        return this;
    }

    public String getSurl() {
        return surl;
    }

    public SingerDO setSurl(String surl) {
        this.surl = surl;
        return this;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "suid='" + suid + '\'' +
                ", sname='" + sname + '\'' +
                ", surl='" + surl + '\'' +
                '}';
    }
}
