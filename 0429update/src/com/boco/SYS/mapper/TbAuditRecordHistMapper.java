package com.boco.SYS.mapper;

import com.boco.TONY.common.POJO.DO.TbAuditRecordHistDO;

import java.util.List;

/**
 * 审批每级历史记录
 *
 * @author tony
 * @describe TbAuditRecordHistMapper
 * @date 2019-10-27
 */
public interface TbAuditRecordHistMapper {
    /**
     * 插入审批记录
     *
     * @param tbAuditRecordHistDO 审批历史记录
     */
    void insertRecordHist(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 获取历史审批记录
     *
     * @param tbAuditRecordHistDO 查询条件
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 获取历史审批记录
     *
     * @param tbAuditRecordHistDO 查询条件
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getReqRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 获取历史审批记录
     *
     * @param tbAuditRecordHistDO 查询条件
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getPlanRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 信贷计划更新状态
     *
     * @param tbAuditRecordHistDO 信贷计划审批记录西施
     */
    void updateRecordHistAuditState(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 信贷计划更新状态
     *
     * @param tbAuditRecordHistDO 信贷计划审批记录西施
     */
    void updateRecordHistPosition(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * 通过需求月查询
     *
     * @param reqMonth 需求月
     * @return
     */
    String selectSubmitMonth(String reqMonth);

    /**
     * 通过需求状态
     *
     * @param reqState 需求状态
     * @return
     */
    String selectSubmitReqState(String reqState);

}
