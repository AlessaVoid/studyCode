package com.boco.PUB.controller.tbOver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbOverRejectController
 * @Description �Ѳ��ص�������ʱ�������
 * @Author tangxn
 * @Date 20191117 ����2:29
 * @Version 2.0
 **/

@Controller
@RequestMapping(value = "/overApplyReject")
public class TbOverRejectController extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private ITbOverService tbOverService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
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

    @RequestMapping("/ListUI")
    @SystemLog(tradeName = "���ص�������ʱ��������б�", funCode = "PUB-13-06", funName = "���ص�������ʱ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanAllRejectPlanListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbOverManage/rejected/rollBackAuditList";
    }

    @ResponseBody
    @RequestMapping("/listAllRollBackTaskList")
    @SystemLog(tradeName = "���ص�������ʱ��������б�", funCode = "PUB-13-06", funName = "���ص�������ʱ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllRollBackTaskList(String qaMonth, Pager pager) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        String auditStatus = TbOver.STATE_DISMISSED + "";
        List<Map<String, Object>> list = tbOverService.getPendingAppReq("0", sessionOperInfo.getOperCode(), qaMonth, auditStatus, pager,"");
        for (Map<String, Object> tbLineOver : list) {
            String qaAmtStr = String.valueOf(tbLineOver.get("qaamt"));
            BigDecimal total = BigDecimal.ZERO;
            String[] qaAmtArr = qaAmtStr.split(",");
            for (int i = 0; i < qaAmtArr.length; i++) {
                total=total.add(new BigDecimal(qaAmtArr[i]));
            }
            tbLineOver.put("qacreateoper",total.toString());
            String appNumStr = String.valueOf(tbLineOver.get("qaoneinfo"));
            BigDecimal totalAppNum = BigDecimal.ZERO;
            String[] appAmtArr = appNumStr.split(",");
            for (int i = 0; i < appAmtArr.length; i++) {
                totalAppNum=totalAppNum.add(new BigDecimal(appAmtArr[i]));
            }
            tbLineOver.put("appTotalNum",totalAppNum.toString());
        }
        return pageData(list);
    }


    @RequestMapping("/rejectDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-13-06-01", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId, String processInstanceId) throws Exception {
        authButtons();

        TbOver tbQuotaApply = tbOverService.selectByPK(qaId);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        String oneInfo = tbQuotaApply.getQaTwoInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        setAttr(tbQuotaApply);
        setAttribute("qaId", qaId);
        setAttribute("comments", BocoUtils.translateComments(comments,"over"));
        setAttribute("TbOver", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbOverManage/rejected/rejectDetailPage";
    }


    /**
     * ͨ�÷���
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbOver tbOver) throws Exception {
        TbPlan searchTb = new TbPlan();
        searchTb.setPlanOrgan(getSessionOrgan().getUporgan());
        searchTb.setPlanMonth(tbOver.getQaMonth());
        searchTb.setPlanType(TbPlan.PLAN);
        List<TbPlan> tbPlanList = tbPlanService.selectByAttr(searchTb);
        String organLevel = String.valueOf(tbPlanList.get(0).getCombLevel());
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(tbOver.getQaCreateOper());
        /*�õ���ǰ��¼�û���Ͻ�����б�*/
        List<Map<String, String>> combList = new ArrayList<>();
        List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        if (webOperLineDOList != null && webOperLineDOList.size() > 0) {
            for (WebOperLineDO operLineDO : webOperLineDOList) {
                ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                if (Objects.nonNull(lineInfoDO)) {
                    String lineId = lineInfoDO.getLineId();
                    List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
                    for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                        LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                        if (Objects.nonNull(loanComposeDO)) {
                            Map<String, String> combMap = new HashMap<>(2);
                            combMap.put("combCode", loanComposeDO.getCombCode());
                            combMap.put("combName", loanComposeDO.getCombName());
                            combList.add(combMap);
                        }
                    }
                }
            }
        } else if ("1".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(1);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        } else if ("2".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(2);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        }
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }

    @RequestMapping("/tbQuotaApplyResubmitAuditUI")
    @SystemLog(tradeName = "�����ύ������ʱ�������", funCode = "PUB-13-06-01", funName = "�����ύ������ʱ�������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int qaid, String taskid) throws Exception {
        authButtons();
        TbOver tbQuotaApply = tbOverService.selectByPK(qaid);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());

        //������ע
        List<Comment> comments = workFlowService.getTaskComments(taskid);

        //�ж���һ��Ƿ��ǽ����ڵ㣬����ǵĻ�������������ʶ���˱�ʶ�����ж��Ƿ���ʾ������Ա
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //�ж��ǲ������һ��������
        boolean lastUserType = this.getLastUserType(activityImpl);
        String oneInfo = tbQuotaApply.getQaTwoInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        setAttr(tbQuotaApply);
        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("qaid", qaid);
        setAttribute("comments", BocoUtils.translateComments(comments,"over"));
        setAttribute("TbOver", tbQuotaApply);
        setAttribute("level", getSessionOrgan().getOrganlevel());
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbOverManage/rejected/rejectCommitPage";
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String qaId) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        String taskKey = task.getTaskDefinitionKey();
        TbOver tbQuotaApply = tbOverService.selectByPK(Integer.valueOf(qaId));
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        /*���޶��׼��*/
        BigDecimal overNum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            overNum = list.get(0).getParamOverMount();
        }
        /*��������д���*/
        String qaAmtStr = tbQuotaApply.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i])).abs();
        }
        int unit =tbQuotaApply.getUnit();
        BigDecimal unitNum =BigDecimal.ONE;
        if(unit==2){
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.abs().multiply(unitNum);
        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel1)) {

        } else if ("1".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
            }
        } else if ("2".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_TWO_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_TWO_LOW_BASE_AUDITOR_PREFIX;
            }
        }
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), auditorPrefix, map);
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanQuotaApplyRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "��һ������ʱ���", funCode = "PUB-13-06", funName = "����������ʱ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanReqRequest(HttpServletRequest request,String comment, String qaId, String taskId, String assignee, String isAgree, String lastUserType) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);
        update(request);
        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************������ϢmsgNo:" + msgNo);
        //�������ϣ����ڴ�Ŷ�Ӧ��webMsg���������
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//�뵱ǰ����󶨴����б�ı��
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("qaId", qaId);
        msgMap.put("taskId", taskId);//��ǰ����ִ�е�����id
        msgMap.put("lastUserType", lastUserType);
        //�ж��Ƿ�ͬ�⣬�����ͬ�⣬���ظ���Ʒ�����ˣ����ͬ�⣬�ύ����һ������
        if ("0".equals(isAgree)) {
            Map<String, Object> isNotAgreeMap = tbOverService.findIsNotAgreeInfo(task, "1", variables);
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
        varMap.put("organCode",getSessionOrgan().getThiscode());
        //�����������
        ProcessInstance processInstance = tbOverService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbOverService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "�����ύ�ɹ�!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //�޸�������ʱ�������
     * @Date ����12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "�޸�������ʱ�������", funCode = "PUB-13-06-01", funName = "�޸�������ʱ�������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //������Ա
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        String qaId = request.getParameter("qaId");
        String tbOverDetail = request.getParameter("tbOverDetail");
        String qaReason = request.getParameter("qaReason");
        String[] tbOverDetailArr = tbOverDetail.split("&");
        String numStr = "";
        String combCodeStr = "";
        for (int i = 0; i < tbOverDetailArr.length; i++) {
            String[] planDetailArr1 = tbOverDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            String num = planDetailArr1[1];
            String combCode = planDetailArr2[0];
            numStr += (num + ",");
            combCodeStr += (combCode + ",");
        }
        if (numStr.endsWith(",")) {
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        if (combCodeStr.endsWith(",")) {
            combCodeStr = combCodeStr.substring(0, combCodeStr.length() - 1);
        }
        TbOver tbQuotaApply = new TbOver();
        tbQuotaApply.setQaId(Integer.parseInt(qaId));
        tbQuotaApply.setQaComb(combCodeStr);
        tbQuotaApply.setQaAmt(numStr);
        tbQuotaApply.setQaOneInfo(numStr);
        tbQuotaApply.setQaReason(qaReason);
        if (!"".equals(fileName)) {
            tbQuotaApply.setQaFileId(fileName);
        }

        tbOverService.updateByPK(tbQuotaApply);
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
