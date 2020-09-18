package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * TbatLoanProd数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbatLoanProdMapper extends GenericMapper<TbatLoanProd,HashMap<String,Object>>{
    /**
     * 查询所有第三方数据
     * @param tbatLoanProd
     * @return
     */
    List<TbatLoanProd> selectAll(TbatLoanProd tbatLoanProd);
}