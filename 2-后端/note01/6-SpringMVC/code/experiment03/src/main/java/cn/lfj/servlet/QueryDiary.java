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

@WebServlet(name = "QueryDiary",urlPatterns = "/QueryDiary")
public class QueryDiary extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession();
            String name = session.getAttribute("name").toString();
            String query = request.getParameter("query");
            System.out.println(query);
            String sql;
            if (query == null){
                //正常来到我的日记
                sql = "select * from diary where name = '"+name+"'";
            }else {
                //模糊查询
                sql = "select * from diary where (title like '%"+query+"%' or date like '%"+query+"%' or content like '%"+query+"%') and name = '"+name+"'";
            }
            DbUtils dbUtils = new DbUtils();
            System.out.println(sql);
            List<Map<String,Object>> list = dbUtils.query(sql);
            request.setAttribute("list",list);
            request.getRequestDispatcher("query_diary.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
