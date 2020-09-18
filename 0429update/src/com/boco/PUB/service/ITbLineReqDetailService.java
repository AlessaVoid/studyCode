package com.boco.PUB.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 条线需求记录详情表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbLineReqDetailService extends IGenericService<TbLineReqDetail, Integer>{



    /**
     * 联想输入所属周期---
     *
     * @param tbLineReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> showLineReqMonth(TbLineReqDetail tbLineReqDetail);



}