package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �Ŵ�����¼����ϸ��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqDetail extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
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

    /**
     * reqdId
     */
    @EntityParaAnno(zhName = "reqdId")
    private java.lang.Integer reqdId;
    /**
     * ��������id
     */
    @EntityParaAnno(zhName = "��������id")
    private java.lang.Integer reqdRefId;
    /**
     * �����id
     */
    @EntityParaAnno(zhName = "�����id")
    private java.lang.String reqdOrgan;
    /**
     * ������ϱ���
     */
    @EntityParaAnno(zhName = "������ϱ���")
    private java.lang.String reqdCombCode;
    /**
     * ��λ
     * 1����Ԫ
     * 2����Ԫ
     */
    @EntityParaAnno(zhName = "��λ")
    private java.lang.Integer reqdUnit;
    /**
     * ��Ŀ״̬
     */
    @EntityParaAnno(zhName = "��Ŀ״̬")
    private java.lang.Integer reqdState;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqdCreateTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqdUpdateTime;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private BigDecimal reqdExpnum;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private BigDecimal reqdReqnum;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private BigDecimal reqdRate;
    /**
     * ���
     */
    @EntityParaAnno(zhName = "���")
    private BigDecimal reqdBalance;


    /**
     * ������
     */
    @EntityParaAnno(zhName = "�������ϼ�")
    private BigDecimal totalReqdExpnum;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "�������ϼ�")
    private BigDecimal totalReqdReqnum;
    /**
     * ���
     */
    @EntityParaAnno(zhName = "���ϼ�")
    private BigDecimal totalReqdBalance;


    /** setter\getter���� */
    /**
     * reqdId
     */
    public void setReqdId(java.lang.Integer reqdId) {
        this.reqdId = reqdId;
    }

    public java.lang.Integer getReqdId() {
        return this.reqdId;
    }

    /**
     * ��������id
     */
    public void setReqdRefId(java.lang.Integer reqdRefId) {
        this.reqdRefId = reqdRefId;
    }

    public java.lang.Integer getReqdRefId() {
        return this.reqdRefId;
    }

    /**
     * �����id
     */
    public void setReqdOrgan(java.lang.String reqdOrgan) {
        this.reqdOrgan = reqdOrgan == null ? null : reqdOrgan.trim();
    }

    public java.lang.String getReqdOrgan() {
        return this.reqdOrgan;
    }

    /**
     * ������ϱ���
     */
    public void setReqdCombCode(java.lang.String reqdCombCode) {
        this.reqdCombCode = reqdCombCode == null ? null : reqdCombCode.trim();
    }

    public java.lang.String getReqdCombCode() {
        return this.reqdCombCode;
    }

    /**
     * ��λ
     */
    public void setReqdUnit(java.lang.Integer reqdUnit) {
        this.reqdUnit = reqdUnit;
    }

    public java.lang.Integer getReqdUnit() {
        return this.reqdUnit;
    }

    /**
     * ��Ŀ״̬
     */
    public void setReqdState(java.lang.Integer reqdState) {
        this.reqdState = reqdState;
    }

    public java.lang.Integer getReqdState() {
        return this.reqdState;
    }

    /**
     * ����ʱ��
     */
    public void setReqdCreateTime(java.lang.String reqdCreateTime) {
        this.reqdCreateTime = reqdCreateTime == null ? null : reqdCreateTime.trim();
    }

    public java.lang.String getReqdCreateTime() {
        return this.reqdCreateTime;
    }

    /**
     * ����ʱ��
     */
    public void setReqdUpdateTime(java.lang.String reqdUpdateTime) {
        this.reqdUpdateTime = reqdUpdateTime == null ? null : reqdUpdateTime.trim();
    }

    public java.lang.String getReqdUpdateTime() {
        return this.reqdUpdateTime;
    }

    /**
     * ������
     */
    public void setReqdExpnum(BigDecimal reqdExpnum) {
        this.reqdExpnum = reqdExpnum;
    }

    public BigDecimal getReqdExpnum() {
        return this.reqdExpnum;
    }

    /**
     * ������
     */
    public void setReqdReqnum(BigDecimal reqdReqnum) {
        this.reqdReqnum = reqdReqnum;
    }

    public BigDecimal getReqdReqnum() {
        return this.reqdReqnum;
    }

    /**
     * ����
     */
    public void setReqdRate(BigDecimal reqdRate) {
        this.reqdRate = reqdRate;
    }

    public BigDecimal getReqdRate() {
        return this.reqdRate;
    }

    /**
     * ���
     */
    public void setReqdBalance(BigDecimal reqdBalance) {
        this.reqdBalance = reqdBalance;
    }

    public BigDecimal getReqdBalance() {
        return this.reqdBalance;
    }

    public BigDecimal getTotalReqdExpnum() {
        return totalReqdExpnum;
    }

    public void setTotalReqdExpnum(BigDecimal totalReqdExpnum) {
        this.totalReqdExpnum = totalReqdExpnum;
    }

    public BigDecimal getTotalReqdReqnum() {
        return totalReqdReqnum;
    }

    public void setTotalReqdReqnum(BigDecimal totalReqdReqnum) {
        this.totalReqdReqnum = totalReqdReqnum;
    }

    public BigDecimal getTotalReqdBalance() {
        return totalReqdBalance;
    }

    public void setTotalReqdBalance(BigDecimal totalReqdBalance) {
        this.totalReqdBalance = totalReqdBalance;
    }
}