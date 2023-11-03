package com.bank.dto;

import com.bank.model.Bankcard;

import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-07-17 15:28
 */
public class BankcardDto {

	List<Bankcard> list;         // 每条数据
	private int recordCount;	// 总共的条数

	public List<Bankcard> getList() {
		return list;
	}

	public void setList(List<Bankcard> list) {
		this.list = list;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}
