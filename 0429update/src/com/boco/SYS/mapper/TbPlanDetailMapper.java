package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlanDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * �ƻ��ƶ���ϸ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbPlanDetailMapper extends GenericMapper<TbPlanDetail, String>{
    /**
     * �����Ŵ��ƻ�����
     *
     * @param tbPlanDetailDO �Ŵ��ƻ�����
     */
    void insertLoanPlanDetail(List<TbPlanDetail> tbPlanDetailDO);

    /**
     * ɾ���Ŵ��ƻ�����
     *
     * @param planId �Ŵ��ƻ�
     */
    void deleteLoanPlanDetail(String planId);


    /**
     * @Description ��ѯ��������
     * @Author liujinbo
     * @Date 2019/12/28
     * @param
     * @param paramMap
     * @Return java.util.List<com.boco.SYS.entity.TbPlanDetail>
     */
    List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap);

    /**
     * ���¼ƻ���ϸ
     * @param planDetail
     */
    void updatePlanDetail(TbPlanDetail planDetail);
}