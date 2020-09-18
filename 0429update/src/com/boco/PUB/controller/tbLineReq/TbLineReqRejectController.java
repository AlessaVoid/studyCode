package com.boco.PUB.controller.tbLineReq;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.lineReq.ILineReqAppService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
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
@RequestMapping(value = "/TbLineReqReject/")
public class TbLineReqRejectController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    ILineReqAppService lineReqAppService;
    @Autowired
    ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    private WebOperLineMapper webOperLineMapper;


    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "���ص��Ŵ������б�", funCode = "PUB-09", funName = "���ص��Ŵ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRollBackAuditPage";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "���ص��Ŵ������б�", funCode = "PUB-09", funName = "���ص��Ŵ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(Pager pager, String lineReqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        String organLevel = getSessionOrgan().getOrganlevel();
        setPageParam();
        if ("��ѡ��".equals(lineReqMonth)) {
            lineReqMonth = "";
        }
        String auditStatus = TbReqDetail.STATE_DISMISSED + "";
        List<Map<String, Object>> list = lineReqAppService.getPendingAppReq(organLevel, sessionOperInfo, auditStatus, pager);
        if (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (lineReqMonth.equals(map.get("linereqmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }


    @RequestMapping("/listReqRejectDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-09", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int lineReqId, String processInstanceId) throws Exception {
        authButtons();
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRejectDetailPage";
    }


    /**
     * ͨ�÷���
     *
     * @param tbLineReqDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqDetail tbLineReqDetail) throws Exception {
        String combCodeStr = tbLineReqDetail.getLineCombCode();
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
        TbReqList  tbReqList =tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
        for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combList.add(combMap);
                    break;
                }
            }
        }
        setAttribute("combList", combList);
    }


    @RequestMapping("/loanReqResubmitAuditUI")
    @SystemLog(tradeName = "�����ύ�Ŵ�����", funCode = "PUB-09", funName = "�����ύ�Ŵ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int lineReqId, String taskid) throws Exception {
        authButtons();
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);

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
        setAttribute("lineReqId", lineReqId);
         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        return basePath + "/PUB/tbLineReqManage/reject/loanReqRejectAuditPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid,String lineReqId) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey = task.getTaskDefinitionKey();

        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.REQ_TOTAL_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("2".equals(organLevel)) {

        }

        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());



        String lineCodeStr = tbLineReqDetailService.selectByPK(Integer.parseInt(lineReqId)).getLineCode();
        List<FdOper> newFdOperList = new ArrayList<>();
        for (FdOper oper : fdOperList) {
            WebOperLineDO searchOper = new WebOperLineDO();
            searchOper.setOperCode(oper.getOpercode());
            searchOper.setStatus(1);
            List<WebOperLineDO> tempList = webOperLineMapper.getAllWebOperLineByOperCode(searchOper);
            if (tempList != null && tempList.size() > 0) {
                for (WebOperLineDO tempOper : tempList) {
                    if (lineCodeStr.equals(tempOper.getLineId())) {
                        newFdOperList.add(oper);
                        break;
                    }
                }
            }
        }
        if(newFdOperList.size()==0){
            return JSON.toJSONString(fdOperList);
        }
        return JSON.toJSONString(newFdOperList);
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanReqRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-09", funName = "�����Ŵ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request, String comment, String lineReqId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        TbLineReqDetail tbLineReqDetail = getListNew(request);
        tbLineReqDetailService.updateByPK(tbLineReqDetail);

        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:" + msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("isAgree", isAgree);
        msgMap.put("organCode", organCode);
        msgMap.put("operCode", webOperInfo.getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("lineReqId", lineReqId);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = lineReqAppService.findIsNotAgreeInfo(task, "1", variables);
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
        //�����������
        ProcessInstance processInstance = lineReqAppService.completeTaskAudit(taskId, comment, varMap, msgMap);
        lineReqAppService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "�����ύ�ɹ�!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //�޸��Ŵ�����
     * @Date ����12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "�޸��Ŵ�����", funCode = "PUB-09", funName = "�޸��Ŵ�����", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbLineReqDetail tbLineReqDetail) throws Exception {
        tbLineReqDetailService.updateByPK(tbLineReqDetail);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @return
     */
    private TbLineReqDetail getListNew(HttpServletRequest request) {
        String tbLineReqDetailStr = request.getParameter("tbLineReqDetail");
        String reqUnit = request.getParameter("lineUnit");
        String lineReqId = request.getParameter("lineReqId");
        String lineRefId = request.getParameter("lineRefId");
        String state = request.getParameter("state");

        List<TbLineReqDetailDTO> tbLineReqDetails = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqDetailStr.split("&");
        Map<String, TbLineReqDetailDTO> map = new HashMap<>();
        for (int i = 0; i < lineReqDetailArr.length; i++) {
            String[] planDetailArr1 = lineReqDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            String num = planDetailArr1[1]; //һ����ֵ
            String combCode = planDetailArr2[0]; //code
            String numTypeCode = planDetailArr2[1]; //expNum;reqNum;rate;balance
            TbLineReqDetailDTO tbReqDetail = map.get(combCode);
            if (tbReqDetail == null) {
                tbReqDetail = new TbLineReqDetailDTO();
                tbReqDetail.setLineCombCode(combCode);
            }
            tbReqDetail.setLineUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setLineRefId(Integer.valueOf(lineRefId));
            if ("expNum".equals(numTypeCode)) {
                tbReqDetail.setLineExpnum(new BigDecimal(num).multiply(unit));
            } else if ("reqNum".equals(numTypeCode)) {
                tbReqDetail.setLineReqnum(new BigDecimal(num).multiply(unit));
            } else if ("rate".equals(numTypeCode)) {
                tbReqDetail.setLineRate(new BigDecimal(num));
            } else if ("balance".equals(numTypeCode)) {
                tbReqDetail.setLineBalance(new BigDecimal(num).multiply(unit));
            }
            tbReqDetail.setLineReqId(Integer.parseInt(lineReqId));
            map.put(combCode, tbReqDetail);
        }

        Collection<TbLineReqDetailDTO> tbReqDetails = map.values();
        for (TbLineReqDetailDTO tb : tbReqDetails) {
            tbLineReqDetails.add(tb);
        }

        String expNumStr = "";
        String reqNumStr = "";
        String rateStr = "";
        String balanceStr = "";
        for (TbLineReqDetailDTO tbDTO : tbLineReqDetails) {
            expNumStr += (tbDTO.getLineExpnum() + ",");
            reqNumStr += (tbDTO.getLineReqnum() + ",");
            rateStr += (tbDTO.getLineRate() + ",");
            balanceStr += (tbDTO.getLineBalance() + ",");
        }
        if (expNumStr.endsWith(",")) {
            expNumStr = expNumStr.substring(0, expNumStr.length() - 1);
        }
        if (reqNumStr.endsWith(",")) {
            reqNumStr = reqNumStr.substring(0, reqNumStr.length() - 1);
        }
        if (rateStr.endsWith(",")) {
            rateStr = rateStr.substring(0, rateStr.length() - 1);
        }
        if (balanceStr.endsWith(",")) {
            balanceStr = balanceStr.substring(0, balanceStr.length() - 1);
        }
        TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
        tbLineReqDetail.setLineExpnum(expNumStr);
        tbLineReqDetail.setLineReqnum(reqNumStr);
        tbLineReqDetail.setLineRate(rateStr);
        tbLineReqDetail.setLineBalance(balanceStr);
        tbLineReqDetail.setLineState(Integer.valueOf(state));
        tbLineReqDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineCreateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineReqId(Integer.parseInt(lineReqId));
        tbLineReqDetail.setLineRefId(Integer.parseInt(lineRefId));

        return tbLineReqDetail;
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
