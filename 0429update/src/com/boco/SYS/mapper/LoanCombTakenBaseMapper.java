package com.boco.SYS.mapper;

import com.boco.TONY.biz.loancomb.POJO.DO.combtaken.CombTakenBaseDO;

/**
 * @author tony
 * @describe LoanCombTakenMapper
 * @date 2019-10-18
 */
public interface LoanCombTakenBaseMapper {
    /**
     * 插入贷种组合占用基本信息
     */
    void insertCombTakenBaseInfo(CombTakenBaseDO combTakenBaseDO);

    /**
     * 通过上级编码获取贷种组合信息
     *
     * @param combParentCode 贷种组合上级信息
     * @return
     */
    CombTakenBaseDO getCombTakenBaseInfo(String combParentCode);


    void updateCombTakenBaseInfo(CombTakenBaseDO combTakenBaseDO);

    void selectInterTakentype();
}
