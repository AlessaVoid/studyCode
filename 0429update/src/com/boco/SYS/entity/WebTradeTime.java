package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebTradeTimeʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebTradeTime extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���ܱ��� */
	private java.lang.String menuCode;
	/** ��ʼʱ�� */
	private java.lang.String beginTime;
	/** ��ֹʱ�� */
	private java.lang.String endTime;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** ���ܱ��� */
	public void setMenuCode(java.lang.String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}
	public java.lang.String getMenuCode() {
		return this.menuCode;
	}
	/** ��ʼʱ�� */
	public void setBeginTime(java.lang.String beginTime) {
		this.beginTime = beginTime == null ? null : beginTime.trim();
	}
	public java.lang.String getBeginTime() {
		return this.beginTime;
	}
	/** ��ֹʱ�� */
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public java.lang.String getEndTime() {
		return this.endTime;
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