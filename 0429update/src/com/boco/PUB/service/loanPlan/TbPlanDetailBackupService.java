package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanDetailBackup;

/**
 * 
 * 
 * 计划制定明细表备份表（第一次制定计划）业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbPlanDetailBackupService extends IGenericService<TbPlanDetailBackup,String>{
    /**
     * @Author: Liujinbo
     * @Description:  添加计划备份
     * @Date: 2020/2/12
     * @param planId :
     * @return: void
     **/
    void addPlanDetailBackup(String planId);
}