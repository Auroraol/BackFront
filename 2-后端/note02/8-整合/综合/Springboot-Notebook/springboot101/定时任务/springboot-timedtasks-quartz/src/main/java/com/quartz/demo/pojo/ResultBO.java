package com.quartz.demo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:操作返回实体
 */
@Data
public class ResultBO<T> implements Serializable {

    private boolean succeed = true;
    private int code = 0;
    private String msg;
    private T content;

    public ResultBO(T content) {
        this.content = content;
    }

    public ResultBO(boolean succeed, int code, String msg, T content) {
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public ResultBO(boolean succeed, int code, String msg) {
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
    }

    public ResultBO() {

    }

    public static <T> ResultBO<T> success(T content) {
        return new ResultBO<T>(content);
    }

    public static ResultBO success() {
        return new ResultBO();
    }

    public static ResultBO fail(int code, String msg) {
        return new ResultBO(false, code, msg);
    }

    public static ResultBO fail(String msg) {
        return new ResultBO(false, -1, msg);
    }

    public static ResultBO fail() {
        return fail("fail");
    }
}
