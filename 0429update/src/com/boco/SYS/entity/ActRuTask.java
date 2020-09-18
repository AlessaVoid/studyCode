package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActRuTask实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class ActRuTask extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	private java.lang.Object id;
	/** rev */
	private java.lang.String rev;
	/** executionId */
	private java.lang.Object executionId;
	/** procInstId */
	private java.lang.Object procInstId;
	/** procDefId */
	private java.lang.Object procDefId;
	/** name */
	private java.lang.Object name;
	/** parentTaskId */
	private java.lang.Object parentTaskId;
	/** description */
	private java.lang.Object description;
	/** taskDefKey */
	private java.lang.Object taskDefKey;
	/** owner */
	private java.lang.Object owner;
	/** assignee */
	private java.lang.Object assignee;
	/** delegation */
	private java.lang.Object delegation;
	/** priority */
	private java.lang.String priority;
	/** createTime */
	private java.lang.String createTime;
	/** dueDate */
	private java.lang.String dueDate;
	/** category */
	private java.lang.Object category;
	/** suspensionState */
	private java.lang.String suspensionState;
	/** tenantId */
	private java.lang.Object tenantId;
	/** formKey */
	private java.lang.Object formKey;
	
	/** setter\getter方法 */
	/** id */
	public void setId(java.lang.Object id) {
		this.id = id;
	}
	public java.lang.Object getId() {
		return this.id;
	}
	/** rev */
	public void setRev(java.lang.String rev) {
		this.rev = rev == null ? null : rev.trim();
	}
	public java.lang.String getRev() {
		return this.rev;
	}
	/** executionId */
	public void setExecutionId(java.lang.Object executionId) {
		this.executionId = executionId;
	}
	public java.lang.Object getExecutionId() {
		return this.executionId;
	}
	/** procInstId */
	public void setProcInstId(java.lang.Object procInstId) {
		this.procInstId = procInstId;
	}
	public java.lang.Object getProcInstId() {
		return this.procInstId;
	}
	/** procDefId */
	public void setProcDefId(java.lang.Object procDefId) {
		this.procDefId = procDefId;
	}
	public java.lang.Object getProcDefId() {
		return this.procDefId;
	}
	/** name */
	public void setName(java.lang.Object name) {
		this.name = name;
	}
	public java.lang.Object getName() {
		return this.name;
	}
	/** parentTaskId */
	public void setParentTaskId(java.lang.Object parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public java.lang.Object getParentTaskId() {
		return this.parentTaskId;
	}
	/** description */
	public void setDescription(java.lang.Object description) {
		this.description = description;
	}
	public java.lang.Object getDescription() {
		return this.description;
	}
	/** taskDefKey */
	public void setTaskDefKey(java.lang.Object taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public java.lang.Object getTaskDefKey() {
		return this.taskDefKey;
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
	/** delegation */
	public void setDelegation(java.lang.Object delegation) {
		this.delegation = delegation;
	}
	public java.lang.Object getDelegation() {
		return this.delegation;
	}
	/** priority */
	public void setPriority(java.lang.String priority) {
		this.priority = priority == null ? null : priority.trim();
	}
	public java.lang.String getPriority() {
		return this.priority;
	}
	/** createTime */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public java.lang.String getCreateTime() {
		return this.createTime;
	}
	/** dueDate */
	public void setDueDate(java.lang.String dueDate) {
		this.dueDate = dueDate == null ? null : dueDate.trim();
	}
	public java.lang.String getDueDate() {
		return this.dueDate;
	}
	/** category */
	public void setCategory(java.lang.Object category) {
		this.category = category;
	}
	public java.lang.Object getCategory() {
		return this.category;
	}
	/** suspensionState */
	public void setSuspensionState(java.lang.String suspensionState) {
		this.suspensionState = suspensionState == null ? null : suspensionState.trim();
	}
	public java.lang.String getSuspensionState() {
		return this.suspensionState;
	}
	/** tenantId */
	public void setTenantId(java.lang.Object tenantId) {
		this.tenantId = tenantId;
	}
	public java.lang.Object getTenantId() {
		return this.tenantId;
	}
	/** formKey */
	public void setFormKey(java.lang.Object formKey) {
		this.formKey = formKey;
	}
	public java.lang.Object getFormKey() {
		return this.formKey;
	}
}