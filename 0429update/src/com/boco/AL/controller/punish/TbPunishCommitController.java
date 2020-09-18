package com.boco.AL.controller.punish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.AL.service.ITbPunishListService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
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
 * @ClassName TbOverCommitController
 * @Description 罚息结果申请 提交
 * @Author tangxn
 * @Date 2019-11-14 下午8:15
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/tbTradeManger/punishListCommit/")
public class TbPunishCommitController extends BaseController {
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("commitTbQuotaUI")
    @SystemLog(tradeName = "AL", funCode = "AL", funName = "罚息结果申请提交", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int id) throws Exception {
        //获取信贷需求
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        //获取下一节点审批人角色
        Map<String, Object> map = new HashMap<>();

        /*流程key*/
        String processKey = "";
        /*初始审批人*/
        String auditorPrefix = "";
        /*机构级别*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*名称*/
        String lineName = getSessionOperInfo().getLineName();

        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        auditorPrefix = AuditMix.PUNISH_BASE_AUDITOR_PREFIX;


        //通过流程key获取最新版本的id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);

        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/CommitPage";

    }


    /**
     * @param tbPunishList 罚息列表
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "状态");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "当月总计划(亿元)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "月末闲置额度(亿元)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "月末闲置率(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "总闲置费(万元)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "实体考核计划(亿元)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "实体月末超计划额度(亿元)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "实体月末超计划幅度(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "实体超计划费(万元)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据考核计划(亿元)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划额度(亿元)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划幅度(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据超计划费(万元)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "罚息总计(万元)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "反馈意见");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }

    @ResponseBody
    @RequestMapping("getOperInfoListByRolecode")
    @SystemLog(tradeName = "罚息结果审批", funCode = "AL", funName = "获取罚息结果审批人员", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        String thisOrganCode = getSessionOperInfo().getOrganCode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(thisOrganCode, rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername());
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
    @SystemLog(tradeName = "启动审批流程", funCode = "AL", funName = "启动审批流程", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int id, String auditOper) throws Exception {
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);
        /*1，罚息结果，*/
        /*流程key*/
        String processKey = "";

        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        ProcessInstance pi = tbPunishListService.startLoanReqAuditProcess(id, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, processKey);
        PlainResult<String> result = tbPunishListService.compleLoanReqAuditProcess(pi, getSessionOperInfo().getOperCode(), auditOper, id, getSessionOperInfo().getOrganCode());
        return JSON.toJSONString(result);
    }


}
