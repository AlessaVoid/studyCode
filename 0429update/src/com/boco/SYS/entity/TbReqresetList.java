package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * TbReqresetListʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqresetList extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * ����Ҫ���·���
     */
    public static final Integer REQ_TO_PRODUCER = 1;
    /**
     * ����Ҫ�������
     */
    public static final Integer REQ_TO_CONSUMER = 2;

    /** ���� */
    /** ���id */
    @EntityParaAnno(zhName="���id")
    private java.lang.Integer reqresetId;
    /** �������������·� */
    @EntityParaAnno(zhName="�������������·�")
    private java.lang.String reqresetMonth;
    /** �������󷢲����� */
    @EntityParaAnno(zhName="�������󷢲�����")
    private java.lang.String reqresetOrgan;
    /** ������������ */
    @EntityParaAnno(zhName="������������")
    private java.lang.Integer reqresetType;
    /** ��������״̬ */
    @EntityParaAnno(zhName="��������״̬")
    private java.lang.Integer reqresetState;
    /** �����������ʼʱ�� */
    @EntityParaAnno(zhName="�����������ʼʱ��")
    private java.lang.String reqresetDateStart;
    /** �������������ʱ�� */
    @EntityParaAnno(zhName="�������������ʱ��")
    private java.lang.String reqresetDateEnd;
    /** ���������id */
    @EntityParaAnno(zhName="���������id")
    private java.lang.Integer reqresetAttachmentId;
    /** �����˵�� */
    @EntityParaAnno(zhName="�����˵��")
    private java.lang.String reqresetNote;
    /** �·������� */
    @EntityParaAnno(zhName="�·�������")
    private java.lang.Integer reqresetTo;
    /** ������Աid */
    @EntityParaAnno(zhName="������Աid")
    private java.lang.String reqresetCreateOper;
    /** ������Աid */
    @EntityParaAnno(zhName="������Աid")
    private java.lang.String reqresetUpdateOper;
    /** ����ʱ�� */
    @EntityParaAnno(zhName="����ʱ��")
    private java.lang.String reqresetCreatetime;
    /** ����ʱ�� */
    @EntityParaAnno(zhName="����ʱ��")
    private java.lang.String reqresetUpdatetime;
    /** ���ڿ�ʼ���� */
    @EntityParaAnno(zhName="���ڿ�ʼ����")
    private java.lang.String reqresetTimeStart;
    /** ���ڽ������� */
    @EntityParaAnno(zhName="���ڽ�������")
    private java.lang.String reqresetTimeEnd;
    /** �·�������� */
    @EntityParaAnno(zhName="�·��������")
    private java.lang.String reqresetOrganList;
    /** �·����������� */
    @EntityParaAnno(zhName="�·�����������")
    private java.lang.String reqresetCombList;
    /** �·�������� */
    @EntityParaAnno(zhName="�·��������")
    private java.lang.String reqresetProdLine;
    /** �������� */
    @EntityParaAnno(zhName="��������")
    private java.lang.String reqresetName;
    /** ����λ */
    @EntityParaAnno(zhName="����λ")
    private java.lang.Integer reqresetUnit;

    /** setter\getter���� */
    /** ���id */
    public void setReqresetId(java.lang.Integer reqresetId) {
        this.reqresetId = reqresetId;
    }
    public java.lang.Integer getReqresetId() {
        return this.reqresetId;
    }
    /** �������������·� */
    public void setReqresetMonth(java.lang.String reqresetMonth) {
        this.reqresetMonth = reqresetMonth == null ? null : reqresetMonth.trim();
    }
    public java.lang.String getReqresetMonth() {
        return this.reqresetMonth;
    }
    /** �������󷢲����� */
    public void setReqresetOrgan(java.lang.String reqresetOrgan) {
        this.reqresetOrgan = reqresetOrgan == null ? null : reqresetOrgan.trim();
    }
    public java.lang.String getReqresetOrgan() {
        return this.reqresetOrgan;
    }
    /** ������������ */
    public void setReqresetType(java.lang.Integer reqresetType) {
        this.reqresetType = reqresetType;
    }
    public java.lang.Integer getReqresetType() {
        return this.reqresetType;
    }
    /** ��������״̬ */
    public void setReqresetState(java.lang.Integer reqresetState) {
        this.reqresetState = reqresetState;
    }
    public java.lang.Integer getReqresetState() {
        return this.reqresetState;
    }
    /** �����������ʼʱ�� */
    public void setReqresetDateStart(java.lang.String reqresetDateStart) {
        this.reqresetDateStart = reqresetDateStart == null ? null : reqresetDateStart.trim();
    }
    public java.lang.String getReqresetDateStart() {
        return this.reqresetDateStart;
    }
    /** �������������ʱ�� */
    public void setReqresetDateEnd(java.lang.String reqresetDateEnd) {
        this.reqresetDateEnd = reqresetDateEnd == null ? null : reqresetDateEnd.trim();
    }
    public java.lang.String getReqresetDateEnd() {
        return this.reqresetDateEnd;
    }
    /** ���������id */
    public void setReqresetAttachmentId(java.lang.Integer reqresetAttachmentId) {
        this.reqresetAttachmentId = reqresetAttachmentId;
    }
    public java.lang.Integer getReqresetAttachmentId() {
        return this.reqresetAttachmentId;
    }
    /** �����˵�� */
    public void setReqresetNote(java.lang.String reqresetNote) {
        this.reqresetNote = reqresetNote == null ? null : reqresetNote.trim();
    }
    public java.lang.String getReqresetNote() {
        return this.reqresetNote;
    }
    /** �·������� */
    public void setReqresetTo(java.lang.Integer reqresetTo) {
        this.reqresetTo = reqresetTo;
    }
    public java.lang.Integer getReqresetTo() {
        return this.reqresetTo;
    }
    /** ������Աid */
    public void setReqresetCreateOper(java.lang.String reqresetCreateOper) {
        this.reqresetCreateOper = reqresetCreateOper == null ? null : reqresetCreateOper.trim();
    }
    public java.lang.String getReqresetCreateOper() {
        return this.reqresetCreateOper;
    }
    /** ������Աid */
    public void setReqresetUpdateOper(java.lang.String reqresetUpdateOper) {
        this.reqresetUpdateOper = reqresetUpdateOper == null ? null : reqresetUpdateOper.trim();
    }
    public java.lang.String getReqresetUpdateOper() {
        return this.reqresetUpdateOper;
    }
    /** ����ʱ�� */
    public void setReqresetCreatetime(java.lang.String reqresetCreatetime) {
        this.reqresetCreatetime = reqresetCreatetime == null ? null : reqresetCreatetime.trim();
    }
    public java.lang.String getReqresetCreatetime() {
        return this.reqresetCreatetime;
    }
    /** ����ʱ�� */
    public void setReqresetUpdatetime(java.lang.String reqresetUpdatetime) {
        this.reqresetUpdatetime = reqresetUpdatetime == null ? null : reqresetUpdatetime.trim();
    }
    public java.lang.String getReqresetUpdatetime() {
        return this.reqresetUpdatetime;
    }
    /** ���ڿ�ʼ���� */
    public void setReqresetTimeStart(java.lang.String reqresetTimeStart) {
        this.reqresetTimeStart = reqresetTimeStart == null ? null : reqresetTimeStart.trim();
    }
    public java.lang.String getReqresetTimeStart() {
        return this.reqresetTimeStart;
    }
    /** ���ڽ������� */
    public void setReqresetTimeEnd(java.lang.String reqresetTimeEnd) {
        this.reqresetTimeEnd = reqresetTimeEnd == null ? null : reqresetTimeEnd.trim();
    }
    public java.lang.String getReqresetTimeEnd() {
        return this.reqresetTimeEnd;
    }
    /** �·�������� */
    public void setReqresetOrganList(java.lang.String reqresetOrganList) {
        this.reqresetOrganList = reqresetOrganList == null ? null : reqresetOrganList.trim();
    }
    public java.lang.String getReqresetOrganList() {
        return this.reqresetOrganList;
    }
    /** �·����������� */
    public void setReqresetCombList(java.lang.String reqresetCombList) {
        this.reqresetCombList = reqresetCombList == null ? null : reqresetCombList.trim();
    }
    public java.lang.String getReqresetCombList() {
        return this.reqresetCombList;
    }
    /** �·�������� */
    public void setReqresetProdLine(java.lang.String reqresetProdLine) {
        this.reqresetProdLine = reqresetProdLine == null ? null : reqresetProdLine.trim();
    }
    public java.lang.String getReqresetProdLine() {
        return this.reqresetProdLine;
    }
    /** �������� */
    public void setReqresetName(java.lang.String reqresetName) {
        this.reqresetName = reqresetName == null ? null : reqresetName.trim();
    }
    public java.lang.String getReqresetName() {
        return this.reqresetName;
    }
    /** ����λ */
    public void setReqresetUnit(java.lang.Integer reqresetUnit) {
        this.reqresetUnit = reqresetUnit;
    }
    public java.lang.Integer getReqresetUnit() {
        return this.reqresetUnit;
    }
}