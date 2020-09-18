package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActHiProcinst实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class ActHiProcinst extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	private java.lang.Object id;
	/** procInstId */
	private java.lang.Object procInstId;
	/** businessKey */
	private java.lang.Object businessKey;
	/** procDefId */
	private java.lang.Object procDefId;
	/** startTime */
	private java.lang.String startTime;
	/** endTime */
	private java.lang.String endTime;
	/** duration */
	private java.lang.String duration;
	/** startUserId */
	private java.lang.Object startUserId;
	/** startActId */
	private java.lang.Object startActId;
	/** endActId */
	private java.lang.Object endActId;
	/** superProcessInstanceId */
	private java.lang.Object superProcessInstanceId;
	/** deleteReason */
	private java.lang.Object deleteReason;
	/** tenantId */
	private java.lang.Object tenantId;
	/** name */
	private java.lang.Object name;
	
	/** setter\getter方法 */
	/** id */
	public void setId(java.lang.Object id) {
		this.id = id;
	}
	public java.lang.Object getId() {
		return this.id;
	}
	/** procInstId */
	public void setProcInstId(java.lang.Object procInstId) {
		this.procInstId = procInstId;
	}
	public java.lang.Object getProcInstId() {
		return this.procInstId;
	}
	/** businessKey */
	public void setBusinessKey(java.lang.Object businessKey) {
		this.businessKey = businessKey;
	}
	public java.lang.Object getBusinessKey() {
		return this.businessKey;
	}
	/** procDefId */
	public void setProcDefId(java.lang.Object procDefId) {
		this.procDefId = procDefId;
	}
	public java.lang.Object getProcDefId() {
		return this.procDefId;
	}
	/** startTime */
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	/** endTime */
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	/** duration */
	public void setDuration(java.lang.String duration) {
		this.duration = duration == null ? null : duration.trim();
	}
	public java.lang.String getDuration() {
		return this.duration;
	}
	/** startUserId */
	public void setStartUserId(java.lang.Object startUserId) {
		this.startUserId = startUserId;
	}
	public java.lang.Object getStartUserId() {
		return this.startUserId;
	}
	/** startActId */
	public void setStartActId(java.lang.Object startActId) {
		this.startActId = startActId;
	}
	public java.lang.Object getStartActId() {
		return this.startActId;
	}
	/** endActId */
	public void setEndActId(java.lang.Object endActId) {
		this.endActId = endActId;
	}
	public java.lang.Object getEndActId() {
		return this.endActId;
	}
	/** superProcessInstanceId */
	public void setSuperProcessInstanceId(java.lang.Object superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}
	public java.lang.Object getSuperProcessInstanceId() {
		return this.superProcessInstanceId;
	}
	/** deleteReason */
	public void setDeleteReason(java.lang.Object deleteReason) {
		this.deleteReason = deleteReason;
	}
	public java.lang.Object getDeleteReason() {
		return this.deleteReason;
	}
	/** tenantId */
	public void setTenantId(java.lang.Object tenantId) {
		this.tenantId = tenantId;
	}
	public java.lang.Object getTenantId() {
		return this.tenantId;
	}
	/** name */
	public void setName(java.lang.Object name) {
		this.name = name;
	}
	public java.lang.Object getName() {
		return this.name;
	}
}