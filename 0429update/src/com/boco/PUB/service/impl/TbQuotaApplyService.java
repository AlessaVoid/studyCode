package com.boco.PUB.service.impl;


import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PUB.service.ITbQuotaApplyService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.global.SysParam;
import com.boco.SYS.mapper.TbQuotaApplyMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbQuotaApply;
import com.boco.SYS.base.GenericService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超限额申请信息表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbQuotaApplyService extends GenericService<TbQuotaApply, Integer> implements ITbQuotaApplyService {
    //有自定义方法时使用
    @Autowired
    private TbQuotaApplyMapper tbQuotaApplyMapper;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWorkFlowService workFlowService;

    /**
     * 联想输入申请编号
     *
     * @param tbQuotaApply
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年月10日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectQaId(TbQuotaApply tbQuotaApply) {
        return tbQuotaApplyMapper.selectQaId(tbQuotaApply);
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbQuotaApply
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectQaMonth(TbQuotaApply tbQuotaApply) {
        return tbQuotaApplyMapper.selectQaMonth(tbQuotaApply);
    }


}