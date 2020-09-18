package com.boco.TONY.biz.planadjust.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.TONY.biz.planadjust.service.IWebLoanPlanAdjustAuditService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划调整业务控制层
 *
 * @author tony
 * @describe TbLoanPlanAdjustAuditController
 * @date 2019-10-10
 */
@Controller
@RequestMapping("/tbPlanAdjust")
public class LoanPlanAdjustAuditController extends BaseController {

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebLoanPlanAdjustAuditService webLoanPlanAdjustAuditService;
    @Autowired
    ILoanPlanService loanPlanService;
    @Autowired
    FdOrganMapper fdOrganMapper;

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03", funName = "加载信贷计划调整列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listTaskListToAuditUI() throws Exception {
        authButtons();
        //todo 当前已审批过的信贷计划,做一次月中调整
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/LoanPlanAdjustAuditPage/loanPlanAdjustAuditIndexList";
    }

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06-03", funName = "驳回的信贷计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/LoanPlanAdjustCommitedPage/loanPlanAdjustSubmitIndexList";
    }

    @RequestMapping("/loanPlanAdjustResubmitAuditUI")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06-05", funName = "信贷计划调整重新审批", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanPlanResubmitAuditUI() throws Exception {
        authButtons();
        String taskId = getParameter("taskId");
        String planId = getParameter("planId");
        PlainResult<TbPlan> result = loanPlanService.selectLoanPlanByPlanId(planId);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        List<Comment> comments = workFlowService.getTaskComments(taskId);
        setAttribute("taskId", taskId);
        setAttribute("planId", planId);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("tbPlanInfo", result.getData());
        List<FdOrganPlanInfo> fdOrganPlanInfoList = loanPlanService.initPlanDetailOrganInfo(planId, fdOrganList);
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/oanPlanAdjustRollBackPage/loanPlanAdjustResubmitAuditPage";
    }

    @RequestMapping("/loadAssigneeAuditedTaskUI")
    @SystemLog(tradeName = "信贷计划审批", funCode = "PUB-06-04", funName = "加载已审批计划调整列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loadAssigneeAuditedTaskUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/LoanPlanAdjustAuditedPage/loanPlanAdjustAuditedTask";
    }

    @RequestMapping("/listPlanDetailAdjustAuditUI")
    @SystemLog(tradeName = "信贷计划审批", funCode = "PUB-06", funName = "加载信贷计划调整详情审批页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listPlanDetailAuditUI() throws Exception {
        authButtons();
        String taskId = getParameter("taskId");
        String planId = getParameter("planId");
        PlainResult<TbPlan> result = loanPlanService.selectLoanPlanByPlanId(planId);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        List<Comment> comments = workFlowService.getTaskComments(taskId);
        Map<String, Object> taskVariables = workFlowService.getTaskVariables(taskId);
        int where = (int) taskVariables.get("where");
        setAttribute("where", where + 1);
        setAttribute("taskId", taskId);
        setAttribute("planId", planId);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("tbPlanInfo", result.getData());
        List<FdOrganPlanInfo> fdOrganPlanInfoList = loanPlanService.initPlanDetailAdjustOrganInfo(planId, fdOrganList);
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/LoanPlanAdjustAuditPage/loanPlanAdjustDetailAuditPage";
    }

    @RequestMapping("/loanPlanAdjustListUI")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "调整信贷计划调整页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanPlanAdjustListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustList/tbPlanAdjustList";
    }

    @RequestMapping("/loanPlanRollBackListUI")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "驳回的信贷计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustAudit/LoanPlanAdjustRollBackPage/loanPlanAdjustRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/startLoanPlanAdjustAudit")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanPlanAdjustAudit(@RequestParam String auditOper, @RequestParam String planId) throws Exception {
        WebOperInfo operInfo = getSessionOperInfo();
        PlainResult<String> result = webLoanPlanAdjustAuditService.startBusinessAuditProcess(operInfo.getOrganCode(), planId, null);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/listAllAssigneeTask")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "列出所有待审批任务", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllAssigneeTask(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo webOperInfo = getSessionOperInfo();
        String assignee = webOperInfo.getOperCode();
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        List<Object> assigneeTaskList = webLoanPlanAdjustAuditService.getAllWaitForAuditTaskList(assignee, pageNo, pageSize);
        return pageData(assigneeTaskList);
    }

    @ResponseBody
    @RequestMapping("/listAllAuditedPlanByOperCode")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "列出所有审批完的计划", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllAuditedPlanByOperCode() throws Exception {
        authButtons();
        setPageParam();
        WebOperInfo webOperInfo = getSessionOperInfo();
        String planOper = webOperInfo.getOperCode();
//        ListResult<TbPlanDTO> result = webLoanPlanAdjustAuditService.listAllLoanPlanByPlanOper(planOper);
        return pageData(null);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanRequireRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06", funName = "信贷计划审批", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest() throws Exception {
        PlainResult<String> result = webLoanPlanAdjustAuditService.auditBusinessTask(getSessionOperInfo().getOperCode(), null);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByOrganLevel")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03", funName = "获取计划审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByOrganLevel(String where) throws Exception {
        authButtons();
        FdOrgan sessionOrgan = getSessionOrgan();
        String organLevel = sessionOrgan.getOrganlevel();
//        ListResult<FdOper> fdOperList = webLoanPlanAdjustAuditService.getOperInfoListByOrganLevel(organLevel, where);
        return null;
    }

    @ResponseBody
    @RequestMapping("/listHistoryLoanRequireWithFinished")
    @SystemLog(tradeName = "机构需求", funCode = "PUB-06", funName = "获取已经走完审批流程的信贷计划信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listHistoryLoanPlanAdjustWithFinished() throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        String assignee = operInfo.getOperCode();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        ListResult<Object> result = webLoanPlanAdjustAuditService.listHistoricBusinessTaskWithFinished(assignee, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        return pageData(result.getData());
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "机构需求", funCode = "PUB-06", funName = "获取被驳回的信贷计划信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList() throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        String assignee = operInfo.getOperCode();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        ListResult<Object> result = webLoanPlanAdjustAuditService.getAllRollbackTaskList(assignee, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        return pageData(result.getData());
    }

    @ResponseBody
    @RequestMapping("/reSubmitRejectTask")
    @SystemLog(tradeName = "机构需求", funCode = "PUB-06", funName = "获取被驳回的信贷计划信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String reSubmitRejectTask() throws Exception {
        authButtons();
        PlainResult<String> result = webLoanPlanAdjustAuditService.resubmitRejectedTask(getParameter(AuditMix.ASSIGNEE_KEY), getParameter(AuditMix.TASK_KEY), null);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "机构需求", funCode = "PUB-06", funName = "获取被驳回的信贷计划信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord() throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        ListResult<Object> result = webLoanPlanAdjustAuditService.getAuditRecordHistRecord(null, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        return pageData(result.getData());
    }
}
