package com.boco.SYS.mapper;

import com.boco.TONY.biz.loanreq.POJO.DO.TbHistoryReqDetailDO;

/**
 * @author tony
 * @describe TbReqDetailHistoryMapper
 * @date 2019-09-29
 */
public interface TbReqDetailHistoryMapper {

    /**�����Ŵ�������ʷ*/
    void insertReqDetailHistory(TbHistoryReqDetailDO tbHistoryReqDetailDO);

    /**��ȡ�Ŵ�������ʷ*/
    void getReqDetailHistory(Integer reqId);
}
