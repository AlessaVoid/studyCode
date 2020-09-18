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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 信贷计划 已审批记录
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanApproved")
public class TbLoanPlanApprovedController extends BaseController {

    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailService tbPlanDetailService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService tbPlanService;


    @RequestMapping("/loadAssigneeAuditedTaskUI")
    @SystemLog(tradeName = "已审批信贷计划列表页", funCode = "PUB-03-08", funName = "已审批信贷计划列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanApprovd/loanPlanApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批信贷计划列表页数据", funCode = "PUB-03-08", funName = "已审批信贷计划列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(HttpServletRequest request,String auditStatus, String planMonth) throws Exception {
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
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            list = tbPlanService.getApprovedRecord(sort,sessionOperInfo.getOperCode(), auditStatus, planMonth, TbPlan.PLAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    @RequestMapping("/listTbPlanApprovedDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-03-08-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planId,String processInstanceId) throws Exception {
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

        return basePath + "/PUB/tbPlan/tbPlanApprovd/tbPlanApprovedDetailPage";

    }

}
