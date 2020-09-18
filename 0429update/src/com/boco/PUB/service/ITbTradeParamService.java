package com.boco.PUB.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 时间计划维护表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbTradeParamService extends IGenericService<TbTradeParam, Integer>{



    /**
     * 查询 TbTradeParam
     *
     * @param ParamId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
     TbTradeParam selectByParamId(Integer ParamId)throws Exception;




    /**
     * 新增 TbTradeParam
     *
     * @param  TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */

    Json insertTbTradeParam(TbTradeParam TbTradeParam) throws Exception;

    /**
     * 修改 TbTradeParam
     *
     * @param  TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */

    Json updateTbTradeParam(TbTradeParam TbTradeParam) throws Exception;


    /**
     *  联想输入参数编号.
     *
     * @param  TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam);

    /**
     *  联想输入所属周期.
     *
     * @param  TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam);



    /**
     *  联想输入计划分配模式.
     *
     * @param  TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam);




}