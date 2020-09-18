package com.boco.AL.service.impl;

import com.boco.AL.service.ITbEvaluateParamService;
import com.boco.SYS.entity.TbEvaluateParam;
import com.boco.SYS.mapper.TbEvaluateParamMapper;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;

import java.util.List;
import java.util.Map;

/**
 * ���ֹ��������ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbEvaluateParamService extends GenericService<TbEvaluateParam, Integer> implements ITbEvaluateParamService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbEvaluateParamMapper tbEvaluateParamMapper;

    /**
     * �������۹���
     *
     * @return
     */
    @Override
    public Json deploy() {
        return null;
    }

    /**
     * ��������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam) {
        return tbEvaluateParamMapper.selectTpId(tbEvaluateParam);
    }

    /**
     * ������������������.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam) {
        return tbEvaluateParamMapper.selectTpComb(tbEvaluateParam);
    }
}