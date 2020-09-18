package com.boco.SYS.mapper;

import com.boco.TONY.biz.loanreq.POJO.DO.TbHistoryReqDetailDO;

/**
 * @author tony
 * @describe TbReqDetailHistoryMapper
 * @date 2019-09-29
 */
public interface TbReqDetailHistoryMapper {

    /**插入信贷详情历史*/
    void insertReqDetailHistory(TbHistoryReqDetailDO tbHistoryReqDetailDO);

    /**获取信贷需求历史*/
    void getReqDetailHistory(Integer reqId);
}
