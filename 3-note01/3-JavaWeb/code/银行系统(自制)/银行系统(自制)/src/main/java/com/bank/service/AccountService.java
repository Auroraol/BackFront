package com.bank.service;

import com.bank.dto.AccountDto;

import java.sql.SQLException;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 14:48
 */
public interface AccountService {
	public AccountDto userLogin(String username, String password) throws SQLException;

	public AccountDto adminLogin(String username, String password) throws SQLException;
	public Integer register(String username, String password) throws SQLException;
}
