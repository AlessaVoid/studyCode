package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebReviewSublistʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebReviewSublist extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���˴���:����+�������� */
	private java.lang.String appNo;
	/** ����������� */
	private java.lang.String orderNo;
	/** �������� */
	private java.lang.String appData;
	
	/** setter\getter���� */
	/** ���˴���:����+�������� */
	public void setAppNo(java.lang.String appNo) {
		this.appNo = appNo == null ? null : appNo.trim();
	}
	public java.lang.String getAppNo() {
		return this.appNo;
	}
	/** ����������� */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** �������� */
	public void setAppData(java.lang.String appData) {
		this.appData = appData == null ? null : appData.trim();
	}
	public java.lang.String getAppData() {
		return this.appData;
	}
}