package com.boco.PUB.service.tbatLoanProd;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * TbatLoanProdҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbatLoanProdService extends IGenericService<TbatLoanProd,HashMap<String,Object>>{
    /**
     * ��ѯ���еĵ���������
     * @param tbatLoanProd
     * @return
     */
    List<TbatLoanProd> selectAll(TbatLoanProd tbatLoanProd);
}