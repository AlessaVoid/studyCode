package com.boco.TONY.biz.loanplan.service.impl;

import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.entity.WebTaskRoleInfo;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.flownode.service.IWebFlowNodeService;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.biz.loanplan.POJO.DTO.PlanTaskInfoDTO;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDTO;
import com.boco.TONY.biz.loanplan.service.IWebLoanPlanAuditService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.POJO.DO.TbAuditRecordHistDO;
import com.boco.TONY.common.POJO.DTO.TbAuditRecordHistDTO;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.TONY.enums.ReqStateEnums;
import com.google.common.base.Preconditions;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 信贷计划审批业务逻辑层
 *
 * @author tony
 * @describe WebLoanPlanAuditServiceImpl
 * @date 2019-09-26
 */
public class WebLoanPlanAuditServiceImpl2 implements IWebLoanPlanAuditService {
    @Autowired
    FdOperMapper fdOperMapper;
    @Autowired
    FdOrganMapper fdOrganMapper;

    @Autowired
    TbPlanMapper tbPlanMapper;
    @Autowired
    LoanPlanDetailMapper loanPlanDetailMapper;

    @Autowired
    WebMsgMapper webMsgMapper;
    @Autowired
    WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebOperInfoMapper webOperInfoMapper;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebFlowNodeService webFlowNodeService;

    @Autowired
    TbAuditRecordHistMapper tbAuditRecordHistMapper;


