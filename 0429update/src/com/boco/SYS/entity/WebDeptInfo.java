package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebDeptInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebDeptInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���ű��� */
	private java.lang.String deptCode;
	/** �������� */
	private java.lang.String deptName;
	/** �ϼ����ű��� */
	private java.lang.String upDeptCode;
	/** ������������ */
	private java.lang.String organcode;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** �ϼ��������� */
	private java.lang.String upDeptName;
	
	public java.lang.String getUpDeptName() {
		return upDeptName;
	}
	public void setUpDeptName(java.lang.String upDeptName) {
		this.upDeptName = upDeptName;
	}
	/** setter\getter���� */
	/** ���ű��� */
	public void setDeptCode(java.lang.String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}
	public java.lang.String getDeptCode() {
		return this.deptCode;
	}
	/** �������� */
	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}
	public java.lang.String getDeptName() {
		return this.deptName;
	}
	/** �ϼ����ű��� */
	public void setUpDeptCode(java.lang.String upDeptCode) {
		this.upDeptCode = upDeptCode == null ? null : upDeptCode.trim();
	}
	public java.lang.String getUpDeptCode() {
		return this.upDeptCode;
	}
	/** ������������ */
	public void setOrgancode(java.lang.String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public java.lang.String getOrgancode() {
		return this.organcode;
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