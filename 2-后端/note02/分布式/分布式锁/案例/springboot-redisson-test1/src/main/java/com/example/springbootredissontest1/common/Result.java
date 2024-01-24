package com.example.springbootredissontest1.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.common
 * @date 2021/6/4 13:45
 * @description 统一返回数据实体类
 */
@Api(value = "通用返回响应结果数据对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 897983798452040353L;

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码", required = true)
    private Integer code;

    /**
     * 响应结果信息
     */
    @ApiModelProperty(value = "响应结果信息", required = true)
    private String message;

    /**
     * 响应结果数据
     */
    @ApiModelProperty(value = "响应结果数据", required = true)
    private T data;

    // 操作成功 不返回数据
    public static <T> Result<T> success() {
        return new Result<>(StatusCode.SUCCESS.code(), StatusCode.SUCCESS.message(), null);
    }

    // 操作成功 返回数据
    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCode.SUCCESS.code(), StatusCode.SUCCESS.message(), data);
    }

    // 系统错误 不返回数据
    public static <T> Result<T> error() {
        return new Result<>(StatusCode.ERROR.code(), StatusCode.ERROR.message(), null);
    }

    // 系统错误 返回逻辑数据
    public static <T> Result<T> error(T data) {
        return new Result<>(StatusCode.ERROR.code(), StatusCode.ERROR.message(), data);
    }

    // 错误并返回指定错误信息和状态码以及逻辑数据
    public static <T> Result<T> error(StatusCode statusCode, T data) {
        return new Result<>(statusCode.code(), statusCode.message(), data);
    }

    // 错误并返回指定错误信息和状态码 不返回数据
    public static <T> Result<T> error(StatusCode statusCode) {
        return new Result<>(statusCode.code(), statusCode.message(), null);
    }

    // 自定义错误和状态返回
    public static <T> Result<T> errorMessage(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    // 自定义错误信息 状态码固定
    public static <T> Result<T> errorMessage(String message) {
        return new Result<>(StatusCode.CUSTOM_ERROR.code(), message, null);
    }

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
