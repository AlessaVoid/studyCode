package com.boco.TONY.biz.loanreq.POJO.DO;

import com.boco.SYS.entity.TbReqList;

/**
 * 信贷需求
 * @author tony
 * @describe 信贷需求历史表
 * @date 2019-09-28
 */
public class TbHistoryReqListDO implements java.io.Serializable {
    private static final long serialVersionUID = -455937311673980017L;

    /**信贷历史需求表*/
    private TbReqList tbReqList;

    /**信贷历史记录迭代版本*/
    private int version;

    public TbReqList getTbReqList() {
        return tbReqList;
    }

    public TbHistoryReqListDO setTbReqList(TbReqList tbReqList) {
        this.tbReqList = tbReqList;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public TbHistoryReqListDO setVersion(int version) {
        this.version = version;
        return this;
    }
}