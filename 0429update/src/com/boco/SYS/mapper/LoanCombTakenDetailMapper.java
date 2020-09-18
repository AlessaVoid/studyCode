package com.boco.SYS.mapper;

import com.boco.TONY.biz.loancomb.POJO.DO.combtaken.CombTakenDetailDO;

import java.util.List;

/**
 * @author tony
 * @describe LoanInnerTakenMapper
 * @date 2019-10-28
 */
public interface LoanCombTakenDetailMapper {
    void insertCombTakenDetailInfo(CombTakenDetailDO combInnerTakenDO);

    List<CombTakenDetailDO> selectCombTakenInfoByParentId(String parentCombId);

    List<CombTakenDetailDO> getInnerTakenCombInfo(String combId);

    void updateInnerTakenCombInfo(CombTakenDetailDO combInnerTakenDO);

    void deleteTakenCombInfo(String parentComb);

    Integer selectInterTakentype();

    Integer getTakenTypeByCombParent(String parentComb);
}
