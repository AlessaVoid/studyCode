package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebRoleInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebRoleInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��ɫ��� */
	private java.lang.String roleCode;
	/** ��ɫ���� */
	private java.lang.String roleName;
	/** �������� 0-ȫ������,1-ʡ��,2-����,3-��,�ؼ�,4-֧���� */
	private java.lang.String organLevel;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** ��ɫ��� */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** ��ɫ���� */
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	public java.lang.String getRoleName() {
		return this.roleName;
	}
	/** �������� 0-ȫ������,1-ʡ��,2-����,3-��,�ؼ�,4-֧���� */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
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