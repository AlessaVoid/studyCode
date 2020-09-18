package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * �ƻ��ƶ���ϸ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbPlanDetailService extends IGenericService<TbPlanDetail, String>{

    /**
     * @Description ��ѯ��������
     * @Author liujinbo
     * @Date 2020/1/2
     * @param
     * @param paramMap
     * @Return java.util.List<com.boco.SYS.entity.TbPlanDetail>
     */
    List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap);
}