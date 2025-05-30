package com.azl.recommendation_system.util.result;


public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(100, "未知错误"),
    SUCCESS(200, "成功"),
    USER_NOT_EXIST(400, "用户不存在"),
    USER_IS_EXISTS(401, "用户已存在"),
    DATA_IS_NULL(500, "数据为空"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
