package com.boco.PUB.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ���������¼�����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbLineReqDetailService extends IGenericService<TbLineReqDetail, Integer>{



    /**
     * ����������������---
     *
     * @param tbLineReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> showLineReqMonth(TbLineReqDetail tbLineReqDetail);



}