package com.boco.TONY.biz.loanplan.POJO.DTO;

import java.io.Serializable;

/**
 * 计划审批任务信息DTO
 *
 * @author tony
 * @describe PlanTaskInfoDTO
 * @date 2019-10-11
 */
public class PlanTaskInfoDTO implements Serializable {
    private static final long serialVersionUID = 6865940764739086129L;
    //计划id
    private String planId;
    //任务id
    private String taskId;
    //计划月份
    private String planMonth;
    //计划机构
    private String planOrgan;
    //计划机构名称
    private String planOrganName;
    //计划状态
    private String planStatus;
    //计划创建人
    private String planCreateOper;
    //上次审批人
    private String lastAuditOper;
    //计划创建时间
    private String planCreateTime;
    //计划结束时间
    private String planEndTime;
    //流程节点标识
    private int where;
    //流程实例
    private String processInstanceId;

    public String getPlanId() {
        return planId;
    }

    public PlanTaskInfoDTO setPlanId(String planId) {
        this.planId = planId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public PlanTaskInfoDTO setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public PlanTaskInfoDTO setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
        return this;
    }

    public String getPlanOrgan() {
        return planOrgan;
    }

    public PlanTaskInfoDTO setPlanOrgan(String planOrgan) {
        this.planOrgan = planOrgan;
        return this;
    }

    public String getPlanOrganName() {
        return planOrganName;
    }

    public PlanTaskInfoDTO setPlanOrganName(String planOrganName) {
        this.planOrganName = planOrganName;
        return this;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public PlanTaskInfoDTO setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
        return this;
    }

    public String getPlanCreateOper() {
        return planCreateOper;
    }

    public PlanTaskInfoDTO setPlanCreateOper(String planCreateOper) {
        this.planCreateOper = planCreateOper;
        return this;
    }

    public String getLastAuditOper() {
        return lastAuditOper;
    }

    public PlanTaskInfoDTO setLastAuditOper(String lastAuditOper) {
        this.lastAuditOper = lastAuditOper;
        return this;
    }

    public String getPlanCreateTime() {
        return planCreateTime;
    }

    public PlanTaskInfoDTO setPlanCreateTime(String planCreateTime) {
        this.planCreateTime = planCreateTime;
        return this;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public PlanTaskInfoDTO setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
        return this;
    }

    public int getWhere() {
        return where;
    }

    public PlanTaskInfoDTO setWhere(int where) {
        this.where = where;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public PlanTaskInfoDTO setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    @Override
    public String toString() {
        return "PlanTaskInfoDTO{" +
                "planId='" + planId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planOrgan='" + planOrgan + '\'' +
                ", planStatus='" + planStatus + '\'' +
                ", planCreateOper='" + planCreateOper + '\'' +
                ", lastAuditOper='" + lastAuditOper + '\'' +
                ", planCreateTime='" + planCreateTime + '\'' +
                ", planEndTime='" + planEndTime + '\'' +
                ", where=" + where +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
