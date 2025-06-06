package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;


@Component
public class Singer {
    // 歌手id
    private String suid;
    // 歌手名称
    private String sname;
    // 歌手url
    private String surl;

    public Singer() {
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

    public Singer setSname(String sname) {
        this.sname = sname;
        return this;
    }

    public String getSurl() {
        return surl;
    }

    public Singer setSurl(String surl) {
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
