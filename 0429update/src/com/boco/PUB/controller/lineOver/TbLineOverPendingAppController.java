package com.boco.PUB.controller.lineOver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbLineOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.*;
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

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbLineOverPendingAppController
 * @Description ��������������ʱ�������
 * @Author tangxn
 * @Date 20191118 ����6:37
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/lineOverApplyPendingApp")
public class TbLineOverPendingAppController extends BaseController {
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private ITbLineOverService tbOverService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private TbLineOverMapper tbQuotaApplyMapper;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private WebOperLineMapper webOperLineMapper;
    @Autowired
    private LineProductMapper lineProductMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "��������������ʱ�������", funCode = "PUB-14-04", funName = "��������������ʱ��������б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/lineOverManage/pendingApp/tbQuotaApplyPendingAppList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbLineOver")
    @SystemLog(tradeName = "��������������ʱ�������", funCode = "PUB-14-04", funName = "��������������ʱ��������б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppTbLineOver(String reqMonth, Pager pager) throws Exception {
        setPageParam();
        List<Map<String, Object>> list = tbOverService.getPendingAppReq("1", getSessionOperInfo().getOperCode(), reqMonth, TbLineOver.STATE_APPROVALING + "", pager);
        for (Map<String, Object> tbLineOver : list) {
            String qaAmtStr = String.valueOf(tbLineOver.get("qaamt"));
            BigDecimal total = BigDecimal.ZERO;
            String[] qaAmtArr = qaAmtStr.split(",");
            for (int i = 0; i < qaAmtArr.length; i++) {
                total = total.add(new BigDecimal(qaAmtArr[i]));
            }
            tbLineOver.put("qacreateoper", total.toString());
        }
        return pageData(list);
    }

    @RequestMapping("/tbQuotaApplyAuditUI")
    @SystemLog(tradeName = "��һ������ʱ���", funCode = "PUB-14-04", funName = "������ʱ�������������������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int qaid, String taskid) throws Exception {
        authButtons();
        TbLineOver tbQuotaApply = tbOverService.selectByPK(qaid);
        setAttr(tbQuotaApply);

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

        setAttribute("lastUserType", lastUserType);
        setAttribute("taskid", taskid);
        setAttribute("qaid", qaid);
        setAttribute("comments", BocoUtils.translateComments(comments, "over"));
        setAttribute("TbLineOver", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/lineOverManage/pendingApp/tbQuotaApplyPendingDetailPage";
    }

    /**
     * ͨ�÷���
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbLineOver tbOver) throws Exception {
        FdOrgan f = organService.selectByPK(tbOver.getQaOrgan());
        String organLevel = f.getOrganlevel();
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

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ������ʱ�������������Ա", funCode = "PUB-14", funName = "��ȡ������ʱ�������������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String qaId) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        TbLineOver tbQuotaApply = tbOverService.selectByPK(Integer.valueOf(qaId));
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("custType", "1");
        //��������id��ȡ���̷�����
        String opercode = tbQuotaApplyMapper.getStartWorkFlowPeople(processInstance.getId());
        FdOper startFdOper = new FdOper();
        startFdOper.setOpercode(opercode);
        List<FdOper> operList = fdOperService.selectByAttr(startFdOper);
        FdOrgan fdOrgan = fdOrganService.selectByPK(operList.get(0).getOrgancode());
        //�õ����� ����id��ȡ���̷����� ��������
        String organLevel = fdOrgan.getOrganlevel();


        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);

        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel1)) {
            processKey = AuditMix.TEMP_TOTAL_LINE_KEY;
        }

        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), workFlowService.getNextTaskAssigneeKey(taskid, AuditMix.REQ_QUOTA_EL_KEY), map);

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();


        String lineCodeStr = tbOverService.selectByPK(Integer.parseInt(qaId)).getLineCode();
        List<FdOper> newFdOperList = new ArrayList<>();
        for (FdOper oper : fdOperList) {
            WebOperLineDO searchOper = new WebOperLineDO();
            searchOper.setOperCode(oper.getOpercode());
            searchOper.setStatus(1);
            List<WebOperLineDO> tempList = webOperLineMapper.getAllWebOperLineByOperCode(searchOper);
            if (tempList != null && tempList.size() > 0) {
                for (WebOperLineDO tempOper : tempList) {
                    if (lineCodeStr.contains(tempOper.getLineId())) {
                        newFdOperList.add(oper);
                        break;
                    }
                }
            }
        }
        if (newFdOperList.size() == 0) {
            for (FdOper tb : fdOperList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", tb.getOpercode());
                jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
                jsonArray.add(jsonObject);
            }
        } else {
            for (FdOper tb : newFdOperList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", tb.getOpercode());
                jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
                jsonArray.add(jsonObject);
            }
        }


        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/auditLoanQuotaApplyRequest", method = RequestMethod.POST)
    @SystemLog(tradeName = "��һ������ʱ���", funCode = "PUB-14-04", funName = "����������ʱ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanQuotaApplyRequest(String comment, String qaId, String taskId, String assignee, String isAgree, String lastUserType, String tbOverDetail) throws Exception {
        if (getSessionOrgan().getOrganlevel().equals("0")) {
            String[] tbOverDetailArr = tbOverDetail.split("&");
            String numStr = "";
            for (int i = 0; i < tbOverDetailArr.length; i++) {
                String[] planDetailArr1 = tbOverDetailArr[i].split("=");
                String num = planDetailArr1[1];
                numStr += (num + ",");
            }
            if (numStr.endsWith(",")) {
                numStr = numStr.substring(0, numStr.length() - 1);
            }
            TbLineOver TemptbQuotaApply = new TbLineOver();
            TemptbQuotaApply.setQaId(Integer.parseInt(qaId));
            TemptbQuotaApply.setQaOneInfo(numStr);
            tbOverService.updateByPK(TemptbQuotaApply);
        }


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
        varMap.put("organCode", getSessionOrgan().getThiscode());
        //�����������
        try {
            ProcessInstance processInstance = tbOverService.completeTaskAudit(taskId, comment, varMap, msgMap);
            tbOverService.completeTask(processInstance, varMap, msgMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.json.returnMsg("true", "�ύ���߳��޶�����ɹ�!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //�޸�������ʱ�������
     * @Date ����12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "�޸�������ʱ�������", funCode = "PUB-14-04", funName = "�޸�������ʱ�������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbLineOver tbQuotaApply) throws Exception {
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
