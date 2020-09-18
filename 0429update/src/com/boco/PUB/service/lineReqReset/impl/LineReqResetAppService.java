package com.boco.PUB.service.lineReqReset.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.lineReqReset.ILineReqResetAppService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.TbLineReqresetDetailMapper;
import com.boco.SYS.mapper.TbReqDetailMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LineReqAppService
 * @Description ���������¼
 * @Author txn
 * @Date 2019/11/14 ����8:16
 * @Version 2.0
 **/
@Service
public class LineReqResetAppService implements ILineReqResetAppService {

    public static Logger logger = Logger.getLogger(LineReqResetAppService.class);

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private TbReqresetListMapper tbReqresetListMapper;
    @Autowired
    TbLineReqresetDetailMapper tbLineReqresetDetailMapper;
    @Autowired
    IActHiTaskinstService actHiTaskinstService;
    @Autowired
    IWebTaskRoleInfoNewService webTaskRoleInfoNewService;


    @Override
    public PlainResult<String> startLoanReqAuditProcess(int lineReqresetId, String organCode, String operCode, String operName, String assignee,String comment) throws Exception {
        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", lineReqresetId + "");
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("custType", "1");
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        String processDefinitionKey = "";
        if (Integer.parseInt(WebContextUtil.getSessionOrgan().getOrganlevel()) == 0) {
            processDefinitionKey = AuditMix.REQRESET_TOTAL_LINE_KEY;
        } else if (Integer.parseInt(WebContextUtil.getSessionOrgan().getOrganlevel()) == 1) {
            processDefinitionKey = AuditMix.REQRESET_ONE_LINE_KEY;
        }

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

        logger.info("�Ŵ������������̱��workFlowCode��" + workFlowCode + "��");

        //�����Ŵ������¼״̬
        TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
        tbLineReqresetDetail.setLineReqresetId(lineReqresetId);
        tbLineReqresetDetail.setLineState(LoanStateEnums.STATE_APPROVING.status);
        tbLineReqresetDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbLineReqresetDetail);

        //TODO
        String url = "TbLineReqResetPendingApp/listReqDetailAuditUI.htm?linereqresetid=" + lineReqresetId + "&taskid=" + task.getId();
        //��¼������Ϣ
        saveMsg(msgNo, operCode, assignee, url, lineReqresetId + "");
        return result.success(workFlowCode, "�������������ɹ�");
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
        List<Map<String, Object>> list = tbLineReqresetDetailMapper.getAuditRecordHist(map);
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
    public List<Map<String, Object>> getPendingAppReq(String organLevel, WebOperInfo sessionOperInfo, String auditStatus, Pager pager) throws Exception {
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //��ѯ��¼�û���������
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        String processDefinitionKey = "";
        if ("0".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQRESET_TOTAL_LINE_KEY;
        } else if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.REQRESET_ONE_LINE_KEY;
        } else if ("2".equals(organLevel)) {

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
            return tbLineReqresetDetailMapper.getPendingAppReq(map);
        } else {
            return null;
        }

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

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String lineReqresetId) throws Exception {
        String reqName =tbReqresetListMapper.selectByPK(tbLineReqresetDetailMapper.selectByPK(Integer.valueOf(lineReqresetId)).getLineResetrefId()).getReqresetName();

        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�����������","MSG_TYPE"));
        webMsg.setOperName(reqName);
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�������������"+lineReqresetId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
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
        msg.setOperDescribe("�������������" + lineReqresetId);
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
        TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
        tbLineReqresetDetail.setLineReqresetId(Integer.parseInt(msgMap.get("lineReqresetId").toString()));
        if ("0".equals(isAgree)) {//����
            tbLineReqresetDetail.setLineState(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("lineReqresetId"), taskId, (String) msgMap.get("custType"));
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
            tbLineReqresetDetail.setLineState(TbReqDetail.STATE_APPROVALING);
            if ("true".equals((String) msgMap.get("lastUserType"))) {//
                tbLineReqresetDetail.setLineState(TbReqDetail.STATE_APPROVED);
            }
        }
        tbLineReqresetDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqresetDetailMapper.updateReqDetailByReqdRefIdAndOrganCode(tbLineReqresetDetail);

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);

        return processInstance;
    }
    @Override
    public void completeTask(ProcessInstance  processInstance, Map<String, Object> varMap, Map msgMap) throws Exception {
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
            String opercode = tbLineReqresetDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String lineReqresetId = String.valueOf(msgMap.get("lineReqresetId"));
        String url = "";
        if ("0".equals(isAgree)) {
            assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            url = "TbLineReqResetReject/loanReqResubmitAuditUI.htm?lineReqresetId=" + lineReqresetId + "&taskid=" + task.getId();
        } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
            url = "TbLineReqResetPendingApp/listReqDetailAuditUI.htm?linereqresetid=" + lineReqresetId + "&taskid=" + task.getId();
        }
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("�����������", "MSG_TYPE"));
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("�������������" + lineReqresetId);
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
            }
        } else {
            saveMsg(msgNo, operCode, assignee, url, lineReqresetId + "");
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

        List<Map<String, Object>> list = tbLineReqresetDetailMapper.getApprovedRecord(map);

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
