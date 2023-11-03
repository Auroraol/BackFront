package com.lfj.interceptor;

import com.alibaba.fastjson2.JSON;
import com.lfj.common.vo.Result;
import com.lfj.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: LFJ
 * @Date: 2023-09-26 21:31
 */

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("X-Token");  // 前端把token放在请求头用X-Token保存
		//System.out.println(request.getRequestURI() +" 待验证："+token);
		if (token != null) {
			try {
				jwtUtil.verify(token);
				log.debug(request.getRequestURI() + " 放行...");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.debug(request.getRequestURI() + " 禁止访问...");

		// 服务器返回
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(Result.fail(20003, "jwt令牌无效，请重新登录")));
		return false;
	}
}
