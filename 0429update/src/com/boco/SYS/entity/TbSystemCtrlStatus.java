package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbSystemCtrlStatusʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbSystemCtrlStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ϵͳ��� */
		@EntityParaAnno(zhName="ϵͳ���")
	private String systemid;
	/** �Ƿ�ܿأ�0-�ܿ� 1-���ܿ� */
		@EntityParaAnno(zhName="�Ƿ�ܿأ�0-�ܿ� 1-���ܿ�")
	private String systemStatus;
	
	/** setter\getter���� */
	/** ϵͳ��� */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** �Ƿ�ܿأ�0-�ܿ� 1-���ܿ� */
	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus == null ? null : systemStatus.trim();
	}
	public String getSystemStatus() {
		return this.systemStatus;
	}
}