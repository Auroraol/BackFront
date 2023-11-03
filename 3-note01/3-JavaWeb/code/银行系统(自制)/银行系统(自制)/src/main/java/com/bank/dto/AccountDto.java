package com.bank.dto;

import com.bank.model.Account;
import com.bank.model.Admin;

/**
 * @Author: LFJ
 * @description:  验证登录
 * @Date: 2023-07-16 14:00
 */
public class AccountDto {

	private Integer code;
	private Account account;
	private Admin adminAccount;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Account getAccount() {
		return account;
	}

	public Admin getAdminAccount() {
		return adminAccount;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	public void setAccount(Admin account) {
		this.adminAccount = account;
	}
}