    /**
     * 启动流程-信贷需求上报流程审批
     *
     * @param organCode 机构号
     * @param planOper  计划制定者
     * @param assignee  下一级审批人
     * @param planId    计划标识
     * @return 启动审批是否成功
     */
    public PlainResult<String> startLoanPlanAuditProcess(String organCode, String planOper, String planOperName, String assignee, String planId) {
        Preconditions.checkArgument(Objects.nonNull(organCode) && Objects.nonNull(planOper) && Objects.nonNull(assignee) && Objects.nonNull(planId));

        PlainResult<String> result = new PlainResult<>();
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(AuditMix.PLAN_TOTAL_MECH, planId, null);
            String processInstanceId = processInstance.getId();
            Map<String, Object> varMap = buildVarMap(assignee, planOper, planId, processInstanceId);
            varMap.put(AuditMix.LAST_ASSIGNEE_KEY, assignee);
            Task task = getTaskByProcessInstanceId(processInstanceId);
            taskService.claim(task.getId(), planOper + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);

            taskService.complete(task.getId(), varMap);

            Task auditTask = getTaskByProcessInstanceId(processInstanceId);
            taskService.claim(auditTask.getId(), assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
            //更新信贷计划状态-审批
            TbPlan tbPlanDO = new TbPlan().setPlanId(planId);
            tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
            tbPlanMapper.updateLoanPlanState(tbPlanDO);
            return result.success("audit|start", "start audit task process.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "start audit task process.");
        }
    }

    private Task getTaskByProcessInstanceId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 查询个人所有已完成审批的记录
     *
     * @param assignee   审批者
     * @param pageNumber 页号
     * @param pageSize   页大小
     * @return ListResult
     */
    public ListResult<PlanTaskInfoDTO> listHistoryLoanPlanWithFinished(String assignee, int pageNumber, int pageSize) {
        Preconditions.checkArgument(null != assignee && 0L < pageNumber && 0L < pageSize);

        ListResult<PlanTaskInfoDTO> result = new ListResult<>();
        List<HistoricTaskInstance> historyTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX).processDefinitionKey(AuditMix.PLAN_TOTAL_MECH).processFinished().listPage(pageNumber - 1, pageSize);

        List<PlanTaskInfoDTO> planTaskInfoDTOList = new ArrayList<>();
        try {
            for (HistoricTaskInstance historicTaskInstance : historyTaskInstanceList) {
                String processInstanceId = historicTaskInstance.getProcessInstanceId();
                HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName(AuditMix.PLAN_KEY).singleResult();
                String planId = (String) historicVariableInstance.getValue();
                TbPlan tbPlanDO = tbPlanMapper.selectLoanPlanByPlanId(planId);
                PlanTaskInfoDTO planTaskInfoDTO = buildTaskInfoDTO(historicTaskInstance, tbPlanDO);
                planTaskInfoDTOList.add(planTaskInfoDTO);
            }

            return result.success(planTaskInfoDTOList, "list history loan require with finished success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "list historic loan require with finished failed");
        }

    }

    /**
     * 通过计划制定人列出所有制定的信贷计划
     *
     * @param planOper 计划制定人
     */
    public ListResult<TbPlanDTO> listAllLoanPlanByPlanOper(String planOper) {
        Preconditions.checkArgument(null != planOper);
        ListResult<TbPlanDTO> result = new ListResult<>();
        try {
            List<TbPlan> planDOList = tbPlanMapper.selectLoanPlanByPlanOper(planOper);
            return result.success(buildTbPlanDTOList(planDOList), "load plan info success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load plan info failed.");
        }
    }

    /**
     * 构建信贷计划DTOList
     *
     * @param planDOList 信贷计划DOList
     * @return List
     */
    private List<TbPlanDTO> buildTbPlanDTOList(List<TbPlan> planDOList) {
        List<TbPlanDTO> tbPlanDTOList = new ArrayList<>();
        for (TbPlan tbPlanDO : planDOList) {
            TbPlanDTO tbPlanDTO = new TbPlanDTO();
            tbPlanDTO.setPlanId(tbPlanDO.getPlanId());
            tbPlanDTO.setPlanStatus(tbPlanDO.getPlanStatus());
            tbPlanDTO.setPlanMonth(tbPlanDO.getPlanMonth());
            tbPlanDTO.setPlanOrgan(tbPlanDO.getPlanOrgan());
            tbPlanDTO.setPlanOper(tbPlanDO.getPlanOper());
            tbPlanDTOList.add(tbPlanDTO);
        }
        return tbPlanDTOList;
    }

    /**
     * 列出自己代办的任务
     *
     * @param assignee   审批人
     * @param pageNumber 页号
     * @param pageSize   页面大小
     * @return 任务集合
     */
    public List<PlanTaskInfoDTO> getAllAssigneeTask(String assignee, int pageNumber, int pageSize) {
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(AuditMix.PLAN_TOTAL_MECH)
                .taskAssignee(assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX).listPage(pageNumber - 1, pageSize);
        List<PlanTaskInfoDTO> planTaskInfoDTOList = new ArrayList<>();
        for (Task task : taskList) {
            Map<String, Object> varMap = runtimeService.getVariables(task.getExecutionId());
            int where = (int) varMap.get(AuditMix.WHERE_KEY);
            String lastAssignee = (String) varMap.get(AuditMix.LAST_ASSIGNEE_KEY);
            String executionId = task.getExecutionId();
            String planId = (String) runtimeService.getVariable(executionId, AuditMix.PLAN_KEY);
            TbPlan tbPlanDO = tbPlanMapper.selectLoanPlanByPlanId(planId);
            planTaskInfoDTOList.add(buildTaskInfoDTO(task, tbPlanDO, where, lastAssignee));
        }
        return planTaskInfoDTOList;
    }

    /**
     * 构建任务信息DTO
     *
     * @param historicTaskInstance 历史任务实例
     * @param tbPlanDO             计划DO
     * @return PlanTaskInfoDTO
     */
    private PlanTaskInfoDTO buildTaskInfoDTO(HistoricTaskInstance historicTaskInstance, TbPlan tbPlanDO) {
//         PlanTaskInfoDTO planTaskInfoDTO = new PlanTaskInfoDTO();
//         if (LoanStateEnums.STATE_APPROVED.status == tbPlanDO.getPlanStatus()) {
//             planTaskInfoDTO.setPlanStatus(LoanStateEnums.STATE_APPROVED.description);
//         }
//         planTaskInfoDTO.setPlanCreateTime(tbPlanDO.getPlanDateStart());
//         planTaskInfoDTO.setPlanEndTime(tbPlanDO.getPlanDateEnd());
//
//         planTaskInfoDTO.setPlanId(tbPlanDO.getPlanId());
//         planTaskInfoDTO.setPlanMonth(tbPlanDO.getPlanMonth());
//         planTaskInfoDTO.setTaskId(historicTaskInstance.getId());
//         planTaskInfoDTO.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
//         planTaskInfoDTO.setPlanCreateOper(tbPlanDO.getPlanOper());
//
//         FdOrgan fdOrgan = fdOrganMapper.selectByPK(tbPlanDO.getPlanOrgan());
//         planTaskInfoDTO.setPlanOrgan(tbPlanDO.getPlanOrgan());
//         planTaskInfoDTO.setPlanOrganName(fdOrgan.getOrganname());
// //        planTaskInfoDTO.setLastAuditOper(lastAssigneeName);
//
//         TbPlanDetailDO queryPlanDetailDO = new TbPlanDetailDO();
//         queryPlanDetailDO.setPdOrgan(tbPlanDO.getPlanOrgan());
//         queryPlanDetailDO.setPdRefId(tbPlanDO.getPlanId());
//
//         TbPlanDetailDO planDetailDO = loanPlanDetailMapper.selectLoanPlanDetailByAttr(queryPlanDetailDO);
//         planTaskInfoDTO.setPlanStatus(LoanStateEnums.sourceOf(planDetailDO.getPdState()));
//         planTaskInfoDTO.setPlanCreateTime(tbPlanDO.getPlanDateStart());
//         planTaskInfoDTO.setPlanEndTime(tbPlanDO.getPlanDateEnd());
//         return planTaskInfoDTO;
        return null;
    }

    /**
     * 构建任务信息
     *
     * @param task     　任务
     * @param tbPlanDO 　信贷计划
     * @param where    　当前审批同级位置
     * @return PlanTaskInfoDTO
     */
    private PlanTaskInfoDTO buildTaskInfoDTO(Task task, TbPlan tbPlanDO, int where, String lastAssignee) {
        PlanTaskInfoDTO planTaskInfoDTO = new PlanTaskInfoDTO();
        planTaskInfoDTO.setProcessInstanceId(task.getProcessInstanceId());
        planTaskInfoDTO.setTaskId(task.getId());
        planTaskInfoDTO.setPlanId(tbPlanDO.getPlanId());
        planTaskInfoDTO.setPlanCreateOper(tbPlanDO.getPlanOper());
        planTaskInfoDTO.setPlanMonth(tbPlanDO.getPlanMonth());
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(tbPlanDO.getPlanOrgan());
        planTaskInfoDTO.setPlanOrgan(fdOrgan.getOrganname());
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(tbPlanDO.getPlanOper());
        List<FdOper> createOperList = fdOperMapper.selectByAttr(fdOper);
        if (CollectionUtils.isNotEmpty(createOperList)) {
            FdOper createOper = createOperList.get(0);
            planTaskInfoDTO.setPlanCreateOper(createOper.getOpername());
        }
        fdOper.setOpercode(lastAssignee);
        List<FdOper> lastAssigneeList = fdOperMapper.selectByAttr(fdOper);
        if (CollectionUtils.isNotEmpty(lastAssigneeList)) {
            FdOper lastAssigneeInfo = lastAssigneeList.get(0);
            planTaskInfoDTO.setLastAuditOper(lastAssigneeInfo.getOpername());
        }
        //拉取下一级审批信息
        planTaskInfoDTO.setWhere(where + 1);
        if (LoanStateEnums.STATE_APPROVING.status == tbPlanDO.getPlanStatus()) {
            planTaskInfoDTO.setPlanStatus(LoanStateEnums.STATE_APPROVING.description);
        } else if (LoanStateEnums.STATE_APPROVED.status == tbPlanDO.getPlanStatus()) {
            planTaskInfoDTO.setPlanStatus(LoanStateEnums.STATE_APPROVED.description);
        } else if (LoanStateEnums.STATE_DISMISSED.status == tbPlanDO.getPlanStatus()) {
            planTaskInfoDTO.setPlanStatus(LoanStateEnums.STATE_DISMISSED.description);
        }


        return planTaskInfoDTO;
    }

    /**
     * 获取拥有信贷需求审批的柜员信息[映射角色信息表]
     *
     * @param organLevel 机构级别
     * @return 授权审批柜员列表
     */
    public ListResult<FdOper> getOperInfoListByOrganLevel(String organLevel, String where, String organCode, String meOperCode) throws Exception {
        Preconditions.checkArgument(null != organLevel && null != where);

        ListResult<FdOper> result = new ListResult<>();
        String taskId = fetchTaskDefineRoleId(where);
        WebTaskRoleInfo taskRoleInfo = new WebTaskRoleInfo();
        taskRoleInfo.setTaskId(taskId);
        taskRoleInfo.setOrganLevel(organLevel);
        List<WebTaskRoleInfo> webTaskRoleInfoList = webTaskRoleInfoService.selectByAttr(taskRoleInfo);

        if (CollectionUtils.isEmpty(webTaskRoleInfoList)) {
            return result.success(new ArrayList<>(), "load oper info is empty.");
        }

        List<FdOper> fdOperList = new ArrayList<>();
        try {
            WebTaskRoleInfo webTaskRoleInfo = webTaskRoleInfoList.get(0);
            String roleCode = webTaskRoleInfo.getRoleCode();

            List<String> operCodeList = webOperRoleMapper.selectRoleCodeListByRoleId(roleCode);
            for (String operCode : operCodeList) {
                WebOperInfo webOperInfo = new WebOperInfo();
                webOperInfo.setOperCode(operCode);
                WebOperInfo operInfo = webOperInfoMapper.selectOperCode(webOperInfo);

                if (Objects.nonNull(operInfo)) {
                    if (organCode.equals(operInfo.getOrganCode()) && !meOperCode.equals(operInfo.getOperCode())) {
                        FdOper fdOper = new FdOper();
                        fdOper.setOpercode(operInfo.getOperCode());
                        fdOper.setOpername(operInfo.getOperName());
                        fdOperList.add(fdOper);
                    }
                }
            }
            return result.success(fdOperList, "fetch oper info list by organ level success.");
        } catch (
                Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "get oper info list by organ level failed");
        }
    }

