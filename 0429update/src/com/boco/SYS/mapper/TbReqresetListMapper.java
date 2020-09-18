package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.base.GenericMapper;

/**
 * TbReqresetList���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbReqresetListMapper extends GenericMapper<TbReqresetList, Integer> {


    /**
     * ���������ѯ-id
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList);

    /**
     * ���������ѯ-id
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList);

    /**
     * ���������ѯ-�����·�
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList);

    /**
     * ���������ѯ-�����·�
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList);

    /**
     * ���������ѯ-�·�����
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList);


    /**
     * �������� �·�״̬
     *
     * @param tbReqresetList
     * @return
     */
    int updateReqState(TbReqresetList tbReqresetList);

    List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList);
}