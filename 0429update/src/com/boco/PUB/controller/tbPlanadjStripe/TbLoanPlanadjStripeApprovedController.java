package com.boco.PUB.controller.tbPlanadjStripe;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPlanadj;
import com.boco.SYS.entity.TbPlanadjDetail;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 条线计划调整 审批通过，已上报
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjStripeApproved")
public class TbLoanPlanadjStripeApprovedController extends BaseController {

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanadjServiceStripe planadjServiceStripe;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebLoanCombService loanCombService;


    @RequestMapping("/loadAssigneeAuditedTaskUI")
    @SystemLog(tradeName = "已审批条线计划调整列表页面", funCode = "PUB-06-04", funName = "已审批条线计划调整列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustApprovd/loanPlanAdjustApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批条线计划调整列表", funCode = "PUB-06-04", funName = "已审批条线计划调整列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(HttpServletRequest request,String auditStatus, String planMonth) throws Exception {
        authButtons();
        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");

        if ("planadjmonth".equals(sort)) {
            sort = "planadj_month";

        } else if ("planadjadjamount".equals(sort)) {
            sort = "planadj_adj_amount";

        } else if ("planadjrealincrement".equals(sort)) {
            sort = "planadj_real_increment";

        } else if ("planadjunit".equals(sort)) {
            sort = "planadj_unit";

        } else if ("planadjstatus".equals(sort)) {
            sort = "planadj_status";

        }else if ("planadjcreatetime".equals(sort)) {
            sort = "planadj_create_time";
        }
        if (sort != null) {
            sort = sort + " " + direction;
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = planadjServiceStripe.getApprovedRecord(sort,sessionOperInfo.getOperCode(), auditStatus, planMonth);

        return pageData(list);
    }

    @RequestMapping("/listTbPlanadjApprovedDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-06-04-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planadjId, String processInstanceId) throws Exception {
        authButtons();

        //查询计划调整
        TbPlanadj tbPlanadj = planadjServiceStripe.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //获取机构号
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);

        //获取审批信息
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustApprovd/tbPlanAdjustApprovedDetailPage";
    }
}