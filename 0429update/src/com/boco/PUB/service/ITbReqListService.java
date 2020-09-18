package com.boco.PUB.service;


import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;

import java.util.List;
import java.util.Map;

/**
 * �·��Ŵ�������Ҫ���ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbReqListService extends IGenericService<TbReqList, Integer> {


    /**
     * �������뱨��Ҫ���·����.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectReqId(TbReqList tbReqList);

    /**
     * �������뱨��Ҫ���·����.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> showReqId(TbReqList tbReqList);

    /**
     * ����������������---����ҳ��.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqMonth(TbReqList tbReqList);
    /**
     * ���������·�����ҳ��.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqOrgan(TbReqList tbReqList);

    /**
     * ����������������---����ҳ��.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> showReqMonth(TbReqList tbReqList);


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
    List<TbReqList> selectByReqTo(Integer reqTo, String organCode,List<TbReqDetail> tbReqDetailList);

    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @param
     * @return
     */
    List<TbReqList> getMonth();


    /**
     * �Ŵ���������֮�� У���������
     *
     * @param reqId
     */
    public void checkChildOrganNum( Integer reqId);


}