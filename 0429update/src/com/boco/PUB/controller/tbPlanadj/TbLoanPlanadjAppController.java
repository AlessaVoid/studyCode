package com.boco.PUB.controller.tbPlanadj;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbPlanadj;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 提交审批信贷计划调整
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjApp")
public class TbLoanPlanadjAppController extends BaseController {

    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanadjService planadjService;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private IWorkFlowService workFlowService;

    /*信贷计划调整页面*/
    @RequestMapping("/commitTbPlanadjUI")
    @SystemLog(tradeName = "提交信贷计划调整", funCode = "PUB-06-01-05", funName = "提交信贷计划调整", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbPlanUI(@RequestParam String planadjId) throws Exception {
        authButtons();

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_MECH;
            auditorPrefix = AuditMix.PLAN_RESET_TOTAL_MECH_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_MECH;
            auditorPrefix = AuditMix.PLAN_RESET_ONE_MECH_AUDITOR_PREFIX;
        }

        //查询计划调整
        TbPlanadj tbPlanadj = planadjService.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //获取机构号
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        //获取下一节点审批人角色
        Map<String, Object> usermap = new HashMap<>();
        usermap.put("organLevel", getSessionOrgan().getOrganlevel());
        usermap.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        setAttribute("rolecode", rolecode);

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustSubmit/loanPlanAdjustDetailCommitPage";
    }

    @ResponseBody
    @RequestMapping("/startLoanPlanadjAudit")
    @SystemLog(tradeName = "启动审批流程", funCode = "PUB-06-01", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(String planadjId, String auditOper,String comment) throws Exception {
        PlainResult<String> result = planadjService.startLoanReqAuditProcess(planadjId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper,comment);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        return JSON.toJSONString(fdOperList);
    }

}
