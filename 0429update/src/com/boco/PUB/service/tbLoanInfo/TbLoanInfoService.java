package com.boco.PUB.service.tbLoanInfo;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbLoanInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ��ݱ�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbLoanInfoService extends IGenericService<TbLoanInfo,HashMap<String,Object>>{
    /**
     * ��ѯ���
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectTbLoanInfo(HashMap<String, Object> paramMap);
}