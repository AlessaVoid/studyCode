package com.boco.PUB.service.tbatLoanProd.impl;

import com.boco.PUB.service.tbatLoanProd.TbatLoanProdService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.mapper.TbatLoanProdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * TbatLoanProdҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbatLoanProdServiceImpl extends GenericService<TbatLoanProd,HashMap<String,Object>> implements TbatLoanProdService {

	@Autowired
	private TbatLoanProdMapper tbatLoanProdMapper;

    @Override
    public List<TbatLoanProd> selectAll(TbatLoanProd tbatLoanProd) {
        List<TbatLoanProd> list = tbatLoanProdMapper.selectAll(tbatLoanProd);
        return list;
    }
}