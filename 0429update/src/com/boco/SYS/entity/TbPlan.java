package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 信贷计划DO
 *
 * @author tony
 * @describe TbPlanDO
 * @date 2019-09-29
 */
public class TbPlan extends BaseEntity implements Serializable {


    /**
     * 计划
     */
    public static final int PLAN = 1;
    /**
     * 条线
     */
    public static final int STRIPE = 2;
    /**
     * 一级贷种
     */
    public static final int COMB_ONE = 1;
    /**
     * 二级贷种
     */
    public static final int COMB_TWO = 2;

    private static final long serialVersionUID = 530070982970664212L;

    /**
     * 信贷计划ID
     */
    private String planId;

    /**
     * 信贷计划机构
     */
    private String planOrgan;

    /**
     * 信贷计划所属月份
     */
    private String planMonth;

    /**
     * 计划制定人
     */
    private String planOper;

    /**
     * 计划状态
     */
    private Integer planStatus;

    /**
     * 计划更新人
     */
    private String planUpdater;
    /**
     * 单位
     */
    private Integer planUnit;
        /**
     * 计划更新时间
     */
    private String planUpdateTime;
    /**
     * 计划创建时间
     */
    private String planCreateTime;
    /**
     * 本月计划净增量
     */
    private BigDecimal planIncrement;
    /** 本月制定净增量 */
    @EntityParaAnno(zhName="本月制定净增量")
    private BigDecimal planRealIncrement;

    /** 计划类型 1-计划  2-条线 */
    @EntityParaAnno(zhName="计划类型 1-计划  2-条线")
    private Integer planType;

    /** 额度生成 状态0已制定 */
    @EntityParaAnno(zhName="额度生成 状态0已制定")
    private Integer quotaStatus;
    /** 机构计划贷种级别 */
    @EntityParaAnno(zhName="机构计划贷种级别")
    private Integer combLevel;

    private String __status;

    public BigDecimal getPlanIncrement() {
        return planIncrement;
    }

    public void setPlanIncrement(BigDecimal planIncrement) {
        this.planIncrement = planIncrement;
    }

    public String getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(String planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public String getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanCreateTime(String planCreateTime) {
        this.planCreateTime = planCreateTime;
    }



    public String getPlanId() {
        return planId;
    }

    public TbPlan setPlanId(String planId) {
        this.planId = planId;
        return this;
    }

    public String getPlanOrgan() {
        return planOrgan;
    }

    public TbPlan setPlanOrgan(String planOrgan) {
        this.planOrgan = planOrgan;
        return this;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public TbPlan setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
        return this;
    }

    public String getPlanOper() {
        return planOper;
    }

    public TbPlan setPlanOper(String planOper) {
        this.planOper = planOper;
        return this;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanUpdater() {
        return planUpdater;
    }

    public TbPlan setPlanUpdater(String planUpdater) {
        this.planUpdater = planUpdater;
        return this;
    }

    public Integer getPlanUnit() {
        return planUnit;
    }

    public void setPlanUnit(Integer planUnit) {
        this.planUnit = planUnit;
    }

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }

    public BigDecimal getPlanRealIncrement() {
        return planRealIncrement;
    }

    public void setPlanRealIncrement(BigDecimal planRealIncrement) {
        this.planRealIncrement = planRealIncrement;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getQuotaStatus() {
        return quotaStatus;
    }

    public void setQuotaStatus(Integer quotaStatus) {
        this.quotaStatus = quotaStatus;
    }

    public Integer getCombLevel() {
        return combLevel;
    }

    public void setCombLevel(Integer combLevel) {
        this.combLevel = combLevel;
    }

    @Override
    public String toString() {
        return "TbPlanDO{" +
                "planId='" + planId + '\'' +
                ", planOrgan='" + planOrgan + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planOper='" + planOper + '\'' +
                ", planStatus=" + planStatus +
                ", planUpdater='" + planUpdater + '\'' +
                ", planUnit=" + planUnit +
                ", planUpdateTime='" + planUpdateTime + '\'' +
                ", planCreateTime='" + planCreateTime + '\'' +
                ", planIncrement='" + planIncrement + '\'' +
                ", __status='" + __status + '\'' +
                '}';
    }

}
