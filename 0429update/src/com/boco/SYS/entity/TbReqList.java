package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * �·��Ŵ�������Ҫ���ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqList extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * ����Ҫ���·���
     */
    public static final Integer REQ_TO_PRODUCER = 1;
    /**
     * ����Ҫ�������
     */
    public static final Integer REQ_TO_CONSUMER = 2;


    /**
     * ��δ����
     */
    public static final Integer STATE_UNDONE = 1;
    /**
     * ����
     */
    public static final Integer STATE_COMPLETE = 2;
    /**
     * ���id
     */
    @EntityParaAnno(zhName = "���id")
    private java.lang.Integer reqId;
    /**
     * ���������·�
     */
    @EntityParaAnno(zhName = "���������·�")
    private java.lang.String reqMonth;
    /**
     * ���󷢲�����
     */
    @EntityParaAnno(zhName = "���󷢲�����")
    private java.lang.String reqOrgan;
    /**
     * ��������
     * 0-����
     * 1-����
     */
    @EntityParaAnno(zhName = "��������")
    private java.lang.Integer reqType;
    /**
     * ����״̬
     */
    @EntityParaAnno(zhName = "����״̬")
    private java.lang.Integer reqState;
    /**
     * �������ʼʱ��
     */
    @EntityParaAnno(zhName = "�������ʼʱ��")
    private java.lang.String reqDateStart;
    /**
     * ���������ʱ��
     */
    @EntityParaAnno(zhName = "���������ʱ��")
    private java.lang.String reqDateEnd;
    /**
     * �˵��
     */
    @EntityParaAnno(zhName = "�˵��")
    private java.lang.String reqNote;
    /**
     * �·�������
     */
    @EntityParaAnno(zhName = "�·�������")
    private java.lang.Integer reqTo;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private java.lang.String reqCreateOper;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private java.lang.String reqUpdateOper;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqCreatetime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqUpdatetime;
    /**
     * �·��������
     */
    @EntityParaAnno(zhName = "�·��������")
    private java.lang.String reqOrganList;
    /**
     * �·�����������
     */
    @EntityParaAnno(zhName = "�·�����������")
    private java.lang.String reqCombList;
    /**
     * �·��������
     */
    @EntityParaAnno(zhName = "�·��������")
    private java.lang.String reqProdLine;
    /**
     * ���ڿ�ʼ����
     */
    @EntityParaAnno(zhName = "���ڿ�ʼ����")
    private java.lang.String reqTimeStart;
    /**
     * ���ڽ�������
     */
    @EntityParaAnno(zhName = "���ڽ�������")
    private java.lang.String reqTimeEnd;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private java.lang.String reqName;

    /**
     * ����λ
     */
    @EntityParaAnno(zhName = "����λ")
    private java.lang.Integer reqUnit;

    /**
     * ��������ʼ����
     */
    @EntityParaAnno(zhName = "��������ʼ����")
    private java.lang.String expTimeStart;
    /**
     * ��������������
     */
    @EntityParaAnno(zhName = "��������������")
    private java.lang.String expTimeEnd;
    /**
     * rateTimeStart
     */
    @EntityParaAnno(zhName = "rateTimeStart")
    private java.lang.String rateTimeStart;
    /**
     * rateTimeEnd
     */
    @EntityParaAnno(zhName = "rateTimeEnd")
    private java.lang.String rateTimeEnd;
    /**
     * balanceTimeStart
     */
    @EntityParaAnno(zhName = "balanceTimeStart")
    private java.lang.String balanceTimeStart;
    /**
     * balanceTimeEnd
     */
    @EntityParaAnno(zhName = "balanceTimeEnd")
    private java.lang.String balanceTimeEnd;

    /**
     * numType
     */
    @EntityParaAnno(zhName = "numType")
    private java.lang.String numType;

    public String getNumType() {
        return numType;
    }

    public void setNumType(String numType) {
        this.numType = numType;
    }
