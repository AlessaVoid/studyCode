package com.boco.AL.controller.punish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.AL.service.ITbPunishListService;
import com.boco.AL.service.ITbPunishResultService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
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
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbOverRejectController
 * @Description 已驳回的罚息结果申请
 * @Author tangxn
 * @Date 20191117 下午2:29
 * @Version 2.0
 **/

@Controller
@RequestMapping(value = "/punishApplyReject")
public class TbPunishRejectController extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    private ITbPunishResultService tbPunishResultService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "驳回的罚息结果申请列表", funCode = "AL", funName = "驳回的罚息结果申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rollBackAuditList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的罚息结果申请列表", funCode = "AL", funName = "驳回的罚息结果申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(String month, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbOver.STATE_DISMISSED + "";
        List<Map<String, Object>> list = tbPunishListService.getPendingAppReq("0", sessionOperInfo.getOperCode(), month, auditStatus, pager);
        return pageData(list);
    }


    @RequestMapping("/rejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "AL", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int id, String processInstanceId) throws Exception {
        authButtons();

        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttr(tbPunishList);
        setAttribute("id", id);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rejectDetailPage";
    }


    /**
     * @param tbPunishList 罚息列表
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "状态");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "当月总计划(亿元)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "月末闲置额度(亿元)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "月末闲置率(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "总闲置费(万元)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "实体考核计划(亿元)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "实体月末超计划额度(亿元)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "实体月末超计划幅度(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "实体超计划费(万元)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据考核计划(亿元)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划额度(亿元)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划幅度(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据超计划费(万元)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "罚息总计(万元)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "反馈意见");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }


    @RequestMapping("/tbQuotaApplyResubmitAuditUI")
    @SystemLog(tradeName = "重新提交罚息结果申请", funCode = "AL", funName = "重新提交罚息结果申请页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int id, String taskid) throws Exception {
        authButtons();
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);


        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);

        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);
        setAttr(tbPunishList);
        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("id", id);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rejectCommitPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "AL", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String id) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey = task.getTaskDefinitionKey();
        TbPunishList tbPunishList = tbPunishListService.selectByPK(Integer.valueOf(id));
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();

        /*申请所填写金额*/
        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*名称*/
        String lineName = getSessionOperInfo().getLineName();
        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        auditorPrefix = AuditMix.PUNISH_BASE_AUDITOR_PREFIX;

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanQuotaApplyRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "罚息结果申请审批", funCode = "AL", funName = "审批罚息结果申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment, String id, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:" + msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("id", id);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = tbPunishListService.findIsNotAgreeInfo(task, "1", variables);
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
        varMap.put("level", getSessionOrgan().getOrganlevel());
        //完成任务流程
        ProcessInstance processInstance = tbPunishListService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbPunishListService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "重新提交成功!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改罚息结果申请
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     * TODO
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改罚息结果申请", funCode = "AL", funName = "修改罚息结果申请", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {

        TbPunishList tbPunishList = new TbPunishList();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String month = request.getParameter("month");
        String collectEndTime = request.getParameter("collectEndTime");
        String note = request.getParameter("note");
        String tbPunishListDetail = request.getParameter("tbPunishListDetail");
        tbPunishList.setId(id);
        tbPunishList.setName(name);
        tbPunishList.setNote(note);
        tbPunishList.setCollectEndTime(collectEndTime);
        tbPunishList.setMonth(month);
        List<TbPunishResult> tbPunishResultList = new ArrayList<>();

        try {
            tbPunishResultList = getList(tbPunishListDetail, tbPunishList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        tbPunishList.setUpdateTime(BocoUtils.getTime());
        tbPunishListService.updateByPK(tbPunishList);

        TbPunishResult tbPunishResult = new TbPunishResult();
        tbPunishResult.setPunishListId(id);
        tbPunishResultService.deleteByAttr(tbPunishResult);
        tbPunishResultService.insertBatch(tbPunishResultList);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * 解析前台传来的参数
     *
     * @param tbPunishListDetail
     * @return
     */
    private List<TbPunishResult> getList(String tbPunishListDetail, TbPunishList tbPunishList) throws Exception {

        //organCode_name=1234&
        FdOrgan thisOrgan = getSessionOrgan();
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        Map<String, String> organMap = new HashMap<>(64);
        organMap = new HashMap<>(36);
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getOrganname());
        }
        List<TbPunishResult> tbPunishResultList = new ArrayList<>();
        String[] planDetailArr = tbPunishListDetail.split("&");
        Map<String, TbPunishResult> tbPunishResultMap = new HashMap<>();
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            TbPunishResult tbPunishResult = tbPunishResultMap.get(planDetailArr2[0]);
            if (tbPunishResult == null) {
                tbPunishResult = new TbPunishResult();
            }
            String nameStr = planDetailArr2[1];
            tbPunishResult.setOrganCode(planDetailArr2[0]);
            tbPunishResult.setOrganName(organMap.get(planDetailArr2[0]));
            tbPunishResult.setEndTime(tbPunishList.getCollectEndTime());
            tbPunishResult.setPunishListId(tbPunishList.getId());
            String valueStr = planDetailArr1[1];
            if ("state".equals(nameStr)) {
                tbPunishResult.setState(Integer.parseInt(valueStr));
            } else if ("planMount".equals(nameStr)) {
                tbPunishResult.setPlanMount(new BigDecimal(valueStr));
            } else if ("monthVacancyAmt".equals(nameStr)) {
                tbPunishResult.setMonthVacancyAmt(new BigDecimal(valueStr));
            } else if ("monthVacancyRate".equals(nameStr)) {
                tbPunishResult.setMonthVacancyRate(new BigDecimal(valueStr));
            } else if ("monthFiveVacancy".equals(nameStr)) {
                tbPunishResult.setMonthFiveVacancy(new BigDecimal(valueStr));
            } else if ("monthShitiPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthShitiPlanMount(new BigDecimal(valueStr));
            } else if ("monthShitiOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverAmt(new BigDecimal(valueStr));
            } else if ("monthShitiOverRate".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverRate(new BigDecimal(valueStr));
            } else if ("monthFiveShitiOver".equals(nameStr)) {
                tbPunishResult.setMonthFiveShitiOver(new BigDecimal(valueStr));
            } else if ("monthPiapjuPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthPiapjuPlanMount(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverAmt(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverRate".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverRate(new BigDecimal(valueStr));
            } else if ("monthFivePiaojuOver".equals(nameStr)) {
                tbPunishResult.setMonthFivePiaojuOver(new BigDecimal(valueStr));
            } else if ("monthTotalPunish".equals(nameStr)) {
                tbPunishResult.setMonthTotalPunish(new BigDecimal(valueStr));
            } else if ("note".equals(nameStr)) {
                tbPunishResult.setNote(valueStr);
            }
            tbPunishResult.setCreateTime(BocoUtils.getTime());
            tbPunishResult.setUpdateTime(BocoUtils.getTime());
            tbPunishResult.setPunishMonth(tbPunishList.getMonth());
            tbPunishResultMap.put(planDetailArr2[0], tbPunishResult);
        }
        Set<String> keySet = tbPunishResultMap.keySet();
        for (String key : keySet) {
            tbPunishResultList.add(tbPunishResultMap.get(key));
        }
        return tbPunishResultList;
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
