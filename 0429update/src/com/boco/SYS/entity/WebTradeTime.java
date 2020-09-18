package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebTradeTime实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebTradeTime extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 功能编码 */
	private java.lang.String menuCode;
	/** 开始时间 */
	private java.lang.String beginTime;
	/** 终止时间 */
	private java.lang.String endTime;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 功能编码 */
	public void setMenuCode(java.lang.String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}
	public java.lang.String getMenuCode() {
		return this.menuCode;
	}
	/** 开始时间 */
	public void setBeginTime(java.lang.String beginTime) {
		this.beginTime = beginTime == null ? null : beginTime.trim();
	}
	public java.lang.String getBeginTime() {
		return this.beginTime;
	}
	/** 终止时间 */
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	/** 最后修改日期 */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** 最后修改时间 */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** 最后操作员 */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
}