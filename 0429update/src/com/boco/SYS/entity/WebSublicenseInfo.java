package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebSublicenseInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebSublicenseInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 主键 */
	private java.lang.String id;
	/** 转出人代码 */
	private java.lang.String outOper;
	/** 转入人代码 */
	private java.lang.String inOper;
	/** 授权角色编码 */
	private java.lang.String roleCode;
	/** 转出日期 */
	private java.lang.String outDate;
	/** 转出时间 */
	private java.lang.String outTime;
	/** 收回日期 */
	private java.lang.String inDate;
	/** 收回时间 */
	private java.lang.String inTime;
	/** 是否收回,1-收回 2-未收回 */
	private java.lang.String isBack;
	/**转入转出标志*/
	private java.lang.String outOrIn;
	
	/** setter\getter方法 */
	/** 主键 */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** 转出人代码 */
	public void setOutOper(java.lang.String outOper) {
		this.outOper = outOper == null ? null : outOper.trim();
	}
	public java.lang.String getOutOper() {
		return this.outOper;
	}
	/** 转入人代码 */
	public void setInOper(java.lang.String inOper) {
		this.inOper = inOper == null ? null : inOper.trim();
	}
	public java.lang.String getInOper() {
		return this.inOper;
	}
	/** 授权角色编码 */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** 转出日期 */
	public void setOutDate(java.lang.String outDate) {
		this.outDate = outDate == null ? null : outDate.trim();
	}
	public java.lang.String getOutDate() {
		return this.outDate;
	}
	/** 转出时间 */
	public void setOutTime(java.lang.String outTime) {
		this.outTime = outTime == null ? null : outTime.trim();
	}
	public java.lang.String getOutTime() {
		return this.outTime;
	}
	/** 收回日期 */
	public void setInDate(java.lang.String inDate) {
		this.inDate = inDate == null ? null : inDate.trim();
	}
	public java.lang.String getInDate() {
		return this.inDate;
	}
	/** 收回时间 */
	public void setInTime(java.lang.String inTime) {
		this.inTime = inTime == null ? null : inTime.trim();
	}
	public java.lang.String getInTime() {
		return this.inTime;
	}
	/** 是否收回,1-收回 2-未收回 */
	public void setIsBack(java.lang.String isBack) {
		this.isBack = isBack == null ? null : isBack.trim();
	}
	public java.lang.String getIsBack() {
		return this.isBack;
	}
	/**转入转出标志*/
	public java.lang.String getOutOrIn() {
		return outOrIn;
	}
	/**转入转出标志*/
	public void setOutOrIn(java.lang.String outOrIn) {
		this.outOrIn = outOrIn;
	}
	
}