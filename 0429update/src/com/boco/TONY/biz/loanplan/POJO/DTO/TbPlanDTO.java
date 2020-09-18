package com.boco.TONY.biz.loanplan.POJO.DTO;

import java.io.Serializable;

/**
 * 信贷计划DTO
 *
 * @author tony
 * @describe TbPlanDTO
 * @date 2019-09-29
 */
public class TbPlanDTO implements Serializable {
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
     * 计划关联需求ID
     */
    private String planRefId;

    /**
     * 计划制定开始时间
     */
    private String planDateStart;

    /**
     * 计划制定结束时间
     */
    private String planDateEnd;

    /**
     * 计划制定人
     */
    private String planOper;

    /**
     * 计划状态
     */
    private int planStatus;
    /**
     * 计划更新人
     */
    private String planUpdater;
    /**
     * 计划更新时间
     */
    private String planUpdateTime;
    /**
     * 单位
     */
    private int planUnit;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanOrgan() {
        return planOrgan;
    }

    public void setPlanOrgan(String planOrgan) {
        this.planOrgan = planOrgan;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getPlanRefId() {
        return planRefId;
    }

    public void setPlanRefId(String planRefId) {
        this.planRefId = planRefId;
    }

    public String getPlanDateStart() {
        return planDateStart;
    }

    public void setPlanDateStart(String planDateStart) {
        this.planDateStart = planDateStart;
    }

    public String getPlanDateEnd() {
        return planDateEnd;
    }

    public void setPlanDateEnd(String planDateEnd) {
        this.planDateEnd = planDateEnd;
    }

    public String getPlanOper() {
        return planOper;
    }

    public void setPlanOper(String planOper) {
        this.planOper = planOper;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanUpdater() {
        return planUpdater;
    }

    public void setPlanUpdater(String planUpdater) {
        this.planUpdater = planUpdater;
    }

    public String getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(String planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public int getPlanUnit() {
        return planUnit;
    }

    public void setPlanUnit(int planUnit) {
        this.planUnit = planUnit;
    }

    @Override
    public String toString() {
        return "TbPlanDTO{" +
                "planId='" + planId + '\'' +
                ", planOrgan='" + planOrgan + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planRefId='" + planRefId + '\'' +
                ", planDateStart='" + planDateStart + '\'' +
                ", planDateEnd='" + planDateEnd + '\'' +
                ", planOper='" + planOper + '\'' +
                ", planStatus=" + planStatus +
                ", planUpdater='" + planUpdater + '\'' +
                ", planUpdateTime=" + planUpdateTime +
                '}';
    }
}
