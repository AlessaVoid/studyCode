package com.boco.PUB.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.base.IGenericService;

/**
 * TbReqresetListҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbReqresetListService extends IGenericService<TbReqresetList, Integer> {

    /**
     * �������뱨��Ҫ���·����.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList);

    /**
     * �������뱨��Ҫ���·����.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList);

    /**
     * ����������������---����ҳ��.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList);

    /**
     * ����������������---����ҳ��.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList);
    /**
     * ���������·�����ҳ��.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList);

    /**
     * �����·�״̬
     *
     * @param id
     * @param state
     * @return
     */
    int updateReqState(Integer id, int state);


    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @param reqTo,organCode
     * @return
     */
    List<TbReqresetList> selectByReqTo(Integer reqTo, String organCode);

    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @param tbReqresetList
     * @return
     */
    List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList);



    /**
     *
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     * return : [organCode_combCode:reserNum]
     */
    public Map<String, BigDecimal> getLineReqResetDetailList(String month, String organCode);


}