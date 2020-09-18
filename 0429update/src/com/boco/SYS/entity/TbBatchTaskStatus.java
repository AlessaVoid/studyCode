package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ��������ִ�б�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbBatchTaskStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String batchDay;
	/** ���������� */
		@EntityParaAnno(zhName="����������")
	private String taskCode;
	/** ����״̬0�ɹ�1ʧ�� */
		@EntityParaAnno(zhName="����״̬0�ɹ�1ʧ��")
	private Integer taskStatus;
	/** ��ʼʱ�� */
		@EntityParaAnno(zhName="��ʼʱ��")
	private String startTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String endTime;
	
	/** setter\getter���� */
	/** �������� */
	public void setBatchDay(String batchDay) {
		this.batchDay = batchDay == null ? null : batchDay.trim();
	}
	public String getBatchDay() {
		return this.batchDay;
	}
	/** ���������� */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode == null ? null : taskCode.trim();
	}
	public String getTaskCode() {
		return this.taskCode;
	}
	/** ����״̬0�ɹ�1ʧ�� */
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getTaskStatus() {
		return this.taskStatus;
	}
	/** ��ʼʱ�� */
	public void setStartTime(String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}
	public String getStartTime() {
		return this.startTime;
	}
	/** ����ʱ�� */
	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public String getEndTime() {
		return this.endTime;
	}
}