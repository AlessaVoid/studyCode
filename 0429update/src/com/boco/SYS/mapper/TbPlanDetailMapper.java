package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlanDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * 计划制定明细表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbPlanDetailMapper extends GenericMapper<TbPlanDetail, String>{
    /**
     * 插入信贷计划详情
     *
     * @param tbPlanDetailDO 信贷计划详情
     */
    void insertLoanPlanDetail(List<TbPlanDetail> tbPlanDetailDO);

    /**
     * 删除信贷计划详情
     *
     * @param planId 信贷计划
     */
    void deleteLoanPlanDetail(String planId);


    /**
     * @Description 查询所有条线
     * @Author liujinbo
     * @Date 2019/12/28
     * @param
     * @param paramMap
     * @Return java.util.List<com.boco.SYS.entity.TbPlanDetail>
     */
    List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap);

    /**
     * 更新计划明细
     * @param planDetail
     */
    void updatePlanDetail(TbPlanDetail planDetail);
}