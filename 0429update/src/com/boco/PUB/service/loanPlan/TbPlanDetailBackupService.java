package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanDetailBackup;

/**
 * 
 * 
 * �ƻ��ƶ���ϸ���ݱ���һ���ƶ��ƻ���ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbPlanDetailBackupService extends IGenericService<TbPlanDetailBackup,String>{
    /**
     * @Author: Liujinbo
     * @Description:  ��Ӽƻ�����
     * @Date: 2020/2/12
     * @param planId :
     * @return: void
     **/
    void addPlanDetailBackup(String planId);
}