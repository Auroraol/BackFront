package com.zhang.musicrs.service;

import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.entity.AdministratorDO;

public interface IAdminService {
    /**
     * 管理员登录业务接口
     * @param admin 传入一个Admin类型的对象，判断是否在数据库中
     * @return Result类型的结果
     */
    Result adminLogin(AdministratorDO admin);
}
