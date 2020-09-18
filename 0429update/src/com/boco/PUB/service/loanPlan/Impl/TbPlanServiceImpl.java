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
 * @Description: 信贷计划service
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


    /*计划上传地址*/
    @Value("${upload.path}")
    private String uploadPath;

    /*超限额附件上传地址*/
    @Value("${upload.quota.path}")
    private String uploadQuotaPath;



    /**
     * 已提交的信贷计划
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

    /*查询已审批的信贷计划*/
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

    /*启动信贷计划审批流程*/
    @Override
    public PlainResult<String> startLoanPlanAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_MECH;
        }

        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", planId);
        //下一审批人
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //提交第一个任务
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //获取最新的任务，并将任务执行对应审批人员
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("机构计划审批流程编号workFlowCode【" + workFlowCode + "】");

        //更新信贷计划记录状态
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(planId);
        tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);
        //记录审批信息
        String url = "tbPlanPendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, planId);
        return result.success(workFlowCode, "启动机构计划审批流程启动成功");
    }

    /*启动信贷条线审批流程*/
    @Override
    public PlainResult<String> startLoanplanStripeAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_LINE;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_LINE;
        }


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", planId);
        //下一审批人
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //提交第一个任务
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //获取最新的任务，并将任务执行对应审批人员
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("条线计划审批流程编号workFlowCode【" + workFlowCode + "】");

        //更新信贷计划记录状态
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(planId);
        tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);
        //记录审批信息
        String url = "tbPlanStripePendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
        saveMsgPlanStripe(msgNo, operCode, assignee, url, planId);
        return result.success(workFlowCode, "启动条线计划审批流程启动成功");
    }

    /*查询待审批的信贷计划*/
    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception {
        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //查询登录用户待办任务
        /*流程key*/
        String processKey = "";
        /*机构级别*/
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

    /*查询待审批的条线计划*/
    @Override
    public List<Map<String, Object>> getPendingPlanStripe(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception {
        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //查询登录用户待办任务
        /*流程key*/
        String processKey = "";
        /*机构级别*/
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

    @Override
    public void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
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
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(msgMap.get("planId").toString());
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//驳回
            tbPlanDO.setPlanStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("planId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //如果是网关的话，通过网关获取下一个节点的名称
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
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
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);

        //流转任务
        workFlowService.completeTask(taskId, comment, varMap);
        //获取最新的任务，并将任务执行对应审批人员
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //获取流程发起人
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //记录审批信息
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planId = String.valueOf(msgMap.get("planId"));

        if("1".equals(isAgree)&&"true".equals((String) msgMap.get("lastUserType"))){
            deleteMsg(planId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //获取流程发起人
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbPlanReject/loanTbPlanResubmitAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            } else if(!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbPlanPendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, planId + "");
        }


        //月度计划审批完成，生成月初计划备份
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String)msgMap.get("lastUserType")))) {
            tbPlanDetailBackupService.addPlanDetailBackup(planId);
        }
    }

    @Override
    public void completeTaskAuditPlanStripe(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
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
        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(msgMap.get("planId").toString());
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//驳回
            tbPlanDO.setPlanStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("planId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //如果是网关的话，通过网关获取下一个节点的名称
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
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
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                tbPlanDO.setPlanStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updateByPK(tbPlanDO);

        //流转任务
        workFlowService.completeTask(taskId, comment, varMap);
        //获取最新的任务，并将任务执行对应审批人员
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //获取流程发起人
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //记录审批信息
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planId = String.valueOf(msgMap.get("planId"));

        if("1".equals(isAgree)&&"true".equals((String) msgMap.get("lastUserType"))){
            deleteMsgPlanStripe(planId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //获取流程发起人
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
                url = "tbPlanStripeReject/loanTbPlanResubmitAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            } else if(!"true".equals((String) msgMap.get("lastUserType"))){
                url = "tbPlanStripePendingApp/listTbPlanDetailAuditUI.htm?planId=" + planId + "&taskid=" + task.getId();
            }
            //记录审批信息
            saveMsgPlanStripe(msgNo, operCode, assignee, url, planId + "");
        }
    }


    /*录入计划文件*/
    @Override
    public Map<String, String> enterReportPlanByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "录入计划成功！");
        InputStream is = null;
        try {
            //-------处理表格-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime()+ "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //判断录入的计划文件是否正确
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname()+"制定机构计划导入表样";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }

            //获取表格贷种组合
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);


            //查询数据库贷种组合
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //把表格的贷种组合格式化，并且判断表格贷种组合是否存在于数据库中
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode)||"".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "贷种组合【"+cellValues.get(i)+"】在数据库中不存在，请检查！");
                        return resultMap;
                    }
                }
                cells[i] =combCode ;
            }

            //获取表格所有内容
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //-------数据入库-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            //校验特殊字段非空
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请选择月份或单位！");
                return resultMap;
            }

            // 校验唯一性
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.PLAN);
            List<TbPlan> tbPlanListCheck = tbplanMapper.selectByAttr(tbPlanParam);
            // 当计划存在时并且是 未提交 或 驳回 状态，更新计划
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
                    resultMap.put("msg", "该月份机构计划正在审批，不能导入！");
                    return resultMap;
                }else if (TbReqDetail.STATE_APPROVED == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "该月份机构计划已经审批完成，不能导入！");
                    return resultMap;
                }
            }

            //分行 条线和计划只能制定一个
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.STRIPE);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "所属月份已经制定条线计划，不能再制定机构计划");
                    return resultMap;
                }
            }



            //构建计划
            //查询贷种级别
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


            //查询贷种组合
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
            /*计划制定净增量*/
            BigDecimal planRealIncrement = new BigDecimal("0");
            //构建计划详情
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
                            resultMap.put("msg", "导入计划失败！请录入正确的数值！");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planAmount = planAmount.multiply(new BigDecimal("10000"));
                        }
                        //构建计划详情
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


            //一级分行计划制定量要等于计划净增量
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlan.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);
                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                }
                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "录入计划失败！" +combname+"制定净增量为"+realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"亿元，计划净增量为"
                                +upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"亿元，请调整！");

                        return resultMap;
                    }
                }


            }



            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlanMapper.deleteLoanPlanInfo(planId);
            tbplanMapper.insertEntity(tbPlan);


            //最后删除文件
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("录入失败");
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

    /*录入条线文件*/
    @Override
    public Map<String, String> enterReportPlanStripeByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "录入计划成功！");
        InputStream is = null;
        try {
            //-------处理表格-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime() + "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //判断录入的计划文件是否正确
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname()+"制定条线计划导入表样";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }

            //获取表格贷种组合
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);

            //查询数据库贷种组合
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //把表格的贷种组合格式化，并且判断表格贷种组合是否存在于数据库中
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode)||"".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "贷种组合【"+cellValues.get(i)+"】在数据库中不存在，请检查！");
                        return resultMap;
                    }
                }
                cells[i] =combCode ;
            }

            //获取表格所有内容
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //-------数据入库-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");

            //校验特殊字段非空
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请选择月份或单位！");
                return resultMap;
            }

            //校验唯一性
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbplanMapper.selectByAttr(tbPlanParam);
            // 当计划存在时并且是 未提交 或 驳回 状态，更新计划
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
                    resultMap.put("msg", "该月份条线计划正在审批，不能导入！");
                    return resultMap;
                } else if (TbReqDetail.STATE_APPROVED == tbPlan.getPlanStatus().intValue()){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "该月份条线计划已经审批完成，不能导入！");
                    return resultMap;
                }
            }

            //分行 条线和计划只能制定一个
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    resultMap.put("code", "false");
                    resultMap.put("msg", "所属月份已经制定机构计划，不能再制定条线计划");
                    return resultMap;
                }
            }

            //构建计划
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


            //获取贷种组合级别 查二级贷种
            int combLevel = 2;
            //查询贷种组合
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            combList = loanCombService.selectCombOfBind(combMap);


            ArrayList<TbPlanDetail> tbPlanDetailList = new ArrayList<>();
            /*计划制定净增量*/
            BigDecimal planRealIncrement = new BigDecimal("0");
            //构建计划详情
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
                            resultMap.put("msg", "导入计划失败！请录入正确的数值！");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planAmount = planAmount.multiply(new BigDecimal("10000"));
                        }
                        //构建计划详情
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

            //一级分行计划制定量要等于计划净增量
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlan.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);
                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = getUporganCombMap(planMonth, uporgan, organCode);
                }
                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "录入计划失败！" +combname+"制定净增量为"+realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"亿元，计划净增量为"
                                +upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"亿元，请调整！");

                        return resultMap;
                    }
                }
            }

            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlanMapper.deleteLoanPlanInfo(planId);
            tbplanMapper.insertEntity(tbPlan);


            //最后删除文件
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("录入失败");
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

        //1-计划模板  2-条线模板
        if ("1".equals(type)) {

            //查询贷种组合
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

            fileName = getSessionOrgan().getOrganname() + "制定机构计划导入表样";

            //查询机构
            // List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
            workbook = exportExcel(combList, organList, fileName);


        } else if ("2".equals(type)) {

            //获取贷种组合级别 查二级贷种
            int combLevel = 2;
            //查询贷种组合
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            combList = loanCombService.selectCombOfBind(combMap);

            fileName = getSessionOrgan().getOrganname() + "制定条线计划导入表样";
            //查询机构
            List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(getSessionOrgan().getThiscode());
            workbook = exportExcel(combList, organList, fileName);

        }

        //文件名
        fileName = fileName + ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");

        }
        // 清空response
        response.reset();
        // response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/octet-stream");

        workbook.write(os);
        os.flush();
        os.close();

    }

    /*维护时间计划后，更新信贷计划的计划净增量*/
    @Override
    public void updatePlanAndPlanadj(TbPlan plan) {
        //更新计划
        plan.setPlanType(TbPlan.PLAN);
        plan.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updatePlan(plan);
        //更新计划详情
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjOrgan(plan.getPlanOrgan());
        tbPlanadj.setPlanadjMonth(plan.getPlanMonth());
        tbPlanadj.setPlanadjType(plan.getPlanType());
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        tbPlanadjMapper.updatePlanadj(tbPlanadj);
    }

    /*维护时间计划后，更新条线计划的计划净增量*/
    @Override
    public void updatePlanStripeAndPlanadjStripe(TbPlan plan) {
        //更新计划
        plan.setPlanType(TbPlan.STRIPE);
        plan.setPlanUpdateTime(BocoUtils.getTime());
        tbplanMapper.updatePlan(plan);
        //更新计划详情
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjOrgan(plan.getPlanOrgan());
        tbPlanadj.setPlanadjMonth(plan.getPlanMonth());
        tbPlanadj.setPlanadjType(plan.getPlanType());
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        tbPlanadjMapper.updatePlanadj(tbPlanadj);
    }


    private void deleteMsg(String planId) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("机构计划审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("机构计划审批：" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String planId) throws Exception {

        //删除当前审批人的小喇叭信息
        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("机构计划审批","MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("机构计划审批："+planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

        /*添加下一级审批人的小喇叭信息*/
        WebMsg msg=new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //获取当前操作人姓名及操作状态
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("机构计划审批","MSG_TYPE"));

        TbPlan plan = tbPlanMapper.selectByPK(planId);
        BigDecimal increment = plan.getPlanRealIncrement();
        String unit = "万元";
        if (plan.getPlanUnit().intValue() == 2) {
            unit = "亿元";
            increment = increment.divide(new BigDecimal("10000"));
        }
        String operName = "所属月份：" + plan.getPlanMonth()
                + " 计划净增量：" + increment.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("机构计划审批："+planId);
        webMsgService.insertEntity(msg);
    }


    private void deleteMsgPlanStripe( String planId) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("条线计划审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("条线计划审批：" + planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }
    private void saveMsgPlanStripe(String msgNo, String operCode, String assignee, String msgUrl, String planId) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("条线计划审批","MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("条线计划审批："+planId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

        WebMsg msg=new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //获取当前操作人姓名及操作状态
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("条线计划审批","MSG_TYPE"));

        TbPlan plan = tbplanMapper.selectByPK(planId);
        BigDecimal increment = plan.getPlanRealIncrement();
        String unit = "万元";
        if (plan.getPlanUnit().intValue() == 2) {
            unit = "亿元";
            increment = increment.divide(new BigDecimal("10000"));
        }
        String operName = "所属月份：" + plan.getPlanMonth()
                + " 计划净增量：" + increment.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("条线计划审批："+planId);
        webMsgService.insertEntity(msg);
    }


    /*删除文件*/
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

    /*找出字符串[]里面的内容*/
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

    //动态生成计划导入表样
    private Workbook exportExcel(List<Map<String, Object>> combList, List<Map<String, Object>> organList, String fileName) {

        Sheet sheet = null;

        // 创建一个excel文件并指定文件名
        String filepath = uploadPath+ fileName + ".xls";
        File file = new File(filepath);

        @SuppressWarnings("resource")
        Workbook workbook = new HSSFWorkbook();

        // 创建sheet
        sheet = workbook.createSheet("计划导入");
        //冻结窗口
        sheet.createFreezePane(1, 2, 1, 2);

        // 合并单元格 s CellRangeAddress(起始行号, 结束行号, 其实列号, 结束列号)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, combList.size()));

        // 标题行样式
        CellStyle titleStyle = workbook.createCellStyle();
        // 水平居中
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //设置字体
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 15);
        titleFont.setBoldweight((short) 100);
        titleStyle.setFont(titleFont);

        /*设置单元格自动换行*/
        CellStyle cellStyle = workbook.createCellStyle();
        //设置自动换行
        cellStyle.setWrapText(true);
        // 水平居中
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);


        // 创建标题行
        Row titleRow = sheet.createRow(0);
        //设置行高
        titleRow.setHeight((short)500);
        titleRow.createCell(0).setCellValue(fileName);
        titleRow.getCell(0).setCellStyle(titleStyle);

        //创建贷种
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellStyle(cellStyle);
        row1.getCell(0).setCellValue("机构[organ]");

        for (int i = 0; i < combList.size(); i++) {
            //构建贷种名称
            Map<String, Object> map = combList.get(i);
            String combname = map.get("combname").toString();
            String combcode = map.get("combcode").toString();
            String comb = combname + "[" + combcode + "]";
            Cell cell = row1.createCell(i + 1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(comb);

            //设置贷种列宽
            int length = comb.toString().getBytes().length  * 256 + 200;
            //这里把宽度最大限制到15000
            if (length>15000){
                length = 15000;
            }
            sheet.setColumnWidth(i+1, length);
        }


// 创建机构
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


        //设置机构列宽 第一个参数代表列id(从0开始),第2个参数代表宽度值
        sheet.setColumnWidth(0, organLength);

        return workbook;

    }

    //计算上级贷种及总的计划制定量
    private Map<String, BigDecimal> comparePlanIncrement(List<TbPlanDetail> tbPlanDetailList, int combLevel) {
        //1.贷种及贷种总金额  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            String amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? "0" : combAmountMap.get(tbPlanDetail.getPdLoanType()).toString();
            combAmountMap.put(tbPlanDetail.getPdLoanType(), new BigDecimal(amount).add(tbPlanDetail.getPdAmount()));
        }

        //2.贷种的上级贷种 <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.计算上级贷种及总的计划制定量
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

            // filePath是指欲下载的文件的路径。
            File file = new File(filePath);

            // 取得文件名。
            String fileName = file.getName();
            int i = fileName.lastIndexOf("_-");
            fileName = fileName.substring(i+2);

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // 清空response
            response.reset();
            // 设置response的Header
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

