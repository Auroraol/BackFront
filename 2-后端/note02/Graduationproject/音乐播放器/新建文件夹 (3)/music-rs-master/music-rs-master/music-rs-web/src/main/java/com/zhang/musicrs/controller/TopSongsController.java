package com.zhang.musicrs.controller;

import com.zhang.musicrs.service.ITopSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangChaojie
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2022/5/1 15:28
 */
@RestController
public class TopSongsController {
    @Autowired
    private ITopSongsService iTopSongsService;
}
