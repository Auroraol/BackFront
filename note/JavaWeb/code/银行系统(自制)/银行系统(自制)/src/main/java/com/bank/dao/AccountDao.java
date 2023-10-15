package com.bank.dao;

import com.bank.model.Account;
import com.bank.model.Admin;
import com.bank.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:  数据库操作类
 * @date 2023-07-16 13:11
 */
public class AccountDao {
    private final JDBCUtil conn = new JDBCUtil();
    private Connection cn;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * 用户登录验证,(查找账号)
     *
     * @param username 账号
     * @return Account || null
     */
    public Account findUserName(String username) throws SQLException {
        cn = conn.getConnection();
        String sql = "SELECT * FROM `account` WHERE `username`=?";
        ps = cn.prepareStatement(sql);
        ps.setString(1, username);
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                // 数据库中有数据
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String passw = rs.getString(3);
                return new Account(id, name, passw);  //返回查找的对象
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.clossConnection(cn, ps, rs);
        }
        return null;
    }

    /**
     * 管理员登录验证,(查找账号)
     *
     * @param username 账号
     * @return Account || null
     */
    public Admin findAdminName(String username) throws SQLException {
        cn = conn.getConnection();
        String sql = "SELECT * FROM `admin` WHERE `username`=?";
        ps = cn.prepareStatement(sql);
        ps.setString(1, username);
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                // 数据库中有数据
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String passw = rs.getString(3);
                return new Admin(id, name, passw);  //返回查找的对象
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.clossConnection(cn, ps, rs);
        }
        return null;
    }

    /**
     * 用户注册
     *
     * @param username 账号
     * @param password 密码
     * @return Account || null
     */
    public Integer userRegister(String username, String password) throws SQLException {
        cn = conn.getConnection();
        String sql = "INSERT  INTO account(username, password) VALUES (?,?)";
        ps = cn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        try {
            int n = ps.executeUpdate(); // 执行插入操作，返回受影响的行数
            if (n > 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.clossConnection(cn, ps, rs);
        }
        return -1;
    }
}