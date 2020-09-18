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
 * 计划制定明细表备份表（第一次制定计划）业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
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
        //获取计划原始值

        List<TbPlanDetail> tbPlanDetailList = tbPlanDetailMapper.selectByWhere("pd_ref_id = \'" + planId + "\'");

        //组装计划备份
        List<TbPlanDetailBackup> tbPlanDetailBackupList = buildPlanBackup(tbPlanDetailList);

        //把计划的原始值放进备份表

        tbPlanDetailBackupMapper.insertBatch(tbPlanDetailBackupList);

    }

    //组装计划备份
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












