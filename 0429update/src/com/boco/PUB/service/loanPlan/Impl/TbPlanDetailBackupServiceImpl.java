package com.boco.PUB.service.loanPlan.Impl;

import com.boco.PUB.service.loanPlan.TbPlanDetailBackupService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbPlanDetailBackup;
import com.boco.SYS.mapper.TbPlanDetailBackupMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.util.BocoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * �ƻ��ƶ���ϸ���ݱ���һ���ƶ��ƻ���ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbPlanDetailBackupServiceImpl extends GenericService<TbPlanDetailBackup,String> implements TbPlanDetailBackupService {
    @Autowired
    private TbPlanDetailBackupMapper tbPlanDetailBackupMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;


    @Override
    public void addPlanDetailBackup(String planId) {
        //��ȡ�ƻ�ԭʼֵ

        List<TbPlanDetail> tbPlanDetailList = tbPlanDetailMapper.selectByWhere("pd_ref_id = \'" + planId + "\'");

        //��װ�ƻ�����
        List<TbPlanDetailBackup> tbPlanDetailBackupList = buildPlanBackup(tbPlanDetailList);

        //�Ѽƻ���ԭʼֵ�Ž����ݱ�

        tbPlanDetailBackupMapper.insertBatch(tbPlanDetailBackupList);

    }

    //��װ�ƻ�����
    private List<TbPlanDetailBackup> buildPlanBackup(List<TbPlanDetail> tbPlanDetailList) {

        ArrayList<TbPlanDetailBackup> tbPlanDetailBackupList = new ArrayList<>();
        for (TbPlanDetail plan : tbPlanDetailList) {
            TbPlanDetailBackup backup = new TbPlanDetailBackup();
            backup.setPdId(plan.getPdId());
            backup.setPdRefId(plan.getPdRefId());
            backup.setPdOrgan(plan.getPdOrgan());
            backup.setPdMonth(plan.getPdMonth());
            backup.setPdLoanType(plan.getPdLoanType());
            backup.setPdAmount(plan.getPdAmount());
            backup.setPdUnit(plan.getPdUnit());
            backup.setPdCreateTime(BocoUtils.getTime());
            backup.setPdUpdateTime(BocoUtils.getTime());

            tbPlanDetailBackupList.add(backup);
        }
        return tbPlanDetailBackupList;
    }
}












