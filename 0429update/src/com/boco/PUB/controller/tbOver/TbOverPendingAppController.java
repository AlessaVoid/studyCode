package com.boco.PUB.controller.tbOver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
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
 * @ClassName TbOverPendingAppController
 * @Description 待审批的条线临时额度申请
 * @Author tangxn
 * @Date 20191118 下午6:37
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/overApplyPendingApp")
public class TbOverPendingAppController extends BaseController {
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
    TbOverMapper tbQuotaApplyMapper;
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

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "待审批的条线临时额度申请", funCode = "PUB-13-04", funName = "待审批的条线临时额度申请列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbOverManage/pendingApp/tbQuotaApplyPendingAppList";
    }

    @ResponseBody
    @RequestMapping("/getPendingAppTbOver")
    @SystemLog(tradeName = "待审批的条线临时额度申请", funCode = "PUB-13-04", funName = "待审批的条线临时额度申请列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getPendingAppTbOver(String reqMonth, Pager pager, String organname) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        String auditStatus = TbOver.STATE_APPROVALING + "";
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = tbOverService.getPendingAppReq("1", sessionOperInfo.getOperCode(), reqMonth, auditStatus, pager, organname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map<String, Object> tbLineOver : list) {
            String qaAmtStr = String.valueOf(tbLineOver.get("qaamt"));
            BigDecimal total = BigDecimal.ZERO;
            String[] qaAmtArr = qaAmtStr.split(",");
            for (int i = 0; i < qaAmtArr.length; i++) {
                total = total.add(new BigDecimal(qaAmtArr[i]));
            }
            tbLineOver.put("qacreateoper", total.toString());
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

    @RequestMapping("/tbQuotaApplyAuditUI")
    @SystemLog(tradeName = "单一条线临时额度", funCode = "PUB-13-04", funName = "条线临时额度申请任务审批详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqDetailAuditUI(int qaid, String taskid) throws Exception {
        authButtons();
        TbOver tbQuotaApply = tbOverService.selectByPK(qaid);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        //任务批注
        List<Comment> comments = workFlowService.getTaskComments(taskid);

        //判断下一活动是否是结束节点，如果是的话，传递审批标识，此标识用于判断是否显示审批人员
        ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskid);
        //判断是不是最后一个审批人
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
        setAttribute("comments", BocoUtils.translateComments(comments,"over"));
        setAttribute("TbOver", tbQuotaApply);
        setAttr(tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbOverManage/pendingApp/tbQuotaApplyPendingDetailPage";
    }

    /**
     * 通用方法
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
        String organCode = tbOver.getQaOrgan();
        FdOrgan fdOrgan = fdOrganService.selectByPK(organCode);
        String organLevel = String.valueOf(tbPlanList.get(0).getCombLevel());
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(tbOver.getQaCreateOper());
        /*拿到当前登录用户所辖条线列表*/
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
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取条线临时额度申请审批人员", funCode = "PUB-13", funName = "获取条线临时额度申请审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String taskid, String qaId) throws Exception {
        authButtons();
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskid);
        Task task = workFlowService.getTaskById(taskid);
        TbOver tbQuotaApply = tbOverService.selectByPK(Integer.valueOf(qaId));
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("custType", "1");
        //根据流程id获取流程发起人
        String opercode = tbQuotaApplyMapper.getStartWorkFlowPeople(processInstance.getId());
        FdOper startFdOper = new FdOper();
        startFdOper.setOpercode(opercode);
        List<FdOper> operList = fdOperService.selectByAttr(startFdOper);
        FdOrgan fdOrgan = fdOrganService.selectByPK(operList.get(0).getOrgancode());
        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        /*流程key*/
        String processKey = "";
        processKey = AuditMix.OVER_MECH_KEY;
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, processInstance.getProcessDefinitionId(), workFlowService.getNextTaskAssigneeKey(taskid, AuditMix.REQ_QUOTA_EL_KEY), map);

        String organcode = getSessionOrgan().getThiscode();
        String curOrganLevel = getSessionOrgan().getOrganlevel();
        WebTaskRoleInfo webTaskRoleInfo = new WebTaskRoleInfo();
        webTaskRoleInfo.setProcDefId(processInstance.getProcessDefinitionId());
        List<WebTaskRoleInfo> webTaskRoleInfoList = webTaskRoleInfoService.selectByAttr(webTaskRoleInfo);
        String webRoleOrganLevel = "";
        for (WebTaskRoleInfo webRole : webTaskRoleInfoList) {
            if (webRole.getRoleCode().equals(rolecode)) {
                webRoleOrganLevel = webRole.getOrganLevel();
            }
        }

        if (!"".equals(webRoleOrganLevel) && (Integer.parseInt(webRoleOrganLevel) < Integer.parseInt(curOrganLevel))) {
            organcode = getSessionOrgan().getUporgan();
        }

        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(organcode, rolecode, getSessionOperInfo().getOperCode());
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
    @SystemLog(tradeName = "单一条线临时额度", funCode = "PUB-13-04", funName = "审批条线临时额度申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String auditLoanQuotaApplyRequest(String comment, String qaId, String taskId, String assignee, String isAgree, String lastUserType,String tbOverDetail) throws Exception {
        if(getSessionOrgan().getOrganlevel().equals("0")){
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
            TbOver TemptbQuotaApply = new TbOver();
            TemptbQuotaApply.setQaId(Integer.parseInt(qaId));
            TemptbQuotaApply.setQaOneInfo(numStr);
            tbOverService.updateByPK(TemptbQuotaApply);
        }

        Map<String, Object> varMap = new HashMap<String, Object>();
        Task task = workFlowService.getTaskById(taskId);
        Map<String, Object> variables = workFlowService.getTaskVariables(taskId);

        String msgNo = (String) variables.get("msgNo");
        logger.info("*********************************审批消息msgNo:" + msgNo);
        //创建集合，用于存放对应的webMsg对象的数据
        Map msgMap = new HashMap();
        msgMap.put("custType", variables.get("custType"));
        msgMap.put("msgNo", msgNo);//与当前任务绑定待办列表的编号
        msgMap.put("isAgree", isAgree);
        msgMap.put("operCode", getSessionOperInfo().getOperCode());
        msgMap.put("comment", comment);
        msgMap.put("qaId", qaId);
        msgMap.put("taskId", taskId);//当前正在执行的任务id
        msgMap.put("lastUserType", lastUserType);
        //判断是否同意，如果不同意，返回给产品发起人，如果同意，提交给下一审批人
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
        varMap.put("level", getSessionOrgan().getOrganlevel());
        varMap.put("organCode",getSessionOrgan().getThiscode());
        //完成任务流程
        ProcessInstance processInstance = tbOverService.completeTaskAudit(taskId, comment, varMap, msgMap);
        tbOverService.completeTask(processInstance, varMap, msgMap);
        return this.json.returnMsg("true", "提交超限额申请成功!").toJson();
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //修改条线临时额度申请
     * @Date 下午12:27 2019/11/17
     * @Param [tbReqDetail]
     **/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "修改条线临时额度申请", funCode = "PUB-13-04", funName = "修改条线临时额度申请", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(TbOver tbQuotaApply) throws Exception {
        tbOverService.updateByPK(tbQuotaApply);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }


    /**
     * 获取当前节点的下一个节点是否是结束节点.
     *
     * @param activityImpl
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月1日    	  杜旭    新建
     * </pre>
     */
    public boolean getLastUserType(ActivityImpl activityImpl) {
        boolean lastUserType = false;
        //获取当前活动完成之后节点的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                PvmActivity act = pvm.getDestination();
                //如果是网关的话，通过网关获取下一个节点的名称
                String name1 = String.valueOf(act.getProperty("name"));
                if ("Exclusive Gateway".equals(name1) || name1.contains("Gateway")) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            //如果连接的下一个节点的名称为End，则返回true
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
