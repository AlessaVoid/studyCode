package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebTaskRoleInfoNewʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class WebTaskRoleInfoNew extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** taskId */
		@EntityParaAnno(zhName="taskId")
	private java.lang.String taskId;
	/** procDefId */
		@EntityParaAnno(zhName="procDefId")
	private java.lang.String procDefId;
	/** upTaskId */
		@EntityParaAnno(zhName="upTaskId")
	private java.lang.String upTaskId;
	/** organLevel */
		@EntityParaAnno(zhName="organLevel")
	private java.lang.String organLevel;
	/** latestModifyDate */
		@EntityParaAnno(zhName="latestModifyDate")
	private java.lang.String latestModifyDate;
	/** latestModifyTime */
		@EntityParaAnno(zhName="latestModifyTime")
	private java.lang.String latestModifyTime;
	/** latestOperCode */
		@EntityParaAnno(zhName="latestOperCode")
	private java.lang.String latestOperCode;
	/** custType */
		@EntityParaAnno(zhName="custType")
	private java.lang.String custType;
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬ */
		@EntityParaAnno(zhName="��¼�ڵ��������Ʒ״̬")
	private java.lang.String appStatus;
	
	/** setter\getter���� */
	/** taskId */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
	}
	/** procDefId */
	public void setProcDefId(java.lang.String procDefId) {
		this.procDefId = procDefId == null ? null : procDefId.trim();
	}
	public java.lang.String getProcDefId() {
		return this.procDefId;
	}
	/** upTaskId */
	public void setUpTaskId(java.lang.String upTaskId) {
		this.upTaskId = upTaskId == null ? null : upTaskId.trim();
	}
	public java.lang.String getUpTaskId() {
		return this.upTaskId;
	}
	/** organLevel */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
	}
	/** latestModifyDate */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** latestModifyTime */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** latestOperCode */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
	/** custType */
	public void setCustType(java.lang.String custType) {
		this.custType = custType == null ? null : custType.trim();
	}
	public java.lang.String getCustType() {
		return this.custType;
	}
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬ */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
}