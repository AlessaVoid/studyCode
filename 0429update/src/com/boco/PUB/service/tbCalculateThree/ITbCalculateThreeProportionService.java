package com.boco.PUB.service.tbCalculateThree;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateThreeProportion;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * TbCalculateThreeProportionҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbCalculateThreeProportionService extends IGenericService<TbCalculateThreeProportion, String>{

    void deleteAll();
}