package com.boco.PUB.service.tbatLoanProd;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * TbatLoanProd业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbatLoanProdService extends IGenericService<TbatLoanProd,HashMap<String,Object>>{
    /**
     * 查询所有的第三方数据
     * @param tbatLoanProd
     * @return
     */
    List<TbatLoanProd> selectAll(TbatLoanProd tbatLoanProd);
}