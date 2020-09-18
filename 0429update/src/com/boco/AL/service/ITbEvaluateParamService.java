package com.boco.AL.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbEvaluateParam;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * 评分管理参数表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbEvaluateParamService extends IGenericService<TbEvaluateParam, Integer> {


    /**
     * 部署评价规则
     *
     * @return
     */
    Json deploy();


    /**
     * 联想输入参数编号.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam);

    /**
     * 联想输入贷种组合名称.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam);


}