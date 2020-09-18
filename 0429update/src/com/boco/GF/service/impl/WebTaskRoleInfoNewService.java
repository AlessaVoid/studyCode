package com.boco.GF.service.impl;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.ActicitiRoleConfigNew;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
import com.boco.SYS.mapper.WebTaskRoleInfoNewMapper;
import com.boco.SYS.service.IActicitiRoleConfigNewService;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebTaskRoleInfoNewҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebTaskRoleInfoNewService extends GenericService<WebTaskRoleInfoNew, HashMap<String, Object>> implements IWebTaskRoleInfoNewService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private WebTaskRoleInfoNewMapper webTaskRoleInfoNewMapper;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private IWorkFlowService workFlowService;

    @Autowired
    private IActicitiRoleConfigNewService acticitiRoleConfigNewService;

    @Override
    public void updateRoleInfoByBatch(List<WebTaskRoleInfoNew> webTaskRoleInfoList, String procDefId)
            throws Exception {
        for (WebTaskRoleInfoNew webTaskRoleInfoNew : webTaskRoleInfoList) {
            webTaskRoleInfoNew.setProcDefId(procDefId);
            webTaskRoleInfoNew.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
            webTaskRoleInfoNew.setLatestModifyTime(BocoUtils.getNowTime());
            webTaskRoleInfoNew.setLatestOperCode(WebContextUtil.getSessionUser().getOpercode());
        }
        //ɾ��������������
        webTaskRoleInfoNewMapper.deleteByProcDefId(procDefId);
        //�������¸������½�ɫ������Ϣ
        if (webTaskRoleInfoList.size() > 0) {
            webTaskRoleInfoNewMapper.insertBatch(webTaskRoleInfoList);
        }
    }

    @Override
    public List<WebTaskRoleInfoNew> selectByProcDefId(String procDefId) throws Exception {
        WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
        webTaskRoleInfoNew.setProcDefId(procDefId);
        return webTaskRoleInfoNewMapper.selectByAttr(webTaskRoleInfoNew);
    }

    @Override
    public void updateProdStatus(String prodCode, String taskId, String custType) throws Exception {
        Task task = workFlowService.getTaskById(taskId);
        HashMap<String, Object> pk = new HashMap<String, Object>();
        pk.put("taskId", task.getTaskDefinitionKey());
        pk.put("procDefId", task.getProcessDefinitionId());
        pk.put("custType", "0".equals(custType) ? "1" : custType);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);
        pk.put("organLevel", String.valueOf(variables.get("organLevel")));
        WebTaskRoleInfoNew webTaskRoleInfoNew = webTaskRoleInfoNewMapper.selectByPK(pk);
    }

    @Override
    public void nodeConfig(String procDefId, String type) throws Exception {
        ActicitiRoleConfigNew acticitiRoleConfigNew = new ActicitiRoleConfigNew();
        acticitiRoleConfigNew.setActivitiType(type);
        List<WebTaskRoleInfoNew> webTaskRoleInfoNew_list = new ArrayList<WebTaskRoleInfoNew>();
        List<ActicitiRoleConfigNew> acticitiRoleConfigNew_list = acticitiRoleConfigNewService.selectByAttr(acticitiRoleConfigNew);
        for (ActicitiRoleConfigNew info : acticitiRoleConfigNew_list) {
            WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
            webTaskRoleInfoNew.setTaskId(info.getTaskId());
            webTaskRoleInfoNew.setProcDefId(procDefId);
            webTaskRoleInfoNew.setUpTaskId(info.getUpTaskId());
            webTaskRoleInfoNew.setOrganLevel(info.getOrganLevel());
            webTaskRoleInfoNew.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
            webTaskRoleInfoNew.setLatestModifyTime(BocoUtils.getNowTime());
            webTaskRoleInfoNew.setLatestOperCode(WebContextUtil.getSessionUser().getOpercode());
            webTaskRoleInfoNew.setCustType(info.getCustType());
            webTaskRoleInfoNew.setAppStatus(info.getAppStatus());
            webTaskRoleInfoNew_list.add(webTaskRoleInfoNew);
        }
        //ɾ��������������
        webTaskRoleInfoNewMapper.deleteByProcDefId(procDefId);
        //�������¸������½�ɫ������Ϣ
        if (webTaskRoleInfoNew_list.size() > 0) {
            webTaskRoleInfoNewMapper.insertBatch(webTaskRoleInfoNew_list);
        }
    }
}