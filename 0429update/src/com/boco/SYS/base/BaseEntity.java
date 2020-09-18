package com.boco.SYS.base;
/**
 * 
 * 
 *  实体类基类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月30日    	    杨滔    新建
 * </pre>
 */
public abstract class BaseEntity{
	/** 排序字段 */
	protected java.lang.String sortColumn;
	
	public java.lang.String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(java.lang.String sortColumn) {
		this.sortColumn = sortColumn == null ? null : sortColumn.trim();
	}
	
	
}
