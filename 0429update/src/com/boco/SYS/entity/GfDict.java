package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * GfDictʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class GfDict extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �ֵ��ʶ */
	private java.lang.String dictNo;
	/** �ֵ����� */
	private java.lang.String dictName;
	/** �ֵ�����-�ڲ� */
	private java.lang.String dictKeyIn;
	/** �ֵ�ֵ-�ڲ� */
	private java.lang.String dictValueIn;
	/** �ֵ�����-�ⲿ */
	private java.lang.String dictKeyOut;
	/** �ֵ�ֵ-�ⲿ */
	private java.lang.String dictValueOut;
	/** ��ʶ��� */
	private java.lang.Integer dictNoOrder;
	/** �ֵ����� */
	private java.lang.String dictDesc;
	/** ״̬ */
	private java.lang.String status;
	/** ����ʱ�� */
	private java.lang.String updateTime;
	/** �������� */
	private java.lang.String updateDate;
	public java.lang.String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.lang.String updateDate) {
		this.updateDate = updateDate;
	}
	/** ������Ա */
	private java.lang.String createOper;
	
	/** setter\getter���� */
	/** �ֵ��ʶ */
	public void setDictNo(java.lang.String dictNo) {
		this.dictNo = dictNo == null ? null : dictNo.trim();
	}
	public java.lang.String getDictNo() {
		return this.dictNo;
	}
	/** �ֵ����� */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName == null ? null : dictName.trim();
	}
	public java.lang.String getDictName() {
		return this.dictName;
	}
	/** �ֵ�����-�ڲ� */
	public void setDictKeyIn(java.lang.String dictKeyIn) {
		this.dictKeyIn = dictKeyIn == null ? null : dictKeyIn.trim();
	}
	public java.lang.String getDictKeyIn() {
		return this.dictKeyIn;
	}
	/** �ֵ�ֵ-�ڲ� */
	public void setDictValueIn(java.lang.String dictValueIn) {
		this.dictValueIn = dictValueIn == null ? null : dictValueIn.trim();
	}
	public java.lang.String getDictValueIn() {
		return this.dictValueIn;
	}
	/** �ֵ�����-�ⲿ */
	public void setDictKeyOut(java.lang.String dictKeyOut) {
		this.dictKeyOut = dictKeyOut == null ? null : dictKeyOut.trim();
	}
	public java.lang.String getDictKeyOut() {
		return this.dictKeyOut;
	}
	/** �ֵ�ֵ-�ⲿ */
	public void setDictValueOut(java.lang.String dictValueOut) {
		this.dictValueOut = dictValueOut == null ? null : dictValueOut.trim();
	}
	public java.lang.String getDictValueOut() {
		return this.dictValueOut;
	}
	/** ��ʶ��� */
	public void setDictNoOrder(java.lang.Integer dictNoOrder) {
		this.dictNoOrder = dictNoOrder;
	}
	public java.lang.Integer getDictNoOrder() {
		return this.dictNoOrder;
	}
	/** �ֵ����� */
	public void setDictDesc(java.lang.String dictDesc) {
		this.dictDesc = dictDesc == null ? null : dictDesc.trim();
	}
	public java.lang.String getDictDesc() {
		return this.dictDesc;
	}
	/** ״̬  1-���� 2-����*/
	public void setStatus(java.lang.String status) {
		this.status = status == null ? null : status.trim();
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	/** ����ʱ�� */
	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}
	public java.lang.String getUpdateTime() {
		return this.updateTime;
	}
	/** ������Ա */
	public void setCreateOper(java.lang.String createOper) {
		this.createOper = createOper == null ? null : createOper.trim();
	}
	public java.lang.String getCreateOper() {
		return this.createOper;
	}
}