package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ���������¼�����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbLineReqDetailDTO extends BaseEntity implements java.io.Serializable {
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
     * �������������¼id
     */
    @EntityParaAnno(zhName = "�������������¼id")
    private Integer lineReqId;
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "lineRefId")
    private Integer lineRefId;
    /**
     * �����·�����
     */
    @EntityParaAnno(zhName = "lineOrgan")
    private String lineOrgan;
    /**
     * ����code
     */
    @EntityParaAnno(zhName = "lineCode")
    private String lineCode;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "lineName")
    private String lineName;
    /**
     * ������Ͻ �������code
     */
    @EntityParaAnno(zhName = "lineCombCode")
    private String lineCombCode;
    /**
     * ����״̬
     */
    @EntityParaAnno(zhName = "lineState")
    private Integer lineState;
    /**
     * ���������¼��λ
     */
    @EntityParaAnno(zhName = "lineUnit")
    private Integer lineUnit;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "lineExpnum")
    private BigDecimal lineExpnum;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "lineReqnum")
    private BigDecimal lineReqnum;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "lineRate")
    private BigDecimal lineRate;
    /**
     * ���
     */
    @EntityParaAnno(zhName = "lineBalance")
    private BigDecimal lineBalance;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "lineCreateTime")
    private String lineCreateTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "lineUpdateTime")
    private String lineUpdateTime;
    /**
     * �������������·�
     */
    @EntityParaAnno(zhName = "lineReqMonth")
    private String lineReqMonth;


    private BigDecimal totalAmount;

    /** setter\getter���� */


    /**
     * �������������¼id
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }


    /**
     * �������������¼id
     */
    public void setLineReqId(Integer lineReqId) {
        this.lineReqId = lineReqId;
    }

    public Integer getLineReqId() {
        return this.lineReqId;
    }

    /**
     * lineRefId
     */
    public void setLineRefId(Integer lineRefId) {
        this.lineRefId = lineRefId;
    }

    public Integer getLineRefId() {
        return this.lineRefId;
    }

    /**
     * lineOrgan
     */
    public void setLineOrgan(String lineOrgan) {
        this.lineOrgan = lineOrgan == null ? null : lineOrgan.trim();
    }

    public String getLineOrgan() {
        return this.lineOrgan;
    }

    /**
     * lineCode
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode == null ? null : lineCode.trim();
    }

    public String getLineCode() {
        return this.lineCode;
    }

    /**
     * lineName
     */
    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getLineName() {
        return this.lineName;
    }

    /**
     * lineCombCode
     */
    public void setLineCombCode(String lineCombCode) {
        this.lineCombCode = lineCombCode == null ? null : lineCombCode.trim();
    }

    public String getLineCombCode() {
        return this.lineCombCode;
    }

    /**
     * lineState
     */
    public void setLineState(Integer lineState) {
        this.lineState = lineState;
    }

    public Integer getLineState() {
        return this.lineState;
    }

    /**
     * lineUnit
     */
    public void setLineUnit(Integer lineUnit) {
        this.lineUnit = lineUnit;
    }

    public Integer getLineUnit() {
        return this.lineUnit;
    }

    /**
     * lineExpnum
     */
    public void setLineExpnum(BigDecimal lineExpnum) {
        this.lineExpnum = lineExpnum;
    }

    public BigDecimal getLineExpnum() {
        return this.lineExpnum;
    }

    /**
     * lineReqnum
     */
    public void setLineReqnum(BigDecimal lineReqnum) {
        this.lineReqnum = lineReqnum;
    }

    public BigDecimal getLineReqnum() {
        return this.lineReqnum;
    }

    /**
     * lineRate
     */
    public void setLineRate(BigDecimal lineRate) {
        this.lineRate = lineRate;
    }

    public BigDecimal getLineRate() {
        return this.lineRate;
    }

    /**
     * lineBalance
     */
    public void setLineBalance(BigDecimal lineBalance) {
        this.lineBalance = lineBalance;
    }

    public BigDecimal getLineBalance() {
        return this.lineBalance;
    }

    /**
     * lineCreateTime
     */
    public void setLineCreateTime(String lineCreateTime) {
        this.lineCreateTime = lineCreateTime == null ? null : lineCreateTime.trim();
    }

    public String getLineCreateTime() {
        return this.lineCreateTime;
    }

    /**
     * lineUpdateTime
     */
    public void setLineUpdateTime(String lineUpdateTime) {
        this.lineUpdateTime = lineUpdateTime == null ? null : lineUpdateTime.trim();
    }

    public String getLineUpdateTime() {
        return this.lineUpdateTime;
    }

    /**
     * lineReqMonth
     */
    public void setLineReqMonth(String lineReqMonth) {
        this.lineReqMonth = lineReqMonth == null ? null : lineReqMonth.trim();
    }

    public String getLineReqMonth() {
        return this.lineReqMonth;
    }
}