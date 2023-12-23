package com.bank.dao;

import com.bank.dto.BankcardDto;
import com.bank.model.Bankcard;
import com.bank.utils.JDBCUtil;

import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 19:00
 */
public class BankcardDao {

	private final JDBCUtil conn = new JDBCUtil();
	private Connection cn;
	private PreparedStatement ps;
	private ResultSet rs;

	public BankcardDto list() throws SQLException {
		BankcardDto bankcardDto = new BankcardDto();
		int count = 0;
		cn = conn.getConnection();
		String sql = "SELECT * FROM `bankcard`";
		ps = cn.prepareStatement(sql);
		List<Bankcard> list = new ArrayList<>();
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				int accountid = rs.getInt(1);
				double cardid = rs.getDouble (2);
				double cardnumber = rs.getDouble(3);
				double balance = rs.getDouble(4);
				int status = rs.getInt(5);
				String cardpassword = rs.getString(6);   //密码
				Bankcard bankcard = new Bankcard(accountid, cardid, cardnumber, balance, status, cardpassword);
				list.add(bankcard);
				count++;
			}
			// 添加数据到bankcardDto中
			bankcardDto.setList(list);
			bankcardDto.setRecordCount(count);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JDBCUtil.clossConnection(cn, ps, rs);
		}
		return bankcardDto;
	}

	/**
	 * @deprecated 分页显示
	 * @return
	 * @throws SQLException
	 */
	public BankcardDto listSize() throws SQLException {
		BankcardDto bankcardDto = new BankcardDto();
		int count = 0;
		cn = conn.getConnection();
		String sql = "SELECT * FROM `bankcard`";
		ps = cn.prepareStatement(sql);
		List<Bankcard> list = new ArrayList<>();
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
			bankcardDto.setRecordCount(count);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JDBCUtil.clossConnection(cn, ps, rs);
		}
		return bankcardDto;
	}

	/**
	 * @deprecated 分页显示
	 * @return
	 * @throws SQLException
	 */
	public BankcardDto list(int start, int end) throws SQLException {
		BankcardDto bankcardDto = new BankcardDto();
		int count = 0;
		cn = conn.getConnection();
		String sql = "SELECT * FROM `bankcard`  LIMIT ?, ?";
		ps = cn.prepareStatement(sql);
		ps.setInt(1, start);
		ps.setInt(2, end);
		List<Bankcard> list = new ArrayList<>();
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				int accountid = rs.getInt(1);
				double cardid = rs.getDouble (2);
				double cardnumber = rs.getDouble(3);
				double balance = rs.getDouble(4);
				int status = rs.getInt(5);
				String cardpassword = rs.getString(6);   //密码
				Bankcard bankcard = new Bankcard(accountid, cardid, cardnumber, balance, status, cardpassword);
				list.add(bankcard);
				count++;
			}
			// 添加数据到bankcardDto中
			bankcardDto.setList(list);
			bankcardDto.setRecordCount(count);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JDBCUtil.clossConnection(cn, ps, rs);
		}
		return bankcardDto;
	}
}
