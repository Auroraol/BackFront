package com.aster.cloud.svc.log.api.context;

/**
 * 日志异步监听事件
 *
 * @author 王骞
 * @date 2021-01-13
 */
public interface ILogListener<T> {

	/**
	 * 保存日志
	 * @param event 日志事件
	 */
	void saveLog(ILogEvent<T> event);
}
