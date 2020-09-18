package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 角色功能对照表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebRoleFun extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 角色编码 */
	private java.lang.String roleCode;
	/** 功能编码 */
	private java.lang.String funCode;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 角色编码 */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** 功能编码 */
	public void setFunCode(java.lang.String funCode) {
		this.funCode = funCode == null ? null : funCode.trim();
	}
	public java.lang.String getFunCode() {
		return this.funCode;
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