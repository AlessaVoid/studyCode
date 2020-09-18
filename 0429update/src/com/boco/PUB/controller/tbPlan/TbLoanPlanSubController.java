package com.boco.PUB.controller.tbPlan;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 信贷计划已提交记录
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanSub")
public class TbLoanPlanSubController extends BaseController {

    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailService tbPlanDetailService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private IWorkFlowService workFlowService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "已提交信贷计划列表", funCode = "PUB-03-06", funName = "已提交信贷计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanSubmitted/loanPlanSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交信贷计划列表数据", funCode = "PUB-03-06", funName = "已提交信贷计划列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(HttpServletRequest request, String auditStatus, String planMonth) throws Exception {
        authButtons();
        List<Map<String, Object>> list = null;
        try {

            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("planmonth".equals(sort)) {
                sort = "plan_month";

            } else if ("planrealincrement".equals(sort)) {
                sort = "plan_real_increment";

            } else if ("plancreatetime".equals(sort)) {
                sort = "plan_create_time";

            } else if ("planoper".equals(sort)) {
                sort = "plan_oper";

            } else if ("planupdatetime".equals(sort)) {
                sort = "plan_update_time";
            }
            if (sort != null) {
                sort = sort + " " + direction;
            }

            setPageParam();
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            list = tbPlanService.getAuditRecordHistRecord(sessionOperInfo.getOperCode(), auditStatus,
                    planMonth,getSessionOperInfo(), TbPlan.PLAN,sort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }


    @RequestMapping("/listTbPlanSubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-03-06-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planId, String processInstanceId) throws Exception {
        authButtons();

        //查询信贷计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装信贷计划详情map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }

        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //查询机构
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("plan", plan);

        return basePath + "/PUB/tbPlan/tbPlanSubmitted/tbPlanSubmitDetailPage";
    }

    //导出计划模板
    @RequestMapping(value = "/downloadPlan")
    @SystemLog(tradeName = "导出计划", funCode = "PUB-03", funName = "导出计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();
        try {
            tbPlanService.downloadPlan(response,request,organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
