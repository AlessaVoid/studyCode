package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * TbatLoanProd���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbatLoanProdMapper extends GenericMapper<TbatLoanProd,HashMap<String,Object>>{
    /**
     * ��ѯ���е���������
     * @param tbatLoanProd
     * @return
     */
    List<TbatLoanProd> selectAll(TbatLoanProd tbatLoanProd);
}