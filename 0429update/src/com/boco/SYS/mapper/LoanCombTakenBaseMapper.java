package com.boco.SYS.mapper;

import com.boco.TONY.biz.loancomb.POJO.DO.combtaken.CombTakenBaseDO;

/**
 * @author tony
 * @describe LoanCombTakenMapper
 * @date 2019-10-18
 */
public interface LoanCombTakenBaseMapper {
    /**
     * ����������ռ�û�����Ϣ
     */
    void insertCombTakenBaseInfo(CombTakenBaseDO combTakenBaseDO);

    /**
     * ͨ���ϼ������ȡ���������Ϣ
     *
     * @param combParentCode ��������ϼ���Ϣ
     * @return
     */
    CombTakenBaseDO getCombTakenBaseInfo(String combParentCode);


    void updateCombTakenBaseInfo(CombTakenBaseDO combTakenBaseDO);

    void selectInterTakentype();
}
