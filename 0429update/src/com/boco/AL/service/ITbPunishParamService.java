package com.boco.AL.service;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * ��Ϣ������ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface ITbPunishParamService extends IGenericService<TbPunishParam, Integer>{



    /**
     * ��ѯtbPunishParam
     *
     * @param ppId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    TbPunishParam selectByPpId(Integer ppId)throws Exception;


    /**
     * ����tbPunishParam����
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */

    Json deploy() throws Exception;


    /**
     * ����tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */

    Json insertTbPunishParam(TbPunishParam tbPunishParam) throws Exception;

    /**
     * �޸�tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */

    Json updateTbPunishParam(TbPunishParam tbPunishParam) throws Exception;


    /**
     *  ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam);

    /**
     *  ���������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam);



    /**
     *  ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam);



}