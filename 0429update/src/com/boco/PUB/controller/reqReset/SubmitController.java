package com.boco.PUB.controller.reqReset;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.loanReqReset.ILoanReqResetService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: txn
 * @Description: 信贷需求调整   提交
 * @Date: 2019/11/15
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/tbReqResetSubmit/")
public class SubmitController extends BaseController {
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private ILoanReqResetService loanReqResetService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;

    @RequestMapping("commitTbReqresetUI")
    @SystemLog(tradeName = "PUB-05-02", funCode = "PUB-05-02", funName = "提交信贷需求调整", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int reqresetId) throws Exception {
        //获取信贷需求
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        setAttr(tbReqresetList);
        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {

        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQRESET_ONE_MECH_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else {
            processKey = AuditMix.REQRESET_TWO_MECH_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        }
        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);
        return basePath + "/PUB/tbReqResetManage/submit/tbReqresetDetailCommitPage";

    }


    //月份  需要查询的机构号
    private BigDecimal getPlanCount(String month, String organ) throws Exception {

        //获取上级机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }

        //获取计划
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //获取计划详情
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

        //计算总金额
        BigDecimal planCount = BigDecimal.ZERO;
        for (TbPlanDetail planDetail : tbPlanDetails) {
            planCount = planCount.add(planDetail.getPdAmount());
        }
        return planCount;

    }


    /**
     * 通用方法
     *
     * @param tbReqresetList
     * @throws Exception
     */
    private void setAttr(TbReqresetList tbReqresetList) throws Exception {
        int reqresetType = tbReqresetList.getReqresetType();
        String combListStr = "";

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //原计划，调整量，调整后金额

        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            //机构的 计划 查询条件 需要修改为上级机构 code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqresetList.getReqresetUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }


        BigDecimal one_oldTotalNum = BigDecimal.ZERO;
        BigDecimal two_oldTotalNum = BigDecimal.ZERO;
        BigDecimal three_oldTotalNum = BigDecimal.ZERO;
        if (reqresetType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqresetList.getReqresetCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                String codeStr = loanCombDO.getCombCode();
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(codeStr)) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combMap.put("oldNum", "0");
                        if (planIsOk) {
                            for (Map<String, Object> map : planList) {
                                String planCombCode = map.get("loantype").toString();
                                if (planCombCode.equals(codeStr)) {
                                    BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                    combMap.put("oldNum", oldPlan.divide(unit).toString());
                                    if (1 == loanCombDO.getCombLevel()) {
                                        one_oldTotalNum = BigDecimalUtil.add(one_oldTotalNum, oldPlan.divide(unit));
                                    } else if (2 == loanCombDO.getCombLevel()) {
                                        two_oldTotalNum = BigDecimalUtil.add(two_oldTotalNum, oldPlan.divide(unit));
                                    } else if (3 == loanCombDO.getCombLevel()) {
                                        three_oldTotalNum = BigDecimalUtil.add(three_oldTotalNum, oldPlan.divide(unit));
                                    }
                                    break;
                                }
                            }
                        }
                        combList.add(combMap);
                        break;
                    }
                }
            }

            setAttribute("combList", combList);
            setAttribute("one_oldTotalNum", one_oldTotalNum);
            setAttribute("two_oldTotalNum", two_oldTotalNum);
            setAttribute("three_oldTotalNum", three_oldTotalNum);
        }
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "信贷需求调整审批", funCode = "PUB-05-02", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        return JSON.toJSONString(fdOperList);
    }


    /**
     * @param
     * @Description 信贷需求录入调整 提交
     * @Author liujinbo
     * @Date 2019/11/15
     * @Return java.lang.String
     */
    @ResponseBody
    @RequestMapping("startLoanReqAudit")
    @SystemLog(tradeName = "信贷需求调整审批", funCode = "PUB-05", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit() throws Exception {
        PlainResult<String> result = loanReqResetService.startLoanReqAuditProcess(Integer.parseInt(getParameter("reqresetId")), getSessionOperInfo().getOrganCode(), getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), getParameter("auditOper"), getParameter("comment"));
        return JSON.toJSONString(result);
    }

}
