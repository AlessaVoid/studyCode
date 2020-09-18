package com.boco.PUB.service;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbLineReqresetDetail;
import com.boco.SYS.base.IGenericService;

/**
 * �������������¼�����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbLineReqresetDetailService extends IGenericService<TbLineReqresetDetail, Integer> {


    /**
     * ����������������---
     *
     * @param tbLineReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> showLineReqResetMonth(TbLineReqresetDetail tbLineReqresetDetail);

    List<TbLineReqresetDetail> getAll(TbLineReqresetDetail tbLineReqresetDetail, FdOper fdOper) throws Exception;


    List<TbLineReqresetDetail> selectAll( TbLineReqresetDetail tbLineReqresetDetail , FdOper fdOper);
}