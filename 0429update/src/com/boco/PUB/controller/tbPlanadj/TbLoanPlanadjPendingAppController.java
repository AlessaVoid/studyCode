package com.boco.PUB.controller.tbPlanadj;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.tbQuotaAllocate.TbQuotaAllocateService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 信贷计划调整 待审批
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjPendingApp")
public class TbLoanPlanadjPendingAppController extends BaseController {

    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbPlanadjService planadjService;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbQuotaAllocateService tbQuotaAllocateService;

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "待审批的信贷计划调整列表页面", funCode = "PUB-06-03", funName = "待审批的信贷计划调整列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustPendingApp/loanPlanAdjustPendingAppIndexList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbPlanadj")
    @SystemLog(tradeName = "待审批的信贷计划调整列表", funCode = "PUB-06-03", funName = "待审批的信贷计划调整列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppReq(HttpServletRequest request,String planMonth, Pager pager) throws Exception {
        authButtons();
        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");

        if ("planadjmonth".equals(sort)) {
            sort = "planadj_month";

        } else if ("planadjadjamount".equals(sort)) {
            sort = "planadj_adj_amount";

        } else if ("planadjrealincrement".equals(sort)) {
            sort = "planadj_real_increment";

        } else if ("planadjunit".equals(sort)) {
            sort = "planadj_unit";

        } else if ("planadjstatus".equals(sort)) {
            sort = "planadj_status";

        }else if ("planadjcreatetime".equals(sort)) {
            sort = "planadj_create_time";
        }
        if (sort != null) {
            sort = sort + " " + direction;
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();

        String auditStatus = TbReqDetail.STATE_APPROVALING + "";
        List<Map<String, Object>> list = planadjService.getPendingAppReq(sort,sessionOperInfo.getOperCode(), planMonth, auditStatus, pager);

        return pageData(list);
    }

    /*信贷计划调整调整审批页面*/
    @RequestMapping("/listTbPlanDetailAuditUI")
    @SystemLog(tradeName = "信贷计划调整审批详情页面", funCode = "PUB-06-03-03", funName = "信贷计划调整审批详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String planadjId, String taskId) throws Exception {
        authButtons();

        //查询计划调整
        TbPlanadj tbPlanadj = planadjService.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());

        //获取机构号
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskId);
        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);

        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskId);
        setAttribute("planadjId", planadjId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustPendingApp/loanPlanAdjustDetailAuditPage";

    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskId) throws Exception {
        authButtons();

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_MECH;
        }

        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        Task task = workFlowService.getTaskById(taskId);
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(),workFlowService.getNextTaskAssigneeKey(taskId,AuditMix.PLAN_BASE_EL_KEY), map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());

        return JSON.toJSONString(fdOperList);
    }


    @ResponseBody
    @RequestMapping(value = "/auditLoanTbPlanadjRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划调整审批", funCode = "PUB-06-03", funName = "审批信贷计划调整", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment, String planadjId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {

        try {
            Map<String, Object> varMap = new HashMap<String, Object>();
            Task task = workFlowService.getTaskById(taskId);
            Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

            WebOperInfo webOperInfo= getSessionOperInfo();
            String organCode =webOperInfo.getOrganCode();
            String msgNo = (String) variables.get("msgNo");
            logger.info("*********************************审批消息msgNo:" + msgNo);
            //创建集合，用于存放对应的webMsg对象的数据
            Map msgMap = new HashMap();
            msgMap.put("custType", variables.get("custType"));
            msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
            msgMap.put("isAgree", isAgree);
            msgMap.put("organCode", organCode);
            msgMap.put("comment", comment);
            msgMap.put("planadjId", planadjId);
            msgMap.put("operCode", getSessionOperInfo().getOperCode());
            msgMap.put("taskid", taskId);//当前正在执行的任务id
            msgMap.put("lastUserType", lastUserType);
            //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
            if ("0".equals(isAgree)) {

                Map<String, Object> isNotAgreeMap = tbPlanService.findIsNotAgreeInfo(task, "1", variables);
                msgMap.put("assignee", String.valueOf(isNotAgreeMap.get("assigneeByWebMsg")));
                msgMap.put("isResubmit", String.valueOf(isNotAgreeMap.get("assignee")));
                varMap.putAll(isNotAgreeMap);
                varMap.put("organLevel", variables.get("organLevel"));
                varMap.put("isAgree", isAgree);
                varMap.put("assigneeGroup", "");
                varMap.put("task", task);
                varMap.put("state",2);
            } else {
                varMap.put("assignee", assignee);
                msgMap.put("assignee", assignee);
                varMap.put("state",1);

            }
            varMap.put("isAgree", isAgree);

            //完成任务流程
            planadjService.completeTaskAudit(taskId, comment, varMap, msgMap);

            return this.json.returnMsg("true", "成功!").toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "失败").toJson();
        }
    }


    @RequestMapping("/listTbPlanPendingDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-06-03-03", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planadjId, String processInstanceId) throws Exception {

        authButtons();

        //查询计划调整
        TbPlanadj tbPlanadj = planadjService.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //获取机构号
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        //获取审批信息
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustPendingApp/loanPlanAdjustPendingDetailPage";

    }

    /**
     * 获取当前节点的下一个节点是否是结束节点.
     *
     * @param activityImpl
     * @return <pre>
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
                String name1 = String.valueOf(act.getProperty("name"));
                if("ExclusiveGateway".equals(name1)||name1.contains("ExclusiveGateway")){
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if(actList!=null && actList.size()>0){
                        for(PvmTransition gwt:actList){
                            PvmActivity gw = gwt.getDestination();
                            //如果连接的下一个节点的名称为End，则返回true
                            String name2 = String.valueOf(gw.getProperty("name"));
                            if ("EndEvent".equals(name2) || "End".equals(name2) || "End Event".equals(name2)) {
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

