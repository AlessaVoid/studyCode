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
 * ���޶�������Ϣ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbQuotaApplyService extends GenericService<TbQuotaApply, Integer> implements ITbQuotaApplyService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbQuotaApplyMapper tbQuotaApplyMapper;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWorkFlowService workFlowService;

    /**
     * ��������������
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectQaId(TbQuotaApply tbQuotaApply) {
        return tbQuotaApplyMapper.selectQaId(tbQuotaApply);
    }

    /**
     * ����������������.
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectQaMonth(TbQuotaApply tbQuotaApply) {
        return tbQuotaApplyMapper.selectQaMonth(tbQuotaApply);
    }


}