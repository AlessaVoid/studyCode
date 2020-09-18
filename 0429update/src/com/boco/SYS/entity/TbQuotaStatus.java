package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * ������������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbQuotaStatus extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //������
    public static final String status_end = "0";
    //Ԥ����
    public static final String status_new = "1";
    //δ����
    public static final String status_start = "2";


    /** ���� */
    /**
     * �·�
     */
    @EntityParaAnno(zhName = "�·�")
    private java.lang.String planMonth;
    /**
     * �������״̬0������1�ƻ����ƶ�δ���ɶ��2�ƻ�δ�ƶ�
     */
    @EntityParaAnno(zhName = "�������״̬0������1�ƻ����ƶ�δ���ɶ��2�ƻ�δ�ƶ�")
    private java.lang.String quotaStatus;
    /**
     * ִ���������״̬0������1Ԥ����2δ����
     */
    @EntityParaAnno(zhName = "ִ���������״̬0������1Ԥ����2δ����")
    private java.lang.String executeStatus;

    /** setter\getter���� */
    /**
     * �·�
     */
    public void setPlanMonth(java.lang.String planMonth) {
        this.planMonth = planMonth == null ? null : planMonth.trim();
    }

    public java.lang.String getPlanMonth() {
        return this.planMonth;
    }

    /**
     * �������״̬0������1�ƻ����ƶ�δ���ɶ��2�ƻ�δ�ƶ�
     */
    public void setQuotaStatus(java.lang.String quotaStatus) {
        this.quotaStatus = quotaStatus == null ? null : quotaStatus.trim();
    }

    public java.lang.String getQuotaStatus() {
        return this.quotaStatus;
    }

    /**
     * ִ���������״̬0������1Ԥ����2δ����
     */
    public void setExecuteStatus(java.lang.String executeStatus) {
        this.executeStatus = executeStatus == null ? null : executeStatus.trim();
    }

    public java.lang.String getExecuteStatus() {
        return this.executeStatus;
    }

}