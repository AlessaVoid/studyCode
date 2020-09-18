package com.boco.PM.DTO;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebAreaOrganInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebAreaOrganInfoDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���������Ӧ�ֵ��dictNo */
	private java.lang.String dictNo;
	/** ���������Ӧ�ֵ��dictName */
	private java.lang.String dictName;
	/** �������� */
	private java.lang.String areaCode;
	/** �������� */
	private java.lang.String areaName;
	/** ʡ�ݴ��� */
	private java.lang.String provCode;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	/** setter\getter���� */
	/** �������� */
	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	/** ʡ�ݴ��� */
	public void setProvCode(java.lang.String provCode) {
		this.provCode = provCode == null ? null : provCode.trim();
	}
	public java.lang.String getProvCode() {
		return this.provCode;
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
	/** �������� */
	public java.lang.String getAreaName() {
		return areaName;
	}
	/** �������� */
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	/** ������Ӧ�ֵ������ */
	public java.lang.String getDictNo() {
		return dictNo;
	}
	/** ������Ӧ�ֵ������ */
	public void setDictNo(java.lang.String dictNo) {
		this.dictNo = dictNo;
	}
	/** ���������Ӧ�ֵ��dictName */
	public java.lang.String getDictName() {
		return dictName;
	}
	/** ���������Ӧ�ֵ��dictName */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName;
	}
}