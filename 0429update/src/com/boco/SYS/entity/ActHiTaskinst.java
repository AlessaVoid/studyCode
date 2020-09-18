package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActHiTaskinst实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class ActHiTaskinst extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	private java.lang.Object id;
	/** procDefId */
	private java.lang.Object procDefId;
	/** taskDefKey */
	private java.lang.Object taskDefKey;
	/** procInstId */
	private java.lang.Object procInstId;
	/** executionId */
	private java.lang.Object executionId;
	/** parentTaskId */
	private java.lang.Object parentTaskId;
	/** name */
	private java.lang.Object name;
	/** description */
	private java.lang.Object description;
	/** owner */
	private java.lang.Object owner;
	/** assignee */
	private java.lang.Object assignee;
	/** startTime */
	private java.lang.String startTime;
	/** claimTime */
	private java.lang.String claimTime;
	/** endTime */
	private java.lang.String endTime;
	/** duration */
	private java.lang.String duration;
	/** deleteReason */
	private java.lang.Object deleteReason;
	/** priority */
	private java.lang.String priority;
	/** dueDate */
	private java.lang.String dueDate;
	/** formKey */
	private java.lang.Object formKey;
	/** category */
	private java.lang.Object category;
	/** tenantId */
	private java.lang.Object tenantId;
	
	/** setter\getter方法 */
	/** id */
	public void setId(java.lang.Object id) {
		this.id = id;
	}
	public java.lang.Object getId() {
		return this.id;
	}
	/** procDefId */
	public void setProcDefId(java.lang.Object procDefId) {
		this.procDefId = procDefId;
	}
	public java.lang.Object getProcDefId() {
		return this.procDefId;
	}
	/** taskDefKey */
	public void setTaskDefKey(java.lang.Object taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public java.lang.Object getTaskDefKey() {
		return this.taskDefKey;
	}
	/** procInstId */
	public void setProcInstId(java.lang.Object procInstId) {
		this.procInstId = procInstId;
	}
	public java.lang.Object getProcInstId() {
		return this.procInstId;
	}
	/** executionId */
	public void setExecutionId(java.lang.Object executionId) {
		this.executionId = executionId;
	}
	public java.lang.Object getExecutionId() {
		return this.executionId;
	}
	/** parentTaskId */
	public void setParentTaskId(java.lang.Object parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public java.lang.Object getParentTaskId() {
		return this.parentTaskId;
	}
	/** name */
	public void setName(java.lang.Object name) {
		this.name = name;
	}
	public java.lang.Object getName() {
		return this.name;
	}
	/** description */
	public void setDescription(java.lang.Object description) {
		this.description = description;
	}
	public java.lang.Object getDescription() {
		return this.description;
	}
	/** owner */
	public void setOwner(java.lang.Object owner) {
		this.owner = owner;
	}
	public java.lang.Object getOwner() {
		return this.owner;
	}
	/** assignee */
	public void setAssignee(java.lang.Object assignee) {
		this.assignee = assignee;
	}
	public java.lang.Object getAssignee() {
		return this.assignee;
	}
	/** startTime */
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	/** claimTime */
	public void setClaimTime(java.lang.String claimTime) {
		this.claimTime = claimTime == null ? null : claimTime.trim();
	}
	public java.lang.String getClaimTime() {
		return this.claimTime;
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
	/** deleteReason */
	public void setDeleteReason(java.lang.Object deleteReason) {
		this.deleteReason = deleteReason;
	}
	public java.lang.Object getDeleteReason() {
		return this.deleteReason;
	}
	/** priority */
	public void setPriority(java.lang.String priority) {
		this.priority = priority == null ? null : priority.trim();
	}
	public java.lang.String getPriority() {
		return this.priority;
	}
	/** dueDate */
	public void setDueDate(java.lang.String dueDate) {
		this.dueDate = dueDate == null ? null : dueDate.trim();
	}
	public java.lang.String getDueDate() {
		return this.dueDate;
	}
	/** formKey */
	public void setFormKey(java.lang.Object formKey) {
		this.formKey = formKey;
	}
	public java.lang.Object getFormKey() {
		return this.formKey;
	}
	/** category */
	public void setCategory(java.lang.Object category) {
		this.category = category;
	}
	public java.lang.Object getCategory() {
		return this.category;
	}
	/** tenantId */
	public void setTenantId(java.lang.Object tenantId) {
		this.tenantId = tenantId;
	}
	public java.lang.Object getTenantId() {
		return this.tenantId;
	}
}