package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ��Ϣ�б�||��Ϣ�б�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class WebMsg extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��Ϣ����||��Ϣ���� */
		@EntityParaAnno(zhName="��Ϣ����")
	private java.lang.String msgNo;
	/** ���״���||���״��� */
		@EntityParaAnno(zhName="���״���")
	private java.lang.String webTradeCode;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String operDescribe;
	/** ���ײ�������||���ײ������� 00-  �򵥸��� 01-Ԥ������ 02-Ԥ�в��� 03-������� 04-��Ʋ��� 05-�������� 06-���ڲ��� 07-�ʽ𻮲����� 08-�ʽ𻮲����� */
		@EntityParaAnno(zhName="���ײ�������")
	private java.lang.String msgType;
	/** ���׶������||���׶������ */
		@EntityParaAnno(zhName="���׶������")
	private java.lang.String operNo;
	/** ����Ա����||����Ա���� */
		@EntityParaAnno(zhName="����Ա����")
	private java.lang.String operName;
	/** ������ϸҳ��||������ϸҳ�� */
		@EntityParaAnno(zhName="������ϸҳ��")
	private java.lang.String msgUrl;
	/** �������Ա����||�������Ա���� */
		@EntityParaAnno(zhName="�������Ա����")
	private java.lang.String appUser;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String appDate;
	/** ����ʱ��||����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String appTime;
	/** �������Ա����||�������Ա���� */
		@EntityParaAnno(zhName="�������Ա����")
	private java.lang.String appOperName;
	/** �������Ա��ɫ����||�������Ա��ɫ���� */
		@EntityParaAnno(zhName="�������Ա��ɫ����")
	private java.lang.String appRoleName;
	/** �������Ա������������||�������Ա������������ */
		@EntityParaAnno(zhName="�������Ա������������")
	private java.lang.String appOrganCode;
	/** �������Ա������������||�������Ա������������ */
		@EntityParaAnno(zhName="�������Ա������������")
	private java.lang.String appOrganName;
	/** ���뱸ע||���뱸ע */
		@EntityParaAnno(zhName="���뱸ע")
	private java.lang.String appRemark;
	/** ���˲���Ա����||���˲���Ա���� */
		@EntityParaAnno(zhName="���˲���Ա����")
	private java.lang.String repUserCode;
	/** ���˲���Ա����||���˲���Ա���� */
		@EntityParaAnno(zhName="���˲���Ա����")
	private java.lang.String repUserName;
	/** ���˲���Ա��ɫ����||���˲���Ա��ɫ���� */
		@EntityParaAnno(zhName="���˲���Ա��ɫ����")
	private java.lang.String repRoleName;
	/** ���˲���Ա������������||���˲���Ա������������ */
		@EntityParaAnno(zhName="���˲���Ա������������")
	private java.lang.String repUserOrganCode;
	/** ���˲���Ա������������||���˲���Ա������������ */
		@EntityParaAnno(zhName="���˲���Ա������������")
	private java.lang.String repUserOrganName;
	/** �������||������� */
		@EntityParaAnno(zhName="�������")
	private java.lang.String repDate;
	/** ����ʱ��||����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String repTime;
	/** ���˱�ע||���˱�ע */
		@EntityParaAnno(zhName="���˱�ע")
	private java.lang.String repRemark;
	/** ����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر����� */
		@EntityParaAnno(zhName="����״̬")
	private java.lang.String webMsgStatus;
	
	/** setter\getter���� */
	/** ��Ϣ����||��Ϣ���� */
	public void setMsgNo(java.lang.String msgNo) {
		this.msgNo = msgNo == null ? null : msgNo.trim();
	}
	public java.lang.String getMsgNo() {
		return this.msgNo;
	}
	/** ���״���||���״��� */
	public void setWebTradeCode(java.lang.String webTradeCode) {
		this.webTradeCode = webTradeCode == null ? null : webTradeCode.trim();
	}
	public java.lang.String getWebTradeCode() {
		return this.webTradeCode;
	}
	/** ��������||�������� */
	public void setOperDescribe(java.lang.String operDescribe) {
		this.operDescribe = operDescribe == null ? null : operDescribe.trim();
	}
	public java.lang.String getOperDescribe() {
		return this.operDescribe;
	}
	/** ���ײ�������||���ײ������� 00-  �򵥸��� 01-Ԥ������ 02-Ԥ�в��� 03-������� 04-��Ʋ��� 05-�������� 06-���ڲ��� 07-�ʽ𻮲����� 08-�ʽ𻮲����� */
	public void setMsgType(java.lang.String msgType) {
		this.msgType = msgType == null ? null : msgType.trim();
	}
	public java.lang.String getMsgType() {
		return this.msgType;
	}
	/** ���׶������||���׶������ */
	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo == null ? null : operNo.trim();
	}
	public java.lang.String getOperNo() {
		return this.operNo;
	}
	/** ����Ա����||����Ա���� */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** ������ϸҳ��||������ϸҳ�� */
	public void setMsgUrl(java.lang.String msgUrl) {
		this.msgUrl = msgUrl == null ? null : msgUrl.trim();
	}
	public java.lang.String getMsgUrl() {
		return this.msgUrl;
	}
	/** �������Ա����||�������Ա���� */
	public void setAppUser(java.lang.String appUser) {
		this.appUser = appUser == null ? null : appUser.trim();
	}
	public java.lang.String getAppUser() {
		return this.appUser;
	}
	/** ��������||�������� */
	public void setAppDate(java.lang.String appDate) {
		this.appDate = appDate == null ? null : appDate.trim();
	}
	public java.lang.String getAppDate() {
		return this.appDate;
	}
	/** ����ʱ��||����ʱ�� */
	public void setAppTime(java.lang.String appTime) {
		this.appTime = appTime == null ? null : appTime.trim();
	}
	public java.lang.String getAppTime() {
		return this.appTime;
	}
	/** �������Ա����||�������Ա���� */
	public void setAppOperName(java.lang.String appOperName) {
		this.appOperName = appOperName == null ? null : appOperName.trim();
	}
	public java.lang.String getAppOperName() {
		return this.appOperName;
	}
	/** �������Ա��ɫ����||�������Ա��ɫ���� */
	public void setAppRoleName(java.lang.String appRoleName) {
		this.appRoleName = appRoleName == null ? null : appRoleName.trim();
	}
	public java.lang.String getAppRoleName() {
		return this.appRoleName;
	}
	/** �������Ա������������||�������Ա������������ */
	public void setAppOrganCode(java.lang.String appOrganCode) {
		this.appOrganCode = appOrganCode == null ? null : appOrganCode.trim();
	}
	public java.lang.String getAppOrganCode() {
		return this.appOrganCode;
	}
	/** �������Ա������������||�������Ա������������ */
	public void setAppOrganName(java.lang.String appOrganName) {
		this.appOrganName = appOrganName == null ? null : appOrganName.trim();
	}
	public java.lang.String getAppOrganName() {
		return this.appOrganName;
	}
	/** ���뱸ע||���뱸ע */
	public void setAppRemark(java.lang.String appRemark) {
		this.appRemark = appRemark == null ? null : appRemark.trim();
	}
	public java.lang.String getAppRemark() {
		return this.appRemark;
	}
	/** ���˲���Ա����||���˲���Ա���� */
	public void setRepUserCode(java.lang.String repUserCode) {
		this.repUserCode = repUserCode == null ? null : repUserCode.trim();
	}
	public java.lang.String getRepUserCode() {
		return this.repUserCode;
	}
	/** ���˲���Ա����||���˲���Ա���� */
	public void setRepUserName(java.lang.String repUserName) {
		this.repUserName = repUserName == null ? null : repUserName.trim();
	}
	public java.lang.String getRepUserName() {
		return this.repUserName;
	}
	/** ���˲���Ա��ɫ����||���˲���Ա��ɫ���� */
	public void setRepRoleName(java.lang.String repRoleName) {
		this.repRoleName = repRoleName == null ? null : repRoleName.trim();
	}
	public java.lang.String getRepRoleName() {
		return this.repRoleName;
	}
	/** ���˲���Ա������������||���˲���Ա������������ */
	public void setRepUserOrganCode(java.lang.String repUserOrganCode) {
		this.repUserOrganCode = repUserOrganCode == null ? null : repUserOrganCode.trim();
	}
	public java.lang.String getRepUserOrganCode() {
		return this.repUserOrganCode;
	}
	/** ���˲���Ա������������||���˲���Ա������������ */
	public void setRepUserOrganName(java.lang.String repUserOrganName) {
		this.repUserOrganName = repUserOrganName == null ? null : repUserOrganName.trim();
	}
	public java.lang.String getRepUserOrganName() {
		return this.repUserOrganName;
	}
	/** �������||������� */
	public void setRepDate(java.lang.String repDate) {
		this.repDate = repDate == null ? null : repDate.trim();
	}
	public java.lang.String getRepDate() {
		return this.repDate;
	}
	/** ����ʱ��||����ʱ�� */
	public void setRepTime(java.lang.String repTime) {
		this.repTime = repTime == null ? null : repTime.trim();
	}
	public java.lang.String getRepTime() {
		return this.repTime;
	}
	/** ���˱�ע||���˱�ע */
	public void setRepRemark(java.lang.String repRemark) {
		this.repRemark = repRemark == null ? null : repRemark.trim();
	}
	public java.lang.String getRepRemark() {
		return this.repRemark;
	}
	/** ����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر����� */
	public void setWebMsgStatus(java.lang.String webMsgStatus) {
		this.webMsgStatus = webMsgStatus == null ? null : webMsgStatus.trim();
	}
	public java.lang.String getWebMsgStatus() {
		return this.webMsgStatus;
	}
}