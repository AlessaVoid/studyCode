package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbLoanInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ��ݱ����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbLoanInfoMapper extends GenericMapper<TbLoanInfo,HashMap<String,Object>>{
    /*��ѯ���*/
    List<Map<String, Object>> selectTbLoanInfo(HashMap<String, Object> paramMap);
}