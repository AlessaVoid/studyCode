package com.boco.PUB.controller.lineOver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbLineOver;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbLineOverCommitController
 * @Description 条线临时额度申请 提交
 * @Author tangxn
 * @Date 2019-11-14 下午8:15
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/tbTradeManger/lineOverCommit/")
public class TbLineOverCommitController extends BaseController {
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private ITbLineOverService tbLineTempService;
    @Autowired
    private WebOperLineMapper webOperLineMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private LineProductMapper lineProductMapper;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("commitTbQuotaUI")
    @SystemLog(tradeName = "PUB-14-01", funCode = "PUB-14-01", funName = "条线临时额度申请提交", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int qaId) throws Exception {
        //获取信贷需求
        TbLineOver tbQuotaApply = tbLineTempService.selectByPK(qaId);
        setAttr(tbQuotaApply);
        //TODO 这里计算数据库数据
        setAttribute("TbLineOver", tbQuotaApply);
        String oneInfo = tbQuotaApply.getQaTwoInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();

        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        /*超限额标准量*/
        BigDecimal overNum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            overNum = list.get(0).getParamOverMount();
        }
        /*申请所填写金额*/
        String qaAmtStr = tbQuotaApply.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i]));
        }
        int unit = tbQuotaApply.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.abs().multiply(unitNum);
        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel1)) {
            processKey = AuditMix.OVER_TOTAL_LINE_KEY;
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
            }
        } else if ("1".equals(organLevel1)) {
            processKey = AuditMix.OVER_ONE_LINE_KEY;
            int a = qaAmt.compareTo(overNum);
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
            }
        }

        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/lineOverManage/tbQuotaApply/tbQuotaListCommitPage";

    }

    /**
     * 通用方法
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbLineOver tbOver) throws Exception {
        String organLevel = getSessionOrgan().getOrganlevel();
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
    @RequestMapping("getOperInfoListByRolecode")
    @SystemLog(tradeName = "条线临时额度审批", funCode = "PUB-14-01", funName = "获取条线临时额度审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode,String qaId) throws Exception {
        authButtons();
        String thisOrganCode = getSessionOperInfo().getOrganCode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(thisOrganCode, rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();


        String lineCodeStr = tbLineTempService.selectByPK(Integer.parseInt(qaId)).getLineCode();
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

        for (FdOper tb : newFdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    /**
     * @return java.lang.String
     * @Author txn
     * @Description //启动审批流程
     * @Date 2019/11/17
     * @Param [qaId, auditOper]
     **/
    @ResponseBody
    @RequestMapping("startLoanQuotaApplyAudit")
    @SystemLog(tradeName = "启动审批流程", funCode = "PUB-01", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int qaId, String auditOper, String comment) throws Exception {
        TbLineOver tbQuotaApply = tbLineTempService.selectByPK(qaId);
        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel1)) {
            processKey = AuditMix.OVER_TOTAL_LINE_KEY;
        } else if ("1".equals(organLevel1)) {
            processKey = AuditMix.OVER_ONE_LINE_KEY;
        } else if ("2".equals(organLevel1)) {

        }

        ProcessInstance pi = tbLineTempService.startLoanReqAuditProcess(qaId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, processKey);
        PlainResult<String> result = tbLineTempService.compleLoanReqAuditProcess(pi, getSessionOperInfo().getOperCode(), auditOper, qaId, getSessionOperInfo().getOrganCode(), comment);

        return JSON.toJSONString(result);
    }


}
