package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �˵���ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebMenuInfo extends BaseEntity implements java.io.Serializable {
	/** ���� */
	/** ���� */
	private java.lang.String id;
	/** �˵���� */
	private java.lang.String menuNo;
	/** �ϼ��˵���� */
	private java.lang.String upMenuNo;
	/** �˵������� */
	private java.lang.String orderNo;
	/** �Ƿ�ΪҶ�ڵ� 0-��1-�� */
	private java.lang.String isParent;
	/** �˵�URL */
	private java.lang.String menuUrl;
	/** �˵����� */
	private java.lang.String menuName;
	/** �˵�ͼ�� */
	private java.lang.String menuIcon;
	/** �˵�״̬ 1-����,2-���� */
	private java.lang.String menuStatus;
	/** �˵����� 1-�˵�,2-��ť,3-���� */
	private java.lang.String menuType;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	/** �ϼ��˵����� */
	private java.lang.String upMenuName;
	
	/** setter\getter���� */
	/** ���� */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** �˵���� */
	public void setMenuNo(java.lang.String menuNo) {
		this.menuNo = menuNo == null ? null : menuNo.trim();
	}
	public java.lang.String getMenuNo() {
		return this.menuNo;
	}
	/** �ϼ��˵���� */
	public void setUpMenuNo(java.lang.String upMenuNo) {
		this.upMenuNo = upMenuNo == null ? null : upMenuNo.trim();
	}
	public java.lang.String getUpMenuNo() {
		return this.upMenuNo;
	}
	/** �˵������� */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** �Ƿ�ΪҶ�ڵ� 0-��1-�� */
	public void setIsParent(java.lang.String isParent) {
		this.isParent = isParent == null ? null : isParent.trim();
	}
	public java.lang.String getIsParent() {
		return this.isParent;
	}
	/** �˵�URL */
	public void setMenuUrl(java.lang.String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}
	public java.lang.String getMenuUrl() {
		return this.menuUrl;
	}
	/** �˵����� */
	public void setMenuName(java.lang.String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	/** �˵�ͼ�� */
	public void setMenuIcon(java.lang.String menuIcon) {
		this.menuIcon = menuIcon == null ? null : menuIcon.trim();
	}
	public java.lang.String getMenuIcon() {
		return this.menuIcon;
	}
	/** �˵�״̬ 1-����,2-���� */
	public void setMenuStatus(java.lang.String menuStatus) {
		this.menuStatus = menuStatus == null ? null : menuStatus.trim();
	}
	public java.lang.String getMenuStatus() {
		return this.menuStatus;
	}
	/** �˵����� 1-�˵�,2-��ť,3-���� */
	public void setMenuType(java.lang.String menuType) {
		this.menuType = menuType == null ? null : menuType.trim();
	}
	public java.lang.String getMenuType() {
		return this.menuType;
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
	/**�ϼ��˵�����*/
	public java.lang.String getUpMenuName() {
		return upMenuName;
	}
	/**�ϼ��˵�����*/
	public void setUpMenuName(java.lang.String upMenuName) {
		this.upMenuName = upMenuName;
	}
}