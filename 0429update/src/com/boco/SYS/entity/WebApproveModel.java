package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebApproveModelʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebApproveModel extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����������� */
	private java.lang.String appCode;
	/** ������� */
	private java.lang.String appAdvice;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** ����������� */
	public void setAppCode(java.lang.String appCode) {
		this.appCode = appCode == null ? null : appCode.trim();
	}
	public java.lang.String getAppCode() {
		return this.appCode;
	}
	/** ������� */
	public void setAppAdvice(java.lang.String appAdvice) {
		this.appAdvice = appAdvice == null ? null : appAdvice.trim();
	}
	public java.lang.String getAppAdvice() {
		return this.appAdvice;
	}
	/** ����޸����� */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** ����޸�ʱ�� */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** ������Ա */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
}