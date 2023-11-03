package com.bank.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharacterFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 把req的默认字符集改成UTF-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 放行
        chain.doFilter(request,response);
    }
}
