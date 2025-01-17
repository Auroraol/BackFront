package com.zhang.musicrs.controller;

import com.zhang.musicrs.entity.LoveSongDO;
import com.zhang.musicrs.service.ILoveSongService;
import com.zhang.musicrs.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangChaojie
 * @Description: TODO(歌曲收藏记录Controller)
 * @date 2022/5/02 12:14
 */
@RestController
public class LoveSongController {
    @Autowired
    ILoveSongService iLoveSongService;
    /**
     * 添加收藏记录
     * @param loveSong 要添加的记录
     * @return
     */
    @RequestMapping(value = "/addLoveSong", method = RequestMethod.POST)
    public Result addLoveSong(@RequestBody LoveSongDO loveSong) {
        System.out.println(loveSong);
        return iLoveSongService.addLoveSong(loveSong);
    }

    /**
     * 删除收藏记录
     * @param loveSong 要删除的记录
     * @return
     */
    @RequestMapping(value = "/cancelLoveSong",method = RequestMethod.POST)
    public Result cancelLoveSong(@RequestBody LoveSongDO loveSong) {
        System.out.println(loveSong);
        return iLoveSongService.cancelLoveSong(loveSong);
    }
}
