package cn.lfj.servlet;

import cn.lfj.tools.DbUtils;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @Author: LFJ
 * @Date: 2024-01-06 11:06
 */

@WebServlet(name = "AddDiary",urlPatterns = "/AddDiary")
public class AddDiary extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            HttpSession session = request.getSession();
            String name = session.getAttribute("name").toString();
            LocalDate localDate = LocalDate.now();
            DbUtils dbUtils = new DbUtils();
            String sql;
            if (id == null){
                sql = "insert into diary (title,date,content,name) values ('"+title+"','"+localDate+"','"+content+"','"+name+"')";
            }else {
                //修改
                sql = "update diary set title = '"+title+"',date = '"+localDate+"',content = '"+content+"' where id = '"+id+"'";
            }
            int rows = dbUtils.update(sql);
            if (rows == 1){
                response.sendRedirect("QueryDiary");
            }else {
                response.sendRedirect("fail.jsp");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
