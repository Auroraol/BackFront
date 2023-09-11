package com.atguigu.boot3.ssm.enutity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  数据库实体
 */
@Data
@NoArgsConstructor //自动生成无参构造器
@AllArgsConstructor //自动生成全参构造器
public class TUser {   //里面写数据库对应表的字段
    private Long id;
    private String loginName;
    private String nickName;
    private String passwd;

}
