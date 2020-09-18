package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActHiVarinst实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class ActHiVarinst extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	private java.lang.Object id;
	/** procInstId */
	private java.lang.Object procInstId;
	/** executionId */
	private java.lang.Object executionId;
	/** taskId */
	private java.lang.Object taskId;
	/** name */
	private java.lang.Object name;
	/** varType */
	private java.lang.Object varType;
	/** rev */
	private java.lang.String rev;
	/** bytearrayId */
	private java.lang.Object bytearrayId;
	/** double_ */
	private java.lang.String double_;
	/** long_ */
	private java.lang.String long_;
	/** text */
	private java.lang.Object text;
	/** text2 */
	private java.lang.Object text2;
	/** createTime */
	private java.lang.String createTime;
	/** lastUpdatedTime */
	private java.lang.String lastUpdatedTime;
	
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
	/** executionId */
	public void setExecutionId(java.lang.Object executionId) {
		this.executionId = executionId;
	}
	public java.lang.Object getExecutionId() {
		return this.executionId;
	}
	/** taskId */
	public void setTaskId(java.lang.Object taskId) {
		this.taskId = taskId;
	}
	public java.lang.Object getTaskId() {
		return this.taskId;
	}
	/** name */
	public void setName(java.lang.Object name) {
		this.name = name;
	}
	public java.lang.Object getName() {
		return this.name;
	}
	/** varType */
	public void setVarType(java.lang.Object varType) {
		this.varType = varType;
	}
	public java.lang.Object getVarType() {
		return this.varType;
	}
	/** rev */
	public void setRev(java.lang.String rev) {
		this.rev = rev == null ? null : rev.trim();
	}
	public java.lang.String getRev() {
		return this.rev;
	}
	/** bytearrayId */
	public void setBytearrayId(java.lang.Object bytearrayId) {
		this.bytearrayId = bytearrayId;
	}
	public java.lang.Object getBytearrayId() {
		return this.bytearrayId;
	}
	/** double_ */
	public void setDouble(java.lang.String double_) {
		this.double_ = double_ == null ? null : double_.trim();
	}
	public java.lang.String getDouble() {
		return this.double_;
	}
	/** long_ */
	public void setLong(java.lang.String long_) {
		this.long_ = long_ == null ? null : long_.trim();
	}
	public java.lang.String getLong() {
		return this.long_;
	}
	/** text */
	public void setText(java.lang.Object text) {
		this.text = text;
	}
	public java.lang.Object getText() {
		return this.text;
	}
	/** text2 */
	public void setText2(java.lang.Object text2) {
		this.text2 = text2;
	}
	public java.lang.Object getText2() {
		return this.text2;
	}
	/** createTime */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public java.lang.String getCreateTime() {
		return this.createTime;
	}
	/** lastUpdatedTime */
	public void setLastUpdatedTime(java.lang.String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime == null ? null : lastUpdatedTime.trim();
	}
	public java.lang.String getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
}