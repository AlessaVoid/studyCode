package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ������״̬ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbMonitor extends BaseEntity implements java.io.Serializable {
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
	/** ��������·�� */
		@EntityParaAnno(zhName="��������·��")
	private String moSvrPath;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String moSvrName;
	/** �������õĶ˿ڣ�û�����ö˿�ʱΪ0,����˿�ʱ,�ö��ŷֿ� */
		@EntityParaAnno(zhName="�������õĶ˿ڣ�û�����ö˿�ʱΪ0,����˿�ʱ,�ö��ŷֿ�")
	private String moSvrPort;
	/** ��������״̬,0������1δ����2������ */
		@EntityParaAnno(zhName="��������״̬,0������1δ����2������")
	private String moSvrRunStatus;
	/** �˿�״̬0�˿�������1�˿�δ����2������ */
		@EntityParaAnno(zhName="�˿�״̬0�˿�������1�˿�δ����2������")
	private String moSvrPortStatus;
	/** ���ݲɼ�ʱ�� */
		@EntityParaAnno(zhName="���ݲɼ�ʱ��")
	private String moCollectTime;
	
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
	/** ��������·�� */
	public void setMoSvrPath(String moSvrPath) {
		this.moSvrPath = moSvrPath == null ? null : moSvrPath.trim();
	}
	public String getMoSvrPath() {
		return this.moSvrPath;
	}
	/** ������ */
	public void setMoSvrName(String moSvrName) {
		this.moSvrName = moSvrName == null ? null : moSvrName.trim();
	}
	public String getMoSvrName() {
		return this.moSvrName;
	}
	/** �������õĶ˿ڣ�û�����ö˿�ʱΪ0,����˿�ʱ,�ö��ŷֿ� */
	public void setMoSvrPort(String moSvrPort) {
		this.moSvrPort = moSvrPort == null ? null : moSvrPort.trim();
	}
	public String getMoSvrPort() {
		return this.moSvrPort;
	}
	/** ��������״̬,0������1δ����2������ */
	public void setMoSvrRunStatus(String moSvrRunStatus) {
		this.moSvrRunStatus = moSvrRunStatus == null ? null : moSvrRunStatus.trim();
	}
	public String getMoSvrRunStatus() {
		return this.moSvrRunStatus;
	}
	/** �˿�״̬0�˿�������1�˿�δ����2������ */
	public void setMoSvrPortStatus(String moSvrPortStatus) {
		this.moSvrPortStatus = moSvrPortStatus == null ? null : moSvrPortStatus.trim();
	}
	public String getMoSvrPortStatus() {
		return this.moSvrPortStatus;
	}
	/** ���ݲɼ�ʱ�� */
	public void setMoCollectTime(String moCollectTime) {
		this.moCollectTime = moCollectTime == null ? null : moCollectTime.trim();
	}
	public String getMoCollectTime() {
		return this.moCollectTime;
	}
}