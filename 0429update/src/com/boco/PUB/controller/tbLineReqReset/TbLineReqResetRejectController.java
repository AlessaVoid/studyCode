package com.boco.PUB.controller.tbLineReqReset;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.lineReqReset.ILineReqResetAppService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
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
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbLoanReqRejectController
 * @Description 已驳回的信贷需求
 * @Author daice
 * @Date 2019/11/17 上午11:23
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLineReqResetReject/")
public class TbLineReqResetRejectController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;

    @Autowired
    private WebOperLineMapper webOperLineMapper;
    @Autowired
    ILineReqResetAppService lineReqResetAppService;
    @Autowired
    ITbLineReqresetDetailService tbLineReqresetDetailService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;


    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "驳回的信贷需求列表", funCode = "PUB-10", funName = "驳回的信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqResetManage/reject/loanReqRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的信贷需求列表", funCode = "PUB-10", funName = "驳回的信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(Pager pager, String lineReqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        String organLevel = getSessionOrgan().getOrganlevel();
        setPageParam();
        if ("请选择".equals(lineReqMonth)) {
            lineReqMonth = "";
        }
        String auditStatus = TbReqDetail.STATE_DISMISSED + "";
        List<Map<String, Object>> list = lineReqResetAppService.getPendingAppReq(organLevel, sessionOperInfo, auditStatus, pager);
        if (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (lineReqMonth.equals(map.get("lineReqMonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }


    @RequestMapping("/listReqRejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-10", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int lineReqresetId, String processInstanceId) throws Exception {
        authButtons();
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        setAttr(tbLineReqresetDetail);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/tbLineReqResetManage/reject/loanReqRejectDetailPage";
    }


    /**
     * 通用方法
     *
     * @param tbLineReqresetDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqresetDetail tbLineReqresetDetail) throws Exception {
        String combCodeStr = tbLineReqresetDetail.getLineCombCode();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);

        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());

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


        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            String codeStr = loanCombDO.getCombCode();
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combMap.put("oldNum", "0");
                    if (planIsOk) {
                        for (Map<String, Object> map : planList) {
                            String planCombCode = map.get("loantype").toString();
                            if (planCombCode.equals(codeStr)) {
                                BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                combMap.put("oldNum", oldPlan.divide(unit).toString());
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
    }


    @RequestMapping("/loanReqResubmitAuditUI")
    @SystemLog(tradeName = "重新提交信贷需求", funCode = "PUB-10", funName = "重新提交信贷需求页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int lineReqresetId, String taskid) throws Exception {
        authButtons();
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        setAttr(tbLineReqresetDetail);
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
        setAttribute("lineReqresetId", lineReqresetId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/tbLineReqResetManage/reject/loanReqRejectAuditPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String lineReqresetId) throws Exception {
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
        /*条线名称*/
        String lineName = getSessionOperInfo().getLineName();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.REQRESET_TOTAL_LINE_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQRESET_ONE_LINE_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else if ("2".equals(organLevel)) {

        }

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());


        String lineCodeStr = tbLineReqresetDetailService.selectByPK(Integer.parseInt(lineReqresetId)).getLineCode();
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
        if (newFdOperList.size() == 0) {
            return JSON.toJSONString(fdOperList);
        }
        return JSON.toJSONString(newFdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "机构需求", funCode = "PUB-10", funName = "审批信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request, String comment, String lineReqresetId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        TbLineReqresetDetail tbLineReqresetDetail = getList(request);
        tbLineReqresetDetailService.updateByPK(tbLineReqresetDetail);


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
        msgMap.put("lineReqresetId", lineReqresetId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = lineReqResetAppService.findIsNotAgreeInfo(task, "1", variables);
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
        ProcessInstance processInstance = lineReqResetAppService.completeTaskAudit(taskId, comment, varMap, msgMap);
        lineReqResetAppService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    /**
     * 解析前台传来的参数
     *
     * @param request
     * @return
     */
    private TbLineReqresetDetail getList(HttpServletRequest request) {
        String tbLineReqresetDetailStr = request.getParameter("tbLineReqResetDetail");
        String lineUnit = request.getParameter("lineUnit");
        String lineReqresetId = request.getParameter("lineReqresetId");
        String lineResetRefId = request.getParameter("lineResetrefId");
        String state = request.getParameter("state");

        List<TbLineReqResetDetailDTO> tbLineReqResetDetailDTOS = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqresetDetailStr.split("&");
        for (int i = 0; i < lineReqDetailArr.length; i++) {
            String[] planDetailArr1 = lineReqDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            //eg: 11005293_x001=123 (贷种code_金额分类=金额&贷种code_金额分类=金额)
            String num = planDetailArr1[1];
            String combCode = planDetailArr2[0];
            TbLineReqResetDetailDTO tbLineReqResetDetailDTO = new TbLineReqResetDetailDTO();
            tbLineReqResetDetailDTO.setLineCombCode(combCode);
            tbLineReqResetDetailDTO.setLineUnit(Integer.valueOf(lineUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(lineUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbLineReqResetDetailDTO.setLineReqresetId(Integer.valueOf(lineReqresetId));
            tbLineReqResetDetailDTO.setLineResetrefId(Integer.valueOf(lineResetRefId));
            tbLineReqResetDetailDTO.setLineNum(new BigDecimal(num).multiply(unit));
            tbLineReqResetDetailDTOS.add(tbLineReqResetDetailDTO);
        }
        String numStr = "";
        for (TbLineReqResetDetailDTO tbDTO : tbLineReqResetDetailDTOS) {
            numStr += (tbDTO.getLineNum() + ",");
        }
        if (numStr.endsWith(",")) {
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
        tbLineReqresetDetail.setLineNum(numStr);
        tbLineReqresetDetail.setLineState(Integer.valueOf(state));
        tbLineReqresetDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqresetDetail.setLineCreateTime(

                BocoUtils.getTime());
        tbLineReqresetDetail.setLineReqresetId(Integer.parseInt(lineReqresetId));
        tbLineReqresetDetail.setLineResetrefId(Integer.parseInt(lineResetRefId));

        return tbLineReqresetDetail;
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
