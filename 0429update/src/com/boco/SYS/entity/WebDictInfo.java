package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebDictInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebDictInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��� */
	private java.lang.String id;
	/** ������� */
	private java.lang.String groupCode;
	/** �������� */
	private java.lang.String groupName;
	/** ����ֵ */
	private java.lang.String argumentValue;
	/** �������� */
	private java.lang.String argumentName;
	/** ���� */
	private java.lang.String orderNo;
	/** �������� */
	private java.lang.String contentDescribe;
	/** ����״̬ */
	private java.lang.String argumentStatus;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	/** �����ַ��� */
	private String sortColumns;
	
	/** setter\getter���� */
	/** ��� */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** ������� */
	public void setGroupCode(java.lang.String groupCode) {
		this.groupCode = groupCode == null ? null : groupCode.trim();
	}
	public java.lang.String getGroupCode() {
		return this.groupCode;
	}
	/** �������� */
	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}
	public java.lang.String getGroupName() {
		return this.groupName;
	}
	/** ����ֵ */
	public void setArgumentValue(java.lang.String argumentValue) {
		this.argumentValue = argumentValue == null ? null : argumentValue.trim();
	}
	public java.lang.String getArgumentValue() {
		return this.argumentValue;
	}
	/** �������� */
	public void setArgumentName(java.lang.String argumentName) {
		this.argumentName = argumentName == null ? null : argumentName.trim();
	}
	public java.lang.String getArgumentName() {
		return this.argumentName;
	}
	/** ���� */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** �������� */
	public void setContentDescribe(java.lang.String contentDescribe) {
		this.contentDescribe = contentDescribe == null ? null : contentDescribe.trim();
	}
	public java.lang.String getContentDescribe() {
		return this.contentDescribe;
	}
	/** ����״̬ */
	public void setArgumentStatus(java.lang.String argumentStatus) {
		this.argumentStatus = argumentStatus == null ? null : argumentStatus.trim();
	}
	public java.lang.String getArgumentStatus() {
		return this.argumentStatus;
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
	/** �����ַ��� */
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public String getSortColumns() {
		return sortColumns;
	}
}