/*下载机构计划*/
    @Override
    public void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel)throws Exception {
       // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        /*查询导出机构*/
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
        Map<String, Object> organTotalMap = new HashMap<>(2);
        organTotalMap.put("organcode", "total");
        organTotalMap.put("organname", "合计");
        organList.add(organTotalMap);


        /*查询导出的数据*/
        String planId = request.getParameter("planId");
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanId(planId);
        TbPlan plan = tbplanMapper.selectByAttr(tbPlanParam).get(0);
        TbPlanDetail param = new TbPlanDetail();
        param.setPdRefId(planId);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(param);

        //查询贷种组合
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
        combTotalMap.put("combname", "合计");
        combList.add(combTotalMap);

        //计算合计
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


        /*包装数据*/
        //计划详情
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

        //机构
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 3 + i);
        }

        //贷种
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i <combList.size() ; i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*写入文件*/

        String filename = getSessionOrgan().getOrganname() +"-"+ plan.getPlanMonth()+ "-机构计划";
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size());
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //单位
        POIExportUtil.setCell(sheet, 1, 0, "单位:" , cellStyle);
        POIExportUtil.setCell(sheet, 1, 1, plan.getPlanUnit()==1?"万元":"亿元" , cellStyle);

        //机构
        POIExportUtil.setCell(sheet, 2, 0, "机构" , cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //贷种
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.setCell(sheet, 2, column, map.get("combname").toString(), cellStyle);
            }
        }
        //数据
        for (String key : planMap.keySet()) {
            String[] keys = key.split("_");
            Integer row =organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, column, planMap.get(key).getPdAmount(), cellStyle);
            }
        }

        //设置列宽
        for (int i = 0; i <combList.size()+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet,1,3);

        //文件名
         filename = filename + ".xls";
                String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
        response.reset();
