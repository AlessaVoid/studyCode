package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebSublicenseInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebSublicenseInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���� */
	private java.lang.String id;
	/** ת���˴��� */
	private java.lang.String outOper;
	/** ת���˴��� */
	private java.lang.String inOper;
	/** ��Ȩ��ɫ���� */
	private java.lang.String roleCode;
	/** ת������ */
	private java.lang.String outDate;
	/** ת��ʱ�� */
	private java.lang.String outTime;
	/** �ջ����� */
	private java.lang.String inDate;
	/** �ջ�ʱ�� */
	private java.lang.String inTime;
	/** �Ƿ��ջ�,1-�ջ� 2-δ�ջ� */
	private java.lang.String isBack;
	/**ת��ת����־*/
	private java.lang.String outOrIn;
	
	/** setter\getter���� */
	/** ���� */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** ת���˴��� */
	public void setOutOper(java.lang.String outOper) {
		this.outOper = outOper == null ? null : outOper.trim();
	}
	public java.lang.String getOutOper() {
		return this.outOper;
	}
	/** ת���˴��� */
	public void setInOper(java.lang.String inOper) {
		this.inOper = inOper == null ? null : inOper.trim();
	}
	public java.lang.String getInOper() {
		return this.inOper;
	}
	/** ��Ȩ��ɫ���� */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** ת������ */
	public void setOutDate(java.lang.String outDate) {
		this.outDate = outDate == null ? null : outDate.trim();
	}
	public java.lang.String getOutDate() {
		return this.outDate;
	}
	/** ת��ʱ�� */
	public void setOutTime(java.lang.String outTime) {
		this.outTime = outTime == null ? null : outTime.trim();
	}
	public java.lang.String getOutTime() {
		return this.outTime;
	}
	/** �ջ����� */
	public void setInDate(java.lang.String inDate) {
		this.inDate = inDate == null ? null : inDate.trim();
	}
	public java.lang.String getInDate() {
		return this.inDate;
	}
	/** �ջ�ʱ�� */
	public void setInTime(java.lang.String inTime) {
		this.inTime = inTime == null ? null : inTime.trim();
	}
	public java.lang.String getInTime() {
		return this.inTime;
	}
	/** �Ƿ��ջ�,1-�ջ� 2-δ�ջ� */
	public void setIsBack(java.lang.String isBack) {
		this.isBack = isBack == null ? null : isBack.trim();
	}
	public java.lang.String getIsBack() {
		return this.isBack;
	}
	/**ת��ת����־*/
	public java.lang.String getOutOrIn() {
		return outOrIn;
	}
	/**ת��ת����־*/
	public void setOutOrIn(java.lang.String outOrIn) {
		this.outOrIn = outOrIn;
	}
	
}