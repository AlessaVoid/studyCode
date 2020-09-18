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
 * TbatLoanProd业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
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