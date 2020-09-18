package com.boco.PUB.service.tbTransSeq;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbTransSeq;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * TbTransSeq业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbTransSeqService extends IGenericService<TbTransSeq,HashMap<String,Object>>{

    /**
     * 查询流水
     * @param tbTransSeq
     * @return
     */
    List<TbTransSeq> selectTbTransSeq(TbTransSeq tbTransSeq);

}