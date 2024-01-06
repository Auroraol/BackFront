package cn.lfj.tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2024-01-06 11:06
 */
public class DbUtils {
	//成员变量，全局变量
	private String driverClass = "com.mysql.jdbc.Driver";
	private String username = "root";
	private String password = "741106";
//	private String url = "jdbc:mysql://localhost:3308/lesson4";  // 报错
	private String url = "jdbc:mysql://localhost:3306/lesson4?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";

	//连接对象
	Connection conn = null;
	//语句对象
	Statement stmt = null;
	//结果集对象
	private ResultSet rs = null;
	//链接方法
	private void getConnection(){
		try{
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url,username,password);
			stmt = conn.createStatement();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//更新:insert update delete,create,drop
	public int update(String sql){
		try{
			//获取连接
			getConnection();
			//执行SQL语句
			int rows = stmt.executeUpdate(sql);
			return rows;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			//关闭资源
			close();/*反复关闭问题，对性能有影响*/
		}
		return -1;
	}

	//查询的封装 提示:返回值类型
	//查询方法
	public List<Map<String,Object>> query(String sql){
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			//获取连接
			getConnection();
			//直接查询sql语句
			rs = stmt.executeQuery(sql);
			//获取表结构
			ResultSetMetaData rsmt = rs.getMetaData();
			//获取字段个数
			int count = rsmt.getColumnCount();
			//将rs结果集中的数据进行遍历
			while (rs.next()){
				Map<String,Object> map = new HashMap<>();/*改一下JDBC*/
				for(int i = 1;i <= count;i++){
					//获取字段名
					String name = rsmt.getColumnName(i);/*不能用*/
					//通过字段名获取字段值
					String value = rs.getString(name);
					map.put(name,value);
				}
				list.add(map);
			}
			return list;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	//关闭方法
	private void close(){
		try{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
