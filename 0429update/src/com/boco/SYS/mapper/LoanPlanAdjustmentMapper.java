package com.boco.SYS.mapper;

import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;

import java.util.List;

/**
 * 信贷计划调整
 *
 * @author tony
 * @describe LoanPlanAdjustmentMapper
 * @date 2019-09-29
 */
public interface LoanPlanAdjustmentMapper {

    /**
     * 插入信贷计划调整详情
     *
     * @param tbPlanAdjustDetail 信贷计划详情
     */
    void insertLoanPlanAdjustmentInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * 更新信贷计划调整详情
     *
     * @param tbPlanAdjustDetail 信贷计划详情
     */
    void updateLoanPlanAdjustmentInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * 删除信贷计划调整详情
     *
     * @param planId 信贷计划
     */
    @SuppressWarnings("unused")
    void deleteLoanPlanAdjustmentInfo(String planId);

    /**
     * 通过信贷计划详情查询信贷计划
     *
     * @param planId 信贷计划详情标识
     */
    @SuppressWarnings("unused")
    List<TbPlanAdjustDetailDO> selectLoanPlanAdInfoById(String planId);
    /**
     * 通过属性查询信贷计划
     *
     * @param tbPlanAdjustDetail 信贷计划属性
     * @return TbPlanDetailDO
     */
    TbPlanAdjustDetailDO selectLoanPlanDetailByAttr(TbPlanAdjustDetailDO tbPlanAdjustDetail);
}
