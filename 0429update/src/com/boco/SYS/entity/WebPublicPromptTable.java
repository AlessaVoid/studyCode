package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ������ʾ��||������ʾ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class WebPublicPromptTable extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id||id */
		@EntityParaAnno(zhName="id")
	private java.lang.String id;
	/** ����||���� */
		@EntityParaAnno(zhName="����")
	private java.lang.String content;
	/** ������||������ */
		@EntityParaAnno(zhName="������")
	private java.lang.String operCode;
	/** ����ʱ��||����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String operTime;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String operDate;
	/** ״̬||״̬ 1-���ã�1-ͣ�� */
		@EntityParaAnno(zhName="״̬")
	private java.lang.String useStatus;
	
	/** setter\getter���� */
	/** id||id */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** ����||���� */
	public void setContent(java.lang.String content) {
		this.content = content == null ? null : content.trim();
	}
	public java.lang.String getContent() {
		return this.content;
	}
	/** ������||������ */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
	/** ����ʱ��||����ʱ�� */
	public void setOperTime(java.lang.String operTime) {
		this.operTime = operTime == null ? null : operTime.trim();
	}
	public java.lang.String getOperTime() {
		return this.operTime;
	}
	/** ��������||�������� */
	public void setOperDate(java.lang.String operDate) {
		this.operDate = operDate == null ? null : operDate.trim();
	}
	public java.lang.String getOperDate() {
		return this.operDate;
	}
	/** ״̬||״̬ 1-���ã�1-ͣ�� */
	public void setUseStatus(java.lang.String useStatus) {
		this.useStatus = useStatus == null ? null : useStatus.trim();
	}
	public java.lang.String getUseStatus() {
		return this.useStatus;
	}
}