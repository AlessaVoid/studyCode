package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 条线需求记录详情表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbLineReqDetailDTO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /**
     * 未下发
     */
    public static final int STATE_NEW = 0;
    /**
     * 已下发
     */
    public static final int STATE_ISSUED = 1;
    /**
     * 已填写,待提交
     */
    public static final int STATE_FILL = 2;
    /**
     * 已提交，待审批
     */
    public static final int STATE_SUBMITED = 4;
    /**
     * 审批中
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * 审批通过，已上报
     */
    public static final int STATE_APPROVED = 16;
    /**
     * 已驳回
     */
    public static final int STATE_DISMISSED = 32;


    /** 属性 */
    /**
     * 条线需求详情记录id
     */
    @EntityParaAnno(zhName = "条线需求详情记录id")
    private Integer lineReqId;
    /**
     * 批次id
     */
    @EntityParaAnno(zhName = "lineRefId")
    private Integer lineRefId;
    /**
     * 条线下发机构
     */
    @EntityParaAnno(zhName = "lineOrgan")
    private String lineOrgan;
    /**
     * 条线code
     */
    @EntityParaAnno(zhName = "lineCode")
    private String lineCode;
    /**
     * 条线名称
     */
    @EntityParaAnno(zhName = "lineName")
    private String lineName;
    /**
     * 条线所辖 贷种组合code
     */
    @EntityParaAnno(zhName = "lineCombCode")
    private String lineCombCode;
    /**
     * 条线状态
     */
    @EntityParaAnno(zhName = "lineState")
    private Integer lineState;
    /**
     * 条线需求记录单位
     */
    @EntityParaAnno(zhName = "lineUnit")
    private Integer lineUnit;
    /**
     * 到期量
     */
    @EntityParaAnno(zhName = "lineExpnum")
    private BigDecimal lineExpnum;
    /**
     * 需求量
     */
    @EntityParaAnno(zhName = "lineReqnum")
    private BigDecimal lineReqnum;
    /**
     * 利率
     */
    @EntityParaAnno(zhName = "lineRate")
    private BigDecimal lineRate;
    /**
     * 余额
     */
    @EntityParaAnno(zhName = "lineBalance")
    private BigDecimal lineBalance;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "lineCreateTime")
    private String lineCreateTime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "lineUpdateTime")
    private String lineUpdateTime;
    /**
     * 条线需求所属月份
     */
    @EntityParaAnno(zhName = "lineReqMonth")
    private String lineReqMonth;


    private BigDecimal totalAmount;

    /** setter\getter方法 */


    /**
     * 条线需求详情记录id
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }


    /**
     * 条线需求详情记录id
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