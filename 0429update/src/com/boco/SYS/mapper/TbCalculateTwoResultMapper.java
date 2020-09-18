package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbCalculateTwoResult;
import com.boco.SYS.base.GenericMapper;

/**
 * ����� ��������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbCalculateTwoResultMapper extends GenericMapper<TbCalculateTwoResult, String> {
    List<Map<String, String>> selectMonth();


    void deleteByMonthAndType(HashMap<String, Object> queryMap);
}