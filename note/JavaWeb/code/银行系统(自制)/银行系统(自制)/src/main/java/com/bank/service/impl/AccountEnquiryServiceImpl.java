package com.bank.service.impl;

//import com.bank.controller.AccountEnquiryServlet;
import com.bank.dao.BankcardDao;
import com.bank.dto.BankcardDto;
import com.bank.model.Bankcard;
import com.bank.service.AccountEnquiryService;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 19:21
 */
public class AccountEnquiryServiceImpl implements  AccountEnquiryService{

	private BankcardDao bankcardDao = new BankcardDao();

	@Override
	public BankcardDto list() throws SQLException {
		return this.bankcardDao.list();
	}

	@Override
	public BankcardDto bankcardSize() throws SQLException {
		return  this.bankcardDao.listSize();
	}

	@Override
	public BankcardDto bankcardInfo(int start, int end)  throws SQLException {
		return this.bankcardDao.list(start, end);
	}
}


