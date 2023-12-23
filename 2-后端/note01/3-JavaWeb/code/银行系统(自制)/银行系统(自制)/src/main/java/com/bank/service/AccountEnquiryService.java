package com.bank.service;

import com.bank.dto.BankcardDto;
import com.bank.model.Bankcard;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 19:21
 */
public interface AccountEnquiryService {

	/**
	 * @deprecated  返回BankcardDto实现分页
	 * @return
	 * @throws SQLException
	 */
	public BankcardDto list()  throws SQLException;
	public BankcardDto bankcardSize()  throws SQLException;
	public BankcardDto bankcardInfo(int start, int end)  throws SQLException;
}
