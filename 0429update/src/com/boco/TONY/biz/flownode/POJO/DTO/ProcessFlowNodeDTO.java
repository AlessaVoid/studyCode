package com.boco.TONY.biz.flownode.POJO.DTO;

import java.io.Serializable;

/**
 * 流程节点维护DTO
 * @author tony
 * @describe ProcessNodeDTO
 * @date 2019-09-25
 */
public class ProcessFlowNodeDTO implements Serializable {
    private static final long serialVersionUID = 3736900201600480554L;

    /**流程节点*/
    private String fnId;

    /**流程节点编码*/
    private String fnCode;

    /**流程名称*/
    private String fnName;

    /**审批类种 需求录入*/
    private String fnKind;

    /**审批节点数量*/
    private int fnCount;

    /**流程节点创建人*/
    private String fnCreateOper;

    /**流程节点创建时间*/
    private String fnCreateTime;

    /**流程节点更新时间*/
    private String  fnUpdateTime;

    /**流程节点更新人*/
    private String fnUpdateOper;

    public String getFnId() {
        return fnId;
    }

    public ProcessFlowNodeDTO setFnId(String fnId) {
        this.fnId = fnId;
        return this;
    }

    public String getFnCode() {
        return fnCode;
    }

    public ProcessFlowNodeDTO setFnCode(String fnCode) {
        this.fnCode = fnCode;
        return this;
    }

    public String getFnName() {
        return fnName;
    }

    public ProcessFlowNodeDTO setFnName(String fnName) {
        this.fnName = fnName;
        return this;
    }

    public String getFnKind() {
        return fnKind;
    }

    public ProcessFlowNodeDTO setFnKind(String fnKind) {
        this.fnKind = fnKind;
        return this;
    }

    public int getFnCount() {
        return fnCount;
    }

    public ProcessFlowNodeDTO setFnCount(int fnCount) {
        this.fnCount = fnCount;
        return this;
    }

    public String getFnCreateOper() {
        return fnCreateOper;
    }

    public ProcessFlowNodeDTO setFnCreateOper(String fnCreateOper) {
        this.fnCreateOper = fnCreateOper;
        return this;
    }

    public String getFnCreateTime() {
        return fnCreateTime;
    }

    public ProcessFlowNodeDTO setFnCreateTime(String fnCreateTime) {
        this.fnCreateTime = fnCreateTime;
        return this;
    }

    public String getFnUpdateTime() {
        return fnUpdateTime;
    }

    public ProcessFlowNodeDTO setFnUpdateTime(String fnUpdateTime) {
        this.fnUpdateTime = fnUpdateTime;
        return this;
    }

    public String getFnUpdateOper() {
        return fnUpdateOper;
    }

    public ProcessFlowNodeDTO setFnUpdateOper(String fnUpdateOper) {
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
