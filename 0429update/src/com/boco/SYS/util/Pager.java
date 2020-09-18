package com.boco.SYS.util;

/**
 * 
 * 
 * 分页工具类
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     雷大鹏    新建
 * </pre>
 */
public class Pager {
	private int totalRows;
	private int pageSize = 10;
	private int pageNo = 1;
	private int totalPages = 0;
	private int startRow;

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartRow() {
		if (this.startRow == 0)
			return ((this.pageNo - 1) * this.pageSize);

		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
}