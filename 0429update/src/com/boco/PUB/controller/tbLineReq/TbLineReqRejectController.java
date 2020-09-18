package com.boco.PUB.controller.tbLineReq;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.lineReq.ILineReqAppService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
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
import java.util.*;

/**
 * @ClassName TbLoanReqRejectController
 * @Description 已驳回的信贷需求
 * @Author daice
 * @Date 2019/11/17 上午11:23
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLineReqReject/")
public class TbLineReqRejectController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    ILineReqAppService lineReqAppService;
    @Autowired
    ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    private WebOperLineMapper webOperLineMapper;


    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "驳回的信贷需求列表", funCode = "PUB-09", funName = "驳回的信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的信贷需求列表", funCode = "PUB-09", funName = "驳回的信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(Pager pager, String lineReqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        String organLevel = getSessionOrgan().getOrganlevel();
        setPageParam();
        if ("请选择".equals(lineReqMonth)) {
            lineReqMonth = "";
        }
        String auditStatus = TbReqDetail.STATE_DISMISSED + "";
        List<Map<String, Object>> list = lineReqAppService.getPendingAppReq(organLevel, sessionOperInfo, auditStatus, pager);
        if (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (lineReqMonth.equals(map.get("linereqmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }


    @RequestMapping("/listReqRejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-09", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int lineReqId, String processInstanceId) throws Exception {
        authButtons();
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRejectDetailPage";
    }


    /**
     * 通用方法
     *
     * @param tbLineReqDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqDetail tbLineReqDetail) throws Exception {
        String combCodeStr = tbLineReqDetail.getLineCombCode();
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
        TbReqList  tbReqList =tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
        for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combList.add(combMap);
                    break;
                }
            }
        }
        setAttribute("combList", combList);
    }


    @RequestMapping("/loanReqResubmitAuditUI")
    @SystemLog(tradeName = "重新提交信贷需求", funCode = "PUB-09", funName = "重新提交信贷需求页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int lineReqId, String taskid) throws Exception {
        authButtons();
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);

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
        setAttribute("lineReqId", lineReqId);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRejectAuditPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid,String lineReqId) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey = task.getTaskDefinitionKey();

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.REQ_TOTAL_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("2".equals(organLevel)) {

        }

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());



        String lineCodeStr = tbLineReqDetailService.selectByPK(Integer.parseInt(lineReqId)).getLineCode();
        List<FdOper> newFdOperList = new ArrayList<>();
        for (FdOper oper : fdOperList) {
            WebOperLineDO searchOper = new WebOperLineDO();
            searchOper.setOperCode(oper.getOpercode());
            searchOper.setStatus(1);
            List<WebOperLineDO> tempList = webOperLineMapper.getAllWebOperLineByOperCode(searchOper);
            if (tempList != null && tempList.size() > 0) {
                for (WebOperLineDO tempOper : tempList) {
                    if (lineCodeStr.equals(tempOper.getLineId())) {
                        newFdOperList.add(oper);
                        break;
                    }
                }
            }
        }
        if(newFdOperList.size()==0){
            return JSON.toJSONString(fdOperList);
        }
        return JSON.toJSONString(newFdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "机构需求", funCode = "PUB-09", funName = "审批信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request, String comment, String lineReqId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        TbLineReqDetail tbLineReqDetail = getListNew(request);
        tbLineReqDetailService.updateByPK(tbLineReqDetail);

        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:" + msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("organCode", organCode);
        msgMap.put("operCode", webOperInfo.getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("lineReqId", lineReqId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = lineReqAppService.findIsNotAgreeInfo(task, "1", variables);
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
        //完成任务流程
        ProcessInstance processInstance = lineReqAppService.completeTaskAudit(taskId, comment, varMap, msgMap);
        lineReqAppService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "重新提交成功!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改信贷需求
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改信贷需求", funCode = "PUB-09", funName = "修改信贷需求", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbLineReqDetail tbLineReqDetail) throws Exception {
        tbLineReqDetailService.updateByPK(tbLineReqDetail);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * 解析前台传来的参数
     *
     * @param request
     * @return
     */
    private TbLineReqDetail getListNew(HttpServletRequest request) {
        String tbLineReqDetailStr = request.getParameter("tbLineReqDetail");
        String reqUnit = request.getParameter("lineUnit");
        String lineReqId = request.getParameter("lineReqId");
        String lineRefId = request.getParameter("lineRefId");
        String state = request.getParameter("state");

        List<TbLineReqDetailDTO> tbLineReqDetails = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqDetailStr.split("&");
        Map<String, TbLineReqDetailDTO> map = new HashMap<>();
        for (int i = 0; i < lineReqDetailArr.length; i++) {
            String[] planDetailArr1 = lineReqDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            String num = planDetailArr1[1]; //一个数值
            String combCode = planDetailArr2[0]; //code
            String numTypeCode = planDetailArr2[1]; //expNum;reqNum;rate;balance
            TbLineReqDetailDTO tbReqDetail = map.get(combCode);
            if (tbReqDetail == null) {
                tbReqDetail = new TbLineReqDetailDTO();
                tbReqDetail.setLineCombCode(combCode);
            }
            tbReqDetail.setLineUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setLineRefId(Integer.valueOf(lineRefId));
            if ("expNum".equals(numTypeCode)) {
                tbReqDetail.setLineExpnum(new BigDecimal(num).multiply(unit));
            } else if ("reqNum".equals(numTypeCode)) {
                tbReqDetail.setLineReqnum(new BigDecimal(num).multiply(unit));
            } else if ("rate".equals(numTypeCode)) {
                tbReqDetail.setLineRate(new BigDecimal(num));
            } else if ("balance".equals(numTypeCode)) {
                tbReqDetail.setLineBalance(new BigDecimal(num).multiply(unit));
            }
            tbReqDetail.setLineReqId(Integer.parseInt(lineReqId));
            map.put(combCode, tbReqDetail);
        }

        Collection<TbLineReqDetailDTO> tbReqDetails = map.values();
        for (TbLineReqDetailDTO tb : tbReqDetails) {
            tbLineReqDetails.add(tb);
        }

        String expNumStr = "";
        String reqNumStr = "";
        String rateStr = "";
        String balanceStr = "";
        for (TbLineReqDetailDTO tbDTO : tbLineReqDetails) {
            expNumStr += (tbDTO.getLineExpnum() + ",");
            reqNumStr += (tbDTO.getLineReqnum() + ",");
            rateStr += (tbDTO.getLineRate() + ",");
            balanceStr += (tbDTO.getLineBalance() + ",");
        }
        if (expNumStr.endsWith(",")) {
            expNumStr = expNumStr.substring(0, expNumStr.length() - 1);
        }
        if (reqNumStr.endsWith(",")) {
            reqNumStr = reqNumStr.substring(0, reqNumStr.length() - 1);
        }
        if (rateStr.endsWith(",")) {
            rateStr = rateStr.substring(0, rateStr.length() - 1);
        }
        if (balanceStr.endsWith(",")) {
            balanceStr = balanceStr.substring(0, balanceStr.length() - 1);
        }
        TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
        tbLineReqDetail.setLineExpnum(expNumStr);
        tbLineReqDetail.setLineReqnum(reqNumStr);
        tbLineReqDetail.setLineRate(rateStr);
        tbLineReqDetail.setLineBalance(balanceStr);
        tbLineReqDetail.setLineState(Integer.valueOf(state));
        tbLineReqDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineCreateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineReqId(Integer.parseInt(lineReqId));
        tbLineReqDetail.setLineRefId(Integer.parseInt(lineRefId));

        return tbLineReqDetail;
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
