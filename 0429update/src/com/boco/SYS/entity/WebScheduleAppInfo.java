package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 理财产品档期审批记录信息||该表存储理财产品设计审批信息记录实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebScheduleAppInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 审批流程代码||审批流程代码 */
	private java.lang.String workflowCode;
	/** 档期编号||档期编号 */
	private java.lang.String scheduleCode;
	/** 产品阶段||1-档期审批中 2-驳回 3-审批成功 */
	private java.lang.String appStatus;
	/** 档期描述||档期描述 */
	private java.lang.String scheduleDesc;
	/** 最后修改日期||最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间||最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员||最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 审批流程代码||审批流程代码 */
	public void setWorkflowCode(java.lang.String workflowCode) {
		this.workflowCode = workflowCode == null ? null : workflowCode.trim();
	}
	public java.lang.String getWorkflowCode() {
		return this.workflowCode;
	}
	/** 档期编号||档期编号 */
	public void setScheduleCode(java.lang.String scheduleCode) {
		this.scheduleCode = scheduleCode == null ? null : scheduleCode.trim();
	}
	public java.lang.String getScheduleCode() {
		return this.scheduleCode;
	}
	/** 产品阶段||1-档期审批中 2-驳回 3-审批成功 */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
	/** 档期描述||档期描述 */
	public void setScheduleDesc(java.lang.String scheduleDesc) {
		this.scheduleDesc = scheduleDesc == null ? null : scheduleDesc.trim();
	}
	public java.lang.String getScheduleDesc() {
		return this.scheduleDesc;
	}
	/** 最后修改日期||最后修改日期 */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** 最后修改时间||最后修改时间 */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** 最后操作员||最后操作员 */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
}