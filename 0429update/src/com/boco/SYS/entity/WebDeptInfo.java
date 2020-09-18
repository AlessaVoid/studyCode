package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebDeptInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebDeptInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 部门编码 */
	private java.lang.String deptCode;
	/** 部门名称 */
	private java.lang.String deptName;
	/** 上级部门编码 */
	private java.lang.String upDeptCode;
	/** 所属机构编码 */
	private java.lang.String organcode;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** 上级部门名称 */
	private java.lang.String upDeptName;
	
	public java.lang.String getUpDeptName() {
		return upDeptName;
	}
	public void setUpDeptName(java.lang.String upDeptName) {
		this.upDeptName = upDeptName;
	}
	/** setter\getter方法 */
	/** 部门编码 */
	public void setDeptCode(java.lang.String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}
	public java.lang.String getDeptCode() {
		return this.deptCode;
	}
	/** 部门名称 */
	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}
	public java.lang.String getDeptName() {
		return this.deptName;
	}
	/** 上级部门编码 */
	public void setUpDeptCode(java.lang.String upDeptCode) {
		this.upDeptCode = upDeptCode == null ? null : upDeptCode.trim();
	}
	public java.lang.String getUpDeptCode() {
		return this.upDeptCode;
	}
	/** 所属机构编码 */
	public void setOrgancode(java.lang.String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public java.lang.String getOrgancode() {
		return this.organcode;
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