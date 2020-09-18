package com.boco.PUB.service.loanPlan.Impl;

import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * 
 * 
 * �ƻ��ƶ���ϸ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbPlanDetailServiceImpl extends GenericService<TbPlanDetail, String> implements TbPlanDetailService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbPlanDetailMapper mapper;


    @Override
    public List<TbPlanDetail> selectAllStripe(HashMap<String, Object> paramMap) {
        return mapper.selectAllStripe(paramMap);
    }

}