    /**
     * 通过定义查询选择相关节点角色
     *
     * @param where 定位同机构审批节点
     * @return 定位的节点实例名称
     */
    private String fetchTaskDefineRoleId(String where) {
        return AuditMix.PLAN_TOTAL_MECH_AUDITOR_PREFIX + where;
    }


    /**
     * 机构需求
     *
     * @param request 机构需求
     * @return 返回状态
     * @throws Exception 异常
     */
    public PlainResult<String> auditLoanPlanRequest(HttpServletRequest request, String lastAssignee, String organCode, String lastAssigneeName) throws Exception {

        String taskId = request.getParameter(AuditMix.TASK_KEY);
        String planId = request.getParameter(AuditMix.PLAN_KEY);
        String comment = request.getParameter(AuditMix.COMMENT_KEY);
        String isAgree = request.getParameter(AuditMix.IS_AGREE_KEY);
        String assignee = request.getParameter(AuditMix.ASSIGNEE_KEY);
        Preconditions.checkArgument(null != taskId && null != planId && null != organCode && null != isAgree && null != comment);

        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
        boolean lastUserType = this.getLastUserType(activityImpl);
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> lastVarMap = workFlowService.getTaskVariables(taskId);
        Map<String, Object> varMap = new HashMap<>();
        if (!lastUserType) {
            int newWhere = (int) lastVarMap.get(AuditMix.WHERE_KEY) + 1;
            varMap.put(AuditMix.WHERE_KEY, newWhere);
            varMap.put(AuditMix.ASSIGNEE_KEY, assignee);
        }
        varMap.put(AuditMix.TASK_KEY, taskId);
        varMap.put(AuditMix.PLAN_KEY, planId);
        varMap.put(AuditMix.IS_AGREE_KEY, isAgree);
        varMap.put(AuditMix.COMMENT_KEY, comment);
        varMap.put(AuditMix.LAST_ASSIGNEE_KEY, lastAssignee);
        varMap.put(AuditMix.LAST_ASSIGNEE_NAME_KEY, lastAssigneeName);
        String processInstanceId = (String) lastVarMap.get(AuditMix.PROCESS_INSTANCE_ID);
        varMap.put(AuditMix.PROCESS_INSTANCE_ID, processInstanceId);
        completeTaskAudit(taskId, comment, varMap, lastUserType);
        return result.success("success", "audit task success");
    }

