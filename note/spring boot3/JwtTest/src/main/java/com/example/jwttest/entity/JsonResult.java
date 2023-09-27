package com.example.jwttest.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-09-26 14:14
 */

/**
 * 通用JsonResult
 */
@Data
public class JsonResult {
	private static final long serialVersionUID =-123847128341023033L;
	@JSONField
	private boolean success = true;
	@JSONField
	private String message = null;
	@JSONField
	private String errorCode = "0";
	@JSONField
	private String errorMsg = "";
	@JSONField
	private Integer total = 0;
	@JSONField
	private List data = new ArrayList();
//	private T data;

	public JsonResult() {

	}

	/**
	 * 当有异常时，直接throw一个实现ErrorCode的异常类
	 * 通过global异常处理器，就可以把jsonResult封装起来，这样代码简洁优美
	 * 如果没有BaseException可以注释掉这个方法
	 */
//    public JsonResult(BaseException exception) {
//        if (exception != null) {
//            success = false;
//            errorCode = exception.getErrorCode();
//            errorMsg = exception.getErrorMsg();
//        }
//    }

	/**
	 *  虽然很多人都写为isSuccess(),但强烈不建议，因为相当于getSuccess()
	 *  可以用idea的自动生成下，如果有isSuccess()，就不会生成getSuccess()
	 */
	public boolean successFlag() {
		return success;
	}

	public JsonResult(List data) {
		if (data != null && data.size() > 0) {
			this.data = data;
			this.message = "操作成功";
		}else {
			this.message = "操作失败";
			this.errorCode = "405";
			this.errorMsg = "返回值为null";
		}
	}
//
	public <T> void JsonResult(T data) {
		if (data != null) {
			this.data.add(data);
			this.message = "操作成功";
		}else {
			this.message = "操作失败";
			this.errorCode = "405";
			this.errorMsg = "返回值为null";
		}
	}
	//  失败情况的构造  只用errorCode，errorMsg即可
	public JsonResult(String errorCode,String errorMsg) {
		this.success=false;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}


	// xxx
	public void error(String s){
		System.out.println(s);
	}
}
