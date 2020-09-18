package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.base.GenericMapper;

/**
 * Ԥ���߱����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbWarnMapper extends GenericMapper<TbWarn, Integer> {

    /**
     * ��������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, Integer>> selectWarnId(TbWarn tbWarn);

    /**
     * ���������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, String>> selectWarnName(TbWarn tbWarn);
    List<Map<String, String>> selectWarnNameByOrgan(TbWarn tbWarn);
    /**
     * ��������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, String>> selectWarnOrgan(TbWarn tbWarn);

    List<Map<String, String>> selectWarnOrgan1(Map<String, String> map);
}