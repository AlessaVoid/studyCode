package com.boco.TONY.biz.loanquota.POJO.DTO;

import java.io.Serializable;

/**
 * 信贷计划超限额DO
 *
 * @author tony
 * @describe PlanTaskInfoDTO
 * @date 2019-10-11
 */
public class QuotaTaskInfoDTO implements Serializable {
    private static final long serialVersionUID = 6865940764739086129L;
    private int qaId;
    private String taskId;
    private String qaMonth;
    private String qaOrgan;
    private String qaStatus;
    private String qaCreateOper;
    private String lastAuditOper;
    private String qaCreateTime;
    private String qaEndTime;
    private int where;
    private String processInstanceId;

    public int getQaId() {
        return qaId;
    }

    public QuotaTaskInfoDTO setQaId(int qaId) {
        this.qaId = qaId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public QuotaTaskInfoDTO setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getQaMonth() {
        return qaMonth;
    }

    public QuotaTaskInfoDTO setQaMonth(String qaMonth) {
        this.qaMonth = qaMonth;
        return this;
    }

    public String getQaOrgan() {
        return qaOrgan;
    }

    public QuotaTaskInfoDTO setQaOrgan(String qaOrgan) {
        this.qaOrgan = qaOrgan;
        return this;
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public QuotaTaskInfoDTO setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus;
        return this;
    }

    public String getQaCreateOper() {
        return qaCreateOper;
    }

    public QuotaTaskInfoDTO setQaCreateOper(String qaCreateOper) {
        this.qaCreateOper = qaCreateOper;
        return this;
    }

    public String getLastAuditOper() {
        return lastAuditOper;
    }

    public QuotaTaskInfoDTO setLastAuditOper(String lastAuditOper) {
        this.lastAuditOper = lastAuditOper;
        return this;
    }

    public String getQaCreateTime() {
        return qaCreateTime;
    }

    public QuotaTaskInfoDTO setQaCreateTime(String qaCreateTime) {
        this.qaCreateTime = qaCreateTime;
        return this;
    }

    public String getQaEndTime() {
        return qaEndTime;
    }

    public QuotaTaskInfoDTO setQaEndTime(String qaEndTime) {
        this.qaEndTime = qaEndTime;
        return this;
    }

    public int getWhere() {
        return where;
    }

    public QuotaTaskInfoDTO setWhere(int where) {
        this.where = where;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public QuotaTaskInfoDTO setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    @Override
    public String toString() {
        return "QuotaTaskInfoDTO{" +
                "qaId='" + qaId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", qaMonth='" + qaMonth + '\'' +
                ", qaOrgan='" + qaOrgan + '\'' +
                ", qaStatus='" + qaStatus + '\'' +
                ", qaCreateOper='" + qaCreateOper + '\'' +
                ", lastAuditOper='" + lastAuditOper + '\'' +
                ", qaCreateTime='" + qaCreateTime + '\'' +
                ", qaEndTime='" + qaEndTime + '\'' +
                ", where=" + where +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
