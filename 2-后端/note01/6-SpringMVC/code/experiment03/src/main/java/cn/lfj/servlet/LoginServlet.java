package cn.lfj.servlet;

import cn.lfj.tools.DbUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2024-01-06 11:06
 */

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           request.setCharacterEncoding("utf-8");
           response.setCharacterEncoding("utf-8");
           String id = request.getParameter("id");
           String password = request.getParameter("password");
           //校验
           DbUtils dbUtils = new DbUtils();
           String sql = "select name,password from user where id = '"+id+"'";
           List<Map<String,Object>> list = dbUtils.query(sql);
           if (!list.isEmpty()){
               //账号存在
               if (password.equals(list.get(0).get("password"))){
                    //校验成功
                   HttpSession session = request.getSession();
                   session.setAttribute("name",list.get(0).get("name"));
                   response.sendRedirect("main.jsp");
               }else {
                   //密码错误
                   response.sendRedirect("index.jsp");
               }
           }else {
               //账号为空
               response.sendRedirect("index.jsp");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
