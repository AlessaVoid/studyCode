package com.boco.loanreq.service.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.TbReqListMapper;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.loanreq.service.ILoanReqAppService;
import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoanReqAppService
 * @Description TODO
 * @Author txn
 * @Date 2019/11/14 下午8:16
 * @Version 2.0
 **/
@Service
public class LoanReqAppService implements ILoanReqAppService {

    public static Logger logger = Logger.getLogger(LoanReqAppService.class);

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    TbReqDetailMapper tbReqDetailMapper;
    @Autowired
    IActHiTaskinstService actHiTaskinstService;
    @Autowired
    IWebTaskRoleInfoNewService webTaskRoleInfoNewService;
    @Autowired
    TbReqListMapper tbReqListMapper;


    @Override
    public PlainResult<String> startLoanReqAuditProcess(int reqId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", reqId + "");
        //下一审批人
        varMap.put("msgNo", msgNo);
        varMap.put("custType", "1");
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        String processDefinitionKey = "";
        if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQ_ONE_MECH_KEY;
        } else {
            processDefinitionKey = AuditMix.REQ_TWO_MECH_KEY;
        }

        //用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processDefinitionKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //提交第一个任务
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //获取最新的任务，并将任务执行对应审批人员
        task = workFlowService.getTaskByPid(pi.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(500);
            task = workFlowService.getTaskByPid(pi.getId());
            i++;
        }
        workFlowService.claim(task.getId(), assignee);
        //更新信贷需求记录状态
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(reqId);
        tbReqDetail.setReqdOrgan(organCode);
        tbReqDetail.setReqdState(LoanStateEnums.STATE_APPROVING.status);
        tbReqDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbReqDetail);
        TbReqList tbReqList = tbReqListMapper.selectByPK(reqId);
        String url = "TbLoanReqPendingApp/listReqDetailAuditUI.htm?reqid=" + reqId + "&taskid=" + task.getId();
        //记录审批信息
        saveMsg(msgNo, operCode, assignee, url, reqId + "_" + organCode, tbReqList.getReqName()+" 报送截止时间："+tbReqList.getReqDateEnd());
        return result.success(workFlowCode, "启动信贷需求审批流程启动成功");
    }

    /**
     * 已提交的信贷需求
     **/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", sessionOperInfo.getOperCode());
        map.put("organCode", sessionOperInfo.getOrganCode());
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }

        List<Map<String, Object>> list = tbReqDetailMapper.getAuditRecordHist(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskId", "");
            } else {
                map1.put("taskId", task.getId());
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getPendingAppReq(String type, WebOperInfo sessionOperInfo, String auditStatus, Pager pager) throws Exception {
        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //查询登录用户待办任务
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        String processDefinitionKey = "";
        if ("0".equals(organLevel)) {
        } else if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQ_ONE_MECH_KEY;
        } else {
            processDefinitionKey = AuditMix.REQ_TWO_MECH_KEY;
        }

        rows = workFlowService.getPersonalTaskPage(processDefinitionKey, pager);
        if (rows != null && rows.size() > 0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", sessionOperInfo.getOperCode());
            map.put("organCode", sessionOperInfo.getOrganCode());
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            return tbReqDetailMapper.getPendingAppReq(map);
        } else {
            return null;
        }

    }

    @Override
    public PlainResult<String> auditLoanReqRequest(HttpServletRequest request, String operCode, String thiscode, String operName) {


        return null;
    }

    @Override
    public Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables) throws Exception {
        //查询当前任务节点的上一个节点
        //设计该表的原因：由于在产品设计的设计流程图中存在多个子流程，未避免找不到上一个节点
        Map<String, Object> map = new HashMap<String, Object>();
        WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
        //当前任务节点
        webTaskRoleInfoNew.setTaskId(task.getTaskDefinitionKey());
        //流程定义id
        webTaskRoleInfoNew.setProcDefId(task.getProcessDefinitionId());
        //存放产品的机构级别，判断是总行的还是分行的
        webTaskRoleInfoNew.setOrganLevel(String.valueOf(variables.get("organLevel")));
        //产品的投资者类型
        webTaskRoleInfoNew.setCustType("0".equals(custType) ? "1" : custType);
        //查询上一个节点
        List<WebTaskRoleInfoNew> roleInfoList = webTaskRoleInfoNewService.selectByAttr(webTaskRoleInfoNew);
        if (roleInfoList.size() > 0) {
            //这样取是因为每个审批节点都有上一个节点
            webTaskRoleInfoNew = roleInfoList.get(0);
        }
        String upTaskId = webTaskRoleInfoNew.getUpTaskId();
        map.put("upTaskId", upTaskId);
        String returnAssginee = "";
        //判断上一个阶段是不是开始节点，如果不是的话，获取上一个节点的审批人，将当前信息驳回给他
        //在历史任务表中会存在多条数据，因此要将数据进行排序，然后获取最新的记录
        if (!"startevent1".equals(upTaskId)) {
            ActHiTaskinst actHiTaskinst = new ActHiTaskinst();
            actHiTaskinst.setProcInstId(task.getProcessInstanceId());
            actHiTaskinst.setTaskDefKey(upTaskId);
            actHiTaskinst.setProcDefId(task.getProcessDefinitionId());
            actHiTaskinst.setSortColumn("START_TIME_ desc");
            List<ActHiTaskinst> hitaskInstList = actHiTaskinstService.selectByAttr(actHiTaskinst);
            if (hitaskInstList.size() > 0) {
                actHiTaskinst = hitaskInstList.get(0);
            }
            returnAssginee = String.valueOf(actHiTaskinst.getAssignee());
            map.put("assignee", returnAssginee);
            map.put("assigneeByWebMsg", returnAssginee.replace(":audit", ""));
        } else {
            //如果上一个节点是开始节点，则要将该记录返回给提交人，从流程变量中获取发起人
            returnAssginee = String.valueOf(variables.get("startUser"));
            map.put("assignee", returnAssginee + ":reSubmit");
            map.put("assigneeByWebMsg", returnAssginee);
        }
        return map;
    }


    private void deleteMsg(String planId) throws Exception {
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("机构需求", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("机构需求：" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String reqId, String reqName) throws Exception {

        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("机构需求", "MSG_TYPE"));
        webMsg.setOperName(reqName);
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("机构需求：" + reqId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        //获取当前操作人姓名及操作状态
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=") + 1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);
        msg.setAppRoleName("");
        msg.setAppOrganCode(WebContextUtil.getSessionOrgan().getThiscode());
        msg.setAppOrganName(WebContextUtil.getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("机构需求", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("机构需求：" + reqId);
        webMsgService.insertEntity(msg);
    }


    @Override
    public ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
        //获取是否同意
        String isAgree = (String) varMap.get("isAgree");
        //获取当前任务对应的流程实例
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //获取当前流程实例对应的流程实例id
            //封装新的流程实例id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }
        TbReqDetail detail = new TbReqDetail();
        detail.setReqdRefId(Integer.parseInt(msgMap.get("reqId").toString()));
        detail.setReqdOrgan(msgMap.get("organCode").toString());
        if ("0".equals(isAgree)) {//驳回
            detail.setReqdState(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("reqId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //如果是网关的话，通过网关获取下一个节点的名称
                    if ("Exclusive Gateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("Exclusive Gateway") || "ExclusiveGateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("ExclusiveGateway")) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //如果连接的下一个节点的名称为End，则返回true
                            }
                        }
                    }
                }
            }
            detail.setReqdState(TbReqDetail.STATE_APPROVALING);
            if ("true".equals((String) msgMap.get("lastUserType"))) {//
                detail.setReqdState(TbReqDetail.STATE_APPROVED);
            }
        }
        detail.setReqdUpdateTime(BocoUtils.getTime());
        tbReqDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(detail);

        //流转任务
        workFlowService.completeTask(taskId, comment, varMap);


        return processInstance;

    }

    @Override
    public void completeTask(ProcessInstance processInstance, Map<String, Object> varMap, Map msgMap) throws Exception {

        String isAgree = (String) varMap.get("isAgree");
        //获取最新的任务，并将任务执行对应审批人员
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(500);
            task = workFlowService.getTaskByPid(processInstance.getId());
            i++;
        }
        if ("0".equals(isAgree)) {//
            //获取流程发起人
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String reqId = String.valueOf(msgMap.get("reqId"));
        String url = "";
        if ("0".equals(isAgree)) {
            assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            url = "TbLoanReqReject/loanReqResubmitAuditUI.htm?reqid=" + reqId + "&taskid=" + task.getId();
        } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
            url = "TbLoanReqPendingApp/listReqDetailAuditUI.htm?reqid=" + reqId + "&taskid=" + task.getId();
        }
        TbReqList tbReqList = tbReqListMapper.selectByPK(Integer.valueOf(reqId));
        String organCode = msgMap.get("organCode").toString();
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {

            deleteMsg(reqId + "_" + organCode);
        } else {
            saveMsg(msgNo, operCode, assignee, url, reqId + "_" + organCode, tbReqList.getReqName());
        }

    }

    @Override
    public List<Map<String, Object>> getApprovedRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("opercode", sessionOperInfo.getOperCode());
        map.put("organCode", sessionOperInfo.getOrganCode());
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }

        List<Map<String, Object>> list = tbReqDetailMapper.getApprovedRecord(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());
            }
        }
        return list;
    }
}
