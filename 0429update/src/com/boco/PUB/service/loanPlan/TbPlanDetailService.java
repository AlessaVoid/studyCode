package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * 计划制定明细表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbPlanDetailService extends IGenericService<TbPlanDetail, String>{

    /**
     * @Description 查询所有条线
     * @Author liujinbo
     * @Date 2020/1/2
     * @param
     * @param paramMap
     * @Return java.util.List<com.boco.SYS.entity.TbPlanDetail>
     */
    List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap);
}