package com.example.springbootredissontest1.common;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.common
 * @date 2021/6/4 13:49
 * @description 状态码枚举
 */
public enum StatusCode {

    SUCCESS(2000, "操作成功"),
    ERROR(5000, "系统异常"),
    CUSTOM_ERROR(5500, "自定义异常"),
    INVALID_PARAMS(1000, "request parameter请求参数{}异常");

    private final Integer code;

    private final String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return
     */
    public Integer code() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return
     */
    public String message() {
        return message;
    }


    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
