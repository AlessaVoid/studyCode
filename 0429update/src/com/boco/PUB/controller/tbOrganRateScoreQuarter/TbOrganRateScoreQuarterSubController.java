package com.boco.PUB.controller.tbOrganRateScoreQuarter;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreQuarterDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbOrganRateScoreQuarterDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: 月度计划评分已提交记录
 * @Date: 季度评分
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreQuarterSub")
public class TbOrganRateScoreQuarterSubController extends BaseController {

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;
    @Autowired
    private TbOrganRateScoreQuarterDetailService tbOrganRateScoreQuarterDetailService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "已提交季度评分列表", funCode = "AL-04-02", funName = "已提交季度评分列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterSubmitted/tbOrganRateScoreQuarterSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交季度评分列表数据", funCode = "AL-04-02", funName = "已提交季度评分列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(HttpServletRequest request,String auditStatus, String rateMonth) throws Exception {
        authButtons();
        List<Map<String, Object>> list = null;
        try {
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("rateMonth".equals(sort)) {
                sort = "rate_month";

            } else if ("rateType".equals(sort)) {
                sort = "rate_type";

            } else if ("rateStatus".equals(sort)) {
                sort = "rate_status";

            } else if ("createTime".equals(sort)) {
                sort = "create_time";

            } else if ("updateTime".equals(sort)) {
                sort = "update_time";

            }
            if (sort != null) {
                sort = sort + " " + direction;
            }
            setPageParam();
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            list = tbOrganRateScoreService.getAuditRecordHistRecord(sort, sessionOperInfo.getOperCode(), auditStatus,
                    rateMonth,getSessionOperInfo(), OrganRateParamElementType.RATE_QUARTER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }


    @RequestMapping("/listRateScoreubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "AL-04-02-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String id, String processInstanceId) throws Exception {
        authButtons();


        //查询季度评分
        List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreQuarterDetail detail : tbOrganRateScoreQuarterDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //查询批注
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));
        setAttribute("organList", organList);

        return basePath +  "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterSubmitted/tbOrganRateScoreQuarterSubmitDetailPage";
    }


}
