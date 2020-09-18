package com.boco.TONY.biz.loanplan.service.impl;

import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.mapper.LoanPlanDetailMapper;
import com.boco.SYS.mapper.TbPlanMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.biz.loanplan.service.IWebLoanPlanAuditService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.google.common.base.Preconditions;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划审批接口实现
 *
 * @author tony
 * @describe WebLoanPlanAuditServiceImpl
 * @date 2019-11-17
 */
@Service
public class WebLoanPlanAuditServiceImpl implements IWebLoanPlanAuditService {
    @Autowired
    IWorkFlowService workFlowService;

    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;

    @Autowired
    TbPlanMapper tbPlanMapper;
    @Autowired
    LoanPlanDetailMapper loanPlanDetailMapper;

    @Override
    public PlainResult<String> startBusinessAuditProcess(String assignee, String businessKey, Map<String, Object> businessMap) {
        PlainResult<String> result = new PlainResult<>();
        try {
            Map<String, Object> varStartMap = new HashMap<>();
            varStartMap.put(AuditMix.BUSINESS_KEY, businessKey);
            ProcessInstance processInstance = workFlowService.startProcess(AuditMix.PLAN_TOTAL_MECH, varStartMap);
            String processInstanceId = processInstance.getId();
            Task task = workFlowService.getTaskByProcessInstanceId(processInstanceId);

            Map<String, Object> varMap = new HashMap<>(16);

            varMap.put(AuditMix.LAST_ASSIGNEE_KEY, assignee);
            String taskId = task.getId();
            workFlowService.claim(taskId, WebContextUtil.getSessionUser().getOpercode());
            workFlowService.completeTask(task.getId(), AuditMix.INIT_COMMENT, varMap);

            Task auditTask = workFlowService.getTaskByProcessInstanceId(processInstanceId);
            workFlowService.claim(auditTask.getId(), assignee);

            //更新信贷计划状态-审批
            TbPlan tbPlanDO = new TbPlan().setPlanId(businessKey);
            tbPlanDO.setPlanStatus(LoanStateEnums.STATE_APPROVING.status);
            tbPlanMapper.updateLoanPlanState(tbPlanDO);
            return result.success("audit|start", "start audit task process.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "start audit task process.");
        }
    }

    @Override
    public PlainResult<String> auditBusinessTask(String currentAssignee, Map<String, Object> businessMap) throws Exception {
        HttpServletRequest request = WebContextUtil.getRequest();
        String taskId = request.getParameter(AuditMix.TASK_KEY);
        String planId = request.getParameter(AuditMix.PLAN_KEY);
        String comment = request.getParameter(AuditMix.COMMENT_KEY);
        String isAgree = request.getParameter(AuditMix.IS_AGREE_KEY);
        String assignee = request.getParameter(AuditMix.ASSIGNEE_KEY);
        String organCode = WebContextUtil.getSessionOrgan().getThiscode();
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
        Preconditions.checkArgument(null != taskId && null != planId && null != organCode && null != isAgree && null != comment);
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

    @Override
    public ListResult<Object> getAllRollbackTaskList(String assignee, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PlainResult<String> resubmitRejectedTask(String assignee, String taskId, Map<String, Object> businessMap) {
        return null;
    }

    @Override
    public ListResult<Object> getAuditRecordHistRecord(Map<String, Object> businessMap, int pageNo, int pageSize) {
        return null;
    }

    public Map<String, Object> buildVarMap(String assignee, String businessKey, String processInstanceId) {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put(AuditMix.PLAN_KEY, businessKey);
        varMap.put(AuditMix.PROCESS_INSTANCE_ID, processInstanceId);
        varMap.put(AuditMix.ASSIGNEE_KEY, assignee);
        //更新状态标识
        return varMap;
    }
}
