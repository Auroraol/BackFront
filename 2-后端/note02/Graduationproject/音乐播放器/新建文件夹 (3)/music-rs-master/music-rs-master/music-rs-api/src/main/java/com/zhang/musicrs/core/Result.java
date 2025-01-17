package com.zhang.musicrs.core;


import com.alibaba.fastjson.JSONObject;
import com.zhang.musicrs.common.SafeKit;
import com.zhang.musicrs.common.StrKit;
import com.zhang.musicrs.exception.ParamException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Zhang Chaojie
 * @comments:
 * @since Date： 2023/04/19 10:14
 */
@ApiModel
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4569216979022946969L;
    @ApiModelProperty(notes = "返回数据", required = true)
    private T data;// 返回数据

    @ApiModelProperty(notes = "成功标识", example = "true", required = true)
    private boolean isSuccess = false;// 成功标识

    @ApiModelProperty(notes = "结果信息", example = "执行成功！", required = true)
    private String resultMsg = "执行失败!";// 结果信息

    public Result() {

    }

    public static <T> Result<T> fail() {
        return new Result<>();
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> result = fail();
        result.setResultMsg(msg);
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = success();
        result.setResultMsg(msg);
        return result;
    }


    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setResultMsg("执行成功！");
        result.setIsSuccess(true);
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = success();
        result.setData(t);
        return result;
    }

    public static <T> Result<T> success(Result<T> result) {
        if (result == null) {
            return success();
        }
        result.setResultMsg("执行成功！");
        result.setIsSuccess(true);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 添加返回数据
     *
     * @param key   key
     * @param value value
     * @return
     */
    public Result addData(String key, Object value) {
        Map<String, Object> map;
        if (this.data == null) {
            map = new HashMap<>();
        } else if (this.data instanceof Map) {
            map = (Map<String, Object>) this.data;
        } else {
            throw new ParamException("not support");
        }
        map.put(key, value);
        setData((T) map);
        return this;
    }

    /**
     * 删除数据
     *
     * @param keys
     * @return
     */
    public Result removeData(String... keys) {
        if (this.data == null || !(this.data instanceof Map)) {
            return this;
        }
        Map<String, Object> map = (Map<String, Object>) this.data;
        for (String key : keys) {
            map.remove(key);
        }
        return this;
    }

    /**
     * 清空返回数据
     *
     * @return
     */
    public Result clearData() {
        this.data = null;
        return this;
    }

    /**
     * 获取dataMap 的值
     *
     * @param key key
     * @return
     */
    public Object get(String key) {
        if (this.data == null || StrKit.isEmpty(key) || !(this.data instanceof Map)) {
            return null;
        }
        Map<String, Object> map = (Map<String, Object>) this.data;
        return map.get(key);
    }

    /**
     * 获取字符串值
     *
     * @param key key
     * @return 不存在返回null
     */
    public String getString(String key) {
        return SafeKit.getString(get(key));
    }

    /**
     * 获取int类型
     *
     * @param key key
     * @return 不存在或不是整型 返回null
     */
    public Integer getInteger(String key) {
        return SafeKit.getInteger(get(key));
    }

    /**
     * 获取Long类型
     *
     * @param key key
     * @return 不存在或不是Long型 返回null
     */
    public Long getLong(String key) {
        return SafeKit.getLong(get(key));
    }


    /**
     * 获取Double类型
     *
     * @param key key
     * @return 不存在或不是Double型 返回null
     */
    public Double getDouble(String key) {
        return SafeKit.getDouble(get(key));
    }

    /**
     * 获取Boolean类型
     *
     * @param key key
     * @return 不存在或不是Boolean型 返回null
     */
    public Boolean getBoolean(String key) {
        return SafeKit.getBoolean(get(key));
    }
}