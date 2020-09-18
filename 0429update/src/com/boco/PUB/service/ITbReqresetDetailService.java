package com.boco.PUB.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.util.BigDecimalUtil;

/**
 * 
 * 
 * �������뱨��Ҫ��¼����ϸ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbReqresetDetailService extends IGenericService<TbReqresetDetail, Integer>{

    /**
     * ��������ɾ��
     * @param tbReqresetDetail
     * @return
     */

    int deleteByAttr(TbReqresetDetail tbReqresetDetail);

    /**
     * ��������������.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail);

    /**
     * ����������������.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail);


    /**
     *
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     * return : [organCode_combCode:reserNum]
     */
    public Map<String, BigDecimal> getReqResetDetailList(String month, String organCode);

}