package com.boco.loanreq.controller;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.common.AuditMix;
import com.boco.loanreq.service.ILoanReqAppService;
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
 * @ClassName TbLoanReqPendingAppController
 * @Description 待审批的信贷需求
 * @Author daice
 * @Date 2019/11/16 下午2:06
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLoanReqPendingApp/")
public class TbLoanReqPendingAppController extends BaseController {

    @Autowired
    ILoanReqAppService loanReqAppService;
    @Autowired
    ITbReqDetailService tbReqDetailService;
    @Autowired
    ITbReqListService tbReqListService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private IFdOrganService fdOrganService;


    @RequestMapping("/listUI")
    @SystemLog(tradeName = "待审批的信贷需求", funCode = "PUB-01-08", funName = "待审批的信贷需求列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanReqApp/reqPendingApp/loanReqPendingAppList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppReq")
    @SystemLog(tradeName = "待审批的信贷需求", funCode = "PUB-01-08", funName = "待审批的信贷需求列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppReq(Pager pager, String reqMonth) throws Exception {
        setPageParam();
        if ("请选择".equals(reqMonth)) {
            reqMonth = "";
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbReqDetail.STATE_APPROVALING + "";
        List<Map<String, Object>> list = loanReqAppService.getPendingAppReq("1", sessionOperInfo, auditStatus, pager);
        if (!"".equals(reqMonth) && reqMonth != null && reqMonth.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (reqMonth.equals(map.get("reqmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/listReqDetailAuditUI")
    @SystemLog(tradeName = "机构需求", funCode = "PUB-01-08-01", funName = "信贷需求任务审批详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int reqid, String taskid) throws Exception {
        authButtons();
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqid));
        if (tbReqList.getExpTimeStart() != null) {
            tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
            tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
            tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
            tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
            tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
            tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
            tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
            tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
            tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
            tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));
        }
        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqid);
        setAttr(tbReqList);

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);
        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("reqId", reqid);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/LoanReqApp/reqPendingApp/loanReqDetailAuditPage";
    }

    /**
     * 通用方法
     *
     * @param tbReqList
     * @throws Exception
     */
    private void setAttr(TbReqList tbReqList) throws Exception {
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "到期量");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "净增量");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "预计新发生利率(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "预计期末时点余额");
        map4.put("code", "balance");
        String numTypeStr = tbReqList.getNumType();
        if (numTypeStr.contains("1")) {
            combAmountNameList.add(map1);
        }
        if (numTypeStr.contains("2")) {
            combAmountNameList.add(map2);
        }
        if (numTypeStr.contains("4")) {
            combAmountNameList.add(map3);
        }
        if (numTypeStr.contains("8")) {
            combAmountNameList.add(map4);
        }
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        if (reqType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqList.getReqCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combList.add(combMap);
                        break;
                    }
                }
            }
            setAttribute("combList", combList);
        }
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);


        String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
        FdOper startFdOper = new FdOper();
        startFdOper.setOpercode(opercode);
        List<FdOper> list = fdOperService.selectByAttr(startFdOper);
        FdOrgan fdOrgan = fdOrganService.selectByPK(list.get(0).getOrgancode());
        //拿到的是 流程id获取流程发起人 机构级别
        String organLevel = fdOrgan.getOrganlevel();

        /*流程key*/
        String processKey = "";

        if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_MECH_KEY;
        } else {
            processKey = AuditMix.REQ_TWO_MECH_KEY;
        }

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), workFlowService.getNextTaskAssigneeKey(taskid, AuditMix.REQ_BASE_EL_KEY), map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());


        return JSON.toJSONString(fdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "机构需求", funCode = "PUB-01-08-01", funName = "审批信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment, String reqId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:" + msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("organCode", organCode);
        msgMap.put("operCode", webOperInfo.getOperCode());
        msgMap.put("isAgree", isAgree);
        msgMap.put("comment", comment);
        msgMap.put("reqId", reqId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = loanReqAppService.findIsNotAgreeInfo(task, "1", variables);
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
        //完成任务流程
        ProcessInstance processInstance = loanReqAppService.completeTaskAudit(taskId, comment, varMap, msgMap);
        loanReqAppService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改信贷需求
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改信贷需求", funCode = "PUB-01-08-01", funName = "修改信贷需求", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbReqDetail tbReqDetail) throws Exception {
        tbReqDetailService.updateByPK(tbReqDetail);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }


    /**
     * 获取当前节点的下一个节点是否是结束节点.
     *
     * @param activityImpl
     * @return true最后一个节点；false 不是最终节点
     *
     * <pre>
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
                if ("Exclusive Gateway".equals(name1) || name1.contains("Exclusive Gateway") || "ExclusiveGateway".equals(name1) || name1.contains("ExclusiveGateway")) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
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
