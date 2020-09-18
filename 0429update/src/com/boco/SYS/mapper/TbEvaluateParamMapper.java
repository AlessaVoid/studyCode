package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbEvaluateParam;

import java.util.List;
import java.util.Map;

/**
 * 评分管理参数表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbEvaluateParamMapper extends GenericMapper<TbEvaluateParam, Integer> {
    /**
     * 联想输入参数编号.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam);

    /**
     * 联想输入贷种组合名称.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam);


}