package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbBatchParamʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbBatchParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ϵͳ���� */
		@EntityParaAnno(zhName="ϵͳ����")
	private String systemDate;
	/** ϵͳ�·� */
		@EntityParaAnno(zhName="ϵͳ�·�")
	private String systemMonth;
	/** id */
		@EntityParaAnno(zhName="id")
	private String paramId;
	
	/** setter\getter���� */
	/** ϵͳ���� */
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate == null ? null : systemDate.trim();
	}
	public String getSystemDate() {
		return this.systemDate;
	}
	/** ϵͳ�·� */
	public void setSystemMonth(String systemMonth) {
		this.systemMonth = systemMonth == null ? null : systemMonth.trim();
	}
	public String getSystemMonth() {
		return this.systemMonth;
	}
	/** id */
	public void setParamId(String paramId) {
		this.paramId = paramId == null ? null : paramId.trim();
	}
	public String getParamId() {
		return this.paramId;
	}
}