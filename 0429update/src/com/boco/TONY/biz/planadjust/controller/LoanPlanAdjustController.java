package com.boco.TONY.biz.planadjust.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 信贷计划业务控制层
 *
 * @author tony
 * @describe LoanPlanAdjustController
 * @date 2019-09-29
 */
@Controller
@RequestMapping("/LoanPlan/Adjust")
public class LoanPlanAdjustController extends BaseController {
    @Autowired
    ILoanPlanService loanPlanService;
    @Autowired
    FdOrganMapper fdOrganMapper;

    @RequestMapping("/showAdjustPlanListPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "调整信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String showAdjustPlanListPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustList/tbPlanAdjustList";
    }

    @RequestMapping("/showAdjustPlanDetailPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "调整信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String showAdjustPlanDetailPage() throws Exception {
        String planId = getParameter("planId");
        authButtons();
        PlainResult<TbPlan> result = loanPlanService.selectLoanPlanByPlanId(planId);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        setAttribute("tbPlanInfo", result.getData());
        List<FdOrganPlanInfo> fdOrganPlanInfoList = loanPlanService.initPlanDetailAdjustOrganInfo(planId, fdOrganList);
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustDetailAdjustPage";
    }

    @RequestMapping("/viewAdjustPlanDetailInfo")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "调整信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String listAdjustPlanDetailInfo() throws Exception {
        authButtons();
        String planId = getParameter("planId");
        PlainResult<TbPlan> result = loanPlanService.selectLoanPlanByPlanId(planId);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        setAttribute("tbPlanInfo", result.getData());
        List<FdOrganPlanInfo> fdOrganPlanInfoList = loanPlanService.initPlanDetailAdjustOrganInfo(planId, fdOrganList);
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/tbPlanAdjustDetailInfo";
    }

    @RequestMapping("/loadLoanPlanDetailInfoInfoPage")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "查询信贷计划详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanDetailInfoInfoPage(String planId) throws Exception {
        authButtons();
        PlainResult<TbPlan> result = loanPlanService.selectLoanPlanByPlanId(planId);
        FdOrgan fdOrgan = getSessionOrgan();
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        setAttribute("tbPlanInfo", result.getData());
        List<FdOrganPlanInfo> fdOrganPlanInfoList = loanPlanService.initPlanDetailOrganInfo(planId, fdOrganList);
        setAttribute("tbPlanOrganList", fdOrganPlanInfoList);
        return basePath + "/PUB/LoanPlan/tbPlanDetail/tbPlanDetailInfo";
    }

    @RequestMapping("/viewChildrenLoanReqInfo")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "查看信贷计划信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String viewChildrenLoanReqInfo() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlan/tbPlanList/tbReqListList";
    }

    @RequestMapping(value = "/adjustPlanDetailInfo", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-06-01-01", funName = "调整信贷计划详情", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String adjustPlanDetailInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail) throws Exception {
        authButtons();
        PlainResult<String> result = loanPlanService.adjustPlanDetailInfo(tbPlanAdjustDetail);
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
}

