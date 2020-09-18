package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.mapper.TbTradeParamMapper;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ʱ��ƻ�ά����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbTradeParamService extends GenericService<TbTradeParam, Integer> implements ITbTradeParamService {


   @Autowired
   private TbTradeParamMapper tbTradeParamMapper;
    /**
     * ��ѯ TbTradeParam
     *
     * @param ParamId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public TbTradeParam selectByParamId(Integer ParamId) throws Exception {
        return tbTradeParamMapper.selectByPK(ParamId);
    }


    /**
     * ���� TbTradeParam
     *
     * @param TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public Json insertTbTradeParam(TbTradeParam TbTradeParam) throws Exception {
        return null;
    }

    /**
     * �޸� TbTradeParam
     *
     * @param TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public Json updateTbTradeParam(TbTradeParam TbTradeParam) throws Exception {
        return null;
    }

    /**
     * ��������������.
     *
     * @param TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam) {
        List<Map<String, Integer>> list = tbTradeParamMapper.selectParamId(TbTradeParam);
        return list;
    }

    /**
     * ����������������.
     *
     * @param TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam) {
        List<Map<String, String>> list = tbTradeParamMapper.selectParamPeriod(TbTradeParam);
        return list;
    }

    /**
     * ��������ƻ�����ģʽ.
     *
     * @param TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam) {
        List<Map<String, String>> list = tbTradeParamMapper.selectParamMode(TbTradeParam);
        return list;
    }
    //���Զ��巽��ʱʹ��
	//@Autowired
	//private TbTradeParamMapper mapper;



}