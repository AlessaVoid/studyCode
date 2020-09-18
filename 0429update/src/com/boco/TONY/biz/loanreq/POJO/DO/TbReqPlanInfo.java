package com.boco.TONY.biz.loanreq.POJO.DO;

/**
 * @author tony
 * @describe TbReqSpInfo
 * @date 2019-10-07
 */
public class TbReqPlanInfo {
    private String reqId;
    private String reqMonth;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqMonth() {
        return reqMonth;
    }

    public void setReqMonth(String reqMonth) {
        this.reqMonth = reqMonth;
    }

    @Override
    public String toString() {
        return "TbReqSpInfo{" +
                "reqId='" + reqId + '\'' +
                ", reqMonth='" + reqMonth + '\'' +
                '}';
    }
}
