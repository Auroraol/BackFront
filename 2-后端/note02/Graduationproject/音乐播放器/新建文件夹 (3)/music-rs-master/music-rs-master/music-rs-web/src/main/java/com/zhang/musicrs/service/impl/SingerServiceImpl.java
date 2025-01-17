package com.zhang.musicrs.service.impl;

import com.zhang.musicrs.dao.SingerMapper;
import com.zhang.musicrs.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingerServiceImpl implements ISingerService {
    @Autowired
    SingerMapper singerMapper;
    @Override
    public String queryUidByName(String name) {
        return singerMapper.queryByName(name);
    }
}
