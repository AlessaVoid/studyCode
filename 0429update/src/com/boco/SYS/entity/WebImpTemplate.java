package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebImpTemplateʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebImpTemplate extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ģ���� */
	private java.lang.String templateCode;
	/** ģ������ */
	private java.lang.String templateName;
	/** ���·�� */
	private java.lang.String templateUrl;
	/** ��ע */
	private java.lang.String remark;
	
	/** setter\getter���� */
	/** ģ���� */
	public void setTemplateCode(java.lang.String templateCode) {
		this.templateCode = templateCode == null ? null : templateCode.trim();
	}
	public java.lang.String getTemplateCode() {
		return this.templateCode;
	}
	/** ģ������ */
	public void setTemplateName(java.lang.String templateName) {
		this.templateName = templateName == null ? null : templateName.trim();
	}
	public java.lang.String getTemplateName() {
		return this.templateName;
	}
	/** ���·�� */
	public void setTemplateUrl(java.lang.String templateUrl) {
		this.templateUrl = templateUrl == null ? null : templateUrl.trim();
	}
	public java.lang.String getTemplateUrl() {
		return this.templateUrl;
	}
	/** ��ע */
	public void setRemark(java.lang.String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
}