package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 信贷需求录入详细表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqDetail extends BaseEntity implements java.io.Serializable {
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

    /**
     * reqdId
     */
    @EntityParaAnno(zhName = "reqdId")
    private java.lang.Integer reqdId;
    /**
     * 所属批次id
     */
    @EntityParaAnno(zhName = "所属批次id")
    private java.lang.Integer reqdRefId;
    /**
     * 填报机构id
     */
    @EntityParaAnno(zhName = "填报机构id")
    private java.lang.String reqdOrgan;
    /**
     * 贷种组合编码
     */
    @EntityParaAnno(zhName = "贷种组合编码")
    private java.lang.String reqdCombCode;
    /**
     * 单位
     * 1：万元
     * 2：亿元
     */
    @EntityParaAnno(zhName = "单位")
    private java.lang.Integer reqdUnit;
    /**
     * 条目状态
     */
    @EntityParaAnno(zhName = "条目状态")
    private java.lang.Integer reqdState;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private java.lang.String reqdCreateTime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private java.lang.String reqdUpdateTime;
    /**
     * 到期量
     */
    @EntityParaAnno(zhName = "到期量")
    private BigDecimal reqdExpnum;
    /**
     * 净增量
     */
    @EntityParaAnno(zhName = "净增量")
    private BigDecimal reqdReqnum;
    /**
     * 利率
     */
    @EntityParaAnno(zhName = "利率")
    private BigDecimal reqdRate;
    /**
     * 余额
     */
    @EntityParaAnno(zhName = "余额")
    private BigDecimal reqdBalance;


    /**
     * 到期量
     */
    @EntityParaAnno(zhName = "到期量合计")
    private BigDecimal totalReqdExpnum;
    /**
     * 净增量
     */
    @EntityParaAnno(zhName = "净增量合计")
    private BigDecimal totalReqdReqnum;
    /**
     * 余额
     */
    @EntityParaAnno(zhName = "余额合计")
    private BigDecimal totalReqdBalance;


    /** setter\getter方法 */
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
     * 所属批次id
     */
    public void setReqdRefId(java.lang.Integer reqdRefId) {
        this.reqdRefId = reqdRefId;
    }

    public java.lang.Integer getReqdRefId() {
        return this.reqdRefId;
    }

    /**
     * 填报机构id
     */
    public void setReqdOrgan(java.lang.String reqdOrgan) {
        this.reqdOrgan = reqdOrgan == null ? null : reqdOrgan.trim();
    }

    public java.lang.String getReqdOrgan() {
        return this.reqdOrgan;
    }

    /**
     * 贷种组合编码
     */
    public void setReqdCombCode(java.lang.String reqdCombCode) {
        this.reqdCombCode = reqdCombCode == null ? null : reqdCombCode.trim();
    }

    public java.lang.String getReqdCombCode() {
        return this.reqdCombCode;
    }

    /**
     * 单位
     */
    public void setReqdUnit(java.lang.Integer reqdUnit) {
        this.reqdUnit = reqdUnit;
    }

    public java.lang.Integer getReqdUnit() {
        return this.reqdUnit;
    }

    /**
     * 条目状态
     */
    public void setReqdState(java.lang.Integer reqdState) {
        this.reqdState = reqdState;
    }

    public java.lang.Integer getReqdState() {
        return this.reqdState;
    }

    /**
     * 创建时间
     */
    public void setReqdCreateTime(java.lang.String reqdCreateTime) {
        this.reqdCreateTime = reqdCreateTime == null ? null : reqdCreateTime.trim();
    }

    public java.lang.String getReqdCreateTime() {
        return this.reqdCreateTime;
    }

    /**
     * 更新时间
     */
    public void setReqdUpdateTime(java.lang.String reqdUpdateTime) {
        this.reqdUpdateTime = reqdUpdateTime == null ? null : reqdUpdateTime.trim();
    }

    public java.lang.String getReqdUpdateTime() {
        return this.reqdUpdateTime;
    }

    /**
     * 到期量
     */
    public void setReqdExpnum(BigDecimal reqdExpnum) {
        this.reqdExpnum = reqdExpnum;
    }

    public BigDecimal getReqdExpnum() {
        return this.reqdExpnum;
    }

    /**
     * 净增量
     */
    public void setReqdReqnum(BigDecimal reqdReqnum) {
        this.reqdReqnum = reqdReqnum;
    }

    public BigDecimal getReqdReqnum() {
        return this.reqdReqnum;
    }

    /**
     * 利率
     */
    public void setReqdRate(BigDecimal reqdRate) {
        this.reqdRate = reqdRate;
    }

    public BigDecimal getReqdRate() {
        return this.reqdRate;
    }

    /**
     * 余额
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