package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateTwoImportData;
import com.boco.SYS.base.GenericMapper;

/**
 * �����  ��ʷ���ݵ�������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbCalculateTwoImportDataMapper extends GenericMapper<TbCalculateTwoImportData, String> {
    void deleteByMonthAndType(HashMap<String, Object> queryMap);
}