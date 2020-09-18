package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActicitiRoleConfig实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class ActicitiRoleConfig extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 任务活动ID||任务活动ID */
		@EntityParaAnno(zhName="任务活动ID")
	private java.lang.String taskId;
	/** 下一活动节点的角色编码||下一活动节点的角色编码 */
		@EntityParaAnno(zhName="下一活动节点的角色编码")
	private java.lang.String roleCode;
	/** 机构级别||机构级别 */
		@EntityParaAnno(zhName="机构级别")
	private java.lang.String organLevel;
	/** "客户类型||1-个人 2-机构" */
		@EntityParaAnno(zhName="客户类型")
	private java.lang.String custType;
	/** 记录节点审批后产品状态||记录节点审批后产品状态0-重编辑  *1-自平衡预研(分行)2  *2-自平衡预研(分行)3  *3-自平衡预研(分行)4  *4-自平衡预研(总行)1  *5-自平衡预研(总行)2  *6-自平衡预研(总行)3  *7-自平衡预研(总行)4  *8-预研待神审核  *9-预研已审核待申报（须已资管系统成功接收到拟申报信息并向销售系统返回接收成功信息为准，避免拟申报产品信息遗漏）  *10-申报通过待设计  *11-进入设计阶段 */
		@EntityParaAnno(zhName="记录节点审批后产品状态")
	private java.lang.String appStatus;
	/** 流程类型||01-预研,02-设计,03-要素变更，04-清算 */
		@EntityParaAnno(zhName="流程类型")
	private java.lang.String activitiType;
	
	/** setter\getter方法 */
	/** 任务活动ID||任务活动ID */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
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
	/** "客户类型||1-个人 2-机构" */
	public void setCustType(java.lang.String custType) {
		this.custType = custType == null ? null : custType.trim();
	}
	public java.lang.String getCustType() {
		return this.custType;
	}
	/** 记录节点审批后产品状态||记录节点审批后产品状态0-重编辑  *1-自平衡预研(分行)2  *2-自平衡预研(分行)3  *3-自平衡预研(分行)4  *4-自平衡预研(总行)1  *5-自平衡预研(总行)2  *6-自平衡预研(总行)3  *7-自平衡预研(总行)4  *8-预研待神审核  *9-预研已审核待申报（须已资管系统成功接收到拟申报信息并向销售系统返回接收成功信息为准，避免拟申报产品信息遗漏）  *10-申报通过待设计  *11-进入设计阶段 */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
	/** 流程类型||01-预研,02-设计,03-要素变更，04-清算 */
	public void setActivitiType(java.lang.String activitiType) {
		this.activitiType = activitiType == null ? null : activitiType.trim();
	}
	public java.lang.String getActivitiType() {
		return this.activitiType;
	}
}