//        response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }



    /*下载条线计划*/
    @Override
    public void downloadPlanStripe(HttpServletResponse response, HttpServletRequest request, String organlevel)throws Exception {
        // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        //查询导出机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        /*查询导出贷种 二级贷种*/
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMapParam = new HashMap<String, Object>();
        combMapParam.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMapParam);
        Map<String, Object> combTotalMap = new HashMap<>(2);
        combTotalMap.put("combcode", "total");
        combTotalMap.put("combname", "合计");
        combList.add(combTotalMap);



        /*查询导出的数据*/
        String planId = request.getParameter("planId");
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanId(planId);
        TbPlan plan = tbplanMapper.selectByAttr(tbPlanParam).get(0);
        TbPlanDetail param = new TbPlanDetail();
        param.setPdRefId(planId);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(param);

        //计算合计
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

        /*包装数据*/
        //计划详情
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
        //机构
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 3 + i);
        }
        //贷种
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i <combList.size() ; i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*写入文件*/

        String filename = getSessionOrgan().getOrganname() +"-"+ plan.getPlanMonth()+ "-条线计划";
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size());
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //单位
        POIExportUtil.setCell(sheet, 1, 0, "单位:" , cellStyle);
        POIExportUtil.setCell(sheet, 1, 1, plan.getPlanUnit()==1?"万元":"亿元" , cellStyle);

        //机构
        POIExportUtil.setCell(sheet, 2, 0, "机构:" , cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //贷种
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.setCell(sheet, 2, column, map.get("combname").toString(), cellStyle);
            }
        }
        //数据
        for (String key : planMap.keySet()) {
            String[] keys = key.split("_");
            Integer row =organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, column, planMap.get(key).getPdAmount(), cellStyle);
            }
        }

        //设置列宽
        for (int i = 0; i <combList.size()+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet,1,3);

        //文件名
         filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
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
     * 获取计划贷种map  对于二级贷种处理为一级贷种   <upcombCode,amount>
     * @param tbPlanDetailList 计划详情list
     * @param combLevel list的贷种级别
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanCombMapAndTransCombLevel(List<TbPlanDetail> tbPlanDetailList, int combLevel) {
        //1.组装当前list 贷种及贷种总金额  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanDetail.getPdLoanType());
            combAmountMap.put(tbPlanDetail.getPdLoanType(), amount.add(tbPlanDetail.getPdAmount()));
        }

        //2.贷种的上级贷种 <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.计算上级贷种及总的计划制定量
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * 获取计划贷种map    list--> <upcombCode,amount>
     * @param tbPlanDetailList 计划详情list
     * @return <upcomb,amount>
     */
    public    Map<String, BigDecimal> getPlanCombMap(List<TbPlanDetail> tbPlanDetailList) {
        //1.组装当前list 贷种及贷种总金额  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanDetail.getPdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanDetail.getPdLoanType());
            combAmountMap.put(tbPlanDetail.getPdLoanType(), amount.add(tbPlanDetail.getPdAmount()));
        }
        return combAmountMap;
    }

    /**
     * 获取总行给本机构制定的计划  <combCode,amount>  对于贷种级别不处理
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    public  Map<String, BigDecimal> getUporganCombMap(String planMonth, String upOrganCode, String organCode){
        //上级贷种及计划净增量 <upcombcode,increment>
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
     * 获取总行给本机构制定的计划 对于二级贷种处理为一级贷种 <combCode,amount>
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    public  Map<String, BigDecimal> getUporganCombMapAndTransCombLevel(String planMonth, String upOrganCode, String organCode,int combLevel){
        //1.贷种及计划净增量 <upcombcode,increment>
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

        //2.贷种的上级贷种 <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.计算上级贷种及总的计划制定量
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combIncrementMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combIncrementMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * 获取计划调整贷种map  对于二级贷种处理为一级贷种   <upcombCode,amount>
     * @param tbPlanadjDetailList 计划调整详情list
     * @param combLevel list的贷种级别
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanadjCombMapAndTransCombLevel(List<TbPlanadjDetail> tbPlanadjDetailList, int combLevel) {
        //1.组装当前list 贷种及贷种总金额  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType());
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), amount.add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }

        //2.贷种的上级贷种 <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.计算上级贷种及总的计划制定量
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? BigDecimal.ZERO : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /**
     * 获取计划调整贷种map    list--> <upcombCode,amount>
     * @param tbPlanadjDetailList 计划调整详情list
     * @return <upcomb,amount>
     */
    public Map<String, BigDecimal> getPlanadjCombMap(List<TbPlanadjDetail> tbPlanadjDetailList) {
        //1.组装当前list 贷种及贷种总金额  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            BigDecimal amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? BigDecimal.ZERO : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType());
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), amount.add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }
        return combAmountMap;
    }

    /*分行查看上级机构给自己制定的计划详情*/
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
