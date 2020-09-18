package com.boco.PUB.controller.reqReset;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.loanReqReset.ILoanReqResetService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName rejectController
 * @Description TODO
 * @Author tangxn
 * @Date 上午10:24
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbReqresetReject/")
public class rejectController extends BaseController {

    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private ILoanReqResetService loanReqResetService;
    @Autowired
    private ITbReqresetDetailService tbReqresetDetailService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;

    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "驳回的信贷需求调整列表", funCode = "PUB-05-06", funName = "驳回的信贷需求调整列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqResetManage/rejected/reqresetRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的信贷需求调整列表", funCode = "PUB-05-06", funName = "驳回的信贷需求调整列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(Pager pager, String reqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        if ("请选择".equals(reqMonth)) {
            reqMonth = "";
        }
        String auditStatus = TbReqresetDetail.STATE_DISMISSED + "";
        List<Map<String, Object>> list = loanReqResetService.getPendingAppReq("0", sessionOperInfo, auditStatus, pager);
        if (!"".equals(reqMonth) && reqMonth != null && reqMonth.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (reqMonth.equals(map.get("reqresetmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }


    //月份  需要查询的机构号
    private BigDecimal getPlanCount(String month, String organ) throws Exception {
        //获取上级机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }
        //获取计划
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //获取计划详情
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

        //计算总金额
        BigDecimal planCount = BigDecimal.ZERO;
        for (TbPlanDetail planDetail : tbPlanDetails) {
            planCount = planCount.add(planDetail.getPdAmount());
        }
        return planCount;
    }

    @RequestMapping("/listReqRejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-05-06", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int reqresetId, String processInstanceId) throws Exception {
        authButtons();
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        setAttr(tbReqresetList);
        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/tbReqResetManage/rejected/reqresetRejectDetailPage";
    }


    /**
     * 通用方法
     *
     * @param tbReqresetList
     * @throws Exception
     */
    private void setAttr(TbReqresetList tbReqresetList) throws Exception {
        int reqresetType = tbReqresetList.getReqresetType();
        String combListStr = "";

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //原计划，调整量，调整后金额

        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            //机构的 计划 查询条件 需要修改为上级机构 code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqresetList.getReqresetUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }


        BigDecimal one_oldTotalNum = BigDecimal.ZERO;
        BigDecimal two_oldTotalNum = BigDecimal.ZERO;
        BigDecimal three_oldTotalNum = BigDecimal.ZERO;
        if (reqresetType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqresetList.getReqresetCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                String codeStr = loanCombDO.getCombCode();
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(codeStr)) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combMap.put("oldNum", "0");
                        if (planIsOk) {
                            for (Map<String, Object> map : planList) {
                                String planCombCode = map.get("loantype").toString();
                                if (planCombCode.equals(codeStr)) {
                                    BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                    combMap.put("oldNum", oldPlan.divide(unit).toString());
                                    if (1 == loanCombDO.getCombLevel()) {
                                        one_oldTotalNum = BigDecimalUtil.add(one_oldTotalNum, oldPlan.divide(unit));
                                    } else if (2 == loanCombDO.getCombLevel()) {
                                        two_oldTotalNum = BigDecimalUtil.add(two_oldTotalNum, oldPlan.divide(unit));
                                    } else if (3 == loanCombDO.getCombLevel()) {
                                        three_oldTotalNum = BigDecimalUtil.add(three_oldTotalNum, oldPlan.divide(unit));
                                    }
                                    break;
                                }
                            }
                        }
                        combList.add(combMap);
                        break;
                    }
                }
            }

            setAttribute("combList", combList);
            setAttribute("one_oldTotalNum", one_oldTotalNum);
            setAttribute("two_oldTotalNum", two_oldTotalNum);
            setAttribute("three_oldTotalNum", three_oldTotalNum);
        }
    }


    @RequestMapping("/loanReqResubmitAuditUI")
    @SystemLog(tradeName = "重新提交信贷需求调整", funCode = "PUB-05-06", funName = "重新提交信贷需求调整页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int reqresetId, String taskid) throws Exception {
        authButtons();
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        setAttr(tbReqresetList);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);

        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));

        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("reqresetId", reqresetId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/tbReqResetManage/rejected/reqresetRejectAuditPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("1".equals(organLevel)) {
            processKey = AuditMix.REQRESET_ONE_MECH_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else {
            processKey = AuditMix.REQRESET_TWO_MECH_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        }

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>(2);
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        return JSON.toJSONString(fdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷需求调整审批", funCode = "PUB-05-06", funName = "审批信贷需求调整", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request, String comment, String reqresetId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        String msgNo = (String) variables.get("msgNo");

        List<TbReqresetDetail> tbPlanDetailList = getList(request, organCode);
        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        tbReqresetDetail.setReqdresetOrgan(organCode);
        tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqresetId));
        tbReqresetDetailService.deleteByAttr(tbReqresetDetail);
        tbReqresetDetailService.insertBatch(tbPlanDetailList);

        logger.info("*********************************审批消息msgNo:" + msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("organCode", organCode);
        msgMap.put("comment", comment);
        msgMap.put("reqresetId", reqresetId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = loanReqResetService.findIsNotAgreeInfo(task, "1", variables);
            msgMap.put("assignee", String.valueOf(isNotAgreeMap.get("assigneeByWebMsg")));
            msgMap.put("isResubmit", String.valueOf(isNotAgreeMap.get("assignee")));
            varMap.putAll(isNotAgreeMap);
            varMap.put("organLevel", variables.get("organLevel"));
            varMap.put("isAgree", isAgree);
            varMap.put("assigneeGroup", "");
            varMap.put("task", task);
        } else {
            varMap.put("assignee", assignee);
            msgMap.put("assignee", assignee);

        }
        varMap.put("isAgree", isAgree);
        varMap.put("isReject", "1");
        //完成任务流程
        ProcessInstance processInstance = loanReqResetService.completeTaskAudit(taskId, comment, varMap, msgMap);
        loanReqResetService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    /**
     * 解析前台传来的参数
     *
     * @param request
     * @param organCode
     * @return
     */
    private List<TbReqresetDetail> getList(HttpServletRequest request, String organCode) {
        String tbReqDetailStr = request.getParameter("tbreqresetDetail");
        String reqUnit = request.getParameter("reqresetUnit");
        String reqId = request.getParameter("reqresetId");
        String state = request.getParameter("state");

        List<TbReqresetDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = tbReqDetailStr.split("&");
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            //eg: 11005293_x001=123 (贷种code_金额分类=金额&贷种code_金额分类=金额)
            String num = planDetailArr1[1];
            String combCodeStr = planDetailArr2[0];
            String combCode = planDetailArr2[1];
            TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
            tbReqresetDetail.setReqdresetCombCode(combCodeStr);
            tbReqresetDetail.setReqdresetUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqId));
            if (combCode.endsWith("Num")) {
                tbReqresetDetail.setReqdresetNum(new BigDecimal(num).multiply(unit));
            }
            if (combCode.endsWith("oldNum")) {
                tbReqresetDetail.setReqdresetOper(new BigDecimal(num).multiply(unit).toString());
            }
            if (combCode.endsWith("newNum")) {
                tbReqresetDetail.setReqdresetUpdateoper(new BigDecimal(num).multiply(unit).toString());
            }
            tbReqresetDetail.setReqdresetOrgan(organCode);
            tbReqresetDetail.setReqdresetState(Integer.valueOf(state));
            tbReqresetDetail.setReqdresetCreatetime(BocoUtils.getTime());
            tbReqresetDetail.setReqdresetUpdatetime(BocoUtils.getTime());
            tbPlanDetailList.add(tbReqresetDetail);
        }
        return tbPlanDetailList;
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改信贷需求调整
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改信贷需求调整", funCode = "PUB-05-06", funName = "修改信贷需求调整", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbReqresetDetail tbReqresetDetail) throws Exception {
        tbReqresetDetailService.updateByPK(tbReqresetDetail);
        return this.json.returnMsg("true", "修改成功!").toJson();
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
    public boolean getLastUserType(ActivityImpl activityImpl) {
        boolean lastUserType = false;
        //获取当前活动完成之后节点的名称
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
                            if ("End".equals(gw.getProperty("name"))) {
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
