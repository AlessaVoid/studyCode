package com.boco.AL.controller.punish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.AL.service.ITbPunishListService;
import com.boco.AL.service.ITbPunishResultService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
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
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbOverRejectController
 * @Description �Ѳ��صķ�Ϣ�������
 * @Author tangxn
 * @Date 20191117 ����2:29
 * @Version 2.0
 **/

@Controller
@RequestMapping(value = "/punishApplyReject")
public class TbPunishRejectController extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    private ITbPunishResultService tbPunishResultService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "���صķ�Ϣ��������б�", funCode = "AL", funName = "���صķ�Ϣ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rollBackAuditList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "���صķ�Ϣ��������б�", funCode = "AL", funName = "���صķ�Ϣ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(String month, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbOver.STATE_DISMISSED + "";
        List<Map<String, Object>> list = tbPunishListService.getPendingAppReq("0", sessionOperInfo.getOperCode(), month, auditStatus, pager);
        return pageData(list);
    }


    @RequestMapping("/rejectDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "AL", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int id, String processInstanceId) throws Exception {
        authButtons();

        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttr(tbPunishList);
        setAttribute("id", id);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rejectDetailPage";
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


    @RequestMapping("/tbQuotaApplyResubmitAuditUI")
    @SystemLog(tradeName = "�����ύ��Ϣ�������", funCode = "AL", funName = "�����ύ��Ϣ�������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
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
        setAttr(tbPunishList);
        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("id", id);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/rejected/rejectCommitPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "AL", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String id) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey = task.getTaskDefinitionKey();
        TbPunishList tbPunishList = tbPunishListService.selectByPK(Integer.valueOf(id));
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();

        /*��������д���*/
        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*����*/
        String lineName = getSessionOperInfo().getLineName();
        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        auditorPrefix = AuditMix.PUNISH_BASE_AUDITOR_PREFIX;

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
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
    public String auditLoanReqRequest(String comment, String id, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
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
        varMap.put("isReject", "1");
        varMap.put("level", getSessionOrgan().getOrganlevel());
        //�����������
        ProcessInstance processInstance = tbPunishListService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbPunishListService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "�����ύ�ɹ�!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //�޸ķ�Ϣ�������
     * @Date ����12:27 2019/11/17
     * @Param [tbReqDetail]
     * TODO
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "�޸ķ�Ϣ�������", funCode = "AL", funName = "�޸ķ�Ϣ�������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {

        TbPunishList tbPunishList = new TbPunishList();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String month = request.getParameter("month");
        String collectEndTime = request.getParameter("collectEndTime");
        String note = request.getParameter("note");
        String tbPunishListDetail = request.getParameter("tbPunishListDetail");
        tbPunishList.setId(id);
        tbPunishList.setName(name);
        tbPunishList.setNote(note);
        tbPunishList.setCollectEndTime(collectEndTime);
        tbPunishList.setMonth(month);
        List<TbPunishResult> tbPunishResultList = new ArrayList<>();

        try {
            tbPunishResultList = getList(tbPunishListDetail, tbPunishList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        tbPunishList.setUpdateTime(BocoUtils.getTime());
        tbPunishListService.updateByPK(tbPunishList);

        TbPunishResult tbPunishResult = new TbPunishResult();
        tbPunishResult.setPunishListId(id);
        tbPunishResultService.deleteByAttr(tbPunishResult);
        tbPunishResultService.insertBatch(tbPunishResultList);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param tbPunishListDetail
     * @return
     */
    private List<TbPunishResult> getList(String tbPunishListDetail, TbPunishList tbPunishList) throws Exception {

        //organCode_name=1234&
        FdOrgan thisOrgan = getSessionOrgan();
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        Map<String, String> organMap = new HashMap<>(64);
        organMap = new HashMap<>(36);
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getOrganname());
        }
        List<TbPunishResult> tbPunishResultList = new ArrayList<>();
        String[] planDetailArr = tbPunishListDetail.split("&");
        Map<String, TbPunishResult> tbPunishResultMap = new HashMap<>();
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            TbPunishResult tbPunishResult = tbPunishResultMap.get(planDetailArr2[0]);
            if (tbPunishResult == null) {
                tbPunishResult = new TbPunishResult();
            }
            String nameStr = planDetailArr2[1];
            tbPunishResult.setOrganCode(planDetailArr2[0]);
            tbPunishResult.setOrganName(organMap.get(planDetailArr2[0]));
            tbPunishResult.setEndTime(tbPunishList.getCollectEndTime());
            tbPunishResult.setPunishListId(tbPunishList.getId());
            String valueStr = planDetailArr1[1];
            if ("state".equals(nameStr)) {
                tbPunishResult.setState(Integer.parseInt(valueStr));
            } else if ("planMount".equals(nameStr)) {
                tbPunishResult.setPlanMount(new BigDecimal(valueStr));
            } else if ("monthVacancyAmt".equals(nameStr)) {
                tbPunishResult.setMonthVacancyAmt(new BigDecimal(valueStr));
            } else if ("monthVacancyRate".equals(nameStr)) {
                tbPunishResult.setMonthVacancyRate(new BigDecimal(valueStr));
            } else if ("monthFiveVacancy".equals(nameStr)) {
                tbPunishResult.setMonthFiveVacancy(new BigDecimal(valueStr));
            } else if ("monthShitiPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthShitiPlanMount(new BigDecimal(valueStr));
            } else if ("monthShitiOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverAmt(new BigDecimal(valueStr));
            } else if ("monthShitiOverRate".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverRate(new BigDecimal(valueStr));
            } else if ("monthFiveShitiOver".equals(nameStr)) {
                tbPunishResult.setMonthFiveShitiOver(new BigDecimal(valueStr));
            } else if ("monthPiapjuPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthPiapjuPlanMount(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverAmt(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverRate".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverRate(new BigDecimal(valueStr));
            } else if ("monthFivePiaojuOver".equals(nameStr)) {
                tbPunishResult.setMonthFivePiaojuOver(new BigDecimal(valueStr));
            } else if ("monthTotalPunish".equals(nameStr)) {
                tbPunishResult.setMonthTotalPunish(new BigDecimal(valueStr));
            } else if ("note".equals(nameStr)) {
                tbPunishResult.setNote(valueStr);
            }
            tbPunishResult.setCreateTime(BocoUtils.getTime());
            tbPunishResult.setUpdateTime(BocoUtils.getTime());
            tbPunishResult.setPunishMonth(tbPunishList.getMonth());
            tbPunishResultMap.put(planDetailArr2[0], tbPunishResult);
        }
        Set<String> keySet = tbPunishResultMap.keySet();
        for (String key : keySet) {
            tbPunishResultList.add(tbPunishResultMap.get(key));
        }
        return tbPunishResultList;
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
                if ("Exclusive Gateway".equals(act.getProperty("name"))) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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
