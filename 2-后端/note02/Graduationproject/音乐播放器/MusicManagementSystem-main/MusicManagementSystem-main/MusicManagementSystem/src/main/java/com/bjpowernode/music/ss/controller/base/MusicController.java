package com.bjpowernode.music.ss.controller.base;

import com.bjpowernode.music.ss.domain.MusicLink;
import com.bjpowernode.music.ss.mapper.IMusicLinkMapper;
import com.bjpowernode.music.ss.service.IMusicLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {


    @Resource
    protected IMusicLinkService musicLinkService;

    @Resource
    private IMusicLinkMapper musicLinkMapper;

    @GetMapping("getMusicList")
    public List<MusicLink> getMusicList() {
        LinkedHashMap<String, String> condition = new LinkedHashMap<String, String>();

        List<MusicLink> list = this.musicLinkService.getList(condition, 1, 10000, null, null);
        return list;
    }

    @RequestMapping("saveMusic")
    public String saveMusic(@RequestBody MusicLink musicLink) {
        musicLinkMapper.saveMusic(musicLink);
        return "OK";
    }
    @RequestMapping("editMusic")
    public String editMusic(@RequestBody MusicLink musicLink) {
        musicLinkMapper.editMusic(musicLink);
        return "OK";
    }
    @RequestMapping("deleteMusic")
    public String deleteMusic(String id) {
        musicLinkMapper.deleteMusic(id);
        return "OK";
    }


}
