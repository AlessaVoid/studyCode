package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ��ɫ���ܶ��ձ�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebRoleFun extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��ɫ���� */
	private java.lang.String roleCode;
	/** ���ܱ��� */
	private java.lang.String funCode;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** ��ɫ���� */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** ���ܱ��� */
	public void setFunCode(java.lang.String funCode) {
		this.funCode = funCode == null ? null : funCode.trim();
	}
	public java.lang.String getFunCode() {
		return this.funCode;
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