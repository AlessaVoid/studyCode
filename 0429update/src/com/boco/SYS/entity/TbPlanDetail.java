package com.boco.SYS.entity;


import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 计划制定明细表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbPlanDetail extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * pdId
     */
    @EntityParaAnno(zhName = "pdId")
    private java.lang.String pdId;
    /**
     * 批次号
     */
    @EntityParaAnno(zhName = "批次号")
    private java.lang.String pdRefId;
    /**
     * 机构号
     */
    @EntityParaAnno(zhName = "机构号")
    private java.lang.String pdOrgan;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private java.lang.String pdMonth;
    /**
     * 贷种组合类型
     */
    @EntityParaAnno(zhName = "pdLoanType")
    private java.lang.String pdLoanType;
    /**
     * 金额
     */
    @EntityParaAnno(zhName = "金额")
    private BigDecimal pdAmount;
    /**
     * 单位
     */
    @EntityParaAnno(zhName = "单位")
    private java.lang.Integer pdUnit;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private java.lang.String pdCreateTime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private java.lang.String pdUpdateTime;

    private String __status;

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }
    /** setter\getter方法 */
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
     * 批次号
     */
    public void setPdRefId(java.lang.String pdRefId) {
        this.pdRefId = pdRefId == null ? null : pdRefId.trim();
    }

    public java.lang.String getPdRefId() {
        return this.pdRefId;
    }

    /**
     * 机构号
     */
    public void setPdOrgan(java.lang.String pdOrgan) {
        this.pdOrgan = pdOrgan == null ? null : pdOrgan.trim();
    }

    public java.lang.String getPdOrgan() {
        return this.pdOrgan;
    }

    /**
     * 所属月份
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

    /*金额*/
    public BigDecimal getPdAmount() {
        return pdAmount;
    }

    public void setPdAmount(BigDecimal pdAmount) {
        this.pdAmount = pdAmount;
    }

    /**
     * 单位
     */
    public void setPdUnit(java.lang.Integer pdUnit) {
        this.pdUnit = pdUnit;
    }

    public java.lang.Integer getPdUnit() {
        return this.pdUnit;
    }

    /**
     * 创建时间
     */
    public void setPdCreateTime(java.lang.String pdCreateTime) {
        this.pdCreateTime = pdCreateTime == null ? null : pdCreateTime.trim();
    }

    public java.lang.String getPdCreateTime() {
        return this.pdCreateTime;
    }

    /**
     * 更新时间
     */
    public void setPdUpdateTime(java.lang.String pdUpdateTime) {
        this.pdUpdateTime = pdUpdateTime == null ? null : pdUpdateTime.trim();
    }

    public java.lang.String getPdUpdateTime() {
        return this.pdUpdateTime;
    }
}