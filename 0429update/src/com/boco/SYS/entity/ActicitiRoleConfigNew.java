package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActicitiRoleConfigNewʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class ActicitiRoleConfigNew extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����ID||����ID */
		@EntityParaAnno(zhName="����ID")
	private java.lang.String taskId;
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
		@EntityParaAnno(zhName="��һ��ڵ�Ľ�ɫ����")
	private java.lang.String upTaskId;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String organLevel;
	/** �ͻ�����||1-���� 2-���� */
		@EntityParaAnno(zhName="�ͻ�����")
	private java.lang.String custType;
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬ */
		@EntityParaAnno(zhName="��¼�ڵ��������Ʒ״̬")
	private java.lang.String appStatus;
	/** ��������||01-Ԥ��,02-���,03-Ҫ�ر����04-���� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String activitiType;
	
	/** setter\getter���� */
	/** ����ID||����ID */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
	}
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
	public void setUpTaskId(java.lang.String upTaskId) {
		this.upTaskId = upTaskId == null ? null : upTaskId.trim();
	}
	public java.lang.String getUpTaskId() {
		return this.upTaskId;
	}
	/** ��������||�������� */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
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
	/** ��������||01-Ԥ��,02-���,03-Ҫ�ر����04-���� */
	public void setActivitiType(java.lang.String activitiType) {
		this.activitiType = activitiType == null ? null : activitiType.trim();
	}
	public java.lang.String getActivitiType() {
		return this.activitiType;
	}
}