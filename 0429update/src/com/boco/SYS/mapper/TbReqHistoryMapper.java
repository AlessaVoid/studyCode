package com.boco.SYS.mapper;

import com.boco.TONY.biz.loanreq.POJO.DO.TbHistoryReqListDO;

/**
 * @author tony
 * @describe TbReqDetailHistoryMapper
 * @date 2019-09-29
 */
public interface TbReqHistoryMapper {

    /**�����Ŵ�����*/
    void insertReqHistory(TbHistoryReqListDO tbHistoryReqListDO);

    /**ѡ���Ŵ�����*/
    void selectReqHistory(int reqId);
}
