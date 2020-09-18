package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebRoleInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebRoleInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 角色编号 */
	private java.lang.String roleCode;
	/** 角色名称 */
	private java.lang.String roleName;
	/** 机构级别 0-全国中心,1-省级,2-地区,3-市,县级,4-支局所 */
	private java.lang.String organLevel;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 角色编号 */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** 角色名称 */
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	public java.lang.String getRoleName() {
		return this.roleName;
	}
	/** 机构级别 0-全国中心,1-省级,2-地区,3-市,县级,4-支局所 */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
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