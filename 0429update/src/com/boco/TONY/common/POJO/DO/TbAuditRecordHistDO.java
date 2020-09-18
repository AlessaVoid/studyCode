package com.boco.TONY.common.POJO.DO;

import java.time.LocalDateTime;

/**
 * ������¼
 *
 * @author tony
 * @describe TbAuditRecordHist
 * @date 2019-10-25
 */
public class TbAuditRecordHistDO {
    //id
    private Long id;
    //ҵ��id
    private String businessId;
    //������ܻ�仯,����Ҫ������processInstanceId���ܱ仯
    private String taskId;
    //���̱�ʶ
    private String processKey;
    //����ʵ��id
    private String processInstanceId;
    //:audit  :reject
    private String assignee;
    //��ǰ�������������ʱ��
    private LocalDateTime startAt;
    //������������ʱ��
    private LocalDateTime endAt;
    //����������
    private String assigneeName;
    //��ǰ������Ա
    private String currentAssignee;
    //��ǰ������Ա������
    private String currentAssigneeName;
    //�Ŵ�����req
    private String month;
    //״̬
    private int status;

    public Long getId() {
        return id;
    }

    public TbAuditRecordHistDO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBusinessId() {
        return businessId;
    }

    public TbAuditRecordHistDO setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TbAuditRecordHistDO setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getProcessKey() {
        return processKey;
    }

    public TbAuditRecordHistDO setProcessKey(String processKey) {
        this.processKey = processKey;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public TbAuditRecordHistDO setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public TbAuditRecordHistDO setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public TbAuditRecordHistDO setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
        return this;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public TbAuditRecordHistDO setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
        return this;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public TbAuditRecordHistDO setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TbAuditRecordHistDO setStatus(int status) {
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
