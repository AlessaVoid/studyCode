package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.GenericMapper;

/**
 * ��Ϣ���������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbPunishParamMapper extends GenericMapper<TbPunishParam, Integer> {




    /**
     * TODO ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��11��    	    �غ���   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam);

    /**
     * TODO ���������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��11��    	    �غ���   �½�
     * </pre>
     */
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam);

    /**
     * TODO ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��11��    	    �غ���   �½�
     * </pre>
     */
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam);

}