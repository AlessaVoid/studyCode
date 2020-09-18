package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ActicitiRoleConfigʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class ActicitiRoleConfig extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����ID||����ID */
		@EntityParaAnno(zhName="����ID")
	private java.lang.String taskId;
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
		@EntityParaAnno(zhName="��һ��ڵ�Ľ�ɫ����")
	private java.lang.String roleCode;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String organLevel;
	/** "�ͻ�����||1-���� 2-����" */
		@EntityParaAnno(zhName="�ͻ�����")
	private java.lang.String custType;
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬0-�ر༭  *1-��ƽ��Ԥ��(����)2  *2-��ƽ��Ԥ��(����)3  *3-��ƽ��Ԥ��(����)4  *4-��ƽ��Ԥ��(����)1  *5-��ƽ��Ԥ��(����)2  *6-��ƽ��Ԥ��(����)3  *7-��ƽ��Ԥ��(����)4  *8-Ԥ�д������  *9-Ԥ������˴��걨�������ʹ�ϵͳ�ɹ����յ����걨��Ϣ��������ϵͳ���ؽ��ճɹ���ϢΪ׼���������걨��Ʒ��Ϣ��©��  *10-�걨ͨ�������  *11-������ƽ׶� */
		@EntityParaAnno(zhName="��¼�ڵ��������Ʒ״̬")
	private java.lang.String appStatus;
	/** ��������||01-Ԥ��,02-���,03-Ҫ�ر����04-���� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String activitiType;
	
	/** setter\getter���� */
	/** ����ID||����ID */
	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}
	public java.lang.String getTaskId() {
		return this.taskId;
	}
	/** ��һ��ڵ�Ľ�ɫ����||��һ��ڵ�Ľ�ɫ���� */
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	/** ��������||�������� */
	public void setOrganLevel(java.lang.String organLevel) {
		this.organLevel = organLevel == null ? null : organLevel.trim();
	}
	public java.lang.String getOrganLevel() {
		return this.organLevel;
	}
	/** "�ͻ�����||1-���� 2-����" */
	public void setCustType(java.lang.String custType) {
		this.custType = custType == null ? null : custType.trim();
	}
	public java.lang.String getCustType() {
		return this.custType;
	}
	/** ��¼�ڵ��������Ʒ״̬||��¼�ڵ��������Ʒ״̬0-�ر༭  *1-��ƽ��Ԥ��(����)2  *2-��ƽ��Ԥ��(����)3  *3-��ƽ��Ԥ��(����)4  *4-��ƽ��Ԥ��(����)1  *5-��ƽ��Ԥ��(����)2  *6-��ƽ��Ԥ��(����)3  *7-��ƽ��Ԥ��(����)4  *8-Ԥ�д������  *9-Ԥ������˴��걨�������ʹ�ϵͳ�ɹ����յ����걨��Ϣ��������ϵͳ���ؽ��ճɹ���ϢΪ׼���������걨��Ʒ��Ϣ��©��  *10-�걨ͨ�������  *11-������ƽ׶� */
	public void setAppStatus(java.lang.String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}
	public java.lang.String getAppStatus() {
		return this.appStatus;
	}
	/** ��������||01-Ԥ��,02-���,03-Ҫ�ر����04-���� */
	public void setActivitiType(java.lang.String activitiType) {
		this.activitiType = activitiType == null ? null : activitiType.trim();
	}
	public java.lang.String getActivitiType() {
		return this.activitiType;
	}
}