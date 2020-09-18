package com.boco.PUB.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbQuotaApply;
import com.boco.SYS.base.IGenericService;

/**
 * ���޶�������Ϣ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbQuotaApplyService extends IGenericService<TbQuotaApply, Integer> {



    /**
     * ��������������
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectQaId(TbQuotaApply tbQuotaApply);

    /**
     * ����������������.
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectQaMonth(TbQuotaApply tbQuotaApply);


}