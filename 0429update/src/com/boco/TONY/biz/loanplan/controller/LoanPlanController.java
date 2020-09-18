package com.boco.TONY.biz.loanplan.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDetailDTO;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划业务控制层
 *
 * @author tony
 * @describe LoanPlanController
 * @date 2019-09-29
 */
@Controller
@RequestMapping("/LoanPlan/LoanPlan")
public class LoanPlanController extends BaseController {
    @Autowired
    ILoanPlanService iLoanPlanService;
    @Autowired
    FdOrganMapper fdOrganMapper;

    @RequestMapping("/loanPlanIndexPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划首页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanIndexPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlan/LoanPlanList/tbPlanListList";
    }

    @RequestMapping("/loanPlanDetailUpdatePage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划详情更新页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanUpdatePage(String planId) throws Exception {
        authButtons();
        PlainResult<TbPlan> result = iLoanPlanService.selectLoanPlanByPlanId(planId);
        setAttribute("tbPlanInfo", result.getData());
        return basePath + "/PUB/LoanPlan/LoanPlanDetail/tbPlanDetailEdit";
    }

    @RequestMapping("/loadAllLoanPlanInfo")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03", funName = "加载信贷计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadAllLoanPlanInfo() throws Exception {
        authButtons();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        setPageParam();
        // Map<String, Object> jsonMap = iLoanPlanService.selectAllLoanPlanInfo(getSessionOrgan().getThiscode(), Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        Map<String, Object> jsonMap = iLoanPlanService.selectTbPlanAndTradeParam(getSessionOrgan().getThiscode(), Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        return JSON.toJSONString(jsonMap);
    }

    @RequestMapping("/loadLoanPlanInfoPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "查询信贷计划详情", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanInfoPage(String planId) throws Exception {
        authButtons();
        PlainResult<TbPlan> result = iLoanPlanService.selectLoanPlanByPlanId(planId);
        setAttribute("tbPlanInfo", result.getData());
        return basePath + "/PUB/LoanPlan/LoanPlanList/tbPlanListInfo";
    }

    @RequestMapping("/loadLoanPlanDetailInfoInsertPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划详情插入页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanDetailInfoInsertPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlan/LoanPlanDetail/tbPlanDetailAdd";
    }

    @RequestMapping("/initPlanDetailOrganInfo")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03", funName = "信贷计划详情机构信息", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String initPlanDetailOrganInfo(@RequestParam String planId) throws Exception {
        authButtons();

        PlainResult<TbPlan> tbPlanDOPlainResult = iLoanPlanService.selectLoanPlanByPlanId(planId);

        List<Map<String,Object>> resultList = new ArrayList<>();
        if (planId == null || "".equals(planId)) {
            resultList = iLoanPlanService.getFdOrganPlanInfoListNotPlanId(getSessionOrgan().getThiscode());
        } else {
            resultList = iLoanPlanService.getFdOrganPlanInfoList(planId, getSessionOrgan().getThiscode());
        }

        setAttribute("tbPlanDO", tbPlanDOPlainResult.getData());
        setAttribute("planId", planId);
        return JSON.toJSONString(resultList);
    }

    @RequestMapping("/loadLoanPlanDetailInfoInfoPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "查询信贷计划详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanDetailInfoInfoPage(String planId) throws Exception {
        authButtons();

        PlainResult<TbPlan> result = iLoanPlanService.selectLoanPlanByPlanId(planId);
        List<Map<String, Object>> fdOrganPlanInfoList = iLoanPlanService.getFdOrganPlanInfoList(planId, getSessionOrgan().getThiscode());

        setAttribute("tbPlanInfo", result.getData());
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlan/LoanPlanDetail/tbPlanDetailInfo";
    }

    @RequestMapping(value = "/createLoanPlanInfo", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "新增信贷计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String insertLoanPlanDetailInfo(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        PlainResult<String> result = iLoanPlanService.insertLoanPlan(request, operInfo.getOperCode(), sessionOrgan.getThiscode());
        return JSON.toJSONString(result);
    }

    @RequestMapping(value ="/updatePlanDetailInfo", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "更新信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String updatePlanDetailInfo(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        PlainResult<String> result = iLoanPlanService.updateLoanPlan(request, operInfo.getOperCode(), sessionOrgan.getThiscode());
        return JSON.toJSONString(result);
    }

    @RequestMapping("/deleteLoanPlanInfo")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05-03", funName = "删除信贷计划", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String deleteLoanPlanInfo(String planId) throws Exception {
        authButtons();
        PlainResult<String> result = iLoanPlanService.deleteLoanPlanInfo(planId);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/selectPlanDetailInfoByPlanId")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05-05", funName = "查询信贷计划详情", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String selectPlanDetailByPlanId(String planId) throws Exception {
        authButtons();
        PlainResult<TbPlanDetailDTO> result = iLoanPlanService.selectPlanDetailByPlanId(planId);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/getAllTbReqSpInfo")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "更新信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String getAllTbReqSpInfo() throws Exception {
        authButtons();
        ListResult<TbReqPlanInfo> result = iLoanPlanService.getAllTbReqSpInfo();
        return JSON.toJSONString(result);
    }

    @RequestMapping("/getChildrenOrganCode")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "更新信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String getChildrenOrganCode() throws Exception {
        authButtons();
        FdOrgan fdOrganQuery = new FdOrgan();
        FdOrgan sessionOrgan = getSessionOrgan();
        fdOrganQuery.setUporgan(sessionOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        return JSON.toJSONString(fdOrganList);
    }

    @RequestMapping("/selectByPlanId")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "计划联想查询", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String selectByPlanId(String planId) {
        List<String> planIdList = iLoanPlanService.selectByPlanId(planId);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : planIdList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping("/selectByPlanMonth")
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "计划联想查询", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String selectByPlanMonth(String planMonth) {
        List<String> planMonthList = iLoanPlanService.selectByPlanMonth(planMonth);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : planMonthList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }
}

