package com.boco.SYS.mapper;


import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.base.GenericMapper;

import java.util.List;
import java.util.Map;

/**
 * ʱ��ƻ�ά�������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbTradeParamMapper extends GenericMapper<TbTradeParam, Integer> {


    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam);

    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam);

    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam);

}