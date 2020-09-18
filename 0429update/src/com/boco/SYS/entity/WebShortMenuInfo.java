package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebShortMenuInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebShortMenuInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 柜员号 */
	private java.lang.String operCode;
	/** 菜单编号 */
	private java.lang.String menuCode;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 柜员号 */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
	/** 菜单编号 */
	public void setMenuCode(java.lang.String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}
	public java.lang.String getMenuCode() {
		return this.menuCode;
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