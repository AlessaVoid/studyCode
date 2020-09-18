package com.boco.TONY.common.POJO.DTO;

/**
 * ������¼
 *
 * @author tony
 * @describe TbAuditRecordHist
 * @date 2019-10-25
 */
public class TbAuditRecordHistDTO {
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
    private String startAt;
    //������������ʱ��
    private String endAt;
    //����������
    private String assigneeName;
    //��ǰ������Ա
    private String currentAssignee;
    //��ǰ������Ա������
    private String currentAssigneeName;
    //״̬
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