/** setter\getter���� */
    /**
     * ���id
     */
    public void setReqId(java.lang.Integer reqId) {
        this.reqId = reqId;
    }

    public java.lang.Integer getReqId() {
        return this.reqId;
    }

    /**
     * ���������·�
     */
    public void setReqMonth(java.lang.String reqMonth) {
        this.reqMonth = reqMonth == null ? null : reqMonth.trim();
    }

    public java.lang.String getReqMonth() {
        return this.reqMonth;
    }

    public String getRateTimeStart() {
        return rateTimeStart;
    }

    public void setRateTimeStart(String rateTimeStart) {
        this.rateTimeStart = rateTimeStart;
    }

    public String getRateTimeEnd() {
        return rateTimeEnd;
    }

    public void setRateTimeEnd(String rateTimeEnd) {
        this.rateTimeEnd = rateTimeEnd;
    }

    public String getBalanceTimeStart() {
        return balanceTimeStart;
    }

    public void setBalanceTimeStart(String balanceTimeStart) {
        this.balanceTimeStart = balanceTimeStart;
    }

    public String getBalanceTimeEnd() {
        return balanceTimeEnd;
    }

    public void setBalanceTimeEnd(String balanceTimeEnd) {
        this.balanceTimeEnd = balanceTimeEnd;
    }

    /**
     * ���󷢲�����
     */
    public void setReqOrgan(java.lang.String reqOrgan) {
        this.reqOrgan = reqOrgan == null ? null : reqOrgan.trim();
    }

    public java.lang.String getReqOrgan() {
        return this.reqOrgan;
    }

    public String getExpTimeStart() {
        return expTimeStart;
    }

    public void setExpTimeStart(String expTimeStart) {
        this.expTimeStart = expTimeStart;
    }

    public String getExpTimeEnd() {
        return expTimeEnd;
    }

    public void setExpTimeEnd(String expTimeEnd) {
        this.expTimeEnd = expTimeEnd;
    }

    /**
     * ��������
     */
    public void setReqType(java.lang.Integer reqType) {
        this.reqType = reqType;
    }

    public java.lang.Integer getReqType() {
        return this.reqType;
    }

    /**
     * ����״̬
     */
    public void setReqState(java.lang.Integer reqState) {
        this.reqState = reqState;
    }

    public java.lang.Integer getReqState() {
        return this.reqState;
    }

    /**
     * �������ʼʱ��
     */
    public void setReqDateStart(java.lang.String reqDateStart) {
        this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
    }

    public java.lang.String getReqDateStart() {
        return this.reqDateStart;
    }

    /**
     * ���������ʱ��
     */
    public void setReqDateEnd(java.lang.String reqDateEnd) {
        this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
    }

    public java.lang.String getReqDateEnd() {
        return this.reqDateEnd;
    }

    /**
     * �˵��
     */
    public void setReqNote(java.lang.String reqNote) {
        this.reqNote = reqNote == null ? null : reqNote.trim();
    }

    public java.lang.String getReqNote() {
        return this.reqNote;
    }

    /**
     * �·�������
     */
    public void setReqTo(java.lang.Integer reqTo) {
        this.reqTo = reqTo;
    }

    public java.lang.Integer getReqTo() {
        return this.reqTo;
    }

    /**
     * ������Աid
     */
    public void setReqCreateOper(java.lang.String reqCreateOper) {
        this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
    }

    public java.lang.String getReqCreateOper() {
        return this.reqCreateOper;
    }

    /**
     * ������Աid
     */
    public void setReqUpdateOper(java.lang.String reqUpdateOper) {
        this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
    }

    public java.lang.String getReqUpdateOper() {
        return this.reqUpdateOper;
    }

    /**
     * ����ʱ��
     */
    public void setReqCreatetime(java.lang.String reqCreatetime) {
        this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
    }

    public java.lang.String getReqCreatetime() {
        return this.reqCreatetime;
    }

    /**
     * ����ʱ��
     */
    public void setReqUpdatetime(java.lang.String reqUpdatetime) {
        this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
    }

    public java.lang.String getReqUpdatetime() {
        return this.reqUpdatetime;
    }

    /**
     * �·��������
     */
    public void setReqOrganList(java.lang.String reqOrganList) {
        this.reqOrganList = reqOrganList == null ? null : reqOrganList.trim();
    }

    public java.lang.String getReqOrganList() {
        return this.reqOrganList;
    }

    /**
     * �·�����������
     */
    public void setReqCombList(java.lang.String reqCombList) {
        this.reqCombList = reqCombList == null ? null : reqCombList.trim();
    }

    public java.lang.String getReqCombList() {
        return this.reqCombList;
    }

    /**
     * �·��������
     */
    public void setReqProdLine(java.lang.String reqProdLine) {
        this.reqProdLine = reqProdLine == null ? null : reqProdLine.trim();
    }

    public java.lang.String getReqProdLine() {
        return this.reqProdLine;
    }

    /**
     * ���ڿ�ʼ����
     */
    public void setReqTimeStart(java.lang.String reqTimeStart) {
        this.reqTimeStart = reqTimeStart == null ? null : reqTimeStart.trim();
    }

    public java.lang.String getReqTimeStart() {
        return this.reqTimeStart;
    }

    /**
     * ���ڽ�������
     */
    public void setReqTimeEnd(java.lang.String reqTimeEnd) {
        this.reqTimeEnd = reqTimeEnd == null ? null : reqTimeEnd.trim();
    }

    public java.lang.String getReqTimeEnd() {
        return this.reqTimeEnd;
    }

    /**
     * ��������
     */
    public void setReqName(java.lang.String reqName) {
        this.reqName = reqName == null ? null : reqName.trim();
    }

    public java.lang.String getReqName() {
        return this.reqName;
    }

    /**
     * ����λ
     */
    public void setReqUnit(java.lang.Integer reqUnit) {
        this.reqUnit = reqUnit;
    }

    public java.lang.Integer getReqUnit() {
        return this.reqUnit;
    }

}