package com.boco.TONY.biz.planadjust.service.impl;

import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.flownode.service.IWebFlowNodeService;
import com.boco.TONY.biz.planadjust.service.IWebLoanPlanAdjustAuditService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 信贷计划调整审批业务逻辑实现
 *
 * @author tony
 * @describe WebLoanPlanAuditServiceImpl
 * @date 2019-09-26
 */
@Service
public class WebLoanPlanAdjustAuditServiceImpl implements IWebLoanPlanAdjustAuditService {
    @Autowired
    FdOperMapper fdOperMapper;
    @Autowired
    FdOrganMapper fdOrganMapper;

    @Autowired
    TbPlanMapper tbPlanMapper;
    @Autowired
    LoanPlanAdjustmentMapper loanPlanAdjustmentMapper;

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
}

