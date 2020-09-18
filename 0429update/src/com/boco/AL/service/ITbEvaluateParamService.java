package com.boco.AL.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbEvaluateParam;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * ���ֹ��������ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbEvaluateParamService extends IGenericService<TbEvaluateParam, Integer> {


    /**
     * �������۹���
     *
     * @return
     */
    Json deploy();


    /**
     * ��������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam);

    /**
     * ������������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam);


}