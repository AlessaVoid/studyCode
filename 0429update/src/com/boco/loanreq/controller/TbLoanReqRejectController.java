package com.boco.loanreq.controller;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
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

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbLoanReqRejectController
 * @Description �Ѳ��ص��Ŵ�����
 * @Author daice
 * @Date 2019/11/17 ����11:23
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLoanReqReject/")
public class TbLoanReqRejectController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    ILoanReqAppService loanReqAppService;
    @Autowired
    ITbReqListService tbReqListService;
    @Autowired
    ITbReqDetailService tbReqDetailService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;


    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "���ص��Ŵ������б�", funCode = "PUB-01-07", funName = "���ص��Ŵ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanReqApp/reqReject/loanReqRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "���ص��Ŵ������б�", funCode = "PUB-01-07", funName = "���ص��Ŵ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(Pager pager, String reqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        if ("��ѡ��".equals(reqMonth)) {
            reqMonth = "";
        }
        String auditStatus = TbReqDetail.STATE_DISMISSED + "";
        List<Map<String, Object>> list = loanReqAppService.getPendingAppReq("0", sessionOperInfo, auditStatus, pager);
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


    @RequestMapping("/listReqRejectDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-01-07-01", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int reqId, String processInstanceId) throws Exception {
        authButtons();
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
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
        setAttribute("reqId", reqId);
        setAttr(tbReqList);
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/LoanReqApp/reqReject/loanReqRejectDetailPage";
    }


    /**
     * ͨ�÷���
     *
     * @param tbReqList
     * @throws Exception
     */
    private void setAttr(TbReqList tbReqList) throws Exception {
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "������");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "Ԥ���·�������(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "Ԥ����ĩʱ�����");
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


    @RequestMapping("/loanReqResubmitAuditUI")
    @SystemLog(tradeName = "�����ύ�Ŵ�����", funCode = "PUB-01-07-01", funName = "�����ύ�Ŵ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int reqid, String taskid) throws Exception {
        authButtons();
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqid));
        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqid);
        setAttr(tbReqList);

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
        setAttribute("reqId", reqid);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/LoanReqApp/reqReject/loanReqRejectAuditPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);

        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();
        /*��������*/
        String lineName = getSessionOperInfo().getLineName();

        if ("0".equals(organLevel)) {

        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_MECH_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else {
            processKey = AuditMix.REQ_TWO_MECH_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        }

        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());


        return JSON.toJSONString(fdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-01-07-01", funName = "�����Ŵ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request, String comment, String reqId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);
        String organCode = getSessionOrgan().getThiscode();
        List<TbReqDetail> tbPlanDetailList = getListNew(request, organCode);
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdOrgan(organCode);
        tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
        tbReqDetailService.deleteByAttr(tbReqDetail);
        tbReqDetailService.insertBatch(tbPlanDetailList);


        WebOperInfo webOperInfo = getSessionOperInfo();
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:" + msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("isAgree", isAgree);
        msgMap.put("organCode", getSessionOrgan().getThiscode());
        msgMap.put("operCode", webOperInfo.getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("reqId", reqId);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
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
        varMap.put("isReject", "1");
        //�����������
        try {
            ProcessInstance processInstance = loanReqAppService.completeTaskAudit(taskId, comment, varMap, msgMap);
            loanReqAppService.completeTask(processInstance, varMap, msgMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.json.returnMsg("true", "�ύ�ɹ�!").toJson();
    }


    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @param organCode
     * @return
     */
    private List<TbReqDetail> getListNew(HttpServletRequest request, String organCode) {
        String tbReqDetailStr = request.getParameter("tbReqDetail");
        String reqUnit = request.getParameter("reqUnit");
        String reqId = request.getParameter("reqId");
        String state = request.getParameter("state");
        List<TbReqDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = tbReqDetailStr.split("&");
        Map<String, TbReqDetail> map = new HashMap<>();
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            //eg: 11005293_x001=123 (����code_������=���&����code_������=���)
            String num = planDetailArr1[1]; //һ����ֵ
            String combCode = planDetailArr2[0]; //code
            String numTypeCode = planDetailArr2[1]; //expNum;reqNum;rate;balance
            TbReqDetail tbReqDetail = map.get(combCode);
            if (tbReqDetail == null) {
                tbReqDetail = new TbReqDetail();
                tbReqDetail.setReqdCombCode(combCode);
            }
            tbReqDetail.setReqdUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
            if ("expNum".equals(numTypeCode)) {
                tbReqDetail.setReqdExpnum(new BigDecimal(num).multiply(unit));
            } else if ("reqNum".equals(numTypeCode)) {
                tbReqDetail.setReqdReqnum(new BigDecimal(num).multiply(unit));
            } else if ("rate".equals(numTypeCode)) {
                tbReqDetail.setReqdRate(new BigDecimal(num).multiply(unit));
            } else if ("balance".equals(numTypeCode)) {
                tbReqDetail.setReqdBalance(new BigDecimal(num).multiply(unit));
            }
            tbReqDetail.setReqdOrgan(organCode);
            tbReqDetail.setReqdState(Integer.valueOf(state));
            tbReqDetail.setReqdCreateTime(BocoUtils.getTime());
            tbReqDetail.setReqdUpdateTime(BocoUtils.getTime());
            map.put(combCode, tbReqDetail);
        }
        Collection<TbReqDetail> tbReqDetails = map.values();
        for (TbReqDetail tb : tbReqDetails) {
            tbPlanDetailList.add(tb);
        }

        return tbPlanDetailList;
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
