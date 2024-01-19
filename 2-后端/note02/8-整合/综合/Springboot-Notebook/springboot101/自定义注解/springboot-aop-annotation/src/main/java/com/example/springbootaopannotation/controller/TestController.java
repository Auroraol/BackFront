package com.example.springbootaopannotation.controller;

import com.example.springbootaopannotation.aspect.TakeTime;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 21:16
 */

@Controller
public class TestController {

	@RequestMapping("/loadForTestVariableCategories")
	@TakeTime(methodName = "loadForTestVariableCategories")
	public void loadForTestVariableCategories(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int n = 0;
		for (int i = 0; i < 100000; i++) {
			n += i;
		}
	}
}
