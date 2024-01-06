package cn.lfj.servlet;

import cn.lfj.tools.DbUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2024-01-06 11:06
 */

@WebServlet(name = "DelDiary",urlPatterns = "/DelDiary")
public class DelDiary extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String sql = "delete from diary where id = '"+id+"'";
            DbUtils dbUtils = new DbUtils();
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
