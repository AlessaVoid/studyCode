package com.boco.PUB.service.tbSingle.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanQuotaApply.impl.LoanQuotaApplyService;
import com.boco.PUB.service.tbSingle.ITbSingleService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.mapper.TbSingleMapper;
import com.boco.SYS.service.IWorkFlowService;
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
 * 单笔专项申请信息表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbSingleService extends GenericService<TbSingle, Integer> implements ITbSingleService {
    public static Logger logger = Logger.getLogger(LoanQuotaApplyService.class);
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    TbSingleMapper tbSingleMapper;
    @Autowired
    IActHiTaskinstService actHiTaskinstService;
    @Autowired
    IWebTaskRoleInfoNewService webTaskRoleInfoNewService;
    @Autowired
    ITbTradeParamService tbTradeParamService;
    @Autowired
    private FdOrganMapper fdOrganMapper;

    @Override
    public PlainResult<String> startLoanReqAuditProcess(int qaId, String organCode, String operCode, String operName, String assignee, String processKey, String comment) throws Exception {
        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", qaId + "");
        //下一审批人
        varMap.put("msgNo", msgNo);
        TbSingle searchTbLineTemp = tbSingleMapper.selectByPK(qaId);
        int unit = searchTbLineTemp.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        varMap.put("qaAmt", searchTbLineTemp.getQaAmt().abs().multiply(unitNum));

        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(searchTbLineTemp.getQaMonth());
        List<TbTradeParam> tbTradeParamList = tbTradeParamService.selectByAttr(tbTradeParam);

        if (tbTradeParamList != null && tbTradeParamList.size() > 0) {
            varMap.put("Single", tbTradeParamList.get(0).getParamSingleMount().abs());
        }
        varMap.put("custType", "1");
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", WebContextUtil.getSessionOrgan().getOrganlevel());
        //用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //提交第一个任务
        Task task = workFlowService.getTaskByPid(pi.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(100);
            task = workFlowService.getTaskByPid(pi.getId());
            i++;
        }
        //绑定任务,分配任务
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);

        //完成任务task
        if ("11005293".equals(organCode)) {
            varMap.put("organName", "总行-");
        } else {
            FdOrgan fdOrgan = fdOrganMapper.selectByPK(organCode);
            varMap.put("organName", fdOrgan.getOrganname() + "-");
        }
        workFlowService.completeTask(task.getId(), comment, varMap);
        //获取最新的任务，并将任务执行对应审批人员
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("单一机构单笔专项流程编号workFlowCode【" + workFlowCode + "】");

        //更新单笔专项申请记录状态
        TbSingle tbLineTemp = new TbSingle();
        tbLineTemp.setQaId(qaId);
        tbLineTemp.setQaOrgan(organCode);
        tbLineTemp.setQaState(LoanStateEnums.STATE_APPROVING.status);
        tbSingleMapper.updateQuotaApplyByQaIdAndOrganCode(tbLineTemp);
        String url = "singleApplyPendingApp/tbQuotaApplyAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        //记录审批信息
        saveMsg(msgNo, operCode, assignee, url, qaId + "");
        return result.success(workFlowCode, "审批流程启动成功");
    }

    /**
     * 已提交的单笔专项申请
     **/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String qaMonth) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("qaMonth", qaMonth);

        List<Map<String, Object>> list = tbSingleMapper.getAuditRecordHist(map);

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
        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<>();
        String organLevel = WebContextUtil.getSessionOrgan().getOrganlevel();
        String processDefinitionKey = "";
        processDefinitionKey = AuditMix.SINGLE_ONE_MECH_KEY;

        //查询登录用户待办任务
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
            return tbSingleMapper.getPendingAppReq(map);
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
        //查询当前任务节点的上一个节点
        //设计该表的原因：由于在产品设计的设计流程图中存在多个子流程，未避免找不到上一个节点
        Map<String, Object> map = new HashMap<String, Object>();
        WebTaskRoleInfoNew webTaskRoleInfoNew = new WebTaskRoleInfoNew();
        //当前任务节点
        webTaskRoleInfoNew.setTaskId(task.getTaskDefinitionKey());
        //流程定义id
        webTaskRoleInfoNew.setProcDefId(task.getProcessDefinitionId());
        //存放产品的机构级别，判断是总行的还是分行的
        webTaskRoleInfoNew.setOrganLevel(String.valueOf(variables.get("organLevel")));
        //产品的投资者类型
        webTaskRoleInfoNew.setCustType("0".equals(custType) ? "1" : custType);
        //查询上一个节点
        List<WebTaskRoleInfoNew> roleInfoList = webTaskRoleInfoNewService.selectByAttr(webTaskRoleInfoNew);
        if (roleInfoList.size() > 0) {
            //这样取是因为每个审批节点都有上一个节点
            webTaskRoleInfoNew = roleInfoList.get(0);
        }
        String upTaskId = webTaskRoleInfoNew.getUpTaskId();
        map.put("upTaskId", upTaskId);
        String returnAssginee = "";
        //判断上一个阶段是不是开始节点，如果不是的话，获取上一个节点的审批人，将当前信息驳回给他
        //在历史任务表中会存在多条数据，因此要将数据进行排序，然后获取最新的记录
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
            //如果上一个节点是开始节点，则要将该记录返回给提交人，从流程变量中获取发起人
            returnAssginee = String.valueOf(variables.get("startUser"));
            map.put("assignee", returnAssginee + ":reSubmit");
            map.put("assigneeByWebMsg", returnAssginee);
        }
        return map;
    }

    /**
     * @param msgNo
     * @param operCode 当前
     * @param assignee 下级
     * @param msgUrl
     * @param qaId
     * @throws Exception
     */
    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String qaId) throws Exception {

        TbSingle tbSingle = tbSingleMapper.selectByPK(Integer.valueOf(qaId));
        BigDecimal qaNum = tbSingle.getQaAmt();
        String combCode = tbSingle.getQaComb();
        HashMap<String, Object> map = new HashMap<>();
        map.put("combcode", combCode);
        List<Map<String, Object>> maps = loanCombMapper.selectCombBycombcode(map);
        String combName = String.valueOf(maps.get(0).get("combname"));
        int unit = tbSingle.getUnit();
        String unitStr = "万元";
        if (unit == 2) {
            unitStr = "亿元";
        }
        String reqName = "贷种组合：" + combName + " 调整金额：" + qaNum.toPlainString() + unitStr;


        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("单一机构单笔专项", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("单一机构单笔专项：" + qaId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        //获取当前操作人姓名及操作状态
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=") + 1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);
        msg.setAppRoleName("");
        msg.setAppOrganCode(WebContextUtil.getSessionOrgan().getThiscode());
        msg.setAppOrganName(tbSingle.getQaSingleOrganName());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("单一机构单笔专项", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("单一机构单笔专项：" + qaId);
        webMsgService.insertEntity(msg);
    }


    @Override
    public ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {

        String qaId = String.valueOf(msgMap.get("qaId"));
        TbSingle tbSingle = tbSingleMapper.selectByPK(Integer.valueOf(qaId));
        int unit = tbSingle.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        varMap.put("qaAmt", tbSingle.getQaAmt().abs().multiply(unitNum));
        //获取是否同意
        String isAgree = (String) varMap.get("isAgree");
        //获取当前任务对应的流程实例
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //获取当前流程实例对应的流程实例id  封装新的流程实例id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }
        TbSingle tbLineTemp = new TbSingle();
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbLineTemp.setQaState(TbSingle.STATE_APPROVED);
        tbLineTemp.setQaId(Integer.parseInt(msgMap.get("qaId").toString()));

        if ("0".equals(isAgree)) {//驳回
            tbLineTemp.setQaState(TbSingle.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("qaId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //如果是网关的话，通过网关获取下一个节点的名称
                    if ("Exclusive Gateway".equals(act.getProperty("name")) || act.getProperty("name").toString().contains("Exclusive Gateway")) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //如果连接的下一个节点的名称为End，则返回true
                            }
                        }
                    }
                }
            }
            if (!"true".equals((String) msgMap.get("lastUserType"))) {//
                tbLineTemp.setQaState(TbSingle.STATE_APPROVALING);
            }
        }
        tbSingleMapper.updateByPK(tbLineTemp);
        //流转任务
        String organCode = varMap.get("organCode").toString();
        if ("11005293".equals(organCode)) {

            varMap.put("organName", "总行-");
        } else {
            FdOrgan fdOrgan = fdOrganMapper.selectByPK(organCode);
            varMap.put("organName", fdOrgan.getOrganname() + "-");
        }

        workFlowService.completeTask(taskId, comment, varMap);
        return processInstance;
    }

    @Override
    public void completeTask(ProcessInstance processInstance, Map<String, Object> varMap, Map msgMap) throws Exception {
        String qaId = String.valueOf(msgMap.get("qaId"));
        String isAgree = (String) varMap.get("isAgree");

        //获取最新的任务，并将任务执行对应审批人员
        //静待1秒，以确定任务已更新
        Thread.sleep(100);
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        int i = 0;
        while (task == null && i <= 5) {
            Thread.sleep(100);
            task = workFlowService.getTaskByPid(processInstance.getId());
            i++;
        }
        if ("0".equals(isAgree)) {
            String opercode = tbSingleMapper.getStartWorkFlowPeople(processInstance.getId());
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
            url = "singleApplyReject/tbQuotaApplyResubmitAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
            url = "singleApplyPendingApp/tbQuotaApplyAuditUI.htm?qaid=" + qaId + "&taskid=" + task.getId();
        }
        //记录审批信息
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("单一机构单笔专项", "MSG_TYPE"));
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("单一机构单笔专项：" + qaId);
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

        List<Map<String, Object>> list = tbSingleMapper.getApprovedRecord(map);

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
     * 联想输入申请编号
     *
     * @param tbQuotaApply
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年月10日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectQaId(TbSingle tbQuotaApply) {
        return tbSingleMapper.selectQaId(tbQuotaApply);
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbQuotaApply
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectQaMonth(TbSingle tbQuotaApply) {
        return tbSingleMapper.selectQaMonth(tbQuotaApply);
    }

}