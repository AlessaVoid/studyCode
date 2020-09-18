package com.boco.TONY.common.POJO.DTO;

/**
 * 审批记录
 *
 * @author tony
 * @describe TbAuditRecordHist
 * @date 2019-10-25
 */
public class TbAuditRecordHistDTO {
    //id
    private Long id;
    //业务id
    private String businessId;
    //这个可能会变化,但主要是流程processInstanceId不能变化
    private String taskId;
    //流程标识
    private String processKey;
    //流程实例id
    private String processInstanceId;
    //:audit  :reject
    private String assignee;
    //当前审批人审批完成时间
    private String startAt;
    //流程审批结束时间
    private String endAt;
    //审批人姓名
    private String assigneeName;
    //当前审批人员
    private String currentAssignee;
    //当前审批人员的名称
    private String currentAssigneeName;
    //状态
    private String status;


    public Long getId() {
        return id;
    }

    public TbAuditRecordHistDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBusinessId() {
        return businessId;
    }

    public TbAuditRecordHistDTO setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TbAuditRecordHistDTO setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getProcessKey() {
        return processKey;
    }

    public TbAuditRecordHistDTO setProcessKey(String processKey) {
        this.processKey = processKey;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public TbAuditRecordHistDTO setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public TbAuditRecordHistDTO setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getStartAt() {
        return startAt;
    }

    public TbAuditRecordHistDTO setStartAt(String startAt) {
        this.startAt = startAt;
        return this;
    }

    public String getEndAt() {
        return endAt;
    }

    public TbAuditRecordHistDTO setEndAt(String endAt) {
        this.endAt = endAt;
        return this;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public TbAuditRecordHistDTO setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TbAuditRecordHistDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCurrentAssignee() {
        return currentAssignee;
    }

    public void setCurrentAssignee(String currentAssignee) {
        this.currentAssignee = currentAssignee;
    }

    public String getCurrentAssigneeName() {
        return currentAssigneeName;
    }

    public void setCurrentAssigneeName(String currentAssigneeName) {
        this.currentAssigneeName = currentAssigneeName;
    }
    @Override
    public String toString() {
        return "TbAuditRecordHistDO{" +
                "id=" + id +
                ", businessId='" + businessId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", processKey='" + processKey + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", assignee='" + assignee + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", assigneeName='" + assigneeName + '\'' +
                ", status=" + status +
                '}';
    }
}
