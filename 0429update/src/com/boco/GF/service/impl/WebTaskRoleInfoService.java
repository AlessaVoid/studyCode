package com.boco.GF.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.service.IFdBusinessDateService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.ActicitiRoleConfig;
import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.entity.WebTaskRoleInfo;
import com.boco.SYS.mapper.WebTaskRoleInfoMapper;
import com.boco.SYS.service.IActicitiRoleConfigService;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;

/**
 * ����ڵ��ɫ��Ӧ��Ϣ��||����ڵ��ɫ��Ӧ��Ϣ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebTaskRoleInfoService extends GenericService<WebTaskRoleInfo, HashMap<String, Object>> implements IWebTaskRoleInfoService {
    @Autowired
    private WebTaskRoleInfoMapper webTaskRoleInfoMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private IActicitiRoleConfigService acticitiRoleConfigService;

    /**
     * TODO ��ȡ�������������˵Ľ�ɫ����.
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��4��6��    	  ����    �½�
     *                   </pre>
     */
    @Override
    public String getAppUserRole(String processDefinitionKey, String ProcessDefinitionId, String taskId, Map<String, Object> map) throws Exception {
        String roleCode = "";
        WebTaskRoleInfo webTaskRoleInfo = new WebTaskRoleInfo();
//        webTaskRoleInfo.setOrganLevel(String.valueOf(map.get("organLevel")));//��ǰ�����Ļ�������
        //��ʼ�ڵ�
        webTaskRoleInfo.setTaskId(taskId);
        //������̶���id��Ϊ��ʱ
        if (StringUtils.isNotEmpty(ProcessDefinitionId)) {
            webTaskRoleInfo.setProcDefId(ProcessDefinitionId);
        } else {//������̶���idΪ��ʱ
            //ͨ��ָ��������key��ȡ���µ����̶�����Ϣ
            ProcessDefinition processDefinition = workFlowService.getProcessDefinitionList(processDefinitionKey).get(0);
            webTaskRoleInfo.setProcDefId(processDefinition.getId());
        }
        if (map != null && null != (String) map.get("custType") && !"".equals((String) map.get("custType"))) {
            String custType = (String) map.get("custType");
            if (custType.equals("0")) {
                custType = "1";
            }
            webTaskRoleInfo.setCustType(custType);
        }
        //��ȡ��ɫ����
        int i = 0;
        List<WebTaskRoleInfo> list = null;
        while (list == null && i <= 5) {
            list = webTaskRoleInfoMapper.selectByAttr(webTaskRoleInfo);
            i++;
        }
        //ƴ�ӽ�ɫ����
        for (WebTaskRoleInfo info : list) {
            roleCode += "," + info.getRoleCode();
        }
        if (roleCode.length() > 0) {
            roleCode = roleCode.substring(1);
        }
        return roleCode;
    }

    /**
     * TODO ��ȡ�������������˵Ľ�ɫ����.
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��4��27��    	  ����    �½�
     *                   </pre>
     */
    @Override
    public String getAppUserRoleByAttr(String processDefinitionKey, WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        String roleCode = "";
        webTaskRoleInfo.setOrganLevel(WebContextUtil.getSessionOrgan().getOrganlevel());//��ǰ�����Ļ�������

        //������̶���idΪ��ʱ
        //ͨ��ָ��������key��ȡ���µ����̶�����Ϣ
        if (!StringUtils.isNotEmpty(webTaskRoleInfo.getProcDefId())) {
            ProcessDefinition processDefinition = workFlowService.getProcessDefinitionList(processDefinitionKey).get(0);
            webTaskRoleInfo.setProcDefId(processDefinition.getId());
        }
        //��ȡ��ɫ����
        List<WebTaskRoleInfo> list = webTaskRoleInfoMapper.selectByAttr(webTaskRoleInfo);
        //ƴ�ӽ�ɫ����
        for (WebTaskRoleInfo info : list) {
            roleCode += "," + info.getRoleCode();
        }
        if (roleCode.length() > 0) {
            roleCode = roleCode.substring(1);
        }
        return roleCode;
    }

    @Override
    public void updateRoleInfoByBatch(List<WebTaskRoleInfo> webTaskRoleInfoList, String procDefId) throws Exception {
        for (WebTaskRoleInfo webTaskRoleInfo : webTaskRoleInfoList) {
            webTaskRoleInfo.setProcDefId(procDefId);
            webTaskRoleInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
            webTaskRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
            webTaskRoleInfo.setLatestOperCode(WebContextUtil.getSessionUser().getOpercode());
        }
        //ɾ��������������
        webTaskRoleInfoMapper.deleteByProcDefId(procDefId);
        //�������¸������½�ɫ������Ϣ
        if (webTaskRoleInfoList.size() > 0) {
            webTaskRoleInfoMapper.insertBatch(webTaskRoleInfoList);
        }
    }

    @Override
    public List<WebTaskRoleInfo> selectByProcDefId(String procDefId) throws Exception {
        WebTaskRoleInfo webTaskRoleInfo = new WebTaskRoleInfo();
        webTaskRoleInfo.setProcDefId(procDefId);
        return webTaskRoleInfoMapper.selectByAttr(webTaskRoleInfo);
    }

    @Override
    public void nodeConfig(String procDefId, String type) throws Exception {
        ActicitiRoleConfig acticitiRoleConfig = new ActicitiRoleConfig();
        acticitiRoleConfig.setActivitiType(type);
        List<WebTaskRoleInfo> webTaskRoleInfo_list = new ArrayList<WebTaskRoleInfo>();
        List<ActicitiRoleConfig> acticitiRoleConfig_list = acticitiRoleConfigService.selectByAttr(acticitiRoleConfig);
        for (ActicitiRoleConfig info : acticitiRoleConfig_list) {
            WebTaskRoleInfo webTaskRoleInfo = new WebTaskRoleInfo();
            webTaskRoleInfo.setTaskId(info.getTaskId());
            webTaskRoleInfo.setProcDefId(procDefId);
            webTaskRoleInfo.setRoleCode(info.getRoleCode());
            webTaskRoleInfo.setOrganLevel(info.getOrganLevel());
            webTaskRoleInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
            webTaskRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
            webTaskRoleInfo.setLatestOperCode(WebContextUtil.getSessionUser().getOpercode());
            webTaskRoleInfo.setAppStatus(info.getAppStatus());
            webTaskRoleInfo.setCustType(info.getCustType());
            webTaskRoleInfo_list.add(webTaskRoleInfo);
        }
        //ɾ��������������
        webTaskRoleInfoMapper.deleteByProcDefId(procDefId);
        //�������¸������½�ɫ������Ϣ
        if (webTaskRoleInfo_list.size() > 0) {
            webTaskRoleInfoMapper.insertBatch(webTaskRoleInfo_list);
        }
    }
}