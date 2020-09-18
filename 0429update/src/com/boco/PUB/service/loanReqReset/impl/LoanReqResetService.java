package com.boco.PUB.service.loanReqReset.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PUB.service.loanReqReset.ILoanReqResetService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.mapper.TbReqresetDetailMapper;
import com.boco.SYS.mapper.TbReqresetListMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
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
 * @Author daice
 * @Date 2019/11/14 ����8:16
 * @Version 2.0
 **/
@Service
public class LoanReqResetService implements ILoanReqResetService {

    public static Logger logger = Logger.getLogger(LoanReqResetService.class);

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private  TbReqresetListMapper tbReqresetListMapper;
    @Autowired
    private TbReqresetDetailMapper tbReqresetDetailMapper;
    @Autowired
    private IActHiTaskinstService actHiTaskinstService;
    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;


    @Override
    public PlainResult<String> startLoanReqAuditProcess(int reqresetId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", reqresetId + "");
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("custType", "1");
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        String processDefinitionKey = "";
        if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQRESET_ONE_MECH_KEY;
        } else {
            processDefinitionKey = AuditMix.REQRESET_TWO_MECH_KEY;
        }


        ProcessInstance pi = workFlowService.startProcess(processDefinitionKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(500);
            task = workFlowService.getTaskByPid(pi.getId());
            i++;
        }
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("������������������̱��workFlowCode��" + workFlowCode + "��");

        //����������������¼״̬
        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        tbReqresetDetail.setReqdresetRefId(reqresetId);
        tbReqresetDetail.setReqdresetOrgan(organCode);
        tbReqresetDetail.setReqdresetState(LoanStateEnums.STATE_APPROVING.status);
        tbReqresetDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbReqresetDetail);
        TbReqresetList tbReqresetList = tbReqresetListMapper.selectByPK(reqresetId);
        String url = "TbReqresetPendingApp/listReqDetailAuditUI.htm?reqresetId=" + reqresetId + "&taskid=" + task.getId();
        //��¼������Ϣ
        saveMsg(msgNo, operCode, assignee, url, reqresetId + "_" + organCode,tbReqresetList.getReqresetName());
        return result.success(workFlowCode, "�������������ɹ�");
    }

    /**
     * ���ύ�������������
     **/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", sessionOperInfo.getOperCode());
        map.put("organCode", sessionOperInfo.getOrganCode());
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }

        List<Map<String, Object>> list = tbReqresetDetailMapper.getAuditRecordHist(map);

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

    /**
     * @param type        0:reject;1:pending
     * @param auditStatus
     * @param pager
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> getPendingAppReq(String type, WebOperInfo sessionOperInfo, String auditStatus, Pager pager) throws Exception {
        //��ѯ��¼�û���������

        String processDefinitionKey = "";
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
        } else if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQRESET_ONE_MECH_KEY;
        } else {
            processDefinitionKey = AuditMix.REQRESET_TWO_MECH_KEY;
        }

        List<Map<String, Object>> rows = workFlowService.getPersonalTaskPage(processDefinitionKey, pager);
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
            List<Map<String, Object>> list = tbReqresetDetailMapper.getPendingAppReq(map);
            return list;
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

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String reqresetId,String reqName) throws Exception {
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�����������", "MSG_TYPE"));
        webMsg.setOperName(reqName);
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�������������" + reqresetId);
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
        msg.setMsgType(DicCache.getKeyByName_("�����������", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("�������������" + reqresetId);
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
        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbReqresetDetail.setReqdresetState(TbReqresetDetail.STATE_APPROVED);
        tbReqresetDetail.setReqdresetRefId(Integer.parseInt(msgMap.get("reqresetId").toString()));
        if ("0".equals(isAgree)) {//����
            tbReqresetDetail.setReqdresetState(TbReqresetDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("reqresetId"), taskId, (String) msgMap.get("custType"));
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
            if (!"true".equals((String) msgMap.get("lastUserType"))) {//
                tbReqresetDetail.setReqdresetState(TbReqresetDetail.STATE_APPROVALING);
            }
        }
        tbReqresetDetail.setReqdresetOrgan(String.valueOf(msgMap.get("organCode")));
        tbReqresetDetail.setReqdresetUpdatetime(BocoUtils.getTime());
        tbReqresetDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbReqresetDetail);

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

            String opercode = tbReqresetDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String reqresetId = String.valueOf(msgMap.get("reqresetId"));
        String organCode = msgMap.get("organCode").toString();
       String reqName = tbReqresetListMapper.selectByPK(Integer.valueOf(reqresetId)).getReqresetName();
        String url = "";
        if ("0".equals(isAgree)) {
            assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            url = "TbReqresetReject/loanReqResubmitAuditUI.htm?reqresetId=" + reqresetId + "&taskid=" + task.getId();
        } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
            url = "TbReqresetPendingApp/listReqDetailAuditUI.htm?reqresetId=" + reqresetId + "&taskid=" + task.getId();
        }
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("�����������", "MSG_TYPE"));
            webMsg.setOperName(reqName);
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("�������������" + reqresetId + "_" + organCode);
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
            }
        } else {
            saveMsg(msgNo, operCode, assignee, url, reqresetId + "_" + organCode,reqName);
        }

    }

    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;

    @Override
    public List<Map<String, Object>> getApprovedRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("opercode", sessionOperInfo.getOperCode());
        map.put("organCode", sessionOperInfo.getOrganCode());
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        List<Map<String, Object>> list = tbReqresetDetailMapper.getApprovedRecord(map);

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
