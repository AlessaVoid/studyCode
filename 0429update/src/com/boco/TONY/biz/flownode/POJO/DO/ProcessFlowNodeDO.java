package com.boco.TONY.biz.flownode.POJO.DO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ���̽ڵ�ά��DO
 * @author tony
 * @describe ProcessFlowNodeDO
 * @date 2019-09-25
 */
public class ProcessFlowNodeDO implements Serializable {
    private static final long serialVersionUID = 6717982394691740007L;
    /**���̽ڵ�*/
    private String fnId;

    /**���̽ڵ����*/
    private String fnCode;

    /**��������*/
    private String fnName;

    /**�������� ����¼��*/
    private String fnKind;

    /**�����ڵ�����*/
    private int fnCount;

    /**���̽ڵ㴴����*/
    private String fnCreateOper;

    /**���̽ڵ㴴��ʱ��*/
    private LocalDateTime fnCreateTime;

    /**���̽ڵ����ʱ��*/
    private LocalDateTime  fnUpdateTime;

    /**���̽ڵ������*/
    private String fnUpdateOper;

    public String getFnId() {
        return fnId;
    }

    public ProcessFlowNodeDO setFnId(String fnId) {
        this.fnId = fnId;
        return this;
    }

    public String getFnCode() {
        return fnCode;
    }

    public ProcessFlowNodeDO setFnCode(String fnCode) {
        this.fnCode = fnCode;
        return this;
    }

    public String getFnName() {
        return fnName;
    }

    public ProcessFlowNodeDO setFnName(String fnName) {
        this.fnName = fnName;
        return this;
    }

    public String getFnKind() {
        return fnKind;
    }

    public ProcessFlowNodeDO setFnKind(String fnKind) {
        this.fnKind = fnKind;
        return this;
    }

    public int getFnCount() {
        return fnCount;
    }

    public ProcessFlowNodeDO setFnCount(int fnCount) {
        this.fnCount = fnCount;
        return this;
    }

    public String getFnCreateOper() {
        return fnCreateOper;
    }

    public ProcessFlowNodeDO setFnCreateOper(String fnCreateOper) {
        this.fnCreateOper = fnCreateOper;
        return this;
    }

    public LocalDateTime getFnCreateTime() {
        return fnCreateTime;
    }

    public ProcessFlowNodeDO setFnCreateTime(LocalDateTime fnCreateTime) {
        this.fnCreateTime = fnCreateTime;
        return this;
    }

    public LocalDateTime getFnUpdateTime() {
        return fnUpdateTime;
    }

    public ProcessFlowNodeDO setFnUpdateTime(LocalDateTime fnUpdateTime) {
        this.fnUpdateTime = fnUpdateTime;
        return this;
    }

    public String getFnUpdateOper() {
        return fnUpdateOper;
    }

    public ProcessFlowNodeDO setFnUpdateOper(String fnUpdateOper) {
        this.fnUpdateOper = fnUpdateOper;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessFlowNodeDO{" +
                "fnId='" + fnId + '\'' +
                ", fnCode='" + fnCode + '\'' +
                ", fnName='" + fnName + '\'' +
                ", fnKind='" + fnKind + '\'' +
                ", fnCount=" + fnCount +
                ", fnCreateOper='" + fnCreateOper + '\'' +
                ", fnCreateTime=" + fnCreateTime +
                ", fnUpdateTime=" + fnUpdateTime +
                ", fnUpdateOper='" + fnUpdateOper + '\'' +
                '}';
    }
}
