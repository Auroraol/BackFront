package com.example.template.exception;

import com.example.template.common.vo.ResponseResult;
import com.example.template.enums.ResponseCodeEnum;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 14:02
 * 自定义的异常类-业务中发生的异常
 */
public class BizException extends RuntimeException {
	private int code;

	public BizException(ResponseCodeEnum responseCode) {
		super(responseCode.getMessage());
		this.code = responseCode.getCode();
	}

//	public BizException(ResponseResult responseResult) {
//		super(responseResult.getMessage());
//		this.code = responseResult.getCode();
//	}
	// Getter 方法
	public int getCode() {
		return code;
	}
}
