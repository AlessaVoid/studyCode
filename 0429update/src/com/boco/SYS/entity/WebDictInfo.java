package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebDictInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebDictInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 序号 */
	private java.lang.String id;
	/** 分组代码 */
	private java.lang.String groupCode;
	/** 分组名称 */
	private java.lang.String groupName;
	/** 参数值 */
	private java.lang.String argumentValue;
	/** 参数名称 */
	private java.lang.String argumentName;
	/** 排序 */
	private java.lang.String orderNo;
	/** 内容描述 */
	private java.lang.String contentDescribe;
	/** 参数状态 */
	private java.lang.String argumentStatus;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	/** 排序字符串 */
	private String sortColumns;
	
	/** setter\getter方法 */
	/** 序号 */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** 分组代码 */
	public void setGroupCode(java.lang.String groupCode) {
		this.groupCode = groupCode == null ? null : groupCode.trim();
	}
	public java.lang.String getGroupCode() {
		return this.groupCode;
	}
	/** 分组名称 */
	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}
	public java.lang.String getGroupName() {
		return this.groupName;
	}
	/** 参数值 */
	public void setArgumentValue(java.lang.String argumentValue) {
		this.argumentValue = argumentValue == null ? null : argumentValue.trim();
	}
	public java.lang.String getArgumentValue() {
		return this.argumentValue;
	}
	/** 参数名称 */
	public void setArgumentName(java.lang.String argumentName) {
		this.argumentName = argumentName == null ? null : argumentName.trim();
	}
	public java.lang.String getArgumentName() {
		return this.argumentName;
	}
	/** 排序 */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** 内容描述 */
	public void setContentDescribe(java.lang.String contentDescribe) {
		this.contentDescribe = contentDescribe == null ? null : contentDescribe.trim();
	}
	public java.lang.String getContentDescribe() {
		return this.contentDescribe;
	}
	/** 参数状态 */
	public void setArgumentStatus(java.lang.String argumentStatus) {
		this.argumentStatus = argumentStatus == null ? null : argumentStatus.trim();
	}
	public java.lang.String getArgumentStatus() {
		return this.argumentStatus;
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
	/** 排序字符串 */
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public String getSortColumns() {
		return sortColumns;
	}
}