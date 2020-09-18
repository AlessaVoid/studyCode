package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 任务节点角色对应信息表||任务节点角色对应信息表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class WebTaskRoleInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 任务活动ID||任务活动ID */
		@EntityParaAnno(zhName="任务活动ID")
	private java.lang.String taskId;
	/** 流程定义ID||流程定义ID */
		@EntityParaAnno(zhName="流程定义ID")
	private java.lang.String procDefId;
	/** 下一活动节点的角色编码||下一活动节点的角色编码 */
		@EntityParaAnno(zhName="下一活动节点的角色编码")
	private java.lang.String roleCode;
	/** 机构级别||机构级别 */
		@EntityParaAnno(zhName="机构级别")
	private java.lang.String organLevel;
	/** 最后修改日期||最后修改日期 */
		@EntityParaAnno(zhName="最后修改日期")
	private java.lang.String latestModifyDate;
	/** 最后修改时间||最后修改时间 */
		@EntityParaAnno(zhName="最后修改时间")
	private java.lang.String latestModifyTime;
	/** 最后操作员||最后操作员 */
		@EntityParaAnno(zhName="最后操作员")
	private java.lang.String latestOperCode;
	/** 客户类型||1-个人 2-机构 */
		@EntityParaAnno(zhName="客户类型")
	private java.lang.String custType;
	/** 记录节点审批后产品状态||记录节点审批后产品状态 */
		@EntityParaAnno(zhName="记录节点审批后产品状态")
	private java.lang.String appStatus;
	
	/** setter\getter方法 */
	/** 任务活动ID||任务活动ID */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
	}
	/** 流程定义ID||流程定义ID */
	public void setProcDefId(java.lang.String procDefId) {
		this.procDefId = procDefId == null ? null : procDefId.trim();
	}
	public java.lang.String getProcDefId() {
		return this.procDefId;
	}
	/** 下一活动节点的角色编码||下一活动节点的角色编码 */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** 机构级别||机构级别 */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
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
	/** 客户类型||1-个人 2-机构 */
	public void setCustType(java.lang.String custType) {
		this.custType = custType == null ? null : custType.trim();
	}
	public java.lang.String getCustType() {
		return this.custType;
	}
	/** 记录节点审批后产品状态||记录节点审批后产品状态 */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
}