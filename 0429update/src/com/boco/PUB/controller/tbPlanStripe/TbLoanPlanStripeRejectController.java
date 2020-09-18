package com.boco.PUB.controller.tbPlanStripe;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.AuditMix;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 条线计划已驳回的记录
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanStripeReject")
public class TbLoanPlanStripeRejectController extends BaseController {

    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailService tbPlanDetailService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService tbPlanService;

    @RequestMapping("/loanPlanRollBackListUI")
    @SystemLog(tradeName = "驳回的条线计划列表", funCode = "PUB-07-05", funName = "驳回的条线计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanReject/loanPlanRejectIndexList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的条线计划列表数据", funCode = "PUB-07-05", funName = "驳回的条线计划列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(HttpServletRequest request,String planMonth, Pager pager) throws Exception {
        authButtons();
        List<Map<String, Object>> list = null;
        try {
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("planmonth".equals(sort)) {
                sort = "plan_month";

            } else if ("planrealincrement".equals(sort)) {
                sort = "plan_real_increment";

            } else if ("plancreatetime".equals(sort)) {
                sort = "plan_create_time";

            } else if ("planoper".equals(sort)) {
                sort = "plan_oper";

            } else if ("planupdatetime".equals(sort)) {
                sort = "plan_update_time";
            }
            if (sort != null) {
                sort = sort + " " + direction;
            }
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            String auditStatus= TbReqDetail.STATE_DISMISSED+"";
            list = tbPlanService.getPendingPlanStripe(sort, sessionOperInfo.getOperCode(), planMonth,auditStatus, pager, TbPlan.STRIPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    @RequestMapping("/loanTbPlanResubmitAuditUI")
    @SystemLog(tradeName = "重新提交条线计划", funCode = "PUB-07-05-01", funName = "重新提交条线计划页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String planId,String taskid) throws Exception {
        authButtons();
        //查询条线计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装条线计划详情map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
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

        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);

        setAttribute("lastUserType",lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("planId", planId);
        setAttribute("plan", plan);
        setAttribute("organlevel", organlevel);
        return basePath + "/PUB/tbPlanStripe/tbPlanReject/tbPlanDetailRejectAuditPage";

    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanTbPlanRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "条线计划审批", funCode = "PUB-07-05-01", funName = "审批条线计划", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment,String planId,String taskId,String assignee,String isAgree,String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        WebOperInfo webOperInfo=getSessionOperInfo();
        String organCode =webOperInfo.getOrganCode();
        String msgNo = (String)variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:"+msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("organCode", organCode);
        msgMap.put("comment", comment);
        msgMap.put("planId", planId);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if("0".equals(isAgree)){
            Map<String,Object> isNotAgreeMap = tbPlanService.findIsNotAgreeInfo(task, "1", variables);
            msgMap.put("assignee", String.valueOf(isNotAgreeMap.get("assigneeByWebMsg")));
            msgMap.put("isResubmit", String.valueOf(isNotAgreeMap.get("assignee")));
            varMap.putAll(isNotAgreeMap);
            varMap.put("organLevel", variables.get("organLevel"));
            varMap.put("isAgree", isAgree);
            varMap.put("assigneeGroup", "");
            varMap.put("task", task);
        }else{
            varMap.put("assignee", assignee);
            msgMap.put("assignee", assignee);

        }
        varMap.put("isAgree", isAgree);
        varMap.put("isReject","1");
        //完成任务流程
        tbPlanService.completeTaskAuditPlanStripe(taskId, comment, varMap,msgMap);

        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    @RequestMapping("/listTbPlanRejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-07-05-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planId,String processInstanceId) throws Exception {

        authButtons();
        //查询条线计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装条线计划详情map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
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

        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("plan", plan);

        return basePath + "/PUB/tbPlanStripe/tbPlanReject/tbPlanRejectDetailPage";
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_LINE;
            auditorPrefix = AuditMix.PLAN_TOTAL_LINE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_LINE;
            auditorPrefix = AuditMix.PLAN_ONE_LINE_AUDITOR_PREFIX;
        }

        ProcessInstance processInstance=workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey=task.getTaskDefinitionKey();

        //获取下一节点审批人角色
        Map<String,Object> map=new HashMap<>();
        map.put("organLevel",getSessionOrgan().getOrganlevel());
        map.put("custType","1");
        String rolecode=webTaskRoleInfoService.getAppUserRole(processKey,processInstance.getProcessDefinitionId(),auditorPrefix,map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode,getSessionOperInfo().getOperCode());


        return JSON.toJSONString(fdOperList);
    }


    /**
     *
     *
     * 获取当前节点的下一个节点是否是结束节点.
     *
     * @param activityImpl
     * @return
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月1日    	  杜旭    新建
     * </pre>
     */
    public boolean getLastUserType(ActivityImpl activityImpl){
        boolean lastUserType = false;
        //获取当前活动完成之后节点的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if(pvmList!=null && pvmList.size()>0){
            for(PvmTransition pvm:pvmList){
                PvmActivity act = pvm.getDestination();
                //如果是网关的话，通过网关获取下一个节点的名称
                if("ExclusiveGateway".equals(act.getProperty("name"))){
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if(actList!=null && actList.size()>0){
                        for(PvmTransition gwt:actList){
                            PvmActivity gw = gwt.getDestination();
                            //如果连接的下一个节点的名称为End，则返回true
                            if("End".equals(gw.getProperty("name"))){
                                lastUserType = true;
                            }
                        }
                    }
                }
            }
        }
        return lastUserType;
    }



}
