package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ��Ʋ�Ʒ����������¼��Ϣ||�ñ�洢��Ʋ�Ʒ���������Ϣ��¼ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebScheduleAppInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �������̴���||�������̴��� */
	private java.lang.String workflowCode;
	/** ���ڱ��||���ڱ�� */
	private java.lang.String scheduleCode;
	/** ��Ʒ�׶�||1-���������� 2-���� 3-�����ɹ� */
	private java.lang.String appStatus;
	/** ��������||�������� */
	private java.lang.String scheduleDesc;
	/** ����޸�����||����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ��||����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա||������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** �������̴���||�������̴��� */
	public void setWorkflowCode(java.lang.String workflowCode) {
		this.workflowCode = workflowCode == null ? null : workflowCode.trim();
	}
	public java.lang.String getWorkflowCode() {
		return this.workflowCode;
	}
	/** ���ڱ��||���ڱ�� */
	public void setScheduleCode(java.lang.String scheduleCode) {
		this.scheduleCode = scheduleCode == null ? null : scheduleCode.trim();
	}
	public java.lang.String getScheduleCode() {
		return this.scheduleCode;
	}
	/** ��Ʒ�׶�||1-���������� 2-���� 3-�����ɹ� */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
	/** ��������||�������� */
	public void setScheduleDesc(java.lang.String scheduleDesc) {
		this.scheduleDesc = scheduleDesc == null ? null : scheduleDesc.trim();
	}
	public java.lang.String getScheduleDesc() {
		return this.scheduleDesc;
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
}