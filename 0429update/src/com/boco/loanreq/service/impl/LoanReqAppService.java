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
 * @Date 2019/11/14 ����8:16
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
        //ҵ�����
        varMap.put("businessKey", reqId + "");
        //��һ������
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

        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processDefinitionKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(500);
            task = workFlowService.getTaskByPid(pi.getId());
            i++;
        }
        workFlowService.claim(task.getId(), assignee);
        //�����Ŵ������¼״̬
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(reqId);
        tbReqDetail.setReqdOrgan(organCode);
        tbReqDetail.setReqdState(LoanStateEnums.STATE_APPROVING.status);
        tbReqDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbReqDetail);
        TbReqList tbReqList = tbReqListMapper.selectByPK(reqId);
        String url = "TbLoanReqPendingApp/listReqDetailAuditUI.htm?reqid=" + reqId + "&taskid=" + task.getId();
        //��¼������Ϣ
        saveMsg(msgNo, operCode, assignee, url, reqId + "_" + organCode, tbReqList.getReqName()+" ���ͽ�ֹʱ�䣺"+tbReqList.getReqDateEnd());
        return result.success(workFlowCode, "�����Ŵ������������������ɹ�");
    }

    /**
     * ���ύ���Ŵ�����
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
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //��ѯ��¼�û���������
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
        //��ѯ��ǰ����ڵ����һ���ڵ�
        //��Ƹñ��ԭ�������ڲ�Ʒ��Ƶ��������ͼ�д��ڶ�������̣�δ�����Ҳ�����һ���ڵ�
        Map<String, Object> map = new HashMap<String, Object>();
        WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
        //��ǰ����ڵ�
        webTaskRoleInfoNew.setTaskId(task.getTaskDefinitionKey());
        //���̶���id
        webTaskRoleInfoNew.setProcDefId(task.getProcessDefinitionId());
        //��Ų�Ʒ�Ļ��������ж������еĻ��Ƿ��е�
        webTaskRoleInfoNew.setOrganLevel(String.valueOf(variables.get("organLevel")));
        //��Ʒ��Ͷ��������
        webTaskRoleInfoNew.setCustType("0".equals(custType) ? "1" : custType);
        //��ѯ��һ���ڵ�
        List<WebTaskRoleInfoNew> roleInfoList = webTaskRoleInfoNewService.selectByAttr(webTaskRoleInfoNew);
        if (roleInfoList.size() > 0) {
            //����ȡ����Ϊÿ�������ڵ㶼����һ���ڵ�
            webTaskRoleInfoNew = roleInfoList.get(0);
        }
        String upTaskId = webTaskRoleInfoNew.getUpTaskId();
        map.put("upTaskId", upTaskId);
        String returnAssginee = "";
        //�ж���һ���׶��ǲ��ǿ�ʼ�ڵ㣬������ǵĻ�����ȡ��һ���ڵ�������ˣ�����ǰ��Ϣ���ظ���
        //����ʷ������л���ڶ������ݣ����Ҫ�����ݽ�������Ȼ���ȡ���µļ�¼
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
            //�����һ���ڵ��ǿ�ʼ�ڵ㣬��Ҫ���ü�¼���ظ��ύ�ˣ������̱����л�ȡ������
            returnAssginee = String.valueOf(variables.get("startUser"));
            map.put("assignee", returnAssginee + ":reSubmit");
            map.put("assigneeByWebMsg", returnAssginee);
        }
        return map;
    }


    private void deleteMsg(String planId) throws Exception {
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("��������" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String reqId, String reqName) throws Exception {

        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        webMsg.setOperName(reqName);
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("��������" + reqId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=") + 1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);
        msg.setAppRoleName("");
        msg.setAppOrganCode(WebContextUtil.getSessionOrgan().getThiscode());
        msg.setAppOrganName(WebContextUtil.getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("��������" + reqId);
        webMsgService.insertEntity(msg);
    }


    @Override
    public ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
        //��ȡ�Ƿ�ͬ��
        String isAgree = (String) varMap.get("isAgree");
        //��ȡ��ǰ�����Ӧ������ʵ��
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //��ȡ��ǰ����ʵ����Ӧ������ʵ��id
            //��װ�µ�����ʵ��id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }
        TbReqDetail detail = new TbReqDetail();
        detail.setReqdRefId(Integer.parseInt(msgMap.get("reqId").toString()));
        detail.setReqdOrgan(msgMap.get("organCode").toString());
        if ("0".equals(isAgree)) {//����
            detail.setReqdState(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("reqId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("Exclusive Gateway") || "ExclusiveGateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("ExclusiveGateway")) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);


        return processInstance;

    }

    @Override
    public void completeTask(ProcessInstance processInstance, Map<String, Object> varMap, Map msgMap) throws Exception {

        String isAgree = (String) varMap.get("isAgree");
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(500);
            task = workFlowService.getTaskByPid(processInstance.getId());
            i++;
        }
        if ("0".equals(isAgree)) {//
            //��ȡ���̷�����
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
