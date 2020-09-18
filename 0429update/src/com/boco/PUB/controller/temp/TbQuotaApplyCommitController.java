package com.boco.PUB.controller.temp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbQuotaApplyService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanQuotaApply.ILoanQuotaApplyService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbQuotaApply;
import com.boco.SYS.entity.TbTradeParam;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbQuotaApplyCommitController
 * @Description 临时额度申请 提交
 * @Author tangxn
 * @Date 2019-11-14 下午8:15
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/tbTradeManger/tbQuotaApplyCommit/")
public class TbQuotaApplyCommitController extends BaseController {
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    private ITbQuotaApplyService tbQuotaApplyService;
    @Autowired
    private ILoanQuotaApplyService loanQuotaApplyService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;

    @RequestMapping("commitTbQuotaUI")
    @SystemLog(tradeName = "PUB-04-01", funCode = "PUB-04-01", funName = "临时额度申请提交", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int qaId) throws Exception {
        //获取信贷需求
        TbQuotaApply tbQuotaApply = tbQuotaApplyService.selectByPK(qaId);
        //TODO 这里计算数据库数据
        setAttribute("TbQuotaApply", tbQuotaApply);
        String oneInfo = tbQuotaApply.getQaOneInfo();
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
        /*临时额度标准量*/
        BigDecimal tempNum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            tempNum = list.get(0).getParamTempMount();
        }
        /*申请所填写金额*/
        BigDecimal qaAmt = tbQuotaApply.getQaAmt();
        int unit =tbQuotaApply.getUnit();
        BigDecimal unitNum =BigDecimal.ONE;
        if(unit==2){
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
            throw new Exception("总行不能申请临时额度！");
        } else if ("1".equals(organLevel1)) {
                processKey = AuditMix.TEMP_ONE_MECH_KEY;
                if (qaAmt.compareTo(tempNum) == 1) {
                    auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
                } else {
                    auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
                }
        } else if ("2".equals(organLevel1)) {

        }

        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);
        String fileId=tbQuotaApply.getQaFileId();
        String fileName="暂无附件，请上传";
        if(!"".equals(fileId)&&fileId.length()>0){
            fileName =fileId.substring(fileId.lastIndexOf("_-")+2);
        }
        setAttribute("fileName", fileName);

        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaListCommitPage";

    }


    @ResponseBody
    @RequestMapping("getOperInfoListByRolecode")
    @SystemLog(tradeName = "临时额度审批", funCode = "PUB-04-01", funName = "获取临时额度审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        String thisOrganCode = getSessionOperInfo().getOrganCode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(thisOrganCode, rolecode, getSessionOperInfo().getOperCode());
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
    public String startLoanReqAudit(int qaId, String auditOper,String comment) throws Exception {
        TbQuotaApply tbQuotaApply = tbQuotaApplyService.selectByPK(qaId);
        /*1，临时额度，2单笔专项*/
        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel1)) {
        } else if ("1".equals(organLevel1)) {
                processKey = AuditMix.TEMP_ONE_MECH_KEY;
        } else if ("2".equals(organLevel1)) {

        }

        PlainResult<String> result = loanQuotaApplyService.startLoanReqAuditProcess(qaId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, processKey,comment);
        return JSON.toJSONString(result);
    }


}
