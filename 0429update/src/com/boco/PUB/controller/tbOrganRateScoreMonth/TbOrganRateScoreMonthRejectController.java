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
 * @Description: �¶������Ѳ��صļ�¼
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
    @SystemLog(tradeName = "���ص��¶������б�", funCode = "AL-03-05", funName = "���ص��¶������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthReject/tbOrganRateScoreMonthRejectIndexList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "���ص��¶������б�����", funCode = "AL-03-05", funName = "���ص��¶������б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
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
    @SystemLog(tradeName = "�����ύ�¶�����ҳ��", funCode = "AL-03-05-01", funName = "�����ύ�¶�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String id,String taskid) throws Exception {
        authButtons();

        //��ѯ�¶�����
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //������ע
        List<Comment> comments = workFlowService.getTaskComments(taskid);
        //�ж���һ��Ƿ��ǽ����ڵ㣬����ǵĻ�������������ʶ���˱�ʶ�����ж��Ƿ���ʾ������Ա
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //�ж��ǲ������һ��������
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
    @SystemLog(tradeName = "�¶���������", funCode = "AL-03-05-01", funName = "�����¶�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment,String id,String taskId,String assignee,String isAgree,String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        WebOperInfo webOperInfo=getSessionOperInfo();
        String organCode =webOperInfo.getOrganCode();
        String msgNo = (String)variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:"+msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("isAgree", isAgree);
        msgMap.put("organCode", organCode);
        msgMap.put("comment", comment);
        msgMap.put("id", id);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
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
        //�����������
        tbOrganRateScoreService.completeTaskAudit(taskId, comment, varMap,msgMap);

        return this.json.returnMsg("true", "�ύ�ɹ�!").toJson();
    }

    @RequestMapping("/listRateScoreRejectDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "AL-03-05-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String id,String processInstanceId) throws Exception {
        authButtons();

        //��ѯ�¶�����
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //��ѯ��ע
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));

        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthReject/tbOrganRateScoreMonthRejectDetailPage";
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();
        ProcessInstance processInstance=workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey=task.getTaskDefinitionKey();

        //��ȡ��һ�ڵ������˽�ɫ
        /*����key*/
        String processKey =  AuditMix.RATE_SCORE;
        /*��ʼ������*/
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
     * ��ȡ��ǰ�ڵ����һ���ڵ��Ƿ��ǽ����ڵ�.
     *
     * @param activityImpl
     * @return
     *
     * <pre>
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
                if("ExclusiveGateway".equals(act.getProperty("name"))){
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if(actList!=null && actList.size()>0){
                        for(PvmTransition gwt:actList){
                            PvmActivity gw = gwt.getDestination();
                            //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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
