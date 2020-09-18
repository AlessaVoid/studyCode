package com.boco.TONY.biz.loanreq.POJO.DO;

import com.boco.SYS.entity.TbReqDetail;

import java.io.Serializable;

/**
 * 信贷需求机构详情DO
 * @author tony
 * @describe FdOrganReqInfo
 * @date 2019-10-09
 */
public class FdOrganReqInfo implements Serializable {
    private static final long serialVersionUID = 3409791497878035948L;
    private String organCode;
    private String organName;
    private TbReqDetail tbReqDetail;

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public TbReqDetail getTbReqDetail() {
        return tbReqDetail;
    }

    public void setTbReqDetail(TbReqDetail tbReqDetail) {
        this.tbReqDetail = tbReqDetail;
    }

    @Override
    public String toString() {
        return "FdOrganReqInfo{" +
                "organCode='" + organCode + '\'' +
                ", organName='" + organName + '\'' +
                ", tbReqDetail=" + tbReqDetail +
                '}';
    }
}