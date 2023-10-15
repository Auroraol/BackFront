package com.bank.utils;


import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

/**
 * @description: 数据库连接类
 * @date 2023-07-16 12:00
 */
public class JDBCUtil {

    private static String driver="";
    private static String url="";
    private static String username="";
    private static String password="";

    private  static Connection connection = null;
    /*
     * 静态代码块，配置信息
     */
    static {
        try {
            //1.Properties集合类
            Properties properties = new Properties();
            //2.加载文件
            properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            /*
            ClassLoader classLoader=JDBCUtil.class.getClassLoader();
            String path= Objects.requireNonNull(classLoader.getResource("jdbc.properties")).getFile();
            path=java.net.URLDecoder.decode(path,"utf-8");
            properties.load(new FileReader(path));
            */
             url = properties.getProperty("jdbc.url");
             username = properties.getProperty("jdbc.username");
             password = properties.getProperty("jdbc.password");
             driver = properties.getProperty("jdbc.driver");
            // 注册驱动
            Class.forName(driver);
        }catch (IOException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return connection;
    }

    /**
     * 释放资源
     * @param rs 	结果集对象
     * @param stmt	执行SQL语句对象
     * @param conn	连接对象
     */
    public static void clossConnection(Connection conn, Statement stmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*测试
    public static void main(String[] args) throws SQLException {
        System.out.println(JDBCUtil.getConnection());
    }
    */
}
