package com.boco.PUB.service.tbCalculateTwo;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateTwoProportion;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ����� Ȩ�ز������ñ�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbCalculateTwoProportionService extends IGenericService<TbCalculateTwoProportion, String>{
    void deleteAll();
}