package com.boco.PUB.controller.tbPlan;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanDetailBackupService;
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
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: �Ŵ��ƻ� ��������¼
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanPendingApp")
public class TbLoanPlanPendingAppController extends BaseController {

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
    @Autowired
    private TbPlanDetailBackupService tbPlanDetailBackupService;


    @RequestMapping("/listUI")
    @SystemLog(tradeName = "���������Ŵ��ƻ��б�ҳ��", funCode = "PUB-03-07", funName = "���������Ŵ��ƻ��б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanPendingApp/loanPlanPendingAppIndexList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbPlan")
    @SystemLog(tradeName = "���������Ŵ��ƻ��б�����", funCode = "PUB-03-07", funName = "���������Ŵ��ƻ��б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppReq(HttpServletRequest request, HttpServletResponse response,Pager pager) throws Exception {
        authButtons();
        List<Map<String, Object>> data = null;
        try{
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
            String planMonth = request.getParameter("planMonth");
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            String auditStatus = TbReqDetail.STATE_APPROVALING + "";
            data = tbPlanService.getPendingAppReq(sort,sessionOperInfo.getOperCode(), planMonth, auditStatus, pager, TbPlan.PLAN);

        }catch(Exception e){
            e.printStackTrace();
        }
        return pageData(data);
    }

    @RequestMapping("/listTbPlanDetailAuditUI")
    @SystemLog(tradeName = "�Ŵ��ƻ���������ҳ��", funCode = "PUB-03-07-02", funName = "�Ŵ��ƻ���������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String planId, String taskid) throws Exception {
        authButtons();

        //��ѯ�Ŵ��ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ�Ŵ��ƻ�����map
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

        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }
        //��ѯ����
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //������ע
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //�ж���һ��Ƿ��ǽ����ڵ㣬����ǵĻ�������������ʶ���˱�ʶ�����ж��Ƿ���ʾ������Ա
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //�ж��ǲ������һ��������
        boolean lastUserType = this.getLastUserType(activityImpl);

        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("plan", plan);

        return basePath + "/PUB/tbPlan/tbPlanPendingApp/tbPlanDetailAuditPage";

    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ������Ա", funCode = "PUB-01", funName = "��ȡ������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();

        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_ONE_MECH;
        }

        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(),workFlowService.getNextTaskAssigneeKey(taskid,AuditMix.PLAN_BASE_EL_KEY), map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());

        return JSON.toJSONString(fdOperList);
    }


    @ResponseBody
    @RequestMapping(value = "/auditLoanTbPlanRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "�Ŵ��ƻ�����", funCode = "PUB-03-07-01", funName = "�����Ŵ��ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment, String planId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        WebOperInfo webOperInfo= getSessionOperInfo();
        String organCode =webOperInfo.getOrganCode();
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:" + msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("organCode", organCode);
        msgMap.put("isAgree", isAgree);
        msgMap.put("comment", comment);
        msgMap.put("planId", planId);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
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
        //�����������
        tbPlanService.completeTaskAudit(taskId, comment, varMap, msgMap);

        return this.json.returnMsg("true", "�ύ�ɹ�!").toJson();
    }


    @RequestMapping("/listTbPlanPendingDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-03-07-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planId, String processInstanceId) throws Exception {

        authButtons();

        //��ѯ�Ŵ��ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ�Ŵ��ƻ�����map
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

        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //��ѯ����
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);



        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("plan", plan);

        return basePath + "/PUB/tbPlan/tbPlanPendingApp/tbPlanPendingDetailPage";
    }



    /**
     * ��ȡ��ǰ�ڵ����һ���ڵ��Ƿ��ǽ����ڵ�.
     *
     * @param activityImpl
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��1��    	  ����    �½�
     * </pre>
     */
    public boolean getLastUserType(ActivityImpl activityImpl){
        boolean lastUserType = false;
        //��ȡ��ǰ����֮��ڵ������
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if(pvmList!=null && pvmList.size()>0){
            for(PvmTransition pvm:pvmList){
                PvmActivity act = pvm.getDestination();
                //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                String name1 = String.valueOf(act.getProperty("name"));
                if("ExclusiveGateway".equals(name1)||name1.contains("ExclusiveGateway")){
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if(actList!=null && actList.size()>0){
                        for(PvmTransition gwt:actList){
                            PvmActivity gw = gwt.getDestination();
                            //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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
