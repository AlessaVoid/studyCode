package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �ƻ�ִ�������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPlanExecute extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String peId;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private String peMonth;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String peOrgan;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String peProdCode;
    /**
     * �ô����������
     */
    @EntityParaAnno(zhName = "�ô����������")
    private String peLastBalance;
    /**
     * �ô��ֱ��ڼƻ�����
     */
    @EntityParaAnno(zhName = "�ô��ֱ��ڼƻ�����")
    private String peQuota;
    /**
     * �ô��ֱ��ڵ�����
     */
    @EntityParaAnno(zhName = "�ô��ֱ��ڵ�����")
    private BigDecimal peExpire;
    /**
     * �ô��ֱ��ڷ�����
     */
    @EntityParaAnno(zhName = "�ô��ֱ��ڷ�����")
    private BigDecimal peOccurrence;
    /**
     * Ŀǰ���ö��
     */
    @EntityParaAnno(zhName = "Ŀǰ���ö��")
    private String peQuotaAvail;
    /**
     * �����е����
     */
    @EntityParaAnno(zhName = "�����е����")
    private String peInprogress;

    /** setter\getter���� */
    /**
     * ����
     */
    public void setPeId(String peId) {
        this.peId = peId == null ? null : peId.trim();
    }

    public String getPeId() {
        return this.peId;
    }

    /**
     * �����·�
     */
    public void setPeMonth(String peMonth) {
        this.peMonth = peMonth == null ? null : peMonth.trim();
    }

    public String getPeMonth() {
        return this.peMonth;
    }

    /**
     * ����
     */
    public void setPeOrgan(String peOrgan) {
        this.peOrgan = peOrgan == null ? null : peOrgan.trim();
    }

    public String getPeOrgan() {
        return this.peOrgan;
    }

    /**
     * ����
     */
    public void setPeProdCode(String peProdCode) {
        this.peProdCode = peProdCode == null ? null : peProdCode.trim();
    }

    public String getPeProdCode() {
        return this.peProdCode;
    }

    /**
     * �ô����������
     */
    public void setPeLastBalance(String peLastBalance) {
        this.peLastBalance = peLastBalance == null ? null : peLastBalance.trim();
    }

    public String getPeLastBalance() {
        return this.peLastBalance;
    }

    /**
     * �ô��ֱ��ڼƻ�����
     */
    public void setPeQuota(String peQuota) {
        this.peQuota = peQuota == null ? null : peQuota.trim();
    }

    public String getPeQuota() {
        return this.peQuota;
    }

    public BigDecimal getPeExpire() {
        return peExpire;
    }

    public void setPeExpire(BigDecimal peExpire) {
        this.peExpire = peExpire;
    }

    public BigDecimal getPeOccurrence() {
        return peOccurrence;
    }

    public void setPeOccurrence(BigDecimal peOccurrence) {
        this.peOccurrence = peOccurrence;
    }

    /**
     * Ŀǰ���ö��
     */
    public void setPeQuotaAvail(String peQuotaAvail) {
        this.peQuotaAvail = peQuotaAvail == null ? null : peQuotaAvail.trim();
    }

    public String getPeQuotaAvail() {
        return this.peQuotaAvail;
    }

    /**
     * �����е����
     */
    public void setPeInprogress(String peInprogress) {
        this.peInprogress = peInprogress == null ? null : peInprogress.trim();
    }

    public String getPeInprogress() {
        return this.peInprogress;
    }
}