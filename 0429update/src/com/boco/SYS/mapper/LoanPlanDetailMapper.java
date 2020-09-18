package com.boco.SYS.mapper;

import com.boco.SYS.entity.TbPlanDetail;

import java.util.List;

/**
 * 信贷计划详情DAO
 *
 * @author tony
 * @describe LoanPlanDetailMapper
 * @date 2019-09-29
 */
public interface LoanPlanDetailMapper {

    /**
     * 插入信贷计划详情
     *
     * @param tbPlanDetail 信贷计划详情
     */
    void insertLoanPlanDetail(List<TbPlanDetail> tbPlanDetail);

    /**
     * 更新信贷计划详情
     *
     * @param tbPlanDetail 信贷计划详情
     */
    void updateLoanPlanDetail(List<TbPlanDetail> tbPlanDetail);

    /**
     * 删除信贷计划详情
     *
     * @param planId 信贷计划
     */
    void deleteLoanPlanDetail(String planId);


    void updateplanDetailList(List<TbPlanDetail> tbPlanDetail);

}
