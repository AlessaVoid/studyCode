package com.boco.TONY.biz.loanreq.POJO.DO;

import com.boco.SYS.entity.TbReqDetail;

/**
 * 信贷需求详情历史记录表
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 tony      批量新建
 * </pre>
 */
public class TbHistoryReqDetailDO implements java.io.Serializable {
    private static final long serialVersionUID = 4701103320165098840L;

    private int version;

    private String processInstanceId;

    private String taskId;

    private String businessId;


    /**
     * 需求明细表
     */
    private TbReqDetail tbReqDetail;

    public TbReqDetail getTbReqDetail() {
        return tbReqDetail;
    }

    public TbHistoryReqDetailDO setTbReqDetail(TbReqDetail tbReqDetail) {
        this.tbReqDetail = tbReqDetail;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public TbHistoryReqDetailDO setVersion(int version) {
        this.version = version;
        return this;
    }
}