package cn.lfj.exception.resolver;

import cn.lfj.exception.PermissionException;
import javafx.fxml.LoadException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LFJ
 * @Date: 2023-10-18 21:22
 * <p>
 * 将在 handler中抛出异常时执行
 * 将捕获任何一个handler的中任何一个异常
 * * 异常解析器：主体逻辑
 * * 执行时刻：当handler中抛出异常时，会执行：捕获异常，并可以跳到错误页面
 * @param: httpServletRequest
 * @param: httpServletResponse
 * @param: o
 * @param: e
 * @return 返回一个ModelAndview，作用在于跳转错误视图
 */
public class MyExResolver implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
		e.printStackTrace();//打印异常栈, 上线时删除
		//创建一个ModelAndView
		ModelAndView mv = new ModelAndView();
		//识别异常
		if (e instanceof LoadException) {
			mv.setViewName("redirect:/user/login");   //跳转handler，handler转发login.jsp
		} else if (e instanceof PermissionException) {
			mv.setViewName("redirect:/user/perm");   //跳转handler,handler 转发 权限不错误页面
		} else {
			mv.setViewName("redirect:/user/global");
		}
		return mv;
	}
}
