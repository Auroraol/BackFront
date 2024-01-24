package com.youzhirong.fbslock.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
 
/**
 * [说明]用户表DTO
 * @author youzhirong
 * @version 创建时间： 2020-04-08
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserDto", description = "用户表")
public class UserDTO {
     
    @ApiModelProperty("主键")
    private Long id; //主键
     
    @ApiModelProperty("登陆用户账号")
    private String loginId; //登陆用户账号
     
    @ApiModelProperty("用户code")
    private String userCode; //用户code
     
    @ApiModelProperty("用户名称")
    private String userName; //用户名称
     
    private Long objectVersionNumber;
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
