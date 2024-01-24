package com.youzhirong.fbslock.service;
 
import com.youzhirong.fbslock.dto.UserDTO;
 
/**
 * [说明]用户表Service
 * @author youzhirong
 * @version 创建时间： 2020-04-08
 */
public interface UserService {
 
    UserDTO selectByCode(String code);
 
    void saveByDTO(UserDTO userDTO);
    
    void saveByDTOLock(UserDTO userDTO);
    
    void saveByDTOFairLock(UserDTO userDTO);
 
}