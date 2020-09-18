package com.boco.PUB.service;


import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;

import java.util.List;
import java.util.Map;

/**
 * �Ŵ�����¼����ϸ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbReqDetailService extends IGenericService<TbReqDetail, Integer> {


    /**
     * ��������������.
     *
     * @param tbReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail);

    /**
     * ����������������.
     *
     * @param tbReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail);


    /**
     * ��������ɾ��
     * @param tbReqDetail
     * @return
     */

    int deleteByAttr(TbReqDetail tbReqDetail);


    /**
     * ����������������.
     *
     * @param tbReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
     List<TbReqList> selectTbreqList(TbReqDetail tbReqDetail,TbReqList tbReqList);



}