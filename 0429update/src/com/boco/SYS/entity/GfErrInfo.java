package com.boco.SYS.entity;

import java.text.SimpleDateFormat;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * GfErrInfoʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class GfErrInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ϵͳ����-�� */
	private java.lang.String gfSysCode;
	/** ��Ӧ����-�� */
	private java.lang.String gfRetCode;
	/** ��Ӧ��Ϣ-�� */
	private java.lang.String gfRetInfo;
	/** ϵͳ����-�� */
	private java.lang.String otherSysCode;
	/** ��Ӧ����-�� */
	private java.lang.String otherRetCode;
	/** ��Ӧ��Ϣ-�� */
	private java.lang.String otherRetInfo;
	/** ����޸����� */
	private java.lang.String latestModifyDate;
	/** ����޸�ʱ�� */
	private java.lang.String latestModifyTime;
	/** ������Ա */
	private java.lang.String latestOperCode;
	
	
	/** setter\getter���� */
	/** ϵͳ����-�� */
	public void setGfSysCode(java.lang.String gfSysCode) {
		this.gfSysCode = gfSysCode == null ? null : gfSysCode.trim();
	}
	public java.lang.String getGfSysCode() {
		return this.gfSysCode;
	}
	/** ��Ӧ����-�� */
	public void setGfRetCode(java.lang.String gfRetCode) {
		this.gfRetCode = gfRetCode == null ? null : gfRetCode.trim();
	}
	public java.lang.String getGfRetCode() {
		return this.gfRetCode;
	}
	/** ��Ӧ��Ϣ-�� */
	public void setGfRetInfo(java.lang.String gfRetInfo) {
		this.gfRetInfo = gfRetInfo == null ? null : gfRetInfo.trim();
	}
	public java.lang.String getGfRetInfo() {
		return this.gfRetInfo;
	}
	/** ϵͳ����-�� */
	public void setOtherSysCode(java.lang.String otherSysCode) {
		this.otherSysCode = otherSysCode == null ? null : otherSysCode.trim();
	}
	public java.lang.String getOtherSysCode() {
		return this.otherSysCode;
	}
	/** ��Ӧ����-�� */
	public void setOtherRetCode(java.lang.String otherRetCode) {
		this.otherRetCode = otherRetCode == null ? null : otherRetCode.trim();
	}
	public java.lang.String getOtherRetCode() {
		return this.otherRetCode;
	}
	/** ��Ӧ��Ϣ-�� */
	public void setOtherRetInfo(java.lang.String otherRetInfo) {
		this.otherRetInfo = otherRetInfo == null ? null : otherRetInfo.trim();
	}
	public java.lang.String getOtherRetInfo() {
		return this.otherRetInfo;
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