package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbReqList;

/**
 * �·��Ŵ�������Ҫ������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbReqListMapper extends GenericMapper<TbReqList, Integer> {


    /**
     * ���������ѯ-id
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, Integer>> selectReqId(TbReqList tbReqList);
    /**
     * ���������ѯ-id
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, Integer>> showReqId(TbReqList tbReqList);

    /**
     * ���������ѯ-�����·�
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> selectReqMonth(TbReqList tbReqList);
    /**
     * ���������ѯ-�����·�
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> selectReqOrgan(TbReqList tbReqList);
    /**
     * ���������ѯ-�����·�
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> showReqMonth(TbReqList tbReqList);


    /**
     * �������� �·�״̬
     *
     * @param tbReqLis
     * @return
     */
    int updateReqState(TbReqList tbReqLis);

    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @param
     * @return
     */
    List<TbReqList> getMonth();


}