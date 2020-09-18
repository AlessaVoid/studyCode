package com.boco.TONY.biz.flownode.POJO.DTO;

import java.io.Serializable;

/**
 * ���̽ڵ�ά��DTO
 * @author tony
 * @describe ProcessNodeDTO
 * @date 2019-09-25
 */
public class ProcessFlowNodeDTOV2 implements Serializable {
    private static final long serialVersionUID = 3736900201600480554L;

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
    private String fnCreateTime;

    /**���̽ڵ����ʱ��*/
    private String   fnUpdateTime;

    /**���̽ڵ������*/
    private String fnUpdateOper;

    public String getFnId() {
        return fnId;
    }

    public ProcessFlowNodeDTOV2 setFnId(String fnId) {
        this.fnId = fnId;
        return this;
    }

    public String getFnCode() {
        return fnCode;
    }

    public ProcessFlowNodeDTOV2 setFnCode(String fnCode) {
        this.fnCode = fnCode;
        return this;
    }

    public String getFnName() {
        return fnName;
    }

    public ProcessFlowNodeDTOV2 setFnName(String fnName) {
        this.fnName = fnName;
        return this;
    }

    public String getFnKind() {
        return fnKind;
    }

    public ProcessFlowNodeDTOV2 setFnKind(String fnKind) {
        this.fnKind = fnKind;
        return this;
    }

    public int getFnCount() {
        return fnCount;
    }

    public ProcessFlowNodeDTOV2 setFnCount(int fnCount) {
        this.fnCount = fnCount;
        return this;
    }

    public String getFnCreateOper() {
        return fnCreateOper;
    }

    public ProcessFlowNodeDTOV2 setFnCreateOper(String fnCreateOper) {
        this.fnCreateOper = fnCreateOper;
        return this;
    }

    public String getFnCreateTime() {
        return fnCreateTime;
    }

    public ProcessFlowNodeDTOV2 setFnCreateTime(String fnCreateTime) {
        this.fnCreateTime = fnCreateTime;
        return this;
    }

    public String getFnUpdateTime() {
        return fnUpdateTime;
    }

    public ProcessFlowNodeDTOV2 setFnUpdateTime(String fnUpdateTime) {
        this.fnUpdateTime = fnUpdateTime;
        return this;
    }

    public String getFnUpdateOper() {
        return fnUpdateOper;
    }

    public ProcessFlowNodeDTOV2 setFnUpdateOper(String fnUpdateOper) {
        this.fnUpdateOper = fnUpdateOper;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessNodeDTO{" +
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
