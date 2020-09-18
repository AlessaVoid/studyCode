package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.mapper.TbTradeParamMapper;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 时间计划维护表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbTradeParamService extends GenericService<TbTradeParam, Integer> implements ITbTradeParamService {


   @Autowired
   private TbTradeParamMapper tbTradeParamMapper;
    /**
     * 查询 TbTradeParam
     *
     * @param ParamId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public TbTradeParam selectByParamId(Integer ParamId) throws Exception {
        return tbTradeParamMapper.selectByPK(ParamId);
    }


    /**
     * 新增 TbTradeParam
     *
     * @param TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public Json insertTbTradeParam(TbTradeParam TbTradeParam) throws Exception {
        return null;
    }

    /**
     * 修改 TbTradeParam
     *
     * @param TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public Json updateTbTradeParam(TbTradeParam TbTradeParam) throws Exception {
        return null;
    }

    /**
     * 联想输入参数编号.
     *
     * @param TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam) {
        List<Map<String, Integer>> list = tbTradeParamMapper.selectParamId(TbTradeParam);
        return list;
    }

    /**
     * 联想输入所属周期.
     *
     * @param TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam) {
        List<Map<String, String>> list = tbTradeParamMapper.selectParamPeriod(TbTradeParam);
        return list;
    }

    /**
     * 联想输入计划分配模式.
     *
     * @param TbTradeParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam) {
        List<Map<String, String>> list = tbTradeParamMapper.selectParamMode(TbTradeParam);
        return list;
    }
    //有自定义方法时使用
	//@Autowired
	//private TbTradeParamMapper mapper;



}