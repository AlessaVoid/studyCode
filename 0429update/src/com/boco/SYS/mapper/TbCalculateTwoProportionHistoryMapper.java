package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateTwoProportionHistory;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * ����� Ȩ�ز��� ��ʷ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbCalculateTwoProportionHistoryMapper extends GenericMapper<TbCalculateTwoProportionHistory, String>{
    void deleteByMonthAndType(HashMap<String, Object> map);
}