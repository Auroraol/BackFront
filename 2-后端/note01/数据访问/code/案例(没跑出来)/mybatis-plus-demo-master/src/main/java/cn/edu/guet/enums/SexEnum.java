package cn.edu.guet.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * @Classname SexEnum
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 11:58
 * @Created by abner.guo
 */
@Getter
public enum SexEnum {

    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private int sex;

    private String sexName;

    SexEnum(int sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
