package com.zhang.musicrs.util.result;


public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(100, "未知错误"),
    SUCCESS(200, "成功"),
    DATA_NOT_EXIST(404, "数据不存在"),
    PARAM_WRONG(401, "参数错误"),
    SERVER_WRONG(500, "服务器错误"),
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
