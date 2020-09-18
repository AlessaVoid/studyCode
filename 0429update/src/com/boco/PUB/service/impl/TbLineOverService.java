package com.boco.PUB.service.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanQuotaApply.impl.LoanQuotaApplyService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���޶�������Ϣ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbLineOverService extends GenericService<TbLineOver, Integer> implements ITbLineOverService {
    public static Logger logger = Logger.getLogger(LoanQuotaApplyService.class);

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    TbLineOverMapper tbLineTempMapper;
    @Autowired
    IActHiTaskinstService actHiTaskinstService;
    @Autowired
    IWebTaskRoleInfoNewService webTaskRoleInfoNewService;
    @Autowired
    ITbTradeParamService tbTradeParamService;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbQuotaAllocateMapper tbQuotaAllocateMapper;

    @Override
    public ProcessInstance startLoanReqAuditProcess(int qaId, String organCode, String operCode, String operName, String assignee, String processKey) throws Exception {


        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", qaId + "");
        //��һ������

        TbLineOver searchTbLineOver = tbLineTempMapper.selectByPK(qaId);
        String qaAmtStr = searchTbLineOver.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        int unit = searchTbLineOver.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i]));
        }
        qaAmt = qaAmt.abs().multiply(unitNum);
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(searchTbLineOver.getQaMonth());
        List<TbTradeParam> tbTradeParamList = tbTradeParamService.selectByAttr(tbTradeParam);

        if (tbTradeParamList != null && tbTradeParamList.size() > 0) {
            varMap.put("Benchmark", tbTradeParamList.get(0).getParamOverMount().abs());
        }
        varMap.put("custType", "1");
        varMap.put("qaAmt", qaAmt);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);

        return pi;

    }

    @Override
    public PlainResult<String> compleLoanReqAuditProcess(ProcessInstance pi, String operCode, String assignee, Integer qaId, String organCode, String comment) throws Exception {
        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        String workFlowCode = pi.getProcessInstanceId();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", qaId + "");
        //��һ������

        TbLineOver searchTbLineOver = tbLineTempMapper.selectByPK(qaId);
        String qaAmtStr = searchTbLineOver.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i]));
        }
        int unit = searchTbLineOver.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.multiply(unitNum).abs();
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(searchTbLineOver.getQaMonth());
        List<TbTradeParam> tbTradeParamList = tbTradeParamService.selectByAttr(tbTradeParam);

        if (tbTradeParamList != null && tbTradeParamList.size() > 0) {
            varMap.put("Benchmark", tbTradeParamList.get(0).getParamOverMount().abs());
        }
        varMap.put("custType", "1");
        varMap.put("qaAmt", qaAmt);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());

        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(100);
            task = workFlowService.getTaskByPid(pi.getId());
            i++;
        }
        //������,��������
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);

        //�������task
        if ("11005293".equals(organCode)) {
            varMap.put("organName", "����-");
        } else {
            FdOrgan fdOrgan = fdOrganMapper.selectByPK(organCode);
            varMap.put("organName", fdOrgan.getOrganname() + "-");
        }
        workFlowService.completeTask(task.getId(), comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("���߳��޶������������̱��workFlowCode��" + workFlowCode + "��");

        //�������߳��޶������¼״̬
        TbLineOver tbLineTemp = new TbLineOver();
        tbLineTemp.setQaId(qaId);
        tbLineTemp.setQaOrgan(organCode);
        tbLineTemp.setQaState(LoanStateEnums.STATE_APPROVING.status);
        tbLineTempMapper.updateQuotaApplyByQaIdAndOrganCode(tbLineTemp);
        String url = "lineOverApplyPendingApp/tbQuotaApplyAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        //��¼������Ϣ
        saveMsg(msgNo, operCode, assignee, url, qaId + "");
        return result.success(workFlowCode, "�������������ɹ�");
    }

    /**
     * ���ύ�ĳ��޶�����
     **/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String qaMonth) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("qaMonth", qaMonth);

        List<Map<String, Object>> list = tbLineTempMapper.getAuditRecordHist(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskId", "");
            } else {
                map1.put("taskId", task.getId());
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getPendingAppReq(String type, String opercode, String qaMonth, String auditStatus, Pager pager) throws Exception {
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<>();
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        String processDefinitionKey = "";

        if ("0".equals(organLevel)) {
            processDefinitionKey = AuditMix.OVER_TOTAL_LINE_KEY;
        } else if ("1".equals(organLevel)) {
            processDefinitionKey = AuditMix.OVER_ONE_LINE_KEY;
        }

        //��ѯ��¼�û���������
        List<Map<String, Object>> rows = workFlowService.getPersonalTaskPage(processDefinitionKey, pager);
        if (rows != null && rows.size() > 0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", opercode);
            map.put("qaMonth", qaMonth);
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            return tbLineTempMapper.getPendingAppReq(map);
        } else {
            return null;
        }

    }

    @Override
    public PlainResult<String> auditLoanReqRequest(HttpServletRequest request, String operCode, String thiscode, String operName) {


        return null;
    }

    @Override
    public Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables) throws Exception {
        //��ѯ��ǰ����ڵ����һ���ڵ�
        //��Ƹñ��ԭ�������ڲ�Ʒ��Ƶ��������ͼ�д��ڶ�������̣�δ�����Ҳ�����һ���ڵ�
        Map<String, Object> map = new HashMap<String, Object>();
        WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
        //��ǰ����ڵ�
        webTaskRoleInfoNew.setTaskId(task.getTaskDefinitionKey());
        //���̶���id
        webTaskRoleInfoNew.setProcDefId(task.getProcessDefinitionId());
        //��Ų�Ʒ�Ļ��������ж������еĻ��Ƿ��е�
        webTaskRoleInfoNew.setOrganLevel(String.valueOf(variables.get("organLevel")));
        //��Ʒ��Ͷ��������
        webTaskRoleInfoNew.setCustType("0".equals(custType) ? "1" : custType);
        //��ѯ��һ���ڵ�
        List<WebTaskRoleInfoNew> roleInfoList = webTaskRoleInfoNewService.selectByAttr(webTaskRoleInfoNew);
        if (roleInfoList.size() > 0) {
            //����ȡ����Ϊÿ�������ڵ㶼����һ���ڵ�
            webTaskRoleInfoNew = roleInfoList.get(0);
        }
        String upTaskId = webTaskRoleInfoNew.getUpTaskId();
        map.put("upTaskId", upTaskId);
        String returnAssginee = "";
        //�ж���һ���׶��ǲ��ǿ�ʼ�ڵ㣬������ǵĻ�����ȡ��һ���ڵ�������ˣ�����ǰ��Ϣ���ظ���
        //����ʷ������л���ڶ������ݣ����Ҫ�����ݽ�������Ȼ���ȡ���µļ�¼
        if (!"startevent1".equals(upTaskId)) {
            ActHiTaskinst actHiTaskinst = new ActHiTaskinst();
            actHiTaskinst.setProcInstId(task.getProcessInstanceId());
            actHiTaskinst.setTaskDefKey(upTaskId);
            actHiTaskinst.setProcDefId(task.getProcessDefinitionId());
            actHiTaskinst.setSortColumn("START_TIME_ desc");
            List<ActHiTaskinst> hitaskInstList = actHiTaskinstService.selectByAttr(actHiTaskinst);
            if (hitaskInstList.size() > 0) {
                actHiTaskinst = hitaskInstList.get(0);
            }
            returnAssginee = String.valueOf(actHiTaskinst.getAssignee());
            map.put("assignee", returnAssginee);
            map.put("assigneeByWebMsg", returnAssginee.replace(":audit", ""));
        } else {
            //�����һ���ڵ��ǿ�ʼ�ڵ㣬��Ҫ���ü�¼���ظ��ύ�ˣ������̱����л�ȡ������
            returnAssginee = String.valueOf(variables.get("startUser"));
            map.put("assignee", returnAssginee + ":reSubmit");
            map.put("assigneeByWebMsg", returnAssginee);
        }
        return map;
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String qaId) throws Exception {

        TbLineOver tbLineOver = tbLineTempMapper.selectByPK(Integer.valueOf(qaId));

        String qaAmtStr = tbLineOver.getQaAmt();
        BigDecimal total = BigDecimal.ZERO;
        String[] qaAmtArr = qaAmtStr.split(",");
        for (int i = 0; i < qaAmtArr.length; i++) {
            total = total.add(new BigDecimal(qaAmtArr[i]));
        }
        int unit = tbLineOver.getUnit();
        String unitStr = "��Ԫ";
        if (unit == 2) {
            unitStr = "��Ԫ";
        }
        String reqName = "������" + total.toPlainString() + unitStr;

        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("��һ���ߵ������", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("��һ���ߵ�����ȣ�" + qaId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=") + 1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);
        msg.setAppRoleName("");
        msg.setAppOrganCode(WebContextUtil.getSessionOrgan().getThiscode());
        msg.setAppOrganName(WebContextUtil.getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("��һ���ߵ������", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("��һ���ߵ�����ȣ�" + qaId);
        webMsgService.insertEntity(msg);
    }


    @Override
    public ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
        String organCode = varMap.get("organCode").toString();
        String qaId = String.valueOf(msgMap.get("qaId"));
        TbLineOver searchTbLineOver = tbLineTempMapper.selectByPK(Integer.valueOf(qaId));
        String qaAmtStr = searchTbLineOver.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i]));
        }
        int unit = searchTbLineOver.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.multiply(unitNum).abs();
        varMap.put("qaAmt", qaAmt.abs());
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(searchTbLineOver.getQaMonth());
        List<TbTradeParam> tbTradeParamList = tbTradeParamService.selectByAttr(tbTradeParam);

        if (tbTradeParamList != null && tbTradeParamList.size() > 0) {
            varMap.put("Benchmark", tbTradeParamList.get(0).getParamOverMount().abs());
        }
        //��ȡ�Ƿ�ͬ��
        String isAgree = (String) varMap.get("isAgree");
        //��ȡ��ǰ�����Ӧ������ʵ��
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //��ȡ��ǰ����ʵ����Ӧ������ʵ��id  ��װ�µ�����ʵ��id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }
        TbLineOver tbLineTemp = new TbLineOver();
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbLineTemp.setQaState(TbLineOver.STATE_APPROVED);
        tbLineTemp.setQaId(Integer.parseInt(msgMap.get("qaId").toString()));

        if ("0".equals(isAgree)) {//����
            tbLineTemp.setQaState(TbLineOver.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("qaId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("Exclusive Gateway")) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //������ӵ���һ���ڵ������ΪEnd���򷵻�true
                            }
                        }
                    }
                }
            }
            if (!"true".equals((String) msgMap.get("lastUserType"))) {//
                tbLineTemp.setQaState(TbLineOver.STATE_APPROVALING);
            }
            FdOrgan fdOrgan = fdOrganMapper.selectByPK(organCode);
            if (fdOrgan.getOrganlevel().equals("0") && tbLineTemp.getQaState() == TbOver.STATE_APPROVED) {
                TbLineOver tempOverResult = tbLineTempMapper.selectByPK(tbLineTemp.getQaId());
                String appNumStr = tempOverResult.getQaOneInfo();
                String[] appNumArr = appNumStr.split(",");
                String lineCombtr = tempOverResult.getQaComb();
                String[] lineCombArr = lineCombtr.split(",");
                List<TbOverDO> tbOverDOS = new ArrayList<>();
                for (int i = 0; i < lineCombArr.length; i++) {
                    TbOverDO tb = new TbOverDO();
                    tb.setQaComb(lineCombArr[i]);
                    tb.setQaOverAmt(new BigDecimal(appNumArr[i]));
                    tbOverDOS.add(tb);
                }
                setAppNum(tempOverResult, tempOverResult.getQaMonth(), organCode, tbOverDOS);
            }
        }
        tbLineTempMapper.updateByPK(tbLineTemp);
        //��ת����

        if ("11005293".equals(organCode)) {

            varMap.put("organName", "����-");
        } else {
            FdOrgan fdOrgan = fdOrganMapper.selectByPK(organCode);
            varMap.put("organName", fdOrgan.getOrganname() + "-");
        }

        workFlowService.completeTask(taskId, comment, varMap);

        return processInstance;
    }

    public void setAppNum(TbLineOver tempOverResult, String month, String organCode, List<TbOverDO> tbOverDOS) {
        Integer unit = tempOverResult.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        TbPlan searchTbPlan = new TbPlan();
        searchTbPlan.setPlanMonth(month);
        searchTbPlan.setPlanOrgan(organCode);
        searchTbPlan.setPlanType(2);
        try {
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(searchTbPlan);
            if (tbPlans != null && tbPlans.size() > 0) {
                String planId = tbPlans.get(0).getPlanId();
                for (TbOverDO tbOverDO : tbOverDOS) {

                    //�޸ļƻ�
                    TbPlanDetail searchTbPlanDetail = new TbPlanDetail();
                    searchTbPlanDetail.setPdLoanType(tbOverDO.getQaComb());
                    searchTbPlanDetail.setPdRefId(planId);
                    searchTbPlanDetail.setPdOrgan(tempOverResult.getQaOrgan());
                    List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(searchTbPlanDetail);
                    if (tbPlanDetails != null && tbPlanDetails.size() > 0) {
                        TbPlanDetail tbPlanDetail = tbPlanDetails.get(0);
                        tbPlanDetail.setPdAmount(BigDecimalUtil.add(tbPlanDetail.getPdAmount(), BigDecimalUtil.getSafeCount(BigDecimalUtil.multiply(tbOverDO.getQaOverAmt(), unitNum))));
                        tbPlanDetailMapper.updatePlanDetail(tbPlanDetail);
                    }
                    //�޸Ķ��
                    TbQuotaAllocate searchTb = new TbQuotaAllocate();
                    searchTb.setPaMonth(month);
                    searchTb.setPaProdCode(tbOverDO.getQaComb());
                    searchTb.setQuotaType(2);//����
                    searchTb.setPaOrgan(tempOverResult.getQaOrgan());
                    List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
                    if (listResult != null && listResult.size() == 1) {
                        TbQuotaAllocate beforeTb = listResult.get(0);
                        BigDecimal beforeAmt = beforeTb.getPaQuotaAvail();
                        beforeTb.setPaQuotaAvail(BigDecimalUtil.add(beforeTb.getPaQuotaAvail(), tbOverDO.getQaOverAmt().multiply(unitNum).multiply(new BigDecimal(10000))));
                        tbQuotaAllocateMapper.updateByPK(beforeTb);
                        logger.info("beforeTb.getPaId():" + beforeTb.getPaId()
                                + "�޸Ķ��:֮ǰ��ȡ�" + beforeAmt + "���޸ĺ��ȡ�" + beforeTb.getPaQuotaAvail() + "]");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void completeTask(ProcessInstance processInstance, Map<String, Object> varMap, Map msgMap) throws Exception {
        String qaId = String.valueOf(msgMap.get("qaId"));
        String isAgree = (String) varMap.get("isAgree");
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        //����1�룬��ȷ�������Ѹ���
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(100);
            task = workFlowService.getTaskByPid(processInstance.getId());
            i++;
        }
        if ("0".equals(isAgree)) {
            String opercode = tbLineTempMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
//            workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String url = "";
        if ("0".equals(isAgree)) {
            assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            url = "lineOverApplyReject/tbQuotaApplyResubmitAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
            url = "lineOverApplyPendingApp/tbQuotaApplyAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        }
        //��¼������Ϣ
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("��һ���ߵ������", "MSG_TYPE"));
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("��һ���ߵ�����ȣ�" + qaId);
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
            }
        } else {
            saveMsg(msgNo, operCode, assignee, url, qaId + "");
        }
    }


    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;

    @Override
    public List<Map<String, Object>> getApprovedRecord(String operCode, String auditStatus, String qaMonth) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("qaMonth", qaMonth);

        List<Map<String, Object>> list = tbLineTempMapper.getApprovedRecord(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());
            }
        }
        return list;
    }


    /**
     * ��������������
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectQaId(TbLineOver tbQuotaApply) {
        return tbLineTempMapper.selectQaId(tbQuotaApply);
    }

    /**
     * ����������������.
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectQaMonth(TbLineOver tbQuotaApply) {
        return tbLineTempMapper.selectQaMonth(tbQuotaApply);
    }

}