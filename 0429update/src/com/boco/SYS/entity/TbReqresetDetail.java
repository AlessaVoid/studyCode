package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �������뱨��Ҫ��¼����ϸ��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqresetDetail extends BaseEntity implements java.io.Serializable {
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
    /** ���� */
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "����id")
    private java.lang.Integer reqdresetId;
    /**
     * ��������id
     */
    @EntityParaAnno(zhName = "��������id")
    private java.lang.Integer reqdresetRefId;
    /**
     * �����id
     */
    @EntityParaAnno(zhName = "�����id")
    private java.lang.String reqdresetOrgan;
    /**
     * ��λ
     */
    @EntityParaAnno(zhName = "��λ")
    private java.lang.Integer reqdresetUnit;
    /**
     * �ϱ���
     */
    @EntityParaAnno(zhName = "�ϱ���")
    private java.lang.String reqdresetOper;
    /**
     * ��Ŀ״̬
     */
    @EntityParaAnno(zhName = "��Ŀ״̬")
    private java.lang.Integer reqdresetState;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqdresetCreatetime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String reqdresetUpdatetime;
    /**
     * ������Ա
     */
    @EntityParaAnno(zhName = "������Ա")
    private java.lang.String reqdresetUpdateoper;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private BigDecimal reqdresetNum;
    /**
     * ������ϱ���
     */
    @EntityParaAnno(zhName = "������ϱ���")
    private java.lang.String reqdresetCombCode;

    private String oldPlan;

    private String newPlan;

    private String totalOldPlan;

    private String totalNewPlan;


    private String totalNum;


    public String getTotalOldPlan() {
        return totalOldPlan;
    }

    public void setTotalOldPlan(String totalOldPlan) {
        this.totalOldPlan = totalOldPlan;
    }

    public String getTotalNewPlan() {
        return totalNewPlan;
    }

    public void setTotalNewPlan(String totalNewPlan) {
        this.totalNewPlan = totalNewPlan;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

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
     * ����id
     */
    public void setReqdresetId(java.lang.Integer reqdresetId) {
        this.reqdresetId = reqdresetId;
    }

    public java.lang.Integer getReqdresetId() {
        return this.reqdresetId;
    }

    /**
     * ��������id
     */
    public void setReqdresetRefId(java.lang.Integer reqdresetRefId) {
        this.reqdresetRefId = reqdresetRefId;
    }

    public java.lang.Integer getReqdresetRefId() {
        return this.reqdresetRefId;
    }

    /**
     * �����id
     */
    public void setReqdresetOrgan(java.lang.String reqdresetOrgan) {
        this.reqdresetOrgan = reqdresetOrgan == null ? null : reqdresetOrgan.trim();
    }

    public java.lang.String getReqdresetOrgan() {
        return this.reqdresetOrgan;
    }

    /**
     * ��λ
     */
    public void setReqdresetUnit(java.lang.Integer reqdresetUnit) {
        this.reqdresetUnit = reqdresetUnit;
    }

    public java.lang.Integer getReqdresetUnit() {
        return this.reqdresetUnit;
    }

    /**
     * �ϱ���
     */
    public void setReqdresetOper(java.lang.String reqdresetOper) {
        this.reqdresetOper = reqdresetOper == null ? null : reqdresetOper.trim();
    }

    public java.lang.String getReqdresetOper() {
        return this.reqdresetOper;
    }

    /**
     * ��Ŀ״̬
     */
    public void setReqdresetState(java.lang.Integer reqdresetState) {
        this.reqdresetState = reqdresetState;
    }

    public java.lang.Integer getReqdresetState() {
        return this.reqdresetState;
    }

    /**
     * ����ʱ��
     */
    public void setReqdresetCreatetime(java.lang.String reqdresetCreatetime) {
        this.reqdresetCreatetime = reqdresetCreatetime == null ? null : reqdresetCreatetime.trim();
    }

    public java.lang.String getReqdresetCreatetime() {
        return this.reqdresetCreatetime;
    }

    /**
     * ����ʱ��
     */
    public void setReqdresetUpdatetime(java.lang.String reqdresetUpdatetime) {
        this.reqdresetUpdatetime = reqdresetUpdatetime == null ? null : reqdresetUpdatetime.trim();
    }

    public java.lang.String getReqdresetUpdatetime() {
        return this.reqdresetUpdatetime;
    }

    /**
     * ������Ա
     */
    public void setReqdresetUpdateoper(java.lang.String reqdresetUpdateoper) {
        this.reqdresetUpdateoper = reqdresetUpdateoper == null ? null : reqdresetUpdateoper.trim();
    }

    public java.lang.String getReqdresetUpdateoper() {
        return this.reqdresetUpdateoper;
    }

    /**
     * ������
     */
    public void setReqdresetNum(BigDecimal reqdresetNum) {
        this.reqdresetNum = reqdresetNum;
    }

    public BigDecimal getReqdresetNum() {
        return this.reqdresetNum;
    }

    /**
     * ������ϱ���
     */
    public void setReqdresetCombCode(java.lang.String reqdresetCombCode) {
        this.reqdresetCombCode = reqdresetCombCode == null ? null : reqdresetCombCode.trim();
    }

    public java.lang.String getReqdresetCombCode() {
        return this.reqdresetCombCode;
    }

}