package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ʱ��ƻ�ά����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbTradeParam extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "����id  ")
    private java.lang.Integer paramId;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private java.lang.String paramPeriod;
    /**
     * �ƻ�����ģʽ
     */
    @EntityParaAnno(zhName = "�ƻ�����ģʽ")
    private java.lang.Integer paramMode;
    /**
     * ��Ϣģ����
     */
    @EntityParaAnno(zhName = "��Ϣģ����")
    private java.lang.Integer paramPunishMode;
    /**
     * ����¼�뿪ʼʱ��
     */
    @EntityParaAnno(zhName = "����¼�뿪ʼʱ��")
    private java.lang.String paramReqStart;
    /**
     * ����¼�����ʱ��
     */
    @EntityParaAnno(zhName = "����¼�����ʱ��")
    private java.lang.String paramReqEnd;
    /**
     * �ƻ��ƶ���ʼʱ��
     */
    @EntityParaAnno(zhName = "�ƻ��ƶ���ʼʱ��")
    private java.lang.String paramPlanStart;
    /**
     * �ƻ��ƶ�����ʱ��
     */
    @EntityParaAnno(zhName = "�ƻ��ƶ�����ʱ��")
    private java.lang.String paramPlanEnd;
    /**
     * ��Ϣ��ʼʱ��
     */
    @EntityParaAnno(zhName = "��Ϣ��ʼʱ��")
    private java.lang.String paramPunishStart;
    /**
     * ��Ϣ����ʱ��
     */
    @EntityParaAnno(zhName = "��Ϣ����ʱ��")
    private java.lang.String paramPunsihEnd;
    /**
     * ��������ʱ��
     */
    @EntityParaAnno(zhName = "��������ʱ��")
    private java.lang.String paramCreatetime;
    /**
     * ��������ʱ��
     */
    @EntityParaAnno(zhName = "��������ʱ��")
    private java.lang.String paramUpdatetime;
    /**
     * ����������Աid
     */
    @EntityParaAnno(zhName = "����������Աid")
    private java.lang.String paramCreateuserid;
    /**
     * ����������Աid
     */
    @EntityParaAnno(zhName = "����������Աid")
    private java.lang.String paramUpdateuserid;
    /**
     * ���»����ƻ�������
     */
    @EntityParaAnno(zhName = "���»����ƻ�������")
    private BigDecimal paramMechIncrement;
    /**
     * �������߼ƻ�������
     */
    @EntityParaAnno(zhName = "�������߼ƻ�������")
    private BigDecimal paramLineIncrement;
    /**
     * ���³��޶��׼��
     */
    @EntityParaAnno(zhName = "���³��޶��׼��")
    private BigDecimal paramOverMount;
    /**
     * �Ƿ������߹ܿأ�0��1��
     */
    @EntityParaAnno(zhName = "�Ƿ������߹ܿأ�0��1��")
    private java.lang.Integer paramIsLine;
    /**
     * ������ʱ��ȱ�׼��
     */
    @EntityParaAnno(zhName = "������ʱ��ȱ�׼��")
    private BigDecimal paramTempMount;
    /**
     * ���µ���ר���׼��
     */
    @EntityParaAnno(zhName = "���µ���ר���׼��")
    private BigDecimal paramSingleMount;

    /** setter\getter���� */
    /**
     * ����id
     */
    public void setParamId(java.lang.Integer paramId) {
        this.paramId = paramId;
    }

    public java.lang.Integer getParamId() {
        return this.paramId;
    }

    /**
     * ��������
     */
    public void setParamPeriod(java.lang.String paramPeriod) {
        this.paramPeriod = paramPeriod == null ? null : paramPeriod.trim();
    }

    public java.lang.String getParamPeriod() {
        return this.paramPeriod;
    }

    /**
     * �ƻ�����ģʽ
     */
    public void setParamMode(java.lang.Integer paramMode) {
        this.paramMode = paramMode;
    }

    public java.lang.Integer getParamMode() {
        return this.paramMode;
    }

    /**
     * ��Ϣģ����
     */
    public void setParamPunishMode(java.lang.Integer paramPunishMode) {
        this.paramPunishMode = paramPunishMode;
    }

    public java.lang.Integer getParamPunishMode() {
        return this.paramPunishMode;
    }

    /**
     * ����¼�뿪ʼʱ��
     */
    public void setParamReqStart(java.lang.String paramReqStart) {
        this.paramReqStart = paramReqStart == null ? null : paramReqStart.trim();
    }

    public java.lang.String getParamReqStart() {
        return this.paramReqStart;
    }

    /**
     * ����¼�����ʱ��
     */
    public void setParamReqEnd(java.lang.String paramReqEnd) {
        this.paramReqEnd = paramReqEnd == null ? null : paramReqEnd.trim();
    }

    public java.lang.String getParamReqEnd() {
        return this.paramReqEnd;
    }

    /**
     * �ƻ��ƶ���ʼʱ��
     */
    public void setParamPlanStart(java.lang.String paramPlanStart) {
        this.paramPlanStart = paramPlanStart == null ? null : paramPlanStart.trim();
    }

    public java.lang.String getParamPlanStart() {
        return this.paramPlanStart;
    }

    /**
     * �ƻ��ƶ�����ʱ��
     */
    public void setParamPlanEnd(java.lang.String paramPlanEnd) {
        this.paramPlanEnd = paramPlanEnd == null ? null : paramPlanEnd.trim();
    }

    public java.lang.String getParamPlanEnd() {
        return this.paramPlanEnd;
    }

    /**
     * ��Ϣ��ʼʱ��
     */
    public void setParamPunishStart(java.lang.String paramPunishStart) {
        this.paramPunishStart = paramPunishStart == null ? null : paramPunishStart.trim();
    }

    public java.lang.String getParamPunishStart() {
        return this.paramPunishStart;
    }

    /**
     * ��Ϣ����ʱ��
     */
    public void setParamPunsihEnd(java.lang.String paramPunsihEnd) {
        this.paramPunsihEnd = paramPunsihEnd == null ? null : paramPunsihEnd.trim();
    }

    public java.lang.String getParamPunsihEnd() {
        return this.paramPunsihEnd;
    }

    /**
     * ��������ʱ��
     */
    public void setParamCreatetime(java.lang.String paramCreatetime) {
        this.paramCreatetime = paramCreatetime == null ? null : paramCreatetime.trim();
    }

    public java.lang.String getParamCreatetime() {
        return this.paramCreatetime;
    }

    /**
     * ��������ʱ��
     */
    public void setParamUpdatetime(java.lang.String paramUpdatetime) {
        this.paramUpdatetime = paramUpdatetime == null ? null : paramUpdatetime.trim();
    }

    public java.lang.String getParamUpdatetime() {
        return this.paramUpdatetime;
    }

    /**
     * ����������Աid
     */
    public void setParamCreateuserid(java.lang.String paramCreateuserid) {
        this.paramCreateuserid = paramCreateuserid == null ? null : paramCreateuserid.trim();
    }

    public java.lang.String getParamCreateuserid() {
        return this.paramCreateuserid;
    }

    /**
     * ����������Աid
     */
    public void setParamUpdateuserid(java.lang.String paramUpdateuserid) {
        this.paramUpdateuserid = paramUpdateuserid == null ? null : paramUpdateuserid.trim();
    }

    public java.lang.String getParamUpdateuserid() {
        return this.paramUpdateuserid;
    }

    /**
     * ���»����ƻ�������
     */
    public void setParamMechIncrement(BigDecimal paramMechIncrement) {
        this.paramMechIncrement = paramMechIncrement ;
    }

    public BigDecimal getParamMechIncrement() {
        return this.paramMechIncrement;
    }

    /**
     * �������߼ƻ�������
     */
    public void setParamLineIncrement(BigDecimal paramLineIncrement) {
        this.paramLineIncrement = paramLineIncrement ;
    }

    public BigDecimal getParamLineIncrement() {
        return this.paramLineIncrement;
    }

    /**
     * ���³��޶��׼��
     */
    public void setParamOverMount(BigDecimal paramOverMount) {
        this.paramOverMount = paramOverMount;
    }

    public BigDecimal getParamOverMount() {
        return this.paramOverMount;
    }

    /**
     * �Ƿ������߹ܿأ�0��1��
     */
    public void setParamIsLine(java.lang.Integer paramIsLine) {
        this.paramIsLine = paramIsLine;
    }

    public java.lang.Integer getParamIsLine() {
        return this.paramIsLine;
    }

    /**
     * ������ʱ��ȱ�׼��
     */
    public void setParamTempMount(BigDecimal paramTempMount) {
        this.paramTempMount = paramTempMount;
    }

    public BigDecimal getParamTempMount() {
        return this.paramTempMount;
    }

    /**
     * ���µ���ר���׼��
     */
    public void setParamSingleMount(BigDecimal paramSingleMount) {
        this.paramSingleMount = paramSingleMount;
    }

    public BigDecimal getParamSingleMount() {
        return this.paramSingleMount;
    }


}