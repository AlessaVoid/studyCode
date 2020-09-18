package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * �·��Ŵ�������Ҫ���ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqListDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */

	/** ���id */
	@EntityParaAnno(zhName="���id")
	private java.lang.Integer reqId;
	/** ���������·� */
	@EntityParaAnno(zhName="���������·�")
	private java.lang.String reqMonth;
	/** ���󷢲����� */
	@EntityParaAnno(zhName="���󷢲�����")
	private java.lang.String reqOrgan;
	/** �������� */
	@EntityParaAnno(zhName="��������")
	private java.lang.Integer reqType;
	/** ����״̬ */
	@EntityParaAnno(zhName="����״̬")
	private java.lang.Integer reqState;
	/** �������ʼʱ�� */
	@EntityParaAnno(zhName="�������ʼʱ��")
	private java.lang.String reqDateStart;
	/** ���������ʱ�� */
	@EntityParaAnno(zhName="���������ʱ��")
	private java.lang.String reqDateEnd;
	/** �����id */
	@EntityParaAnno(zhName="�����id")
	private java.lang.Integer reqAttachmentId;
	/** �˵�� */
	@EntityParaAnno(zhName="�˵��")
	private java.lang.String reqNote;
	/** �·������� */
	@EntityParaAnno(zhName="�·�������")
	private java.lang.Integer reqTo;
	/** ������Աid */
	@EntityParaAnno(zhName="������Աid")
	private java.lang.String reqCreateOper;
	/** ������Աid */
	@EntityParaAnno(zhName="������Աid")
	private java.lang.String reqUpdateOper;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String reqCreatetime;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String reqUpdatetime;

	/** setter\getter���� */
	/** ���id */
	public void setReqId(java.lang.Integer reqId) {
		this.reqId = reqId;
	}
	public java.lang.Integer getReqId() {
		return this.reqId;
	}
	/** ���������·� */
	public void setReqMonth(java.lang.String reqMonth) {
		this.reqMonth = reqMonth == null ? null : reqMonth.trim();
	}
	public java.lang.String getReqMonth() {
		return this.reqMonth;
	}
	/** ���󷢲����� */
	public void setReqOrgan(java.lang.String reqOrgan) {
		this.reqOrgan = reqOrgan == null ? null : reqOrgan.trim();
	}
	public java.lang.String getReqOrgan() {
		return this.reqOrgan;
	}
	/** �������� */
	public void setReqType(java.lang.Integer reqType) {
		this.reqType = reqType;
	}
	public java.lang.Integer getReqType() {
		return this.reqType;
	}
	/** ����״̬ */
	public void setReqState(java.lang.Integer reqState) {
		this.reqState = reqState;
	}
	public java.lang.Integer getReqState() {
		return this.reqState;
	}
	/** �������ʼʱ�� */
	public void setReqDateStart(java.lang.String reqDateStart) {
		this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
	}
	public java.lang.String getReqDateStart() {
		return this.reqDateStart;
	}
	/** ���������ʱ�� */
	public void setReqDateEnd(java.lang.String reqDateEnd) {
		this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
	}
	public java.lang.String getReqDateEnd() {
		return this.reqDateEnd;
	}
	/** �����id */
	public void setReqAttachmentId(java.lang.Integer reqAttachmentId) {
		this.reqAttachmentId = reqAttachmentId;
	}
	public java.lang.Integer getReqAttachmentId() {
		return this.reqAttachmentId;
	}
	/** �˵�� */
	public void setReqNote(java.lang.String reqNote) {
		this.reqNote = reqNote == null ? null : reqNote.trim();
	}
	public java.lang.String getReqNote() {
		return this.reqNote;
	}
	/** �·������� */
	public void setReqTo(java.lang.Integer reqTo) {
		this.reqTo = reqTo;
	}
	public java.lang.Integer getReqTo() {
		return this.reqTo;
	}
	/** ������Աid */
	public void setReqCreateOper(java.lang.String reqCreateOper) {
		this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
	}
	public java.lang.String getReqCreateOper() {
		return this.reqCreateOper;
	}
	/** ������Աid */
	public void setReqUpdateOper(java.lang.String reqUpdateOper) {
		this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
	}
	public java.lang.String getReqUpdateOper() {
		return this.reqUpdateOper;
	}
	/** ����ʱ�� */
	public void setReqCreatetime(java.lang.String reqCreatetime) {
		this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
	}
	public java.lang.String getReqCreatetime() {
		return this.reqCreatetime;
	}
	/** ����ʱ�� */
	public void setReqUpdatetime(java.lang.String reqUpdatetime) {
		this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
	}
	public java.lang.String getReqUpdatetime() {
		return this.reqUpdatetime;
	}
}