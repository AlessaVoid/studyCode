package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbBankRmbBusi;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ��ҵ���������ҵ��ͳ�Ʊ����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbBankRmbBusiMapper extends GenericMapper<TbBankRmbBusi, String>{


    /**
     * ��ѯ��ǰ�·��������ݵ�����
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectDataDate(Map<String, Object> paramMap);
}