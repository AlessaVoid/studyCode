package com.boco.AL.service.impl;

import com.boco.AL.service.ITbEvaluateParamService;
import com.boco.SYS.entity.TbEvaluateParam;
import com.boco.SYS.mapper.TbEvaluateParamMapper;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;

import java.util.List;
import java.util.Map;

/**
 * 评分管理参数表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbEvaluateParamService extends GenericService<TbEvaluateParam, Integer> implements ITbEvaluateParamService {
    //有自定义方法时使用
    @Autowired
    private TbEvaluateParamMapper tbEvaluateParamMapper;

    /**
     * 部署评价规则
     *
     * @return
     */
    @Override
    public Json deploy() {
        return null;
    }

    /**
     * 联想输入参数编号.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectTpId(TbEvaluateParam tbEvaluateParam) {
        return tbEvaluateParamMapper.selectTpId(tbEvaluateParam);
    }

    /**
     * 联想输入贷种组合名称.
     *
     * @param tbEvaluateParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectTpComb(TbEvaluateParam tbEvaluateParam) {
        return tbEvaluateParamMapper.selectTpComb(tbEvaluateParam);
    }
}