package com.boco.SYS.entity;


import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �ƻ��ƶ���ϸ��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPlanDetail extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * pdId
     */
    @EntityParaAnno(zhName = "pdId")
    private java.lang.String pdId;
    /**
     * ���κ�
     */
    @EntityParaAnno(zhName = "���κ�")
    private java.lang.String pdRefId;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private java.lang.String pdOrgan;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private java.lang.String pdMonth;
    /**
     * �����������
     */
    @EntityParaAnno(zhName = "pdLoanType")
    private java.lang.String pdLoanType;
    /**
     * ���
     */
    @EntityParaAnno(zhName = "���")
    private BigDecimal pdAmount;
    /**
     * ��λ
     */
    @EntityParaAnno(zhName = "��λ")
    private java.lang.Integer pdUnit;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String pdCreateTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String pdUpdateTime;

    private String __status;

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }
    /** setter\getter���� */
    /**
     * pdId
     */
    public void setPdId(java.lang.String pdId) {
        this.pdId = pdId == null ? null : pdId.trim();
    }

    public java.lang.String getPdId() {
        return this.pdId;
    }

    /**
     * ���κ�
     */
    public void setPdRefId(java.lang.String pdRefId) {
        this.pdRefId = pdRefId == null ? null : pdRefId.trim();
    }

    public java.lang.String getPdRefId() {
        return this.pdRefId;
    }

    /**
     * ������
     */
    public void setPdOrgan(java.lang.String pdOrgan) {
        this.pdOrgan = pdOrgan == null ? null : pdOrgan.trim();
    }

    public java.lang.String getPdOrgan() {
        return this.pdOrgan;
    }

    /**
     * �����·�
     */
    public void setPdMonth(java.lang.String pdMonth) {
        this.pdMonth = pdMonth == null ? null : pdMonth.trim();
    }

    public java.lang.String getPdMonth() {
        return this.pdMonth;
    }

    /**
     * pdLoanType
     */
    public void setPdLoanType(java.lang.String pdLoanType) {
        this.pdLoanType = pdLoanType == null ? null : pdLoanType.trim();
    }

    public java.lang.String getPdLoanType() {
        return this.pdLoanType;
    }

    /*���*/
    public BigDecimal getPdAmount() {
        return pdAmount;
    }

    public void setPdAmount(BigDecimal pdAmount) {
        this.pdAmount = pdAmount;
    }

    /**
     * ��λ
     */
    public void setPdUnit(java.lang.Integer pdUnit) {
        this.pdUnit = pdUnit;
    }

    public java.lang.Integer getPdUnit() {
        return this.pdUnit;
    }

    /**
     * ����ʱ��
     */
    public void setPdCreateTime(java.lang.String pdCreateTime) {
        this.pdCreateTime = pdCreateTime == null ? null : pdCreateTime.trim();
    }

    public java.lang.String getPdCreateTime() {
        return this.pdCreateTime;
    }

    /**
     * ����ʱ��
     */
    public void setPdUpdateTime(java.lang.String pdUpdateTime) {
        this.pdUpdateTime = pdUpdateTime == null ? null : pdUpdateTime.trim();
    }

    public java.lang.String getPdUpdateTime() {
        return this.pdUpdateTime;
    }
}