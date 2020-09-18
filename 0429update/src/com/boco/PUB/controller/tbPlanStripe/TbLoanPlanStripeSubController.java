package com.boco.PUB.controller.tbPlanStripe;

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
 * @Description: ���߼ƻ����ύ��¼
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanStripeSub")
public class TbLoanPlanStripeSubController extends BaseController {

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
    @SystemLog(tradeName = "���ύ���߼ƻ��б�", funCode = "PUB-07-02", funName = "���ύ���߼ƻ��б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanSubmitted/loanPlanSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "���ύ���߼ƻ��б�����", funCode = "PUB-07-02", funName = "���ύ���߼ƻ��б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(HttpServletRequest request,String auditStatus, String planMonth) throws Exception {
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
                    planMonth,getSessionOperInfo(), TbPlan.STRIPE, sort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }


    @RequestMapping("/listTbPlanSubmitDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-07-02-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planId, String processInstanceId) throws Exception {
        authButtons();

        //��ѯ���߼ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ���߼ƻ�����map
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

        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("planId", planId);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("combList", combList);
        setAttribute("organList", organList);
        setAttribute("planMap", planMap);
        setAttribute("tradeParam", tradeParams.get(0));
        setAttribute("plan", plan);

        return basePath + "/PUB/tbPlanStripe/tbPlanSubmitted/tbPlanSubmitDetailPage";
    }

    //�����ƻ�ģ��
    @RequestMapping(value = "/downloadPlan")
    @SystemLog(tradeName = "�����ƻ�", funCode = "PUB-07", funName = "�����ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //��ȡ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        try {
            tbPlanService.downloadPlanStripe(response,request,organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}