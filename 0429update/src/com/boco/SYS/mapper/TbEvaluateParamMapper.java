package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbEvaluateParam;

import java.util.List;
import java.util.Map;

/**
 * ���ֹ�����������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbEvaluateParamMapper extends GenericMapper<TbEvaluateParam, Integer> {
    /**
     * ��������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam);

    /**
     * ������������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam);


}