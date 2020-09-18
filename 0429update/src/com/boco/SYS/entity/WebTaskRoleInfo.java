package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ����ڵ��ɫ��Ӧ��Ϣ��||����ڵ��ɫ��Ӧ��Ϣ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class WebTaskRoleInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����ID||����ID */
		@EntityParaAnno(zhName="����ID")
	private java.lang.String taskId;
	/** ���̶���ID||���̶���ID */
		@EntityParaAnno(zhName="���̶���ID")
	private java.lang.String procDefId;
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
		@EntityParaAnno(zhName="��һ��ڵ�Ľ�ɫ����")
	private java.lang.String roleCode;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String organLevel;
	/** ����޸�����||����޸����� */
		@EntityParaAnno(zhName="����޸�����")
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ��||����޸�ʱ�� */
		@EntityParaAnno(zhName="����޸�ʱ��")
	private java.lang.String latestModifyTime;
	/** ������Ա||������Ա */
		@EntityParaAnno(zhName="������Ա")
	private java.lang.String latestOperCode;
	/** �ͻ�����||1-���� 2-���� */
		@EntityParaAnno(zhName="�ͻ�����")
	private java.lang.String custType;
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬ */
		@EntityParaAnno(zhName="��¼�ڵ��������Ʒ״̬")
	private java.lang.String appStatus;
	
	/** setter\getter���� */
	/** ����ID||����ID */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
	}
	/** ���̶���ID||���̶���ID */
	public void setProcDefId(java.lang.String procDefId) {
		this.procDefId = procDefId == null ? null : procDefId.trim();
	}
	public java.lang.String getProcDefId() {
		return this.procDefId;
	}
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** ��������||�������� */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
	}
	/** ����޸�����||����޸����� */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** ����޸�ʱ��||����޸�ʱ�� */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** ������Ա||������Ա */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
	/** �ͻ�����||1-���� 2-���� */
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