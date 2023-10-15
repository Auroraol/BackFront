package com.bank.utils;

/**
 * @Author: LFJ
 * @Date: 2023-07-17 14:55
 */
public class PageUtil {
	// 每页显示的条数
	private int pageSize;
	// 总共的条数
	private int recordCount;
	// 当前页面
	private int currentPage;
	//总页数
	public int pageCount;
	//上一页的最后一条记录
	private int start;
	//当前页的最后一条记录
	private int end;

	// 获取上一页的最后一条记录
	public int getStart() {
		start=(currentPage - 1) * pageSize;
		return start;
	}
	//为mysql写的 select * from table limit start,end;
	//limit是限制查询从start+1开始，最多查询end条数据
	public int getEnd() {
		end=pageSize;
		return end;
	}

	// 构造方法
	public PageUtil(int pageSize, int recordCount, int currentPage) {
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		setCurrentPage(currentPage);
	}

	// 构造方法
	public PageUtil(int pageSize, int recordCount) {
		this(pageSize, recordCount, 1);
	}

	public PageUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 总页数
	public int getPageCount() {
		// 总条数/每页显示的条数=总页数
		int size = recordCount / pageSize;
		// 最后一页的条数
		int mod = recordCount % pageSize;
		// 看需不需要多余的页，也就是最后一页
		if (mod != 0)
			size++;
		this.pageCount=recordCount == 0 ? 1 : size;
		return this.pageCount;
	}

	// 上一页的最后一条记录数。包含，起始索引为0
	public int getFromIndex() {
		// System.out.println("from index:"+(currentPage-1) * pageSize);
		return (currentPage - 1) * pageSize;
	}

	// 本页的最后一条记录数。不包含
	public int getToIndex() {
		// System.out.println("to index:"+Math.min(recordCount, currentPage *
		// pageSize));
		return Math.min(recordCount, currentPage * pageSize);
	}

	// 得到当前页
	public int getCurrentPage() {
		return currentPage;
	}

	// 设置当前页
	public void setCurrentPage(int currentPage) {
		int validPage = currentPage <= 0 ? 1 : currentPage;
		validPage = validPage > getPageCount() ? getPageCount() : validPage;
		this.currentPage = validPage;
	}

	// 得到每页显示的条数
	public int getPageSize() {
		return pageSize;
	}

	// 设置每页显示的条数
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 得到总共的条数
	public int getRecordCount() {
		return recordCount;
	}

	// 设置总共的条数
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}
