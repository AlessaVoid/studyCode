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
 * 任务节点角色对应信息表||任务节点角色对应信息表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
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
     * TODO 获取启动流程审批人的角色代码.
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年4月6日    	  杜旭    新建
     *                   </pre>
     */
    @Override
    public String getAppUserRole(String processDefinitionKey, String ProcessDefinitionId, String taskId, Map<String, Object> map) throws Exception {
        String roleCode = "";
        WebTaskRoleInfo webTaskRoleInfo = new WebTaskRoleInfo();
//        webTaskRoleInfo.setOrganLevel(String.valueOf(map.get("organLevel")));//当前机构的机构级别
        //开始节点
        webTaskRoleInfo.setTaskId(taskId);
        //如果流程定义id不为空时
        if (StringUtils.isNotEmpty(ProcessDefinitionId)) {
            webTaskRoleInfo.setProcDefId(ProcessDefinitionId);
        } else {//如果流程定义id为空时
            //通过指定的流程key获取最新的流程定义信息
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
        //获取角色编码
        int i = 0;
        List<WebTaskRoleInfo> list = null;
        while (list == null && i <= 5) {
            list = webTaskRoleInfoMapper.selectByAttr(webTaskRoleInfo);
            i++;
        }
        //拼接角色代码
        for (WebTaskRoleInfo info : list) {
            roleCode += "," + info.getRoleCode();
        }
        if (roleCode.length() > 0) {
            roleCode = roleCode.substring(1);
        }
        return roleCode;
    }

    /**
     * TODO 获取启动流程审批人的角色代码.
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年4月27日    	  杜旭    新建
     *                   </pre>
     */
    @Override
    public String getAppUserRoleByAttr(String processDefinitionKey, WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        String roleCode = "";
        webTaskRoleInfo.setOrganLevel(WebContextUtil.getSessionOrgan().getOrganlevel());//当前机构的机构级别

        //如果流程定义id为空时
        //通过指定的流程key获取最新的流程定义信息
        if (!StringUtils.isNotEmpty(webTaskRoleInfo.getProcDefId())) {
            ProcessDefinition processDefinition = workFlowService.getProcessDefinitionList(processDefinitionKey).get(0);
            webTaskRoleInfo.setProcDefId(processDefinition.getId());
        }
        //获取角色编码
        List<WebTaskRoleInfo> list = webTaskRoleInfoMapper.selectByAttr(webTaskRoleInfo);
        //拼接角色代码
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
        //删除该流程下数据
        webTaskRoleInfoMapper.deleteByProcDefId(procDefId);
        //批量更新该流程下角色配置信息
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
        //删除该流程下数据
        webTaskRoleInfoMapper.deleteByProcDefId(procDefId);
        //批量更新该流程下角色配置信息
        if (webTaskRoleInfo_list.size() > 0) {
            webTaskRoleInfoMapper.insertBatch(webTaskRoleInfo_list);
        }
    }
}