    /**
     * 判断是否是最后一个审批人
     *
     * @param activityImpl activityImpl
     * @return 是否是最后一个审批人
     */
    private boolean getLastUserType(ActivityImpl activityImpl) {
        boolean lastUserType = false;
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                PvmActivity act = pvm.getDestination();
                if (AuditMix.EXCLUSIVE_GATEWAY_KEY.equals(act.getProperty("name"))) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            if (AuditMix.END_EVENT_KEY.equals(gw.getProperty("name"))) {
                                lastUserType = true;
                            }
                        }
                    }
                }
            }
        }
        return lastUserType;
    }

    @Override
    public PlainResult<String> startBusinessAuditProcess(String assignee, String businessKey, Map<String, Object> businessMap) {
        return null;
    }

    @Override
    public PlainResult<String> auditBusinessTask(String currentAssignee, Map<String, Object> businessMap) throws Exception {
        return null;
    }

    @Override
    public void completeBusinessTask(String taskId, String comment, Map<String, Object> varMap, boolean lastUserType) throws Exception {

    }

    @Override
    public ListResult<FdOper> getBusinessAuditorList(Map<String, Object> businessMap) throws Exception {
        return null;
    }

    @Override
    public List<Object> getAllWaitForAuditTaskList(String assignee, int pageNo, int pageSize) throws Exception {
        return null;
    }

    @Override
    public ListResult<Object> listHistoricBusinessTaskWithFinished(String assignee, int pageNumber, int pageSize) throws Exception {
        return null;
    }

    /**
     * 审批驳回的任务列表
     *
     * @param assignee 审批人
     * @param pageNo   页号
     * @param pageSize 页大小
     * @return ListResult
     */
    public ListResult<Object> getAllRollbackTaskList(String assignee, int pageNo, int pageSize) {
        Preconditions.checkArgument(null != assignee && 0 < pageNo && 0 < pageSize);

        ListResult<Object> result = new ListResult<>();
        try {
            List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(AuditMix.PLAN_TOTAL_MECH).taskAssignee(assignee + AuditMix.COLON + AuditMix.AUDIT_REJECT_SUFFIX).listPage(pageNo - 1, pageSize);
            final int startStep = 1;
            List<Object> planTaskInfoDTOList = new ArrayList<>();
            for (Task task : taskList) {
                Map<String, Object> varMap = taskService.getVariables(task.getId());
                String planId = (String) varMap.get(AuditMix.PLAN_KEY);
                TbPlan tbPlanDO = tbPlanMapper.selectLoanPlanByPlanId(planId);

                PlanTaskInfoDTO planTaskInfoDTO = new PlanTaskInfoDTO();
                planTaskInfoDTO.setPlanId(planId);
                planTaskInfoDTO.setTaskId(task.getId());
                planTaskInfoDTO.setProcessInstanceId(task.getProcessInstanceId());
                planTaskInfoDTO.setWhere(startStep);
                if (tbPlanDO.getPlanStatus() == LoanStateEnums.STATE_DISMISSED.status) {
                    planTaskInfoDTO.setPlanStatus(LoanStateEnums.STATE_DISMISSED.description);
                }

                FdOper fdOper = new FdOper();
                fdOper.setOpercode(tbPlanDO.getPlanOper());
                List<FdOper> planOperList = fdOperMapper.selectByAttr(fdOper);

                if (CollectionUtils.isNotEmpty(planOperList)) {
                    FdOper planOperInfo = planOperList.get(0);
                    planTaskInfoDTO.setPlanCreateOper(planOperInfo.getOpername());
                } else {
                    planTaskInfoDTO.setPlanCreateOper("未知柜员");
                }
                FdOrgan fdOrgan = fdOrganMapper.selectByPK(tbPlanDO.getPlanOrgan());
                planTaskInfoDTO.setPlanOrgan(fdOrgan.getOrganname());
                String lastAssignee = (String) varMap.get(AuditMix.LAST_ASSIGNEE_KEY);
                fdOper.setOpercode(lastAssignee);
                List<FdOper> lastAssigneeList = fdOperMapper.selectByAttr(fdOper);
                if (CollectionUtils.isNotEmpty(lastAssigneeList)) {
                    FdOper lastAssigneeInfo = lastAssigneeList.get(0);
                    planTaskInfoDTO.setLastAuditOper(lastAssigneeInfo.getOpername());
                } else {
                    planTaskInfoDTO.setPlanCreateOper("未知柜员");
                }
                planTaskInfoDTO.setPlanMonth(tbPlanDO.getPlanMonth());
                planTaskInfoDTOList.add(planTaskInfoDTO);
            }
            return result.success(planTaskInfoDTOList, "load all reject tasks success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load all reject tasks failed");
        }

    }

    @Override
    public PlainResult<String> resubmitRejectedTask(String assignee, String taskId, Map<String, Object> businessMap) {
        return null;
    }


    @Override
    public ListResult<Object> getAuditRecordHistRecord(Map<String, Object> businessMap, int pageNo, int pageSize) {
        return null;
    }

    /**
     * 完成信贷任务审批
     *
     * @param taskId  任务Id
     * @param comment 审批评语
     * @param varMap  变量集合
     */
    public void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, boolean lastUserType) {
        String isAgree = (String) varMap.get(AuditMix.IS_AGREE_KEY);
        String planId = (String) varMap.get(AuditMix.PLAN_KEY);
        String lastUser = (String) varMap.get(AuditMix.LAST_ASSIGNEE_KEY);
        String processInstanceId = (String) varMap.get(AuditMix.PROCESS_INSTANCE_ID);

        TbPlan tbPlanDO = new TbPlan();
        tbPlanDO.setPlanId(planId);
        Task task = taskService.createTaskQuery().processDefinitionKey(AuditMix.PLAN_TOTAL_MECH).processInstanceId(processInstanceId).singleResult();
        FdOper fdOper = getOperInfoByOperCode(lastUser);
        Preconditions.checkArgument(Objects.nonNull(fdOper), "no match oper info find");
        switch (isAgree) {
            case AuditMix.AUDIT_REJECT:
                tbPlanDO.setPlanStatus(LoanStateEnums.STATE_DISMISSED.status);
                break;
            case AuditMix.AUDIT_PASS:
                if (lastUserType) {
                    tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVED.status);
                } else {
                    tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
                }
                break;
        }
        tbPlanMapper.updateLoanPlanState(tbPlanDO);
        //TODO   fix bug 20191016 no assignee info for comment
        Authentication.setAuthenticatedUserId(lastUser);
        taskService.addComment(taskId, processInstanceId, comment);
        taskService.complete(taskId, varMap);

        if (!lastUserType && !AuditMix.AUDIT_REJECT.equals(isAgree)) {
            Task auditTask = taskService.createTaskQuery().processDefinitionKey(AuditMix.PLAN_TOTAL_MECH).processInstanceId(processInstanceId).singleResult();
            if (Objects.nonNull(auditTask)) {
                String assignee = (String) varMap.get(AuditMix.ASSIGNEE_KEY);
                taskService.claim(auditTask.getId(), assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
            }

        } else if (AuditMix.AUDIT_REJECT.equals(isAgree)) {
            Task auditTask = taskService.createTaskQuery().processDefinitionKey(AuditMix.PLAN_TOTAL_MECH).processInstanceId(processInstanceId).singleResult();
            if (Objects.nonNull(auditTask)) {
                HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                        .variableName(AuditMix.PLAN_OPER_KEY).processInstanceId(processInstanceId).singleResult();
                String assignee = (String) historicVariableInstance.getValue();
                taskService.claim(auditTask.getId(), assignee + AuditMix.COLON + AuditMix.AUDIT_REJECT_SUFFIX);
            }
        }
    }

    /**
     * 通过柜员号获取柜员信息
     *
     * @param operCode 柜员号
     * @return
     */
    private FdOper getOperInfoByOperCode(String operCode) {
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(operCode);
        List<FdOper> fdOperList = fdOperMapper.selectByAttr(fdOper);
        if (CollectionUtils.isNotEmpty(fdOperList)) {
            return fdOperList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 审批不通过的信贷计划,重新审批
     *
     * @param assignee 审批人
     * @param planOper 计划制定人
     * @param planId   计划标识
     * @param taskId   审批任务标识
     * @return PlainResult
     */
    public PlainResult<String> resubmitRejectTask(String assignee, String planOper, String operName, String planId, String taskId) {
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> lastVarMap = taskService.getVariables(taskId);
        String processInstanceId = (String) lastVarMap.get(AuditMix.PROCESS_INSTANCE_ID);
        Map<String, Object> varMap = buildVarMap(assignee, planOper, planId, processInstanceId);
        try {
            TbAuditRecordHistDO tbAuditRecordHistDO = new TbAuditRecordHistDO();
            tbAuditRecordHistDO.setBusinessId(planId + "");
            tbAuditRecordHistDO.setProcessKey(AuditMix.PLAN_TOTAL_MECH);
            tbAuditRecordHistDO.setProcessInstanceId(processInstanceId);
            tbAuditRecordHistDO.setTaskId(taskId);
            tbAuditRecordHistDO.setAssignee(planOper + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
            tbAuditRecordHistDO.setStatus(LoanStateEnums.STATE_APPROVING.status);
            tbAuditRecordHistDO.setAssigneeName(operName);
            tbAuditRecordHistDO.setStartAt(LocalDateTime.now());
            //记录历史记录
            tbAuditRecordHistMapper.insertRecordHist(tbAuditRecordHistDO);
            taskService.complete(taskId, varMap);
            Task auditTask = getTaskByProcessInstanceId(processInstanceId);
            taskService.claim(auditTask.getId(), assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
            TbPlan tbPlanDO = new TbPlan();
            tbPlanDO.setPlanId(planId);
            tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
            tbPlanMapper.updateLoanPlanState(tbPlanDO);
            return result.success("audit|resubmit", "resubmit audit task success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "resubmit audit task error.");
        }
    }

    /**
     * 审批历史记录
     *
     * @param assignee 任务签收人
     * @param pageNo   页号
     * @param pageSize 页大小
     * @return ListResult
     */
    public ListResult<TbAuditRecordHistDTO> getAuditRecordHistRecord(String assignee, String operName, int pageNo, int pageSize) {
        Preconditions.checkArgument(StringUtils.isNotBlank(assignee));

        ListResult<TbAuditRecordHistDTO> result = new ListResult<>();
        TbAuditRecordHistDO tbAuditRecordHistDO = new TbAuditRecordHistDO();
        tbAuditRecordHistDO.setAssignee(assignee + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        tbAuditRecordHistDO.setProcessKey(AuditMix.PLAN_TOTAL_MECH);
        tbAuditRecordHistDO.setStatus(LoanStateEnums.STATE_APPROVING.status);

        try {
            List<TbAuditRecordHistDO> auditRecordHistDOList = tbAuditRecordHistMapper.getRecordHistByAssignee(tbAuditRecordHistDO);

            return result.success(buildTbAuditRecordHistDTOList(auditRecordHistDOList), "get audit record history record success");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "get audit record history record failed");
        }
    }

    private List<TbAuditRecordHistDTO> buildTbAuditRecordHistDTOList(List<TbAuditRecordHistDO> auditRecordHistDOList) {
        List<TbAuditRecordHistDTO> tbAuditRecordHistDTOList = new ArrayList<>();
        for (TbAuditRecordHistDO auditRecordHistDO : auditRecordHistDOList) {
            TbAuditRecordHistDTO tbAuditRecordHistDTO = new TbAuditRecordHistDTO();
            BeanUtils.copyProperties(auditRecordHistDO, tbAuditRecordHistDTO);
            tbAuditRecordHistDTO.setStartAt(auditRecordHistDO.getStartAt().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss")));
            if (Objects.isNull(auditRecordHistDO.getEndAt())) {
                tbAuditRecordHistDTO.setEndAt("----");
            } else {
                tbAuditRecordHistDTO.setEndAt(auditRecordHistDO.getEndAt().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss")));
            }
            tbAuditRecordHistDTO.setStatus(ReqStateEnums.sourceOf(auditRecordHistDO.getStatus()));
            tbAuditRecordHistDTOList.add(tbAuditRecordHistDTO);
        }
        return tbAuditRecordHistDTOList;
    }

    /**
     * 构建流程变量集合
     *
     * @param assignee          审批人
     * @param planOper          计划创建者
     * @param planId            计划标识
     * @param processInstanceId 流程实例标识
     * @return Map
     */
    private Map<String, Object> buildVarMap(String assignee, String planOper, String planId, String processInstanceId) {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put(AuditMix.PLAN_KEY, planId);
        varMap.put(AuditMix.PROCESS_INSTANCE_ID, processInstanceId);
        varMap.put(AuditMix.ASSIGNEE_KEY, assignee);
        varMap.put(AuditMix.PLAN_OPER_KEY, planOper);

        //更新状态标识
        int beginStep = 1;
        varMap.put(AuditMix.WHERE_KEY, beginStep);
        return varMap;
    }
}

