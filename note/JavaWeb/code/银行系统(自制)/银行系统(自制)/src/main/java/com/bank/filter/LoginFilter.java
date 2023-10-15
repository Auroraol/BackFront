package com.bank.filter;

import com.bank.model.Account;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",
        servletNames = {
                "TransferServlet", "TradeListByTimeServlet", "TradeListServlet", "TransferServlet"
        },
        value = {
                // 如果是白名单的地址，直接放行
                "/index.jsp",
                "/transfer.jsp",
                "/access.jsp",
                "/admin/manager.jsp"
        })
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // 获取session
        Account admin = (Account) req.getSession().getAttribute("admin");
        Account user = (Account) req.getSession().getAttribute("user");
        // 判断用户有没有登录
//        if (admin != null  &&  user == null){
//            // 回到管理员页
//            resp.sendRedirect("/admin/manager.jsp");
//        }
//        if (admin == null  &&  user != null){
//            resp.sendRedirect("/index.jsp");
//        }
        if (admin != null || user != null){
            // 登录了，直接放行
            chain.doFilter(request, response);
        }else {
            resp.sendRedirect("login.jsp");
        }
    }
}
