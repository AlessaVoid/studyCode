package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebReviewMainʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebReviewMain extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���˴��� */
	private java.lang.String appNo;
	/** ������ */
	private java.lang.String tradeCode;
	/** �������� */
	private java.lang.String operDescribe;
	/** ���ײ�������: 0- ���� 1- �޸�2- ɾ */
	private java.lang.String appType;
	/** ���׶������ */
	private java.lang.String operNo;
	/** ���׶������� */
	private java.lang.String operName;
	/** ������ϸҳ�� */
	private java.lang.String appUrl;
	/** ���±༭��ַ */
	private java.lang.String reeditUrl;
	/** �������Ա���� */
	private java.lang.String appUser;
	/** �������� */
	private java.lang.String appDate;
	/** ����ʱ�� */
	private java.lang.String appTime;
	/** �������Ա���� */
	private java.lang.String appOperName;
	/** �������Ա��ɫ���� */
	private java.lang.String appRoleName;
	/** �������Ա������������ */
	private java.lang.String appOrganCode;
	/** �������Ա������������ */
	private java.lang.String appOrganName;
	/** ���뱸ע */
	private java.lang.String appRemark;
	/** ���˲���Ա���� */
	private java.lang.String repUserCode;
	/** ���˲���Ա���� */
	private java.lang.String repUserName;
	/** ���˲���Ա��ɫ���� */
	private java.lang.String repRoleName;
	/** ���˲���Ա������������ */
	private java.lang.String repUserOrganCode;
	/** ���˲���Ա������������ */
	private java.lang.String repUserOrganName;
	/** �������� */
	private java.lang.String repDate;
	/** ����ʱ�� */
	private java.lang.String repTime;
	/** ���˱�ע */
	private java.lang.String repRemark;
	/** ����״̬: 1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����  */
	private java.lang.String repStatus;
	/** ������ʶ�����ڲ�ѯ��1-����,����-�������������б�ֻȡ�ǳ���״̬������  */
	private java.lang.String revocationFlag;
	/** ��ѯ��ʶ�������Բ�Ϊ��ʱ��Ĭ�ϲ�ѯĳ���û�����͸��˵��б��������ֻ��ѯ���û�����򸴺˵��б�  */
	private java.lang.String defaultList;
	/** �޸�����ʱ����ʾ�޸�ǰ������ */
	private java.lang.String oldData;
	/** ���뿪ʼʱ�� */
	private java.lang.String appBeginDate;
	/** �������ʱ�� */
	private java.lang.String appEndDate;
	
	/** setter\getter���� */
	/** ���˴��� */
	public void setAppNo(java.lang.String appNo) {
		this.appNo = appNo == null ? null : appNo.trim();
	}
	public java.lang.String getAppNo() {
		return this.appNo;
	}
	/** ������ */
	public void setTradeCode(java.lang.String tradeCode) {
		this.tradeCode = tradeCode == null ? null : tradeCode.trim();
	}
	public java.lang.String getTradeCode() {
		return this.tradeCode;
	}
	/** �������� */
	public void setOperDescribe(java.lang.String operDescribe) {
		this.operDescribe = operDescribe == null ? null : operDescribe.trim();
	}
	public java.lang.String getOperDescribe() {
		return this.operDescribe;
	}
	/** ���ײ�������: 0- ���� 1- �޸�2- ɾ */
	public void setAppType(java.lang.String appType) {
		this.appType = appType == null ? null : appType.trim();
	}
	public java.lang.String getAppType() {
		return this.appType;
	}
	/** ���׶������ */
	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo == null ? null : operNo.trim();
	}
	public java.lang.String getOperNo() {
		return this.operNo;
	}
	/** ���׶������� */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** ������ϸҳ�� */
	public void setAppUrl(java.lang.String appUrl) {
		this.appUrl = appUrl == null ? null : appUrl.trim();
	}
	public java.lang.String getAppUrl() {
		return this.appUrl;
	}
	/** ���±༭��ַ */
	public void setReeditUrl(java.lang.String reeditUrl) {
		this.reeditUrl = reeditUrl == null ? null : reeditUrl.trim();
	}
	public java.lang.String getReeditUrl() {
		return this.reeditUrl;
	}
	/** �������Ա���� */
	public void setAppUser(java.lang.String appUser) {
		this.appUser = appUser == null ? null : appUser.trim();
	}
	public java.lang.String getAppUser() {
		return this.appUser;
	}
	/** �������� */
	public void setAppDate(java.lang.String appDate) {
		this.appDate = appDate == null ? null : appDate.trim();
	}
	public java.lang.String getAppDate() {
		return this.appDate;
	}
	/** ����ʱ�� */
	public void setAppTime(java.lang.String appTime) {
		this.appTime = appTime == null ? null : appTime.trim();
	}
	public java.lang.String getAppTime() {
		return this.appTime;
	}
	/** �������Ա���� */
	public void setAppOperName(java.lang.String appOperName) {
		this.appOperName = appOperName == null ? null : appOperName.trim();
	}
	public java.lang.String getAppOperName() {
		return this.appOperName;
	}
	/** �������Ա��ɫ���� */
	public void setAppRoleName(java.lang.String appRoleName) {
		this.appRoleName = appRoleName == null ? null : appRoleName.trim();
	}
	public java.lang.String getAppRoleName() {
		return this.appRoleName;
	}
	/** �������Ա������������ */
	public void setAppOrganCode(java.lang.String appOrganCode) {
		this.appOrganCode = appOrganCode == null ? null : appOrganCode.trim();
	}
	public java.lang.String getAppOrganCode() {
		return this.appOrganCode;
	}
	/** �������Ա������������ */
	public void setAppOrganName(java.lang.String appOrganName) {
		this.appOrganName = appOrganName == null ? null : appOrganName.trim();
	}
	public java.lang.String getAppOrganName() {
		return this.appOrganName;
	}
	/** ���뱸ע */
	public void setAppRemark(java.lang.String appRemark) {
		this.appRemark = appRemark == null ? null : appRemark.trim();
	}
	public java.lang.String getAppRemark() {
		return this.appRemark;
	}
	/** ���˲���Ա���� */
	public void setRepUserCode(java.lang.String repUserCode) {
		this.repUserCode = repUserCode == null ? null : repUserCode.trim();
	}
	public java.lang.String getRepUserCode() {
		return this.repUserCode;
	}
	/** ���˲���Ա���� */
	public void setRepUserName(java.lang.String repUserName) {
		this.repUserName = repUserName == null ? null : repUserName.trim();
	}
	public java.lang.String getRepUserName() {
		return this.repUserName;
	}
	/** ���˲���Ա��ɫ���� */
	public void setRepRoleName(java.lang.String repRoleName) {
		this.repRoleName = repRoleName == null ? null : repRoleName.trim();
	}
	public java.lang.String getRepRoleName() {
		return this.repRoleName;
	}
	/** ���˲���Ա������������ */
	public void setRepUserOrganCode(java.lang.String repUserOrganCode) {
		this.repUserOrganCode = repUserOrganCode == null ? null : repUserOrganCode.trim();
	}
	public java.lang.String getRepUserOrganCode() {
		return this.repUserOrganCode;
	}
	/** ���˲���Ա������������ */
	public void setRepUserOrganName(java.lang.String repUserOrganName) {
		this.repUserOrganName = repUserOrganName == null ? null : repUserOrganName.trim();
	}
	public java.lang.String getRepUserOrganName() {
		return this.repUserOrganName;
	}
	/** �������� */
	public void setRepDate(java.lang.String repDate) {
		this.repDate = repDate == null ? null : repDate.trim();
	}
	public java.lang.String getRepDate() {
		return this.repDate;
	}
	/** ����ʱ�� */
	public void setRepTime(java.lang.String repTime) {
		this.repTime = repTime == null ? null : repTime.trim();
	}
	public java.lang.String getRepTime() {
		return this.repTime;
	}
	/** ���˱�ע */
	public void setRepRemark(java.lang.String repRemark) {
		this.repRemark = repRemark == null ? null : repRemark.trim();
	}
	public java.lang.String getRepRemark() {
		return this.repRemark;
	}
	/** ����״̬: 1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����  */
	public void setRepStatus(java.lang.String repStatus) {
		this.repStatus = repStatus == null ? null : repStatus.trim();
	}
	public java.lang.String getRepStatus() {
		return this.repStatus;
	}
	/** ������ʶ�����ڲ�ѯ��1-����,����-�������������б�ֻȡ�ǳ���״̬������  */
	public void setRevocationFlag(java.lang.String revocationFlag) {
		this.revocationFlag = revocationFlag;
	}
	public java.lang.String getRevocationFlag() {
		return revocationFlag;
	}
	/**��ѯ��ʶ�������Բ�Ϊ��ʱ��Ĭ�ϲ�ѯĳ���û�����͸��˵��б��������ֻ��ѯ���û�����򸴺˵��б�*/
	public void setDefaultList(java.lang.String defaultList) {
		this.defaultList = defaultList;
	}
	public java.lang.String getDefaultList() {
		return defaultList;
	}
	/** �޸�����ʱ����ʾ�޸�ǰ������ */
	public void setOldData(java.lang.String value) {
		this.oldData = value;
	}
	/** �޸�����ʱ����ʾ�޸�ǰ������ */
	public java.lang.String getOldData() {
		return this.oldData;
	}
	/**���뿪ʼʱ��*/
	public java.lang.String getAppBeginDate() {
		return appBeginDate;
	}
	/**���뿪ʼʱ��*/
	public void setAppBeginDate(java.lang.String appBeginDate) {
		this.appBeginDate = appBeginDate;
	}
	/**�������ʱ��*/
	public java.lang.String getAppEndDate() {
		return appEndDate;
	}
	/**�������ʱ��*/
	public void setAppEndDate(java.lang.String appEndDate) {
		this.appEndDate = appEndDate;
	}
}