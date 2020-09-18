package com.boco.TONY.biz.loanreq.POJO.DTO;

import com.boco.TONY.common.POJO.DTO.TbAuditRecordHistDTO;

/**
 * 信贷计划审批历史DTO
 *
 * @author tony
 * @describe TbReqAuditRecordHistDTO
 * @date 2019-11-13
 */
public class TbReqAuditRecordHistDTO extends TbAuditRecordHistDTO {
    private String reqMonth;
    private String reqOrganCode;
    private String reqOrganName;
    private String nextAssignee;
    private String nextAssigneeName;
    private String reqStart;
    private String reqEnd;
    //需求净增总量
    private String reqTotalAmount;
    //需求到期总量
    private String reqTotalExpire;

    public String getReqMonth() {
        return reqMonth;
    }

    public void setReqMonth(String reqMonth) {
        this.reqMonth = reqMonth;
    }

    public String getReqOrganCode() {
        return reqOrganCode;
    }

    public void setReqOrganCode(String reqOrganCode) {
        this.reqOrganCode = reqOrganCode;
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

    public String getReqStart() {
        return reqStart;
    }

    public void setReqStart(String reqStart) {
        this.reqStart = reqStart;
    }

    public String getReqEnd() {
        return reqEnd;
    }

    public void setReqEnd(String reqEnd) {
        this.reqEnd = reqEnd;
    }

    public String getNextAssigneeName() {
        return nextAssigneeName;
    }

    public void setNextAssigneeName(String nextAssigneeName) {
        this.nextAssigneeName = nextAssigneeName;
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
}
