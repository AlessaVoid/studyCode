package com.boco.SYS.mapper;


import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.base.GenericMapper;

import java.util.List;
import java.util.Map;

/**
 * 时间计划维护表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbTradeParamMapper extends GenericMapper<TbTradeParam, Integer> {


    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, Integer>> selectParamId(TbTradeParam TbTradeParam);

    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, String>> selectParamPeriod(TbTradeParam TbTradeParam);

    /**
     * @param TbTradeParam
     * @return
     */
    List<Map<String, String>> selectParamMode(TbTradeParam TbTradeParam);

}