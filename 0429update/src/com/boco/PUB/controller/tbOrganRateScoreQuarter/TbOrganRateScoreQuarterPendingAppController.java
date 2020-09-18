package com.boco.PUB.controller.tbOrganRateScoreQuarter;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreQuarterDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbOrganRateScoreQuarterDetail;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: �������ִ�������¼
 * @Date: 2020/02/05
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreQuarterPendingApp")
public class TbOrganRateScoreQuarterPendingAppController extends BaseController {

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
    @Autowired
    private TbOrganRateScoreQuarterDetailService tbOrganRateScoreQuarterDetailService;


    @RequestMapping("/listUI")
    @SystemLog(tradeName = "�������ļ��������б�ҳ��", funCode = "AL-04-03", funName = "�������ļ��������б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterPendingApp/tbOrganRateScoreQuarterPendingAppIndexList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppRateScore")
    @SystemLog(tradeName = "�������ļ��������б�����", funCode = "AL-04-03", funName = "�������ļ��������б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppReq(HttpServletRequest request, HttpServletResponse response,Pager pager) throws Exception {
        authButtons();
        List<Map<String, Object>> data = null;
        try{
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
            String rateMonth = request.getParameter("rateMonth");
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            String auditStatus = TbReqDetail.STATE_APPROVALING + "";
            data = tbOrganRateScoreService.getPendingAppReq(sort, sessionOperInfo.getOperCode(), rateMonth, auditStatus, pager, OrganRateParamElementType.RATE_QUARTER);

        }catch(Exception e){
            e.printStackTrace();
        }
        return pageData(data);
    }

    @RequestMapping("/listRateScoreDetailAuditUI")
    @SystemLog(tradeName = "����������������ҳ��", funCode = "AL-04-03-01", funName = "����������������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(String id, String taskid) throws Exception {
        authButtons();


        //��ѯ��������
        List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreQuarterDetail detail : tbOrganRateScoreQuarterDetails) {
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
        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));

        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterPendingApp/tbOrganRateScoreQuarterDetailAuditPage";

    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ������Ա", funCode = "PUB-01", funName = "��ȡ������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();

        /*����key*/
        String processKey =  AuditMix.RATE_SCORE;

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
    @RequestMapping(value = "/auditLoanRateScoreRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "������������", funCode = "AL-04-03-01", funName = "������������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(String comment, String id, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
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
        msgMap.put("id", id);
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
        tbOrganRateScoreService.completeTaskAuditQuarter(taskId, comment, varMap, msgMap);

        return this.json.returnMsg("true", "�ύ�ɹ�!").toJson();
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
