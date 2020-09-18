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
public class TbLineTemp extends BaseEntity implements java.io.Serializable {


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
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private Integer qaId;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private String qaMonth;
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "����id")
    private String qaOrgan;
    /**
     * �������id
     */
    @EntityParaAnno(zhName = "�������id")
    private String qaComb;
    /**
     * �������/��ʱ���
     */
    @EntityParaAnno(zhName = "�������/��ʱ���")
    private BigDecimal qaAmt;
    /**
     * ��ʱ���ʧЧ����
     */
    @EntityParaAnno(zhName = "��ʱ���ʧЧ����")
    private String qaExpDate;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String qaReason;
    /**
     * �ϴ��ļ�id
     */
    @EntityParaAnno(zhName = "�ϴ��ļ�id")
    private String qaFileId;
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
    private String qaThreeInfo;
    /**
     * ֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    @EntityParaAnno(zhName = "֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  ")
    private String qaTwoInfo;
    /**
     * ֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    @EntityParaAnno(zhName = "֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  ")
    private String qaOneInfo;
    /**
     * ȫ��ƻ����� (67)
     */
    @EntityParaAnno(zhName = "ȫ��ƻ����� (67)")
    private BigDecimal qaYearRate;
    /**
     * ״̬
     */
    @EntityParaAnno(zhName = "״̬")
    private Integer qaState;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private String qaCreateOper;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String qaCreateTime;
    /**
     * ��ʱ�����Ч����
     */
    @EntityParaAnno(zhName = "��ʱ�����Ч����")
    private String qaStartDate;
    /**
     * ��λ
     */
    @EntityParaAnno(zhName = "��λ")
    private java.lang.Integer unit;

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
    /** setter\getter���� */

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
     * ��������
     */

    public void setQaId(Integer qaId) {
        this.qaId = qaId;
    }

    public Integer getQaId() {
        return this.qaId;
    }

    /**
     * �����·�
     */
    public void setQaMonth(String qaMonth) {
        this.qaMonth = qaMonth == null ? null : qaMonth.trim();
    }

    public String getQaMonth() {
        return this.qaMonth;
    }

    /**
     * ����id
     */
    public void setQaOrgan(String qaOrgan) {
        this.qaOrgan = qaOrgan == null ? null : qaOrgan.trim();
    }

    public String getQaOrgan() {
        return this.qaOrgan;
    }

    /**
     * �������id
     */
    public void setQaComb(String qaComb) {
        this.qaComb = qaComb == null ? null : qaComb.trim();
    }

    public String getQaComb() {
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
     * ��ʱ���ʧЧ����
     */
    public void setQaExpDate(String qaExpDate) {
        this.qaExpDate = qaExpDate == null ? null : qaExpDate.trim();
    }

    public String getQaExpDate() {
        return this.qaExpDate;
    }

    /**
     * ����
     */
    public void setQaReason(String qaReason) {
        this.qaReason = qaReason == null ? null : qaReason.trim();
    }

    public String getQaReason() {
        return this.qaReason;
    }

    /**
     * �ϴ��ļ�id
     */
    public void setQaFileId(String qaFileId) {
        this.qaFileId = qaFileId == null ? null : qaFileId.trim();
    }

    public String getQaFileId() {
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
    public void setQaThreeInfo(String qaThreeInfo) {
        this.qaThreeInfo = qaThreeInfo == null ? null : qaThreeInfo.trim();
    }

    public String getQaThreeInfo() {
        return this.qaThreeInfo;
    }

    /**
     * ֮ǰ�ڶ��������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    public void setQaTwoInfo(String qaTwoInfo) {
        this.qaTwoInfo = qaTwoInfo == null ? null : qaTwoInfo.trim();
    }

    public String getQaTwoInfo() {
        return this.qaTwoInfo;
    }

    /**
     * ֮ǰ��һ�������ó���ģ��� ���û򳬹�ģ���_�ٷֱ����磺-100_20%  |  +200_30%��
     */
    public void setQaOneInfo(String qaOneInfo) {
        this.qaOneInfo = qaOneInfo == null ? null : qaOneInfo.trim();
    }

    public String getQaOneInfo() {
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
    public void setQaState(Integer qaState) {
        this.qaState = qaState;
    }

    public Integer getQaState() {
        return this.qaState;
    }

    /**
     * ������
     */
    public void setQaCreateOper(String qaCreateOper) {
        this.qaCreateOper = qaCreateOper == null ? null : qaCreateOper.trim();
    }

    public String getQaCreateOper() {
        return this.qaCreateOper;
    }

    /**
     * ����ʱ��
     */
    public void setQaCreateTime(String qaCreateTime) {
        this.qaCreateTime = qaCreateTime == null ? null : qaCreateTime.trim();
    }

    public String getQaCreateTime() {
        return this.qaCreateTime;
    }

    /**
     * ��ʱ�����Ч����
     */
    public void setQaStartDate(String qaStartDate) {
        this.qaStartDate = qaStartDate == null ? null : qaStartDate.trim();
    }

    public String getQaStartDate() {
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
}