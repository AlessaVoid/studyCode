package com.boco.PUB.service.loanPlan.Impl;

import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * 
 * 
 * 计划制定明细表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbPlanDetailServiceImpl extends GenericService<TbPlanDetail, String> implements TbPlanDetailService {
    //有自定义方法时使用
    @Autowired
    private TbPlanDetailMapper mapper;


    @Override
    public List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap) {
        return mapper.selectAllStripe(paramMap);
    }

}