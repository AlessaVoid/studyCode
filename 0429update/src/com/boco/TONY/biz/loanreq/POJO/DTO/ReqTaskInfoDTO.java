package com.boco.TONY.biz.loanreq.POJO.DTO;

import java.io.Serializable;

/**
 * 信贷需求审批任务DTO
 *
 * @author tony
 * @describe ReqTaskInfoDTO
 * @date 2019-10-11
 */
public class ReqTaskInfoDTO implements Serializable {
    private static final long serialVersionUID = 6865940764739086129L;
    /**
     * 信贷需求Id
     */
    private int reqId;
    /**
     * 信贷需求审批任务标识
     */
    private String taskId;
    /**
     * 信贷需求月标识
     */
    private String reqMonth;
    /**
     * 信贷需求机构
     */
    private String reqOrgan;
    /**
     * 信贷需求状态
     */
    private String reqStatus;
    /**
     * 信贷需求创建柜员
     */
    private String reqCreateOper;
    /**
     * 上一级审批人
     */
    private String lastAuditOper;
    /**
     * 信贷需求创建时间
     */
    private String reqCreateTime;
    /**
     * 信贷需求结束时间
     */
    private String reqEndTime;
    /**
     * 审批节点位置
     */
    private int where;
    private String processInstanceId;

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getReqMonth() {
        return reqMonth;
    }

    public void setReqMonth(String reqMonth) {
        this.reqMonth = reqMonth;
    }

    public String getReqOrgan() {
        return reqOrgan;
    }

    public void setReqOrgan(String reqOrgan) {
        this.reqOrgan = reqOrgan;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqCreateOper() {
        return reqCreateOper;
    }

    public void setReqCreateOper(String reqCreateOper) {
        this.reqCreateOper = reqCreateOper;
    }

    public String getLastAuditOper() {
        return lastAuditOper;
    }

    public void setLastAuditOper(String lastAuditOper) {
        this.lastAuditOper = lastAuditOper;
    }

    public String getReqCreateTime() {
        return reqCreateTime;
    }

    public void setReqCreateTime(String reqCreateTime) {
        this.reqCreateTime = reqCreateTime;
    }

    public String getReqEndTime() {
        return reqEndTime;
    }

    public void setReqEndTime(String reqEndTime) {
        this.reqEndTime = reqEndTime;
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "ReqTaskInfoDTO{" +
                "reqId='" + reqId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", reqMonth='" + reqMonth + '\'' +
                ", reqOrgan='" + reqOrgan + '\'' +
                ", reqStatus='" + reqStatus + '\'' +
                ", reqCreateOper='" + reqCreateOper + '\'' +
                ", lastAuditOper='" + lastAuditOper + '\'' +
                ", reqCreateTime='" + reqCreateTime + '\'' +
                ", reqEndTime='" + reqEndTime + '\'' +
                ", where=" + where +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
