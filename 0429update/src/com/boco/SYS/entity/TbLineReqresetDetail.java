package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * �������������¼�����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbLineReqresetDetail extends BaseEntity implements java.io.Serializable {

    /**
     * δ�·�
     */
    public static final int STATE_NEW = 0;
    /**
     * ���·�
     */
    public static final int STATE_ISSUED = 1;
    /**
     * ����д,���ύ
     */
    public static final int STATE_FILL = 2;
    /**
     * ���ύ��������
     */
    public static final int STATE_SUBMITED = 4;
    /**
     * ������
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * ����ͨ�������ϱ�
     */
    public static final int STATE_APPROVED = 16;
    /**
     * �Ѳ���
     */
    public static final int STATE_DISMISSED = 32;

    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ����������������¼id
     */
    @EntityParaAnno(zhName = "����������������¼id")
    private java.lang.Integer lineReqresetId;
    /**
     * lineResetrefId
     */
    @EntityParaAnno(zhName = "lineResetrefId")
    private java.lang.Integer lineResetrefId;
    /**
     * lineOrgan
     */
    @EntityParaAnno(zhName = "lineOrgan")
    private java.lang.String lineOrgan;
    /**
     * lineCode
     */
    @EntityParaAnno(zhName = "lineCode")
    private java.lang.String lineCode;
    /**
     * lineName
     */
    @EntityParaAnno(zhName = "lineName")
    private java.lang.String lineName;
    /**
     * lineCombCode
     */
    @EntityParaAnno(zhName = "lineCombCode")
    private java.lang.String lineCombCode;
    /**
     * lineState
     */
    @EntityParaAnno(zhName = "lineState")
    private java.lang.Integer lineState;
    /**
     * lineUnit
     */
    @EntityParaAnno(zhName = "lineUnit")
    private java.lang.Integer lineUnit;
    /**
     * lineNum
     */
    @EntityParaAnno(zhName = "lineNum")
    private java.lang.String lineNum;
    /**
     * lineCreateTime
     */
    @EntityParaAnno(zhName = "lineCreateTime")
    private java.lang.String lineCreateTime;
    /**
     * lineUpdateTime
     */
    @EntityParaAnno(zhName = "lineUpdateTime")
    private java.lang.String lineUpdateTime;
    /**
     * lineReqMonth
     */
    @EntityParaAnno(zhName = "lineReqMonth")
    private java.lang.String lineReqMonth;
    /**
     * lineReqName
     */
    @EntityParaAnno(zhName = "lineReqName")
    private java.lang.String lineReqName;
    /**
     * lineReqNote
     */
    @EntityParaAnno(zhName = "lineReqNote")
    private java.lang.String lineReqNote;

    /**
     * ԭ�ƻ�
     */
    private String oldPlan;

    /**
     * ������ƻ�
     */
    private String newPlan;

    public String getOldPlan() {
        return oldPlan;
    }

    public void setOldPlan(String oldPlan) {
        this.oldPlan = oldPlan;
    }

    public String getNewPlan() {
        return newPlan;
    }

    public void setNewPlan(String newPlan) {
        this.newPlan = newPlan;
    }

/** setter\getter���� */
    /**
     * ����������������¼id
     */
    public void setLineReqresetId(java.lang.Integer lineReqresetId) {
        this.lineReqresetId = lineReqresetId;
    }

    public java.lang.Integer getLineReqresetId() {
        return this.lineReqresetId;
    }

    /**
     * lineResetrefId
     */
    public void setLineResetrefId(java.lang.Integer lineResetrefId) {
        this.lineResetrefId = lineResetrefId;
    }

    public java.lang.Integer getLineResetrefId() {
        return this.lineResetrefId;
    }

    /**
     * lineOrgan
     */
    public void setLineOrgan(java.lang.String lineOrgan) {
        this.lineOrgan = lineOrgan == null ? null : lineOrgan.trim();
    }

    public java.lang.String getLineOrgan() {
        return this.lineOrgan;
    }

    /**
     * lineCode
     */
    public void setLineCode(java.lang.String lineCode) {
        this.lineCode = lineCode == null ? null : lineCode.trim();
    }

    public java.lang.String getLineCode() {
        return this.lineCode;
    }

    /**
     * lineName
     */
    public void setLineName(java.lang.String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public java.lang.String getLineName() {
        return this.lineName;
    }

    /**
     * lineCombCode
     */
    public void setLineCombCode(java.lang.String lineCombCode) {
        this.lineCombCode = lineCombCode == null ? null : lineCombCode.trim();
    }

    public java.lang.String getLineCombCode() {
        return this.lineCombCode;
    }

    /**
     * lineState
     */
    public void setLineState(java.lang.Integer lineState) {
        this.lineState = lineState;
    }

    public java.lang.Integer getLineState() {
        return this.lineState;
    }

    /**
     * lineUnit
     */
    public void setLineUnit(java.lang.Integer lineUnit) {
        this.lineUnit = lineUnit;
    }

    public java.lang.Integer getLineUnit() {
        return this.lineUnit;
    }

    /**
     * lineNum
     */
    public void setLineNum(java.lang.String lineNum) {
        this.lineNum = lineNum == null ? null : lineNum.trim();
    }

    public java.lang.String getLineNum() {
        return this.lineNum;
    }

    /**
     * lineCreateTime
     */
    public void setLineCreateTime(java.lang.String lineCreateTime) {
        this.lineCreateTime = lineCreateTime == null ? null : lineCreateTime.trim();
    }

    public java.lang.String getLineCreateTime() {
        return this.lineCreateTime;
    }

    /**
     * lineUpdateTime
     */
    public void setLineUpdateTime(java.lang.String lineUpdateTime) {
        this.lineUpdateTime = lineUpdateTime == null ? null : lineUpdateTime.trim();
    }

    public java.lang.String getLineUpdateTime() {
        return this.lineUpdateTime;
    }

    /**
     * lineReqMonth
     */
    public void setLineReqMonth(java.lang.String lineReqMonth) {
        this.lineReqMonth = lineReqMonth == null ? null : lineReqMonth.trim();
    }

    public java.lang.String getLineReqMonth() {
        return this.lineReqMonth;
    }

    /**
     * lineReqName
     */
    public void setLineReqName(java.lang.String lineReqName) {
        this.lineReqName = lineReqName == null ? null : lineReqName.trim();
    }

    public java.lang.String getLineReqName() {
        return this.lineReqName;
    }

    /**
     * lineReqNote
     */
    public void setLineReqNote(java.lang.String lineReqNote) {
        this.lineReqNote = lineReqNote == null ? null : lineReqNote.trim();
    }

    public java.lang.String getLineReqNote() {
        return this.lineReqNote;
    }
}