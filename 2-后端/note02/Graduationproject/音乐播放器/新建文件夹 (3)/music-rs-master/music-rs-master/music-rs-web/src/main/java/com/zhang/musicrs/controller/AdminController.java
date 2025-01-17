package com.zhang.musicrs.controller;

import com.zhang.musicrs.entity.AdministratorDO;
import com.zhang.musicrs.service.IAdminService;
import com.zhang.musicrs.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangChaojie
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2022/4/26 17:30
 */
@RestController
public class AdminController {
    // 这里不需要修改，不影响正常运行
    @Autowired
    private IAdminService iAdminService;
    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    // 使用@RequestBody，前端传过来的数据必须是json格式，且json数据中的字段名要和对象的属性名一致，后端才能正确接收参数
    public Result adminLogin(@RequestBody AdministratorDO admin) {
        System.out.println(admin.toString());
        return iAdminService.adminLogin(admin);
    }
}
