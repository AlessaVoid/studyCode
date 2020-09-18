package com.boco.SYS.mapper;

import com.boco.SYS.entity.TbPlanDetail;

import java.util.List;

/**
 * �Ŵ��ƻ�����DAO
 *
 * @author tony
 * @describe LoanPlanDetailMapper
 * @date 2019-09-29
 */
public interface LoanPlanDetailMapper {

    /**
     * �����Ŵ��ƻ�����
     *
     * @param tbPlanDetail �Ŵ��ƻ�����
     */
    void insertLoanPlanDetail(List<TbPlanDetail> tbPlanDetail);

    /**
     * �����Ŵ��ƻ�����
     *
     * @param tbPlanDetail �Ŵ��ƻ�����
     */
    void updateLoanPlanDetail(List<TbPlanDetail> tbPlanDetail);

    /**
     * ɾ���Ŵ��ƻ�����
     *
     * @param planId �Ŵ��ƻ�
     */
    void deleteLoanPlanDetail(String planId);


    void updateplanDetailList(List<TbPlanDetail> tbPlanDetail);

}
