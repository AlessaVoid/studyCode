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
 * @Description �������ķ�Ϣ�������
 * @Author tangxn
 * @Date 20191118 ����6:37
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
    @SystemLog(tradeName = "�������ķ�Ϣ�������", funCode = "AL", funName = "�������ķ�Ϣ��������б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/pendingApp/tbQuotaApplyPendingAppList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbLineOver")
    @SystemLog(tradeName = "�������ķ�Ϣ�������", funCode = "AL", funName = "�������ķ�Ϣ��������б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppTbOver(String month, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbPunishList.STATE_APPROVALING + "";
        List<Map<String, Object>> list = tbPunishListService.getPendingAppReq("1", sessionOperInfo.getOperCode(), month, auditStatus, pager);
        return pageData(list);
    }

    @RequestMapping("/tbQuotaApplyAuditUI")
    @SystemLog(tradeName = "��Ϣ�����������", funCode = "AL", funName = "��Ϣ�������������������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int id, String taskid) throws Exception {
        authButtons();
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);


        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());

        //������ע
        List<Comment> comments = workFlowService.getTaskComments(taskid);

        //�ж���һ��Ƿ��ǽ����ڵ㣬����ǵĻ�������������ʶ���˱�ʶ�����ж��Ƿ���ʾ������Ա
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //�ж��ǲ������һ��������
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
     * @param tbPunishList ��Ϣ�б�
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "״̬");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�����ܼƻ�(��Ԫ)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "��ĩ���ö��(��Ԫ)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "��ĩ������(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "�����÷�(��Ԫ)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "ʵ�忼�˼ƻ�(��Ԫ)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "ʵ����ĩ���ƻ����(��Ԫ)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "ʵ����ĩ���ƻ�����(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "ʵ�峬�ƻ���(��Ԫ)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݿ��˼ƻ�(��Ԫ)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ����(��Ԫ)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ�����(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݳ��ƻ���(��Ԫ)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "��Ϣ�ܼ�(��Ԫ)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "�������");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ��Ϣ�������������Ա", funCode = "AL", funName = "��ȡ��Ϣ�������������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String id) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        TbPunishList tbPunishList = tbPunishListService.selectByPK(Integer.valueOf(id));
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        //�Ҷ��õ�����һ�ڵ�� taskID֮����Ϊ�ڵ�idΨһ���һ�����
//        map.put("organLevel",getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        //��������id��ȡ���̷�����
        String opercode = tbPunishListMapper.getStartWorkFlowPeople(processInstance.getId());
        FdOper startFdOper = new FdOper();
        startFdOper.setOpercode(opercode);
        List<FdOper> operList = fdOperService.selectByAttr(startFdOper);
        FdOrgan fdOrgan = fdOrganService.selectByPK(operList.get(0).getOrgancode());
        //�õ����� ����id��ȡ���̷����� ��������

        /*����key*/
        String processKey = "";
        /*��������*/
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
    @SystemLog(tradeName = "��Ϣ�����������", funCode = "AL", funName = "������Ϣ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanQuotaApplyRequest(String comment, String id, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:" + msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("id", id);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
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
        //�����������
        ProcessInstance processInstance = tbPunishListService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbPunishListService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "�ύ���޶�����ɹ�!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //�޸ķ�Ϣ�������
     * @Date ����12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "�޸ķ�Ϣ�������", funCode = "AL", funName = "�޸ķ�Ϣ�������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbPunishList tbPunishList) throws Exception {
        tbPunishListService.updateByPK(tbPunishList);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
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
    public boolean getLastUserType(ActivityImpl activityImpl) {
        boolean lastUserType = false;
        //��ȡ��ǰ����֮��ڵ������
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                PvmActivity act = pvm.getDestination();
                //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                String name1 = String.valueOf(act.getProperty("name"));
                if ("Exclusive Gateway".equals(name1) || name1.contains("Gateway")) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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
