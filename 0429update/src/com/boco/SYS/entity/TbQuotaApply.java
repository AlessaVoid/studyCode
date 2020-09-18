package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ���޶�������Ϣ��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbQuotaApply extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;


    /**
     * �ݸ�
     */
    public static final int STATE_DRAFT = 0;
    /**
     * �½�
     */
    public static final int STATE_NEW = 1;
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
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private java.lang.Integer qaId;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private java.lang.String qaMonth;
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "����id")
    private java.lang.String qaOrgan;
    /**
     * �������id
     */
    @EntityParaAnno(zhName = "�������id")
    private java.lang.String qaComb;
    /**
     * �������/��ʱ���
     */
    @EntityParaAnno(zhName = "�������/��ʱ���")
    private BigDecimal qaAmt;
    /**
     * ���� 0���޶1��ʱ���
     */
    @EntityParaAnno(zhName = "���� 0���ߣ�1����")
    private java.lang.Integer qaType;
    /**
     * ��ʱ�����Чʱ��
     */
    @EntityParaAnno(zhName = "��ʱ�����Чʱ��")
    private java.lang.String qaExpDate;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private java.lang.String qaReason;
    /**
     * �ϴ��ļ�id
     */
    @EntityParaAnno(zhName = "�ϴ��ļ�id")
    private java.lang.String qaFileId;
    /**
     * ���¼ƻ�
     */
    @EntityParaAnno(zhName = "���¼ƻ�")
    private BigDecimal qaPlanAmt;
    /**
     * ���³��ƻ����
     */
    @EntityParaAnno(zhName = "���³��ƻ����")
    private BigDecimal qaOverAmt;
    /**
     * ֮ǰ�����������ó���ģ��������û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    @EntityParaAnno(zhName = "֮ǰ�����������ó���ģ��������û򳬹�ģ���_�ٷֱ����磺-100_20%  ")
    private java.lang.String qaThreeInfo;
    /**
     * ֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    @EntityParaAnno(zhName = "֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  ")
    private java.lang.String qaTwoInfo;
    /**
     * ֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    @EntityParaAnno(zhName = "֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  ")
    private java.lang.String qaOneInfo;
    /**
     * ȫ��ƻ����� (67)
     */
    @EntityParaAnno(zhName = "ȫ��ƻ����� (67)")
    private BigDecimal qaYearRate;
    /**
     * ״̬
     */
    @EntityParaAnno(zhName = "״̬")
    private java.lang.Integer qaState;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private java.lang.String qaCreateOper;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private java.lang.String qaCreateTime;
    /**
     * qaStartDate
     */
    @EntityParaAnno(zhName = "qaStartDate")
    private java.lang.String qaStartDate;
    /**
     * ��λ
     */
    @EntityParaAnno(zhName = "��λ")
    private java.lang.Integer unit;
    /**
     * ��ʱ���ʹ�û���
     */
    @EntityParaAnno(zhName = "��ʱ���ʹ�û���")
    private java.lang.String organCode;
    /**
     * ��ʱ���ʹ�û�������
     */
    @EntityParaAnno(zhName = "��ʱ���ʹ�û�������")
    private java.lang.String organName;

    /** setter\getter���� */
    /**
     * ��������
     */
    public void setQaId(java.lang.Integer qaId) {
        this.qaId = qaId;
    }

    public java.lang.Integer getQaId() {
        return this.qaId;
    }

    /**
     * �����·�
     */
    public void setQaMonth(java.lang.String qaMonth) {
        this.qaMonth = qaMonth == null ? null : qaMonth.trim();
    }

    public java.lang.String getQaMonth() {
        return this.qaMonth;
    }

    /**
     * ����id
     */
    public void setQaOrgan(java.lang.String qaOrgan) {
        this.qaOrgan = qaOrgan == null ? null : qaOrgan.trim();
    }

    public java.lang.String getQaOrgan() {
        return this.qaOrgan;
    }

    /**
     * �������id
     */
    public void setQaComb(java.lang.String qaComb) {
        this.qaComb = qaComb == null ? null : qaComb.trim();
    }

    public java.lang.String getQaComb() {
        return this.qaComb;
    }

    /**
     * �������/��ʱ���
     */
    public void setQaAmt(BigDecimal qaAmt) {
        this.qaAmt = qaAmt;
    }

    public BigDecimal getQaAmt() {
        return this.qaAmt;
    }

    /**
     * ���� 0���޶1��ʱ���
     */
    public void setQaType(java.lang.Integer qaType) {
        this.qaType = qaType;
    }

    public java.lang.Integer getQaType() {
        return this.qaType;
    }

    /**
     * ��ʱ�����Чʱ��
     */
    public void setQaExpDate(java.lang.String qaExpDate) {
        this.qaExpDate = qaExpDate == null ? null : qaExpDate.trim();
    }

    public java.lang.String getQaExpDate() {
        return this.qaExpDate;
    }

    /**
     * ����
     */
    public void setQaReason(java.lang.String qaReason) {
        this.qaReason = qaReason == null ? null : qaReason.trim();
    }

    public java.lang.String getQaReason() {
        return this.qaReason;
    }

    /**
     * �ϴ��ļ�id
     */
    public void setQaFileId(java.lang.String qaFileId) {
        this.qaFileId = qaFileId == null ? null : qaFileId.trim();
    }

    public java.lang.String getQaFileId() {
        return this.qaFileId;
    }

    /**
     * ���¼ƻ�
     */
    public void setQaPlanAmt(BigDecimal qaPlanAmt) {
        this.qaPlanAmt = qaPlanAmt;
    }

    public BigDecimal getQaPlanAmt() {
        return this.qaPlanAmt;
    }

    /**
     * ���³��ƻ����
     */
    public void setQaOverAmt(BigDecimal qaOverAmt) {
        this.qaOverAmt = qaOverAmt;
    }

    public BigDecimal getQaOverAmt() {
        return this.qaOverAmt;
    }

    /**
     * ֮ǰ�����������ó���ģ��������û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    public void setQaThreeInfo(java.lang.String qaThreeInfo) {
        this.qaThreeInfo = qaThreeInfo == null ? null : qaThreeInfo.trim();
    }

    public java.lang.String getQaThreeInfo() {
        return this.qaThreeInfo;
    }

    /**
     * ֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    public void setQaTwoInfo(java.lang.String qaTwoInfo) {
        this.qaTwoInfo = qaTwoInfo == null ? null : qaTwoInfo.trim();
    }

    public java.lang.String getQaTwoInfo() {
        return this.qaTwoInfo;
    }

    /**
     * ֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    public void setQaOneInfo(java.lang.String qaOneInfo) {
        this.qaOneInfo = qaOneInfo == null ? null : qaOneInfo.trim();
    }

    public java.lang.String getQaOneInfo() {
        return this.qaOneInfo;
    }

    /**
     * ȫ��ƻ����� (67)
     */
    public void setQaYearRate(BigDecimal qaYearRate) {
        this.qaYearRate = qaYearRate;
    }

    public BigDecimal getQaYearRate() {
        return this.qaYearRate;
    }

    /**
     * ״̬
     */
    public void setQaState(java.lang.Integer qaState) {
        this.qaState = qaState;
    }

    public java.lang.Integer getQaState() {
        return this.qaState;
    }

    /**
     * ������
     */
    public void setQaCreateOper(java.lang.String qaCreateOper) {
        this.qaCreateOper = qaCreateOper == null ? null : qaCreateOper.trim();
    }

    public java.lang.String getQaCreateOper() {
        return this.qaCreateOper;
    }

    /**
     * ����ʱ��
     */
    public void setQaCreateTime(java.lang.String qaCreateTime) {
        this.qaCreateTime = qaCreateTime == null ? null : qaCreateTime.trim();
    }

    public java.lang.String getQaCreateTime() {
        return this.qaCreateTime;
    }

    /**
     * qaStartDate
     */
    public void setQaStartDate(java.lang.String qaStartDate) {
        this.qaStartDate = qaStartDate == null ? null : qaStartDate.trim();
    }

    public java.lang.String getQaStartDate() {
        return this.qaStartDate;
    }

    /**
     * ��λ
     */
    public void setUnit(java.lang.Integer unit) {
        this.unit = unit;
    }

    public java.lang.Integer getUnit() {
        return this.unit;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}