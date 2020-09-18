package com.boco.PUB.service.loanPlan.Impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.loanPlan.TbPlanDetailBackupService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.*;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import static com.boco.SYS.util.WebContextUtil.getSessionOrgan;

/**
 * @Author: liujinbo
 * @Description: �Ŵ��ƻ�service
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Service
public class TbPlanServiceImpl extends GenericService<TbPlan, String> implements TbPlanService  {

    public static Logger logger = Logger.getLogger(TbPlanServiceImpl.class);

    @Autowired
    private IWorkFlowService workFlowService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IWebMsgService webMsgService;

    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;

    @Autowired
    private TbPlanMapper tbplanMapper;

    @Autowired
    private IActHiTaskinstService actHiTaskinstService;

    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;

    @Autowired
    private FdOrganMapper fdOrganMapper;

    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private TbPlanadjMapper tbPlanadjMapper;

    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private LoanPlanDetailMapper loanPlanDetailMapper;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailBackupService tbPlanDetailBackupService;


    /*�ƻ��ϴ���ַ*/
    @Value("${upload.path}")
    private String uploadPath;

    /*���޶���ϴ���ַ*/
    @Value("${upload.quota.path}")
    private String uploadQuotaPath;



    /**
     * ���ύ���Ŵ��ƻ�
     **/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo, Integer planType, String sort) throws Exception {


        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth", reqMonth);
        map.put("planType", planType);
        map.put("sort", sort);

        List<Map<String, Object>> list = tbplanMapper.getAuditRecordHist(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());

            }
        }

        for (Map<String, Object> map2 : list) {
            map2.put("planincrement", new BigDecimal(map2.get("planincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planrealincrement", new BigDecimal(map2.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
        }

        return list;
    }

    /*��ѯ���������Ŵ��ƻ�*/
    @Override
    public List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth, Integer planType) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth", reqMonth);
        map.put("planType", planType);
        map.put("sort", sort);

        List<Map<String, Object>> list = tbplanMapper.getApprovedRecord(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());
            }
        }

        for (Map<String, Object> map2 : list) {
            map2.put("planincrement", new BigDecimal(map2.get("planincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planrealincrement", new BigDecimal(map2.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
        }

        return list;
    }

    /*�����Ŵ��ƻ���������*/
    @Override
    public PlainResult<String> startLoanPlanAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_MECH;
        }

        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", planId);
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("�����ƻ��������̱��workFlowCode��" + workFlowCode + "��");

        //�����Ŵ��ƻ���¼״̬
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(planId);
        tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);
        //��¼������Ϣ
        String url = "tbPlanPendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, planId);
        return result.success(workFlowCode, "���������ƻ��������������ɹ�");
    }

    /*�����Ŵ�������������*/
    @Override
    public PlainResult<String> startLoanplanStripeAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_LINE;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_LINE;
        }


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", planId);
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("���߼ƻ��������̱��workFlowCode��" + workFlowCode + "��");

        //�����Ŵ��ƻ���¼״̬
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(planId);
        tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);
        //��¼������Ϣ
        String url = "tbPlanStripePendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
        saveMsgPlanStripe(msgNo, operCode, assignee, url, planId);
        return result.success(workFlowCode, "�������߼ƻ��������������ɹ�");
    }

    /*��ѯ���������Ŵ��ƻ�*/
    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception {
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //��ѯ��¼�û���������
        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_MECH;
        }
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        rows = workFlowService.getPersonalTaskPage(processKey, pager);
        if (rows != null && rows.size() > 0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", operCode);
            map.put("reqMonth", reqMonth);
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            map.put("planType", planType);
            map.put("sort", sort);
            List<Map<String, Object>> list = tbplanMapper.getPendingAppReq(map);
            for (Map<String, Object> map2 : list) {
                map2.put("planincrement", new BigDecimal(map2.get("planincrement").toString()).divide(new BigDecimal("10000")));
                map2.put("planrealincrement", new BigDecimal(map2.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
            }
            return list;
        } else {
            return null;
        }
    }

    /*��ѯ�����������߼ƻ�*/
    @Override
    public List<Map<String, Object>> getPendingPlanStripe(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception {
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //��ѯ��¼�û���������
        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_LINE;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_LINE;
        }
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        rows = workFlowService.getPersonalTaskPage(processKey, pager);
        if (rows != null && rows.size() > 0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", operCode);
            map.put("reqMonth", reqMonth);
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            map.put("planType", planType);
            map.put("sort", sort);
            List<Map<String, Object>> list = tbplanMapper.getPendingAppReq(map);
            for (Map<String, Object> map2 : list) {
                map2.put("planincrement", new BigDecimal(map2.get("planincrement").toString()).divide(new BigDecimal("10000")));
                map2.put("planrealincrement", new BigDecimal(map2.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
            }
            return list;
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

    @Override
    public void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
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
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(msgMap.get("planId").toString());
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//����
            tbPlanDO.setPlanStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("planId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
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
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //��ȡ���̷�����
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //��¼������Ϣ
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planId = String.valueOf(msgMap.get("planId"));

        if("1".equals(isAgree)&&"true".equals((String) msgMap.get("lastUserType"))){
            deleteMsg(planId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //��ȡ���̷�����
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbPlanReject/loanTbPlanResubmitAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            } else if(!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbPlanPendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, planId + "");
        }


        //�¶ȼƻ�������ɣ������³��ƻ�����
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String)msgMap.get("lastUserType")))) {
            tbPlanDetailBackupService.addPlanDetailBackup(planId);
        }
    }

    @Override
    public void completeTaskAuditPlanStripe(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
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
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(msgMap.get("planId").toString());
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//����
            tbPlanDO.setPlanStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("planId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
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
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //��ȡ���̷�����
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //��¼������Ϣ
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planId = String.valueOf(msgMap.get("planId"));

        if("1".equals(isAgree)&&"true".equals((String) msgMap.get("lastUserType"))){
            deleteMsgPlanStripe(planId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //��ȡ���̷�����
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
                url = "tbPlanStripeReject/loanTbPlanResubmitAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            } else if(!"true".equals((String) msgMap.get("lastUserType"))){
                url = "tbPlanStripePendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            }
            //��¼������Ϣ
            saveMsgPlanStripe(msgNo, operCode, assignee, url, planId + "");
        }
    }


    /*¼��ƻ��ļ�*/
    @Override
    public Map<String, String> enterReportPlanByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "¼��ƻ��ɹ���");
        InputStream is = null;
        try {
            //-------������-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime()+ "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //�ж�¼��ļƻ��ļ��Ƿ���ȷ
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname()+"�ƶ������ƻ��������";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }

            //��ȡ���������
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);


            //��ѯ���ݿ�������
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //�ѱ��Ĵ�����ϸ�ʽ���������жϱ���������Ƿ���������ݿ���
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode)||"".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "������ϡ�"+cellValues.get(i)+"�������ݿ��в����ڣ����飡");
                        return resultMap;
                    }
                }
                cells[i] =combCode ;
            }

            //��ȡ�����������
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //-------�������-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            //У�������ֶηǿ�
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��ѡ���·ݻ�λ��");
                return resultMap;
            }

            // У��Ψһ��
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.PLAN);
            List<TbPlan> tbPlanListCheck = tbplanMapper.selectByAttr(tbPlanParam);
            // ���ƻ�����ʱ������ δ�ύ �� ���� ״̬�����¼ƻ�
            String planId = IDGeneratorUtils.getSequence();
            Integer planStatus = new Integer(TbReqDetail.STATE_NEW);
            String planCreateTime = BocoUtils.getTime();
            if (CollectionUtils.isNotEmpty(tbPlanListCheck)) {
                TbPlan tbPlan = tbPlanListCheck.get(0);
                if (TbReqDetail.STATE_DISMISSED == tbPlan.getPlanStatus().intValue()) {
                    planId = tbPlan.getPlanId();
                    planStatus = tbPlan.getPlanStatus();
                    planCreateTime = tbPlan.getPlanCreateTime();
                } else if (TbReqDetail.STATE_NEW == tbPlan.getPlanStatus().intValue()) {
                    planId = tbPlan.getPlanId();
                    planStatus = tbPlan.getPlanStatus();
                    planCreateTime = tbPlan.getPlanCreateTime();
                } else if (TbReqDetail.STATE_APPROVALING == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "���·ݻ����ƻ��������������ܵ��룡");
                    return resultMap;
                }else if (TbReqDetail.STATE_APPROVED == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "���·ݻ����ƻ��Ѿ�������ɣ����ܵ��룡");
                    return resultMap;
                }
            }

            //���� ���ߺͼƻ�ֻ���ƶ�һ��
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.STRIPE);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "�����·��Ѿ��ƶ����߼ƻ����������ƶ������ƻ�");
                    return resultMap;
                }
            }



            //�����ƻ�
            //��ѯ���ּ���
            LoanCombDO loanComb = loanCombMapper.getLoanCombInfoByCombCode(cells[1]);
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanOrgan(organCode);
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOper(operCode);
            tbPlan.setPlanStatus(planStatus);
            BigDecimal planIncreament = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncreament = planIncreament.multiply(new BigDecimal("10000"));
            tbPlan.setPlanIncrement(planIncreament);
            tbPlan.setPlanCreateTime(planCreateTime);
            tbPlan.setPlanUnit(Integer.parseInt(planUnit));
            tbPlan.setCombLevel(loanComb.getCombLevel());
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanType(TbPlan.PLAN);


            //��ѯ�������
            int combLevel = loanComb.getCombLevel();
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            if (1==combLevel) {
                combList = loanCombMapper.selectComb(combMap);
            } else if (2==combLevel) {
                combList = loanCombMapper.selectCombOfBind(combMap);
            }

            ArrayList<TbPlanDetail> tbPlanDetailList = new ArrayList<>();
            /*�ƻ��ƶ�������*/
            BigDecimal planRealIncrement = new BigDecimal("0");
            //�����ƻ�����
            for (Map<String, Object> map : maps) {
                String organ = findCode(map.get("organ").toString());
                if (!"".equals(organ)) {
                    for (Map<String, Object> comb : combList) {
                        String combCode = comb.get("combcode").toString();
                        String amount = map.get(combCode) == null ? "0" : map.get(combCode).toString();
                        BigDecimal planAmount = null;
                        try {
                            planAmount = new BigDecimal("".equals(amount)?"0":amount);
                        } catch (Exception e) {
                            resultMap.put("code", "false");
                            resultMap.put("msg", "����ƻ�ʧ�ܣ���¼����ȷ����ֵ��");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planAmount = planAmount.multiply(new BigDecimal("10000"));
                        }
                        //�����ƻ�����
                        String plandId = IDGeneratorUtils.getSequence();
                        TbPlanDetail tbPlanDetail = new TbPlanDetail();
                        tbPlanDetail.setPdId(plandId);
                        tbPlanDetail.setPdRefId(planId);
                        tbPlanDetail.setPdOrgan(organ);
                        tbPlanDetail.setPdMonth(planMonth);
                        tbPlanDetail.setPdLoanType(combCode);
                        tbPlanDetail.setPdAmount(planAmount);
                        tbPlanDetail.setPdUnit(Integer.parseInt(planUnit));
                        tbPlanDetail.setPdCreateTime(planCreateTime);
                        tbPlanDetail.setPdUpdateTime(BocoUtils.getTime());
                        planRealIncrement = planRealIncrement.add(planAmount);
                        tbPlanDetailList.add(tbPlanDetail);
                    }
                }
            }


            //һ�����мƻ��ƶ���Ҫ���ڼƻ�������
            if ("1".equals(organlevel)) {

                // һ�ִ��ּ���
                int combLevelOne = tbPlan.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);
                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                }
                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "¼��ƻ�ʧ�ܣ�" +combname+"�ƶ�������Ϊ"+realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���ƻ�������Ϊ"
                                +upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���������");

                        return resultMap;
                    }
                }


            }



            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlanMapper.deleteLoanPlanInfo(planId);
            tbplanMapper.insertEntity(tbPlan);


            //���ɾ���ļ�
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("¼��ʧ��");
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /*¼�������ļ�*/
    @Override
    public Map<String, String> enterReportPlanStripeByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "¼��ƻ��ɹ���");
        InputStream is = null;
        try {
            //-------������-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime() + "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //�ж�¼��ļƻ��ļ��Ƿ���ȷ
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname()+"�ƶ����߼ƻ��������";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }

            //��ȡ���������
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);

            //��ѯ���ݿ�������
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //�ѱ��Ĵ�����ϸ�ʽ���������жϱ���������Ƿ���������ݿ���
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode)||"".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "������ϡ�"+cellValues.get(i)+"�������ݿ��в����ڣ����飡");
                        return resultMap;
                    }
                }
                cells[i] =combCode ;
            }

            //��ȡ�����������
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //-------�������-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");

            //У�������ֶηǿ�
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��ѡ���·ݻ�λ��");
                return resultMap;
            }

            //У��Ψһ��
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbplanMapper.selectByAttr(tbPlanParam);
            // ���ƻ�����ʱ������ δ�ύ �� ���� ״̬�����¼ƻ�
            String planId = IDGeneratorUtils.getSequence();
            Integer planStatus = new Integer(TbReqDetail.STATE_NEW);
            String planCreateTime = BocoUtils.getTime();
            if (CollectionUtils.isNotEmpty(tbPlanListCheck)) {
                TbPlan tbPlan = tbPlanListCheck.get(0);
                if (TbReqDetail.STATE_DISMISSED == tbPlan.getPlanStatus().intValue()) {
                    planId = tbPlan.getPlanId();
                    planStatus = tbPlan.getPlanStatus();
                    planCreateTime = tbPlan.getPlanCreateTime();
                } else if (TbReqDetail.STATE_NEW == tbPlan.getPlanStatus().intValue()) {
                    planId = tbPlan.getPlanId();
                    planStatus = tbPlan.getPlanStatus();
                    planCreateTime = tbPlan.getPlanCreateTime();
                } else if (TbReqDetail.STATE_APPROVALING == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "���·����߼ƻ��������������ܵ��룡");
                    return resultMap;
                } else if (TbReqDetail.STATE_APPROVED == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "���·����߼ƻ��Ѿ�������ɣ����ܵ��룡");
                    return resultMap;
                }
            }

            //���� ���ߺͼƻ�ֻ���ƶ�һ��
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "�����·��Ѿ��ƶ������ƻ����������ƶ����߼ƻ�");
                    return resultMap;
                }
            }

            //�����ƻ�
            LoanCombDO loanComb = loanCombMapper.getLoanCombInfoByCombCode(cells[1]);
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanOrgan(organCode);
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOper(operCode);
            tbPlan.setPlanStatus(planStatus);
            BigDecimal planIncreament = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncreament = planIncreament.multiply(new BigDecimal("10000"));
            tbPlan.setPlanIncrement(planIncreament);
            tbPlan.setPlanCreateTime(planCreateTime);
            tbPlan.setPlanUnit(Integer.parseInt(planUnit));
            tbPlan.setCombLevel(loanComb.getCombLevel());
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanType(TbPlan.STRIPE);


            //��ȡ������ϼ��� ���������
            int combLevel = 2;
            //��ѯ�������
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            combList = loanCombService.selectCombOfBind(combMap);


            ArrayList<TbPlanDetail> tbPlanDetailList = new ArrayList<>();
            /*�ƻ��ƶ�������*/
            BigDecimal planRealIncrement = new BigDecimal("0");
            //�����ƻ�����
            for (Map<String, Object> map : maps) {
                String organ = findCode(map.get("organ").toString());
                if (!"".equals(organ)) {
                    for (Map<String, Object> comb : combList) {
                        String combCode = comb.get("combcode").toString();
                        String amount = map.get(combCode) == null ? "0" : map.get(combCode).toString();
                        BigDecimal planAmount = null;
                        try {
                            planAmount = new BigDecimal("".equals(amount)?"0":amount);
                        } catch (Exception e) {
                            resultMap.put("code", "false");
                            resultMap.put("msg", "����ƻ�ʧ�ܣ���¼����ȷ����ֵ��");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planAmount = planAmount.multiply(new BigDecimal("10000"));
                        }
                        //�����ƻ�����
                        String plandId = IDGeneratorUtils.getSequence();
                        TbPlanDetail tbPlanDetail = new TbPlanDetail();
                        tbPlanDetail.setPdId(plandId);
                        tbPlanDetail.setPdRefId(planId);
                        tbPlanDetail.setPdOrgan(organ);
                        tbPlanDetail.setPdMonth(planMonth);
                        tbPlanDetail.setPdLoanType(combCode);
                        tbPlanDetail.setPdAmount(planAmount);
                        tbPlanDetail.setPdUnit(Integer.parseInt(planUnit));
                        tbPlanDetail.setPdCreateTime(planCreateTime);
                        tbPlanDetail.setPdUpdateTime(BocoUtils.getTime());
                        planRealIncrement = planRealIncrement.add(planAmount);
                        tbPlanDetailList.add(tbPlanDetail);
                    }
                }
            }

            //һ�����мƻ��ƶ���Ҫ���ڼƻ�������
            if ("1".equals(organlevel)) {

                // һ�ִ��ּ���
                int combLevelOne = tbPlan.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);
                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                }
                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "¼��ƻ�ʧ�ܣ�" +combname+"�ƶ�������Ϊ"+realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���ƻ�������Ϊ"
                                +upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���������");

                        return resultMap;
                    }
                }
            }

            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlanMapper.deleteLoanPlanInfo(planId);
            tbplanMapper.insertEntity(tbPlan);


            //���ɾ���ļ�
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("¼��ʧ��");
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    @Override
    public void downloadPlanTemplate(HttpServletRequest request, String type, HttpServletResponse response, String organlevel) throws Exception {

        OutputStream os = response.getOutputStream();
        Workbook workbook = null;
        String fileName = "";

        //1-�ƻ�ģ��  2-����ģ��
        if ("1".equals(type)) {

            //��ѯ�������
            String combLevelStr = request.getParameter("combLevel") == null ? "1" : request.getParameter("combLevel");
            int combLevel = Integer.valueOf(combLevelStr);
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            if (1 == combLevel) {
                combList = loanCombMapper.selectComb(combMap);
            } else if (2 == combLevel) {
                combList = loanCombMapper.selectCombOfBind(combMap);
            }

            fileName = getSessionOrgan().getOrganname() + "�ƶ������ƻ��������";

            //��ѯ����
            // List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
            workbook = exportExcel(combList, organList, fileName);


        } else if ("2".equals(type)) {

            //��ȡ������ϼ��� ���������
            int combLevel = 2;
            //��ѯ�������
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            combList = loanCombService.selectCombOfBind(combMap);

            fileName = getSessionOrgan().getOrganname() + "�ƶ����߼ƻ��������";
            //��ѯ����
            List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(getSessionOrgan().getThiscode());
            workbook = exportExcel(combList, organList, fileName);

        }

        //�ļ���
        fileName = fileName + ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie ������ϵͳ��ie�����
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");

        }
        // ���response
        response.reset();
        // response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/octet-stream");

        workbook.write(os);
        os.flush();
        os.close();

    }

    /*ά��ʱ��ƻ��󣬸����Ŵ��ƻ��ļƻ�������*/
    @Override
    public void updatePlanAndPlanadj(TbPlan plan) {
        //���¼ƻ�
        plan.setPlanType(TbPlan.PLAN);
        plan.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updatePlan(plan);
        //���¼ƻ�����
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjOrgan(plan.getPlanOrgan());
        tbPlanadj.setPlanadjMonth(plan.getPlanMonth());
        tbPlanadj.setPlanadjType(plan.getPlanType());
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        tbPlanadjMapper.updatePlanadj(tbPlanadj);
    }

    /*ά��ʱ��ƻ��󣬸������߼ƻ��ļƻ�������*/
    @Override
    public void updatePlanStripeAndPlanadjStripe(TbPlan plan) {
        //���¼ƻ�
        plan.setPlanType(TbPlan.STRIPE);
        plan.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updatePlan(plan);
        //���¼ƻ�����
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjOrgan(plan.getPlanOrgan());
        tbPlanadj.setPlanadjMonth(plan.getPlanMonth());
        tbPlanadj.setPlanadjType(plan.getPlanType());
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        tbPlanadjMapper.updatePlanadj(tbPlanadj);
    }


    private void deleteMsg(String planId) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�����ƻ�����", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�����ƻ�������" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String planId) throws Exception {

        //ɾ����ǰ�����˵�С������Ϣ
        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�����ƻ�����","MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�����ƻ�������"+planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

        /*�����һ�������˵�С������Ϣ*/
        WebMsg msg=new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("�����ƻ�����","MSG_TYPE"));

        TbPlan plan = tbPlanMapper.selectByPK(planId);
        BigDecimal increment = plan.getPlanRealIncrement();
        String unit = "��Ԫ";
        if (plan.getPlanUnit().intValue() == 2) {
            unit = "��Ԫ";
            increment = increment.divide(new BigDecimal("10000"));
        }
        String operName = "�����·ݣ�" + plan.getPlanMonth()
                + " �ƻ���������" + increment.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("�����ƻ�������"+planId);
        webMsgService.insertEntity(msg);
    }


    private void deleteMsgPlanStripe( String planId) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("���߼ƻ�����", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("���߼ƻ�������" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }
    private void saveMsgPlanStripe(String msgNo, String operCode, String assignee, String msgUrl, String planId) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("���߼ƻ�����","MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("���߼ƻ�������"+planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

        WebMsg msg=new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("���߼ƻ�����","MSG_TYPE"));

        TbPlan plan = tbplanMapper.selectByPK(planId);
        BigDecimal increment = plan.getPlanRealIncrement();
        String unit = "��Ԫ";
        if (plan.getPlanUnit().intValue() == 2) {
            unit = "��Ԫ";
            increment = increment.divide(new BigDecimal("10000"));
        }
        String operName = "�����·ݣ�" + plan.getPlanMonth()
                + " �ƻ���������" + increment.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("���߼ƻ�������"+planId);
        webMsgService.insertEntity(msg);
    }


    /*ɾ���ļ�*/
     private boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    /*�ҳ��ַ���[]���������*/
    private  String findCode(String str) {
        String code = "";
        try {
            int begin = str.indexOf("[");
            int end = str.indexOf("]");
            code = str.substring(begin+1, end);
        } catch (Exception e) {
            e.printStackTrace();
            code = "";
        }
        return  code;
    }

    //��̬���ɼƻ��������
    private Workbook exportExcel(List<Map<String, Object>> combList, List<Map<String, Object>> organList, String fileName) {

        Sheet sheet = null;

        // ����һ��excel�ļ���ָ���ļ���
        String filepath = uploadPath+ fileName + ".xls";
        File file = new File(filepath);

        @SuppressWarnings("resource")
        Workbook workbook = new HSSFWorkbook();

        // ����sheet
        sheet = workbook.createSheet("�ƻ�����");
        //���ᴰ��
        sheet.createFreezePane(1, 2, 1, 2);

        // �ϲ���Ԫ�� s CellRangeAddress(��ʼ�к�, �����к�, ��ʵ�к�, �����к�)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, combList.size()));

        // ��������ʽ
        CellStyle titleStyle = workbook.createCellStyle();
        // ˮƽ����
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //��ֱ����
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //��������
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 15);
        titleFont.setBoldweight((short) 100);
        titleStyle.setFont(titleFont);

        /*���õ�Ԫ���Զ�����*/
        CellStyle cellStyle = workbook.createCellStyle();
        //�����Զ�����
        cellStyle.setWrapText(true);
        // ˮƽ����
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);


        // ����������
        Row titleRow = sheet.createRow(0);
        //�����и�
        titleRow.setHeight((short)500);
        titleRow.createCell(0).setCellValue(fileName);
        titleRow.getCell(0).setCellStyle(titleStyle);

        //��������
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellStyle(cellStyle);
        row1.getCell(0).setCellValue("����[organ]");

        for (int i = 0; i < combList.size(); i++) {
            //������������
            Map<String, Object> map = combList.get(i);
            String combname = map.get("combname").toString();
            String combcode = map.get("combcode").toString();
            String comb = combname + "[" + combcode + "]";
            Cell cell = row1.createCell(i + 1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(comb);

            //���ô����п�
            int length = comb.toString().getBytes().length  * 256 + 200;
            //����ѿ��������Ƶ�15000
            if (length>15000){
                length = 15000;
            }
            sheet.setColumnWidth(i+1, length);
        }


// ��������
        int organLength = 200;
        Row row = null;
        for (int i = 0; i < organList.size(); i++) {
            row = sheet.createRow(i + 2);
            Cell cell = row.createCell(0);
            Map<String, Object> map = organList.get(i);
            String organname = map.get("organname").toString();
            String organcode = map.get("organcode").toString();
            String organ = organname + "[" + organcode + "]";
            organLength = Math.max(organLength, organ.getBytes().length * 256 + 200);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(organ);
        }


        //���û����п� ��һ������������id(��0��ʼ),��2������������ֵ
        sheet.setColumnWidth(0, organLength);

        return workbook;

    }

    //�����ϼ����ּ��ܵļƻ��ƶ���
    private Map<String, BigDecimal> comparePlanIncrement(List<TbPlanDetail> tbPlanDetailList, int combLevel) {
        //1.���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            String amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? "0" : combAmountMap.get(tbPlanDetail.getPdLoanType()).toString();
            combAmountMap.put(tbPlanDetail.getPdLoanType(), new BigDecimal(amount).add(tbPlanDetail.getPdAmount()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? new BigDecimal("0") : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }


    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public HttpServletResponse downloadFile(String filePath, HttpServletResponse response) {
        InputStream fis = null;
        try {

            // filePath��ָ�����ص��ļ���·����
            File file = new File(filePath);

            // ȡ���ļ�����
            String fileName = file.getName();
            int i = fileName.lastIndexOf("_-");
            fileName = fileName.substring(i+2);

            // ��������ʽ�����ļ���
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // ���response
            response.reset();
            // ����response��Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

/*���ػ����ƻ�*/
    @Override
    public void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel)throws Exception {
       // ��ȡ�����
        OutputStream os = response.getOutputStream();

        //����������
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //������ʽ
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        /*��ѯ��������*/
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
        Map<String, Object> organTotalMap = new HashMap<>(2);
        organTotalMap.put("organcode", "total");
        organTotalMap.put("organname", "�ϼ�");
        organList.add(organTotalMap);


        /*��ѯ����������*/
        String planId = request.getParameter("planId");
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanId(planId);
        TbPlan plan = tbplanMapper.selectByAttr(tbPlanParam).get(0);
        TbPlanDetail param = new TbPlanDetail();
        param.setPdRefId(planId);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(param);

        //��ѯ�������
        Map<String, Object> combParam = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combParam.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combParam);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combParam);
        }
        Map<String, Object> combTotalMap = new HashMap<>(2);
        combTotalMap.put("combcode", "total");
        combTotalMap.put("combname", "�ϼ�");
        combList.add(combTotalMap);

        //����ϼ�
        for (Map<String, Object> map : combList) {
            String comb = map.get("combcode").toString();
            BigDecimal amout = BigDecimal.ZERO;
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                if (comb.equals(tbPlanDetail.getPdLoanType())) {
                    amout = BigDecimalUtil.add(amout, tbPlanDetail.getPdAmount());
                }
            }
            TbPlanDetail detail = new TbPlanDetail();
            detail.setPdOrgan("total");
            detail.setPdLoanType(comb);
            detail.setPdAmount(amout);
            tbPlanDetails.add(detail);
        }
        for (Map<String, Object> map : organList) {
            String organ = map.get("organcode").toString();
            BigDecimal amout = BigDecimal.ZERO;
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                if (organ.equals(tbPlanDetail.getPdOrgan())) {
                    amout = BigDecimalUtil.add(amout, tbPlanDetail.getPdAmount());
                }
            }
            TbPlanDetail detail = new TbPlanDetail();
            detail.setPdOrgan(organ);
            detail.setPdLoanType("total");
            detail.setPdAmount(amout);
            tbPlanDetails.add(detail);
        }


        /*��װ����*/
        //�ƻ�����
        HashMap<String, TbPlanDetail> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }

        //����
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 3 + i);
        }

        //����
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i <combList.size() ; i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*д���ļ�*/

        String filename = getSessionOrgan().getOrganname() +"-"+ plan.getPlanMonth()+ "-�����ƻ�";
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size());
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //��λ
        POIExportUtil.setCell(sheet, 1, 0, "��λ:" , cellStyle);
        POIExportUtil.setCell(sheet, 1, 1, plan.getPlanUnit()==1?"��Ԫ":"��Ԫ" , cellStyle);

        //����
        POIExportUtil.setCell(sheet, 2, 0, "����" , cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //����
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.setCell(sheet, 2, column, map.get("combname").toString(), cellStyle);
            }
        }
        //����
        for (String key : planMap.keySet()) {
            String[] keys = key.split("_");
            Integer row =organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, column, planMap.get(key).getPdAmount(), cellStyle);
            }
        }

        //�����п�
        for (int i = 0; i <combList.size()+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //��������
        POIExportUtil.createFreezePane(sheet,1,3);

        //�ļ���
         filename = filename + ".xls";
                String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie ������ϵͳ��ie�����
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         ���response
        response.reset();
//        response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }



    /*�������߼ƻ�*/
    @Override
    public void downloadPlanStripe(HttpServletResponse response, HttpServletRequest request, String organlevel)throws Exception {
        // ��ȡ�����
        OutputStream os = response.getOutputStream();

        //����������
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //������ʽ
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        //��ѯ��������
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        /*��ѯ�������� ��������*/
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMapParam = new HashMap<String, Object>();
        combMapParam.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMapParam);
        Map<String, Object> combTotalMap = new HashMap<>(2);
        combTotalMap.put("combcode", "total");
        combTotalMap.put("combname", "�ϼ�");
        combList.add(combTotalMap);



        /*��ѯ����������*/
        String planId = request.getParameter("planId");
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanId(planId);
        TbPlan plan = tbplanMapper.selectByAttr(tbPlanParam).get(0);
        TbPlanDetail param = new TbPlanDetail();
        param.setPdRefId(planId);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(param);

        //����ϼ�
        for (Map<String, Object> map : organList) {
            String organ = map.get("organcode").toString();
            BigDecimal amout = BigDecimal.ZERO;
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                if (organ.equals(tbPlanDetail.getPdOrgan())) {
                    amout = BigDecimalUtil.add(amout, tbPlanDetail.getPdAmount());
                }
            }
            TbPlanDetail detail = new TbPlanDetail();
            detail.setPdOrgan(organ);
            detail.setPdLoanType("total");
            detail.setPdAmount(amout);
            tbPlanDetails.add(detail);
        }

        /*��װ����*/
        //�ƻ�����
        HashMap<String, TbPlanDetail> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }
        //����
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 3 + i);
        }
        //����
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i <combList.size() ; i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*д���ļ�*/

        String filename = getSessionOrgan().getOrganname() +"-"+ plan.getPlanMonth()+ "-���߼ƻ�";
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size());
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //��λ
        POIExportUtil.setCell(sheet, 1, 0, "��λ:" , cellStyle);
        POIExportUtil.setCell(sheet, 1, 1, plan.getPlanUnit()==1?"��Ԫ":"��Ԫ" , cellStyle);

        //����
        POIExportUtil.setCell(sheet, 2, 0, "����:" , cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //����
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.setCell(sheet, 2, column, map.get("combname").toString(), cellStyle);
            }
        }
        //����
        for (String key : planMap.keySet()) {
            String[] keys = key.split("_");
            Integer row =organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, column, planMap.get(key).getPdAmount(), cellStyle);
            }
        }

        //�����п�
        for (int i = 0; i <combList.size()+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //��������
        POIExportUtil.createFreezePane(sheet,1,3);

        //�ļ���
         filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie ������ϵͳ��ie�����
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         ���response
        response.reset();
//        response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }






    public String uploadFile(MultipartFile file) {
        InputStream is = null;
        String originalFilename = null;
        try {
            originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime()+ "_-" + originalFilename;
            File fileExcel = new File(uploadQuotaPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return uploadQuotaPath +originalFilename;
    }


    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }


    public String getUploadQuotaPath() {
        return uploadQuotaPath;
    }

    public void setUploadQuotaPath(String uploadQuotaPath) {
        this.uploadQuotaPath = uploadQuotaPath;
    }

    /**
     * ��ȡ�ƻ�����map  ���ڶ������ִ���Ϊһ������   <upcombCode,amount>
     * @param tbPlanDetailList �ƻ�����list
     * @param combLevel list�Ĵ��ּ���
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanCombMapAndTransCombLevel(List<TbPlanDetail> tbPlanDetailList, int combLevel) {
        //1.��װ��ǰlist ���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanDetail.getPdLoanType());
            combAmountMap.put(tbPlanDetail.getPdLoanType(), amount.add(tbPlanDetail.getPdAmount()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * ��ȡ�ƻ�����map    list--> <upcombCode,amount>
     * @param tbPlanDetailList �ƻ�����list
     * @return <upcomb,amount>
     */
    public    Map<String, BigDecimal> getPlanCombMap(List<TbPlanDetail> tbPlanDetailList) {
        //1.��װ��ǰlist ���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanDetail.getPdLoanType());
            combAmountMap.put(tbPlanDetail.getPdLoanType(), amount.add(tbPlanDetail.getPdAmount()));
        }
        return combAmountMap;
    }

    /**
     * ��ȡ���и��������ƶ��ļƻ�  <combCode,amount>  ���ڴ��ּ��𲻴���
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    public  Map<String, BigDecimal> getUporganCombMap(String planMonth, String upOrganCode, String organCode){
        //�ϼ����ּ��ƻ������� <upcombcode,increment>
        HashMap<String, BigDecimal> upcombIncrementMap = new HashMap<>();
        HashMap<String, Object> planQueryMap = new HashMap<>();
        planQueryMap.put("planMonth", planMonth);
        planQueryMap.put("planType", TbPlan.PLAN);
        planQueryMap.put("upOrganCode", upOrganCode);
        planQueryMap.put("organCode", organCode);
        List<Map<String, Object>> planDetails = tbPlanMapper.selectPlanOrganIncreament(planQueryMap);
        for (Map<String, Object> map : planDetails) {
            upcombIncrementMap.put(map.get("upcombcode").toString(), new BigDecimal(map.get("increment").toString()));
        }
        return upcombIncrementMap;
    }

    /**
     * ��ȡ���и��������ƶ��ļƻ� ���ڶ������ִ���Ϊһ������ <combCode,amount>
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    public  Map<String, BigDecimal> getUporganCombMapAndTransCombLevel(String planMonth, String upOrganCode, String organCode,int combLevel){
        //1.���ּ��ƻ������� <upcombcode,increment>
        HashMap<String, BigDecimal> combIncrementMap = new HashMap<>();
        HashMap<String, Object> planQueryMap = new HashMap<>();
        planQueryMap.put("planMonth", planMonth);
        planQueryMap.put("planType", TbPlan.PLAN);
        planQueryMap.put("upOrganCode", upOrganCode);
        planQueryMap.put("organCode", organCode);
        List<Map<String, Object>> planDetails = tbPlanMapper.selectPlanOrganIncreament(planQueryMap);
        for (Map<String, Object> map : planDetails) {
            combIncrementMap.put(map.get("upcombcode").toString(), new BigDecimal(map.get("increment").toString()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combIncrementMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combIncrementMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * ��ȡ�ƻ���������map  ���ڶ������ִ���Ϊһ������   <upcombCode,amount>
     * @param tbPlanadjDetailList �ƻ���������list
     * @param combLevel list�Ĵ��ּ���
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanadjCombMapAndTransCombLevel(List<TbPlanadjDetail> tbPlanadjDetailList, int combLevel) {
        //1.��װ��ǰlist ���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType());
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), amount.add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * ��ȡ�ƻ���������map    list--> <upcombCode,amount>
     * @param tbPlanadjDetailList �ƻ���������list
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanadjCombMap(List<TbPlanadjDetail> tbPlanadjDetailList) {
        //1.��װ��ǰlist ���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType());
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), amount.add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }
        return combAmountMap;
    }

    /*���в鿴�ϼ��������Լ��ƶ��ļƻ�����*/
    @Override
    public List<Map<String, Object>> selectLowOrganIncrement(Map<String, Object> paramMap) {
        List<Map<String, Object>> planList = tbplanMapper.selectLowOrganIncrement(paramMap);
        for (Map<String, Object> map : planList) {
            if (map.get("unit") != null && "2".equals(map.get("unit").toString())) {
                map.put("amount",new BigDecimal(map.get("amount").toString()).divide(new BigDecimal("10000")));
            }
        }
        return planList;
    }

}
