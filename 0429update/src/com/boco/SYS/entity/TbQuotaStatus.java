package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 额度生成情况表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbQuotaStatus extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //已生成
    public static final String status_end = "0";
    //预生成
    public static final String status_new = "1";
    //未生成
    public static final String status_start = "2";


    /** 属性 */
    /**
     * 月份
     */
    @EntityParaAnno(zhName = "月份")
    private java.lang.String planMonth;
    /**
     * 额度生成状态0已生成1计划已制定未生成额度2计划未制定
     */
    @EntityParaAnno(zhName = "额度生成状态0已生成1计划已制定未生成额度2计划未制定")
    private java.lang.String quotaStatus;
    /**
     * 执行情况生成状态0已生成1预生成2未生成
     */
    @EntityParaAnno(zhName = "执行情况生成状态0已生成1预生成2未生成")
    private java.lang.String executeStatus;

    /** setter\getter方法 */
    /**
     * 月份
     */
    public void setPlanMonth(java.lang.String planMonth) {
        this.planMonth = planMonth == null ? null : planMonth.trim();
    }

    public java.lang.String getPlanMonth() {
        return this.planMonth;
    }

    /**
     * 额度生成状态0已生成1计划已制定未生成额度2计划未制定
     */
    public void setQuotaStatus(java.lang.String quotaStatus) {
        this.quotaStatus = quotaStatus == null ? null : quotaStatus.trim();
    }

    public java.lang.String getQuotaStatus() {
        return this.quotaStatus;
    }

    /**
     * 执行情况生成状态0已生成1预生成2未生成
     */
    public void setExecuteStatus(java.lang.String executeStatus) {
        this.executeStatus = executeStatus == null ? null : executeStatus.trim();
    }

    public java.lang.String getExecuteStatus() {
        return this.executeStatus;
    }

}