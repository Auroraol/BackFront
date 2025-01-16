package com.aster.cloud.svc.log.api.context;

/**
 * 日志事件接口类
 *
 * @author 王骞
 * @date 2021-01-13
 */
public interface ILogEvent<T> {

	/**
	 * 获取日志
	 * @return
	 */
	T getLog();

}
