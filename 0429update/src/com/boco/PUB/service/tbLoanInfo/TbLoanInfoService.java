package com.boco.PUB.service.tbLoanInfo;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbLoanInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 借据表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbLoanInfoService extends IGenericService<TbLoanInfo,HashMap<String,Object>>{
    /**
     * 查询借据
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectTbLoanInfo(HashMap<String, Object> paramMap);
}