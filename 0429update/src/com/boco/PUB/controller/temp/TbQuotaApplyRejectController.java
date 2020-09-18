package com.boco.PUB.controller.temp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbQuotaApplyService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanQuotaApply.ILoanQuotaApplyService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbQuotaApplyRejectController
 * @Description 已驳回的临时额度申请
 * @Author tangxn
 * @Date 20191117 下午2:29
 * @Version 2.0
 **/

@Controller
@RequestMapping(value = "/TbQuotaApplyReject")
public class TbQuotaApplyRejectController extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    ILoanQuotaApplyService loanQuotaApplyService;
    @Autowired
    ITbQuotaApplyService tbQuotaApplyService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;

    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "驳回的临时额度申请列表", funCode = "PUB-04-06", funName = "驳回的临时额度申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaMange/rejected/rollBackAuditList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的临时额度申请列表", funCode = "PUB-04-06", funName = "驳回的临时额度申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(String qaMonth, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus= TbQuotaApply.STATE_DISMISSED+"";
        List<Map<String,Object>> list= loanQuotaApplyService.getPendingAppReq("0",sessionOperInfo.getOperCode(),qaMonth,auditStatus,pager);
        return pageData(list);
    }




    @RequestMapping("/rejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-04-06-01", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId,String processInstanceId) throws Exception {
        authButtons();

        TbQuotaApply tbQuotaApply = tbQuotaApplyService.selectByPK(qaId);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        String oneInfo = tbQuotaApply.getQaOneInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);

        setAttribute("qaId", qaId);
         setAttribute("comments",  BocoUtils.translateComments(comments,"over"));
        setAttribute("TbQuotaApply", tbQuotaApply);
        String fileId=tbQuotaApply.getQaFileId();
        String fileName="暂无附件，请上传";
        if(!"".equals(fileId)&&fileId.length()>0){
            fileName =fileId.substring(fileId.lastIndexOf("_-")+2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbQuotaMange/rejected/rejectDetailPage";
    }



    @RequestMapping("/tbQuotaApplyResubmitAuditUI")
    @SystemLog(tradeName = "重新提交临时额度申请", funCode = "PUB-04-06-01", funName = "重新提交临时额度申请页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int qaid,String taskid) throws Exception {
        authButtons();
        TbQuotaApply tbQuotaApply=tbQuotaApplyService.selectByPK(qaid);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);

        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);
        String oneInfo = tbQuotaApply.getQaOneInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);

        setAttribute("lastUserType",lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("qaid", qaid);
         setAttribute("comments",  BocoUtils.translateComments(comments,"over"));
        setAttribute("TbQuotaApply", tbQuotaApply);
        String fileId=tbQuotaApply.getQaFileId();
        String fileName="暂无附件，请上传";
        if(!"".equals(fileId)&&fileId.length()>0){
            fileName =fileId.substring(fileId.lastIndexOf("_-")+2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbQuotaMange/rejected/rejectCommitPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid,String qaId) throws Exception {
        authButtons();
        ProcessInstance processInstance=workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey=task.getTaskDefinitionKey();
        TbQuotaApply tbQuotaApply = tbQuotaApplyService.selectByPK(Integer.valueOf(qaId));
        //获取下一节点审批人角色
        Map<String,Object> map=new HashMap<>();

        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        /*临时额度标准量*/
        BigDecimal tempNum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            tempNum = list.get(0).getParamTempMount();
        }
        /*申请所填写金额*/
        BigDecimal qaAmt = tbQuotaApply.getQaAmt();
        int unit =tbQuotaApply.getUnit();
        BigDecimal unitNum =BigDecimal.ONE;
        if(unit==2){
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.abs().multiply(unitNum);
        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*条线名称*/
        String lineName = getSessionOperInfo().getLineName();

        if ("0".equals(organLevel1)) {
        } else if ("1".equals(organLevel1)) {
                processKey = AuditMix.TEMP_ONE_MECH_KEY;
                if (qaAmt.compareTo(tempNum) == 1) {
                    auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
                } else {
                    auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
                }
        } else if ("2".equals(organLevel1)) {

        }

        map.put("custType","1");
        String rolecode=webTaskRoleInfoService.getAppUserRole(processKey,processInstance.getProcessDefinitionId(),auditorPrefix,map);
        FdOrgan fdOrgan = fdOrganService.selectByPK(getSessionOrgan().getThiscode());
        String organCode = fdOrgan.getThiscode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(organCode, rolecode,getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray =new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanQuotaApplyRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "单一机构临时额度", funCode = "PUB-04-06", funName = "审批临时额度申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request,String comment,String qaId,String taskId,String assignee,String isAgree,String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);
        update(request);
        String msgNo = (String)variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:"+msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("qaId", qaId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
        if("0".equals(isAgree)){
            Map<String,Object> isNotAgreeMap = loanQuotaApplyService.findIsNotAgreeInfo(task, "1", variables);
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
        varMap.put("organCode",getSessionOrgan().getThiscode());
        //完成任务流程
        ProcessInstance processInstance = loanQuotaApplyService.completeTaskAudit(taskId, comment, varMap, msgMap);
        loanQuotaApplyService.completeTask(processInstance, varMap, msgMap);

        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    /**
     * @Author daice
     * @Description //修改临时额度申请
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     * @return java.lang.String
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改临时额度申请", funCode = "PUB-04-06-01", funName = "修改临时额度申请", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update( HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //最后操作员
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        String qaComb = request.getParameter("qaComb");
        String qaId = request.getParameter("qaId");
        String qaAmt = request.getParameter("qaAmt");
        String qaExpDate = request.getParameter("qaExpDate");
        String qaStartDate = request.getParameter("qaStartDate");
        String qaReason = request.getParameter("qaReason");
        String organName = request.getParameter("organName");
        TbQuotaApply tbQuotaApply = new TbQuotaApply();
        FdOrgan searchFdOrgan = new FdOrgan();
        if(organName!=null&&!"".equals(organName.trim())){
            searchFdOrgan.setOrganname(organName);
            List<FdOrgan> list = fdOrganService.selectByAttr(searchFdOrgan);
            if (list != null && list.size() > 0) {
                tbQuotaApply.setOrganCode(list.get(0).getThiscode());
                tbQuotaApply.setOrganName(organName);
            } else {
                return this.json.returnMsg("false", "新增失败，请确保机构真实存在!").toJson();
            }
        }
        tbQuotaApply.setQaId(Integer.parseInt(qaId));
        tbQuotaApply.setQaComb(qaComb);
        tbQuotaApply.setQaAmt(new BigDecimal(qaAmt));
        tbQuotaApply.setQaStartDate(qaStartDate);
        tbQuotaApply.setQaExpDate(qaExpDate);
        tbQuotaApply.setQaReason(qaReason);
        if(!"".equals(fileName)){
            tbQuotaApply.setQaFileId(fileName);
        }
        tbQuotaApplyService.updateByPK(tbQuotaApply);
        return this.json.returnMsg("true", "修改成功!").toJson();
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
                if("Exclusive Gateway".equals(act.getProperty("name"))){
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
