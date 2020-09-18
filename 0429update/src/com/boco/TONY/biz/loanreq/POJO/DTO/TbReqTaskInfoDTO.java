package com.boco.TONY.biz.loanreq.POJO.DTO;

import java.io.Serializable;

/**
 * 信贷计划审批任务DTO
 *
 * @author tony
 * @describe PlanTaskInfoDTO
 * @date 2019-10-11
 */
public class TbReqTaskInfoDTO implements Serializable {
    private static final long serialVersionUID = 6865940764739086129L;
    private int reqId;
    private String taskId;
    private String reqOrgan;
    private String reqOrganName;
    private String reqMonth;
    private String reqStatus;
    private String reqCreateOper;
    private String lastAuditOper;
    private String reqCreateTime;
    private String reqEndTime;
    private int where;
    private String nextAssignee;
    private String nextAssigneeName;
    private String processInstanceId;

    //需求净增总量
    private String reqTotalAmount;
    //需求到期总量
    private String reqTotalExpire;

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

    public String getReqOrganName() {
        return reqOrganName;
    }

    public void setReqOrganName(String reqOrganName) {
        this.reqOrganName = reqOrganName;
    }

    public String getNextAssignee() {
        return nextAssignee;
    }

    public void setNextAssignee(String nextAssignee) {
        this.nextAssignee = nextAssignee;
    }

    public String getNextAssigneeName() {
        return nextAssigneeName;
    }

    public void setNextAssigneeName(String nextAssigneeName) {
        this.nextAssigneeName = nextAssigneeName;
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

    public String getReqTotalAmount() {
        return reqTotalAmount;
    }

    public void setReqTotalAmount(String reqTotalAmount) {
        this.reqTotalAmount = reqTotalAmount;
    }

    public String getReqTotalExpire() {
        return reqTotalExpire;
    }

    public void setReqTotalExpire(String reqTotalExpire) {
        this.reqTotalExpire = reqTotalExpire;
    }

    @Override
    public String toString() {
        return "TbReqTaskInfoDTO{" +
                "reqId=" + reqId +
                ", taskId='" + taskId + '\'' +
                ", reqMonth='" + reqMonth + '\'' +
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
