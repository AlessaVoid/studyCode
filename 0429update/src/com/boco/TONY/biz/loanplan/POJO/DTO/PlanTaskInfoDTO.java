package com.boco.TONY.biz.loanplan.POJO.DTO;

import java.io.Serializable;

/**
 * �ƻ�����������ϢDTO
 *
 * @author tony
 * @describe PlanTaskInfoDTO
 * @date 2019-10-11
 */
public class PlanTaskInfoDTO implements Serializable {
    private static final long serialVersionUID = 6865940764739086129L;
    //�ƻ�id
    private String planId;
    //����id
    private String taskId;
    //�ƻ��·�
    private String planMonth;
    //�ƻ�����
    private String planOrgan;
    //�ƻ���������
    private String planOrganName;
    //�ƻ�״̬
    private String planStatus;
    //�ƻ�������
    private String planCreateOper;
    //�ϴ�������
    private String lastAuditOper;
    //�ƻ�����ʱ��
    private String planCreateTime;
    //�ƻ�����ʱ��
    private String planEndTime;
    //���̽ڵ��ʶ
    private int where;
    //����ʵ��
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
