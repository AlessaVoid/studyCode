package com.boco.PUB.controller.tbOrganRateScoreMonth;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.WebOperInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 月度评分已驳回的记录
 * @Date: 2020/02/05
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreMonthReject")
public class TbOrganRateScoreMonthRejectController extends BaseController {

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;


    @RequestMapping("/loanPlanadjRollBackListUI")
    @SystemLog(tradeName = "驳回的月度评分列表", funCode = "AL-03-05", funName = "驳回的月度评分列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthReject/tbOrganRateScoreMonthRejectIndexList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "驳回的月度评分列表数据", funCode = "AL-03-05", funName = "驳回的月度评分列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(HttpServletRequest request,String rateMonth, Pager pager) throws Exception {
        authButtons();
        List<Map<String, Object>> list = null;
        try {
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("rateMonth".equals(sort)) {
                sort = "rate_month";

            } else if ("rateType".equals(sort)) {
                sort = "rate_type";

            } else if ("rateStatus".equals(sort)) {
                sort = "rate_status";

            } else if ("createTime".equals(sort)) {
                sort = "create_time";

            } else if ("updateTime".equals(sort)) {
                sort = "update_time";

            }
            if (sort != null) {
                sort = sort + " " + direction;
            }
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            String auditStatus= TbReqDetail.STATE_DISMISSED+"";
            list = tbOrganRateScoreService.getPendingAppReq(sort, sessionOperInfo.getOperCode(), rateMonth,auditStatus, pager, OrganRateParamElementType.RATE_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    @RequestMapping("/rateScoreResubmitAuditUI")
    @SystemLog(tradeName = "重新提交月度评分页面", funCode = "AL-03-05-01", funName = "重新提交月度评分页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String id,String taskid) throws Exception {
        authButtons();

        //查询月度评分
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
        boolean lastUserType = this.getLastUserType(activityImpl);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("lastUserType",lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));

        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthReject/tbOrganRateScoreMonthDetailRejectAuditPage";

    }

    @ResponseBody
    @RequestMapping(value = "/auditRateScoreRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "月度评分审批", funCode = "AL-03-05-01", funName = "审批月度评分", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment,String id,String taskId,String assignee,String isAgree,String lastUserType) throws Exception {
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
        msgMap.put("id", id);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
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
        tbOrganRateScoreService.completeTaskAudit(taskId, comment, varMap,msgMap);

        return this.json.returnMsg("true", "提交成功!").toJson();
    }

    @RequestMapping("/listRateScoreRejectDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "AL-03-05-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String id,String processInstanceId) throws Exception {
        authButtons();

        //查询月度评分
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //查询批注
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));

        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthReject/tbOrganRateScoreMonthRejectDetailPage";
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();
        ProcessInstance processInstance=workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey=task.getTaskDefinitionKey();

        //获取下一节点审批人角色
        /*流程key*/
        String processKey =  AuditMix.RATE_SCORE;
        /*初始审批人*/
        String auditorPrefix = AuditMix.RATE_SCORE_AUDITOR_PREFIX;

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
