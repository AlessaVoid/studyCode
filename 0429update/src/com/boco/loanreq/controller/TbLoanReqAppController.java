package com.boco.loanreq.controller;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.loanreq.service.ILoanReqAppService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.global.Dic;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbLoanReqAppController
 * @Description 信贷需求录入提交
 * @Author daice
 * @Date 2019/11/14 下午6:30
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLoanReqApp/")
public class TbLoanReqAppController extends BaseController {

    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    ILoanReqAppService loanReqAppService;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private IWorkFlowService workFlowService;


    @RequestMapping("commitTbReqUI")
    @SystemLog(tradeName = "PUB-01-04", funCode = "PUB-01-04", funName = "提交信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int reqId) throws Exception {
//        //获取信贷需求
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        if (tbReqList.getExpTimeStart() != null) {

            if (tbReqList.getReqDateStart() != null && tbReqList.getReqDateStart().trim().length() > 0)
                tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
            if (tbReqList.getReqDateEnd() != null && tbReqList.getReqDateEnd().trim().length() > 0)
                tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
            if (tbReqList.getReqTimeStart() != null && tbReqList.getReqTimeStart().trim().length() > 0)
                tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
            if (tbReqList.getReqTimeEnd() != null && tbReqList.getReqTimeEnd().trim().length() > 0)
                tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
            if (tbReqList.getExpTimeStart() != null && tbReqList.getExpTimeStart().trim().length() > 0)
                tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
            if (tbReqList.getExpTimeEnd() != null && tbReqList.getExpTimeEnd().trim().length() > 0)
                tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
            if (tbReqList.getRateTimeStart() != null && tbReqList.getRateTimeStart().trim().length() > 0)
                tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
            if (tbReqList.getRateTimeEnd() != null && tbReqList.getRateTimeEnd().trim().length() > 0)
                tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
            if (tbReqList.getBalanceTimeStart() != null && tbReqList.getBalanceTimeStart().trim().length() > 0)
                tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
            if (tbReqList.getBalanceTimeEnd() != null && tbReqList.getBalanceTimeEnd().trim().length() > 0)
                tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));


        }
        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqId);
        setAttr(tbReqList);

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {

        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_MECH_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else {
            processKey = AuditMix.REQ_TWO_MECH_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        }

        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);

        return basePath + "/PUB/LoanReqApp/reqSubmit/tbReqListCommitPage";

    }


    /**
     * 通用方法
     *
     * @param tbReqList
     * @throws Exception
     */
    private void setAttr(TbReqList tbReqList) throws Exception {
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        String lineListStr = "";

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "到期量");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "净增量");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "预计新发生利率(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "预计期末时点余额");
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
        } else {
            lineListStr = "这里设置条线的贷种组合_TODO";
        }

    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        return JSON.toJSONString(fdOperList);
    }

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //启动审批流程
     * @Date 下午8:55 2019/11/14
     * @Param [reqId, auditOper]
     **/
    @ResponseBody
    @RequestMapping("/startLoanReqAudit")
    @SystemLog(tradeName = "启动审批流程", funCode = "PUB-01-04", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int reqId, String auditOper, String comment) throws Exception {
        PlainResult<String> result = loanReqAppService.startLoanReqAuditProcess(reqId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, comment);
        return JSON.toJSONString(result);
    }
}
