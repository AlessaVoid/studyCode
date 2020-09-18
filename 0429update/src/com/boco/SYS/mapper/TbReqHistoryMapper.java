package com.boco.SYS.mapper;

import com.boco.TONY.biz.loanreq.POJO.DO.TbHistoryReqListDO;

/**
 * @author tony
 * @describe TbReqDetailHistoryMapper
 * @date 2019-09-29
 */
public interface TbReqHistoryMapper {

    /**插入信贷需求*/
    void insertReqHistory(TbHistoryReqListDO tbHistoryReqListDO);

    /**选择信贷需求*/
    void selectReqHistory(int reqId);
}
