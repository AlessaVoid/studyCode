package com.boco.AL.controller.punish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.AL.service.ITbPunishListService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbOverPendingAppController
 * @Description 待审批的罚息结果申请
 * @Author tangxn
 * @Date 20191118 下午6:37
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/punishApplyPendingApp")
public class TbPunishPendingAppController extends BaseController {

    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    TbPunishListMapper tbPunishListMapper;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;
    @Autowired
    private IFdOrganService organService;

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "待审批的罚息结果申请", funCode = "AL", funName = "待审批的罚息结果申请列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/pendingApp/tbQuotaApplyPendingAppList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbLineOver")
    @SystemLog(tradeName = "待审批的罚息结果申请", funCode = "AL", funName = "待审批的罚息结果申请列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppTbOver(String month, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbPunishList.STATE_APPROVALING + "";
        List<Map<String, Object>> list = tbPunishListService.getPendingAppReq("1", sessionOperInfo.getOperCode(), month, auditStatus, pager);
        return pageData(list);
    }

    @RequestMapping("/tbQuotaApplyAuditUI")
    @SystemLog(tradeName = "罚息结果申请审批", funCode = "AL", funName = "罚息结果申请任务审批详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
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

        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("id", id);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/pendingApp/tbQuotaApplyPendingDetailPage";
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

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取罚息结果申请审批人员", funCode = "AL", funName = "获取罚息结果申请审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String id) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        TbPunishList tbPunishList = tbPunishListService.selectByPK(Integer.valueOf(id));
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        //我都拿到了下一节点的 taskID之后，因为节点id唯一，我还管他
//        map.put("organLevel",getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        //根据流程id获取流程发起人
        String opercode = tbPunishListMapper.getStartWorkFlowPeople(processInstance.getId());
        FdOper startFdOper = new FdOper();
        startFdOper.setOpercode(opercode);
        List<FdOper> operList = fdOperService.selectByAttr(startFdOper);
        FdOrgan fdOrgan = fdOrganService.selectByPK(operList.get(0).getOrgancode());
        //拿到的是 流程id获取流程发起人 机构级别

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();

        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), workFlowService.getNextTaskAssigneeKey(taskid, AuditMix.REQ_QUOTA_EL_KEY), map);

        String organcode = getSessionOrgan().getThiscode();

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(organcode, rolecode, getSessionOperInfo().getOperCode());
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
    public String auditLoanQuotaApplyRequest(String comment, String id, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
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
        varMap.put("level", getSessionOrgan().getOrganlevel());
        //完成任务流程
        ProcessInstance processInstance = tbPunishListService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbPunishListService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "提交超限额申请成功!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改罚息结果申请
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改罚息结果申请", funCode = "AL", funName = "修改罚息结果申请", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbPunishList tbPunishList) throws Exception {
        tbPunishListService.updateByPK(tbPunishList);
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
                String name1 = String.valueOf(act.getProperty("name"));
                if ("Exclusive Gateway".equals(name1) || name1.contains("Gateway")) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            //如果连接的下一个节点的名称为End，则返回true
                            String name2 = String.valueOf(gw.getProperty("name"));
                            if ("EndEvent".equals(name2) || "End".equals(name2)) {
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
