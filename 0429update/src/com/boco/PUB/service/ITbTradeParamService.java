package com.boco.PUB.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ʱ��ƻ�ά����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbTradeParamService extends IGenericService<TbTradeParam, Integer>{



    /**
     * ��ѯ TbTradeParam
     *
     * @param ParamId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
     TbTradeParam selectByParamId(Integer ParamId)throws Exception;




    /**
     * ���� TbTradeParam
     *
     * @param  TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */

    Json insertTbTradeParam(TbTradeParam TbTradeParam) throws Exception;

    /**
     * �޸� TbTradeParam
     *
     * @param  TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */

    Json updateTbTradeParam(TbTradeParam TbTradeParam) throws Exception;


    /**
     *  ��������������.
     *
     * @param  TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam);

    /**
     *  ����������������.
     *
     * @param  TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam);



    /**
     *  ��������ƻ�����ģʽ.
     *
     * @param  TbTradeParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam);




}