package com.springboot101.model;

import com.springboot101.annotation.EncryptField;
import lombok.Data;

import java.io.Serializable;

/**

 * @Date: 2021/7/26 15:10
 * @Description:
 */
@Data
public class UserVo implements Serializable {

    private Long userId;

    @EncryptField
    private String mobile;

    @EncryptField
    private String address;

    private String age;
}
