package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ���Ͷ���-������״̬ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class MsgMonitor extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��������ip��ַ */
		@EntityParaAnno(zhName="��������ip��ַ")
	private String moIp;
	/** ����ڵ��� */
		@EntityParaAnno(zhName="����ڵ���")
	private String moSvrId;
	/** ����ڵ����� ap��ctrl��end��web */
		@EntityParaAnno(zhName="����ڵ����� ap��ctrl��end��web")
	private String moSvrType;
	/** ��������״̬,0������1δ����2������ */
		@EntityParaAnno(zhName="��������״̬,0������1δ����2������")
	private String moSvrRunStatus;
	/** ���ŷ��ʹ��� */
		@EntityParaAnno(zhName="���ŷ��ʹ���")
	private Integer sendCount;
	
	/** setter\getter���� */
	/** ��������ip��ַ */
	public void setMoIp(String moIp) {
		this.moIp = moIp == null ? null : moIp.trim();
	}
	public String getMoIp() {
		return this.moIp;
	}
	/** ����ڵ��� */
	public void setMoSvrId(String moSvrId) {
		this.moSvrId = moSvrId == null ? null : moSvrId.trim();
	}
	public String getMoSvrId() {
		return this.moSvrId;
	}
	/** ����ڵ����� ap��ctrl��end��web */
	public void setMoSvrType(String moSvrType) {
		this.moSvrType = moSvrType == null ? null : moSvrType.trim();
	}
	public String getMoSvrType() {
		return this.moSvrType;
	}
	/** ��������״̬,0������1δ����2������ */
	public void setMoSvrRunStatus(String moSvrRunStatus) {
		this.moSvrRunStatus = moSvrRunStatus == null ? null : moSvrRunStatus.trim();
	}
	public String getMoSvrRunStatus() {
		return this.moSvrRunStatus;
	}
	/** ���ŷ��ʹ��� */
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getSendCount() {
		return this.sendCount;
	}
}