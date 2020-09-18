package com.boco.PUB.controller.tbLineReqReset;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.lineReqReset.ILineReqResetAppService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbLineReqresetDetail;
import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
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
 * @ClassName TbLoanReqAppController
 * @Description 信贷需求录入提交
 * @Author txn
 * @Date 2019/11/14 下午6:30
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLineReqResetApp/")
public class TbLineReqResetAppController extends BaseController {

    @Autowired
    private ITbLineReqresetDetailService tbLineReqresetDetailService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    ILineReqResetAppService lineReqResetAppService;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private WebOperLineMapper webOperLineMapper;


    @RequestMapping("commitTbReqUI")
    @SystemLog(tradeName = "PUB-10-01", funCode = "PUB-10-01", funName = "提交信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int lineReqresetId) throws Exception {
//        //获取信贷需求
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        setAttr(tbLineReqresetDetail);

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        /*条线名称*/
        String lineName = getSessionOperInfo().getLineName();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.REQRESET_TOTAL_LINE_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQRESET_ONE_LINE_KEY;
            auditorPrefix = AuditMix.REQRESET_BASE_AUDITOR_PREFIX;
        } else if ("2".equals(organLevel)) {

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

        return basePath + "/PUB/tbLineReqResetManage/submit/tbLineReqListCommitPage";

    }


    /**
     * 通用方法
     *
     * @param tbLineReqresetDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqresetDetail tbLineReqresetDetail) throws Exception {
        String combCodeStr = tbLineReqresetDetail.getLineCombCode();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);

        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());

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


        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            String codeStr = loanCombDO.getCombCode();
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combMap.put("oldNum", "0");
                    if (planIsOk) {
                        for (Map<String, Object> map : planList) {
                            String planCombCode = map.get("loantype").toString();
                            if (planCombCode.equals(codeStr)) {
                                BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                combMap.put("oldNum", oldPlan.divide(unit).toString());
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
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "获取需求审批人员", funCode = "PUB-01", funName = "获取需求审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode, String lineReqresetId) throws Exception {
        authButtons();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        String lineCodeStr = tbLineReqresetDetailService.selectByPK(Integer.parseInt(lineReqresetId)).getLineCode();
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
        if (newFdOperList.size() == 0) {
            return JSON.toJSONString(fdOperList);
        }
        return JSON.toJSONString(newFdOperList);

    }

    /**
     * @return java.lang.String
     * @Author txn
     * @Description //启动审批流程
     * @Date 下午8:55 2019/11/14
     * @Param [lineReqresetId, auditOper]
     **/
    @ResponseBody
    @RequestMapping("/startLoanReqAudit")
    @SystemLog(tradeName = "启动审批流程", funCode = "PUB-10-01", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int lineReqresetId, String auditOper, String comment) throws Exception {
        PlainResult<String> result = lineReqResetAppService.startLoanReqAuditProcess(lineReqresetId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, comment);
        return JSON.toJSONString(result);
    }
}
