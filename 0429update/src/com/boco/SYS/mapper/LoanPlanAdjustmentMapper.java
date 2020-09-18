package com.boco.SYS.mapper;

import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;

import java.util.List;

/**
 * �Ŵ��ƻ�����
 *
 * @author tony
 * @describe LoanPlanAdjustmentMapper
 * @date 2019-09-29
 */
public interface LoanPlanAdjustmentMapper {

    /**
     * �����Ŵ��ƻ���������
     *
     * @param tbPlanAdjustDetail �Ŵ��ƻ�����
     */
    void insertLoanPlanAdjustmentInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * �����Ŵ��ƻ���������
     *
     * @param tbPlanAdjustDetail �Ŵ��ƻ�����
     */
    void updateLoanPlanAdjustmentInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * ɾ���Ŵ��ƻ���������
     *
     * @param planId �Ŵ��ƻ�
     */
    @SuppressWarnings("unused")
    void deleteLoanPlanAdjustmentInfo(String planId);

    /**
     * ͨ���Ŵ��ƻ������ѯ�Ŵ��ƻ�
     *
     * @param planId �Ŵ��ƻ������ʶ
     */
    @SuppressWarnings("unused")
    List<TbPlanAdjustDetailDO> selectLoanPlanAdInfoById(String planId);
    /**
     * ͨ�����Բ�ѯ�Ŵ��ƻ�
     *
     * @param tbPlanAdjustDetail �Ŵ��ƻ�����
     * @return TbPlanDetailDO
     */
    TbPlanAdjustDetailDO selectLoanPlanDetailByAttr(TbPlanAdjustDetailDO tbPlanAdjustDetail);
}
