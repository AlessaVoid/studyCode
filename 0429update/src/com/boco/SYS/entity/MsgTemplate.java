package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ����ģ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class MsgTemplate extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ģ��id */
		@EntityParaAnno(zhName="ģ��id")
	private String id;
	/** ģ������ */
		@EntityParaAnno(zhName="ģ������")
	private String name;
	/** ģ������ */
		@EntityParaAnno(zhName="ģ������")
	private String template;
	
	/** setter\getter���� */
	/** ģ��id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** ģ������ */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** ģ������ */
	public void setTemplate(String template) {
		this.template = template == null ? null : template.trim();
	}
	public String getTemplate() {
		return this.template;
	}
}